package com.itany.nmms.service.proxy;

import com.github.pagehelper.PageInfo;
import com.itany.nmms.entity.Dept;
import com.itany.nmms.exception.*;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.DeptService;
import com.itany.nmms.tran.TransactionManager;

import javax.servlet.http.HttpSession;
import java.util.List;

public class DeptServiceProxy implements DeptService {
    @Override
    public PageInfo<Dept> findPage(String pageNo, String pageSize) throws ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        DeptService deptService = (DeptService) ObjectFactory.getObject("deptServiceTarget");
        try {
            tran.begin();
            PageInfo<Dept> deptPage = deptService.findPage(pageNo, pageSize);
            tran.commit();
            return deptPage;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void addDept(String name, String remark, String fatherDept, HttpSession session) throws RequestParameterErrorException, DeptNotExistException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        DeptService deptService = (DeptService) ObjectFactory.getObject("deptServiceTarget");
        try {
            tran.begin();
            deptService.addDept(name, remark, fatherDept, session);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            tran.rollback();
            throw e;
        } catch (DeptNotExistException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void modifyDept(Integer deptId, String deptName, String remark) throws RequestParameterErrorException, DeptNotExistException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        DeptService deptService = (DeptService) ObjectFactory.getObject("deptServiceTarget");
        try {
            tran.begin();
            deptService.modifyDept(deptId, deptName, remark);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            tran.rollback();
            throw e;
        } catch (DeptNotExistException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void enableDept(String id) throws RequestParameterErrorException, DeptStatusException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        DeptService deptService = (DeptService) ObjectFactory.getObject("deptServiceTarget");
        try {
            tran.begin();
            deptService.enableDept(id);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            tran.rollback();
            throw e;
        } catch (DeptStatusException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void disableDept(String id) throws RequestParameterErrorException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        DeptService deptService = (DeptService) ObjectFactory.getObject("deptServiceTarget");
        try {
            tran.begin();
            deptService.disableDept(id);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public List<Dept> findAll() throws DeptNotExistException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        DeptService deptService = (DeptService) ObjectFactory.getObject("deptServiceTarget");
        try {
            tran.begin();
            List<Dept> depts = deptService.findAll();
            tran.commit();
            return depts;
        } catch (DeptNotExistException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }
}
