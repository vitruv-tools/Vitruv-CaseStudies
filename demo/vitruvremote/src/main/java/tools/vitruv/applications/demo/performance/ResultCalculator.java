package tools.vitruv.applications.demo.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.apache.commons.math.stat.StatUtils;

public final class ResultCalculator {
	private ResultCalculator() {}
	
	public static void main(String[] args) throws IOException {
		processPerformanceData();
	}
	
	private static void processPerformanceData() throws IOException {
		var root = Paths.get("target");
		
		var serverDeserializationTimes = new ArrayList<Long>();
		var serverPropagationTimes = new ArrayList<Long>();
		var clientCycleTimes = new ArrayList<Long>();

		// Extract all relevant performance data from the APM files.
		processApmFile(root.resolve(OutputConstants.SERVER_APM_FILE_NAME), (String id, Long time) -> {
			if (id.contains("deserialization")) {
				serverDeserializationTimes.add(time);
			} else if (id.contains("propagation")) {
				serverPropagationTimes.add(time);
			}
		});
		processApmFile(root.resolve(OutputConstants.CLIENT_APM_FILE_NAME), (String id, Long time) -> {
			if (id.contains("/view") && id.contains("PATCH")) {
				clientCycleTimes.add(time);
			}
		});
		
		// In case that the client process was stopped before all data was written, we fill the data with 0.
		while (clientCycleTimes.size() != serverDeserializationTimes.size()) {
			clientCycleTimes.add(0L);
		}
		// Every second data point is removed because the propagation in the client builds up the model
		// (first data point) and deletes it afterward (second data point) to reuse the V-SUM / Vitruvius Server.
		for (int idx = serverDeserializationTimes.size() - 1; idx > 0; idx -= 2) {
			serverDeserializationTimes.remove(idx);
			serverPropagationTimes.remove(idx);
			clientCycleTimes.remove(idx);
		}
		
		// We extend the performance data of server - client and calculate the average and standard deviation.
		var out = extendServerClientData(root, serverDeserializationTimes, serverPropagationTimes, clientCycleTimes);
		out.add(root.resolve(OutputConstants.LOCAL_DATA_FILE_NAME_SMALL));
		out.add(root.resolve(OutputConstants.LOCAL_DATA_FILE_NAME_MEDIUM));
		out.add(root.resolve(OutputConstants.LOCAL_DATA_FILE_NAME_LARGE));
		for (var file : out) {
			calculateAndPrintStatistics(file);
		}
	}
	
	private static void processApmFile(Path file, BiConsumer<String, Long> valueConsumer) throws IOException, NumberFormatException {
		try (var reader = Files.newBufferedReader(file)) {
			var content = "";
			while ((content = reader.readLine()) != null) {
				var lastPos = content.lastIndexOf(",");
				var id = content.substring(0, lastPos);
				var timeString = content.substring(lastPos + 1);
				
				valueConsumer.accept(id, Long.parseLong(timeString));
			}
		}
	}
	
	private static List<Path> extendServerClientData(Path root, List<Long> serverDeserializationTimes,
			List<Long> serverPropagationTimes, List<Long> clientCycleTimes) throws IOException {
		var fileNamePrefix = OutputConstants.CLIENT_DATA_FILE_NAME_PREFIX + Configuration.HOST_NAME_OR_IP;
		String[] fileNames = {
			fileNamePrefix + OutputConstants.CLIENT_DATA_FILE_NAME_SMALL,
			fileNamePrefix + OutputConstants.CLIENT_DATA_FILE_NAME_MEDIUM,
			fileNamePrefix + OutputConstants.CLIENT_DATA_FILE_NAME_LARGE
		};
		var outFiles = new ArrayList<Path>();
		var currentIdx = 0;
		var builder = new StringBuilder();
		
		for (var fileName : fileNames) {
			try (var reader = Files.newBufferedReader(root.resolve(fileName))) {
				builder.append(reader.readLine());
				builder.append(",Deserialization time,Actual propagation time,HTTP RTT time\n");
				var content = "";
				while ((content = reader.readLine()) != null) {
					builder.append(content);
					builder.append(",");
					builder.append(serverDeserializationTimes.get(currentIdx));
					builder.append(",");
					builder.append(serverPropagationTimes.get(currentIdx));
					builder.append(",");
					builder.append(clientCycleTimes.get(currentIdx));
					builder.append("\n");
					currentIdx++;
				}
			}
			
			var out = root.resolve(fileName + "-ext.csv");
			try (var writer = Files.newBufferedWriter(out)) {
				writer.append(builder.toString());
			}
			builder.delete(0, builder.length());
			outFiles.add(out);
		}
		
		return outFiles;
	}
	
	private static void calculateAndPrintStatistics(Path file) throws IOException {
		try (var reader = Files.newBufferedReader(file)) {
			var headline = reader.readLine();
			var headers = headline.split(",");
			var measures = new ArrayList<List<Long>>();
			for (var idx = 0; idx < headers.length; idx++) {
				measures.add(new ArrayList<>());
			}
			
			var content = "";
			while ((content = reader.readLine()) != null) {
				var parts = content.split(",");
				for (var idx = 0; idx < headers.length; idx++) {
					measures.get(idx).add(Long.parseLong(parts[idx]));
				}
			}
			
			for (var idx = 0; idx < headers.length; idx++) {
				var times = measures.get(idx).stream().mapToDouble(l -> l).toArray();
				var avg = StatUtils.mean(times);
				var std = Math.sqrt(StatUtils.variance(times));
				System.out.format("%s - %s: %f ms (std. %f ms)%n", file.getFileName().toString(), headers[idx], avg, std);
			}
		}
	}
}
