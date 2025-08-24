package tools.vitruv.applications.demo.performance.configs;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import tools.vitruv.applications.demo.performance.common.ProgressUtility;
import tools.vitruv.applications.demo.performance.configs.client.ClientExecutor;
import tools.vitruv.applications.demo.performance.configs.client.ClientJettyConfigurator;
import tools.vitruv.applications.demo.performance.configs.client.ClientOriginalConfigurator;
import tools.vitruv.applications.demo.performance.configs.client.ClientSecurityConfigurator;
import tools.vitruv.applications.demo.performance.configs.client.ClientSupplier;
import tools.vitruv.applications.demo.performance.data.PerformanceDataContainer;
import tools.vitruv.framework.remote.common.AvailableHttpVersions;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;

public class VitruvClientController {
    private Map<String, ClientSupplier> configToClientSupplier = new HashMap<>();
    private ClientExecutor executor;

    public VitruvClientController(TlsContextConfiguration localTlsConfig, TlsContextConfiguration remoteTlsConfig, PerformanceDataContainer dataContainer, Path vsumDir) {
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_ORIGINAL, new ClientOriginalConfigurator());
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_JETTY_HTTP11, new ClientJettyConfigurator(AvailableHttpVersions.HTTP_1_1));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_JETTY_HTTP2, new ClientJettyConfigurator(AvailableHttpVersions.HTTP_2));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP11_LOCAL, new ClientSecurityConfigurator(localTlsConfig, AvailableHttpVersions.HTTP_1_1));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP2_LOCAL, new ClientSecurityConfigurator(localTlsConfig, AvailableHttpVersions.HTTP_2));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP3_LOCAL, new ClientSecurityConfigurator(localTlsConfig, AvailableHttpVersions.HTTP_3));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP11, new ClientSecurityConfigurator(remoteTlsConfig, AvailableHttpVersions.HTTP_1_1));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP2, new ClientSecurityConfigurator(remoteTlsConfig, AvailableHttpVersions.HTTP_2));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP3, new ClientSecurityConfigurator(remoteTlsConfig, AvailableHttpVersions.HTTP_3));

        this.executor = new ClientExecutor(vsumDir, dataContainer);
    }

    public void excuteClient(String configName, String serverConfig, String uri) throws Exception {
        if (this.isClientRunning()) {
            throw new IllegalStateException("Client already running.");
        }

        var supplier = this.configToClientSupplier.get(configName);
        if (supplier == null) {
            throw new IllegalArgumentException("Could not find client configuration.");
        }

        var wrapper = supplier.get();
        this.executor.executeMeasurements(uri, wrapper, serverConfig, configName);
    }

    public double getProgress() {
        return this.executor.getProgress();
    }

    public boolean isClientRunning() {
        return this.executor.getProgress() < ProgressUtility.DEFAULT_NO_PROGRESS_VALUE;
    }
}
