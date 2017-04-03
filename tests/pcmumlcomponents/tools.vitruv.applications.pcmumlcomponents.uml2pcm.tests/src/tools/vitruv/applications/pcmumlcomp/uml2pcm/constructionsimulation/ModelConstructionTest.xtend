package tools.vitruv.applications.pcmumlcomp.uml2pcm.constructionsimulation

import java.io.File
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import tools.vitruv.applications.pcmumlcomp.uml2pcm.UmlToPcmComponentsChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.tests.VitruviusEMFCasestudyTest
import tools.vitruv.framework.util.bridges.EcoreResourceBridge

class ModelConstructionTest extends VitruviusEMFCasestudyTest {
		
	protected static val Logger logger = Logger.getLogger(ModelConstructionTest)
	
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
		return #[new UmlToPcmComponentsChangePropagationSpecification()]
	}
	
	override protected createMetamodels() {
		return #[new UmlDomain().metamodel, new PcmDomain().metamodel]
	}
	
}