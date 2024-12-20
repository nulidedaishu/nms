package com.itany.nmms.service.proxy;

import com.github.pagehelper.PageInfo;
import com.itany.nmms.entity.Staff;
import com.itany.nmms.entity.User;
import com.itany.nmms.entity.User;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.exception.StaffExistException;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.StaffService;
import com.itany.nmms.service.UserService;
import com.itany.nmms.service.UserService;
import com.itany.nmms.tran.TransactionManager;

public class UserServiceProxy implements UserService {
    @Override
    public PageInfo<User> findPage(String pageNo, String pageSize, String userName, String loginName, String phone, String address, String isValid) throws ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        UserService userService = (UserService) ObjectFactory.getObject("userServiceTarget");
        try {
            tran.begin();
            PageInfo<User> userPage = userService.findPage(pageNo, pageSize, userName, loginName, phone, address, isValid);
            tran.commit();
            return userPage;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public User findById(String id) throws RequestParameterErrorException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        UserService userService = (UserService) ObjectFactory.getObject("userServiceTarget");
        try {
            tran.begin();
            User user = userService.findById(id);
            tran.commit();
            return user;
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
        UserService userService = (UserService) ObjectFactory.getObject("userServiceTarget");
        try {
            tran.begin();
            userService.modifyStatus(id);
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
    public void modifyUser(String id, String userName, String loginName, String phone, String address) throws RequestParameterErrorException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        UserService userService = (UserService) ObjectFactory.getObject("userServiceTarget");
        try {
            tran.begin();
            userService.modifyUser(id, userName, loginName, phone, address);
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

}
