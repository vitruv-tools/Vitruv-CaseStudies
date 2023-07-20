package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Type
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMCollectionDataTypeBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMCompositeDataTypeBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMRepositoryBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLClassBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLPackageBuilder
import java.util.List

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::InnerDeclaration with 
 * its corresponding uml::Property and test the propagation of type and multiplicity changes.
 * <br><br>
 * Related files: PcmInnerDeclaration.reactions, UmlInnerDeclarationProperty.reactions 
 */
class AttributeConceptTest extends PcmUmlClassApplicationTest {

	static val TEST_ATTRIBUTE = "testAttribute"

	private def void createAndInitializeRepository() {
		initPCMRepository()

		changePcmView[
			PcmUmlClassApplicationTestHelper.createCompositeDataType(defaultPcmRepository)
			val compositeDataType2 = PcmUmlClassApplicationTestHelper.createCompositeDataType_2(defaultPcmRepository)
			PcmUmlClassApplicationTestHelper.createCollectionDataType(defaultPcmRepository, compositeDataType2)
		]
	}

	private def void createPcmAttributeAtCompositeType1(Function1<? super DataType, Boolean> innerTypeSelector) {
		changePcmView[
			val pcmType = it.getRootObjects(Repository).flatMap[it.dataTypes__Repository].findFirst(innerTypeSelector)
			val pcmCompositeType = PcmUmlClassApplicationTestHelper.getPcmCompositeDataType(defaultPcmRepository)

			var pcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration
			pcmAttribute.entityName = TEST_ATTRIBUTE
			pcmAttribute.datatype_InnerDeclaration = pcmType
			pcmCompositeType.innerDeclaration_CompositeDataType += pcmAttribute
		]
	}

	@Test
	def void testCreateAttributeConcept_PCM_primitiveType() {
		createAndInitializeRepository()

		createPcmAttributeAtCompositeType1[
			it instanceof PrimitiveDataType && (it as PrimitiveDataType).type == PrimitiveTypeEnum.STRING
		]

		validateUmlView[
			val umlStringType = it.getRootObjects(Model).findFirst[it.name == "PrimitiveTypes"].packagedElements.filter [
				it instanceof PrimitiveType
			].map[it as PrimitiveType].findFirst[it.name == "String"]

			val umlTestCompositeType2 = new FluentUMLClassBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2, false).build
			val umlTestCompositeType1 = new FluentUMLClassBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME, false).addAttribute(TEST_ATTRIBUTE,
				umlStringType, 1, 1).build

			val expectedDataTypesPackage = new FluentUMLPackageBuilder(DATATYPES_PACKAGE).addPackagedElement(
				umlTestCompositeType1).addPackagedElement(umlTestCompositeType2).build
			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel, String.join(".", PACKAGE_NAME, DATATYPES_PACKAGE),
				expectedDataTypesPackage)
		]
	}

	@Test
	def void testCreateAttributeConcept_PCM_compositeType() {
		createAndInitializeRepository()

		// TODO innerDeclaration with same type as outer CompositeDataType doesn't trigger change event
		createPcmAttributeAtCompositeType1[
			it instanceof CompositeDataType &&
				(it as CompositeDataType).entityName == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2
		]

		validateUmlView[
			val umlTestCompositeType2 = new FluentUMLClassBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2, false).build
			val umlTestCompositeType1 = new FluentUMLClassBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME, false).addAttribute(TEST_ATTRIBUTE,
				umlTestCompositeType2, 1, 1).build

			val expectedDataTypesPackage = new FluentUMLPackageBuilder(DATATYPES_PACKAGE).addPackagedElement(
				umlTestCompositeType1).addPackagedElement(umlTestCompositeType2).build
			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel, String.join(".", PACKAGE_NAME, DATATYPES_PACKAGE),
				expectedDataTypesPackage)
		]
	}

	@Test
	def void testCreateAttributeConcept_PCM_collectionType() {
		createAndInitializeRepository()

		createPcmAttributeAtCompositeType1[
			it instanceof CollectionDataType &&
				(it as CollectionDataType).entityName == PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME
		]

		validateUmlView[
			val umlTestCompositeType2 = new FluentUMLClassBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2, false).build
			val umlTestCompositeType1 = new FluentUMLClassBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME, false).addAttribute(TEST_ATTRIBUTE,
				umlTestCompositeType2, 0, -1).build

			val expectedDataTypesPackage = new FluentUMLPackageBuilder(DATATYPES_PACKAGE).addPackagedElement(
				umlTestCompositeType1).addPackagedElement(umlTestCompositeType2).build
			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel, String.join(".", PACKAGE_NAME, DATATYPES_PACKAGE),
				expectedDataTypesPackage)
		]
	}

	private def void testCreateAttributeConcept_UML(Function1<? super Type, Boolean> umlTypeSelector, int lower,
		int upper, List<DataType> expectedPcmDataTypes) {
		createAndInitializeRepository()

		changeUmlView[
			val umlType = it.getRootObjects(Model).flatMap[it.eAllContents.toList].filter[it instanceof Type].map [
				it as Type
			].findFirst(umlTypeSelector) as Type

			val umlCompositeTypeClass = umlDatatypesPackage.packagedElements.findFirst [
				it.name == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME
			] as Class

			val umlAttribute = umlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, null)
			// CollectionType propagation only works if the typeSet is propagated first or last, 
			// otherwise it will overwrite one of the multiplicity changes on the back-propagation (pcm -> uml).
			// The failure is explicitly produced here to show case the problem as long as it exists.
			umlAttribute.lower = lower
			umlAttribute.type = umlType
			umlAttribute.upper = upper
		]

		validatePcmView[
			val pcmRepositoryBuilder = new FluentPCMRepositoryBuilder(PACKAGE_NAME_FIRST_UPPER)
			expectedPcmDataTypes.forEach[pcmRepositoryBuilder.addDataType(it)]
			val expectedPcmRepository = pcmRepositoryBuilder.build

			assertEqualityOfPcmRepository(defaultPcmRepository, expectedPcmRepository)
		]
	}

	@Disabled("TODO: fix handling of primitive types")
	@Test
	def void testCreateAttributeConcept_UML_primitiveType() {
		// TODO: fix handling of primitive types
		// This statement only serves as placeholder. If handling of primitive types is fixed insert actual corresponding PCM int PrimitiveTypeHere
		val pcmIntPrimitiveType = null

		val pcmCompositeDataType2 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2).build
		val pcmCompositeDataType1 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME).addInnerDeclaration(TEST_ATTRIBUTE,
			pcmIntPrimitiveType).build
		val pcmCollectionDataType = new FluentPCMCollectionDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME, pcmCompositeDataType2).build

		testCreateAttributeConcept_UML([it.name == "int"], 1, 1,
			#[pcmCompositeDataType1, pcmCompositeDataType2, pcmCollectionDataType])
	}

	@Test
	def void testCreateAttributeConcept_UML_compositeType() {
		val pcmCompositeDataType2 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2).build
		val pcmCompositeDataType1 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME).addInnerDeclaration(TEST_ATTRIBUTE,
			pcmCompositeDataType2).build
		val pcmCollectionDataType = new FluentPCMCollectionDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME, pcmCompositeDataType2).build

		testCreateAttributeConcept_UML([it.name == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2], 1, 1,
			#[pcmCompositeDataType1, pcmCompositeDataType2, pcmCollectionDataType])
	}

	@Test
	def void testCreateAttributeConcept_UML_collectionType() {
		val pcmCompositeDataType2 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2).build
		val pcmCollectionDataType = new FluentPCMCollectionDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME, pcmCompositeDataType2).build
		val pcmCompositeDataType1 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME).addInnerDeclaration(TEST_ATTRIBUTE,
			pcmCollectionDataType).build

		testCreateAttributeConcept_UML([it.name == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2], 0,
			LiteralUnlimitedNatural.UNLIMITED, #[pcmCompositeDataType1, pcmCompositeDataType2, pcmCollectionDataType])
	}
}
