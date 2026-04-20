package tools.vitruv.applications.demo.performance.configs.server;

import java.io.IOException;
import java.nio.file.Path;

import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.secserver.config.ConfigManager;

public class PerformanceSecurityServerConfigManager extends ConfigManager {
    private VitruvServerConfiguration secureServerConfig;
    private VitruvServerConfiguration serverConfig;
    private TlsContextConfiguration tlsConfig;
    private String oidcUri;
    private Path certPath;
    private Path keyPath;

    public PerformanceSecurityServerConfigManager(
        VitruvServerConfiguration secureServerConfig,
        VitruvServerConfiguration serverConfig,
        TlsContextConfiguration tlsConfig,
        String oidcUri
    ) {
        this.secureServerConfig = secureServerConfig;
        this.serverConfig = serverConfig;
        this.tlsConfig = tlsConfig;
        this.oidcUri = oidcUri;
        this.certPath = tlsConfig.tempCertDir().resolve("cert-security-server.pem");
        this.keyPath = tlsConfig.tempCertDir().resolve("key-security-server.pem");
        // TODO: Create these files.
    }

    @Override
    public void initialize() throws IOException {
    }

    @Override
    public int getVitruvServerPort() {
        return this.serverConfig.port();
    }

    @Override
    public int getHttpsServerPort() {
        return this.secureServerConfig.port();
    }

    @Override
    public String getDomainProtocol() {
        return "https";
    }

    @Override
    public String getDomainName() {
        return this.secureServerConfig.hostOrIp();
    }

    @Override
    public String getCertChainPath() {
        return this.certPath.toString();
    }

    @Override
    public String getCertKeyPath() {
        return this.keyPath.toString();
    }

    @Override
    public String getOidcClientId() {
        return "42";
    }

    @Override
    public String getOidcClientSecret() {
        return this.tlsConfig.keyStorePassword();
    }

    @Override
    public String getTlsPassword() {
        return this.tlsConfig.keyStorePassword();
    }

    @Override
    public String getOIDCDiscoveryUri() {
        return this.oidcUri + "/.well-known/openid-configuration";
    }
}
