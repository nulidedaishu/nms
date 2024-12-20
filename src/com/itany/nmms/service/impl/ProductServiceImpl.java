package com.itany.nmms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itany.mvc.util.CommonsMultipartFile;
import com.itany.nmms.constant.DictConstant;
import com.itany.nmms.dao.ProductMapper;
import com.itany.nmms.dao.SequenceMapper;
import com.itany.nmms.entity.*;
import com.itany.nmms.exception.ProductNotExistException;
import com.itany.nmms.exception.RequestParameterErrorException;
import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.factory.ObjectFactory;
import com.itany.nmms.service.ProductService;
import com.itany.nmms.util.ParameterUtil;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    public PageInfo<Product> findPage(String pageNo, String pageSize) throws ServiceException {
        ProductMapper productMapper = (ProductMapper) ObjectFactory.getObject("productMapper");

        if (ParameterUtil.isNull(pageNo)) {
            pageNo = DictConstant.PRODUCT_DEFAULT_PAGE_NO;
        }
        if (ParameterUtil.isNull(pageSize)) {
            pageSize = DictConstant.PRODUCT_DEFAULT_PAGE_SIZE;
        }

        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        List<Product> products = productMapper.selectProductsWithType(new ProductExample());

        PageInfo<Product> productPage = new PageInfo<>(products);

        return productPage;
    }

    @Override
    public void addProduct(String name, String price, List<CommonsMultipartFile> files, String typeId, HttpSession session) throws RequestParameterErrorException, ProductNotExistException, FileUploadException, ServiceException {
        ProductMapper productMapper = (ProductMapper) ObjectFactory.getObject("productMapper");
        SequenceMapper sequenceMapper = (SequenceMapper) ObjectFactory.getObject("sequenceMapper");
        if (ParameterUtil.isNull(name) || ParameterUtil.isNull(String.valueOf(price)) || files.isEmpty() || ParameterUtil.isNull(typeId)) {
            throw new RequestParameterErrorException("请求参数有误");
        }

        CommonsMultipartFile file = files.get(0);
        if (file.isEmpty()) {
            throw new RequestParameterErrorException("上传的商品图片不能为空");
        }

        ProductExample example = new ProductExample();
        example.or()
                .andNameEqualTo(name)
                .andProductTypeIdEqualTo(Integer.valueOf(typeId));
        List<Product> products = productMapper.selectByExample(example);
        if (!products.isEmpty()) {
            throw new ProductNotExistException("该类型的商品已经存在了");
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(Double.valueOf(price));
        product.setProductTypeId(Integer.valueOf(typeId));

        SequenceExample sequenceExample = new SequenceExample();
        sequenceExample.or()
                .andNameEqualTo(DictConstant.PRODUCT_NO_PREFIX);
        List<Sequence> sequences = sequenceMapper.selectByExample(sequenceExample);
        if (sequences.isEmpty()) {
            Sequence sequence = new Sequence();
            sequence.setName(DictConstant.PRODUCT_NO_PREFIX);
            sequence.setValue(DictConstant.PRODUCT_NO_INIT_VALUE);
            sequenceMapper.insertSelective(sequence);
            product.setProductNo(DictConstant.PRODUCT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequence.getValue());
        } else {
            Sequence sequence = sequences.get(0);
            if (DictConstant.PRODUCT_NO_MAX_VALUE.equals(sequence.getValue())) {
                sequence.setValue(DictConstant.PRODUCT_NO_INIT_VALUE);
            } else {
                sequence.setValue(ParameterUtil.nextValue(sequence.getValue()));
            }
            sequenceMapper.updateByPrimaryKeySelective(sequence);
            product.setProductNo(DictConstant.PRODUCT_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequence.getValue());
        }

        String path = DictConstant.PRODUCT_IMAGE_ROOT_FOLDER + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String cp = session.getServletContext().getRealPath(path);
        File f = new File(cp);
        f.mkdirs();

        String fileName = file.getOriginalFilename();
        fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "-" + new Date().getTime() + fileName.substring(fileName.lastIndexOf("."));

        productMapper.insertSelective(product);
        try {
            file.transferTo(new File(cp, fileName));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException("上传商品图片失败");
        }
    }

    @Override
    public void modifyProduct(String productNo, String name, String price, List<CommonsMultipartFile> files, String typeId, HttpSession session) throws RequestParameterErrorException, ProductNotExistException, FileUploadException, ServiceException {
        ProductMapper productMapper = (ProductMapper) ObjectFactory.getObject("productMapper");

        if (ParameterUtil.isNull(name) || ParameterUtil.isNull(String.valueOf(price)) || files.isEmpty() || ParameterUtil.isNull(typeId)) {
            throw new RequestParameterErrorException("请求参数有误");
        }

        CommonsMultipartFile file = files.get(0);
        if (file.isEmpty()) {
            throw new RequestParameterErrorException("上传的商品图片不能为空");
        }

        ProductExample example = new ProductExample();
        example.or()
                .andNameEqualTo(name)
                .andProductTypeIdEqualTo(Integer.valueOf(typeId));
        List<Product> products = productMapper.selectByExample(example);
        if (!products.isEmpty()) {
            throw new ProductNotExistException("该类型的商品已经存在了");
        }

        Product product = productMapper.selectByNo(productNo);

        product.setName(name);
        product.setPrice(Double.valueOf(price));
        product.setProductTypeId(Integer.valueOf(typeId));


        String path = DictConstant.PRODUCT_IMAGE_ROOT_FOLDER + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String cp = session.getServletContext().getRealPath(path);
        File f = new File(cp);
        f.mkdirs();

        String fileName = file.getOriginalFilename();
        fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "-" + new Date().getTime() + fileName.substring(fileName.lastIndexOf("."));

        productMapper.updateByPrimaryKey(product);
        try {
            file.transferTo(new File(cp, fileName));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException("上传商品图片失败");
        }
    }

    @Override
    public void deleteProduct(String productNo) throws RequestParameterErrorException, ProductNotExistException, ServiceException {
        ProductMapper productMapper = (ProductMapper) ObjectFactory.getObject("productMapper");
        productMapper.deleteByNo(productNo);
    }
}

