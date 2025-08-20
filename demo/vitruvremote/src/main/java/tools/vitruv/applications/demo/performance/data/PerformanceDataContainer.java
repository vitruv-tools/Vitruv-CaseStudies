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

	private Path temporaryStoragePath;
	private List<PerformanceDataPoint> data = new ArrayList<>();

	public PerformanceDataContainer(Path temporaryStoragePath) {
		this.temporaryStoragePath = temporaryStoragePath;
	}
	
	public void addData(PerformanceDataPoint data) {
		this.data.add(data);
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
