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
	private Gson gson = new Gson();

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

	public void addDataFromJson(String json) {
		var newData = this.gson.<Map<String, List<PerformanceDataPoint>>>fromJson(json, this.data.getClass());
		for (var entry : newData.entrySet()) {
			var list = this.data.get(entry.getKey());
			if (list != null) {
				list.addAll(entry.getValue());
			} else {
				this.data.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public String getAllDataAsJson() {
		return this.gson.toJson(this.data);
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
