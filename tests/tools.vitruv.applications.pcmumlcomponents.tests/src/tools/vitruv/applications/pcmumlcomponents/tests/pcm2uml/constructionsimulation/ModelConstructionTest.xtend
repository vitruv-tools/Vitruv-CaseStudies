package tools.vitruv.applications.pcmumlcomponents.tests.pcm2uml.constructionsimulation

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.resource.Resource
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.applications.pcmumlcomponents.tests.pcm2uml.AbstractPcmUmlTest
import tools.vitruv.domains.pcm.util.RepositoryModelLoader
import org.eclipse.uml2.uml.Model
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.InnerDeclaration
import org.eclipse.emf.ecore.EObject
import org.palladiosimulator.pcm.repository.Interface
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature
import org.eclipse.uml2.uml.Operation
import org.palladiosimulator.pcm.repository.Parameter
import org.eclipse.emf.common.util.URI

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals

abstract class ModelConstructionTest extends AbstractPcmUmlTest {
		
	protected static val Logger logger = Logger.getLogger(ModelConstructionTest)
		
	protected val TARGET_MODEL_NAME = "model/model.repository"
	
	protected def Resource loadModel(String path) {
		return RepositoryModelLoader.loadPcmResource(URI.createURI(path))
	}
	
	protected def Repository getRootElement(Resource resource) {
		return resource.allContents.head as Repository
	}
	
	protected def Repository constructRepository(Repository inputRepository) {
		createAndSynchronizeModel(TARGET_MODEL_NAME, inputRepository)
		return inputRepository
	}
	
	override protected initializeTestModel() {
		
	}
	
	protected def <T> returnType(EObject eobj, Class<T> type) {
		assertTrue(type.isAssignableFrom(eobj.class))
		return type.cast(eobj)
	}
	
	protected def void validateCorrespondence(Logger logger, Parameter pcmParameter) {
		logger.debug("Checking for correspondence for pcm::Parameter " + pcmParameter.parameterName)
		val correspondingElements = pcmParameter.correspondingElements
		assertEquals(1, correspondingElements.length)
		val umlParameter = correspondingElements.head.returnType(org.eclipse.uml2.uml.Parameter)
		assertEquals(pcmParameter.parameterName, umlParameter.name)
	}
	
	protected def void validateCorrespondence(Logger logger, OperationSignature pcmSignature) {
		logger.debug("Checking for correspondence for pcm::OperationSignature " + pcmSignature.entityName)
		val correspondingElements = pcmSignature.correspondingElements
		assertEquals(1, correspondingElements.length)
		val umlSignature = correspondingElements.head.returnType(Operation)
		assertEquals(pcmSignature.entityName, umlSignature.name)
		for (parameter : pcmSignature.parameters__OperationSignature)
			validateCorrespondence(logger, parameter)
	}
	
	protected def void validateCorrespondence(Logger logger, Interface pcmInterface) {
		logger.debug("Checking for correspondence for pcm::Interface " + pcmInterface.entityName)
		if (pcmInterface instanceof OperationInterface) {
			val correspondingElements = pcmInterface.correspondingElements
			assertEquals(1, correspondingElements.length)
			val umlInterface = correspondingElements.head.returnType(org.eclipse.uml2.uml.Interface)
			assertEquals(pcmInterface.entityName, umlInterface.name)
			for (signature : pcmInterface.signatures__OperationInterface)
				validateCorrespondence(logger, signature)
		} else {
			logger.debug("Non-OperationInterface - skipping")
			return
		}
	}
	
	protected def void validateCorrespondence(Logger logger, InnerDeclaration innerDeclaration) {
		logger.debug("Checking for correspondence for pcm::InnerDeclaration " + innerDeclaration.entityName)
		val correspondingElements = innerDeclaration.correspondingElements
		assertEquals(1, correspondingElements.length)
		val umlProperty = correspondingElements.head.returnType(org.eclipse.uml2.uml.Property)
		assertEquals(innerDeclaration.entityName, umlProperty.name)
	}
	
	protected def void validateCorrespondence(Logger logger, DataType dataType) {
		logger.debug("Checking for correspondence for pcm::DataType")
		val correspondingElements = dataType.correspondingElements
		if (dataType instanceof CollectionDataType) {
			assertEquals(0, correspondingElements.length)
		}
		if (dataType instanceof CompositeDataType) {
			assertEquals(1, correspondingElements.length)
			assertTrue(correspondingElements.head instanceof org.eclipse.uml2.uml.DataType)
			val umlType = correspondingElements.head as org.eclipse.uml2.uml.DataType
			assertEquals(dataType.entityName, umlType.name)
			for (innerDeclaration : dataType.innerDeclaration_CompositeDataType) {
				validateCorrespondence(logger, innerDeclaration)
			}
		}
	}
	
	protected def void validateCorrespondence(Logger logger, Repository repository) {
		logger.debug("Checking for correspondence for pcm::Repository " + repository.entityName)
		val correspondingElements = repository.correspondingElements
		assertEquals(1, correspondingElements.length)
		assertTrue(correspondingElements.get(0) instanceof Model)
		for (dataType : repository.dataTypes__Repository)
			validateCorrespondence(logger, dataType)
		for (interface : repository.interfaces__Repository)
			validateCorrespondence(logger, interface)
	}
	
}