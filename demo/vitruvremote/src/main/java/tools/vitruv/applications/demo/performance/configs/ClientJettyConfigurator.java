package tools.vitruv.applications.demo.performance.configs;

import tools.vitruv.framework.remote.client.http.VitruvHttpClientWrapper;
import tools.vitruv.framework.remote.client.jetty.JettyHttpClientWrapper;
import tools.vitruv.framework.remote.common.AvailableHttpVersions;

public class ClientJettyConfigurator implements ClientSupplier {
    private AvailableHttpVersions httpVersion;

    public ClientJettyConfigurator(AvailableHttpVersions httpVersion) {
        this.httpVersion = httpVersion;
    }

    @Override
    public VitruvHttpClientWrapper get() throws Exception {
        var wrapper = new JettyHttpClientWrapper();
        wrapper.setFixedVersion(this.httpVersion);
        wrapper.initialize();
        return wrapper;
    }
}
