package tools.vitruv.applications.demo.performance.configs.exchange;

public class StartConfigurationSetting {
    private String serverConfig;
    private String[] clientConfig;
    private String communication;
    private String serverUri;

    public String getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(String serverConfig) {
        this.serverConfig = serverConfig;
    }

    public String[] getClientConfigs() {
        return clientConfig;
    }

    public void setClientConfig(String[] clientConfig) {
        this.clientConfig = clientConfig;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getServerUri() {
        return serverUri;
    }

    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }
}
