package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import java.util.Collections
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.compare.EMFCompare
import org.eclipse.emf.compare.scope.IComparisonScope
import org.eclipse.uml2.uml.Model
import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository
import org.eclipse.emf.compare.scope.DefaultComparisonScope
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl
import org.eclipse.emf.compare.utils.UseIdentifiers

/**
 * This will use test-models adapted form the Vitruv-Applications-PCMJavaAdditionals repository
 */
class PcmJavaAdditionalsTest extends ModelConstructionTest {
	
	@Test
	public def void smallExampleTest() {
		Logger.rootLogger.level = Level.INFO
		val resource = loadModel("model/small_example.repository")
		loadPrimitiveTypes(resource.resourceSet)
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
		loadPrimitiveTypes(resource.resourceSet)
		//val resource = TestUtil.loadPcmResource("model-pcm/mediastore.repository")
		val repo = constructRepository(resource.allContents.head as Repository)
		
		/* // EMF Compare
		val model = correspondenceModel.getCorrespondingEObjects(#[repo]).flatten.head as Model
		
		val resourceSet = new ResourceSetImpl()
		val targetModel = resourceSet.getResource(URI.createFileURI("target_model/mediastore.uml"), true)
		
		val scope = new DefaultComparisonScope(targetModel, null, model.eResource)

		val registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance()
		val matchEngineFactory = new MatchEngineFactoryImpl(UseIdentifiers.NEVER)
		matchEngineFactory.ranking = 20
		registry.add(matchEngineFactory)	

		val comparison = EMFCompare.builder.setMatchEngineFactoryRegistry(registry).build().compare(scope)
		val differences = comparison.differences
		for (diff : differences) {
			println(diff)
		}
		*/
	}
	
}