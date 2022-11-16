package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*
import org.junit.jupiter.api.Disabled

class CompositeDataTypeMappingTransformationTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testAddCompositeDataType() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		
		changePcmView[
			claimSinglePcmRepository(it) => [
				dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
			]
		]
		
		validateJavaView[
			val expectedCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}

	@Test
	def void testRenameCompositeDataType() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView[
			claimSinglePcmRepository(it) => [
				dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
			]
		]
		
		changePcmView[
			claimSinglePcmRepository(it) => [
				var dataType = dataTypes__Repository.filter(CompositeDataType).claimOne
				dataType.entityName = Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + Pcm2JavaTestUtils.RENAME_SUFIX
			]
		]
		
		validateJavaView[
			val expectedCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME + Pcm2JavaTestUtils.RENAME_SUFIX,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}

	@Test
	def void testAddPrimitiveTypeToCompositeDataType() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView[
			claimSinglePcmRepository(it) => [
				dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				dataTypes__Repository += createPrimitiveDataType(PrimitiveTypeEnum.INT)
			]
		]
		
		changePcmView[
			claimSinglePcmRepository(it) => [
				val innerType = dataTypes__Repository.filter(PrimitiveDataType).claimOne
				val dataType = dataTypes__Repository.filter(CompositeDataType).claimOne
				dataType.innerDeclaration_CompositeDataType += createInnerDeclaration(innerType, Pcm2JavaTestUtils.INNER_DEC_NAME)
			]
		]
		
		validateJavaView[
			val expectedCompilationUnit = new FluentJavaClassBuilder(
					Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME, 
					Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
				)
				.addPrivateField(Pcm2JavaTestUtils.INNER_DEC_NAME, createInt())
				.addGetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME)
				.addSetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME)
				.build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}
	
	@Test
	def void testAddCompositeDataTypeToCompositeDataType() {
		val INNER_COMPOSITE_DATATYPE_NAME = "InnerCompositeDataType"
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView[
			claimSinglePcmRepository(it) => [
				dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				dataTypes__Repository += createCompositeDataType(INNER_COMPOSITE_DATATYPE_NAME)
			]
		]
		
		changePcmView[
			claimSinglePcmRepository(it) => [
				val outerCompositeDataType = dataTypes__Repository.filter(CompositeDataType).filter(ctd | ctd.entityName == Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME).claimOne
				val innerCompositeDataType = dataTypes__Repository.filter(CompositeDataType).filter(ctd | ctd.entityName == INNER_COMPOSITE_DATATYPE_NAME).claimOne
				outerCompositeDataType.innerDeclaration_CompositeDataType += createInnerDeclaration(innerCompositeDataType, Pcm2JavaTestUtils.INNER_DEC_NAME)
			]
		]
		
		validateJavaView[
			val innerDataTypeExpectedCompilationUnit = new FluentJavaClassBuilder( 
					INNER_COMPOSITE_DATATYPE_NAME, 
					Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
				)
				.build
			
			val outerDataTypeExpectedCompilationUnit = new FluentJavaClassBuilder( 
					Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME, 
					Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
				)
				.addPrivateField(Pcm2JavaTestUtils.INNER_DEC_NAME, getReference(innerDataTypeExpectedCompilationUnit))
				.addGetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME)
				.addSetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME)
				.build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(innerDataTypeExpectedCompilationUnit, outerDataTypeExpectedCompilationUnit))
		]
	}
	
	@Disabled("TODO: adapt reactions")
	static class BidirectionalTest extends CompositeDataTypeMappingTransformationTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}
}
