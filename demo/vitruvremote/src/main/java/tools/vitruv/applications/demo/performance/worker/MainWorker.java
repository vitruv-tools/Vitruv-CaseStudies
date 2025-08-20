package tools.vitruv.applications.demo.performance.worker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

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
import tools.vitruv.applications.demo.performance.common.VitruvClientController;
import tools.vitruv.applications.demo.performance.common.VitruvServerController;
import tools.vitruv.applications.demo.performance.data.PerformanceDataContainer;
import tools.vitruv.applications.demo.performance.worker.handler.ClientStateHandler;
import tools.vitruv.applications.demo.performance.worker.handler.PerformanceDataSendHandler;
import tools.vitruv.applications.demo.performance.worker.handler.StartClientHandler;
import tools.vitruv.applications.demo.performance.worker.handler.StartServerHandler;
import tools.vitruv.applications.demo.performance.worker.handler.StopServerHandler;
import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.seccommon.cert.CertificateGenerator;

public final class MainWorker {
    private static final String ENV_KEY_VITRUV_WORKER_SERVER_PORT = "VITRUV_WORKER_SERVER_PORT";
    private static final String ENV_KEY_VITRUV_WORKER_OIDC_PORT = "VITRUV_WORKER_OIDC_PORT";
    private static final String ENV_KEY_VITRUV_WORKER_VITRUV_SERVER_PORT = "VITRUV_WORKER_SERVER_PORT";
    private static final String ENV_KEY_VITRUV_WORKER_WORK_DIR = "VITRUV_WORKER_WORK_DIR";
    private static final String ENV_KEY_VITRUV_WORKER_TLS_PWD = "VITRUV_WORKER_TLS_PWD";

    private MainWorker() {}

    public static void main(String[] args) {
        // Set environment up.
        var serverPortString = System.getenv(ENV_KEY_VITRUV_WORKER_SERVER_PORT);
        var serverPort = asInteger(serverPortString, 9094);

        var oidcPortString = System.getenv(ENV_KEY_VITRUV_WORKER_OIDC_PORT);
        var oidcPort = asInteger(oidcPortString, 9095);

        var vitruvServerPortString = System.getenv(ENV_KEY_VITRUV_WORKER_VITRUV_SERVER_PORT);
        var vitruvServerPort = asInteger(vitruvServerPortString, 9096);

        var tlsPwd = System.getenv(ENV_KEY_VITRUV_WORKER_TLS_PWD);
        if (tlsPwd == null || tlsPwd.isBlank()) {
            tlsPwd = "vitruv";
        }

        var workDirString = System.getenv(ENV_KEY_VITRUV_WORKER_WORK_DIR);
        if (workDirString == null || workDirString.isBlank()) {
            workDirString = "target";
        }
        var workDir = Paths.get(workDirString);

        var certDir = Paths.get("certs");
        var keyStorePath = certDir.resolve("keystore.ks");
        var trustStorePath = certDir.resolve("truststore.ks");

        try {
            checkAndCreateDirectory(certDir);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not create directory for certificates. Stopping.");
            return;
        }

        try {
            CertificateGenerator.generateFullCertificateChain(tlsPwd, keyStorePath, tlsPwd, trustStorePath, "", null);
        } catch (NoSuchAlgorithmException | OperatorCreationException | CertificateException | KeyStoreException
                | IOException e) {
            e.printStackTrace();
            System.err.println("Could not create certificates. Stopping.");
            return;
        }

        // Measure local configurations.

        // Start servers for controller - worker communication.
        OIDCMockServer oidcServer = new OIDCMockServer(new OIDCMockServerConfiguration(
            new VitruvServerConfiguration("", oidcPort),
            new TlsContextConfiguration(
                keyStorePath,
                null,
                tlsPwd,
                trustStorePath,
                null,
                tlsPwd,
                null
            )
        ));
        try {
            oidcServer.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not start OIDC mock server. Stopping.");
            return;
        }

        Server server = new Server();

        HttpConfiguration httpConfig = new HttpConfiguration();
        HTTP2CServerConnectionFactory h2c = new HTTP2CServerConnectionFactory(httpConfig);
        ServerConnector connector = new ServerConnector(server, h2c);
        connector.setPort(serverPort);
        server.addConnector(connector);

        var dataContainer = new PerformanceDataContainer();
        var serverController = new VitruvServerController();
        var clientController = new VitruvClientController();

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
}
