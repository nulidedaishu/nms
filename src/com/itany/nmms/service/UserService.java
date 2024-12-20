package com.itany.nmms.service;

import com.github.pagehelper.PageInfo;
import com.itany.nmms.entity.User;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.exception.StaffExistException;

public interface UserService {

    PageInfo<User> findPage(String pageNo, String pageSize, String userName, String loginName, String phone, String address, String isValid) throws ServiceException;

    User findById(String id)throws RequestParameterErrorException, ServiceException;

    void modifyStatus(String id)throws RequestParameterErrorException, ServiceException;;

    void modifyUser(String id, String userName, String loginName, String phone, String address)throws RequestParameterErrorException, ServiceException;

}
