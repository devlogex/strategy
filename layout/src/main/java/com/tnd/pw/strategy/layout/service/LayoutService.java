package com.tnd.pw.strategy.layout.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;

import java.io.IOException;
import java.util.List;

public interface LayoutService {
    Layout create(Long parentId, String type, String layout) throws IOException, DBServiceException;
    Layout update(Layout entity) throws IOException, DBServiceException;
    Layout get(Long parentId, String type) throws IOException, DBServiceException, LayoutNotFoundException;
}
