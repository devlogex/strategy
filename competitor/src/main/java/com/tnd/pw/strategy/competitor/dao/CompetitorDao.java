package com.tnd.pw.strategy.competitor.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.competitor.entity.Competitor;
import com.tnd.pw.strategy.competitor.exception.CompetitorNotFoundException;

import java.io.IOException;
import java.util.List;

public interface CompetitorDao {
    void create(Competitor entity) throws DBServiceException;
    void update(Competitor entity) throws DBServiceException;
    List<Competitor> get(Competitor entity) throws DBServiceException, CompetitorNotFoundException;
    void remove(Long competitorId) throws DBServiceException;
}