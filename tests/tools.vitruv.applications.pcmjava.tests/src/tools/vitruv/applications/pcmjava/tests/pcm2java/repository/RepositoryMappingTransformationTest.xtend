package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createBasicComponent
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmRepository
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.createPackage

class RepositoryMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	def void testAddRepository() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)

		validateJavaView [
			val repositoryPackage = createPackage [
				name = Pcm2JavaTestUtils.REPOSITORY_NAME
			]
			val contractsPackage = createPackage [
				name = Pcm2JavaTestUtils.CONTRACTS_PACKAGE
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME
			]
			val dataTypesPackage = createPackage [
				name = Pcm2JavaTestUtils.DATATYPES_PACKAGE
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME
			]
			assertExistenceOfPackagesDeeplyEqualTo(List.of(repositoryPackage, contractsPackage, dataTypesPackage))
		]
	}

	@Test
	def void testRepositoryNameChange() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)

		changePcmView[
			claimSinglePcmRepository(it) => [
				entityName = Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
		]

		validateJavaView [
			val repositoryPackage = createPackage [
				name = Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
			val contractsPackage = createPackage [
				name = Pcm2JavaTestUtils.CONTRACTS_PACKAGE
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
			val dataTypesPackage = createPackage [
				name = Pcm2JavaTestUtils.DATATYPES_PACKAGE
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
			assertExistenceOfPackagesDeeplyEqualTo(List.of(repositoryPackage, contractsPackage, dataTypesPackage))
		]
	}

	@Test
	def void testRepositoryNameChangeWithComponents() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
			]
		]

		changePcmView[
			claimSinglePcmRepository(it) => [
				entityName = Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
		]

		validateJavaView [
			val expectedCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME_SUFIX + "." +
					Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).build
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))

			val repositoryPackage = createPackage [
				name = Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
			val contractsPackage = createPackage [
				name = Pcm2JavaTestUtils.CONTRACTS_PACKAGE
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
			val dataTypesPackage = createPackage [
				name = Pcm2JavaTestUtils.DATATYPES_PACKAGE
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
			val componentPackage = createPackage [
				name = Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
			assertExistenceOfPackagesDeeplyEqualTo(
				List.of(repositoryPackage, contractsPackage, dataTypesPackage, componentPackage))
		]
	}

	static class BidirectionalTest extends RepositoryMappingTransformationTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}
}
