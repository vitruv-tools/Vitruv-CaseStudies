package tools.vitruv.applications.demo.secremote;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import tools.vitruv.applications.demo.DemoUtility;
import tools.vitruv.framework.remote.client.VitruvClientFactory;
import tools.vitruv.framework.remote.client.http.VitruvHttpRequest;
import tools.vitruv.framework.remote.common.AvailableHttpVersions;
import tools.vitruv.remote.secclient.SecurityJettyHttpClientWrapper;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.secserver.handler.ApiPaths;

public class DemoSecClient {
	private static final Path TEMPORARY_PATH = Paths.get("target", "vitruv-temp").toAbsolutePath();
	
	public static void main(String[] args) throws Exception {
		// Register the meta models used in the VSUM at the server side.
		DemoUtility.registerFactories();

		var rootDir = Paths.get("target");
		var certProperties = SecUtility.initializeCertificates(rootDir, false);
		var tempCertDir = rootDir.resolve("temp-cert-client");
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
		clientWrapper.setFixedVersion(AvailableHttpVersions.HTTP_3);
		
		// Create the actual client.
		var client = VitruvClientFactory.create(serverUri, clientWrapper, TEMPORARY_PATH);
		
		var viewTypes = client.getViewTypes();
		
		var familiesViewType = viewTypes.stream().filter(vt -> vt.getName().equals(DemoUtility.FAMILIES_VIEW_TYPE_NAME)).findAny();
		if (familiesViewType.isEmpty()) {
			throw new IllegalStateException("Looked for the families view tpye, but did not find it.");
		}
		
		// Get a view and change the family name.
		var viewSource = familiesViewType.get().createSelector(null);
		viewSource.getSelectableElements().forEach(element -> viewSource.setSelected(element, true));
		var view = viewSource.createView().withChangeRecordingTrait();
		var familyRegister = view.getRootObjects(FamilyRegister.class).iterator().next();
		var family = familyRegister.getFamilies().get(0);
		family.setLastName("Musterfrau");
		// Commit changes.
		view.commitChangesAndUpdate();
		view.close();

		// Get changed persons.
		var personsViewType = viewTypes.stream().filter(vt -> vt.getName().equals(DemoUtility.PERSONS_VIEW_TYPE_NAME)).findAny();
		if (personsViewType.isEmpty()) {
			throw new IllegalStateException("Looked for the persons view type, but did not find it.");
		}
		var changedViewSource = personsViewType.get().createSelector(null);
		changedViewSource.getSelectableElements().forEach(element -> changedViewSource.setSelected(element, true));
		var changedView = changedViewSource.createView();
		var changedPersonsRegister = changedView.getRootObjects(PersonRegister.class).iterator().next();
		for (var person : changedPersonsRegister.getPersons()) {
			System.out.println("Found person " + person.getFullName());
		}
		changedView.close();

		client.disconnect();
	}
}
