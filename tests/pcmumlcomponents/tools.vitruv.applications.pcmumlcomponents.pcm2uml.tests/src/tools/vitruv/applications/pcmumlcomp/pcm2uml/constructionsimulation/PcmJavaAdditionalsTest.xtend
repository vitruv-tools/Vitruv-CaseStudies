package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.junit.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.PrimitiveDataType

/**
 * This will use test-models adapted form the Vitruv-Applications-PCMJavaAdditionals repository
 */
class PcmJavaAdditionalsTest extends ModelConstructionTest {
	
	@Test
	public def void smallExampleTest() {
		Logger.rootLogger.level = Level.INFO
		val resource = loadModel("model/small_example.repository")
		val repository = resource.allContents.head as Repository
		/*this.loadPrimitiveTypes()
		var types = repository.dataTypes__Repository.filter(CompositeDataType)
		for (CompositeDataType type : types) {
			println("> composite type " + type.entityName)
			for (id : type.innerDeclaration_CompositeDataType) {
				println(">> inner declaration " + id.entityName)
				println(id.datatype_InnerDeclaration)
				if (id.datatype_InnerDeclaration instanceof PrimitiveDataType) {
					println((id.datatype_InnerDeclaration as PrimitiveDataType).type)
				}
			}
		}*/
		logger.level = Level.ALL
		val r = constructRepository(repository)
		validateCorrespondence(logger, r)
		//createAndSynchronizeModel(TARGET_MODEL_NAME, resource.rootElement)
		
	}
	
	@Test
	public def void mediastoreTest() {
		Logger.rootLogger.level = Level.INFO
		val resource = loadModel("model/mediastore.repository")
		constructRepository(resource.allContents.head as Repository)
		
	}
	
}