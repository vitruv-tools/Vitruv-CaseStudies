package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaInterfaceBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.MethodDescription

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createBasicComponent
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createCompositeComponent
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationInterface
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationRequiredRole
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationSignature
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmRepository
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.createVoid
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.getReference
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.getRequiredInterfacFieldOrVariableName

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimComponent
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimOperationInterface
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmSystem

class OperationRequiredRoleMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	def void testAddOperationRequiredRole() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += signature
				interfaces__Repository += interface
				components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				val interface = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				component.requiredRoles_InterfaceRequiringEntity += createOperationRequiredRole(interface, component)
			]
		]

		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).
				build

			val componentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).addImportWithNamespace(interfaceCompilationUnit).addPrivateField(
				getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME,
					Pcm2JavaTestUtils.BASIC_COMPONENT_NAME), getReference(interfaceCompilationUnit)).
				addConstructorInitalizationForField(
					getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME,
						Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit, componentCompilationUnit))
		]
	}

	@Test
	def void testAddOperationRequiredToSystem() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		createSystem(Pcm2JavaTestUtils.SYSTEM_NAME)
		val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				interfaces__Repository += interface
			]
		]

		changePcmView [
			claimSinglePcmSystem() => [
				requiredRoles_InterfaceRequiringEntity += createOperationRequiredRole(interface, it)
			]
		]

		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).build
			val systemCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.SYSTEM_NAMESPACE
			).addImportWithNamespace(interfaceCompilationUnit).addPrivateField(
				getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME, Pcm2JavaTestUtils.SYSTEM_NAME),
				getReference(interfaceCompilationUnit)).addConstructorInitalizationForField(
				getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME,
					Pcm2JavaTestUtils.SYSTEM_NAME)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit, systemCompilationUnit))
		]
	}

	@Test
	def void testAddOperationRequiredRoleToCompositeComponent() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += signature
				interfaces__Repository += interface
				components__Repository += createCompositeComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val component = claimComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)
				val interface = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				component.requiredRoles_InterfaceRequiringEntity += createOperationRequiredRole(interface, component)
			]
		]

		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).
				build

			val componentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
			).addImportWithNamespace(interfaceCompilationUnit).addPrivateField(
				getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME,
					Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME), getReference(interfaceCompilationUnit)).
				addConstructorInitalizationForField(
					getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME,
						Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit, componentCompilationUnit))
		]
	}

	@Test
	def void testChangeOperationRequiredRole() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				val interface1 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface1.signatures__OperationInterface += signature
				interfaces__Repository += interface1
				val interface2 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME +
					Pcm2JavaTestUtils.RENAME_SUFIX)
				interfaces__Repository += interface2
				val component1 = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				component1.requiredRoles_InterfaceRequiringEntity += createOperationRequiredRole(interface1, component1)
				components__Repository += component1
				components__Repository +=
					createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME_SUFIX)
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val component2 = claimComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME_SUFIX)
				val interface2 = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME +
					Pcm2JavaTestUtils.RENAME_SUFIX)
				val operationRequiredRole = claimComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME).
					requiredRoles_InterfaceRequiringEntity.filter(OperationRequiredRole).claimOne
				operationRequiredRole.requiredInterface__OperationRequiredRole = interface2
				component2.requiredRoles_InterfaceRequiringEntity += operationRequiredRole
			]
		]

		validateJavaView[
			val interface1CompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).
				build
			val interface2CompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).build
			val component1CompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			) // TODO: it is probably also intended to remove an empty constructor (-> bug in reaction)
			.addConstructor.build
			val component2CompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME_SUFIX + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME +
					Pcm2JavaTestUtils.RENAME_SUFIX
			).addImportWithNamespace(interface2CompilationUnit).addPrivateField(
				getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME,
					Pcm2JavaTestUtils.BASIC_COMPONENT_NAME), getReference(interface2CompilationUnit)).
				addConstructorInitalizationForField(
					getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME,
						Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(interface1CompilationUnit, interface2CompilationUnit, component1CompilationUnit,
					component2CompilationUnit))
		]
	}

	@Test
	@Disabled("reaction does not work as expected")
	// the reaction fails to remove the old assignment in the constructor
	def void testChangeNameOfOperationRequiredRole() {
		val NEW_OPERATION_REQUIRED_ROLE_NAME = "operationReqRoleNameChange"
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += signature
				interfaces__Repository += interface
				val component = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				component.requiredRoles_InterfaceRequiringEntity += createOperationRequiredRole(interface, component)
				components__Repository += component
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val operationRequiredRole = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME).
					requiredRoles_InterfaceRequiringEntity.filter(OperationRequiredRole).claimOne
				operationRequiredRole.entityName = NEW_OPERATION_REQUIRED_ROLE_NAME
			]
		]

		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).
				build

			val componentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).addImportWithNamespace(interfaceCompilationUnit).addPrivateField(NEW_OPERATION_REQUIRED_ROLE_NAME,
				getReference(interfaceCompilationUnit)).
				addConstructorInitalizationForField(NEW_OPERATION_REQUIRED_ROLE_NAME).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit, componentCompilationUnit))
		]
	}

	@Test
	def void testChangeTypeOfOperationRequiredRole() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				val interface1 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface1.signatures__OperationInterface += signature
				interfaces__Repository += interface1
				val interface2 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME +
					Pcm2JavaTestUtils.RENAME_SUFIX)
				interfaces__Repository += interface2
				val component = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += component
				component.requiredRoles_InterfaceRequiringEntity += createOperationRequiredRole(interface1, component)
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val interface2 = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME +
					Pcm2JavaTestUtils.RENAME_SUFIX)
				val operationRequiredRole = claimComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME).
					requiredRoles_InterfaceRequiringEntity.filter(OperationRequiredRole).claimOne
				operationRequiredRole.requiredInterface__OperationRequiredRole = interface2
			]
		]

		validateJavaView [
			val interface1CompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).
				build
			val interface2CompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).build
			val componentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).addImportWithNamespace(interface2CompilationUnit).addPrivateField(
				getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME,
					Pcm2JavaTestUtils.BASIC_COMPONENT_NAME), getReference(interface2CompilationUnit)).
				addConstructorInitalizationForField(
					getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME,
						Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(interface1CompilationUnit, interface2CompilationUnit, componentCompilationUnit))
		]
	}

	@Disabled("TODO: adapt reactions")
	static class BidirectionalTest extends OperationRequiredRoleMappingTransformationTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}
}
