package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.BasicComponent
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaInterfaceBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.MethodDescription

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class SEFFMappingTransformationTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testCreateSEFF() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += signature
				interfaces__Repository += interface
				val component = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += component
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface, component)
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val signature = claimOperationInterface(it, Pcm2JavaTestUtils.INTERFACE_NAME).signatures__OperationInterface.claimOne
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME) as BasicComponent
				component.serviceEffectSpecifications__BasicComponent += createResourceDemandingSEFF(signature)
			]
		]
		
		validateJavaView [
			val interfaceCompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), List.of()))
				.build
			val componentCompilationUnit = new JavaClassBuilder(
					Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
					Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
				)
				.addImplements(getReference(interfaceCompilationUnit))
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), List.of()))
				.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit, componentCompilationUnit))
		]
	}
	
	@Test
	def void testRenameSEFF() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += signature
				interfaces__Repository += interface
				val component = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += component
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface, component)
				component.serviceEffectSpecifications__BasicComponent += createResourceDemandingSEFF(signature)
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val signature = claimOperationInterface(it, Pcm2JavaTestUtils.INTERFACE_NAME).signatures__OperationInterface.claimOne
				signature.entityName = Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME + Pcm2JavaTestUtils.RENAME
			]
		]
		
		validateJavaView [
			val interfaceCompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME + Pcm2JavaTestUtils.RENAME, createVoid(), List.of()))
				.build
			val componentCompilationUnit = new JavaClassBuilder(
					Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
					Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME + Pcm2JavaTestUtils.RENAME, createVoid(), List.of()))
				.addImplements(getReference(interfaceCompilationUnit))
				.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit, componentCompilationUnit))
		]
	}
}