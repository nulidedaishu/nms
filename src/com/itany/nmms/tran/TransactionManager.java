package com.itany.nmms.tran;

import com.itany.nmms.exception.ServiceException;

public interface TransactionManager {
    void begin();

    void commit() throws ServiceException;

    void rollback() throws ServiceException;
}
