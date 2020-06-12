package com.tnd.pw.strategy.runner;

import com.tnd.com.ioc.SpringApplicationContext;
import com.tnd.common.api.server.CommonServer;
import com.tnd.pw.strategy.runner.config.RunnerConfig;
import com.tnd.pw.strategy.runner.handler.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StrategyRunner {
    public static void main(String args[]) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(RunnerConfig.class);
        context.refresh();
        SpringApplicationContext.setShareApplicationContext(context);

        CommonServer commonServer = new CommonServer();
        commonServer.register(SpringApplicationContext.getBean(VisionHandler.class));
        commonServer.register(SpringApplicationContext.getBean(LayoutHandler.class));
        commonServer.register(SpringApplicationContext.getBean(ModelHandler.class));
        commonServer.register(SpringApplicationContext.getBean(PositionHandler.class));
        commonServer.register(SpringApplicationContext.getBean(PersonasHandler.class));
        commonServer.register(SpringApplicationContext.getBean(CompetitorHandler.class));

        String port = System.getenv("PORT");
        if(port == null) {
            commonServer.initServlet(8002);
        }
        else {
            commonServer.initServlet(Integer.parseInt(port));
        }
        commonServer.startServer();
    }
}
