package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaInterfaceBuilder

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*

import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*

class NewOperationInterfaceMappingTransformationTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testAddInterface() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		
		changePcmView [
			modifySingleRepository [
				interfaces__Repository += createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
			]
		]
		
		validateJavaView [
			val interfaceCompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).build
			assertCompilationUnits(List.of(interfaceCompilationUnit))
		]
	}
	
	@Test
	def void testRenameInterface() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				interfaces__Repository += createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val interface = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.entityName = Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME
			]
		]
		
		validateJavaView [
			val interfaceCompilationUnit = new JavaInterfaceBuilder(Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFIX
			).build
			assertCompilationUnits(List.of(interfaceCompilationUnit))
		]
	}
}