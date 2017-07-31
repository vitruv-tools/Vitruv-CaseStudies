package tools.vitruv.applications.pcmumlcomponents.both.tests

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.Model

import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory

import tools.vitruv.applications.pcmumlcomponents.pcm2uml.PcmToUmlComponentsChangePropagationSpecification
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.UmlToPcmComponentsChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.tests.VitruviusApplicationTest

class AbstractPcmUmlBothDirectionsTest extends VitruviusApplicationTest {
	protected static extension RepositoryFactory = RepositoryFactory::eINSTANCE
	protected static val MODEL_FILE_EXTENSION = "repository"
	protected static val MODEL_NAME = "model"
	protected val ATTRIBUTE_NAME = "fooAttribute"
	protected val INTERFACE_NAME = "TestInterface"
	protected val OPERATION_NAME = "fooOperation"
	protected val OPERATION_NAME_2 = "barOperation"
	protected val PARAMETER_NAME = "fooParameter"
	protected val PARAMETER_NAME_2 = "barParameter"
	static val PRIMITIVETYPES_URI = "pathmap://PCM_MODELS/PrimitiveTypes.repository"
// static val PRIMITIVETYPES_URI = "platform:/plugin/org.palladiosimulator.pcm.resources/defaultModels/PrimitiveTypes.repository"
	protected static Repository primitiveTypesRepository = null

	override unresolveChanges() { false }

	override cleanup() {}

	override setup() {
		initializeTestModel
	}

	override createChangePropagationSpecifications() {
		return #[
			new UmlToPcmComponentsChangePropagationSpecification,
			new PcmToUmlComponentsChangePropagationSpecification
		]
	}

	override getVitruvDomains() {
		return #[
			new UmlDomainProvider().domain,
			new PcmDomainProvider().domain
		]
	}

	protected def initializeTestModel() {
		val pcmRepository = createRepository
		pcmRepository.entityName = MODEL_NAME
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, pcmRepository)
	}

	protected def Model getUmlModel() {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[rootElement]).flatten
		return (correspondingElements.get(0) as Model)
	}

	protected def Repository getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as Repository
	}

	protected def Repository loadPrimitiveTypes() {
		if (primitiveTypesRepository !== null)
			return primitiveTypesRepository

		val ResourceSet resSet = new ResourceSetImpl
		return loadPrimitiveTypes(resSet)
	}

	protected def Repository loadPrimitiveTypes(ResourceSet resourceSet) {
		if (primitiveTypesRepository !== null)
			return primitiveTypesRepository
		val uri = URI::createURI(PRIMITIVETYPES_URI)

		val resource = resourceSet.getResource(uri, true)

		primitiveTypesRepository = resource.contents.head as Repository
		return primitiveTypesRepository
	}

	protected def PrimitiveDataType getPrimitiveType(PrimitiveTypeEnum type) {
		return loadPrimitiveTypes.dataTypes__Repository.get(type.value) as PrimitiveDataType
	}

	protected def Iterable<EObject> correspondingElements(EObject element) {
		return correspondenceModel.getCorrespondingEObjects(#[element]).flatten
	}

	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION
	}
}
