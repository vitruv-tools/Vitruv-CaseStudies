package tools.vitruv.applications.demo.secremote;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jetty.security.openid.OpenIdConfiguration;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import tools.vitruv.applications.demo.DemoUtility;
import tools.vitruv.applications.demo.FamiliesPersonsVsumWrapper;
import tools.vitruv.applications.demo.oidc.OIDCMockServer;
import tools.vitruv.applications.demo.oidc.OIDCMockServer.OIDCMockServerConfiguration;
import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.remote.secclient.JettySecureHttpClientFactory;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.secserver.builder.VitruvSecurityServer2Builder;
import tools.vitruv.remote.secserver.config.AuthenticationMode;

public class DemoSecServer {
	public static void main(String[] args) throws Exception {
		var rootDir = Paths.get("target");
		
		var certProperties = SecUtility.initializeCertificates(rootDir, true);
		var tempCertDir = rootDir.resolve("temp-cert-server");
		Files.createDirectories(tempCertDir);

		var oidcServerPort = 9095;
		var oidcServer = new OIDCMockServer(
			new OIDCMockServerConfiguration(
				new VitruvServerConfiguration(DemoUtility.SERVER_HOST, oidcServerPort),
				new TlsContextConfiguration(certProperties.keyStorePath(), null, certProperties.password(), null, null, null, null)
			)
		);
		oidcServer.start();

		// Since we use a self-signed server certificate, the HTTP client used by Jetty for communicating with
		// the OpenID Connect provider must know the server certificate. Thus, we create our own HTTP client.
		var openIdHttpClient = JettySecureHttpClientFactory.createSecureHttpClient(
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
		OpenIdConfiguration oidcConfig = new OpenIdConfiguration(oidcServer.getBaseUri(), null, null, "42", certProperties.password(), openIdHttpClient);
		
		var vitruvSecServer = VitruvSecurityServer2Builder.createBuilder()
			.forHostNameOrIp(DemoUtility.SERVER_HOST)
			.onPort(DemoUtility.SERVER_PORT)
			.withHttp11()
			.withHttp2()
			.withExperimentalHttp3(tempCertDir)
			.usingKeyStorePath(certProperties.keyStorePath().toString(), certProperties.password())
			.operateInProxyMode()
			.runVitruvServerOnRandomPort()
			.authenticateWith(AuthenticationMode.OPEN_ID)
			.withOpenIdConfiguration(oidcConfig)
			.buildFor(() -> {
				FamiliesPersonsVsumWrapper vsumWrapper = new FamiliesPersonsVsumWrapper();
				try {
					vsumWrapper.initialize();
				} catch (Exception ex) {
					throw new RuntimeException("Could not initialize the VSUM.", ex);
				}
				
				// Create a family.
				CommittableView view = vsumWrapper.getFamilyView().withChangeRecordingTrait();
				FamilyRegister database = view.getRootObjects(FamilyRegister.class).iterator().next();
				Family family = FamiliesFactory.eINSTANCE.createFamily();
				family.setLastName("Mustermann");
				Member father = FamiliesFactory.eINSTANCE.createMember();
				father.setFirstName("Max");
				Member mother = FamiliesFactory.eINSTANCE.createMember();
				mother.setFirstName("Erika");
				
				family.setFather(father);
				family.setMother(mother);
				database.getFamilies().add(family);
				view.commitChanges();
				try {
					view.close();
				} catch (Exception ex) {
					throw new RuntimeException("Could not close view.", ex);
				}
				
				return vsumWrapper.getVsum();
			}
		);
		
		vitruvSecServer.start();
		
		System.out.println("Vitruvius Security Server with Jetty running on: " + vitruvSecServer.getBaseUrl());
	}
}
