package com.itany.nmms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itany.nmms.constant.DictConstant;
import com.itany.nmms.constant.StatusConstant;
import com.itany.nmms.dao.DeptMapper;
import com.itany.nmms.dao.SequenceMapper;
import com.itany.nmms.entity.*;
import com.itany.nmms.exception.DeptNotExistException;
import com.itany.nmms.exception.DeptStatusException;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.DeptService;
import com.itany.nmms.util.ParameterUtil;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DeptServiceImpl implements DeptService {
    @Override
    public PageInfo<Dept> findPage(String pageNo, String pageSize) throws ServiceException {
        DeptMapper deptMapper = (DeptMapper) ObjectFactory.getObject("deptMapper");

        if (ParameterUtil.isNull(pageNo)) {
            pageNo = DictConstant.DEPT_DEFAULT_PAGE_NO;
        }
        if (ParameterUtil.isNull(pageSize)) {
            pageSize = DictConstant.DEPT_DEFAULT_PAGE_SIZE;
        }

        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        List<Dept> depts = deptMapper.selectFatherDeptName(new DeptExample());

        PageInfo<Dept> deptPage = new PageInfo<>(depts);

        return deptPage;
    }

    @Override
    public void addDept(String name, String remark, String fatherDept, HttpSession session) throws RequestParameterErrorException, DeptNotExistException, ServiceException {
        DeptMapper deptMapper = (DeptMapper) ObjectFactory.getObject("deptMapper");
        SequenceMapper sequenceMapper = (SequenceMapper) ObjectFactory.getObject("sequenceMapper");

        if (ParameterUtil.isNull(name) || ParameterUtil.isNull(remark)) {
            throw new RequestParameterErrorException("请求参数有误");
        }

        DeptExample example = new DeptExample();
        example.or()
                .andDeptNameEqualTo(name);
        List<Dept> depts = deptMapper.selectByExample(example);
        if (!depts.isEmpty()) {
            throw new DeptNotExistException("该部门已经存在");
        }

        Dept dept = new Dept();
        dept.setDeptName(name);
        dept.setRemark(remark);

        SequenceExample sequenceExample = new SequenceExample();
        sequenceExample.or()
                .andNameEqualTo(DictConstant.DEPT_NO_PREFIX);
        List<Sequence> sequences = sequenceMapper.selectByExample(sequenceExample);
        if (sequences.isEmpty()) {
            Sequence sequence = new Sequence();
            sequence.setName(DictConstant.DEPT_NO_PREFIX);
            sequence.setValue(DictConstant.DEPT_NO_INIT_VALUE);
            sequenceMapper.insertSelective(sequence);
            dept.setDeptNo(DictConstant.DEPT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequence.getValue());
        } else {
            Sequence sequence = sequences.get(0);
            if (DictConstant.DEPT_NO_MAX_VALUE.equals(sequence.getValue())) {
                sequence.setValue(DictConstant.DEPT_NO_INIT_VALUE);
            } else {
                sequence.setValue(ParameterUtil.nextValue(sequence.getValue()));
            }
            sequenceMapper.updateByPrimaryKeySelective(sequence);
            dept.setDeptNo(DictConstant.DEPT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequence.getValue());
        }

        Integer createStaffId = (Integer) session.getAttribute("userId");
        dept.setCreateStaffId(createStaffId);

        Date createDate = new Date();
        dept.setCreateDate(createDate);

        dept.setIsValid(StatusConstant.DEPT_IS_VALID_YES);

        if (!ParameterUtil.isNull(fatherDept)) {
            DeptExample example1 = new DeptExample();
            example1.createCriteria().andDeptNameEqualTo(fatherDept);
            List<Dept> deptFathers = deptMapper.selectByExample(example1);
            Dept deptFather = deptFathers.get(0);
            Integer deptId = deptFather.getDeptId();
            dept.setFatherDeptId(deptId);
        }

        deptMapper.insertSelective(dept);
    }

    @Override
    public void modifyDept(Integer deptId, String deptName, String remark) throws RequestParameterErrorException, DeptNotExistException, ServiceException {
        DeptMapper deptMapper = (DeptMapper) ObjectFactory.getObject("deptMapper");

        if (ParameterUtil.isNull(String.valueOf(deptId)) ||ParameterUtil.isNull(deptName) || ParameterUtil.isNull(remark)) {
            throw new RequestParameterErrorException("请求参数有误");
        }

        DeptExample example = new DeptExample();
        example.or()
                .andDeptNameEqualTo(deptName);
        List<Dept> depts = deptMapper.selectByExample(example);
        if (!depts.isEmpty()&& !depts.get(0).getDeptId().equals(deptId)) {
            throw new DeptNotExistException("该部门已经存在");
        }

        Dept dept=deptMapper.selectByPrimaryKey(deptId);

        dept.setDeptName(deptName);
        dept.setRemark(remark);

        deptMapper.updateByPrimaryKey(dept);
    }

    @Override
    public void enableDept(String id) throws RequestParameterErrorException, DeptStatusException {
        DeptMapper deptMapper = (DeptMapper) ObjectFactory.getObject("deptMapper");
        if (ParameterUtil.isNull(id)) {
            throw new RequestParameterErrorException("请求参数有误");
        }
        Dept dept = deptMapper.selectByPrimaryKey(Integer.parseInt(id));
        Dept fatherDept = deptMapper.selectByPrimaryKey(dept.getFatherDeptId());
        if (fatherDept != null && StatusConstant.DEPT_IS_VALID_NO == fatherDept.getIsValid()) {
            throw new DeptStatusException("请先启用上级部门");
        }
        dept.setIsValid(StatusConstant.DEPT_IS_VALID_YES);
        deptMapper.updateByPrimaryKeySelective(dept);
    }

    @Override
    public void disableDept(String id) throws RequestParameterErrorException {
        DeptMapper deptMapper = (DeptMapper) ObjectFactory.getObject("deptMapper");
        if (ParameterUtil.isNull(id)) {
            throw new RequestParameterErrorException("请求参数有误");
        }
        Dept dept = deptMapper.selectByPrimaryKey(Integer.parseInt(id));
        dept.setIsValid(StatusConstant.DEPT_IS_VALID_NO);
        deptMapper.updateByPrimaryKeySelective(dept);
        DeptExample example = new DeptExample();
        example.or()
                .andFatherDeptIdEqualTo(dept.getDeptId());
        List<Dept> sonDepts = deptMapper.selectByExample(example);
        for (Dept sonDept : sonDepts) {
            disableDept(sonDept.getDeptId().toString());
        }
    }


    @Override
    public List<Dept> findAll() throws DeptNotExistException, ServiceException {
        DeptMapper deptMapper= (DeptMapper) ObjectFactory.getObject("deptMapper");
        List<Dept> depts=deptMapper.selectByExample(new DeptExample());
        if (depts.isEmpty()){
            throw new DeptNotExistException("请先添加部门");
        }
        return depts;
    }
}
