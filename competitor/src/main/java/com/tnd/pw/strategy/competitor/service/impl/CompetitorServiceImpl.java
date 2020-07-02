package com.tnd.pw.strategy.competitor.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.competitor.dao.CompetitorDao;
import com.tnd.pw.strategy.competitor.entity.Competitor;
import com.tnd.pw.strategy.competitor.exception.CompetitorNotFoundException;
import com.tnd.pw.strategy.competitor.service.CompetitorService;
import com.tnd.pw.strategy.dbservice.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CompetitorServiceImpl implements CompetitorService {
    @Autowired
    private CompetitorDao competitorDao;

    @Override
    public Competitor create(Long productId) throws IOException, DBServiceException {
        Competitor entity = new Competitor();
        entity.setId(System.currentTimeMillis());
        entity.setProductId(productId);
        entity.setName("Name");
        entity.setImage("");
        entity.setColor("#D50000");
        entity.setUrl("");
        entity.setScore(defaultScore());
        entity.setContent(defaultContent());
        competitorDao.create(entity);
        return entity;
    }

    private String defaultScore() {
        HashMap<String,Integer> score = new HashMap<>();
        score.put("Product_breadth", 0);
        score.put("Ability_to_execute", 0);
        score.put("Strength_of_vision", 0);
        score.put("Market_awareness", 0);
        return GsonUtils.convertToString(score);
    }

    private String defaultContent() {
        HashMap<String, HashMap<String,String>> content = new HashMap<>();

        content.put("Revenue", new HashMap<>());
        content.get("Revenue").put("content","");
        content.get("Revenue").put("files","");

        content.put("Employees", new HashMap<>());
        content.get("Employees").put("content","");
        content.get("Employees").put("files","");

        content.put("Growth_Rate", new HashMap<>());
        content.get("Growth_Rate").put("content","");
        content.get("Growth_Rate").put("files","");

        content.put("Customers", new HashMap<>());
        content.get("Customers").put("content","");
        content.get("Customers").put("files","");

        content.put("Pricing", new HashMap<>());
        content.get("Pricing").put("content","");
        content.get("Pricing").put("files","");

        content.put("Mission", new HashMap<>());
        content.get("Mission").put("content","");
        content.get("Mission").put("files","");

        content.put("Description", new HashMap<>());
        content.get("Description").put("content","");
        content.get("Description").put("files","");

        content.put("Products", new HashMap<>());
        content.get("Products").put("content","");
        content.get("Products").put("files","");

        content.put("Strengths", new HashMap<>());
        content.get("Strengths").put("content","");
        content.get("Strengths").put("files","");

        content.put("Weaknesses", new HashMap<>());
        content.get("Weaknesses").put("content","");
        content.get("Weaknesses").put("files","");

        content.put("Challenges", new HashMap<>());
        content.get("Challenges").put("content","");
        content.get("Challenges").put("files","");

        content.put("Differentiators", new HashMap<>());
        content.get("Differentiators").put("content","");
        content.get("Differentiators").put("files","");

        content.put("Recent_News", new HashMap<>());
        content.get("Recent_News").put("content","");
        content.get("Recent_News").put("files","");

        return GsonUtils.convertToString(content);
    }

    @Override
    public void update(Competitor entity) throws IOException, DBServiceException {
        competitorDao.update(entity);
    }

    @Override
    public List<Competitor> get(Competitor entity) throws IOException, DBServiceException, CompetitorNotFoundException {
        return competitorDao.get(entity);
    }

    @Override
    public void remove(Long personasId) throws IOException, DBServiceException {
        competitorDao.remove(personasId);
    }
}
