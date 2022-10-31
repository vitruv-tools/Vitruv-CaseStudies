package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.MethodDescription
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.ParameterDescription

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaInterfaceBuilder

class PcmParameterMappingTransformationTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testAddParameter() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				interfaces__Repository += interface
				dataTypes__Repository += createPrimitiveDataType(PrimitiveTypeEnum.INT)
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val dataType = claimPrimitiveDataType(PrimitiveTypeEnum.INT)
				val signature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).signatures__OperationInterface.claimOne
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType
					modifier__Parameter = ParameterModifier.IN
					parameterName = Pcm2JavaTestUtils.PARAMETER_NAME
				]
			]
		]
		
		validateJavaView [
			val parameters = List.of(new ParameterDescription(Pcm2JavaTestUtils.PARAMETER_NAME, createInt()))
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), parameters))
				.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit))
		]
	}
	
	@Test
	def void testChangeParameterName() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val dataType = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				dataTypes__Repository += dataType
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
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
			modifySingleRepository [
				val parameter = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).signatures__OperationInterface.claimOne.parameters__OperationSignature.claimOne
				parameter.parameterName = Pcm2JavaTestUtils.PARAMETER_NAME + Pcm2JavaTestUtils.PARAMETER_NAME
			]
		]
		
		validateJavaView [
			val parameters = List.of(new ParameterDescription(Pcm2JavaTestUtils.PARAMETER_NAME + Pcm2JavaTestUtils.PARAMETER_NAME, createInt()))
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), parameters))
				.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit))
		]
	}
	
	@Test
	def void testChangeParameterType() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val dataType1 = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				val dataType2 = createPrimitiveDataType(PrimitiveTypeEnum.BOOL)
				dataTypes__Repository += dataType1
				dataTypes__Repository += dataType2
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
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
			modifySingleRepository [
				val dataType2 = claimPrimitiveDataType(PrimitiveTypeEnum.BOOL)
				val parameter = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).signatures__OperationInterface.claimOne.parameters__OperationSignature.claimOne
				parameter.dataType__Parameter = dataType2
			]
		]
		
		validateJavaView [
			val parameters = List.of(new ParameterDescription(Pcm2JavaTestUtils.PARAMETER_NAME, createBoolean()))
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), parameters))
				.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit))
		]
	}
	
	@Test
	def void testAddParameterWithCompositeDataType() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				interfaces__Repository += interface
				dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val compositeDataType = claimCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				val signature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).signatures__OperationInterface.claimOne
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
			val parameters = List.of(new ParameterDescription(Pcm2JavaTestUtils.PARAMETER_NAME, getReference(compositeDataTypeCompilationUnit)))
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), parameters))
				.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit, compositeDataTypeCompilationUnit))
		]
	}
	
	@Test
	def void testRemoveParameter() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val dataType1 = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				val dataType2 = createPrimitiveDataType(PrimitiveTypeEnum.BOOL)
				dataTypes__Repository += dataType1
				dataTypes__Repository += dataType2
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
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
			modifySingleRepository [
				val signature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).signatures__OperationInterface.claimOne
				val parameter = signature.parameters__OperationSignature.claimOne
				signature.parameters__OperationSignature.remove(parameter)
			]
		]
		
		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), List.of()))
				.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit))
		]
	}
	
	@Test
	def void testAddMultipleParameters() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				interfaces__Repository += interface
				val dataType1 = createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				val dataType2 = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				val dataType3 = createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + "_2")
				dataTypes__Repository += #[dataType1, dataType2, dataType3]
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val dataType1 = claimCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				val dataType2 = claimPrimitiveDataType(PrimitiveTypeEnum.INT)
				val dataType3 = claimCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + "_2")
				val signature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).signatures__OperationInterface.claimOne
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType1
					modifier__Parameter = ParameterModifier.IN
					parameterName = "compositeParam1"
				]
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType2
					modifier__Parameter = ParameterModifier.IN
					parameterName = Pcm2JavaTestUtils.PARAMETER_NAME
				]
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType3
					modifier__Parameter = ParameterModifier.IN
					parameterName = "compositeParam2"
				]
			]
		]
		
		validateJavaView [
			val dataType1CompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			val dataType3CompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + "_2",
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			val parameters = List.of(
				new ParameterDescription("compositeParam1", getReference(dataType1CompilationUnit)),
				new ParameterDescription(Pcm2JavaTestUtils.PARAMETER_NAME, createInt()),
				new ParameterDescription("compositeParam2", getReference(dataType3CompilationUnit))
			)
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), parameters))
				.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit, dataType1CompilationUnit, dataType3CompilationUnit))
		]
	}
	
	@Test
	def void testAddMultipleParametersAndRemoveOne() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val dataType1 = createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				val dataType2 = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				val dataType3 = createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + "_2")
				dataTypes__Repository += #[dataType1, dataType2, dataType3]
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType1
					modifier__Parameter = ParameterModifier.IN
					parameterName = "compositeParam1"
				]
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType2
					modifier__Parameter = ParameterModifier.IN
					parameterName = Pcm2JavaTestUtils.PARAMETER_NAME
				]
				signature.parameters__OperationSignature += createParameter[
					dataType__Parameter = dataType3
					modifier__Parameter = ParameterModifier.IN
					parameterName = "compositeParam2"
				]
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interfaces__Repository += interface
				interface.signatures__OperationInterface += signature
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val signature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).signatures__OperationInterface.claimOne
				signature.parameters__OperationSignature.remove(1)
			]
		]
		
		validateJavaView [
			val dataType1CompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			val dataType3CompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + "_2",
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			val parameters = List.of(
				new ParameterDescription("compositeParam1", getReference(dataType1CompilationUnit)),
				new ParameterDescription("compositeParam2", getReference(dataType3CompilationUnit))
			)
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), parameters))
				.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit, dataType1CompilationUnit, dataType3CompilationUnit))
		]
	}
}