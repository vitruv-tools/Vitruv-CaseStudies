package tools.vitruv.applications.pcmumlcomponents.tests.uml2pcm

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
import org.eclipse.emf.common.util.BasicEList
import org.palladiosimulator.pcm.repository.Repository
import org.eclipse.emf.ecore.EClass

import org.eclipse.uml2.uml.UMLPackage
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.testutils.LegacyVitruvApplicationTest
import java.nio.file.Path

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertEquals
import tools.vitruv.applications.pcmumlcomponents.UmlToPcmComponentsChangePropagationSpecification
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.UmlToPcmTypesUtil

abstract class AbstractUmlPcmTest extends LegacyVitruvApplicationTest {
	protected static val MODEL_FILE_EXTENSION = "uml"
	protected static val MODEL_NAME = "model"
	protected static val COMPONENT_NAME = "TestComponent"
	protected static val INTERFACE_NAME = "TestInterface"
	protected static val OPERATION_NAME = "fooOperation"
	protected static val PARAMETER_NAME = "fooParameter"
	protected static val PARAMETER_NAME_2 = "barParameter"

	protected static val UML_TYPE_BOOL = "Boolean"
	protected static val UML_TYPE_INT = "Integer"
	protected static val UML_TYPE_REAL = "Real"
	protected static val UML_TYPE_STRING = "String"

	@BeforeEach
	protected def void disableTransitivePropagation() {
		this.virtualModel.transitivePropagationEnabled = false
	}
	
	private def Path getProjectModelPath(String modelName) {
		Path.of("model").resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

	protected def Model getRootElement() {
		return Model.from(MODEL_NAME.getProjectModelPath)
	}

	override protected getChangePropagationSpecifications() {
		return #[new UmlToPcmComponentsChangePropagationSpecification()]
	}

	protected def void initializeTestModel() {
		resourceAt(MODEL_NAME.projectModelPath).startRecordingChanges => [
			contents += UMLFactory.eINSTANCE.createModel() => [
				name = MODEL_NAME
			]
		]
		propagate
	}

	@BeforeEach
	def protected setup() {
		this.initializeTestModel()
	}

	protected def importPrimitiveTypes() {
		// val resourceSet = rootElement.eResource.resourceSet
		val resourceSet = new ResourceSetImpl()
		resourceSet.createResource(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI))
		val primitiveTypesUri = URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI).appendFragment("_0")
		val primitiveTypes = resourceSet.getEObject(primitiveTypesUri, true)
		val package = primitiveTypes as Package
		rootElement.createPackageImport(package)
		propagate
		return package
	}

	protected def int getDataTypeUserSelection(String umlTypeName) {
		val pcmType = UmlToPcmTypesUtil.getDataTypeEnumValue(umlTypeName)
		return Arrays.asList(PrimitiveTypeEnum.values).indexOf(pcmType) % PrimitiveTypeEnum.values.length + 1
	}

	protected def Iterable<EObject> correspondingElements(EObject element) {
		return getCorrespondingEObjects(element, EObject)
	}

	protected def <T> EList<T> toEList(List<T> elements) {
		val eList = new BasicEList<T>()
		eList.addAll(elements)
		return eList
	}

	protected def Repository claimCorrespondingRepository(EObject rootElement) {
		rootElement.correspondingElements.size == 2
		val potentialRepositories = rootElement.correspondingElements.filter(Repository)
		val potentialClasses = rootElement.correspondingElements.filter(EClass)
		assertEquals(1, potentialRepositories.size)
		assertEquals(1, potentialClasses.size)
		assertEquals(UMLPackage.eINSTANCE.model, potentialClasses.get(0))
		return potentialRepositories.get(0)
	}

}
