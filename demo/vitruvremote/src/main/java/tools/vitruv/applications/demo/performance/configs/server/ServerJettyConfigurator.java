package tools.vitruv.applications.demo.performance.configs.server;

import java.nio.file.Path;

import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.framework.remote.server.VitruviusServer;
import tools.vitruv.framework.remote.server.jetty.JettyVitruvServer;

public class ServerJettyConfigurator extends ServerOriginalConfigurator {
    public ServerJettyConfigurator(VitruvServerConfiguration serverConfig, Path vsumDir) {
        super(serverConfig, vsumDir);
    }
    
    @Override
    VitruviusServer createVitruviusServer(VitruvServerConfiguration serverConfig) {
        return new JettyVitruvServer(serverConfig);
    }
}
