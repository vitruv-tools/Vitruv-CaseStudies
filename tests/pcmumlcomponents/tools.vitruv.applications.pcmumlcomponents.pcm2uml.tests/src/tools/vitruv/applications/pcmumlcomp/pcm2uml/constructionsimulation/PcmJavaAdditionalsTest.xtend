package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Test
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

/**
 * This will use test-models adapted form the Vitruv-Applications-PCMJavaAdditionals repository
 */
class PcmJavaAdditionalsTest extends ModelConstructionTest {
	
	@Test
	public def void smallExampleTest() {
		Logger.rootLogger.level = Level.INFO
		val resource = loadModel("model/small_example.repository")
		val rs = new ResourceSetImpl()
		rs.resources += resource
		rs.resources += loadModel("model/PrimitiveTypes.repository")
		//resourceSet.resources += loadModel("model/PrimitiveTypes.repository")
		EcoreUtil.resolveAll(rs)
		val dataTypes = resource.rootElement.dataTypes__Repository
		println("types: ")
		for (t : dataTypes) {
			if (t instanceof CollectionDataType) {
				println(t.innerType_CollectionDataType)
				//println(t.innerType_CollectionDataType)
			}
			if (t instanceof CompositeDataType) {
				println("composite Type " + t.entityName)
				for (p : t.innerDeclaration_CompositeDataType) {
					println(" param " + p.entityName)
					println(p.datatype_InnerDeclaration)
				}
			}
		}
		saveAndSynchronizeChanges(resource.rootElement)
		//saveModel(resource.rootElement)
	}
	
}