package com.tnd.pw.strategy.personas.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.personas.dao.PersonasDao;
import com.tnd.pw.strategy.personas.entity.Personas;
import com.tnd.pw.strategy.personas.exception.PersonasNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class PersonasDaoImpl implements PersonasDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO personas(id, product_id, name, image, color, content) " +
                    "values(%d, %d, '%s', '%s', '%s', '%s')";
    private static final String SQL_UPDATE =
            "UPDATE personas SET name = '%s', image = '%s', color = '%s', content = '%s' WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM personas WHERE id = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM personas WHERE product_id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM personas WHERE id = %d";
    @Override
    public void create(Personas entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(), entity.getName(),
                entity.getImage(), entity.getColor(), entity.getContent());
        dataHelper.executeSQL(query);
    }

    @Override
    public void update(Personas entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(),
                entity.getImage(), entity.getColor(), entity.getContent(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<Personas> get(Personas entity) throws IOException, DBServiceException, PersonasNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID, entity.getProductId());
        }
        List<Personas> entities = dataHelper.querySQL(query, Personas.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new PersonasNotFoundException();
        }
        return entities;
    }

    @Override
    public void remove(Long personasId) throws IOException, DBServiceException {
        String query = String.format(SQL_DELETE, personasId);
        dataHelper.executeSQL(query);
    }
}
