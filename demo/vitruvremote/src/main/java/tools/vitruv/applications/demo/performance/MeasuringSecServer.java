package tools.vitruv.applications.demo.performance;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jetty.security.openid.OpenIdConfiguration;

import tools.vitruv.applications.demo.DemoUtility;
import tools.vitruv.applications.demo.FamiliesPersonsVsumWrapper;
import tools.vitruv.applications.demo.oidc.OIDCMockServer;
import tools.vitruv.applications.demo.oidc.OIDCMockServer.OIDCMockServerConfiguration;
import tools.vitruv.applications.demo.secremote.SecUtility;
import tools.vitruv.framework.remote.common.apm.VitruvApmController;
import tools.vitruv.framework.remote.server.VitruvServerConfiguration;
import tools.vitruv.remote.secclient.JettySecureHttpClientFactory;
import tools.vitruv.remote.seccommon.TlsContextConfiguration;
import tools.vitruv.remote.secserver.builder.VitruvSecurityServer2Builder;
import tools.vitruv.remote.secserver.config.AuthenticationMode;

public class MeasuringSecServer {

	public static void main(String[] args) throws Exception {
		VitruvApmController.enable(Paths.get("target", OutputConstants.SEC_SERVER_APM_FILE_NAME));

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
				return vsumWrapper.getVsum();
			}
		);
		
		vitruvSecServer.start();
		
		
		System.out.println("Vitruvius Security Server with Jetty running on: " + vitruvSecServer.getBaseUrl());
	
	}

}
