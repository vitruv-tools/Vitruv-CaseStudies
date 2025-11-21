package tools.vitruv.applications.demo.performance.configs;

import java.util.HashMap;
import java.util.Map;

import tools.vitruv.applications.demo.performance.common.ProgressUtility;
import tools.vitruv.applications.demo.performance.configs.client.ClientJettyConfigurator;
import tools.vitruv.applications.demo.performance.configs.client.ClientOriginalConfigurator;
import tools.vitruv.applications.demo.performance.configs.client.ClientSecurityConfigurator;
import tools.vitruv.applications.demo.performance.configs.client.ClientSupplier;
import tools.vitruv.applications.demo.performance.configs.client.MeasurementExecutionEngine;
import tools.vitruv.applications.demo.performance.configs.exchange.ConfigNames;
import tools.vitruv.applications.demo.performance.configs.exchange.StartConfigurationSetting;
import tools.vitruv.framework.remote.common.AvailableHttpVersions;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;

public class VitruvClientController {
    private Map<String, ClientSupplier> configToClientSupplier = new HashMap<>();
    private MeasurementExecutionEngine executionEngine;

    public VitruvClientController(TlsContextConfiguration tlsConfig, MeasurementExecutionEngine executionEngine) {
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_ORIGINAL, new ClientOriginalConfigurator());
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_JETTY_HTTP11, new ClientJettyConfigurator(AvailableHttpVersions.HTTP_1_1));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_JETTY_HTTP2, new ClientJettyConfigurator(AvailableHttpVersions.HTTP_2));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP11, new ClientSecurityConfigurator(tlsConfig, AvailableHttpVersions.HTTP_1_1));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP2, new ClientSecurityConfigurator(tlsConfig, AvailableHttpVersions.HTTP_2));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP3, new ClientSecurityConfigurator(tlsConfig, AvailableHttpVersions.HTTP_3));

        this.executionEngine = executionEngine;
    }

    public void excuteClient(StartConfigurationSetting setting, String clientConfig, String uri) throws Exception {
        if (this.isClientRunning()) {
            throw new IllegalStateException("Client already running.");
        }

        var supplier = this.configToClientSupplier.get(clientConfig);
        if (supplier == null) {
            throw new IllegalArgumentException("Could not find client configuration.");
        }

        var wrapper = supplier.get();
        this.executionEngine.executeClientMeasurements(uri, wrapper, setting, clientConfig);
    }

    public double getProgress() {
        return this.executionEngine.getProgress();
    }

    public boolean isClientRunning() {
        return this.executionEngine.getProgress() < ProgressUtility.DEFAULT_NO_PROGRESS_VALUE;
    }
}
