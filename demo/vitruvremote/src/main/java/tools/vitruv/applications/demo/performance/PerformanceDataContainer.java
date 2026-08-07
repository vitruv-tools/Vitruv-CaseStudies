package tools.vitruv.applications.demo.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PerformanceDataContainer {
	private List<ExternalPerformanceData> externalData = new ArrayList<>();
	
	public void addExternalData(ExternalPerformanceData data) {
		this.externalData.add(data);
	}
	
	public void save(Path file) throws IOException {
		try (var writer = Files.newBufferedWriter(file)) {
			writer.append("Creation time,Propagation time\n");
			for (var data : externalData) {
				writer.append(data.creationTime() + "," + data.propagationTime() + "\n");
			}
		}
	}
}
