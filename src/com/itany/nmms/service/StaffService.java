package com.itany.nmms.service;

import com.github.pagehelper.PageInfo;
import com.itany.nmms.entity.Staff;
import com.itany.nmms.exception.*;

public interface StaffService {

    Staff login(String loginName,String password,String role,String code,String image) throws RequestParameterErrorException, CodeErrorException, StaffNotExistException, ServiceException;

    PageInfo<Staff> findPage(String pageNo, String pageSize, String staffName, String loginName, String phone, String email, String deptId, String role, String isValid) throws ServiceException;

    void addStaff(String userName, String loginName, String password, String phone, String email, String deptId, String role,String createStaffId) throws RequestParameterErrorException, StaffExistException, ServiceException;

    void modifyStaff(String staffName, String loginName, String id, String phone, String email, String deptId, String role, String createStaffId)throws RequestParameterErrorException, ServiceException;

    Staff findById(String id) throws RequestParameterErrorException, ServiceException;

    void modifyStatus(String id)throws RequestParameterErrorException, ServiceException;;
}
