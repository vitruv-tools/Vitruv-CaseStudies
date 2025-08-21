package tools.vitruv.applications.demo.performance.configs;

import tools.vitruv.framework.remote.server.VitruviusServer;

@FunctionalInterface()
public interface ServerSupplier {
    VitruviusServer get() throws Exception;
}
