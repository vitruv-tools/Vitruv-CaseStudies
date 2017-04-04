package tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation

import java.io.File
import java.util.List
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.junit.Test
import tools.vitruv.applications.pcmumlcomp.pcm2uml.PcmToUmlComponentsChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.tests.VitruviusEMFCasestudyTest
import tools.vitruv.framework.util.bridges.EcoreResourceBridge

class ModelConstructionTest extends VitruviusEMFCasestudyTest {
	
	val REPOSITORY_PATH = "model/small_example.repository"
	
	private static val Logger logger = Logger.getLogger(ModelConstructionTest)
		
	@Test
	def void smallExampleTest() {
		logger.level = Level.ALL
		val integrationStrategy = new PCMRepositoryIntegrationStrategy
		val Resource resource = integrationStrategy.loadModel(REPOSITORY_PATH)
		val changes = integrationStrategy.createChangeModels(null, resource)
		createAndSynchronizeModel("model/small_example.repository", resource.contents.get(0))
		triggerSynchronization(changes)
	}
	
	def protected void triggerSynchronization(List<VitruviusChange> changes) {
		for (change : changes) {
			triggerSynchronization(change)
		}
	}
	
	def protected void triggerSynchronization(VitruviusChange change) {
		virtualModel.propagateChange(change)
	}
	
	def protected void createAndSynchronizeModel(String path, EObject rootElement) {
		if (path.isNullOrEmpty || rootElement === null) {
			throw new IllegalArgumentException()
		}
		val modelPath = currentTestProjectName + File.separator + path
		val resource = resourceSet.createResource(
			URI.createPlatformResourceURI(modelPath, true)
		)
		EcoreResourceBridge.saveResource(resource)
		resource.contents.add(rootElement)
	}
		
	override protected createChangePropagationSpecifications() {
		return #[new PcmToUmlComponentsChangePropagationSpecification()]
	}
	
	override protected createMetamodels() {
		return #[new PcmDomain().metamodel, new UmlDomain().metamodel]
	}
	
}