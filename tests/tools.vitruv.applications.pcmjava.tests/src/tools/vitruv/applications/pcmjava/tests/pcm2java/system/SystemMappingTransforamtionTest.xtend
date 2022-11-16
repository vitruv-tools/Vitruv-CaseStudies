package tools.vitruv.applications.pcmjava.tests.pcm2java.system

import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder

class SystemMappingTransforamtionTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testCreateSystem() {
		createSystem(Pcm2JavaTestUtils.SYSTEM_NAME)
		
		validateJavaView [
			val systemCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.SYSTEM_NAMESPACE
			)
			.build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(systemCompilationUnit))
		]
	}
	
	@Test
	def void testRenameSystem() {
		createSystem(Pcm2JavaTestUtils.SYSTEM_NAME)
		
		changePcmView [
			claimSinglePcmSystem(it) => [
				entityName = Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
		]
		
		validateJavaView [
			val systemCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.SYSTEM_NAME + Pcm2JavaTestUtils.RENAME_SUFIX + Pcm2JavaTestUtils.IMPL_SUFIX,
				Pcm2JavaTestUtils.SYSTEM_NAMESPACE + Pcm2JavaTestUtils.RENAME_SUFIX
			)
			.build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(systemCompilationUnit))
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
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of())
			assertExistenceOfPackagesDeeplyEqualTo(List.of())
		]
	}
	
	static class BidirectionalTest extends SystemMappingTransforamtionTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}
}