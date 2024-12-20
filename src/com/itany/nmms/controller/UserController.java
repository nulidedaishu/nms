package com.itany.nmms.controller;

import com.github.pagehelper.PageInfo;
import com.itany.mvc.annotation.RequestMapping;
import com.itany.mvc.annotation.ResponseBody;
import com.itany.nmms.constant.ResponseCodeConstant;
import com.itany.nmms.entity.Dept;
import com.itany.nmms.entity.Staff;
import com.itany.nmms.entity.User;
import com.itany.nmms.exception.*;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.UserService;
import com.itany.nmms.util.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/user")
public class UserController {

    private UserService userService = (UserService) ObjectFactory.getObject("userService");

    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String userName = request.getParameter("userName");
        String loginName = request.getParameter("loginName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String isValid = request.getParameter("isValid");
        request.setAttribute("userName", userName);
        request.setAttribute("loginName", loginName);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);
        request.setAttribute("isValid", isValid);

        try {
            PageInfo<User> userPage = userService.findPage(pageNo, pageSize, userName, loginName, phone, address, isValid);
            request.setAttribute("userPage", userPage);
            System.out.println(userPage);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "backend/userManager";
    }

    @ResponseBody
    @RequestMapping("/findById")
    public ResponseResult findById(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        String id = request.getParameter("id");
        try {
            User user = userService.findById(id);
            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setReturnObject(user);
        } catch (RequestParameterErrorException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (ServiceException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/modifyUser")
    public ResponseResult modifyUser(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("1111111111111");
        ResponseResult result = new ResponseResult();
        String id = request.getParameter("MargerUserId");
        String userName = request.getParameter("MargerUsername");
        String loginName = request.getParameter("MargerLoginName");
        String phone = request.getParameter("MargerPhone");
        String address = request.getParameter("MargerAdrees");
        try {
            userService.modifyUser(id, userName, loginName, phone, address);
            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
        } catch (RequestParameterErrorException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (ServiceException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        } catch (Exception e) {
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            e.printStackTrace();
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/modifyStatus")
    public ResponseResult modifyStatus(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        String id = request.getParameter("id");
        try {
            userService.modifyStatus(id);
            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
        } catch (RequestParameterErrorException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (ServiceException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }
        return result;
    }
}
