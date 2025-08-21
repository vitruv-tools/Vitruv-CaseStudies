package tools.vitruv.applications.demo.performance.configs.client;

import tools.vitruv.framework.remote.client.http.VitruvHttpClientWrapper;
import tools.vitruv.framework.remote.common.AvailableHttpVersions;
import tools.vitruv.remote.secclient.SecurityJettyHttpClientWrapper;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;

public class ClientSecurityConfigurator implements ClientSupplier {
    private TlsContextConfiguration tslConfig;
    private AvailableHttpVersions httpVersion;

    public ClientSecurityConfigurator(TlsContextConfiguration tlsConfig, AvailableHttpVersions httpVersion) {
        this.tslConfig = tlsConfig;
        this.httpVersion = httpVersion;
    }

    @Override
    public VitruvHttpClientWrapper get() throws Exception {
        var wrapper = new SecurityJettyHttpClientWrapper();
        wrapper.setConfiguration(this.tslConfig);
        wrapper.setFixedVersion(this.httpVersion);
        wrapper.initialize();
        return wrapper;
    }
}
