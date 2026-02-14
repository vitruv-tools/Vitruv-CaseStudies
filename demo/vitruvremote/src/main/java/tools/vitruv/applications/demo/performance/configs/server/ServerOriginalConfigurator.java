package tools.vitruv.applications.demo.performance.configs.server;

import java.nio.file.Path;

import tools.vitruv.applications.demo.performance.configs.MeasuredVsumInitializer;
import tools.vitruv.framework.remote.server.VitruvServer;
import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.framework.remote.server.VitruviusServer;

public class ServerOriginalConfigurator implements ServerSupplier {
    private VitruvServerConfiguration serverConfig;
    private Path vsumDir;

    public ServerOriginalConfigurator(VitruvServerConfiguration serverConfig, Path vsumDir) {
        this.serverConfig = serverConfig;
        this.vsumDir = vsumDir;
    }

    @Override
    public VitruviusServer get() throws Exception {
        var vitruvServer = this.createVitruviusServer(this.serverConfig);
        vitruvServer.initialize(new MeasuredVsumInitializer(this.vsumDir));
        vitruvServer.start();
        return vitruvServer;
    }

    VitruviusServer createVitruviusServer(VitruvServerConfiguration serverConfig) {
        return new VitruvServer(serverConfig);
    }
}
