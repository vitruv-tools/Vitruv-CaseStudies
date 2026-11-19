package tools.vitruv.applications.demo.performance.configs;

import tools.vitruv.applications.demo.performance.common.ProgressUtility;
import tools.vitruv.applications.demo.performance.configs.client.MeasurementExecutionEngine;
import tools.vitruv.applications.demo.performance.configs.exchange.ConfigNames;
import tools.vitruv.applications.demo.performance.configs.exchange.StartConfigurationSetting;

public class LocalExecutionController {
    private VitruvServerController serverController;
    private VitruvClientController clientController;
    private MeasurementExecutionEngine executionEngine;

    public LocalExecutionController(
        VitruvServerController serverController,
        VitruvClientController clientController,
        MeasurementExecutionEngine executionEngine
    ) {
        this.serverController = serverController;
        this.clientController = clientController;
        this.executionEngine = executionEngine;
    }

    public double getProgress() {
        return this.executionEngine.getProgress();
    }

    public void startLocalMeasurement(StartConfigurationSetting setting) throws Exception {
        if (setting.getServerConfig().equals(ConfigNames.CONFIG_LOCAL)
            || setting.getServerConfig().equals(ConfigNames.CONFIG_PRE_MEASUREMENTS)) {
            this.executionEngine.executeLocalMeasurements(setting);
        } else {
            this.executeLocalServerClientMeasurement(setting);
        }
    }

    private void executeLocalServerClientMeasurement(StartConfigurationSetting setting) throws Exception {
        var uri = this.serverController.startServer(setting.getServerConfig());
        for (var cConfig : setting.getClientConfigs()) {
            this.clientController.excuteClient(setting, cConfig, uri);
        }
        this.serverController.stopServer();
    }

    public boolean isLocalExecutionRunning() {
        return this.getProgress() < ProgressUtility.DEFAULT_NO_PROGRESS_VALUE;
    }
}
