package com.tnd.pw.strategy.runner.config;

import com.tnd.pw.strategy.competitor.dao.CompetitorDao;
import com.tnd.pw.strategy.competitor.dao.impl.CompetitorDaoImpl;
import com.tnd.pw.strategy.competitor.service.CompetitorService;
import com.tnd.pw.strategy.competitor.service.impl.CompetitorServiceImpl;
import com.tnd.pw.strategy.dbservice.DBServiceApiClient;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.goal.dao.GoalDao;
import com.tnd.pw.strategy.goal.dao.impl.GoalDaoImpl;
import com.tnd.pw.strategy.goal.service.GoalService;
import com.tnd.pw.strategy.goal.service.impl.GoalServiceImpl;
import com.tnd.pw.strategy.initiative.dao.InitiativeDao;
import com.tnd.pw.strategy.initiative.dao.impl.InitiativeDaoImpl;
import com.tnd.pw.strategy.initiative.service.InitiativeService;
import com.tnd.pw.strategy.initiative.service.impl.InitiativeServiceImpl;
import com.tnd.pw.strategy.layout.dao.LayoutDao;
import com.tnd.pw.strategy.layout.dao.impl.LayoutDaoImpl;
import com.tnd.pw.strategy.layout.service.LayoutService;
import com.tnd.pw.strategy.layout.service.impl.LayoutServiceImpl;
import com.tnd.pw.strategy.model.dao.ModelComponentDao;
import com.tnd.pw.strategy.model.dao.ModelDao;
import com.tnd.pw.strategy.model.dao.impl.ModelComponentDaoImpl;
import com.tnd.pw.strategy.model.dao.impl.ModelDaoImpl;
import com.tnd.pw.strategy.model.service.ModelComponentService;
import com.tnd.pw.strategy.model.service.ModelService;
import com.tnd.pw.strategy.model.service.impl.ModelComponentServiceImpl;
import com.tnd.pw.strategy.model.service.impl.ModelServiceImpl;
import com.tnd.pw.strategy.personas.dao.PersonasDao;
import com.tnd.pw.strategy.personas.dao.impl.PersonasDaoImpl;
import com.tnd.pw.strategy.personas.service.PersonasService;
import com.tnd.pw.strategy.personas.service.impl.PersonasServiceImpl;
import com.tnd.pw.strategy.positioning.dao.PositionComponentDao;
import com.tnd.pw.strategy.positioning.dao.PositionDao;
import com.tnd.pw.strategy.positioning.dao.impl.PositionComponentDaoImpl;
import com.tnd.pw.strategy.positioning.dao.impl.PositionDaoImpl;
import com.tnd.pw.strategy.positioning.service.PositionComponentService;
import com.tnd.pw.strategy.positioning.service.PositionService;
import com.tnd.pw.strategy.positioning.service.impl.PositionComponentServiceImpl;
import com.tnd.pw.strategy.positioning.service.impl.PositionServiceImpl;
import com.tnd.pw.strategy.runner.handler.*;
import com.tnd.pw.strategy.runner.service.*;
import com.tnd.pw.strategy.runner.service.impl.*;
import com.tnd.pw.strategy.vision.dao.VisionComponentDao;
import com.tnd.pw.strategy.vision.dao.VisionDao;
import com.tnd.pw.strategy.vision.dao.impl.VisionComponentDaoImpl;
import com.tnd.pw.strategy.vision.dao.impl.VisionDaoImpl;
import com.tnd.pw.strategy.vision.service.VisionComponentService;
import com.tnd.pw.strategy.vision.service.VisionService;
import com.tnd.pw.strategy.vision.service.impl.VisionComponentServiceImpl;
import com.tnd.pw.strategy.vision.service.impl.VisionServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class RunnerConfig {
    @Value("${db.url}")
    private String db_url;

    @Bean
    public DBServiceApiClient dbServiceApiClient() {
        return new DBServiceApiClient();
    }

    @Bean
    public DataHelper dataHelper(DBServiceApiClient dbServiceApiClient) {
        return new DataHelper(db_url, dbServiceApiClient);
    }

    @Bean
    public VisionDao visionDao() {
        return new VisionDaoImpl();
    }

    @Bean
    public VisionComponentDao visionComponentDao() {
        return new VisionComponentDaoImpl();
    }

    @Bean
    public LayoutDao layoutDao() {
        return new LayoutDaoImpl();
    }

    @Bean
    public LayoutService layoutService() {
        return new LayoutServiceImpl();
    }

    @Bean
    public VisionService visionService() {
        return new VisionServiceImpl();
    }

    @Bean
    public VisionComponentService visionComponentService() {
        return new VisionComponentServiceImpl();
    }

    @Bean
    public VisionServiceHandlerImpl visionServiceHandler() {
        return new VisionServiceHandlerImpl();
    }

    @Bean
    public VisionHandler visionHandler() {
        return new VisionHandler();
    }

    @Bean
    public LayoutHandler layoutHandler() {
        return new LayoutHandler();
    }

    @Bean
    LayoutServiceHandler layoutServiceHandler() {
        return new LayoutServiceHandlerImpl();
    }

    @Bean
    public ModelDao modelDao(){
        return new ModelDaoImpl();
    }

    @Bean
    public ModelComponentDao modelComponentDao() {
        return new ModelComponentDaoImpl();
    }

    @Bean
    public ModelService modelService() {
        return new ModelServiceImpl();
    }

    @Bean
    public ModelComponentService modelComponentService() {
        return new ModelComponentServiceImpl();
    }

    @Bean
    public ModelServiceHandler modelServiceHandler() {
        return new ModelServiceHandlerImpl();
    }

    @Bean
    public ModelHandler modelHandler() {
        return new ModelHandler();
    }

    @Bean
    public PositionDao positionDao(){
        return new PositionDaoImpl();
    }

    @Bean
    public PositionComponentDao positionComponentDao() {
        return new PositionComponentDaoImpl();
    }

    @Bean
    public PositionService positionService() {
        return new PositionServiceImpl();
    }

    @Bean
    public PositionComponentService positionComponentService() {
        return new PositionComponentServiceImpl();
    }

    @Bean
    public PositionServiceHandler positionServiceHandler() {
        return new PositionServiceHandlerImpl();
    }

    @Bean
    public PositionHandler positionHandler() {
        return new PositionHandler();
    }

    @Bean
    public PersonasDao personasDao(){
        return new PersonasDaoImpl();
    }

    @Bean
    public PersonasService personasService() {
        return new PersonasServiceImpl();
    }

    @Bean
    public PersonasServiceHandler personasServiceHandler() {
        return new PersonasServiceHandlerImpl();
    }

    @Bean
    public PersonasHandler personasHandler() {
        return new PersonasHandler();
    }

    /////
    @Bean
    public CompetitorDao competitorDao(){
        return new CompetitorDaoImpl();
    }

    @Bean
    public CompetitorService competitorService() {
        return new CompetitorServiceImpl();
    }

    @Bean
    public CompetitorServiceHandler competitorServiceHandler() {
        return new CompetitorServiceHandlerImpl();
    }

    @Bean
    public CompetitorHandler competitorHandler() {
        return new CompetitorHandler();
    }

    /////
    @Bean
    public GoalDao goalDao(){
        return new GoalDaoImpl();
    }

    @Bean
    public GoalService goalService() {
        return new GoalServiceImpl();
    }

    @Bean
    public GoalServiceHandler goalServiceHandler() {
        return new GoalServiceHandlerImpl();
    }

    @Bean
    public GoalHandler goalHandler() {
        return new GoalHandler();
    }

    /////
    @Bean
    public InitiativeDao initiativeDao(){
        return new InitiativeDaoImpl();
    }

    @Bean
    public InitiativeService initiativeService() {
        return new InitiativeServiceImpl();
    }

    @Bean
    public InitiativeServiceHandler initiativeServiceHandler() {
        return new InitiativeServiceHandlerImpl();
    }

    @Bean
    public InitiativeHandler initiativeHandler() {
        return new InitiativeHandler();
    }
}
