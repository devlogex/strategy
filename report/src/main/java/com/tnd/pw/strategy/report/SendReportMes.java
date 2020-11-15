package com.tnd.pw.strategy.report;

import com.tnd.pw.report.sdk.ReportSdkClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;

public class SendReportMes {
    @Autowired
    private ReportSdkClient reportSdkClient;
    @Autowired
    private ExecutorService executor;

    public void createHistory(Long userId, Long objectId, String action, String content) {
        executor.execute(() -> reportSdkClient.createHistory(userId, objectId, action, content));
    }

    public void createWatcher(Long userId, Long objectId) {
        executor.execute(() -> reportSdkClient.createWatcher(userId, objectId));
    }
}
