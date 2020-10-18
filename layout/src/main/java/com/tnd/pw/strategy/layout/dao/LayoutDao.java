package com.tnd.pw.strategy.layout.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;

import java.io.IOException;
import java.util.List;

public interface LayoutDao {
    void create(Layout entity) throws DBServiceException;
    void update(Layout entity) throws DBServiceException;
    Layout get(Layout entity) throws DBServiceException, LayoutNotFoundException;
    void remove(Layout entity) throws DBServiceException;
}
