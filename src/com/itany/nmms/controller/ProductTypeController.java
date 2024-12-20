package com.itany.nmms.controller;

import com.github.pagehelper.PageInfo;
import com.itany.mvc.annotation.RequestMapping;
import com.itany.mvc.annotation.ResponseBody;
import com.itany.nmms.constant.ResponseCodeConstant;
import com.itany.nmms.entity.ProductType;
import com.itany.nmms.exception.ProductTypeNotExistException;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.ProductTypeService;
import com.itany.nmms.util.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/type")
public class ProductTypeController {

    private ProductTypeService typeService= (ProductTypeService) ObjectFactory.getObject("typeService");

    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request, HttpServletResponse response){
        String pageNo=request.getParameter("pageNo");
        String pageSize=request.getParameter("pageSize");
        try {
            PageInfo<ProductType> typePage=typeService.findPage(pageNo,pageSize);
            request.setAttribute("typePage",typePage);
        } catch (ServiceException e) {
            request.setAttribute("typeMsg",e.getMessage());
        }catch (Exception e){
            request.setAttribute("typeMsg","服务器内部异常");
        }
        return "backend/productTypeManager";
    }

    @ResponseBody
    @RequestMapping("/addType")
    public ResponseResult addType(HttpServletRequest request,HttpServletResponse response){
        ResponseResult result=new ResponseResult();

        String name=request.getParameter("name");
        try {
            typeService.addType(name);
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setMessage("成功");
        } catch (RequestParameterErrorException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
            result.setMessage(e.getMessage());
        } catch (ProductTypeNotExistException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
        } catch (ServiceException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
        }catch (Exception e){
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage("服务器内部异常");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/modifyType")
    public ResponseResult modifyType(HttpServletRequest request,HttpServletResponse response){
        ResponseResult result=new ResponseResult();
        String name=request.getParameter("name");
        Integer id = Integer.parseInt(request.getParameter("id"));
        try {
            typeService.modifyType(id, name);
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setMessage("更新成功");
        } catch (NumberFormatException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
            result.setMessage(e.getMessage());
        } catch (ServiceException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
        } catch (Exception e){
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/modifyStatusType1")
    public ResponseResult modifyStatusType1(HttpServletRequest request,HttpServletResponse response){
        ResponseResult result=new ResponseResult();
        Integer id=Integer.parseInt(request.getParameter("id"));
        try {
            typeService.modifyStatusType1(id);
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setMessage("更新成功");
        } catch (ServiceException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/modifyStatusType0")
    public ResponseResult modifyStatusType0(HttpServletRequest request,HttpServletResponse response){
        ResponseResult result=new ResponseResult();
        Integer id=Integer.parseInt(request.getParameter("id"));
        try {
            typeService.modifyStatusType0(id);
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setMessage("更新成功");
        } catch (ServiceException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
