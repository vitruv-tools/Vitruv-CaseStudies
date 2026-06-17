package tools.vitruv.applications.demo.performance.configs.client;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import tools.vitruv.applications.demo.DemoUtility;
import tools.vitruv.applications.demo.FamiliesPersonsVsumWrapper;
import tools.vitruv.applications.demo.performance.RandomFamiliesModelGenerator;
import tools.vitruv.applications.demo.performance.common.FamilyModelGenerationParameters;
import tools.vitruv.applications.demo.performance.common.ProgressUtility;
import tools.vitruv.applications.demo.performance.configs.exchange.ConfigNames;
import tools.vitruv.applications.demo.performance.configs.exchange.StartConfigurationSetting;
import tools.vitruv.applications.demo.performance.configs.exchange.StartConfigurationSetting.RepeatedModelGenerationConfiguration;
import tools.vitruv.applications.demo.performance.data.PerformanceDataContainer;
import tools.vitruv.framework.remote.client.VitruvClientFactory;
import tools.vitruv.framework.remote.client.http.VitruvHttpClientWrapper;
import tools.vitruv.framework.remote.client.http.VitruvHttpRequest;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.remote.secserver.handler.ApiPaths;

public class MeasurementExecutionEngine {
    private static record InternalViewTypeSourcePair(ViewType<?> viewType, ChangeableViewSource viewSource) {}

    private Path vsumDir;
    private PerformanceDataContainer dataContainer;
    private double progress = ProgressUtility.DEFAULT_NO_PROGRESS_VALUE;
    private String serverConfig;
    private String clientConfig;
    
    public MeasurementExecutionEngine(Path vsumDir, PerformanceDataContainer dataContainer) {
        this.vsumDir = vsumDir;
        this.dataContainer = dataContainer;
    }

    public double getProgress() {
        return this.progress;
    }

    public void executeLocalMeasurements(StartConfigurationSetting setting) {
        var wrapper = new FamiliesPersonsVsumWrapper();
        try {
            wrapper.initialize(this.vsumDir);
        } catch (Exception e) {
            throw new IllegalStateException("Could not initialize the VSUM.", e);
        }
        
        var familyViewType = wrapper.getViewType(DemoUtility.FAMILIES_VIEW_TYPE_NAME);
        var viewPair = new InternalViewTypeSourcePair(familyViewType, (ChangeableViewSource) wrapper.getVsum());
        this.progress = 0.0;
        this.serverConfig = setting.getServerConfig();
        this.clientConfig = setting.getServerConfig();

        try {
            System.out.println("--- Starting with local measurements. ---");
            if (setting.isRandomExecution()) {
                this.executeMeasurementsRandomly(viewPair, setting.getGenerationConfigurations());
            } else {
                this.executeMeasurementsNonRandomly(viewPair, setting.getGenerationConfigurations());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not measure local performance.");
            this.dataContainer.save();
        }
        
        wrapper.close();
        try {
            FileUtils.deleteDirectory(this.vsumDir.toFile());
        } catch (IOException e) {
        }

        this.progress = ProgressUtility.DEFAULT_NO_PROGRESS_VALUE;
    }

    public void executeClientMeasurements(String serverUri, VitruvHttpClientWrapper clientWrapper, StartConfigurationSetting settings, String clientConfig) {
        this.progress = 0.0;
        this.serverConfig = settings.getServerConfig();
        this.clientConfig = clientConfig;

        if (serverUri.startsWith("https")) {
            try {
                VitruvHttpRequest.GET(serverUri + ApiPaths.OPENID_LOGIN_PATH).sendRequest(clientWrapper);
            } catch (Exception e) {
                throw new IllegalStateException("Could not establish authentication.", e);
            }
        }

		var client = VitruvClientFactory.create(serverUri, clientWrapper, this.vsumDir);
		
		var viewTypes = client.getViewTypes();

		var familiesViewType = viewTypes.stream().filter(vt -> vt.getName().equals(DemoUtility.FAMILIES_VIEW_TYPE_NAME)).findAny();
		if (familiesViewType.isEmpty()) {
			throw new IllegalStateException("Looked for the families view tpye, but did not find it.");
		}
        var viewPair = new InternalViewTypeSourcePair(familiesViewType.get(), null);
		
        try {
            System.out.println("--- Starting measures with Vitruvius client ---");
            if (settings.isRandomExecution()) {
                this.executeMeasurementsRandomly(viewPair, settings.getGenerationConfigurations());
            } else {
                this.executeMeasurementsNonRandomly(viewPair, settings.getGenerationConfigurations());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not execute all client measurements.");
            this.dataContainer.save();
        } finally {
            try {
                clientWrapper.disconnect();
            } catch (Exception e) {
            }
        }

        try {
            FileUtils.deleteDirectory(this.vsumDir.toFile());
        } catch (IOException e) {
        }

        this.progress = ProgressUtility.DEFAULT_NO_PROGRESS_VALUE;
    }

    private void executeMeasurementsNonRandomly(InternalViewTypeSourcePair viewPair, List<RepeatedModelGenerationConfiguration> genParams) throws Exception {
        for (var config : genParams) {
            for (var round = 0; round < config.getRepetitions(); round++) {
                this.measurePerformance(viewPair, config.getGenerationParams());
                this.progress = ((double) round / config.getRepetitions()) / genParams.size();
            }
        }
    }

    private void executeMeasurementsRandomly(InternalViewTypeSourcePair viewPair, List<RepeatedModelGenerationConfiguration> genParams) throws Exception {
        var openForGeneration = new ArrayList<>(genParams);
        var generationCounters = new HashMap<RepeatedModelGenerationConfiguration, Integer>();
        openForGeneration.forEach(param -> generationCounters.put(param, 0));
        var noAllRepetitions = openForGeneration.parallelStream().mapToInt(param -> param.getRepetitions()).sum();

        while (openForGeneration.size() > 0) {
            var nextIdx = (int) Math.floor(Math.random() * openForGeneration.size());
            var nextParam = openForGeneration.get(nextIdx);

            this.measurePerformance(viewPair, nextParam.getGenerationParams());

            var counter = generationCounters.get(nextParam);
            if (counter + 1 == nextParam.getRepetitions()) {
                openForGeneration.remove(nextIdx);
                generationCounters.remove(nextParam);
            } else {
                generationCounters.put(nextParam, counter + 1);
            }
            this.progress += 1.0 / noAllRepetitions;
        }
    }

    private void measurePerformance(InternalViewTypeSourcePair viewPair, FamilyModelGenerationParameters params) throws Exception {
        var familySelector = viewPair.viewType().createSelector(viewPair.viewSource());
        familySelector.getSelectableElements().forEach((element) -> familySelector.setSelected(element, true));
        var familyView = familySelector.createView().withChangeRecordingTrait();
        var familyRegister = familyView.getRootObjects(FamilyRegister.class).stream().findAny();
        if (familyRegister.isEmpty()) {
            throw new IllegalStateException("Did not find a FamilyRegister.");
		}
			
        long cTime = System.currentTimeMillis();
        RandomFamiliesModelGenerator.createFamilies(familyRegister.get(), params);
        cTime = System.currentTimeMillis() - cTime;
			
        long pTime = System.currentTimeMillis();
        familyView.commitChanges();
        pTime = System.currentTimeMillis() - pTime;
			
        familyRegister.get().getFamilies().clear();
        familyView.commitChanges();
			
		familyView.close();
			
		System.out.format("%d ms (creation), %d ms (propagation), %f %% %n", cTime, pTime, this.progress);
		this.dataContainer.addData(this.serverConfig, this.clientConfig, ConfigNames.COMMUNICATION, params, cTime, pTime);
        this.dataContainer.save();
	}
}
