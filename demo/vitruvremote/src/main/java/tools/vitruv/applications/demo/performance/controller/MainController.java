package tools.vitruv.applications.demo.performance.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.x509.GeneralName;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import tools.vitruv.applications.demo.DemoUtility;
import tools.vitruv.applications.demo.performance.Configuration;
import tools.vitruv.applications.demo.performance.common.EnvUtility;
import tools.vitruv.applications.demo.performance.common.IpUtil;
import tools.vitruv.applications.demo.performance.common.PathConstants;
import tools.vitruv.applications.demo.performance.common.PerformanceDirectoryStructure;
import tools.vitruv.applications.demo.performance.configs.LocalExecutionController;
import tools.vitruv.applications.demo.performance.configs.VitruvClientController;
import tools.vitruv.applications.demo.performance.configs.VitruvServerController;
import tools.vitruv.applications.demo.performance.configs.client.MeasurementExecutionEngine;
import tools.vitruv.applications.demo.performance.configs.exchange.ConfigNames;
import tools.vitruv.applications.demo.performance.configs.exchange.StartConfigurationSetting.RepeatedModelGenerationConfiguration;
import tools.vitruv.applications.demo.performance.data.PerformanceDataContainer;
import tools.vitruv.framework.remote.client.jetty.JettyHttpClientFactory;
import tools.vitruv.framework.remote.common.AvailableHttpVersions;
import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.seccommon.cert.CertificateGenerator;

public final class MainController {
    private static final Logger logger = Logger.getLogger(MainController.class);

    private MainController() {}

    public static void main(String[] args) throws Exception {
        logger.info("Starting the performance measurement controller.");

        DemoUtility.registerFactories();

        // Setup environment.
        var remoteUri = EnvUtility.asString(EnvUtility.ENV_KEY_VITRUV_CONTROLLER_REMOTE_WORKER_URI, "http://localhost:9094");
        var vitruvServerPort = EnvUtility.asInt(EnvUtility.ENV_KEY_VITRUV_PERF_VITRUV_SERVER_PORT, 9097);
        
        var workDirString = EnvUtility.asString(EnvUtility.ENV_KEY_VITRUV_PERF_WORK_DIR, "target" + File.separator + "controller");
        var workDir = Paths.get(workDirString);
        var fileStructure = new PerformanceDirectoryStructure(workDir);

        var workerClient = JettyHttpClientFactory.createClearTextHttpClient(List.of(AvailableHttpVersions.HTTP_2));
        workerClient.setExecutor(new QueuedThreadPool(8));
        workerClient.start();
        var oidcUri = obtainOidcUri(workerClient, remoteUri);
        var tlsPwd = downloadTrustStore(workerClient, remoteUri, fileStructure.getRemoteServerTrustStorePath());
        
        var dataContainer = new PerformanceDataContainer(fileStructure.getPerformanceDataFile());

        // Prepare local measurements for server - client communication.
        var ip = IpUtil.getHostIp();
        if (remoteUri.contains(ip)) {
            downloadKeyStore(workerClient, remoteUri, fileStructure.getKeyStorePath());
        } else {
            CertificateGenerator.generateFullCertificateChain(
                tlsPwd,
                fileStructure.getKeyStorePath(),
                tlsPwd,
                fileStructure.getTrustStorePath(),
                ip,
                new GeneralName[] { new GeneralName(GeneralName.iPAddress, ip), new GeneralName(GeneralName.dNSName, ip) }
            );
            uploadTrustStore(workerClient, remoteUri, fileStructure.getTrustStorePath());
            CertificateGenerator.mergeKeyStores(fileStructure.getRemoteServerTrustStorePath(), tlsPwd, fileStructure.getTrustStorePath(), tlsPwd, false);
        }

        var tlsConfig = new TlsContextConfiguration(
            fileStructure.getKeyStorePath(),
            null,
            tlsPwd,
            fileStructure.getRemoteServerTrustStorePath(),
            null,
            tlsPwd,
            fileStructure.getTempCertDir()
        );
        
        var executionEngine = new MeasurementExecutionEngine(fileStructure.getVsumClientDir(), dataContainer);
        var serverController = new VitruvServerController(new VitruvServerConfiguration(ip, vitruvServerPort), tlsConfig, oidcUri, fileStructure.getVsumServerDir());
        var clientController = new VitruvClientController(tlsConfig, executionEngine);
        var localController = new LocalExecutionController(serverController, clientController, executionEngine);
        var executionUtil = new ControllerExecutionUtil(serverController, clientController, localController, workerClient, remoteUri);

        executionUtil.executePureLocalMeasurements(List.of(new RepeatedModelGenerationConfiguration(100, Configuration.SMALL_MODEL_PARAMETERS)));

        var generationConfigs = getGenerationConfigs();

        executionUtil.executePureLocalMeasurements(generationConfigs);

        executionUtil.executeLocalMeasurements(
            ConfigNames.CONFIG_SERVER_ORIGINAL,
            new String[] { ConfigNames.CONFIG_CLIENT_ORIGINAL },
            generationConfigs
        );

        executionUtil.executeLocalMeasurements(
            ConfigNames.CONFIG_SERVER_SECURITY2_PROXY_MODE,
            new String[] { ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP11 },
            generationConfigs
        );

        executionUtil.executeRemoteMeasurements(
            ConfigNames.CONFIG_SERVER_SECURITY2_DIRECT_MODE,
            new String[] { ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP11, ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP2 },
            generationConfigs
        );

        executionUtil.executeRemoteMeasurements(
            ConfigNames.CONFIG_SERVER_SECURITY2_PROXY_MODE,
            new String[] { ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP11, ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP3 },
            generationConfigs
        );

        executionUtil.executeRemoteMeasurements(
            ConfigNames.CONFIG_SERVER_ORIGINAL,
            new String[] { ConfigNames.CONFIG_CLIENT_ORIGINAL },
            generationConfigs
        );

        executionUtil.executeRemoteMeasurements(
            ConfigNames.CONFIG_SERVER_JETTY,
            new String[] { ConfigNames.CONFIG_CLIENT_JETTY_HTTP11 },
            generationConfigs
        );

        obtainAndStorePerformanceData(workerClient, remoteUri, dataContainer);
        dataContainer.toCsv(fileStructure.getPerformanceDataFile().resolveSibling("perf.csv"));
        stopRemoteServer(workerClient, remoteUri);
        workerClient.stop();
        logger.info("Finished. Have a nice day.");
    }

    private static String downloadTrustStore(HttpClient client, String baseUri, Path trustStorePath) throws Exception {
        var response = client.GET(baseUri + PathConstants.PATH_TRUST_STORE);
        Files.write(trustStorePath, response.getContent());
        return response.getHeaders().get(PathConstants.HEADER_VITRUV_SECRET);
    }

    private static void downloadKeyStore(HttpClient client, String baseUri, Path keyStorePath) throws Exception {
        var response = client.GET(baseUri + PathConstants.PATH_KEY_STORE);
        Files.write(keyStorePath, response.getContent());
    }

    private static void uploadTrustStore(HttpClient client, String baseUri, Path trustStorePath) throws Exception {
        client.POST(baseUri + PathConstants.PATH_TRUST_STORE).file(trustStorePath).send();
    }

    private static String obtainOidcUri(HttpClient client, String baseUri) throws Exception {
        return client.GET(baseUri + PathConstants.PATH_GET_OIDC).getContentAsString();
    }

    private static void obtainAndStorePerformanceData(HttpClient client, String baseUri, PerformanceDataContainer dataContainer) throws Exception {
        var response = client.GET(baseUri + PathConstants.PATH_PERFORMANCE_DATA);
        if (response.getStatus() != HttpStatus.OK_200) {
            System.err.println("Could not receive performance data from server");
            dataContainer.save();
            return;
        }
        dataContainer.addDataFromJson(response.getContentAsString());
        dataContainer.save();
    }

    private static void stopRemoteServer(HttpClient client, String baseUri) throws Exception {
        client.GET(baseUri + PathConstants.PATH_WORKER_STOP);
    }

    private static List<RepeatedModelGenerationConfiguration> getGenerationConfigs() {
        return getDefaultGenerationConfigs();
    }

    private static List<RepeatedModelGenerationConfiguration> getDefaultGenerationConfigs() {
        return List.of(
            new RepeatedModelGenerationConfiguration(Configuration.REPETITIONS_SMALL, Configuration.SMALL_MODEL_PARAMETERS),
            new RepeatedModelGenerationConfiguration(Configuration.REPETITIONS_MEDIUM, Configuration.MEDIUM_MODEL_PARAMETERS),
            new RepeatedModelGenerationConfiguration(Configuration.REPETITIONS_LARGE, Configuration.LARGE_MODEL_PARAMETERS)
        );
    }
}
