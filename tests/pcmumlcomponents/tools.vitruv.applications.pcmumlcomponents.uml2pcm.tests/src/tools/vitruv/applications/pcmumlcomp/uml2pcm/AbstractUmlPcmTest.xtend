package tools.vitruv.applications.pcmumlcomp.uml2pcm

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.domains.pcm.PcmDomain
import tools.vitruv.domains.uml.UmlDomain
import tools.vitruv.framework.tests.VitruviusApplicationTest
import org.eclipse.emf.common.util.URI
import org.eclipse.uml2.uml.resource.UMLResource

class AbstractUmlPcmTest extends VitruviusApplicationTest {
	protected static val MODEL_FILE_EXTENSION = "uml";
	protected static val MODEL_NAME = "model";
	
	private def String getProjectModelPath(String modelName) {
		"repository/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.getProjectModelPath.firstRootElement as Model;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new UmlToPcmComponentsChangePropagationSpecification()]; 
	}
	
	override protected createMetamodels() {
		return #[new UmlDomain().metamodel, new PcmDomain().metamodel];
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
		val resourceSet = rootElement.eResource.resourceSet
		resourceSet.createResource(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI))
		val primitiveTypesUri = URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI).appendFragment("_0")
		val primitiveTypes = resourceSet.getEObject(primitiveTypesUri, true)
		val package = primitiveTypes as org.eclipse.uml2.uml.Package
		//rootElement.createPackageImport(package)
		//saveAndSynchronizeChanges(rootElement)
		return package
	}

}
