package com.itany.nmms.service.proxy;

import com.github.pagehelper.PageInfo;
import com.itany.nmms.entity.Product;
import com.itany.nmms.entity.Staff;
import com.itany.nmms.exception.*;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.ProductService;
import com.itany.nmms.service.StaffService;
import com.itany.nmms.tran.TransactionManager;

public class StaffServiceProxy implements StaffService {
    @Override
    public Staff login(String loginName, String password, String role, String code, String image) throws RequestParameterErrorException, CodeErrorException, StaffNotExistException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        StaffService staffService = (StaffService) ObjectFactory.getObject("staffServiceTarget");
        try {
            tran.begin();
            Staff staff = staffService.login(loginName, password, role, code, image);
            tran.commit();
            return staff;
        } catch (RequestParameterErrorException e) {
            e.printStackTrace();
            tran.rollback();
            throw e;
        } catch (CodeErrorException e) {
            e.printStackTrace();
            tran.rollback();
            throw e;
        } catch (StaffNotExistException e) {
            e.printStackTrace();
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            e.printStackTrace();
            tran.rollback();
            throw e;
        }
    }

    @Override
    public PageInfo<Staff> findPage(String pageNo, String pageSize, String staffName, String loginName, String phone, String email, String deptId, String role, String isValid) throws ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        StaffService staffService = (StaffService) ObjectFactory.getObject("staffServiceTarget");
        try {
            tran.begin();
            PageInfo<Staff> staffPage = staffService.findPage(pageNo, pageSize, staffName, loginName, phone, email, deptId, role, isValid);
            tran.commit();
            return staffPage;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void addStaff(String userName, String loginName, String password, String phone, String email, String deptId, String role, String createStaffId) throws RequestParameterErrorException, StaffExistException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        StaffService staffService = (StaffService) ObjectFactory.getObject("staffServiceTarget");
        try {
            tran.begin();
            staffService.addStaff(userName, loginName, password, phone, email, deptId, role,createStaffId);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            e.printStackTrace();
            tran.rollback();
            throw e;
        } catch (StaffExistException e) {
            e.printStackTrace();
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            e.printStackTrace();
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void modifyStaff(String staffName, String loginName, String id, String phone, String email, String deptId, String role, String createStaffId) throws RequestParameterErrorException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        StaffService staffService = (StaffService) ObjectFactory.getObject("staffServiceTarget");
        try {
            tran.begin();
            staffService.modifyStaff(staffName, loginName, id, phone, email, deptId, role,createStaffId);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            e.printStackTrace();
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            e.printStackTrace();
            tran.rollback();
            throw e;
        }
    }

    @Override
    public Staff findById(String id) throws RequestParameterErrorException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        StaffService staffService = (StaffService) ObjectFactory.getObject("staffServiceTarget");
        try {
            tran.begin();
            Staff staff = staffService.findById(id);
            tran.commit();
            return staff;
        } catch (RequestParameterErrorException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void modifyStatus(String id) throws RequestParameterErrorException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        StaffService staffService = (StaffService) ObjectFactory.getObject("staffServiceTarget");
        try {
            tran.begin();
            staffService.modifyStatus(id);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

}
