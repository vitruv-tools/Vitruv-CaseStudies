package tools.vitruv.applications.demo.secremote;

import java.nio.file.Files;
import java.nio.file.Path;

import tools.vitruv.remote.seccommon.cert.CertificateGenerator;

public final class SecUtility {
    private SecUtility() {}

    public static record CertificateProperties(Path keyStorePath, Path trustStorePath, String password) {}

    public static CertificateProperties initializeCertificates(Path rootDir, boolean createCertificates) throws Exception {
		var certDir = rootDir.resolve("certs");
		var keyStorePath = certDir.resolve("keys.ks");
		var trustStorePath = certDir.resolve("trust.ks");
		var password = "vitruv";

        if (createCertificates && Files.notExists(certDir)) {
			Files.createDirectories(certDir);
			CertificateGenerator.generateFullCertificateChainForLocalhost(password, keyStorePath, password, trustStorePath);
        }

        return new CertificateProperties(keyStorePath, trustStorePath, password);
    }
}
