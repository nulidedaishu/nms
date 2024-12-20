package com.itany.nmms.controller;

import com.github.pagehelper.PageInfo;
import com.itany.mvc.annotation.RequestMapping;
import com.itany.mvc.annotation.ResponseBody;
import com.itany.mvc.util.CommonsMultipartFile;
import com.itany.nmms.constant.ResponseCodeConstant;
import com.itany.nmms.entity.Product;
import com.itany.nmms.entity.ProductType;
import com.itany.nmms.exception.ProductNotExistException;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.ProductService;
import com.itany.nmms.service.ProductTypeService;
import com.itany.nmms.util.ResponseResult;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/product")
public class ProductController {

    private ProductService productService = (ProductService) ObjectFactory.getObject("productService");
    private ProductTypeService typeService = (ProductTypeService) ObjectFactory.getObject("typeService");

    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        try {
            List<ProductType> types = typeService.findAll();
            request.setAttribute("types", types);
            PageInfo<Product> productPage = productService.findPage(pageNo, pageSize);
            request.setAttribute("productPage", productPage);
        } catch (ServiceException e) {
            request.setAttribute("productMsg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("productMsg", "服务器内部异常");
        }
        return "backend/ProductManager";
    }

    @ResponseBody
    @RequestMapping("/addProduct")
    public ResponseResult addProduct(HttpServletRequest request, HttpServletResponse response, List<CommonsMultipartFile> files) {
        ResponseResult result = new ResponseResult();
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String typeId = request.getParameter("typeId");
        try {
            productService.addProduct(name, price, files, typeId, request.getSession());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setMessage("成功");
        } catch (RequestParameterErrorException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (ProductNotExistException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
        } catch (FileUploadException e) {
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
    @RequestMapping("/modifyProduct")
    public ResponseResult modifyProduct(HttpServletRequest request, HttpServletResponse response, List<CommonsMultipartFile> files) {
        ResponseResult result = new ResponseResult();
        String productNo = request.getParameter("no");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String typeId = request.getParameter("typeId");

        try {
            productService.modifyProduct(productNo, name, price, files, typeId, request.getSession());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setMessage("成功");
        } catch (RequestParameterErrorException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (ProductNotExistException e) {
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
            result.setMessage(e.getMessage());
        } catch (FileUploadException e) {
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
    @RequestMapping("/deleteProduct")
    public ResponseResult deleteProduct(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        String productNo = request.getParameter("productNo");
        System.out.println(productNo+"productNo");
        try {
            productService.deleteProduct(productNo);
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setMessage("成功");
        } catch (RequestParameterErrorException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (ProductNotExistException e) {
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
}
