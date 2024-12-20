package com.itany.nmms.service;

import com.github.pagehelper.PageInfo;
import com.itany.nmms.entity.Dept;
import com.itany.nmms.exception.DeptNotExistException;
import com.itany.nmms.exception.DeptStatusException;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface DeptService {

    PageInfo<Dept> findPage(String pageNo, String pageSize) throws ServiceException;

    void addDept(String name, String remark,String fatherDept, HttpSession session) throws RequestParameterErrorException, DeptNotExistException, ServiceException;

    void modifyDept(Integer deptId,String deptName,String remark) throws RequestParameterErrorException, DeptNotExistException, ServiceException;

    void enableDept(String id) throws RequestParameterErrorException, DeptStatusException, ServiceException;

    void disableDept(String id) throws RequestParameterErrorException, ServiceException;

    List<Dept> findAll() throws DeptNotExistException,ServiceException;
}
