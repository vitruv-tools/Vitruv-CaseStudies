package tools.vitruv.applications.demo.performance.configs.server;

import tools.vitruv.framework.remote.server.VitruviusServer;

@FunctionalInterface()
public interface ServerSupplier {
    VitruviusServer get() throws Exception;
}
