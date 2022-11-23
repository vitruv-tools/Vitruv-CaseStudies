package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.BasicComponent
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaInterfaceBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.MethodDescription

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createBasicComponent
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationInterface
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationProvidedRole
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationSignature
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createResourceDemandingSEFF
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimComponent
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimOperationInterface
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmRepository
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.createVoid
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.getReference

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

class SEFFMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	def void testCreateSEFF() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
				val interface = createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.signatures__OperationInterface += signature
				interfaces__Repository += interface
				val component = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += component
				component.providedRoles_InterfaceProvidingEntity += createOperationProvidedRole(interface, component)
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = claimOperationInterface(it, Pcm2JavaTestUtils.INTERFACE_NAME).
					signatures__OperationInterface.claimOne
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME) as BasicComponent
				component.serviceEffectSpecifications__BasicComponent += createResourceDemandingSEFF(signature)
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
			).addImplements(getReference(interfaceCompilationUnit)).addMethod(
				new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME, createVoid(), List.of())).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit, componentCompilationUnit))
		]
	}

	@Test
	def void testRenameSEFF() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				val signature = createOperationSignature(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME)
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
			claimSinglePcmRepository(it) => [
				val signature = claimOperationInterface(it, Pcm2JavaTestUtils.INTERFACE_NAME).
					signatures__OperationInterface.claimOne
				signature.entityName = Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME + Pcm2JavaTestUtils.RENAME_SUFFIX
			]
		]

		validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).addMethod(
				new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME + Pcm2JavaTestUtils.RENAME_SUFFIX,
					createVoid(), List.of())).build
			val componentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).addMethod(
				new MethodDescription(Pcm2JavaTestUtils.OPERATION_SIGNATURE_NAME + Pcm2JavaTestUtils.RENAME_SUFFIX,
					createVoid(), List.of())).addImplements(getReference(interfaceCompilationUnit)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit, componentCompilationUnit))
		]
	}

	@Disabled("TODO: adapt reactions")
	static class BidirectionalTest extends SEFFMappingTransformationTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}
}
