package tools.vitruv.applications.demo.performance.configs;

import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.framework.remote.server.VitruviusServer;
import tools.vitruv.framework.remote.server.jetty.JettyVitruvServer;

public class ServerJettyConfigurator extends ServerOriginalConfigurator {
    public ServerJettyConfigurator(VitruvServerConfiguration serverConfig) {
        super(serverConfig);
    }
    
    @Override
    VitruviusServer createVitruviusServer(VitruvServerConfiguration serverConfig) {
        return new JettyVitruvServer(serverConfig);
    }
}
