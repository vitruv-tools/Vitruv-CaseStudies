package tools.vitruv.applications.pcmumlcomp.uml2pcm

import org.eclipse.emf.common.util.URI
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.resource.UMLResource
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.tests.VitruviusApplicationTest
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

class AbstractUmlPcmTest extends VitruviusApplicationTest {
	protected static val MODEL_FILE_EXTENSION = "uml";
	protected static val MODEL_NAME = "model";
	protected static val COMPONENT_NAME = "TestComponent"
	protected static val INTERFACE_NAME = "TestInterface"
	protected static val OPERATION_NAME = "fooOperation"
	protected static val PARAMETER_NAME = "fooParameter"
	protected static val PARAMETER_NAME_2 = "barParameter"
	
	private def String getProjectModelPath(String modelName) {
		"repository/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.getProjectModelPath.firstRootElement as Model;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new UmlToPcmComponentsChangePropagationSpecification()]; 
	}
	
	override protected getVitruvDomains() {
		return #[new UmlDomainProvider().domain, new PcmDomainProvider().domain];
	}
	
	protected def initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel();
		umlModel.name = MODEL_NAME;
		createAndSynchronizeModel(MODEL_NAME.getProjectModelPath, umlModel);
	}
	
	override protected cleanup() {
	}
	
	override protected setup() {
		this.initializeTestModel()
	}
	
	protected def importPrimitiveTypes() {
		//val resourceSet = rootElement.eResource.resourceSet
		val resourceSet = new ResourceSetImpl()
		resourceSet.createResource(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI))
		val primitiveTypesUri = URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI).appendFragment("_0")
		val primitiveTypes = resourceSet.getEObject(primitiveTypesUri, true)
		val package = primitiveTypes as Package
		rootElement.createPackageImport(package)
		saveAndSynchronizeChanges(rootElement)
		return package
	}

}
