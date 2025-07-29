package tools.vitruv.applications.demo.performance;

import java.io.IOException;
import java.nio.file.Paths;

import tools.vitruv.applications.demo.FamiliesPersonsVsumWrapper;
import tools.vitruv.framework.remote.common.apm.VitruvApmController;
import tools.vitruv.framework.remote.server.VitruvServer;

public class MeasuringServer {
	public static void main(String[] args) throws IOException {
		VitruvApmController.enable(Paths.get("target", OutputConstants.SERVER_APM_FILE_NAME));
		
		var server = new VitruvServer(() -> {
			FamiliesPersonsVsumWrapper vsumWrapper = new FamiliesPersonsVsumWrapper();
			try {
				vsumWrapper.initialize();
			} catch (Exception ex) {
				throw new RuntimeException("Could not initialize the VSUM.", ex);
			}
			return vsumWrapper.getVsum();
		}, Configuration.PORT, Configuration.HOST_NAME_OR_IP);
		server.start();
		
		System.out.format("Vitruvius server started on: http://%s:%d%n", Configuration.HOST_NAME_OR_IP, Configuration.PORT);
	}
}
