package com.itany.nmms.controller;

import com.github.pagehelper.PageInfo;
import com.itany.mvc.annotation.RequestMapping;
import com.itany.mvc.annotation.ResponseBody;
import com.itany.nmms.constant.ResponseCodeConstant;
import com.itany.nmms.entity.Dept;
import com.itany.nmms.exception.DeptNotExistException;
import com.itany.nmms.exception.DeptStatusException;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.DeptService;
import com.itany.nmms.util.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/dept")
public class DeptController {
    private DeptService deptService = (DeptService) ObjectFactory.getObject("deptService");

    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        try {
            PageInfo<Dept> deptPage = deptService.findPage(pageNo, pageSize);
            request.setAttribute("deptPage", deptPage);
        } catch (ServiceException e) {
            request.setAttribute("deptMsg", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("deptMsg", "服务器内部异常");
            e.printStackTrace();
        }
        return "backend/deptManager";
    }

    @ResponseBody
    @RequestMapping("/addDept")
    public ResponseResult addDept(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        String fatherDept = request.getParameter("fatherDept");
        try {
            deptService.addDept(name, remark, fatherDept, request.getSession());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setMessage("成功");
        } catch (RequestParameterErrorException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (DeptNotExistException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
        } catch (ServiceException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/modifyDept")
    public ResponseResult modifyDept(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        Integer id = Integer.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        try {
            deptService.modifyDept(id, name, remark);
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setMessage("成功");
        } catch (RequestParameterErrorException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (DeptNotExistException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
        } catch (ServiceException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/enableDept")
    public ResponseResult enableDept(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        String id = request.getParameter("id");

        try {
            deptService.enableDept(id);
            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
        } catch (RequestParameterErrorException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (DeptStatusException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
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
    @RequestMapping("/disableDept")
    public ResponseResult disableDept(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        String id = request.getParameter("id");

        try {
            deptService.disableDept(id);
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
