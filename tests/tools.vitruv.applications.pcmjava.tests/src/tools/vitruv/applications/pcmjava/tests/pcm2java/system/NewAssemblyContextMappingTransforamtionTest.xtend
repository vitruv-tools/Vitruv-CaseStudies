package tools.vitruv.applications.pcmjava.tests.pcm2java.system

import java.util.List
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.ConstructorArguments
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaClassBuilder

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*

class NewAssemblyContextMappingTransforamtionTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testCreateAssemblyContext() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		createSystem(Pcm2JavaTestUtils.SYSTEM_NAME)
		val basicComponent = createBasicComponent(Pcm2JavaTestUtils.BASIC_COMPONENT_NAME)
		changePcmView [
			modifySingleRepository[
				components__Repository += basicComponent
			]
		]
		
		changePcmView [
			modifySingleSystem [
				assemblyContexts__ComposedStructure += createAssemblyContext(basicComponent, Pcm2JavaTestUtils.ASSEMBLY_CONTEXT_NAME)
			]
		]
		
		validateJavaView [
		 	val basicComponentCompilationUnit = new JavaClassBuilder(
					Pcm2JavaTestUtils.BASIC_COMPONENT_NAME + Pcm2JavaTestUtils.IMPL_SUFIX, 
					Pcm2JavaTestUtils.REPOSITORY_NAME + "." + Pcm2JavaTestUtils.BASIC_COMPONENT_NAME
				)
				.build
			val systemCompilationUnit = new JavaClassBuilder(
					Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
					Pcm2JavaTestUtils.SYSTEM_NAME_CAMELCASE
				)
				.addImportWithNamespace(basicComponentCompilationUnit)
				.addPrivateField(Pcm2JavaTestUtils.ASSEMBLY_CONTEXT_NAME, getReference(basicComponentCompilationUnit))
				.addConstructorConstructionForField(Pcm2JavaTestUtils.ASSEMBLY_CONTEXT_NAME, ConstructorArguments.WITHOUT_NULL_LITERAL)
				.build
		 	assertCompilationUnits(List.of(basicComponentCompilationUnit, systemCompilationUnit))
		 ]
	}
}