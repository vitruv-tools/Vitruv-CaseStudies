package tools.vitruv.applications.pcmumlcomponents.tests.pcm2uml

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.Model
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.junit.jupiter.api.BeforeEach
import java.nio.file.Path
import tools.vitruv.testutils.LegacyVitruvApplicationTest
import tools.vitruv.applications.pcmumlcomponents.PcmToUmlComponentsChangePropagationSpecification
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

abstract class AbstractPcmUmlTest extends LegacyVitruvApplicationTest {
	protected static val MODEL_FILE_EXTENSION = "repository"
	protected static val MODEL_NAME = "model"
	// private static val PRIMITIVETYPES_URI = "platform:/plugin/org.palladiosimulator.pcm.resources/defaultModels/PrimitiveTypes.repository"
	static val PRIMITIVETYPES_URI = "pathmap://PCM_MODELS/PrimitiveTypes.repository"

	protected val INTERFACE_NAME = "TestInterface"
	protected val OPERATION_NAME = "fooOperation"
	protected val OPERATION_NAME_2 = "barOperation"
	protected val PARAMETER_NAME = "fooParameter"
	protected val PARAMETER_NAME_2 = "barParameter"
	protected val ATTRIBUTE_NAME = "fooAttribute"

	protected static var Repository primitiveTypesRepository = null

	private def Path getProjectModelPath(String modelName) {
		Path.of("model").resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

	protected def Repository getRootElement() {
		return Repository.from(MODEL_NAME.projectModelPath)
	}

	override protected getChangePropagationSpecifications() {
		return #[new PcmToUmlComponentsChangePropagationSpecification()]
	}

	protected def void initializeTestModel() {
		resourceAt(MODEL_NAME.projectModelPath).startRecordingChanges => [
			contents += RepositoryFactory.eINSTANCE.createRepository() => [
				entityName = MODEL_NAME
			]
		]
		propagate
	}

	protected def Model getUmlModel() {
		return getCorrespondingEObjects(rootElement, Model).claimOne()
	}

	@BeforeEach
	def protected setup() {
		initializeTestModel()
	}

	protected def Repository loadPrimitiveTypes() {
		if (primitiveTypesRepository !== null) {
			return primitiveTypesRepository
		}

		val ResourceSet resSet = new ResourceSetImpl()
		return loadPrimitiveTypes(resSet)
	}

	protected def Repository loadPrimitiveTypes(ResourceSet resourceSet) {
		if (primitiveTypesRepository !== null) {
			return primitiveTypesRepository
		}
		val URI uri = URI.createURI(PRIMITIVETYPES_URI)

		val Resource resource = resourceSet.getResource(uri, true)

		primitiveTypesRepository = resource.contents.head as Repository
		return primitiveTypesRepository
	}

	protected def PrimitiveDataType getPrimitiveType(PrimitiveTypeEnum type) {
		return loadPrimitiveTypes().dataTypes__Repository.get(type.value) as PrimitiveDataType
	}

	protected def Iterable<EObject> correspondingElements(EObject element) {
		return getCorrespondingEObjects(element, EObject)
	}

}
