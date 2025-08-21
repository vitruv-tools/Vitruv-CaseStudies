package tools.vitruv.applications.demo.performance.configs;

import tools.vitruv.framework.remote.client.http.VitruvHttpClientWrapper;
import tools.vitruv.framework.remote.client.http.VitruvHttpRequest;
import tools.vitruv.framework.remote.common.AvailableHttpVersions;
import tools.vitruv.remote.secclient.SecurityJettyHttpClientWrapper;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.secserver.handler.ApiPaths;

public class ClientSecurityConfigurator implements ClientSupplier {
    private TlsContextConfiguration tslConfig;
    private AvailableHttpVersions httpVersion;
    private String uri;

    public ClientSecurityConfigurator(TlsContextConfiguration tlsConfig, AvailableHttpVersions httpVersion, String uri) {
        this.tslConfig = tlsConfig;
        this.httpVersion = httpVersion;
        this.uri = uri;
    }

    @Override
    public VitruvHttpClientWrapper get() throws Exception {
        var wrapper = new SecurityJettyHttpClientWrapper();
        wrapper.setConfiguration(this.tslConfig);
        wrapper.setFixedVersion(this.httpVersion);
        wrapper.initialize();
        VitruvHttpRequest.GET(this.uri + ApiPaths.OPENID_LOGIN_PATH).sendRequest(wrapper);
        return wrapper;
    }
}
