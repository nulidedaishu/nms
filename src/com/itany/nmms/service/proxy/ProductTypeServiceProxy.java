package com.itany.nmms.service.proxy;

import com.github.pagehelper.PageInfo;
import com.itany.nmms.entity.ProductType;
import com.itany.nmms.exception.ProductTypeNotExistException;
import com.itany.nmms.exception.ProductTypeNotExistEception;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.ProductTypeService;
import com.itany.nmms.tran.TransactionManager;

import java.util.List;

public class ProductTypeServiceProxy implements ProductTypeService {
    @Override
    public void addType(String name) throws RequestParameterErrorException, ProductTypeNotExistException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        ProductTypeService typeService = (ProductTypeService) ObjectFactory.getObject("typeServiceTarget");
        try {
            tran.begin();
            typeService.addType(name);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            tran.rollback();
            throw e;
        } catch (ProductTypeNotExistException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void modifyType(Integer id, String name) throws RequestParameterErrorException, ProductTypeNotExistException, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        ProductTypeService typeService = (ProductTypeService) ObjectFactory.getObject("typeServiceTarget");
        try {
            tran.begin();
            typeService.modifyType(id, name);
            tran.commit();
        } catch (RequestParameterErrorException e) {
            tran.rollback();
            throw e;
        } catch (ProductTypeNotExistException e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }

    }

    @Override
    public PageInfo<ProductType> findPage(String pageNo, String pageSize) throws ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        ProductTypeService typeService = (ProductTypeService) ObjectFactory.getObject("typeServiceTarget");
        try {
            tran.begin();
            PageInfo<ProductType> typePage = typeService.findPage(pageNo, pageSize);
            tran.commit();
            return typePage;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void modifyStatusType1(Integer id) throws ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        ProductTypeService typeService = (ProductTypeService) ObjectFactory.getObject("typeServiceTarget");
        try {
            tran.begin();
            typeService.modifyStatusType1(id);
            tran.commit();
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public void modifyStatusType0(Integer id) throws ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        ProductTypeService typeService = (ProductTypeService) ObjectFactory.getObject("typeServiceTarget");
        try {
            tran.begin();
            typeService.modifyStatusType0(id);
            tran.commit();
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }

    @Override
    public List<ProductType> findAll() throws ProductTypeNotExistEception, ServiceException {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("tran");
        ProductTypeService typeService = (ProductTypeService) ObjectFactory.getObject("typeServiceTarget");
        try {
            tran.begin();
            List<ProductType> types = typeService.findAll();
            tran.commit();
            return types;
        } catch (ProductTypeNotExistEception e) {
            tran.rollback();
            throw e;
        } catch (ServiceException e) {
            tran.rollback();
            throw e;
        }
    }
}

