package com.itany.nmms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itany.nmms.constant.DictConstant;
import com.itany.nmms.constant.StatusConstant;
import com.itany.nmms.dao.ProductTypeMapper;
import com.itany.nmms.entity.ProductType;
import com.itany.nmms.entity.ProductTypeExample;
import com.itany.nmms.exception.ProductTypeNotExistException;
import com.itany.nmms.exception.ProductTypeNotExistEception;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.ProductTypeService;
import com.itany.nmms.util.ParameterUtil;

import java.util.List;

public class ProductTypeServiceImpl implements ProductTypeService {
    @Override
    public void addType(String name) throws RequestParameterErrorException, ProductTypeNotExistException {
        ProductTypeMapper typeMapper = (ProductTypeMapper) ObjectFactory.getObject("typeMapper");
        if (ParameterUtil.isNull(name)) {
            throw new RequestParameterErrorException("请求参数有误");
        }

        ProductTypeExample example = new ProductTypeExample();
        example.or()
                .andNameEqualTo(name);
        List<ProductType> types = typeMapper.selectByExample(example);
        if (!types.isEmpty()) {
            throw new ProductTypeNotExistException("该类型已经存在");
        }

        ProductType type = new ProductType();
        type.setName(name);

        type.setStatus(StatusConstant.PRODUCT_TYPE_STATUS_ENABLED);
        typeMapper.insertSelective(type);
    }

    @Override
    public void modifyType(Integer id, String name) throws RequestParameterErrorException, ProductTypeNotExistException {
        ProductTypeMapper typeMapper = (ProductTypeMapper) ObjectFactory.getObject("typeMapper");

        if (ParameterUtil.isNull(name)) {
            throw new RequestParameterErrorException("请求参数有误");
        }

        ProductTypeExample example = new ProductTypeExample();
        example.or()
                .andNameEqualTo(name);
        List<ProductType> types = typeMapper.selectByExample(example);
//        if (!types.isEmpty()) {
//            if (!types.get(0).getName().equals(name)) {
//                throw new ProductTypeExistException("该类型已经存在");
//            }
//        }

        if (!types.isEmpty()) {
            ProductType existingType = types.get(0);
            if (name.equalsIgnoreCase(existingType.getName())) {
                // 如果修改前后名称相同，则不报异常，直接返回
                return;
            }
            throw new ProductTypeNotExistException("该类型已经存在");
        }

        ProductType type = typeMapper.selectByPrimaryKey(id);

        type.setName(name);
        typeMapper.updateByPrimaryKey(type);
    }

    @Override
    public PageInfo<ProductType> findPage(String pageNo, String pageSize) throws ServiceException {
        ProductTypeMapper typeMapper = (ProductTypeMapper) ObjectFactory.getObject("typeMapper");

        if (ParameterUtil.isNull(pageNo)) {
            pageNo = DictConstant.PRODUCT_TYPE_DEFAULT_PAGE_NO;
        }
        if (ParameterUtil.isNull(pageSize)) {
            pageSize = DictConstant.PRODUCT_TYPE_DEFAULT_PAGE_SIZE;
        }

        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        List<ProductType> types = typeMapper.selectByExample(new ProductTypeExample());

        PageInfo<ProductType> typePage = new PageInfo<>(types);

        return typePage;
    }

    @Override
    public void modifyStatusType1(Integer id) {
        ProductTypeMapper typeMapper = (ProductTypeMapper) ObjectFactory.getObject("typeMapper");

        ProductType type = typeMapper.selectByPrimaryKey(id);

        type.setStatus(StatusConstant.PRODUCT_TYPE_STATUS_ENABLED);
        typeMapper.updateByPrimaryKey(type);
    }

    @Override
    public void modifyStatusType0(Integer id) {
        ProductTypeMapper typeMapper = (ProductTypeMapper) ObjectFactory.getObject("typeMapper");

        ProductType type = typeMapper.selectByPrimaryKey(id);

        type.setStatus(StatusConstant.PRODUCT_TYPE_STATUS_DISABLED);
        typeMapper.updateByPrimaryKey(type);
    }

    @Override
    public List<ProductType> findAll() throws ProductTypeNotExistEception, ServiceException {
        ProductTypeMapper typeMapper = (ProductTypeMapper) ObjectFactory.getObject("typeMapper");
        List<ProductType> types = typeMapper.selectByExample(new ProductTypeExample());
        if (types.isEmpty()) {
            throw new ProductTypeNotExistEception("请先添加商品类型");
        }
        return types;
    }
}
