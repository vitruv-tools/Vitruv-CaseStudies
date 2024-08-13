package tools.vitruv.applications.demo.performance;

import java.nio.file.Path;
import java.nio.file.Paths;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import tools.vitruv.applications.demo.DemoUtility;
import tools.vitruv.applications.demo.performance.RandomFamiliesModelGenerator.FamilyModelGenerationParameters;
import tools.vitruv.framework.remote.client.VitruvClientFactory;
import tools.vitruv.framework.views.ViewType;

public class MeasuringClient {
	public static void main(String[] args) throws Exception {
		var root = Paths.get("target");
		var tempRoot = root.resolve("temp");
		DemoUtility.registerFactories();
		var client = VitruvClientFactory.create(Configuration.HOST_NAME_OR_IP, Configuration.PORT, tempRoot);
		var familyViewType = client.getViewTypes().stream().filter((vType) -> vType.getName().equals(DemoUtility.FAMILIES_VIEW_TYPE_NAME)).findAny();
		if (familyViewType.isEmpty()) {
			throw new IllegalStateException("Did not find the families view type.");
		}
		
		var outputPrefix = "client-" + Configuration.HOST_NAME_OR_IP + "-";
		System.out.println("--- Small configuration with Vitruvius client ---");
		measureClientPerformance(Configuration.REPETITIONS, familyViewType.get(), root.resolve(outputPrefix + "small.csv"), Configuration.SMALL_MODEL_PARAMETERS);
		System.out.println("--- Medium configuration with local Vitruvius client ---");
		measureClientPerformance(Configuration.REPETITIONS, familyViewType.get(), root.resolve(outputPrefix + "medium.csv"), Configuration.MEDIUM_MODEL_PARAMETERS);
		System.out.println("--- Large configuration with local Vitruvius client ---");
		measureClientPerformance(Configuration.REPETITIONS, familyViewType.get(), root.resolve(outputPrefix + "large.csv"), Configuration.LARGE_MODEL_PARAMETERS);
	}
	
	public static void measureClientPerformance(int repetitions, ViewType<?> familyViewType, Path output, FamilyModelGenerationParameters params) throws Exception {
		var data = new PerformanceDataContainer();
		
		for (int rIdx = 0; rIdx < repetitions; rIdx++) {
			System.out.println("Round " + rIdx);
			var familySelector = familyViewType.createSelector(null);
			familySelector.getSelectableElements().forEach((element) -> {
//				if (element.eResource().getURI().toString().contains("families")) {
					familySelector.setSelected(element, true);
//				}
			});
			var familyView = familySelector.createView().withChangeRecordingTrait();
			var familyRegister = familyView.getRootObjects(FamilyRegister.class).stream().findAny();
			if (familyRegister.isEmpty()) {
				throw new IllegalStateException("Did not find a FamilyRegister.");
			}
			
			long cTime = System.currentTimeMillis();
			RandomFamiliesModelGenerator.createFamilies(familyRegister.get(), params);
			cTime = System.currentTimeMillis() - cTime;
			
			long pTime = System.currentTimeMillis();
			familyView.commitChanges();
			pTime = System.currentTimeMillis() - pTime;
			
			familyRegister.get().getFamilies().clear();
			familyView.commitChanges();
			
			familyView.close();
			System.out.format("%d ms (creation), %d ms (propagation)%n", cTime, pTime);
			data.addExternalData(new ExternalPerformanceData(cTime, pTime));
		}
		
		data.calculateAndPrintStatistics();
		data.save(output);
	}
}
