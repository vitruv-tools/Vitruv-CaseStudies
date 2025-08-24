package tools.vitruv.applications.demo.performance.configs.client;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import tools.vitruv.applications.demo.DemoUtility;
import tools.vitruv.applications.demo.performance.Configuration;
import tools.vitruv.applications.demo.performance.RandomFamiliesModelGenerator;
import tools.vitruv.applications.demo.performance.common.FamilyModelGenerationParameters;
import tools.vitruv.applications.demo.performance.common.ProgressUtility;
import tools.vitruv.applications.demo.performance.configs.ConfigNames;
import tools.vitruv.applications.demo.performance.data.PerformanceDataContainer;
import tools.vitruv.framework.remote.client.VitruvClientFactory;
import tools.vitruv.framework.remote.client.http.VitruvHttpClientWrapper;
import tools.vitruv.framework.remote.client.http.VitruvHttpRequest;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.remote.secserver.handler.ApiPaths;

public class ClientExecutor {
    private Path vsumDir;
    private PerformanceDataContainer dataContainer;
    private double progress = ProgressUtility.DEFAULT_NO_PROGRESS_VALUE;
    private String serverConfig;
    private String clientConfig;
    
    public ClientExecutor(Path vsumDir, PerformanceDataContainer dataContainer) {
        this.vsumDir = vsumDir;
        this.dataContainer = dataContainer;
    }

    public double getProgress() {
        return this.progress;
    }

    public void executeMeasurements(String serverUri, VitruvHttpClientWrapper clientWrapper, String serverConfig, String clientConfig) {
        this.progress = 0.0;
        this.serverConfig = serverConfig;
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
		
        try {
            System.out.println("--- Small configuration with Vitruvius client ---");
            measureClientPerformance(Configuration.REPETITIONS_SMALL, familiesViewType.get(),
                Configuration.SMALL_MODEL_PARAMETERS);
            System.out.println("--- Medium configuration with local Vitruvius security client ---");
            measureClientPerformance(Configuration.REPETITIONS_MEDIUM, familiesViewType.get(),
                Configuration.MEDIUM_MODEL_PARAMETERS);
            System.out.println("--- Large configuration with local Vitruvius security client ---");
            measureClientPerformance(Configuration.REPETITIONS_LARGE, familiesViewType.get(),
                Configuration.LARGE_MODEL_PARAMETERS);
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

        this.progress = 1.5;
    }

    private void measureClientPerformance(int repetitions, ViewType<?> familyViewType, FamilyModelGenerationParameters params) throws Exception {
		for (int rIdx = 0; rIdx < repetitions; rIdx++) {
			System.out.println("Round " + rIdx);
			
            var familySelector = familyViewType.createSelector(null);
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
			
			System.out.format("%d ms (creation), %d ms (propagation)%n", cTime, pTime);
			this.dataContainer.addData(this.serverConfig, this.clientConfig, ConfigNames.COMMUNICATION, cTime, pTime);
            this.progress += rIdx / repetitions / 3;
		}
        this.dataContainer.save();
	}
}
