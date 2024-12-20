package com.itany.nmms.service;

import com.github.pagehelper.PageInfo;
import com.itany.nmms.entity.ProductType;
import com.itany.nmms.exception.ProductTypeNotExistException;
import com.itany.nmms.exception.ProductTypeNotExistEception;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;

import java.util.List;

public interface ProductTypeService {

    void addType(String name) throws RequestParameterErrorException, ProductTypeNotExistException, ServiceException;

    void modifyType(Integer id,String name) throws RequestParameterErrorException, ProductTypeNotExistException,ServiceException;

    PageInfo<ProductType> findPage(String pageNo,String pageSize) throws ServiceException;

    void modifyStatusType1(Integer id) throws ServiceException;

    void modifyStatusType0(Integer id) throws ServiceException;

    List<ProductType> findAll() throws ProductTypeNotExistEception,ServiceException;
}
