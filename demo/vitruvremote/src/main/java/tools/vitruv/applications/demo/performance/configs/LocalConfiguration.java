package tools.vitruv.applications.demo.performance.configs;

import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import tools.vitruv.applications.demo.FamiliesPersonsVsumWrapper;
import tools.vitruv.applications.demo.performance.Configuration;
import tools.vitruv.applications.demo.performance.RandomFamiliesModelGenerator.FamilyModelGenerationParameters;
import tools.vitruv.applications.demo.performance.common.FixedFamiliesModelGenerator;
import tools.vitruv.applications.demo.performance.data.PerformanceDataContainer;
import tools.vitruv.applications.demo.performance.data.PerformanceDataContainer.PerformanceDataPoint;

public final class LocalConfiguration {
	private LocalConfiguration() {}

	public static void executeLocalConfiguration(Path vsumDir, PerformanceDataContainer dataContainer) throws Exception {
		System.out.println("--- Small configuration with local Vitruvius ---");
		measureLocalPerformance(Configuration.REPETITIONS_SMALL, vsumDir,
				Configuration.SMALL_MODEL_PARAMETERS, dataContainer);
		System.out.println("--- Medium configuration with local Vitruvius ---");
		measureLocalPerformance(Configuration.REPETITIONS_MEDIUM, vsumDir,
				Configuration.MEDIUM_MODEL_PARAMETERS, dataContainer);
		System.out.println("--- Large configuration with local Vitruvius ---");
		measureLocalPerformance(Configuration.REPETITIONS_LARGE, vsumDir,
				Configuration.LARGE_MODEL_PARAMETERS, dataContainer);
	}
	
	private static void measureLocalPerformance(int repetitions, Path vsumPath, FamilyModelGenerationParameters genParams, PerformanceDataContainer dataContainer) throws Exception {
		for (int rIdx = 0; rIdx < repetitions; rIdx++) {
			System.out.println("Starting round " + rIdx);
			
			var wrapper = new FamiliesPersonsVsumWrapper();
			wrapper.initialize(vsumPath);
			
			var familyView = wrapper.getFamilyView().withChangeRecordingTrait();
			var familyRegister = familyView.getRootObjects(FamilyRegister.class).stream().findAny();
			if (familyRegister.isEmpty()) {
				throw new IllegalStateException("");
			}
			
			long cTime = System.currentTimeMillis();
			FixedFamiliesModelGenerator.createFamilies(familyRegister.get(), genParams);
			cTime = System.currentTimeMillis() - cTime;
			
			long pTime = System.currentTimeMillis();
			familyView.commitChanges();
			pTime = System.currentTimeMillis() - pTime;
			
			familyView.close();
			wrapper.close();
			
			System.out.format("Finished round %d with %d ms (creation) and %d ms (propagation).%n", rIdx, cTime, pTime);
			dataContainer.addData(new PerformanceDataPoint(ConfigNames.CONFIG_LOCAL, ConfigNames.CONFIG_LOCAL, ConfigNames.COMMUNICATION, cTime, pTime));
		}
		
		FileUtils.deleteDirectory(vsumPath.toFile());
		dataContainer.save();
	}
}
