package com.tnd.pw.strategy.competitor.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.competitor.dao.CompetitorDao;
import com.tnd.pw.strategy.competitor.entity.Competitor;
import com.tnd.pw.strategy.competitor.exception.CompetitorNotFoundException;
import com.tnd.pw.strategy.dbservice.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class CompetitorDaoImpl implements CompetitorDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO competitor(id, product_id, name, image, color, score, url, content) " +
                    "values(%d, %d, '%s', '%s', '%s', '%s', '%s', '%s')";
    private static final String SQL_UPDATE =
            "UPDATE competitor SET name = '%s', image = '%s', color = '%s', score = '%s'" +
                    ", url = '%s', content = '%s' WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM competitor WHERE id = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM competitor WHERE product_id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM competitor WHERE id = %d";
    @Override
    public void create(Competitor entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(),
                entity.getName(), entity.getImage(), entity.getColor(),
                entity.getScore(), entity.getUrl(), entity.getContent());
        dataHelper.executeSQL(query);
    }

    @Override
    public void update(Competitor entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(),entity.getImage(),
                entity.getColor(), entity.getScore(), entity.getUrl(),
                entity.getContent(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<Competitor> get(Competitor entity) throws DBServiceException, CompetitorNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID, entity.getProductId());
        }
        List<Competitor> entities = dataHelper.querySQL(query, Competitor.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new CompetitorNotFoundException();
        }
        return entities;
    }

    @Override
    public void remove(Long competitorId) throws DBServiceException {
        String query = String.format(SQL_DELETE, competitorId);
        dataHelper.executeSQL(query);
    }
}
