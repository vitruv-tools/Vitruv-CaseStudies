package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaInterfaceBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.MethodDescription

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*

class NewOperationProvidedRoleMappingTransformationTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testAddOperationProvidedRole() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += signature
				interfaces__Repository += interface
				
				components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				val interface = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface, component)
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
			.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit, componentCompilationUnit))
		]
	}
	
	@Test
	def void testChangeInterfaceOfOperationProvidedRole() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				val interface1 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface1.signatures__OperationInterface += signature
				interfaces__Repository += interface1
				val component = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += component
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface1, component)
				
				val interface2 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME)
				interfaces__Repository += interface2
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val interface2 = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME)
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				val providedRole = component.providedRoles_InterfaceProvidingEntity.filter(OperationProvidedRole).claimOne()
				
				providedRole.providedInterface__OperationProvidedRole = interface2
			]
		]
		
		validateJavaView[
			val interface1CompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), List.of()))
				.build
				
			val interface2CompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.build
			val componentCompilationUnit = new JavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			)
			.addImplements(getReference(interface2CompilationUnit))
			.build
			
			assertCompilationUnits(List.of(interface1CompilationUnit, interface2CompilationUnit, componentCompilationUnit))
		]
	}
	
	@Test
	def void testChangeComponentOfOperationProvidedRole() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += signature
				interfaces__Repository += interface
				val component1 = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += component1
				component1.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface, component1)
				val component2 = createBasicComponent("NewProvidingComponent")
				components__Repository += component2
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val providedRole = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME).providedRoles_InterfaceProvidingEntity.filter(OperationProvidedRole).claimOne()
				val component2 = claimComponent(it, "NewProvidingComponent")
				
				component2.providedRoles_InterfaceProvidingEntity += providedRole
			]
		]
		
		validateJavaView [
			val interfaceCompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), List.of()))
				.build
			val component1CompilationUnit = new JavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			)
			.build
			val component2CompilationUnit = new JavaClassBuilder(
				"NewProvidingComponent" + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + "NewProvidingComponent"
			)
			.addImplements(getReference(interfaceCompilationUnit))
			.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit, component1CompilationUnit, component2CompilationUnit))
		]
	}
	
	@Test
	def void testTwoProvidedRolesInOneComponent() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				val interface1 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface1.signatures__OperationInterface += signature
				interfaces__Repository += interface1
				val component = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += component
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface1, component)
				
				val interface2 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME)
				interfaces__Repository += interface2
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val interface2 = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME)
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface2, component)
			]
		]
		
		validateJavaView [
			val interface1CompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), List.of()))
				.build
				
			val interface2CompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.build
			val componentCompilationUnit = new JavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			)
			.addImplements(getReference(interface1CompilationUnit))
			.addImplements(getReference(interface2CompilationUnit))
			.build
			
			assertCompilationUnits(List.of(interface1CompilationUnit, interface2CompilationUnit, componentCompilationUnit))
		]
	}
	
	@Test
	def void testTwoProvidedRolesInOneComponentAndRemoveOne() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME)
				val interface1 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface1.signatures__OperationInterface += signature
				interfaces__Repository += interface1
				val interface2 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME)
				interfaces__Repository += interface2
				
				val component = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += component
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface1, component)
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface2, component)
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				val providedRole2 = component.providedRoles_InterfaceProvidingEntity
					.filter(OperationProvidedRole)
					.filter(role | role.providedInterface__OperationProvidedRole.entityName == Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME)
					.claimOne
					
				component.providedRoles_InterfaceProvidingEntity.remove(providedRole2)
			]
		]
		
		validateJavaView [
			val interface1CompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME, createVoid(), List.of()))
				.build
				
			val interface2CompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
				)
				.build
			val componentCompilationUnit = new JavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			)
			.addImplements(getReference(interface1CompilationUnit))
			.build
			
			assertCompilationUnits(List.of(interface1CompilationUnit, interface2CompilationUnit, componentCompilationUnit))
		]
	}
	
	@Test
	def void testOperationProvidedRoleToSystem() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		createSystem(Pcm2JavaTestUtils.SYSTEM_NAME)
		val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
		changePcmView [
			modifySingleRepository [
				interfaces__Repository += interface
			]
		]
		
		changePcmView [
			modifySingleSystem [
				providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface, it)
			]
		]
		
		validateJavaView [
			val interfaceCompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			)
			.build
			val systemCompilationUnit = new JavaClassBuilder(
				Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.SYSTEM_NAME_CAMELCASE
			)
			.addImplements(getReference(interfaceCompilationUnit))
			.build
			
			assertCompilationUnits(List.of(interfaceCompilationUnit, systemCompilationUnit))
		]
	}
}