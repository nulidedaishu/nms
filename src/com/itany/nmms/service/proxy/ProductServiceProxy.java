package com.itany.nmms.service.proxy;

import com.github.pagehelper.PageInfo;
import com.itany.mvc.util.CommonsMultipartFile;
import com.itany.nmms.entity.Product;
import com.itany.nmms.exception.ProductNotExistException;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.ProductService;
import com.itany.nmms.tran.TransactionManager;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ProductServiceProxy implements ProductService {
    @Override
    public PageInfo<Product> findPage(String pageNo, String pageSize) throws ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        ProductService productService = (ProductService) ObjectFactory.getObject("productServiceTarget");
        try {
            tran.begin();
            PageInfo<Product> productPage = productService.findPage(pageNo, pageSize);
            tran.commit();
            return productPage;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void addProduct(String name, String price, List<CommonsMultipartFile> files, String typeId, HttpSession session) throws RequestParameterErrorException, ProductNotExistException, FileUploadException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        ProductService productService = (ProductService) ObjectFactory.getObject("productServiceTarget");
        try {
            tran.begin();
            productService.addProduct(name, price, files, typeId, session);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            tran.rollback();
            throw e;
        } catch (ProductNotExistException e) {
            tran.rollback();
            throw e;
        } catch (FileUploadException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void modifyProduct(String productNo, String name, String price, List<CommonsMultipartFile> files, String typeId, HttpSession session) throws RequestParameterErrorException, ProductNotExistException, FileUploadException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        ProductService productService = (ProductService) ObjectFactory.getObject("productServiceTarget");
        try {
            tran.begin();
            productService.modifyProduct(productNo, name, price, files, typeId, session);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            tran.rollback();
            throw e;
        } catch (ProductNotExistException e) {
            tran.rollback();
            throw e;
        } catch (FileUploadException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void deleteProduct(String productNo) throws RequestParameterErrorException, ProductNotExistException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        ProductService productService = (ProductService) ObjectFactory.getObject("productServiceTarget");
        try {
            tran.begin();
            productService.deleteProduct(productNo);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            tran.rollback();
            throw e;
        } catch (ProductNotExistException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }
}


