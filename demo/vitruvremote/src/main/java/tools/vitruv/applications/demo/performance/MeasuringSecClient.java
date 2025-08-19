package tools.vitruv.applications.demo.performance;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import tools.vitruv.applications.demo.DemoUtility;
import tools.vitruv.applications.demo.performance.RandomFamiliesModelGenerator.FamilyModelGenerationParameters;
import tools.vitruv.applications.demo.secremote.SecUtility;
import tools.vitruv.framework.remote.client.VitruvClientFactory;
import tools.vitruv.framework.remote.client.http.VitruvHttpRequest;
import tools.vitruv.framework.remote.common.AvailableHttpVersions;
import tools.vitruv.framework.remote.common.apm.VitruvApmController;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.remote.secclient.SecurityJettyHttpClientWrapper;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.secserver.handler.ApiPaths;

public class MeasuringSecClient {

	public static void main(String[] args) throws Exception {
		var root = Paths.get("target");
		VitruvApmController.enable(root.resolve(OutputConstants.SEC_CLIENT_APM_FILE_NAME));
		var tempRoot = root.resolve("temp");
		DemoUtility.registerFactories();
		
		var certProperties = SecUtility.initializeCertificates(root, false);
		var tempCertDir = root.resolve("temp-cert-client");
		Files.createDirectories(tempCertDir);
		SecurityJettyHttpClientWrapper clientWrapper = new SecurityJettyHttpClientWrapper();
		clientWrapper.setConfiguration(
			new TlsContextConfiguration(
				null,
				null,
				certProperties.password(),
				certProperties.trustStorePath(),
				null,
				certProperties.password(),
				tempCertDir
			)
		);
		clientWrapper.initialize();
		
		var serverUri = String.format("https://%s:%d", DemoUtility.SERVER_HOST, DemoUtility.SERVER_PORT);

		// Perform initial request for login with OpenID Connect.
		VitruvHttpRequest.GET(serverUri + ApiPaths.OPENID_LOGIN_PATH).sendRequest(clientWrapper);

		// Per default, the HTTP client uses HTTP/2 or HTTP/1.1. To use HTTP/3, it must be explicitly set.
		// However, this can only be done after the OpenID Connect login. Otherwise, it also tries to contact
		// the OpenID Connect provider with HTTP/3, which is not supported by the mock server.
		clientWrapper.setFixedVersion(AvailableHttpVersions.HTTP_2);
		
		// Create the actual client.
		var client = VitruvClientFactory.create(serverUri, clientWrapper, tempRoot);
		
		var viewTypes = client.getViewTypes();

		var familiesViewType = viewTypes.stream().filter(vt -> vt.getName().equals(DemoUtility.FAMILIES_VIEW_TYPE_NAME)).findAny();
		if (familiesViewType.isEmpty()) {
			throw new IllegalStateException("Looked for the families view tpye, but did not find it.");
		}
		
		var outputPrefix = OutputConstants.SEC_CLIENT_DATA_FILE_NAME_PREFIX + Configuration.HOST_NAME_OR_IP;
		System.out.println("--- Small configuration with Vitruvius security client ---");
		measureSecClientPerformance(Configuration.REPETITIONS_SMALL, familiesViewType.get(),
				root.resolve(outputPrefix + OutputConstants.CLIENT_DATA_FILE_NAME_SMALL), Configuration.SMALL_MODEL_PARAMETERS);
		System.out.println("--- Medium configuration with local Vitruvius security client ---");
		measureSecClientPerformance(Configuration.REPETITIONS_MEDIUM, familiesViewType.get(),
				root.resolve(outputPrefix + OutputConstants.CLIENT_DATA_FILE_NAME_MEDIUM), Configuration.MEDIUM_MODEL_PARAMETERS);
		System.out.println("--- Large configuration with local Vitruvius security client ---");
		measureSecClientPerformance(Configuration.REPETITIONS_LARGE, familiesViewType.get(),
				root.resolve(outputPrefix + OutputConstants.CLIENT_DATA_FILE_NAME_LARGE), Configuration.LARGE_MODEL_PARAMETERS);
		
		
		System.out.println("Please do not stop this process. We are waiting one minute to ensure that all relevant data is written. Afterward, we stop automatically.");
		Thread.sleep(60000); // 60,000 ms = 1 min
		System.out.println("Thank you.");

		client.disconnect();

	}
	public static void measureSecClientPerformance(int repetitions, ViewType<?> familyViewType, Path output, FamilyModelGenerationParameters params) throws Exception {
		var data = new PerformanceDataContainer();
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
			data.addExternalData(new ExternalPerformanceData(cTime, pTime));
		}
	}
}
