package tools.vitruv.applications.pcmumlcomponents.uml2pcm

import java.util.Arrays
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.resource.UMLResource
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.testutils.VitruviusApplicationTest
import org.eclipse.emf.common.util.BasicEList

abstract class AbstractUmlPcmTest extends VitruviusApplicationTest {
	protected static val MODEL_FILE_EXTENSION = "uml";
	protected static val MODEL_NAME = "model";
	protected static val COMPONENT_NAME = "TestComponent"
	protected static val INTERFACE_NAME = "TestInterface"
	protected static val OPERATION_NAME = "fooOperation"
	protected static val PARAMETER_NAME = "fooParameter"
	protected static val PARAMETER_NAME_2 = "barParameter"
		
	protected static val UML_TYPE_BOOL = "Boolean"
	protected static val UML_TYPE_INT = "Integer"
	protected static val UML_TYPE_REAL = "Real"
	protected static val UML_TYPE_STRING = "String"	
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
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
	
	protected def int getDataTypeUserSelection(String umlTypeName) {
		val pcmType = UmlToPcmTypesUtil.getDataTypeEnumValue(umlTypeName)
		return Arrays.asList(PrimitiveTypeEnum.values).indexOf(pcmType) % PrimitiveTypeEnum.values.length + 1
	}
	
	protected def Iterable<EObject> correspondingElements(EObject element) {
		return correspondenceModel.getCorrespondingEObjects(#[element]).flatten
	}
	
	protected def <T> EList<T> toEList(List<T> elements) {
		val eList = new BasicEList<T>()
		eList.addAll(elements)
		return eList
	}

}
