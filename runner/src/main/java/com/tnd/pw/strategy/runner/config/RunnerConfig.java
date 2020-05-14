package com.tnd.pw.strategy.runner.config;

import com.tnd.pw.strategy.dbservice.DBServiceApiClient;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.runner.handler.VisionHandler;
import com.tnd.pw.strategy.runner.service.VisionHandlerBuz;
import com.tnd.pw.strategy.vision.buz.VisionBuz;
import com.tnd.pw.strategy.vision.buz.impl.VisionBuzImpl;
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
    public VisionService visionService() {
        return new VisionServiceImpl();
    }

    @Bean
    public VisionComponentService visionComponentService() {
        return new VisionComponentServiceImpl();
    }

    @Bean
    public VisionBuz visionBuz() {
        return new VisionBuzImpl();
    }

    @Bean
    public VisionHandlerBuz visionHandlerBuz() {
        return new VisionHandlerBuz();
    }

    @Bean
    public VisionHandler visionHandler() {
        return new VisionHandler();
    }
}
