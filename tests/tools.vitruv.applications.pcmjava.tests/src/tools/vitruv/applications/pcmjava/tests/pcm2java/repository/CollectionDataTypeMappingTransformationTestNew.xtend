package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaTypeHelper

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.*
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.*

import static extension tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.*
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaStdLibCompilationUnitHelper
import org.junit.jupiter.api.Disabled

class CollectionDataTypeMappingTransformationTestNew extends Pcm2JavaTransformationTest {
	
	@Test
	def void testAddCollectionDataTypeWithoutInnerType() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		
		this.getUserInteraction().addNextSingleSelection(0);
		changePcmView [
			modifySingleRepository [
				it.dataTypes__Repository += createCollectionDataType(Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME, null)
			]
		]
		
		validateJavaView[
			val stdLibHelper = new JavaStdLibCompilationUnitHelper(it)
			val voidCompilationUnit = stdLibHelper.getCompilationUnitFor(Pcm2JavaTestUtils.VOID_COMPILATIONUNIT_NAME)
			val arrayListCompilationUnit = stdLibHelper.getCompilationUnitFor(Pcm2JavaTestUtils.ARRAYLIST_COMPILATIONUNIT_NAME)
			
			val collectionDataTypeCompilationUnit = new JavaClassBuilder(new JavaTypeHelper(),
				Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			)
			.addImportWithoutNamespace(arrayListCompilationUnit)
			.addExtends(getTypedReference(arrayListCompilationUnit, voidCompilationUnit))
			.build
			
			assertCompilationUnits(List.of(collectionDataTypeCompilationUnit, voidCompilationUnit, arrayListCompilationUnit))
		]
	}
	
	@Test
	@Disabled("this special case has not been implemented so far because it is difficult to implement (String is no primitive type)")
	def void testAddCollectionDataTypeWithPrimitiveTypeStringAsInnerType() {
	}
	
	def void testAddCollectionDataTypeWithPrimitiveTypeIntAsInnerType() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository[
				it.dataTypes__Repository += createPrimitiveDataType(PrimitiveTypeEnum.INT)
			]
		]
		
		this.getUserInteraction().addNextSingleSelection(0);
		changePcmView [
			modifySingleRepository [
				val PrimitiveDataType innerType = claimPrimitiveDataType(it, PrimitiveTypeEnum.INT)
				it.dataTypes__Repository += createCollectionDataType(Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME, innerType)
			]
		]
		
		validateJavaView[
			val stdLibHelper = new JavaStdLibCompilationUnitHelper(it)
			val listCompilationUnit = stdLibHelper.getCompilationUnitFor(Pcm2JavaTestUtils.ARRAYLIST_COMPILATIONUNIT_NAME)
			val intergerCompilaitonUnit = stdLibHelper.getCompilationUnitFor(Pcm2JavaTestUtils.INTEGER_COMPILATIONUNIT_NAME)
		
			val collectionDataTypeCompilationUnit = new JavaClassBuilder(new JavaTypeHelper(),
				Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			)
			.addImportWithoutNamespace(listCompilationUnit)
			.addExtends(getTypedReference(listCompilationUnit, intergerCompilaitonUnit))
			.build
			
			assertCompilationUnits(List.of(collectionDataTypeCompilationUnit, listCompilationUnit))
		]
	}
	
	@Test
	def void testAddCollectionDataTypeWithComplexInnerType() {
		createRepostory(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			modifySingleRepository[
				it.dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
			]
		]
		
		this.getUserInteraction().addNextSingleSelection(0);
		changePcmView [
			modifySingleRepository [
				val CompositeDataType innerType = claimCompositeDataType(it, Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				it.dataTypes__Repository += createCollectionDataType(Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME, innerType)
			]
		]
		
		validateJavaView[
			val typeHelper = new JavaTypeHelper()
			val stdLibHelper = new JavaStdLibCompilationUnitHelper(it)
			val arrayListCompilationUnit = stdLibHelper.getCompilationUnitFor("java.util.ArrayList.java")
			
			val compositeDataTypeCompilationUnit = new JavaClassBuilder(typeHelper,
				Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			).build
			val collectionDataTypeCompilationUnit = new JavaClassBuilder(typeHelper,
				Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFIX
			)
			.addImportWithoutNamespace(arrayListCompilationUnit)
			.addExtends(getTypedReference(arrayListCompilationUnit, compositeDataTypeCompilationUnit))
			.build
			
			assertCompilationUnits(List.of(arrayListCompilationUnit, compositeDataTypeCompilationUnit, collectionDataTypeCompilationUnit))
		]
	}
}
