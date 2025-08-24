package tools.vitruv.applications.demo.performance.configs;

import java.nio.file.Path;

import tools.vitruv.applications.demo.performance.common.ProgressUtility;
import tools.vitruv.applications.demo.performance.data.PerformanceDataContainer;

public class LocalExecutionController {
    private VitruvServerController serverController;
    private VitruvClientController clientController;
    private LocalExecutor localController;
    private Path vsumDir;
    private PerformanceDataContainer dataContainer;

    public LocalExecutionController(
        VitruvServerController serverController,
        VitruvClientController clientController,
        Path vsumDir,
        PerformanceDataContainer dataContainer
    ) {
        this.serverController = serverController;
        this.clientController = clientController;
        this.localController = new LocalExecutor();
        this.vsumDir = vsumDir;
        this.dataContainer = dataContainer;
    }

    public double getProgress() {
        return Math.min(this.clientController.getProgress(), this.localController.getProgress());
    }

    public void startLocalMeasurement(String serverConfig, String[] clientConfigs) throws Exception {
        if (serverConfig.equals(ConfigNames.CONFIG_LOCAL)) {
            this.localController.executeLocalConfiguration(this.vsumDir, this.dataContainer);
        } else {
            this.executeLocalServerClientMeasurement(serverConfig, clientConfigs);
        }
    }

    private void executeLocalServerClientMeasurement(String serverConfig, String[] clientConfigs) throws Exception {
        var uri = this.serverController.startServer(serverConfig);
        for (var cConfig : clientConfigs) {
            this.clientController.excuteClient(cConfig, serverConfig, uri);
        }
        this.serverController.stopServer();
    }

    public boolean isLocalExecutionRunning() {
        return this.getProgress() < ProgressUtility.DEFAULT_NO_PROGRESS_VALUE;
    }
}
