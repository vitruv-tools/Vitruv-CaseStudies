package tools.vitruv.applications.demo.performance.configs;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

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
    private Path vsumDir;
    
    public VitruvServerController(
        VitruvServerConfiguration serverConfig,
        TlsContextConfiguration tslConfig,
        String oidcUri,
        Path vsumDir
    ) {
        this.configToSupplier.put(ConfigNames.CONFIG_SERVER_SECURITY2_DIRECT_MODE, new ServerSecurity2Configurator(serverConfig, tslConfig, oidcUri, vsumDir, true));
        this.configToSupplier.put(ConfigNames.CONFIG_SERVER_SECURITY2_PROXY_MODE, new ServerSecurity2Configurator(serverConfig, tslConfig, oidcUri, vsumDir, false));
        this.configToSupplier.put(ConfigNames.CONFIG_SERVER_ORIGINAL, new ServerOriginalConfigurator(serverConfig, vsumDir));
        this.configToSupplier.put(ConfigNames.CONFIG_SERVER_JETTY, new ServerJettyConfigurator(serverConfig, vsumDir));

        this.vsumDir = vsumDir;
    }

    public String startServer(String configName) throws Exception {
        if (this.isServerRunning()) {
            throw new IllegalStateException("A server is already running. Cannot start another.");
        }

        var supplier = this.configToSupplier.get(configName);
        if (supplier == null) {
            throw new IllegalArgumentException("Configuration not found.");
        }
        
        this.currentRunningServer = supplier.get();
        this.currentRunningConfig = configName;
        return this.currentRunningServer.getBaseUrl();
    }

    public void stopServer() throws Exception {
        if (!this.isServerRunning()) {
            throw new IllegalStateException("Cannot stop server, which is not running.");
        }
        this.currentRunningServer.stop();
        this.currentRunningServer = null;
        this.currentRunningConfig = null;
        FileUtils.deleteDirectory(this.vsumDir.toFile());
    }

    public boolean isServerRunning() {
        return this.currentRunningServer != null;
    }

    public boolean isServerRunning(String configName) {
        return configName.equals(this.currentRunningConfig) && this.currentRunningServer != null;
    }
}
