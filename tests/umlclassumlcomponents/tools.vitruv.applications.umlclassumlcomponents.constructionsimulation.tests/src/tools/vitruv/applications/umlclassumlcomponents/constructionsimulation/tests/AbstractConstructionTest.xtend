package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.tests

import tools.vitruv.applications.umlclassumlcomponents.class2comp.UmlClass2UmlCompChangePropagation
import tools.vitruv.framework.tests.VitruviusEMFCasestudyTest
import java.util.List
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.emf.ecore.EObject
import java.io.File
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.metamodel.Metamodel
import org.eclipse.emf.ecore.resource.Resource

abstract class AbstractConstructionTest extends VitruviusEMFCasestudyTest {
	
	def protected void triggerSynchronization(List<VitruviusChange> changes) {
		for (change : changes) {
			triggerSynchronization(change)
		}
	}
	
	def protected void triggerSynchronization(VitruviusChange change) {
		virtualModel.propagateChange(change)
	}
	
	def protected Resource createAndSynchronizeModel(String path, EObject rootElement) {
		if (path.isNullOrEmpty || rootElement === null) {
			throw new IllegalArgumentException()
		}
		val modelPath = currentTestProjectName + File.separator + path
		val resource = resourceSet.createResource(URI.createPlatformResourceURI(modelPath, true))
		EcoreResourceBridge.saveResource(resource)
		resource.contents.add(rootElement)
		return resource
	}
		
	override protected createChangePropagationSpecifications() {
		return #[new UmlClass2UmlCompChangePropagation()]
	}	

	override protected createMetamodels() {
		//return #[new UmlDomain().metamodel, new UmlDomain().metamodel]
		return #[new UmlDomain().metamodel]
	}	
	
	//hack for handling of one singular UML model instead of two
	override protected getCorrespondenceModel() {
		val Metamodel umlMM = metamodels.iterator().next
		return this.getVirtualModel().getCorrespondenceModel(umlMM.getURI(), umlMM.getURI()) 
	}
	
}
