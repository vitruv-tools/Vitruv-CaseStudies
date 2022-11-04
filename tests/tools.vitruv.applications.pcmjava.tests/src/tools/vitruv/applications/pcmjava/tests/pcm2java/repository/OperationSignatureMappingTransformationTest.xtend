package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.MethodDescription

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaInterfaceBuilder

class OperationSignatureMappingTransformationTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testAddOperationSignature() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interfaces__Repository += interface
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val interface = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
			]
		]
		
		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), List.of()))
				.build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit))
		]
	}
	
	@Test
	def void testRenameOperationSignature() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				interfaces__Repository += interface
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val operationSignature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).signatures__OperationInterface.claimOne
				operationSignature.entityName = Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME + Pcm2JavaTestUtils.RENAME
			]
		]
		
		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME + Pcm2JavaTestUtils.RENAME, createVoid(), List.of()))
				.build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit))
		]
	}
	
	@Test
	def void testChangeOperationSignatureReturnType() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				interfaces__Repository += interface
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val operationSignature = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME).signatures__OperationInterface.claimOne
				val dataType = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				dataTypes__Repository += dataType
				operationSignature.returnType__OperationSignature = dataType
			]
		]
		
		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createInt(), List.of()))
				.build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit))
		]
	}
	
	@Test
	def void testCreateOperationSignatureWithReturnType() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interfaces__Repository += interface
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val interface = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				val dataType = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				dataTypes__Repository += dataType
				val operationSignature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				operationSignature.returnType__OperationSignature = dataType
				interface.signatures__OperationInterface += operationSignature
			]
		]
		
		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createInt(), List.of()))
				.build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit))
		]
	}
}