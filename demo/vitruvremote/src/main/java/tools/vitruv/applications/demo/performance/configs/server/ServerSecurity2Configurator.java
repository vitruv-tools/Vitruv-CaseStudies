package tools.vitruv.applications.demo.performance.configs.server;

import java.nio.file.Path;

import org.eclipse.jetty.security.openid.OpenIdConfiguration;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import tools.vitruv.applications.demo.performance.configs.MeasuredVsumInitializer;
import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.framework.remote.server.VitruviusServer;
import tools.vitruv.remote.secclient.JettySecureHttpClientFactory;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.secserver.builder.ServerHandlerConfigurator;
import tools.vitruv.remote.secserver.builder.VitruvSecurityServer2Builder;
import tools.vitruv.remote.secserver.config.AuthenticationMode;

public class ServerSecurity2Configurator implements ServerSupplier {
    private VitruvServerConfiguration serverConfig;
    private TlsContextConfiguration tlsConfig;
    private String oidcUri;
    private Path vsumDir;
    private boolean useDirectConnection;

    public ServerSecurity2Configurator(
            VitruvServerConfiguration serverConfig,
            TlsContextConfiguration tlsConfig,
            String oidcUri,
            Path vsumDir,
            boolean useDirectConnection
    ) {
        this.serverConfig = serverConfig;
        this.tlsConfig = tlsConfig;
        this.oidcUri = oidcUri;
        this.vsumDir = vsumDir;
        this.useDirectConnection = useDirectConnection;
    }

    @Override
    public VitruviusServer get() throws Exception {
        // Since we use a self-signed server certificate, the HTTP client used by Jetty for communicating with
		// the OpenID Connect provider must know the server certificate. Thus, we create our own HTTP client.
		var openIdHttpClient = JettySecureHttpClientFactory.createSecureHttpClient(this.tlsConfig);
        openIdHttpClient.setExecutor(new QueuedThreadPool(8));
		OpenIdConfiguration oidcConfig = new OpenIdConfiguration(this.oidcUri, null, null, "42", "a", openIdHttpClient);
		
		var builder = VitruvSecurityServer2Builder.createBuilder()
			.forHostNameOrIp(this.serverConfig.hostOrIp())
			.onPort(this.serverConfig.port())
			.withHttp11()
			.withHttp2()
			.withExperimentalHttp3(this.tlsConfig.tempCertDir())
			.usingKeyStorePath(this.tlsConfig.keyStorePath().toString(), this.tlsConfig.keyStorePassword()
        );
		
        ServerHandlerConfigurator nextBuilder;
        if (this.useDirectConnection) {
            nextBuilder = builder.operateInDirectConnectionMode();
        } else {
            nextBuilder = builder.operateInProxyMode();
        }
		
        var vitruvSecServer = nextBuilder.authenticateWith(AuthenticationMode.OPEN_ID)
			.withOpenIdConfiguration(oidcConfig)
			.buildFor(new MeasuredVsumInitializer(this.vsumDir));
		
		vitruvSecServer.start();
        
        return vitruvSecServer;
    }
}
