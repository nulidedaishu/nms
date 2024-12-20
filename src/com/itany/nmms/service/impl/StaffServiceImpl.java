package com.itany.nmms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itany.nmms.constant.DictConstant;
import com.itany.nmms.constant.StatusConstant;

import com.itany.nmms.dao.ProductMapper;
import com.itany.nmms.dao.StaffMapper;
import com.itany.nmms.dao.UserMapper;
import com.itany.nmms.entity.*;
import com.itany.nmms.exception.*;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.StaffService;
import com.itany.nmms.util.MD5Util;
import com.itany.nmms.util.ParameterUtil;

import java.util.Date;
import java.util.List;

public class StaffServiceImpl implements StaffService {

    @Override
    public Staff login(String loginName, String password, String role, String code, String image) throws RequestParameterErrorException, CodeErrorException, StaffNotExistException {
        StaffMapper staffMapper = (StaffMapper) ObjectFactory.getObject("staffMapper");

        if (ParameterUtil.isNull(loginName) || ParameterUtil.isNull(password) || ParameterUtil.isNull(role) || ParameterUtil.isNull(code) || ParameterUtil.isNull(image)) {
            throw new RequestParameterErrorException("请求参数有误");
        }

        if (!code.equals(image)) {
            throw new CodeErrorException("验证码不正确");
        }

        StaffExample example = new StaffExample();
        example.or()
                .andLoginNameEqualTo(loginName)
                .andPasswordEqualTo(MD5Util.md5(password))
                .andRoleEqualTo(role)
                .andIsValidEqualTo(StatusConstant.STAFF_IS_VALID_YES);
        List<Staff> staffs = staffMapper.selectByExample(example);
        if (staffs.isEmpty()) {
            throw new StaffNotExistException("账号或密码错误");
        }
        return staffs.get(0);
    }

    @Override
    public PageInfo<Staff> findPage(String pageNo, String pageSize, String staffName, String loginName, String phone, String email, String deptId, String role, String isValid) {
        StaffMapper staffMapper = (StaffMapper) ObjectFactory.getObject("staffMapper");

        if (ParameterUtil.isNull(pageNo)) {
            pageNo = DictConstant.STAFF_DEFAULT_PAGE_NO;
        }
        if (ParameterUtil.isNull(pageSize)) {
            pageSize = DictConstant.STAFF_DEFAULT_PAGE_SIZE;
        }
        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        Staff param = new Staff();
        param.setStaffName(ParameterUtil.escapeString(staffName));
        param.setLoginName(ParameterUtil.escapeString(loginName));
        param.setPhone(ParameterUtil.escapeString(phone));
        param.setEmail(ParameterUtil.escapeString(email));
        if (!ParameterUtil.isNull(deptId)) {
            param.setDeptId(Integer.parseInt(deptId));
        }
        param.setRole(role);
        if (!ParameterUtil.isNull(isValid)) {
            param.setIsValid(Integer.parseInt(isValid));
        }
        List<Staff> staffs = staffMapper.selectByParams(param);

        PageInfo<Staff> staffPage = new PageInfo<>(staffs);
        return staffPage;
    }

    @Override
    public void addStaff(String userName, String loginName, String password, String phone, String email, String deptId, String role, String createStaffId) throws RequestParameterErrorException, StaffExistException, ServiceException {
        StaffMapper staffMapper = (StaffMapper) ObjectFactory.getObject("staffMapper");

        if (ParameterUtil.isNull(userName) || ParameterUtil.isNull(loginName) || ParameterUtil.isNull(password) || ParameterUtil.isNull(phone) || ParameterUtil.isNull(email) || ParameterUtil.isNull(deptId) || ParameterUtil.isNull(role)) {
            throw new RequestParameterErrorException("请求参数有误");
        }

        StaffExample example = new StaffExample();
        example.or()
                .andLoginNameEqualTo(loginName);
        List<Staff> staffs = staffMapper.selectByExample(example);
        if (!staffs.isEmpty()) {
            throw new StaffExistException("该管理员已经存在");
        }


        Staff staff = new Staff();
        staff.setLoginName(loginName);
        staff.setStaffName(userName);
        staff.setPassword(MD5Util.md5(password));
        staff.setPhone(phone);
        staff.setEmail(email);
        staff.setDeptId(Integer.valueOf(deptId));
        staff.setRole(role);
        staff.setIsValid(1);
        staff.setCreateDate(new Date());
        if (!ParameterUtil.isNull(createStaffId)) {
            staff.setCreateStaffId(Integer.valueOf(createStaffId));
        }

        staffMapper.insertSelective(staff);
    }

    @Override
    public void modifyStaff(String staffName, String loginName, String id, String phone, String email, String deptId, String role, String createStaffId) throws RequestParameterErrorException, ServiceException {
        StaffMapper staffMapper = (StaffMapper) ObjectFactory.getObject("staffMapper");

        if (ParameterUtil.isNull(staffName) || ParameterUtil.isNull(loginName) || ParameterUtil.isNull(id) || ParameterUtil.isNull(phone) || ParameterUtil.isNull(email) || ParameterUtil.isNull(deptId) || ParameterUtil.isNull(role)) {
            throw new RequestParameterErrorException("请求参数有误");
        }



        Staff staff = new Staff();
        staff.setLoginName(loginName);
        staff.setStaffName(staffName);
        staff.setStaffId(Integer.valueOf(id));
        staff.setPhone(phone);
        staff.setEmail(email);
        staff.setDeptId(Integer.valueOf(deptId));
        staff.setRole(role);
        staff.setCreateDate(new Date());
        if (!ParameterUtil.isNull(createStaffId)) {
            staff.setCreateStaffId(Integer.valueOf(createStaffId));
        }

        staffMapper.updateByPrimaryKeySelective(staff);
    }

    @Override
    public Staff findById(String id) throws RequestParameterErrorException, ServiceException {
        StaffMapper staffMapper = (StaffMapper) ObjectFactory.getObject("staffMapper");
        if (ParameterUtil.isNull(id)) {
            throw new RequestParameterErrorException("请求参数有误");
        }
        Staff staff = staffMapper.selectByPrimaryKey(Integer.parseInt(id));
        return staff;
    }

    @Override
    public void modifyStatus(String id) throws RequestParameterErrorException, ServiceException {
        StaffMapper staffMapper = (StaffMapper) ObjectFactory.getObject("staffMapper");
        if (ParameterUtil.isNull(id)) {
            throw new RequestParameterErrorException("请求参数有误");
        }
        Staff staff = staffMapper.selectByPrimaryKey(Integer.parseInt(id));
        if (StatusConstant.STAFF_IS_VALID_YES == staff.getIsValid()) {
            staff.setIsValid(StatusConstant.STAFF_IS_VALID_NO);
        } else {
            staff.setIsValid(StatusConstant.STAFF_IS_VALID_YES);
        }
        staffMapper.updateByPrimaryKeySelective(staff);
    }
    
}
