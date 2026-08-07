package tools.vitruv.applications.demo.performance.configs.exchange;

import java.util.ArrayList;
import java.util.List;

import tools.vitruv.applications.demo.performance.common.FamilyModelGenerationParameters;

public class StartConfigurationSetting {
    public static class RepeatedModelGenerationConfiguration {
        private int repetitions;
        private FamilyModelGenerationParameters generationParams;

        public RepeatedModelGenerationConfiguration(int repetitions, FamilyModelGenerationParameters generationParams) {
            this.repetitions = repetitions;
            this.generationParams = generationParams;
        }

        public int getRepetitions() {
            return repetitions;
        }

        public FamilyModelGenerationParameters getGenerationParams() {
            return generationParams;
        }
    }

    private String serverConfig;
    private String[] clientConfig;
    private String communication;
    private String serverUri;
    private List<RepeatedModelGenerationConfiguration> generationConfigurations = new ArrayList<>();
    private boolean randomExecution;

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

    public List<RepeatedModelGenerationConfiguration> getGenerationConfigurations() {
        return generationConfigurations;
    }

    public boolean isRandomExecution() {
        return randomExecution;
    }

    public void setRandomExecution(boolean randomExecution) {
        this.randomExecution = randomExecution;
    }
}
