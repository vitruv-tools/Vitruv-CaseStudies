package tools.vitruv.applications.pcmjava.ejbtransformations.editortests.java2pcm

import tools.vitruv.applications.pcmjava.tests.util.pcm2java.Pcm2JavaTestUtils
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum

import tools.vitruv.applications.pcmjava.tests.util.java2pcm.CompilationUnitManipulatorHelper
import org.eclipse.jdt.core.IMethod
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class EjbInterfaceMappingTest extends EjbJava2PcmTransformationTest {

	@Test
	def testCreateInterfaceAndAddRemoteAnnotation() {
		super.addRepoContractsAndDatatypesPackage()

		val correspondingOpInterface = createEjbInterface(TEST_INTERFACE_NAME)

		assertEquals(correspondingOpInterface.entityName, TEST_INTERFACE_NAME,
			"Created component has different name as added class")
	}

	@Test
	def testCreateMethodInEjbInterface() {
		val correspondingOpSignature = createPackageEjbInterrfaceAndInterfaceMethod()

		assertEquals(correspondingOpSignature.entityName, Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME,
			"OperationSiganture has not the expected name")
	}

	@Test
	def testCreateParameterInMethodInEjbInterface() {
		val correspondingOpSignature = createPackageEjbInterrfaceAndInterfaceMethod()

		val pcmParam = super.addParameterToSignature(TEST_INTERFACE_NAME, correspondingOpSignature.entityName, "byte[]",
			"data", null)

		assertPCMParam(pcmParam, "data", PrimitiveTypeEnum.BYTE)
	}

	@Test
	def testSetReturnTypeInMethodInEjbInterface() {
		val correspondingOpSignature = createPackageEjbInterrfaceAndInterfaceMethod()

		val opSignature = addReturnTypeToSignature(TEST_INTERFACE_NAME, correspondingOpSignature.entityName, "byte[]",
			null)

		assertEquals(opSignature.entityName, correspondingOpSignature.entityName, "Wrong signature changed")
		assertTrue(opSignature.returnType__OperationSignature instanceof CollectionDataType,
			"OpSignature returnType is not a collection Data type")
		val CollectionDataType cdt = opSignature.returnType__OperationSignature as CollectionDataType
		assertTrue(cdt.innerType_CollectionDataType instanceof PrimitiveDataType,
			"OpSignature returnType: InnerDatatype is not a primitive type")
		val primitiveDataType = cdt.innerType_CollectionDataType as PrimitiveDataType
		assertEquals(PrimitiveTypeEnum.BYTE, primitiveDataType.type,
			"OpSignature returnType : InnerDatatype is from type BYTE")
	}

	@Test
	def testCreateMultipleParametersInMethodInEjbInterface() {
		val correspondingOpSignature = createPackageEjbInterrfaceAndInterfaceMethod()

		val name = "data"
		val typeName = "byte[]"
		val pcmParam = super.addParameterToSignature(TEST_INTERFACE_NAME, correspondingOpSignature.entityName, typeName,
			name, null)
		assertPCMParam(pcmParam, name, PrimitiveTypeEnum.BYTE)

		val icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(TEST_INTERFACE_NAME,
			this.currentTestProject)
		val IMethod iMethod = icu.getType(TEST_INTERFACE_NAME).methods.get(0)
		val secondParamname = "additionalData"
		val typeStringName = "String[]"
		val String secondParameterStr = ", " + typeStringName + " " + secondParamname
		val secondPcmParam = super.insertParameterIntoSignature(correspondingOpSignature.entityName, secondParamname,
			icu, iMethod, secondParameterStr)
		assertPCMParam(secondPcmParam, secondParamname, PrimitiveTypeEnum.STRING)

	}

	private def createPackageEjbInterrfaceAndInterfaceMethod() {
		super.createPackageAndEjbInterface()
		val correspondingOpSignature = super.addMethodToInterfaceWithCorrespondence(TEST_INTERFACE_NAME)
		correspondingOpSignature
	}

	private def assertPCMParam(Parameter pcmParam, String expectedName, PrimitiveTypeEnum expectedPrimiteveTypeEnum) {
		assertEquals(expectedName, pcmParam.parameterName, "PCM Parameter has not the expected name")
		// expecting type byte[]	
		assertTrue(pcmParam.dataType__Parameter instanceof CollectionDataType,
			"PCM Parameter Type is not a collection Data type")
		val CollectionDataType cdt = pcmParam.dataType__Parameter as CollectionDataType
		assertTrue(cdt.innerType_CollectionDataType instanceof PrimitiveDataType,
			"PCM Parameter Type: InnerDatatype is not a primitive type")
		val primitiveDataType = cdt.innerType_CollectionDataType as PrimitiveDataType
		assertEquals(expectedPrimiteveTypeEnum, primitiveDataType.type, "PCM Parameter Type: wrong InnerDatatype")
	}

}
