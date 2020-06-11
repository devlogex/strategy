package com.tnd.pw.strategy.personas.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.personas.entity.Personas;
import com.tnd.pw.strategy.personas.exception.PersonasNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PersonasDao {
    void create(Personas entity) throws IOException, DBServiceException;
    void update(Personas entity) throws IOException, DBServiceException;
    List<Personas> get(Personas entity) throws IOException, DBServiceException, PersonasNotFoundException;
    void remove(Long personasId) throws IOException, DBServiceException;
}
