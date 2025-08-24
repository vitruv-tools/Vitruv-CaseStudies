package tools.vitruv.applications.demo.performance.worker;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
import tools.vitruv.applications.demo.performance.common.EnvUtility;
import tools.vitruv.applications.demo.performance.common.IpUtil;
import tools.vitruv.applications.demo.performance.common.PathConstants;
import tools.vitruv.applications.demo.performance.common.PerformanceDirectoryStructure;
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
    private MainWorker() {}

    public static void main(String[] args) throws Exception {
        // Set environment up.
        var serverPort = EnvUtility.asInt(EnvUtility.ENV_KEY_VITRUV_PERF_SERVER_PORT, 9094);
        var oidcPort = EnvUtility.asInt(EnvUtility.ENV_KEY_VITRUV_WORKER_OIDC_PORT, 9095);
        var vitruvServerPort = EnvUtility.asInt(EnvUtility.ENV_KEY_VITRUV_PERF_VITRUV_SERVER_PORT, 9096);

        var ip = IpUtil.getHostIp();
        var serverUri = String.format("http://%s:%d", ip, vitruvServerPort);
        var secureServerUri = String.format("https://%s:%d", ip, vitruvServerPort);

        var workDirString = EnvUtility.asString(EnvUtility.ENV_KEY_VITRUV_PERF_WORK_DIR, "target" + File.separator + "worker");
        var workDir = Paths.get(workDirString);
        PerformanceDirectoryStructure fileStructure = new PerformanceDirectoryStructure(workDir);
        
        var tlsPwd = EnvUtility.asString(EnvUtility.ENV_KEY_VITRUV_PERF_TLS_PWD, "vitruv");

        CertificateGenerator.generateFullCertificateChain(
            tlsPwd,
            fileStructure.getKeyStorePath(),
            tlsPwd,
            fileStructure.getTrustStorePath(),
            ip,
            new GeneralName[] { new GeneralName(GeneralName.iPAddress, ip), new GeneralName(GeneralName.dNSName, ip) }
        );
        
        // Start OpenID Connect server.
        var tlsConfig = new TlsContextConfiguration(
            fileStructure.getKeyStorePath(),
            null,
            tlsPwd,
            fileStructure.getTrustStorePath(),
            null,
            tlsPwd,
            fileStructure.getTempCertDir()
        );
        OIDCMockServer oidcServer = new OIDCMockServer(new OIDCMockServerConfiguration(
            new VitruvServerConfiguration(ip, oidcPort), tlsConfig
        ));
        oidcServer.start();

        var dataContainer = new PerformanceDataContainer(fileStructure.getPerformanceDataFile());
        var serverController = new VitruvServerController(
            new VitruvServerConfiguration(ip, vitruvServerPort),
            tlsConfig,
            oidcServer.getBaseUri(),
            fileStructure.getVsumServerDir()
        );
        var clientController = new VitruvClientController(tlsConfig, dataContainer, fileStructure.getVsumClientDir());

        // Start server for worker - controller communication.
        Server server = new Server();

        HttpConfiguration httpConfig = new HttpConfiguration();
        HTTP2CServerConnectionFactory h2c = new HTTP2CServerConnectionFactory(httpConfig);
        ServerConnector connector = new ServerConnector(server, h2c);
        connector.setPort(serverPort);
        server.addConnector(connector);

        ContextHandlerCollection coreHandler = new ContextHandlerCollection();
        
        ResourceHandler trustStoreHandler = new ResourceHandler();
        trustStoreHandler.setBaseResource(ResourceFactory.of(trustStoreHandler).newResource(fileStructure.getTrustStorePath()));
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

        server.start();
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
