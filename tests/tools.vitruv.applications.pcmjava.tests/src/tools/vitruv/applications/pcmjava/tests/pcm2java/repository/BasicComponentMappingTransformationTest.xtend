package tools.vitruv.applications.pcmjava.tests.pcm2java.repository;

import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createBasicComponent
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimComponent
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmRepository

class BasicComponentMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	def void testAddBasicComponent() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)

		changePcmView [
			claimSinglePcmRepository(it) => [
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
			]
		]

		validateJavaView [
			val expectedCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).build
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}

	@Test
	def void testDeleteBasicComponent() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val component = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				EcoreUtil.delete(component)
			]
		]

		validateJavaView [
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of())
		]
	}

	@Test
	def void testRenameBasicComponent() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME).entityName = Pcm2JavaTestUtils.
					BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
		]

		validateJavaView [
			val expectedCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.RENAME_SUFIX + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME +
					Pcm2JavaTestUtils.RENAME_SUFIX
			).build
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}

	@Test
	def void testAddTowBasicComponentAndDeleteOne() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME_SEC)
			]
		]

		changePcmView [
			claimSinglePcmRepository(it) => [
				val secondComponent = claimComponent(it, Pcm2JavaTestUtils.BASIC_COMPONENT_NAME_SEC)
				it.components__Repository -= secondComponent
			]
		]

		validateJavaView [
			val expectedCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).build
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}

	@Disabled("TODO: adapt reactions")
	static class BidirectionalTest extends BasicComponentMappingTransformationTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}
}
