package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.CompositeComponent
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.ConstructorArguments
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaInterfaceBuilder

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createAssemblyContext
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createBasicComponent
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createCompositeComponent
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationInterface
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationProvidedRole
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationRequiredRole
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmRepository
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.getReference
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.getRequiredInterfacFieldOrVariableName

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimComponent
import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimOperationInterface

class CompositeComponentMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	def void testCreateCompositeComponent() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)

		viewFactory.changePcmView[
			claimSinglePcmRepository(it) => [
				components__Repository += createCompositeComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)
			]
		]

		viewFactory.validateJavaView[
			val expectedCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
			).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}

	@Test
	def void testAddProvidedRoleToCompositeComponent() {
		this.addRepositoryAndCompositeComponent()
		viewFactory.changePcmView[
			claimSinglePcmRepository(it) => [
				interfaces__Repository += createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
			]
		]

		viewFactory.changePcmView[
			claimSinglePcmRepository(it) => [
				var operationInterface = it.claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				var compositeComponent = claimComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)
				compositeComponent.providedRoles_InterfaceProvidingEntity +=
					createOperationProvidedRole(operationInterface, compositeComponent)
			]
		]

		viewFactory.validateJavaView[
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX).build
			val compositeComponentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
			).addImplements(getReference(interfaceCompilationUnit)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(compositeComponentCompilationUnit, interfaceCompilationUnit))
		]
	}

	@Test
	def void testAddRequiredRoleToCompositeComponent() {
		this.addRepositoryAndCompositeComponent()
		viewFactory.changePcmView[
			claimSinglePcmRepository(it) => [
				interfaces__Repository += createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
			]
		]

		viewFactory.changePcmView[
			claimSinglePcmRepository(it) => [
				var operationInterface = it.claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				var compositeComponent = claimComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)
				compositeComponent.requiredRoles_InterfaceRequiringEntity +=
					createOperationRequiredRole(operationInterface, compositeComponent)
			]
		]

		viewFactory.validateJavaView[
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX).build
			val compositeComponentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
			).addPrivateField(
				getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME,
					Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME), getReference(interfaceCompilationUnit)).
				addConstructorInitalizationForField(
					getRequiredInterfacFieldOrVariableName(Pcm2JavaTestUtils.INTERFACE_NAME,
						Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)).addImportWithNamespace(interfaceCompilationUnit).
				build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(compositeComponentCompilationUnit, interfaceCompilationUnit))
		]
	}

	@Test
	def void testAddAssemblyContextToCompositeComponent() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		viewFactory.changePcmView[
			claimSinglePcmRepository(it) => [
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				components__Repository += createCompositeComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)
			]
		]

		viewFactory.changePcmView[
			claimSinglePcmRepository(it) => [
				var basicComponent = components__Repository.filter(BasicComponent).claimOne
				var compositeComponent = components__Repository.filter(CompositeComponent).claimOne
				compositeComponent.assemblyContexts__ComposedStructure +=
					createAssemblyContext(basicComponent, Pcm2JavaTestUtils.ASSEMBLY_CONTEXT_NAME)
			]
		]

		viewFactory.validateJavaView [
			val basicComponentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).build
			val compositeComponentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
			).addImportWithNamespace(basicComponentCompilationUnit).addPrivateField(
				Pcm2JavaTestUtils.ASSEMBLY_CONTEXT_NAME, getReference(basicComponentCompilationUnit)).
				addConstructorConstructionForField(Pcm2JavaTestUtils.ASSEMBLY_CONTEXT_NAME,
					ConstructorArguments.WITHOUT_NULL_LITERAL).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(basicComponentCompilationUnit, compositeComponentCompilationUnit))
		]
	}

	@Test
	def void testAddRequiredDelegationRoleToCompositeComponent() {
		addRepositoryAndCompositeComponent()

		viewFactory.changePcmView[
			claimSinglePcmRepository(it) => [
				interfaces__Repository += createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
			]
		]

		viewFactory.validateJavaView[
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX).build
			val compositeComponentCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME
			).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(compositeComponentCompilationUnit, interfaceCompilationUnit))
		]
	}

	def void addRepositoryAndCompositeComponent() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)

		viewFactory.changePcmView [
			claimSinglePcmRepository(it) => [
				components__Repository += createCompositeComponent(Pcm2JavaTestUtils.COMPOSITE_COMPONENT_NAME)
			]
		]
	}

	@Disabled("TODO: adapt reactions")
	static class BidirectionalTest extends CompositeComponentMappingTransformationTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}
}
