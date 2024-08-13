package tools.vitruv.applications.demo.performance;

import tools.vitruv.applications.demo.DemoUtility;
import tools.vitruv.applications.demo.performance.RandomFamiliesModelGenerator.FamilyModelGenerationParameters;

public class Configuration {
	private Configuration() {}
	
	public static final FamilyModelGenerationParameters SMALL_MODEL_PARAMETERS = new FamilyModelGenerationParameters(10, 5);
	public static final FamilyModelGenerationParameters MEDIUM_MODEL_PARAMETERS = new FamilyModelGenerationParameters(100, 10);
	public static final FamilyModelGenerationParameters LARGE_MODEL_PARAMETERS = new FamilyModelGenerationParameters(300, 20);
	
	public static final int REPETITIONS = 100;
	
	public static final String HOST_NAME_OR_IP = DemoUtility.SERVER_HOST;
	public static final int PORT = DemoUtility.SERVER_PORT;
}
