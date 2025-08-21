package tools.vitruv.applications.demo.performance.configs;

import tools.vitruv.framework.remote.client.http.VitruvHttpClientWrapper;

@FunctionalInterface()
public interface ClientSupplier {
    VitruvHttpClientWrapper get() throws Exception;
}
