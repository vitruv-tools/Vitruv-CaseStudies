package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaInterfaceBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.MethodDescription
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.ParameterDescription

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createCompositeDataType
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationInterface
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationSignature
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createParameter
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createPrimitiveDataType
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmRepository
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.createBoolean
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.createInt
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.createVoid
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.getReference

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimCompositeDataType
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimOperationInterface
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimPrimitiveDataType

class PcmParameterMappingTransformationTest extends Pcm2JavaTransformationTest {

	static final String COMPOSITE_PARAMETER_NAME_1 = "compositeParam1"
	static final String COMPOSITE_PARAMETER_NAME_2 = "compositeParam2"
	static final String COMPOSITE_DATATYPE_NAME_1 = Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + "_1"
	static final String COMPOSITE_DATATYPE_NAME_2 = Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + "_2"

	@Test
	def void testAddParameter() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface +=
					createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				interfaces__Repository += interface
				dataTypes__Repository += createPrimitiveDataType(PrimitiveTypeEnum.INT)
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val dataType = claimPrimitiveDataType(PrimitiveTypeEnum.INT)
				val signature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).
					signatures__OperationInterface.claimOne
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType
					modifier__Parameter = ParameterModifier.IN
					parameterName = Pcm2JavaTestUtils.PARAMETER_NAME
				]
			]
		]

		validateJavaView [
			val parameters = List.of(new ParameterDescription(Pcm2JavaTestUtils.PARAMETER_NAME, createInt()))
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), parameters)).
				build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit))
		]
	}

	@Test
	def void testChangeParameterName() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val dataType = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				dataTypes__Repository += dataType
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType
					modifier__Parameter = ParameterModifier.IN
					parameterName = Pcm2JavaTestUtils.PARAMETER_NAME
				]
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += signature
				interfaces__Repository += interface
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val parameter = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).
					signatures__OperationInterface.claimOne.parameters__OperationSignature.claimOne
				parameter.parameterName = Pcm2JavaTestUtils.PARAMETER_NAME + Pcm2JavaTestUtils.PARAMETER_NAME
			]
		]

		validateJavaView [
			val parameters = List.of(
				new ParameterDescription(Pcm2JavaTestUtils.PARAMETER_NAME + Pcm2JavaTestUtils.PARAMETER_NAME,
					createInt()))
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), parameters)).
				build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit))
		]
	}

	@Test
	def void testChangeParameterType() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val dataType1 = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				val dataType2 = createPrimitiveDataType(PrimitiveTypeEnum.BOOL)
				dataTypes__Repository += dataType1
				dataTypes__Repository += dataType2
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType1
					modifier__Parameter = ParameterModifier.IN
					parameterName = Pcm2JavaTestUtils.PARAMETER_NAME
				]
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += signature
				interfaces__Repository += interface
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val dataType2 = claimPrimitiveDataType(PrimitiveTypeEnum.BOOL)
				val parameter = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).
					signatures__OperationInterface.claimOne.parameters__OperationSignature.claimOne
				parameter.dataType__Parameter = dataType2
			]
		]

		validateJavaView [
			val parameters = List.of(new ParameterDescription(Pcm2JavaTestUtils.PARAMETER_NAME, createBoolean()))
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), parameters)).
				build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit))
		]
	}

	@Test
	def void testAddParameterWithCompositeDataType() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface +=
					createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				interfaces__Repository += interface
				dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val compositeDataType = claimCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				val signature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).
					signatures__OperationInterface.claimOne
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = compositeDataType
					modifier__Parameter = ParameterModifier.IN
					parameterName = Pcm2JavaTestUtils.PARAMETER_NAME
				]
			]
		]

		validateJavaView [
			val compositeDataTypeCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			val parameters = List.of(
				new ParameterDescription(Pcm2JavaTestUtils.PARAMETER_NAME,
					getReference(compositeDataTypeCompilationUnit)))
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), parameters)).
				build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(interfaceCompilationUnit, compositeDataTypeCompilationUnit))
		]
	}

	@Test
	def void testRemoveParameter() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val dataType1 = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				val dataType2 = createPrimitiveDataType(PrimitiveTypeEnum.BOOL)
				dataTypes__Repository += dataType1
				dataTypes__Repository += dataType2
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType1
					modifier__Parameter = ParameterModifier.IN
					parameterName = Pcm2JavaTestUtils.PARAMETER_NAME
				]
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += signature
				interfaces__Repository += interface
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).
					signatures__OperationInterface.claimOne
				val parameter = signature.parameters__OperationSignature.claimOne
				signature.parameters__OperationSignature.remove(parameter)
			]
		]

		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).
				build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit))
		]
	}

	@Test
	def void testAddMultipleParameters() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface +=
					createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				interfaces__Repository += interface
				val dataType1 = createCompositeDataType(COMPOSITE_DATATYPE_NAME_1)
				val dataType2 = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				val dataType3 = createCompositeDataType(COMPOSITE_DATATYPE_NAME_2)
				dataTypes__Repository += #[dataType1, dataType2, dataType3]
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val dataType1 = claimCompositeDataType(COMPOSITE_DATATYPE_NAME_1)
				val dataType2 = claimPrimitiveDataType(PrimitiveTypeEnum.INT)
				val dataType3 = claimCompositeDataType(COMPOSITE_DATATYPE_NAME_2)
				val signature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).
					signatures__OperationInterface.claimOne
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType1
					modifier__Parameter = ParameterModifier.IN
					parameterName = COMPOSITE_PARAMETER_NAME_1
				]
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType2
					modifier__Parameter = ParameterModifier.IN
					parameterName = Pcm2JavaTestUtils.PARAMETER_NAME
				]
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType3
					modifier__Parameter = ParameterModifier.IN
					parameterName = COMPOSITE_PARAMETER_NAME_2
				]
			]
		]

		validateJavaView [
			val dataType1CompilationUnit = new FluentJavaClassBuilder(
				COMPOSITE_DATATYPE_NAME_1,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			val dataType3CompilationUnit = new FluentJavaClassBuilder(
				COMPOSITE_DATATYPE_NAME_2,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			val parameters = List.of(
				new ParameterDescription(COMPOSITE_PARAMETER_NAME_1, getReference(dataType1CompilationUnit)),
				new ParameterDescription(Pcm2JavaTestUtils.PARAMETER_NAME, createInt()),
				new ParameterDescription(COMPOSITE_PARAMETER_NAME_2, getReference(dataType3CompilationUnit))
			)
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), parameters)).
				build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(interfaceCompilationUnit, dataType1CompilationUnit, dataType3CompilationUnit))
		]
	}

	@Test
	def void testAddMultipleParametersAndRemoveOne() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val dataType1 = createCompositeDataType(COMPOSITE_DATATYPE_NAME_1)
				val dataType2 = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				val dataType3 = createCompositeDataType(COMPOSITE_DATATYPE_NAME_2)
				dataTypes__Repository += #[dataType1, dataType2, dataType3]
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType1
					modifier__Parameter = ParameterModifier.IN
					parameterName = COMPOSITE_PARAMETER_NAME_1
				]
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType2
					modifier__Parameter = ParameterModifier.IN
					parameterName = Pcm2JavaTestUtils.PARAMETER_NAME
				]
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType3
					modifier__Parameter = ParameterModifier.IN
					parameterName = COMPOSITE_PARAMETER_NAME_2
				]
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interfaces__Repository += interface
				interface.signatures__OperationInterface += signature
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).
					signatures__OperationInterface.claimOne
				signature.parameters__OperationSignature.remove(1)
			]
		]

		validateJavaView [
			val dataType1CompilationUnit = new FluentJavaClassBuilder(
				COMPOSITE_DATATYPE_NAME_1,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			val dataType3CompilationUnit = new FluentJavaClassBuilder(
				COMPOSITE_DATATYPE_NAME_2,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			val parameters = List.of(
				new ParameterDescription(COMPOSITE_PARAMETER_NAME_1, getReference(dataType1CompilationUnit)),
				new ParameterDescription(COMPOSITE_PARAMETER_NAME_2, getReference(dataType3CompilationUnit))
			)
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), parameters)).
				build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(interfaceCompilationUnit, dataType1CompilationUnit, dataType3CompilationUnit))
		]
	}

	@Disabled("TODO: adapt reactions")
	static class BidirectionalTest extends PcmParameterMappingTransformationTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}
}
