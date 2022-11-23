package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaInterfaceBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.MethodDescription

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createBasicComponent
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationInterface
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationProvidedRole
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationSignature
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimComponent
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmRepository
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmSystem
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.createVoid
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.getReference

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimOperationInterface

class OperationProvidedRoleMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	def void testAddOperationProvidedRole() {
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
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface, component)
			]
		]

		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).
				build

			val componentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).addImplements(getReference(interfaceCompilationUnit)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit, componentCompilationUnit))
		]
	}

	@Test
	def void testChangeInterfaceOfOperationProvidedRole() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				val interface1 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface1.signatures__OperationInterface += signature
				interfaces__Repository += interface1
				val component = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += component
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface1, component)

				val interface2 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME +
					Pcm2JavaTestUtils.RENAME_SUFFIX)
				interfaces__Repository += interface2
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val interface2 = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME +
					Pcm2JavaTestUtils.RENAME_SUFFIX)
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				val providedRole = component.providedRoles_InterfaceProvidingEntity.filter(OperationProvidedRole).
					claimOne()

				providedRole.providedInterface__OperationProvidedRole = interface2
			]
		]

		validateJavaView[
			val interface1CompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).
				build

			val interface2CompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).build
			val componentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).addImplements(getReference(interface2CompilationUnit)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(interface1CompilationUnit, interface2CompilationUnit, componentCompilationUnit))
		]
	}

	@Test
	def void testChangeComponentOfOperationProvidedRole() {
		val NEW_PROVIDING_COMPONENT_NAME = "NewProvidingComponent"
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
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
			claimSinglePcmRepository(it) => [
				val providedRole = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME).
					providedRoles_InterfaceProvidingEntity.filter(OperationProvidedRole).claimOne()
				val component2 = claimComponent(it, NEW_PROVIDING_COMPONENT_NAME)

				component2.providedRoles_InterfaceProvidingEntity += providedRole
			]
		]

		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).
				build
			val component1CompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).build
			val component2CompilationUnit = new FluentJavaClassBuilder(
				"NewProvidingComponent" + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + NEW_PROVIDING_COMPONENT_NAME
			).addImplements(getReference(interfaceCompilationUnit)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(interfaceCompilationUnit, component1CompilationUnit, component2CompilationUnit))
		]
	}

	@Test
	def void testTwoProvidedRolesInOneComponent() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				val interface1 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface1.signatures__OperationInterface += signature
				interfaces__Repository += interface1
				val component = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += component
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface1, component)

				val interface2 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME +
					Pcm2JavaTestUtils.RENAME_SUFFIX)
				interfaces__Repository += interface2
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val interface2 = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME +
					Pcm2JavaTestUtils.RENAME_SUFFIX)
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)

				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface2, component)
			]
		]

		validateJavaView [
			val interface1CompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).
				build

			val interface2CompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).build
			val componentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).addImplements(getReference(interface1CompilationUnit)).addImplements(
				getReference(interface2CompilationUnit)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(interface1CompilationUnit, interface2CompilationUnit, componentCompilationUnit))
		]
	}

	@Test
	def void testTwoProvidedRolesInOneComponentAndRemoveOne() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				val interface1 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface1.signatures__OperationInterface += signature
				interfaces__Repository += interface1
				val interface2 = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME +
					Pcm2JavaTestUtils.RENAME_SUFFIX)
				interfaces__Repository += interface2

				val component = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += component
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface1, component)
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface2, component)
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				val providedRole2 = component.providedRoles_InterfaceProvidingEntity.filter(OperationProvidedRole).
					filter(
						role |
							role.providedInterface__OperationProvidedRole.entityName ==
								Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME_SUFFIX
					).claimOne

				component.providedRoles_InterfaceProvidingEntity.remove(providedRole2)
			]
		]

		validateJavaView [
			val interface1CompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).addMethod(new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).
				build

			val interface2CompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).build
			val componentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).addImplements(getReference(interface1CompilationUnit)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(interface1CompilationUnit, interface2CompilationUnit, componentCompilationUnit))
		]
	}

	@Test
	def void testOperationProvidedRoleToSystem() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		createSystem(Pcm2JavaTestUtils.SYSTEM_NAME)
		val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				interfaces__Repository += interface
			]
		]

		changePcmView [
			claimSinglePcmSystem(it) => [
				providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface, it)
			]
		]

		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).build
			val systemCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.SYSTEM_NAMESPACE
			).addImplements(getReference(interfaceCompilationUnit)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit, systemCompilationUnit))
		]
	}

	@Disabled("TODO: adapt reactions")
	static class BidirectionalTest extends OperationProvidedRoleMappingTransformationTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}
}
