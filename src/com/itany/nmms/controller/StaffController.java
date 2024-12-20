package com.itany.nmms.controller;

import com.github.pagehelper.PageInfo;
import com.itany.mvc.annotation.RequestMapping;
import com.itany.mvc.annotation.ResponseBody;
import com.itany.mvc.util.CommonsMultipartFile;
import com.itany.nmms.constant.ResponseCodeConstant;
import com.itany.nmms.entity.Dept;
import com.itany.nmms.entity.Product;
import com.itany.nmms.entity.Staff;
import com.itany.nmms.exception.*;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.DeptService;
import com.itany.nmms.service.StaffService;
import com.itany.nmms.util.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/staff")
public class StaffController {

    private StaffService staffService = (StaffService) ObjectFactory.getObject("staffService");
    private DeptService deptService = (DeptService) ObjectFactory.getObject("deptService");

    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String staffName = request.getParameter("userName");
        String loginName = request.getParameter("loginName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String deptId = request.getParameter("deptId");
        String role = request.getParameter("role");
        String isValid = request.getParameter("isValid");
        request.setAttribute("userName", staffName);
        request.setAttribute("loginName", loginName);
        request.setAttribute("phone", phone);
        request.setAttribute("email", email);
        request.setAttribute("deptId", deptId);
        request.setAttribute("role", role);
        request.setAttribute("isValid", isValid);

        try {
            List<Dept> depts = deptService.findAll();
            request.setAttribute("depts", depts);
            PageInfo<Staff> staffPage = staffService.findPage(pageNo, pageSize, staffName, loginName, phone, email, deptId, role, isValid);
            request.setAttribute("staffPage", staffPage);
        } catch (ServiceException | DeptNotExistException e) {
            e.printStackTrace();
        }
        return "backend/staffManager";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        String image = (String) session.getAttribute("code");

        try {
            Staff staff = staffService.login(loginName, password, role, code, image);
            request.getSession().setAttribute("staff", staff);

            return "redirect:/showMain.do";
        } catch (RequestParameterErrorException e) {
            request.setAttribute("loginMsg", e.getMessage());
            return "/showLogin.do";
        } catch (CodeErrorException e) {
            request.setAttribute("loginMsg", e.getMessage());
            return "backend/login";
        } catch (StaffNotExistException e) {
            request.setAttribute("loginMsg", e.getMessage());
            return "backend/login";
        } catch (ServiceException e) {
            request.setAttribute("loginMsg", e.getMessage());
            return "backend/login";
        }
    }

    @ResponseBody
    @RequestMapping("/addStaff")
    public ResponseResult addStaff(HttpServletRequest request, HttpServletResponse response, List<CommonsMultipartFile> files) {
        ResponseResult result = new ResponseResult();
        String userName = request.getParameter("userName");
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String deptId = request.getParameter("deptId");
        String role = request.getParameter("role");
        String createStaffId = request.getParameter("createStaffId");
        try {
            staffService.addStaff(userName, loginName, password, phone, email, deptId, role, createStaffId);
            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
        } catch (RequestParameterErrorException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (StaffExistException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
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
    @RequestMapping("/modifyStaff")
    public ResponseResult modifyStaff(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        String id = request.getParameter("MargerStaffId");
        String staffName = request.getParameter("MargerStaffname");
        String loginName = request.getParameter("MargerLoginName");
        String phone = request.getParameter("MargerPhone");
        String email = request.getParameter("MargerAdrees");
        String role = request.getParameter("MargerRole");
        String deptId = request.getParameter("MargerDept");
        String createStaffId = request.getParameter("createStaffId");
        System.out.println("1111111111111");
        System.out.println(staffName);
        try {
            staffService.modifyStaff(staffName, loginName, id, phone, email, deptId, role, createStaffId);
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
    @RequestMapping("/findById")
    public ResponseResult findById(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        String id = request.getParameter("id");
        try {
            Staff staff = staffService.findById(id);
            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setReturnObject(staff);
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
    @RequestMapping("/modifyStatus")
    public ResponseResult modifyStatus(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        String id = request.getParameter("id");
        try {
            staffService.modifyStatus(id);
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
