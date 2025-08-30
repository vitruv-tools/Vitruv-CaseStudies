package tools.vitruv.applications.demo.performance.worker;

import java.io.File;
import java.nio.file.Paths;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.bouncycastle.asn1.x509.GeneralName;
import org.eclipse.jetty.http.pathmap.RegexPathSpec;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.PathMappingsHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.google.common.io.Files;

import tools.vitruv.applications.demo.DemoUtility;
import tools.vitruv.applications.demo.oidc.OIDCMockServer;
import tools.vitruv.applications.demo.oidc.OIDCMockServer.OIDCMockServerConfiguration;
import tools.vitruv.applications.demo.performance.common.EnvUtility;
import tools.vitruv.applications.demo.performance.common.IpUtil;
import tools.vitruv.applications.demo.performance.common.PathConstants;
import tools.vitruv.applications.demo.performance.common.PerformanceDirectoryStructure;
import tools.vitruv.applications.demo.performance.configs.LocalExecutionController;
import tools.vitruv.applications.demo.performance.configs.VitruvClientController;
import tools.vitruv.applications.demo.performance.configs.VitruvServerController;
import tools.vitruv.applications.demo.performance.configs.client.MeasurementExecutionEngine;
import tools.vitruv.applications.demo.performance.data.PerformanceDataContainer;
import tools.vitruv.applications.demo.performance.worker.handler.StateHandler;
import tools.vitruv.applications.demo.performance.worker.handler.GetOidcHandler;
import tools.vitruv.applications.demo.performance.worker.handler.KeyStoreHandler;
import tools.vitruv.applications.demo.performance.worker.handler.PerformanceDataSendHandler;
import tools.vitruv.applications.demo.performance.worker.handler.StartConfigHandler;
import tools.vitruv.applications.demo.performance.worker.handler.StopServerHandler;
import tools.vitruv.applications.demo.performance.worker.handler.StopWorkerHandler;
import tools.vitruv.applications.demo.performance.worker.handler.TrustStoreHandler;
import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.framework.remote.common.AddressBinderUtil;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.seccommon.cert.CertificateGenerator;

public final class MainWorker {
    private MainWorker() {}

    public static void main(String[] args) throws Exception {
        // Set environment up.
        Configurator.setRootLevel(Level.INFO);
        
        DemoUtility.registerFactories();

        var serverPort = EnvUtility.asInt(EnvUtility.ENV_KEY_VITRUV_PERF_SERVER_PORT, 9094);
        var oidcPort = EnvUtility.asInt(EnvUtility.ENV_KEY_VITRUV_WORKER_OIDC_PORT, 9095);
        var vitruvServerPort = EnvUtility.asInt(EnvUtility.ENV_KEY_VITRUV_PERF_VITRUV_SERVER_PORT, 9096);

        var ip = EnvUtility.asString(EnvUtility.ENV_KEY_VITRUV_PERF_SERVER_HOST, IpUtil.getHostIp());

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
        Files.copy(fileStructure.getTrustStorePath().toFile(), fileStructure.getRemoteServerTrustStorePath().toFile());
        
        var tlsConfig = new TlsContextConfiguration(
            fileStructure.getKeyStorePath(),
            null,
            tlsPwd,
            fileStructure.getRemoteServerTrustStorePath(),
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
        var executionEngine = new MeasurementExecutionEngine(fileStructure.getVsumClientDir(), dataContainer);
        var clientController = new VitruvClientController(tlsConfig, executionEngine);
        var localController = new LocalExecutionController(serverController, clientController, executionEngine);
        var executionController = new ConfigExecutionController(serverController, clientController, localController);

        // Start server for worker - controller communication.
        Server server = new Server(new QueuedThreadPool(8));

        HttpConfiguration httpConfig = new HttpConfiguration();
        HTTP2CServerConnectionFactory h2c = new HTTP2CServerConnectionFactory(httpConfig);
        ServerConnector connector = new ServerConnector(server, h2c);
        connector.setPort(serverPort);
        connector.setHost(AddressBinderUtil.getAddressForBinding(ip));
        server.addConnector(connector);

        var coreHandler = new PathMappingsHandler();
        coreHandler.addMapping(new RegexPathSpec(PathConstants.PATH_TRUST_STORE), new TrustStoreHandler(fileStructure.getTrustStorePath(), fileStructure.getRemoteServerTrustStorePath(), tlsPwd));
        coreHandler.addMapping(new RegexPathSpec(PathConstants.PATH_KEY_STORE), new KeyStoreHandler(fileStructure.getKeyStorePath(), ip));
        coreHandler.addMapping(new RegexPathSpec(PathConstants.PATH_START_CONFIG), new StartConfigHandler(executionController));
        coreHandler.addMapping(new RegexPathSpec(PathConstants.PATH_SERVER_STOP), new StopServerHandler(serverController));
        coreHandler.addMapping(new RegexPathSpec(PathConstants.PATH_STATE), new StateHandler(executionController));
        coreHandler.addMapping(new RegexPathSpec(PathConstants.PATH_PERFORMANCE_DATA), new PerformanceDataSendHandler(dataContainer));
        coreHandler.addMapping(new RegexPathSpec(PathConstants.PATH_WORKER_STOP), new StopWorkerHandler(oidcServer));
        coreHandler.addMapping(new RegexPathSpec(PathConstants.PATH_GET_OIDC), new GetOidcHandler(oidcServer.getBaseUri()));
        
        server.setHandler(coreHandler);

        server.start();
    }
}
