package com.tnd.pw.strategy.layout.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.layout.dao.LayoutDao;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.layout.service.LayoutService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class LayoutServiceImpl implements LayoutService {
    @Autowired
    private LayoutDao layoutDao;

    @Override
    public Layout create(Long parentId, String type, String layout) throws DBServiceException {
        Layout entity = new Layout();
        entity.setId(GenUID.genIdByParent(parentId));
        entity.setParentId(parentId);
        entity.setLayout(layout);
        entity.setType(type);
        layoutDao.create(entity);
        return entity;
    }

    @Override
    public Layout update(Layout entity) throws DBServiceException {
        layoutDao.update(entity);
        return entity;
    }

    @Override
    public Layout get(Long parentId, String type) throws DBServiceException, LayoutNotFoundException {
        Layout entity = new Layout();
        entity.setParentId(parentId);
        entity.setType(type);
        return layoutDao.get(entity);
    }

    @Override
    public void remove(Long parentId, String type) throws DBServiceException {
        Layout entity = new Layout();
        entity.setParentId(parentId);
        entity.setType(type);
        layoutDao.remove(entity);
    }
}
