package tools.vitruv.applications.demo.oidc;

import java.util.List;

import org.eclipse.jetty.http.pathmap.RegexPathSpec;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.PathMappingsHandler;

import com.nimbusds.openid.connect.sdk.op.OIDCProviderConfigurationRequest;

import tools.vitruv.applications.demo.oidc.OIDCMockProvider.OIDCMockProviderConfiguration;
import tools.vitruv.applications.demo.oidc.handler.AuthorizationHandler;
import tools.vitruv.applications.demo.oidc.handler.JwkSetHandler;
import tools.vitruv.applications.demo.oidc.handler.LogoutHandler;
import tools.vitruv.applications.demo.oidc.handler.ProviderMetadataHandler;
import tools.vitruv.applications.demo.oidc.handler.TokenHandler;
import tools.vitruv.framework.remote.common.AvailableHttpVersions;
import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.remote.seccommon.SecurityProviderInitialization;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.secserver.config.ServerConnectionConfiguration;
import tools.vitruv.remote.secserver.jetty.JettyServerConnectionInitializer;

public class OIDCMockServer {
    static {
        SecurityProviderInitialization.initializeSecurityProviders();
    }

    public static record OIDCMockServerConfiguration(
        VitruvServerConfiguration connectionConfig,
        TlsContextConfiguration tlsConfig
    ) {}

    private OIDCMockServerConfiguration config;
    private Server server;

    public OIDCMockServer(OIDCMockServerConfiguration config) {
        this.config = config;
    }
    
    public void start() throws Exception {
        boolean useTls = this.config.tlsConfig() != null;
        
        String baseUri = String.format(
            "http%s://%s:%s",
            useTls ? "s" : "",
            this.config.connectionConfig.hostOrIp(),
            this.config.connectionConfig.port()
        );

        var oidcProvider = new OIDCMockProvider();
        oidcProvider.initialize(
            new OIDCMockProviderConfiguration(
                baseUri,
                this.config.tlsConfig().keyStorePath(),
                this.config.tlsConfig().keyStorePassword(),
                this.config.connectionConfig.hostOrIp()
            )
        );

        this.server = new Server();

        if (useTls) {
            JettyServerConnectionInitializer.initializeConnectors(
                server,
                new ServerConnectionConfiguration(
                    List.of(AvailableHttpVersions.HTTP_1_1, AvailableHttpVersions.HTTP_2),
                    this.config.connectionConfig().hostOrIp(),
                    this.config.connectionConfig().port(),
                    this.config.tlsConfig()
                )
            );
        } else {
            HttpConfiguration httpConfig = new HttpConfiguration();
            HttpConnectionFactory http1 = new HttpConnectionFactory(httpConfig);
            HTTP2CServerConnectionFactory h2c = new HTTP2CServerConnectionFactory(httpConfig);
            ServerConnector connector = new ServerConnector(server, http1, h2c);
            connector.setHost(this.config.connectionConfig().hostOrIp());
            connector.setPort(this.config.connectionConfig().port());
            server.addConnector(connector);
        }

        var handler = new PathMappingsHandler();
        handler.addMapping(new RegexPathSpec(OIDCProviderConfigurationRequest.OPENID_PROVIDER_WELL_KNOWN_PATH), new ProviderMetadataHandler(oidcProvider));
        handler.addMapping(new RegexPathSpec(OIDCMockProvider.OIDC_AUTHORIZATION_PATH), new AuthorizationHandler(oidcProvider));
        handler.addMapping(new RegexPathSpec(OIDCMockProvider.OIDC_TOKEN_PATH), new TokenHandler(oidcProvider));
        handler.addMapping(new RegexPathSpec(OIDCMockProvider.OIDC_JKWS_PATH), new JwkSetHandler(oidcProvider));
        handler.addMapping(new RegexPathSpec(OIDCMockProvider.OIDC_LOGOUT_PATH), new LogoutHandler(oidcProvider));
        server.setHandler(handler);

        server.start();
    }

    public void stop() throws Exception {
        this.server.stop();
    }
}
