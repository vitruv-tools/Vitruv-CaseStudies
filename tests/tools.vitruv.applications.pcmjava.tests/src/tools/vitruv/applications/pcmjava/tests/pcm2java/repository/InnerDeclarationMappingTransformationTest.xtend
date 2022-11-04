package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder

class InnerDeclarationMappingTransformationTest extends Pcm2JavaTransformationTest {
	
	@Test
	def void testAddInnerDeclaration() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				dataTypes__Repository += createPrimitiveDataType(PrimitiveTypeEnum.INT)
			]
		]
		
		changePcmView [
			modifySingleRepository[
				val compositeDataType = claimCompositeDataType(it, Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				val innerDataType = claimPrimitiveDataType(it, PrimitiveTypeEnum.INT)
				val innerDeclaration = createInnerDeclaration(innerDataType, Pcm2JavaTestUtils.INNER_DEC_NAME)
				compositeDataType.innerDeclaration_CompositeDataType += innerDeclaration
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
	def void testRenameInnerDeclaration(){
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val compositeDataType = createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				val innerDataType = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				dataTypes__Repository += compositeDataType
				dataTypes__Repository += innerDataType
				
				val innerDeclaration = createInnerDeclaration(innerDataType, Pcm2JavaTestUtils.INNER_DEC_NAME)
				compositeDataType.innerDeclaration_CompositeDataType += innerDeclaration
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val innerDeclaration = claimCompositeDataType(it, Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME).innerDeclaration_CompositeDataType.claimOne
				innerDeclaration.entityName = Pcm2JavaTestUtils.INNER_DEC_NAME + Pcm2JavaTestUtils.RENAME
			]
		]
		
		validateJavaView[
			val expectedCompilationUnit = new FluentJavaClassBuilder( 
					Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME, 
					Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
				)
				.addPrivateField(Pcm2JavaTestUtils.INNER_DEC_NAME + Pcm2JavaTestUtils.RENAME, createInt())
				.addGetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME + Pcm2JavaTestUtils.RENAME)
				.addSetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME + Pcm2JavaTestUtils.RENAME)
				.build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}
	
	@Test
	def void testChangeInnerDeclarationType(){
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository [
				val compositeDataType = createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				val innerDataType = createPrimitiveDataType(PrimitiveTypeEnum.INT)
				dataTypes__Repository += createPrimitiveDataType(PrimitiveTypeEnum.BOOL)
				dataTypes__Repository += compositeDataType
				dataTypes__Repository += innerDataType
				
				val innerDeclaration = createInnerDeclaration(innerDataType, Pcm2JavaTestUtils.INNER_DEC_NAME)
				compositeDataType.innerDeclaration_CompositeDataType += innerDeclaration
			]
		]
		
		changePcmView [
			modifySingleRepository [
				val innerDeclaration = claimCompositeDataType(it, Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME).innerDeclaration_CompositeDataType.claimOne
				val boolDataType = claimPrimitiveDataType(it, PrimitiveTypeEnum.BOOL)
				innerDeclaration.datatype_InnerDeclaration = boolDataType
			]
		]
		
		validateJavaView[
			val expectedCompilationUnit = new FluentJavaClassBuilder( 
					Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME, 
					Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
				)
				.addPrivateField(Pcm2JavaTestUtils.INNER_DEC_NAME, createBoolean())
				.addGetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME)
				.addSetterForField(Pcm2JavaTestUtils.INNER_DEC_NAME)
				.build
			
			assertExistenceOfCompilationUnitsDeeplyEqualTo(List.of(expectedCompilationUnit))
		]
	}
}