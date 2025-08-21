package tools.vitruv.applications.demo.performance.configs;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    private ExecutorService parallelExecutor = Executors.newFixedThreadPool(1);
    private Future<?> currentAwaitedResult;

    public VitruvClientController(TlsContextConfiguration tslConfig, PerformanceDataContainer dataContainer, Path vsumDir) {
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_ORIGINAL, new ClientOriginalConfigurator());
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_JETTY_HTTP11, new ClientJettyConfigurator(AvailableHttpVersions.HTTP_1_1));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_JETTY_HTTP2, new ClientJettyConfigurator(AvailableHttpVersions.HTTP_2));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP11, new ClientSecurityConfigurator(tslConfig, AvailableHttpVersions.HTTP_1_1));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP2, new ClientSecurityConfigurator(tslConfig, AvailableHttpVersions.HTTP_2));
        this.configToClientSupplier.put(ConfigNames.CONFIG_CLIENT_SECURITY_JETTY_HTTP3, new ClientSecurityConfigurator(tslConfig, AvailableHttpVersions.HTTP_3));

        this.executor = new ClientExecutor(vsumDir, dataContainer);
    }

    public void startClient(String configName, String serverConfig, String uri) throws Exception {
        if (this.isClientRunning()) {
            throw new IllegalStateException("Client already running.");
        }

        var supplier = this.configToClientSupplier.get(configName);
        if (supplier == null) {
            throw new IllegalArgumentException("Could not find client configuration.");
        }

        var wrapper = supplier.get();
        this.currentAwaitedResult = this.parallelExecutor.submit(() -> this.executor.executeMeasurements(uri, wrapper, serverConfig, configName));
    }

    public double getProgress() {
        return this.executor.getProgress();
    }

    public boolean isClientRunning() {
        return this.currentAwaitedResult != null && !this.currentAwaitedResult.isDone();
    }

    public void waitForCompletion() {
        try {
            this.currentAwaitedResult.get();
        } catch (InterruptedException | ExecutionException e) {
        }
    }
}
