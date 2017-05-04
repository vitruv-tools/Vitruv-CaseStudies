package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.tests

import java.io.File
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.applications.umlclassumlcomponents.class2comp.UmlClass2UmlCompChangePropagation
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.tests.VitruviusApplicationTest
import tools.vitruv.framework.util.bridges.EcoreResourceBridge

abstract class AbstractConstructionTest extends VitruviusApplicationTest  {
	
	protected static val OUTPUT_NAME = "model/model.uml"
	
	def protected void triggerSynchronization(List<VitruviusChange> changes) {
		for (change : changes) {
			triggerSynchronization(change)
		}
	}
	
	def protected void triggerSynchronization(VitruviusChange change) {
		virtualModel.propagateChange(change)
	}
	
	def protected Resource createAndSynchronizeModelWithReturnedResource(String path, EObject rootElement) { //TODO Temp, remove
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
	
	def protected createModel(String path, EObject rootElement) { //TODO Temp, remove
		if (path.isNullOrEmpty || rootElement === null) {
			throw new IllegalArgumentException()
		}
		//val resourceSet = new ResourceSetImpl()
		//val resource = resourceSet.createResource(URI.createPlatformResourceURI(path, true))
		val resource = createModelResource(path)
		//EcoreResourceBridge.saveResource(resource)
		resource.contents.add(rootElement)
	}

	override protected createChangePropagationSpecifications() {
		return #[new UmlClass2UmlCompChangePropagation()]
	}	

	//Hack for handling of one singular UML model instead of two
	override protected getVitruvDomains() {
		return #[new UmlDomainProvider().domain]
	}	
	
	//Hack for handling of one singular UML model instead of two
	override protected getCorrespondenceModel() {
		val VitruvDomain umlDomain = this.getVitruvDomains().iterator().next
		return this.getVirtualModel().getCorrespondenceModel(umlDomain.getURI(), umlDomain.getURI()) 
	}
		
	override protected cleanup() {
	}
	
	override protected setup() {
		
	}
}
