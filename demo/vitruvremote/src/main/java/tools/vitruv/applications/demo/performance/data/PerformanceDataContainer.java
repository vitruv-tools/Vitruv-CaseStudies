package tools.vitruv.applications.demo.performance.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.nimbusds.jose.shaded.gson.Gson;

public class PerformanceDataContainer {
	public static record PerformanceDataPoint(
		String serverConfig,
		String clientConfig,
		String communication,
		long modelCreationTime,
		long propagationTime
	) {}

	private List<PerformanceDataPoint> data = new ArrayList<>();
	
	public void addData(PerformanceDataPoint data) {
		this.data.add(data);
	}

	public String getAllDataAsJson() {
		return new Gson().toJson(this.data);
	}
	
	public void save(Path file) throws IOException {
		var dataJson = this.getAllDataAsJson();
		try (var writer = Files.newBufferedWriter(file)) {
			writer.append(dataJson);
		}
	}
}
