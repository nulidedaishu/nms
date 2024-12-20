package com.itany.nmms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itany.nmms.constant.DictConstant;
import com.itany.nmms.constant.StatusConstant;
import com.itany.nmms.dao.StaffMapper;
import com.itany.nmms.dao.UserMapper;
import com.itany.nmms.entity.Staff;
import com.itany.nmms.entity.User;
import com.itany.nmms.entity.User;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.exception.StaffExistException;
import com.itany.nmms.exception.UserExistException;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.UserService;
import com.itany.nmms.util.ParameterUtil;

import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public PageInfo<User> findPage(String pageNo, String pageSize, String userName, String loginName, String phone, String address, String isValid) throws ServiceException {
        UserMapper userMapper = (UserMapper) ObjectFactory.getObject("userMapper");

        if (ParameterUtil.isNull(pageNo)) {
            pageNo = DictConstant.STAFF_DEFAULT_PAGE_NO;
        }
        if (ParameterUtil.isNull(pageSize)) {
            pageSize = DictConstant.STAFF_DEFAULT_PAGE_SIZE;
        }
        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        User param = new User();
        param.setUserName(ParameterUtil.escapeString(userName));
        param.setLoginName(ParameterUtil.escapeString(loginName));
        param.setPhone(ParameterUtil.escapeString(phone));
        param.setAddress(ParameterUtil.escapeString(address));
        if (!ParameterUtil.isNull(isValid)) {
            param.setIsValid(Integer.parseInt(isValid));
        }
        List<User> users = userMapper.selectByParams(param);

        PageInfo<User> userPage = new PageInfo<>(users);
        return userPage;
    }

    @Override
    public User findById(String id) throws RequestParameterErrorException, ServiceException {
        UserMapper userMapper = (UserMapper) ObjectFactory.getObject("userMapper");
        if (ParameterUtil.isNull(id)) {
            throw new RequestParameterErrorException("请求参数有误");
        }
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(id));
        return user;
    }

    @Override
    public void modifyStatus(String id) throws RequestParameterErrorException, ServiceException {
        UserMapper userMapper = (UserMapper) ObjectFactory.getObject("userMapper");
        if (ParameterUtil.isNull(id)) {
            throw new RequestParameterErrorException("请求参数有误");
        }
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(id));
        if (StatusConstant.USER_IS_VALID_YES == user.getIsValid()) {
            user.setIsValid(StatusConstant.USER_IS_VALID_NO);
        } else {
            user.setIsValid(StatusConstant.USER_IS_VALID_YES);
        }
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void modifyUser(String id, String userName, String loginName, String phone, String address) throws RequestParameterErrorException, ServiceException {
        UserMapper userMapper = (UserMapper) ObjectFactory.getObject("userMapper");

        if (ParameterUtil.isNull(userName) || ParameterUtil.isNull(loginName) || ParameterUtil.isNull(id) || ParameterUtil.isNull(phone) || ParameterUtil.isNull(address)) {
            throw new RequestParameterErrorException("请求参数有误");
        }
        User user = new User();
        user.setLoginName(loginName);
        user.setUserName(userName);
        user.setUserId(Integer.valueOf(id));
        user.setPhone(phone);
        user.setAddress(address);

        userMapper.updateByPrimaryKeySelective(user);
    }
}
