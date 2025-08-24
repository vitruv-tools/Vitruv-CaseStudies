package tools.vitruv.applications.demo.performance.controller;

import org.apache.log4j.Logger;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.Request.Content;
import org.eclipse.jetty.client.StringRequestContent;
import org.eclipse.jetty.http.HttpStatus;

import com.nimbusds.jose.shaded.gson.Gson;

import tools.vitruv.applications.demo.performance.common.PathConstants;
import tools.vitruv.applications.demo.performance.common.ProgressUtility;
import tools.vitruv.applications.demo.performance.configs.ConfigNames;
import tools.vitruv.applications.demo.performance.configs.LocalExecutionController;
import tools.vitruv.applications.demo.performance.configs.VitruvClientController;
import tools.vitruv.applications.demo.performance.configs.VitruvServerController;
import tools.vitruv.applications.demo.performance.configs.exchange.StartConfigurationSetting;

public class ControllerExecutionUtil {
    private static final Logger logger = Logger.getLogger(ControllerExecutionUtil.class);
    private VitruvServerController serverController;
    private VitruvClientController clientController;
    private LocalExecutionController localController;
    private HttpClient client;
    private String remoteUri;

    public ControllerExecutionUtil(
        VitruvServerController serverController,
        VitruvClientController clientController,
        LocalExecutionController localController,
        HttpClient client,
        String remoteUri
    ) {
        this.serverController = serverController;
        this.clientController = clientController;
        this.localController = localController;
        this.client = client;
        this.remoteUri = remoteUri;
    }

    public void executePureLocalMeasurements() throws Exception {
        ConfigNames.COMMUNICATION = ConfigNames.COMMUNICATION_SIDE_CONTROLLER;
        sendStartConfigRequest(ConfigNames.CONFIG_LOCAL, new String [0], null);
        localController.startLocalMeasurement(ConfigNames.CONFIG_LOCAL, null);
        waitForWorker();
    }

    public void executeLocalMeasurements(String serverConfig, String[] clientConfigs) throws Exception {
        ConfigNames.COMMUNICATION = ConfigNames.COMMUNICATION_SIDE_CONTROLLER;
        sendStartConfigRequest(serverConfig, clientConfigs, null);
        this.localController.startLocalMeasurement(serverConfig, clientConfigs);
        waitForWorker();
    }

    public void executeRemoteMeasurements(String serverConfig, String[] clientConfigs) throws Exception {
        executeControllerToWorkerMeasurements(serverConfig, clientConfigs);
        executeWorkerToControllerMeasurements(serverConfig, clientConfigs);
    }

    public void executeControllerToWorkerMeasurements(String serverConfig, String[] clientConfigs) throws Exception {
        logger.info("Starting with (controller -> worker):");
        logger.info(serverConfig);

        ConfigNames.COMMUNICATION = ConfigNames.COMMUNICATION_CONTROLLER_TO_WORKER;
        var vitruvServerUri = sendStartConfigRequest(serverConfig, new String [0], null);

        for (var cConfig : clientConfigs) {
            logger.info("Client config " + cConfig);
            clientController.excuteClient(cConfig, serverConfig, vitruvServerUri);
        }

        client.GET(remoteUri + PathConstants.PATH_SERVER_STOP);
    }

    public void executeWorkerToControllerMeasurements(String serverConfig, String[] clientConfigs) throws Exception {
        logger.info("Starting with (worker -> controller):");
        logger.info(serverConfig);

        ConfigNames.COMMUNICATION = ConfigNames.COMMUNICATION_WORKER_TO_CONTROLLER;
        var serverUri = serverController.startServer(serverConfig);

        for (var cConfig : clientConfigs) {
            logger.info("Client config: " + cConfig);
            try {
                sendStartConfigRequest(serverConfig, cConfig, serverUri);
            } catch (Exception e) {
                continue;
            }
            waitForWorker();
        }

        serverController.stopServer();
    }

    private void waitForWorker() throws Exception {
        logger.info("Waiting for worker.");

        var progress = 0.0;
        long defaultWaitingTime = 1000 * 60 * 5;
        var waitingTime = defaultWaitingTime;

        while (progress < ProgressUtility.DEFAULT_NO_PROGRESS_VALUE) {
            Thread.sleep(waitingTime);

            var response = client.GET(remoteUri + PathConstants.PATH_STATE);

            var currentProgress = Double.parseDouble(response.getContentAsString());
            var estimatedRemainingTime = (1 - currentProgress) / (currentProgress - progress) * defaultWaitingTime;

            logger.info("Finished " + currentProgress + " % (Estimated remaining time: " + estimatedRemainingTime + " ms)");

            if (estimatedRemainingTime < defaultWaitingTime) {
                waitingTime = defaultWaitingTime;
            } else {
                waitingTime = Math.max((long) estimatedRemainingTime, 5 * defaultWaitingTime);
            }

            progress = currentProgress;
        }
    }

    private String sendStartConfigRequest(String serverConfig, String clientConfig, String serverUri) throws Exception {
        return sendStartConfigRequest(serverConfig, new String[] { clientConfig }, serverUri);
    }

    private String sendStartConfigRequest(String serverConfig, String[] clientConfigs, String serverUri) throws Exception {
        var response = client
            .POST(remoteUri + PathConstants.PATH_START_CONFIG)
            .body(toConfigSettingsContent(serverConfig, clientConfigs, serverUri))
            .send();
        if (response.getStatus() != HttpStatus.OK_200) {
            throw new IllegalStateException("Something went wrong. Got response: " + response.getStatus());
        }
        return response.getContentAsString();
    }

    private Content toConfigSettingsContent(
        String serverConfig,
        String[] clientConfigs,
        String serverUri
    ) {
        var config = new StartConfigurationSetting();
        config.setCommunication(ConfigNames.COMMUNICATION);
        if (ConfigNames.COMMUNICATION.equals(ConfigNames.COMMUNICATION_SIDE_CONTROLLER)) {
            config.setCommunication(ConfigNames.COMMUNICATION_SIDE_WORKER);
        }
        config.setServerConfig(serverConfig);
        config.setClientConfig(clientConfigs);
        config.setServerUri(serverUri);
        var bodyString = new Gson().toJson(config);
        return new StringRequestContent(bodyString);
    }
}
