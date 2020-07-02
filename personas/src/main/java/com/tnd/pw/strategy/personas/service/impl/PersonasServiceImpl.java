package com.tnd.pw.strategy.personas.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.dbservice.GsonUtils;
import com.tnd.pw.strategy.personas.dao.PersonasDao;
import com.tnd.pw.strategy.personas.entity.Personas;
import com.tnd.pw.strategy.personas.exception.PersonasNotFoundException;
import com.tnd.pw.strategy.personas.service.PersonasService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PersonasServiceImpl implements PersonasService {
    @Autowired
    private PersonasDao personasDao;

    @Override
    public Personas create(Long productId) throws IOException, DBServiceException {
        Personas entity = new Personas();
        entity.setId(System.currentTimeMillis());
        entity.setProductId(productId);
        entity.setName("Name");
        entity.setImage("");
        entity.setColor("#D50000");
        entity.setContent(defaultContent());
        personasDao.create(entity);
        return entity;
    }

    private String defaultContent() {
        HashMap<String, HashMap<String,String>> content = new HashMap<>();

        content.put("Education", new HashMap<>());
        content.get("Education").put("content","");
        content.get("Education").put("files","");

        content.put("Education", new HashMap<>());
        content.get("Education").put("content","");
        content.get("Education").put("files","");

        content.put("Experience", new HashMap<>());
        content.get("Experience").put("content","");
        content.get("Experience").put("files","");

        content.put("Influence", new HashMap<>());
        content.get("Influence").put("content","");
        content.get("Influence").put("files","");

        content.put("Product_knowledge", new HashMap<>());
        content.get("Product_knowledge").put("content","");
        content.get("Product_knowledge").put("files","");

        content.put("Likes", new HashMap<>());
        content.get("Likes").put("content","");
        content.get("Likes").put("files","");

        content.put("Dislikes", new HashMap<>());
        content.get("Dislikes").put("content","");
        content.get("Dislikes").put("files","");

        content.put("Description", new HashMap<>());
        content.get("Description").put("content","");
        content.get("Description").put("files","");

        content.put("Responsibilities", new HashMap<>());
        content.get("Responsibilities").put("content","");
        content.get("Responsibilities").put("files","");

        content.put("Goals", new HashMap<>());
        content.get("Goals").put("content","");
        content.get("Goals").put("files","");

        content.put("Challenges", new HashMap<>());
        content.get("Challenges").put("content","");
        content.get("Challenges").put("files","");

        content.put("Skills", new HashMap<>());
        content.get("Skills").put("content","");
        content.get("Skills").put("files","");

        content.put("Trusts_information_from", new HashMap<>());
        content.get("Trusts_information_from").put("content","");
        content.get("Trusts_information_from").put("files","");

        return GsonUtils.convertToString(content);
    }

    @Override
    public void update(Personas entity) throws IOException, DBServiceException {
        personasDao.update(entity);
    }

    @Override
    public List<Personas> get(Personas entity) throws IOException, DBServiceException, PersonasNotFoundException {
        return personasDao.get(entity);
    }

    @Override
    public void remove(Long personasId) throws IOException, DBServiceException {
        personasDao.remove(personasId);
    }
}
