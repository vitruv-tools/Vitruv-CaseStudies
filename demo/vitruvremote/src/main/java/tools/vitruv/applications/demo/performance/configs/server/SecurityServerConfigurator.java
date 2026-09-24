package tools.vitruv.applications.demo.performance.configs.server;

import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.framework.remote.server.VitruviusServer;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.secserver.VitruvSecurityServer;

public class SecurityServerConfigurator implements ServerSupplier {
    private PerformanceSecurityServerConfigManager configManager;

    public SecurityServerConfigurator(
        VitruvServerConfiguration secureServerConfig,
        VitruvServerConfiguration serverConfig,
        TlsContextConfiguration tlsConfig,
        String oidcUri
    ) {
        this.configManager = new PerformanceSecurityServerConfigManager(secureServerConfig, serverConfig, tlsConfig, oidcUri);
    }

    @Override
    public VitruviusServer get() throws Exception {
        return new VitruvSecurityServer(this.configManager);
    }
}
