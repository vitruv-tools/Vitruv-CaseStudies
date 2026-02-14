package tools.vitruv.applications.demo.performance.worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tools.vitruv.applications.demo.performance.configs.LocalExecutionController;
import tools.vitruv.applications.demo.performance.configs.VitruvClientController;
import tools.vitruv.applications.demo.performance.configs.VitruvServerController;
import tools.vitruv.applications.demo.performance.configs.exchange.ConfigNames;
import tools.vitruv.applications.demo.performance.configs.exchange.StartConfigurationSetting;

public class ConfigExecutionController {
    private VitruvServerController serverController;
    private VitruvClientController clientController;
    private LocalExecutionController localController;
    private ExecutorService executionService = Executors.newFixedThreadPool(1);

    public ConfigExecutionController(
        VitruvServerController serverController,
        VitruvClientController clientController,
        LocalExecutionController localController
    ) {
        this.serverController = serverController;
        this.clientController = clientController;
        this.localController = localController;
    }

    public String executeConfig(StartConfigurationSetting configSetting) throws Exception {
        ConfigNames.COMMUNICATION = configSetting.getCommunication();
        if (configSetting.getCommunication().equals(ConfigNames.COMMUNICATION_SIDE_WORKER)) {
            this.executionService.execute(() -> {
                try {
                    this.localController.startLocalMeasurement(configSetting);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else if (configSetting.getClientConfigs() == null || configSetting.getClientConfigs().length == 0) {
            return this.serverController.startServer(configSetting.getServerConfig());
        } else {
            this.executionService.execute(() -> {
                try {
                    this.clientController.excuteClient(
                        configSetting,
                        configSetting.getClientConfigs()[0],
                        configSetting.getServerUri()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return "";
    }

    public void stopServer() throws Exception {
        if (!this.localController.isLocalExecutionRunning() && this.serverController.isServerRunning()) {
            this.serverController.stopServer();
        }
    }

    public double getProgress() {
        return Math.min(this.clientController.getProgress(), this.localController.getProgress());
    }

    public boolean isRunning() {
        return this.serverController.isServerRunning()
            || this.clientController.isClientRunning()
            || this.localController.isLocalExecutionRunning();
    }
}
