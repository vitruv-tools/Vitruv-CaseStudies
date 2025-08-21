package tools.vitruv.applications.demo.performance.configs;

import tools.vitruv.framework.remote.client.http.JavaHttpClientWrapper;
import tools.vitruv.framework.remote.client.http.VitruvHttpClientWrapper;

public class ClientOriginalConfigurator implements ClientSupplier {
    @Override
    public VitruvHttpClientWrapper get() throws Exception {
        return new JavaHttpClientWrapper();
    }
}
