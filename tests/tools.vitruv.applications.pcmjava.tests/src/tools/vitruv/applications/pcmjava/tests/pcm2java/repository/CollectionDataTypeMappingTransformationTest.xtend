package tools.vitruv.applications.pcmjava.tests.pcm2java.repository

import java.util.List
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTestUtils
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.FluentJavaClassBuilder
import tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaStdLibCompilationUnitHelper

import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createCollectionDataType
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createCompositeDataType
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmCreatorsUtil.createPrimitiveDataType
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimCompositeDataType
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimPrimitiveDataType
import static tools.vitruv.applications.pcmjava.tests.pcm2java.PcmQueryUtil.claimSinglePcmRepository
import static tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper.JavaCreatorsUtil.getTypedReference

class CollectionDataTypeMappingTransformationTest extends Pcm2JavaTransformationTest {

	@Test
	def void testAddCollectionDataTypeWithoutInnerType() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)

		this.getUserInteraction().addNextSingleSelection(0);
		changePcmView [
			claimSinglePcmRepository(it) => [
				it.dataTypes__Repository += createCollectionDataType(Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME, null)
			]
		]

		validateJavaView[
			val stdLibHelper = new JavaStdLibCompilationUnitHelper(it)
			val voidCompilationUnit = stdLibHelper.voidCompilationUnit
			val arrayListCompilationUnit = stdLibHelper.arrayListCompilationUnit

			val collectionDataTypeCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFFIX
			).addImportWithoutNamespace(arrayListCompilationUnit).setExtends(
				getTypedReference(arrayListCompilationUnit, voidCompilationUnit)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(collectionDataTypeCompilationUnit, voidCompilationUnit, arrayListCompilationUnit))
		]
	}

	@Test
	@Disabled("this special case has not been implemented so far because it is difficult to implement (String is no primitive type)")
	def void testAddCollectionDataTypeWithPrimitiveTypeStringAsInnerType() {
		// same test as testAddCollectionDataTypeWithPrimitiveTypeIntAsInnerType except choose string as primitive datatype
	}

	@Test
	def void testAddCollectionDataTypeWithPrimitiveTypeIntAsInnerType() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				it.dataTypes__Repository += createPrimitiveDataType(PrimitiveTypeEnum.INT)
			]
		]

		this.getUserInteraction().addNextSingleSelection(0);
		changePcmView [
			claimSinglePcmRepository(it) => [
				val PrimitiveDataType innerType = claimPrimitiveDataType(it, PrimitiveTypeEnum.INT)
				it.dataTypes__Repository +=
					createCollectionDataType(Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME, innerType)
			]
		]

		validateJavaView[
			val stdLibHelper = new JavaStdLibCompilationUnitHelper(it)
			val listCompilationUnit = stdLibHelper.arrayListCompilationUnit
			val integerCompilationUnit = stdLibHelper.integerCompilationUnit

			val collectionDataTypeCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFFIX
			).addImportWithoutNamespace(listCompilationUnit).setExtends(
				getTypedReference(listCompilationUnit, integerCompilationUnit)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(collectionDataTypeCompilationUnit, listCompilationUnit, integerCompilationUnit))
		]
	}

	@Test
	def void testAddCollectionDataTypeWithComplexInnerType() {
		createRepository(Pcm2JavaTestUtils.REPOSITORY_NAME)
		changePcmView [
			claimSinglePcmRepository(it) => [
				it.dataTypes__Repository += createCompositeDataType(Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
			]
		]

		this.getUserInteraction().addNextSingleSelection(0);
		changePcmView [
			claimSinglePcmRepository(it) => [
				val CompositeDataType innerType = claimCompositeDataType(it, Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME)
				it.dataTypes__Repository +=
					createCollectionDataType(Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME, innerType)
			]
		]

		validateJavaView[
			val stdLibHelper = new JavaStdLibCompilationUnitHelper(it)
			val arrayListCompilationUnit = stdLibHelper.getArrayListCompilationUnit()

			val compositeDataTypeCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COMPOSITE_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFFIX
			).build
			val collectionDataTypeCompilationUnit = new FluentJavaClassBuilder(
				Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME,
				Pcm2JavaTestUtils.REPOSITORY_NAME + Pcm2JavaTestUtils.DATATYPES_SUFFIX
			).addImportWithoutNamespace(arrayListCompilationUnit).setExtends(
				getTypedReference(arrayListCompilationUnit, compositeDataTypeCompilationUnit)).build

			assertExistenceOfCompilationUnitsDeeplyEqualTo(
				List.of(arrayListCompilationUnit, compositeDataTypeCompilationUnit, collectionDataTypeCompilationUnit))
		]
	}

	@Disabled("TODO: adapt reactions")
	static class BidirectionalTest extends CollectionDataTypeMappingTransformationTest {
		override protected enableTransitiveCyclicChangePropagation() {
			true
		}
	}
}
