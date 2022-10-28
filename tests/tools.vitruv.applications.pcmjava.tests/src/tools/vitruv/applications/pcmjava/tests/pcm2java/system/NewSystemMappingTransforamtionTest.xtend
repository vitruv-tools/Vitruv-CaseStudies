package tools.vitruv.applications.pcmjava.tests.pcm2java.system

import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaClassBuilder

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*

class NewSystemMappingTransforamtionTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testCreateSystem() {
		createSystem(Pcm2JavaTestUtils.SYSTEM_NAME)
		
		validateJavaView [
			val systemCompilationUnit = new JavaClassBuilder(
				Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.SYSTEM_NAME_CAMELCASE
			)
			.build
			
			assertCompilationUnits(List.of(systemCompilationUnit))
		]
	}
	
	@Test
	def void testRenameSystem() {
		createSystem(Pcm2JavaTestUtils.SYSTEM_NAME)
		
		changePcmView [
			modifySingleSystem [
				entityName = Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.RENAME
			]
		]
		
		validateJavaView [
			val systemCompilationUnit = new JavaClassBuilder(
				Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.RENAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.SYSTEM_NAME_CAMELCASE + Pcm2JavaTestUtils.RENAME
			)
			.build
			
			assertCompilationUnits(List.of(systemCompilationUnit))
		]
	}
	
	@Test
	def void testRemoveSystem() {
		createSystem(Pcm2JavaTestUtils.SYSTEM_NAME)
		
		changePcmView [
			val system = claimSinglePcmSystem(it)
			EcoreUtil.delete(system)
		]
		
		validateJavaView [
			assertCompilationUnits(List.of())
			assertPackages(List.of())
		]
	}
}