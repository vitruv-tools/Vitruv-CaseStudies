package tools.vitruv.applications.pcmumlcomp.uml2pcm

import org.eclipse.emf.common.util.BasicEList
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature

import static org.junit.Assert.*
import org.eclipse.uml2.uml.Operation
import tools.vitruv.applications.pcmumlcomp.uml2pcm.UmlToPcmUtil
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.apache.log4j.Logger
import org.apache.log4j.Level

class InterfacesTest extends AbstractUmlPcmTest {
	
	protected def Interface createUmlInterface(String name) {
		val umlInterface = UMLFactory.eINSTANCE.createInterface();
		umlInterface.name = name
		rootElement.packagedElements += umlInterface
		saveAndSynchronizeChanges(rootElement);
		return umlInterface
	}
	
	@Test
	public def void createInterfaceTest() {
		val interfaceName = "i1"
		val umlInterface = createUmlInterface(interfaceName)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlInterface]).flatten;
		assertEquals(1, correspondingElements.length);
		val pcmInterface = (correspondingElements.get(0) as OperationInterface)
		assertTrue(pcmInterface instanceof OperationInterface)
		assertEquals(interfaceName, pcmInterface.entityName)
	}
	
	@Test
	public def void createIntefaceOperation() {
		val logger = Logger.getLogger("")
		logger.level = Level.ALL
		val umlInterface = createUmlInterface("i2")
		val p1Type = UMLFactory.eINSTANCE.createPrimitiveType()
		p1Type.name = "t1";
		rootElement.packagedElements += p1Type
		/*val returnType = UMLFactory.eINSTANCE.createPrimitiveType()
		returnType.name = "Boolean"
		rootElement.packagedElements += returnType*/
		val parameterNames = new BasicEList<String>()
		parameterNames += "p1"
		val parameterTypes = new BasicEList<Type>()
		parameterTypes += p1Type
		// TODO: this is not picked up correctly and creates parameters without further properties in the saved .repository
		//val umlOperation = umlInterface.createOwnedOperation("o1", parameterNames, parameterTypes, returnType)
		val umlOperation = createInterfaceOperation(umlInterface, "o1", "Boolean")
		saveAndSynchronizeChanges(rootElement);

		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlOperation]).flatten;
		assertEquals(1, correspondingElements.length);
		val pcmOperation = (correspondingElements.get(0) as OperationSignature)
		assertTrue(pcmOperation instanceof OperationSignature)
		assertEquals(umlOperation.name, pcmOperation.entityName)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(umlOperation.type.name),
			(pcmOperation.returnType__OperationSignature as PrimitiveDataType).type)
			
		//assertEquals(1, pcmOperation.parameters__OperationSignature.length)
		//assertEquals(parameterNames.get(0), pcmOperation.parameters__OperationSignature.get(0).entityName)
	}
	
	protected def Operation createInterfaceOperation(Interface umlInterface, String operationName, String operationType) {
		val returnType = UMLFactory.eINSTANCE.createPrimitiveType()
		returnType.name = operationType
		rootElement.packagedElements += returnType
		val umlOperation = UMLFactory.eINSTANCE.createOperation()
		umlOperation.name = operationName
		umlOperation.type = returnType
		umlInterface.ownedOperations += umlOperation
		saveAndSynchronizeChanges(rootElement)
		return umlOperation
	}
	
	@Test
	public def void changeInterfaceOperation() {
		val umlInterface = createUmlInterface("i3")
		val umlOperation = createInterfaceOperation(umlInterface, "o1", "Boolean")
		var correspondingSignatures = correspondenceModel.getCorrespondingEObjects(#[umlOperation]).flatten
		var pcmSignature = (correspondingSignatures.get(0) as OperationSignature)
		umlOperation.name = "o11"
		saveAndSynchronizeChanges(umlOperation)
		assertEquals(umlOperation.name, pcmSignature.entityName)
		val newType = UMLFactory.eINSTANCE.createPrimitiveType()
		newType.name = "Integer"
		rootElement.packagedElements += newType
		umlOperation.type = newType
		saveAndSynchronizeChanges(umlOperation)
		
		correspondingSignatures = correspondenceModel.getCorrespondingEObjects(#[umlOperation]).flatten
		pcmSignature = (correspondingSignatures.get(0) as OperationSignature)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(newType.name),
			(pcmSignature.returnType__OperationSignature as PrimitiveDataType).type)
			
		umlOperation.type = null
		saveAndSynchronizeChanges(umlOperation)
		correspondingSignatures = correspondenceModel.getCorrespondingEObjects(#[umlOperation]).flatten
		pcmSignature = (correspondingSignatures.get(0) as OperationSignature)
		assertNull(pcmSignature.returnType__OperationSignature)
	}
	
	protected def Parameter createParameter(Operation umlOperation, String name, String type) {
		val parameterType = UMLFactory.eINSTANCE.createPrimitiveType()
		parameterType.name = type
		rootElement.packagedElements += parameterType 
		val umlParameter = UMLFactory.eINSTANCE.createParameter()
		umlParameter.name = name
		umlParameter.type = parameterType
		umlOperation.ownedParameters += umlParameter
		saveAndSynchronizeChanges(rootElement)
		return umlParameter
	}
	
	@Test
	public def void addOperationParameter() {
		val umlInterface = createUmlInterface("i4")
		val umlOperation = createInterfaceOperation(umlInterface, "o1", "Boolean")
		val umlParameter = createParameter(umlOperation, "p1", "Integer")
		
		val pcmSignature = getCorrespondingSignature(umlOperation)
		assertEquals(1, pcmSignature.parameters__OperationSignature.length)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlParameter]).flatten
		assertEquals(1, correspondingElements.length)
		assertTrue(correspondingElements.get(0) instanceof org.palladiosimulator.pcm.repository.Parameter)
		val pcmParameter = (correspondingElements.get(0) as org.palladiosimulator.pcm.repository.Parameter)
		assertEquals(umlParameter.name, pcmParameter.parameterName)
		assertEquals(umlParameter.name, pcmParameter.entityName)
		// TODO: pcm modifier is not set or explicitly changed per default, uml sets IN as default
		// assertEquals(UmlToPcmUtil.getPcmParameterModifier(umlParameter.direction), pcmParameter.modifier__Parameter)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(umlParameter.type.name), (pcmParameter.dataType__Parameter as PrimitiveDataType).type)
	}
	
	protected def OperationSignature getCorrespondingSignature(Operation operation) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[operation]).flatten
		return (correspondingElements.get(0) as OperationSignature)
	}
	
	protected def org.palladiosimulator.pcm.repository.Parameter getCorrespondingParameter(Parameter umlParameter) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlParameter]).flatten
		return (correspondingElements.get(0) as org.palladiosimulator.pcm.repository.Parameter)
	}
	
	@Test
	public def void changeOperationParameter() {
		val umlInterface = createUmlInterface("i4")
		val umlOperation = createInterfaceOperation(umlInterface, "o1", "Boolean")
		val umlParameter = createParameter(umlOperation, "p1", "Integer")
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlParameter]).flatten
		assertEquals(1, correspondingElements.length)
		assertTrue(correspondingElements.get(0) instanceof org.palladiosimulator.pcm.repository.Parameter)
		var pcmParameter = (correspondingElements.get(0) as org.palladiosimulator.pcm.repository.Parameter)
		
		val newName = "p11"
		umlParameter.name = newName
		saveAndSynchronizeChanges(umlParameter)
		pcmParameter = getCorrespondingParameter(umlParameter)
		assertEquals(newName, pcmParameter.parameterName)
		
		val newType = umlOperation.type
		umlParameter.type = newType
		saveAndSynchronizeChanges(umlParameter)
		pcmParameter = getCorrespondingParameter(umlParameter)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(newType.name), (pcmParameter.dataType__Parameter as PrimitiveDataType).type)
		
		umlParameter.direction = ParameterDirectionKind.INOUT_LITERAL
		saveAndSynchronizeChanges(umlParameter)
		pcmParameter = getCorrespondingParameter(umlParameter)
		assertEquals(UmlToPcmUtil.getPcmParameterModifier(umlParameter.direction), pcmParameter.modifier__Parameter)
	}
	
	@Test
	public def void deleteOperationParameter() {
		val umlInterface = createUmlInterface("i5")
		val umlOperation = createInterfaceOperation(umlInterface, "o1", "Boolean")
		val parameter1 = createParameter(umlOperation, "p1", "Integer")
		val remainingParameterName = "p2"
		createParameter(umlOperation, remainingParameterName, "String")
		
		var pcmSignature = getCorrespondingSignature(umlOperation)
		
		assertEquals(2, pcmSignature.parameters__OperationSignature.length)
		
		umlOperation.ownedParameters -= parameter1
		saveAndSynchronizeChanges(umlOperation)
		
		pcmSignature = getCorrespondingSignature(umlOperation)
		assertEquals(1, pcmSignature.parameters__OperationSignature.length)
		assertEquals(remainingParameterName, pcmSignature.parameters__OperationSignature.get(0).entityName)
		
		umlOperation.ownedParameters.remove(0)
		saveAndSynchronizeChanges(umlOperation)
		
		pcmSignature = getCorrespondingSignature(umlOperation)
		assertEquals(1, pcmSignature.parameters__OperationSignature.length)
		assertNull(pcmSignature.returnType__OperationSignature)
	}
	
}