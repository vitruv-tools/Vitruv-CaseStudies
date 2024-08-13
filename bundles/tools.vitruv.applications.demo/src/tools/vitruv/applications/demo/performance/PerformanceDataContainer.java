package tools.vitruv.applications.demo.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math.stat.StatUtils;

public class PerformanceDataContainer {
	private List<ExternalPerformanceData> externalData = new ArrayList<>();
	
	public void addExternalData(ExternalPerformanceData data) {
		this.externalData.add(data);
	}
	
	public void calculateAndPrintStatistics() {
		var times = externalData.stream().mapToDouble((data) -> data.creationTime()).toArray();
		var avgCreation = StatUtils.mean(times);
		var sCreation = Math.sqrt(StatUtils.variance(times));
		times = externalData.stream().mapToDouble((data) -> data.propagationTime()).toArray();
		var avgPropagation = StatUtils.mean(times);
		var sPropagation = Math.sqrt(StatUtils.variance(times));
		System.out.format("Result: %f ms (avg. creation time, std=%f) and %f ms (avg. propagation time, std=%f)%n", avgCreation, sCreation, avgPropagation, sPropagation);
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
