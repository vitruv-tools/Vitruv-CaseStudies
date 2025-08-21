package tools.vitruv.applications.demo.performance.configs.server;

import tools.vitruv.applications.demo.performance.configs.MeasuredVsumInitializer;
import tools.vitruv.framework.remote.server.VitruvServer;
import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.framework.remote.server.VitruviusServer;

public class ServerOriginalConfigurator implements ServerSupplier {
    private VitruvServerConfiguration serverConfig;

    public ServerOriginalConfigurator(VitruvServerConfiguration serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public VitruviusServer get() throws Exception {
        var vitruvServer = this.createVitruviusServer(this.serverConfig);
        vitruvServer.initialize(new MeasuredVsumInitializer());
        vitruvServer.start();
        return vitruvServer;
    }

    VitruviusServer createVitruviusServer(VitruvServerConfiguration serverConfig) {
        return new VitruvServer(serverConfig);
    }
}
