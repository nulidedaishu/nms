package com.itany.nmms.tran.impl;

import com.itany.nmms.exception.ServiceException;
import com.itany.nmms.tran.TransactionManager;
import com.itany.nmms.util.MyBatisUtil;

public class TransactionManagerImpl implements TransactionManager {
    @Override
    public void begin() {
        MyBatisUtil.getSession();
    }

    @Override
    public void commit() throws ServiceException {
        try {
            MyBatisUtil.getSession().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("事务提交出错");
        } finally {
            MyBatisUtil.close();
        }
    }

    @Override
    public void rollback() throws ServiceException {
        try {
            MyBatisUtil.getSession().rollback();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("事务回滚出错");
        } finally {
            MyBatisUtil.close();
        }
    }
}
