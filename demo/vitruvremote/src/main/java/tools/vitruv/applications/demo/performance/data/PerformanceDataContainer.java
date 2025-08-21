package tools.vitruv.applications.demo.performance.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nimbusds.jose.shaded.gson.Gson;

public class PerformanceDataContainer {
	public static record PerformanceDataPoint(long modelCreationTime, long propagationTime) {}

	private Path temporaryStoragePath;
	private Map<String, List<PerformanceDataPoint>> data = new HashMap<>();

	public PerformanceDataContainer(Path temporaryStoragePath) {
		this.temporaryStoragePath = temporaryStoragePath;
	}
	
	public void addData(String serverConfig, String clientConfig, String communication, long creationTime, long propagationTime) {
		var id = serverConfig + "-" + clientConfig + "-" + communication;
		var points = this.data.get(id);
		if (points == null) {
			points = new ArrayList<>();
			this.data.put(id, points);
		}
		points.add(new PerformanceDataPoint(creationTime, propagationTime));
	}

	public String getAllDataAsJson() {
		return new Gson().toJson(this.data);
	}
	
	public void save() {
		var dataJson = this.getAllDataAsJson();
		try (var writer = Files.newBufferedWriter(this.temporaryStoragePath)) {
			writer.append(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not store performance data. This will be ignored.");
		}
	}
}
