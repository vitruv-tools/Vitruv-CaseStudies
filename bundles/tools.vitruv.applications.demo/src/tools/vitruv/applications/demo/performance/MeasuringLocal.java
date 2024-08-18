package tools.vitruv.applications.demo.performance;

import java.nio.file.Path;
import java.nio.file.Paths;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import tools.vitruv.applications.demo.FamiliesPersonsVsumWrapper;
import tools.vitruv.applications.demo.performance.RandomFamiliesModelGenerator.FamilyModelGenerationParameters;

public class MeasuringLocal {
	public static void main(String[] args) throws Exception {
		var root = Paths.get("target");
		System.out.println("--- Small configuration with local Vitruvius ---");
		measureLocalPerformance(Configuration.REPETITIONS_SMALL, root.resolve("local-small"), root.resolve("local-small.csv"), Configuration.SMALL_MODEL_PARAMETERS);
		System.out.println("--- Medium configuration with local Vitruvius ---");
		measureLocalPerformance(Configuration.REPETITIONS_MEDIUM, root.resolve("local-medium"), root.resolve("local-medium.csv"), Configuration.MEDIUM_MODEL_PARAMETERS);
		System.out.println("--- Large configuration with local Vitruvius ---");
		measureLocalPerformance(Configuration.REPETITIONS_LARGE, root.resolve("local-large"), root.resolve("local-large.csv"), Configuration.LARGE_MODEL_PARAMETERS);
	}
	
	private static void measureLocalPerformance(int repetitions, Path rootPath, Path output, FamilyModelGenerationParameters genParams) throws Exception {
		var data = new PerformanceDataContainer();
		
		for (int rIdx = 0; rIdx < repetitions; rIdx++) {
			System.out.println("Starting round " + rIdx);
			
			var wrapper = new FamiliesPersonsVsumWrapper();
			wrapper.initialize(rootPath);
			
			var familyView = wrapper.getFamilyView().withChangeRecordingTrait();
			var familyRegister = familyView.getRootObjects(FamilyRegister.class).stream().findAny();
			if (familyRegister.isEmpty()) {
				throw new IllegalStateException("");
			}
			
			long cTime = System.currentTimeMillis();
			RandomFamiliesModelGenerator.createFamilies(familyRegister.get(), genParams);
			cTime = System.currentTimeMillis() - cTime;
			
			long pTime = System.currentTimeMillis();
			familyView.commitChanges();
			pTime = System.currentTimeMillis() - pTime;
			
			familyView.close();
			wrapper.close();
			
			System.out.format("Finished round %d with %d ms (creation) and %d ms (propagation).%n", rIdx, cTime, pTime);
			data.addExternalData(new ExternalPerformanceData(cTime, pTime));
		}
		
		data.calculateAndPrintStatistics();
		data.save(output);
	}
}
