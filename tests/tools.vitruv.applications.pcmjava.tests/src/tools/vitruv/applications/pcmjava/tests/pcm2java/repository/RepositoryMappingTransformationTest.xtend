package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder

class RepositoryMappingTransformationTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testAddRepository() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		
		validateJavaView [
			val repositoryPackage = createPackage [
				name = Pcm2JavaTestUtils.REPOSITORY_NAME
			]
			val contractsPackage = createPackage [
				name = "contracts"
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME
			]
			val dataTypesPackage = createPackage [
				name = "datatypes"
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME
			]
			assertExistenceOfPackagesDeeplyEqualTo(List.of(repositoryPackage, contractsPackage, dataTypesPackage))
		]
	}
	
	@Test
	def void testRepositoryNameChange() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		
		changePcmView[
			modifySingleRepository [
				entityName = Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME
			]
		]
		
		validateJavaView [
			val repositoryPackage = createPackage [
				name = Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME
			]
			val contractsPackage = createPackage [
				name = "contracts"
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME
			]
			val dataTypesPackage = createPackage [
				name = "datatypes"
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME
			]
			assertExistenceOfPackagesDeeplyEqualTo(List.of(repositoryPackage, contractsPackage, dataTypesPackage))
		]
	}
	
	@Test
	def void testRepositoryNameChangeWithComponents() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				it.components__Repository += createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
			]
		]
		
		changePcmView[
			modifySingleRepository [
				entityName = Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME
			]
		]
		
		validateJavaView [
			val expectedCompilationUnit = new FluentJavaClassBuilder( 
				Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX, 
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
			).build
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
			
			val repositoryPackage = createPackage [
				name = Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME
			]
			val contractsPackage = createPackage [
				name = "contracts"
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME
			]
			val dataTypesPackage = createPackage [
				name = "datatypes"
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME
			]
			val componentPackage = createPackage [
				name = Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
				namespaces += Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.RENAME
			]
			assertExistenceOfPackagesDeeplyEqualTo(List.of(repositoryPackage, contractsPackage, dataTypesPackage, componentPackage))
		]
	}
}