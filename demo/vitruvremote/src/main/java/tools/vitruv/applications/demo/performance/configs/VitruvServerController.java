package tools.vitruv.applications.demo.performance.configs;

import java.util.HashMap;
import java.util.Map;

import tools.vitruv.applications.demo.performance.configs.server.ServerJettyConfigurator;
import tools.vitruv.applications.demo.performance.configs.server.ServerOriginalConfigurator;
import tools.vitruv.applications.demo.performance.configs.server.ServerSecurity2Configurator;
import tools.vitruv.applications.demo.performance.configs.server.ServerSupplier;
import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.framework.remote.server.VitruviusServer;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;

public class VitruvServerController {
    private Map<String, ServerSupplier> configToSupplier = new HashMap<>();
    private String currentRunningConfig;
    private VitruviusServer currentRunningServer;
    
    public VitruvServerController(
        VitruvServerConfiguration serverConfig,
        TlsContextConfiguration tslConfig,
        String oidcUri
    ) {
        this.configToSupplier.put(ConfigNames.CONFIG_SERVER_SECURITY2_PROXY_MODE, new ServerSecurity2Configurator(serverConfig, tslConfig, oidcUri, true));
        this.configToSupplier.put(ConfigNames.CONFIG_SERVER_SECURITY2_PROXY_MODE, new ServerSecurity2Configurator(serverConfig, tslConfig, oidcUri, false));
        this.configToSupplier.put(ConfigNames.CONFIG_SERVER_ORIGINAL, new ServerOriginalConfigurator(serverConfig));
        this.configToSupplier.put(ConfigNames.CONFIG_SERVER_JETTY, new ServerJettyConfigurator(serverConfig));
    }

    public void startServer(String configName) throws Exception {
        if (this.isServerRunning()) {
            throw new IllegalStateException("A server is already running. Cannot start another.");
        }
        var supplier = this.configToSupplier.get(configName);
        if (supplier == null) {
            throw new IllegalArgumentException("Configuration not found.");
        }
        this.currentRunningServer = supplier.get();
        this.currentRunningConfig = configName;
    }

    public void stopServer() throws Exception {
        if (!this.isServerRunning()) {
            throw new IllegalStateException("Cannot stop server, which is not running.");
        }
        this.currentRunningServer.stop();
        this.currentRunningServer = null;
        this.currentRunningConfig = null;
    }

    public boolean isServerRunning() {
        return this.currentRunningServer != null;
    }

    public boolean isServerRunning(String configName) {
        return configName.equals(this.currentRunningConfig) && this.currentRunningServer != null;
    }
}
