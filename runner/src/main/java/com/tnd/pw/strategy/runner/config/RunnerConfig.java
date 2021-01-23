package com.tnd.pw.strategy.runner.config;

import com.tnd.dbservice.sdk.api.DBServiceSdkClient;
import com.tnd.dbservice.sdk.api.impl.DBServiceSdkClientImpl;
import com.tnd.pw.action.sdk.ActionServiceSdkClient;
import com.tnd.pw.action.sdk.impl.ActionServiceSdkClientImpl;
import com.tnd.pw.development.sdk.DevServiceSdkClient;
import com.tnd.pw.development.sdk.impl.DevServiceSdkClientImpl;
import com.tnd.pw.report.sdk.ReportSdkClient;
import com.tnd.pw.report.sdk.impl.ReportSdkClientImpl;
import com.tnd.pw.strategy.competitor.dao.CompetitorDao;
import com.tnd.pw.strategy.competitor.dao.impl.CompetitorDaoImpl;
import com.tnd.pw.strategy.competitor.service.CompetitorService;
import com.tnd.pw.strategy.competitor.service.impl.CompetitorServiceImpl;
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
import com.tnd.pw.strategy.report.SendReportMes;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@PropertySource("classpath:application.properties")
public class RunnerConfig {
    @Value("${db.host}")
    private String db_host;
    @Value("${db.port}")
    private String db_port;
    @Value("${action.service.host}")
    private String action_service_host;
    @Value("${action.service.port}")
    private String action_service_port;
    @Value("${report.service.host}")
    private String report_service_host;
    @Value("${report.service.port}")
    private String report_service_port;
    @Value("${dev.service.host}")
    private String dev_service_host;
    @Value("${dev.service.port}")
    private String dev_service_port;

    @Bean
    public DevServiceSdkClient devServiceSdkClient() {
        return new DevServiceSdkClientImpl(dev_service_host, Integer.parseInt(dev_service_port), 2);
    }

    @Bean
    public ActionServiceSdkClient actionServiceSdkClient() {
        return new ActionServiceSdkClientImpl(action_service_host, Integer.parseInt(action_service_port), 2);
    }

    @Bean
    public DBServiceSdkClient dbServiceSdkClient() {
        return new DBServiceSdkClientImpl(db_host,Integer.parseInt(db_port), 1);
    }

    @Bean
    public DataHelper dataHelper(DBServiceSdkClient dbServiceSdkClient) {
        return new DataHelper(dbServiceSdkClient);
    }

    @Bean
    public SdkService sdkService() {
        return new SdkService();
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

    @Bean
    public StrategyHandler strategyHandler() {
        return new StrategyHandler();
    }

    @Bean
    public ReportSdkClient reportSdkClient() {
        return new ReportSdkClientImpl(report_service_host, Integer.parseInt(report_service_port), 1);
    }

    @Bean
    public ExecutorService executor() {
        return Executors.newFixedThreadPool(5);
    }

    @Bean
    public SendReportMes sendReportMes() {
        return new SendReportMes();
    }
}
