package tools.vitruv.applications.demo.performance.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;

import tools.vitruv.applications.demo.performance.common.FamilyModelGenerationParameters;

public class PerformanceDataContainer {
	public static class PerformanceMeasurementSeries {
		private String serverConfig;
		private String clientConfig;
		private String communication;
		private int noFamilies;
		private int noMembersPerFamily;
		private List<PerformanceDataPoint> dataPoints = new ArrayList<>();

		public String getServerConfig() {
			return serverConfig;
		}

		public void setServerConfig(String serverConfig) {
			this.serverConfig = serverConfig;
		}

		public String getClientConfig() {
			return clientConfig;
		}

		public void setClientConfig(String clientConfig) {
			this.clientConfig = clientConfig;
		}

		public String getCommunication() {
			return communication;
		}

		public void setCommunication(String communication) {
			this.communication = communication;
		}

		public int getNoFamilies() {
			return noFamilies;
		}

		public void setNoFamilies(int noFamilies) {
			this.noFamilies = noFamilies;
		}

		public int getNoMembersPerFamily() {
			return noMembersPerFamily;
		}
		
		public void setNoMembersPerFamily(int noMembersPerFamily) {
			this.noMembersPerFamily = noMembersPerFamily;
		}

		public List<PerformanceDataPoint> getDataPoints() {
			return dataPoints;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof PerformanceMeasurementSeries)) {
				return false;
			}
			var otherSeries = (PerformanceMeasurementSeries) obj;
			return otherSeries.serverConfig.equals(serverConfig)
				&& otherSeries.clientConfig.equals(clientConfig)
				&& otherSeries.communication.equals(communication)
				&& otherSeries.noFamilies == noFamilies
				&& otherSeries.noMembersPerFamily == noMembersPerFamily;
		}

		@Override
		public String toString() {
			return String.format("%s-%s-%s-%d-%d", this.serverConfig, this.clientConfig, this.communication, this.noFamilies, this.noMembersPerFamily);
		}
	}

	public static class PerformanceDataPoint {
		private long modelCreationTime;
		private long propagationTime;
		private long measured;

		public PerformanceDataPoint(long modelCreationTime, long propagationTime, long measured) {
			this.modelCreationTime = modelCreationTime;
			this.propagationTime = propagationTime;
			this.measured = measured;
		}

		public long modelCreationTime() {
			return modelCreationTime;
		}

		public long propagationTime() {
			return propagationTime;
		}

		public long measured() {
			return measured;
		}
	}

	private Path temporaryStoragePath;
	private List<PerformanceMeasurementSeries> measurementSeries = new ArrayList<>();
	private Gson gson = new Gson();

	public PerformanceDataContainer(Path temporaryStoragePath) {
		this.temporaryStoragePath = temporaryStoragePath;
	}
	
	public void addData(
		String serverConfig,
		String clientConfig,
		String communication,
		FamilyModelGenerationParameters genParams,
		long creationTime,
		long propagationTime
	) {
		var series = new PerformanceMeasurementSeries();
		series.setServerConfig(serverConfig);
		series.setClientConfig(clientConfig);
		series.setCommunication(communication);
		series.setNoFamilies(genParams.noFamilies());
		series.setNoMembersPerFamily(genParams.noMembersPerFamily());

		var identifiedSeries = this.measurementSeries
			.parallelStream()
			.filter(nextSeries -> nextSeries.equals(series))
			.findFirst();

		PerformanceMeasurementSeries actualSeries;
		if (identifiedSeries.isEmpty()) {
			this.measurementSeries.add(series);
			actualSeries = series;
		} else {
			actualSeries = identifiedSeries.get();
		}

		actualSeries.getDataPoints().add(new PerformanceDataPoint(creationTime, propagationTime, System.currentTimeMillis()));
	}

	private Optional<PerformanceMeasurementSeries> getSeries(PerformanceMeasurementSeries otherSeries) {
		return this.measurementSeries.parallelStream().filter(otherSeries::equals).findFirst();
	}

	public void addDataFromJson(String json) {
		var newSeries = (List<PerformanceMeasurementSeries>) this.gson
			.fromJson(json, TypeToken.getParameterized(ArrayList.class, PerformanceMeasurementSeries.class));
		for (var entry : newSeries) {
			var potentialSeries = this.getSeries(entry);
			if (potentialSeries.isPresent()) {
				potentialSeries.get().getDataPoints().addAll(entry.getDataPoints());
			} else {
				this.measurementSeries.add(entry);
			}
		}
	}

	public String getAllDataAsJson() {
		return this.gson.toJson(this.measurementSeries);
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

	public void load() throws IOException {
		var json = Files.readString(this.temporaryStoragePath);
		this.addDataFromJson(json);
	}

	public void toCsv(Path csvFile) throws IOException {
		try (var writer = Files.newBufferedWriter(csvFile)) {
			for (var series : this.measurementSeries) {
				writer.append(series.toString());
				for (var data : series.getDataPoints()) {
					writer.append("," + data.propagationTime);
				}
				writer.newLine();
			}
		}
	}
}
