package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository

class ExistingModelsTest extends ModelConstructionTest {
	
	@Test
	public def void smallExampleTest() {
		Logger.rootLogger.level = Level.INFO
		val resource = loadModel("model/small_example.repository")
		//loadPrimitiveTypes(resource.resourceSet)
		val repository = resource.allContents.head as Repository
		logger.level = Level.ALL
		constructRepository(repository)
		
	}
	
	@Test
	public def void mediastoreTest() {
		Logger.rootLogger.level = Level.INFO
		val resource = loadModel("model/mediastore.repository")
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.allContents.head)
	}
	
	@Test
	public def void mediastoreRoundtripTest() {
		Logger.rootLogger.level = Level.INFO
		val resource = loadModel("model/mediastore_current.repository")
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.allContents.head)
	}
	
	@Test
	public def void smallExampleRoudtripTest() {
		val resource = loadModel("model/small_example_generated.repository")
		createAndSynchronizeModel(TARGET_MODEL_NAME, resource.allContents.head)
	}
	
}