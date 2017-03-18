package tool.vitruv.applications.pcmumlcomp.uml2pcm

import org.eclipse.emf.common.util.BasicEList
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature

import static org.junit.Assert.*
import org.eclipse.uml2.uml.Operation
import tools.vitruv.aplications.pcmumlcomp.uml2pcm.UmlToPcmUtil
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.ParameterDirectionKind

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
	public def void createIntefaceSignature() {
		val umlInterface = createUmlInterface("i2")
		val p1Type = UMLFactory.eINSTANCE.createPrimitiveType()
		p1Type.name = "t1";
		rootElement.packagedElements += p1Type
		val returnType = UMLFactory.eINSTANCE.createPrimitiveType()
		returnType.name = "Boolean"
		rootElement.packagedElements += returnType
		val parameters = new BasicEList<String>()
		parameters += "p1"
		val parameterTypes = new BasicEList<Type>()
		parameterTypes += p1Type
		val umlOperation = umlInterface.createOwnedOperation("o1", parameters, parameterTypes, returnType)
		saveAndSynchronizeChanges(rootElement);

		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlOperation]).flatten;
		assertEquals(1, correspondingElements.length);
		val pcmOperation = (correspondingElements.get(0) as OperationSignature)
		assertTrue(pcmOperation instanceof OperationSignature)
		assertEquals(umlOperation.name, pcmOperation.entityName)
		println("createInterfaceSignature")
		println(returnType)
		println(pcmOperation.returnType__OperationSignature)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(returnType.name),
			(pcmOperation.returnType__OperationSignature as PrimitiveDataType).type)
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
	public def void changeInterfaceSignature() {
		val umlInterface = createUmlInterface("i3")
		val umlOperation = createInterfaceOperation(umlInterface, "o1", "Boolean")
		val correspondingSignatures = correspondenceModel.getCorrespondingEObjects(#[umlOperation]).flatten
		val pcmSignature = (correspondingSignatures.get(0) as OperationSignature)
		umlOperation.name = "o11"
		println("change interface signature")
		println(pcmSignature.returnType__OperationSignature)
		println((pcmSignature.returnType__OperationSignature as PrimitiveDataType).type)
		saveAndSynchronizeChanges(umlOperation)
		assertEquals(umlOperation.name, pcmSignature.entityName)
		val newType = UMLFactory.eINSTANCE.createPrimitiveType()
		newType.name = "Integer"
		rootElement.packagedElements += newType
		umlOperation.type = newType
		saveAndSynchronizeChanges(umlOperation)
		
		saveAndSynchronizeChanges(rootElement)
		println(pcmSignature.returnType__OperationSignature)
		println((pcmSignature.returnType__OperationSignature as PrimitiveDataType).type)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(newType.name),
			(pcmSignature.returnType__OperationSignature as PrimitiveDataType).type)
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
	public def void addSignatureParameter() {
		val umlInterface = createUmlInterface("i4")
		val umlOperation = createInterfaceOperation(umlInterface, "o1", "Boolean")
		val umlParameter = createParameter(umlOperation, "p1", "Integer")
		
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
	
	@Test
	public def void changeSignatureParameter() {
		val umlInterface = createUmlInterface("i4")
		val umlOperation = createInterfaceOperation(umlInterface, "o1", "Boolean")
		val umlParameter = createParameter(umlOperation, "p1", "Integer")
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlParameter]).flatten
		assertEquals(1, correspondingElements.length)
		assertTrue(correspondingElements.get(0) instanceof org.palladiosimulator.pcm.repository.Parameter)
		val pcmParameter = (correspondingElements.get(0) as org.palladiosimulator.pcm.repository.Parameter)
		
		val newName = "p11"
		umlParameter.name = newName
		saveAndSynchronizeChanges(umlParameter)
		assertEquals(newName, pcmParameter.parameterName)
		
		val newType = umlOperation.type
		umlParameter.type = newType
		saveAndSynchronizeChanges(umlParameter)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(newType.name), (pcmParameter.dataType__Parameter as PrimitiveDataType).type)
		
		umlParameter.direction = ParameterDirectionKind.INOUT_LITERAL
		saveAndSynchronizeChanges(umlParameter)
		assertEquals(UmlToPcmUtil.getPcmParameterModifier(umlParameter.direction), pcmParameter.modifier__Parameter)
	}
	
}