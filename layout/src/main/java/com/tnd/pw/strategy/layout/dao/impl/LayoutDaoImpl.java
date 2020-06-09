package com.tnd.pw.strategy.layout.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.layout.dao.LayoutDao;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class LayoutDaoImpl implements LayoutDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO layout(id, parent_id, type, layout) " +
                    "values(%d, %d, '%s', '%s')";
    private static final String SQL_UPDATE =
            "UPDATE layout SET layout = '%s' WHERE id = %d";
    private static final String SQL_SELECT =
            "SELECT * FROM layout where parent_id = %d AND type = '%s'";
    private static final String SQL_DELETE =
            "DELETE FROM layout WHERE parent_id = %d AND type = '%s'";

    @Override
    public void create(Layout entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getParentId(), entity.getType(), entity.getLayout());
        dataHelper.executeSQL(query);
    }

    @Override
    public void update(Layout entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getLayout(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public Layout get(Layout entity) throws IOException, DBServiceException, LayoutNotFoundException {
        String query = String.format(SQL_SELECT, entity.getParentId(), entity.getType());
        List<Layout> entities = dataHelper.querySQL(query, Layout.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new LayoutNotFoundException();
        }
        return entities.get(0);
    }

    @Override
    public void remove(Layout entity) throws IOException, DBServiceException {
        String query = String.format(SQL_DELETE, entity.getParentId(), entity.getType());
        dataHelper.executeSQL(query);
    }
}
