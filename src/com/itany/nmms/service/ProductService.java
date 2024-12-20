package com.itany.nmms.service;

import com.github.pagehelper.PageInfo;
import com.itany.mvc.util.CommonsMultipartFile;
import com.itany.nmms.entity.Product;
import com.itany.nmms.exception.ProductNotExistException;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ProductService {

    PageInfo<Product> findPage(String pageNo, String pageSize) throws ServiceException;

    void addProduct(String name, String price, List<CommonsMultipartFile> files, String typeId, HttpSession session) throws RequestParameterErrorException, ProductNotExistException, FileUploadException, ServiceException;

    void modifyProduct(String productNo,String name, String price, List<CommonsMultipartFile> files, String typeId, HttpSession session) throws RequestParameterErrorException, ProductNotExistException, FileUploadException, ServiceException;

    void deleteProduct(String productNo)throws RequestParameterErrorException, ProductNotExistException, ServiceException;
}
