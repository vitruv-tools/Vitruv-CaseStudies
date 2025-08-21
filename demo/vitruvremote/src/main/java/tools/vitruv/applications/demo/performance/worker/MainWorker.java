package tools.vitruv.applications.demo.performance.worker;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.operator.OperatorCreationException;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.ResourceFactory;

import tools.vitruv.applications.demo.oidc.OIDCMockServer;
import tools.vitruv.applications.demo.oidc.OIDCMockServer.OIDCMockServerConfiguration;
import tools.vitruv.applications.demo.performance.common.PathConstants;
import tools.vitruv.applications.demo.performance.configs.ConfigNames;
import tools.vitruv.applications.demo.performance.configs.LocalConfiguration;
import tools.vitruv.applications.demo.performance.configs.VitruvClientController;
import tools.vitruv.applications.demo.performance.configs.VitruvServerController;
import tools.vitruv.applications.demo.performance.data.PerformanceDataContainer;
import tools.vitruv.applications.demo.performance.worker.handler.ClientStateHandler;
import tools.vitruv.applications.demo.performance.worker.handler.GetOidcHandler;
import tools.vitruv.applications.demo.performance.worker.handler.PerformanceDataSendHandler;
import tools.vitruv.applications.demo.performance.worker.handler.StartClientHandler;
import tools.vitruv.applications.demo.performance.worker.handler.StartServerHandler;
import tools.vitruv.applications.demo.performance.worker.handler.StopServerHandler;
import tools.vitruv.applications.demo.performance.worker.handler.StopWorkerHandler;
import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.seccommon.cert.CertificateGenerator;

public final class MainWorker {
    private static final String ENV_KEY_VITRUV_WORKER_SERVER_PORT = "VITRUV_WORKER_SERVER_PORT";
    private static final String ENV_KEY_VITRUV_WORKER_OIDC_PORT = "VITRUV_WORKER_OIDC_PORT";
    private static final String ENV_KEY_VITRUV_WORKER_VITRUV_SERVER_PORT = "VITRUV_WORKER_SERVER_PORT";
    private static final String ENV_KEY_VITRUV_WORKER_WORK_DIR = "VITRUV_WORKER_WORK_DIR";
    private static final String ENV_KEY_VITRUV_WORKER_TLS_PWD = "VITRUV_WORKER_TLS_PWD";
    private static final String ENV_KEY_VITRUV_WORKER_SKIP_LOCAL_MEASUREMENTS = "VITRUV_WORKER_SKIP_LOCAL_MEASUREMENTS";

    private MainWorker() {}

    public static void main(String[] args) {
        // Set environment up.
        var skipLocalMeasurementsString = System.getenv(ENV_KEY_VITRUV_WORKER_SKIP_LOCAL_MEASUREMENTS);
        var skipLocalMeasurements = Boolean.parseBoolean(skipLocalMeasurementsString);

        var serverPortString = System.getenv(ENV_KEY_VITRUV_WORKER_SERVER_PORT);
        var serverPort = asInteger(serverPortString, 9094);

        var oidcPortString = System.getenv(ENV_KEY_VITRUV_WORKER_OIDC_PORT);
        var oidcPort = asInteger(oidcPortString, 9095);

        var ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        var vitruvServerPortString = System.getenv(ENV_KEY_VITRUV_WORKER_VITRUV_SERVER_PORT);
        var vitruvServerPort = asInteger(vitruvServerPortString, 9096);

        var serverUri = String.format("http://%s:%d", ip, vitruvServerPort);
        var secureServerUri = String.format("https://%s:%d", ip, vitruvServerPort);
        
        var tlsPwd = System.getenv(ENV_KEY_VITRUV_WORKER_TLS_PWD);
        if (tlsPwd == null || tlsPwd.isBlank()) {
            tlsPwd = "vitruv";
        }

        var workDirString = System.getenv(ENV_KEY_VITRUV_WORKER_WORK_DIR);
        if (workDirString == null || workDirString.isBlank()) {
            workDirString = "target";
        }
        var workDir = Paths.get(workDirString);
        var vsumDir = workDir.resolve("vsum");
        var clientVsumDir = workDir.resolve("vsum-client");

        var certDir = workDir.resolve("certs");
        var keyStorePath = certDir.resolve("keystore.ks");
        var trustStorePath = certDir.resolve("truststore.ks");
        var tempServerCertDir = workDir.resolve("certs-server");

        try {
            checkAndCreateDirectory(certDir);
            checkAndCreateDirectory(tempServerCertDir);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not create directory for certificates. Stopping.");
            return;
        }

        try {
            CertificateGenerator.generateFullCertificateChain(
                tlsPwd,
                keyStorePath,
                tlsPwd,
                trustStorePath,
                ip,
                new GeneralName[] { new GeneralName(GeneralName.iPAddress, ip), new GeneralName(GeneralName.dNSName, ip) }
            );
        } catch (NoSuchAlgorithmException | OperatorCreationException | CertificateException | KeyStoreException
                | IOException e) {
            e.printStackTrace();
            System.err.println("Could not create certificates. Stopping.");
            return;
        }

        // Measure local configuration.
        ConfigNames.COMMUNICATION = ConfigNames.COMMUNICATION_SIDE_WORKER;
        var dataContainer = new PerformanceDataContainer(workDir.resolve("perf-data.json"));

        if (!skipLocalMeasurements) {
            try {
                LocalConfiguration.executeLocalConfiguration(vsumDir, dataContainer);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Could not fully execute local configurations. Stopping.");
                return;
            }
        }

        // Start OpenID Connect server.
        var tlsConfig = new TlsContextConfiguration(
            keyStorePath,
            null,
            tlsPwd,
            trustStorePath,
            null,
            tlsPwd,
            tempServerCertDir
        );
        OIDCMockServer oidcServer = new OIDCMockServer(new OIDCMockServerConfiguration(
            new VitruvServerConfiguration(ip, oidcPort), tlsConfig
        ));
        try {
            oidcServer.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not start OIDC mock server. Stopping.");
            return;
        }

        var serverController = new VitruvServerController(
            new VitruvServerConfiguration(ip, vitruvServerPort),
            tlsConfig,
            oidcServer.getBaseUri(),
            vsumDir
        );
        var clientController = new VitruvClientController(tlsConfig, dataContainer, clientVsumDir, secureServerUri);

        // Measure server - client on localhost.
        if (!skipLocalMeasurements) {
            try {
                executeLocalServerClientMeasurement(serverController, clientController, ConfigNames.CONFIG_SERVER_ORIGINAL, ConfigNames.CONFIG_CLIENT_ORIGINAL, serverUri);
                executeLocalServerClientMeasurement(serverController, clientController, ConfigNames.CONFIG_SERVER_SECURITY2_PROXY_MODE, ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP11, secureServerUri);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Could not execute local server - client measurements.");
                return;
            }
        }

        // Start server for worker - controller communication.
        ConfigNames.COMMUNICATION = ConfigNames.COMMUNICATION_WORKER_TO_CONTROLLER;
        Server server = new Server();

        HttpConfiguration httpConfig = new HttpConfiguration();
        HTTP2CServerConnectionFactory h2c = new HTTP2CServerConnectionFactory(httpConfig);
        ServerConnector connector = new ServerConnector(server, h2c);
        connector.setPort(serverPort);
        server.addConnector(connector);

        ContextHandlerCollection coreHandler = new ContextHandlerCollection();
        
        ResourceHandler trustStoreHandler = new ResourceHandler();
        trustStoreHandler.setBaseResource(ResourceFactory.of(trustStoreHandler).newResource(trustStorePath));
        var trustStoreContext = new ContextHandler(trustStoreHandler, PathConstants.PATH_TRUST_STORE);
        trustStoreContext.setAllowNullPathInContext(true);
        coreHandler.addHandler(trustStoreContext);
        
        coreHandler.addHandler(new ContextHandler(new StartServerHandler(serverController), PathConstants.PATH_SERVER_START));
        coreHandler.addHandler(new ContextHandler(new StopServerHandler(serverController), PathConstants.PATH_SERVER_STOP));
        coreHandler.addHandler(new ContextHandler(new StartClientHandler(clientController), PathConstants.PATH_CLIENT_START));
        coreHandler.addHandler(new ContextHandler(new ClientStateHandler(clientController), PathConstants.PATH_CLIENT_STATE));
        coreHandler.addHandler(new ContextHandler(new PerformanceDataSendHandler(dataContainer), PathConstants.PATH_PERFORMANCE_DATA));
        coreHandler.addHandler(new ContextHandler(new StopWorkerHandler(oidcServer), PathConstants.PATH_WORKER_STOP));
        coreHandler.addHandler(new ContextHandler(new GetOidcHandler(oidcServer.getBaseUri()), PathConstants.PATH_GET_OIDC));
        
        server.setHandler(coreHandler);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not start server. Stopping.");
            return;
        }
    }

    private static int asInteger(String potentialNumber, int defaultValue) {
        try {
            return Integer.parseInt(potentialNumber);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    private static void checkAndCreateDirectory(Path dirPath) throws IOException {
        if (Files.notExists(dirPath)) {
            Files.createDirectories(dirPath);
        }
    }

    private static void executeLocalServerClientMeasurement(
        VitruvServerController serverController,
        VitruvClientController clientController,
        String serverConfig,
        String clientConfig,
        String secureServerUri
    ) throws Exception {
        serverController.startServer(serverConfig);
        clientController.startClient(clientConfig, serverConfig, secureServerUri);
        clientController.waitForCompletion();
        serverController.stopServer();
    }
}
