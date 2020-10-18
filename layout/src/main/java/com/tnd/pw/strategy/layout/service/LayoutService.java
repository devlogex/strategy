package com.tnd.pw.strategy.layout.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;

import java.io.IOException;
import java.util.List;

public interface LayoutService {
    Layout create(Long parentId, String type, String layout) throws DBServiceException;
    Layout update(Layout entity) throws DBServiceException;
    Layout get(Long parentId, String type) throws DBServiceException, LayoutNotFoundException;
    void remove(Long id, String type) throws DBServiceException;
}
