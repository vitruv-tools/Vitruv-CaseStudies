package tools.vitruv.applications.demo.performance.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PerformanceDirectoryStructure {
    private Path vsumServerDir;
    private Path vsumClientDir;
    private Path certDir;
    private Path keyStorePath;
    private Path trustStorePath;
    private Path remoteServerTrustStorePath;
    private Path tempCertDir;
    private Path performanceDateFile;

    public PerformanceDirectoryStructure(Path rootDir) throws IOException {
        vsumServerDir = rootDir.resolve("vsum-server");
        vsumClientDir = rootDir.resolve("vsum-client");

        certDir = rootDir.resolve("certs");
        keyStorePath = certDir.resolve("keystore.ks");
        trustStorePath = certDir.resolve("truststore.ks");
        remoteServerTrustStorePath = certDir.resolve("truststore-rc.ks");
        tempCertDir = rootDir.resolve("certs-temp");

        performanceDateFile = rootDir.resolve("perf-data.json");

        checkAndCreateDirectory(certDir);
        checkAndCreateDirectory(tempCertDir);
    }

    public Path getVsumServerDir() {
        return vsumServerDir;
    }

    public Path getVsumClientDir() {
        return vsumClientDir;
    }

    public Path getCertDir() {
        return certDir;
    }

    public Path getKeyStorePath() {
        return keyStorePath;
    }

    public Path getTrustStorePath() {
        return trustStorePath;
    }

    public Path getRemoteServerTrustStorePath() {
        return remoteServerTrustStorePath;
    }

    public Path getTempCertDir() {
        return tempCertDir;
    }

    public Path getPerformanceDataFile() {
        return performanceDateFile;
    }

    private static void checkAndCreateDirectory(Path dirPath) throws IOException {
        if (Files.notExists(dirPath)) {
            Files.createDirectories(dirPath);
        }
    }
}
