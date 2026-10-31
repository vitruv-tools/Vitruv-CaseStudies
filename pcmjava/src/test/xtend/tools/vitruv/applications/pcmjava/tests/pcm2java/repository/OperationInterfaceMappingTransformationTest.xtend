package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaInterfaceBuilder

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createOperationInterface
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmRepository

import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimOperationInterface

class OperationInterfaceMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	def void testAddInterface() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)

		viewFactory.changePcmView [
			claimSinglePcmRepository(it) => [
				interfaces__Repository += createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
			]
		]

		viewFactory.validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).build
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit))
		]
	}

	@Test
	def void testRenameInterface() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		viewFactory.changePcmView [
			claimSinglePcmRepository(it) => [
				interfaces__Repository += createOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
			]
		]

		viewFactory.changePcmView [
			claimSinglePcmRepository(it) => [
				val interface = claimOperationInterface(Pcm2JavaTestUtils.INTERFACE_NAME)
				interface.entityName = Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME_SUFFIX
			]
		]

		viewFactory.validateJavaView [
			val interfaceCompilationUnit = new FluentJavaInterfaceBuilder(
				Pcm2JavaTestUtils.INTERFACE_NAME + Pcm2JavaTestUtils.RENAME_SUFFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.CONTRACTS_SUFFIX
			).build
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(interfaceCompilationUnit))
		]
	}

	static class BidirectionalTest extends OperationInterfaceMappingTransformationTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}
}
