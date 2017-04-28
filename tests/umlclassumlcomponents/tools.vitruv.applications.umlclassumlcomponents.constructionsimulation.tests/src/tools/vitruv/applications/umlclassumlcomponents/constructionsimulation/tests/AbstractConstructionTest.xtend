package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.tests

import tools.vitruv.applications.umlclassumlcomponents.class2comp.UmlClass2UmlCompChangePropagation
import tools.vitruv.framework.tests.VitruviusApplicationTest 
import java.util.List
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.emf.ecore.EObject
import java.io.File
import tools.vitruv.framework.util.bridges.EcoreResourceBridge
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.domains.VitruvDomain

abstract class AbstractConstructionTest extends VitruviusApplicationTest  {
	
	def protected void triggerSynchronization(List<VitruviusChange> changes) {
		for (change : changes) {
			triggerSynchronization(change)
		}
	}
	
	def protected void triggerSynchronization(VitruviusChange change) {
		virtualModel.propagateChange(change)
	}
	
	def protected Resource createAndSynchronizeModel2(String path, EObject rootElement) {
		if (path.isNullOrEmpty || rootElement === null) {
			throw new IllegalArgumentException()
		}
		//val modelPath = currentTestProjectName + File.separator + path
		val modelPath = testName + File.separator + path
		//val resource = resourceSet.createResource(URI.createPlatformResourceURI(modelPath, true))
		val resource = createModelResource(modelPath)
		EcoreResourceBridge.saveResource(resource)
		resource.contents.add(rootElement)
		return resource
	}
		
	override protected createChangePropagationSpecifications() {
		return #[new UmlClass2UmlCompChangePropagation()]
	}	

	//hack for handling of one singular UML model instead of two
	override protected getVitruvDomains() {
		return #[new UmlDomainProvider().domain]
	}	
	
	//hack for handling of one singular UML model instead of two
	override protected getCorrespondenceModel() {
		val VitruvDomain umlDomain = this.getVitruvDomains().iterator().next
		return this.getVirtualModel().getCorrespondenceModel(umlDomain.getURI(), umlDomain.getURI()) 
	}
		
	override protected cleanup() {
	}
	
	override protected setup() {
		
	}
	
	
}
