package tools.vitruv.applications.pcmumlclass.tests

import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMCompositeDataTypeBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMRepositoryBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLClassBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLPackageBuilder

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::CompositeDataType in a pcm::Repository
 * with its corresponding uml::Class (implementation) in an uml::Package (the datatypes package corresponding to the repository).
 * <br><br>
 * Related files: 
 * 		PcmCompositeDataType.reactions,
 * 		UmlCompositeDataTypeClass.reactions, 
 * 		UmlCompositeDataTypeGeneralization.reactions
 */
class CompositeDataTypeConceptTest extends PcmUmlClassApplicationTest {

	static val TEST_COMPOSITE_DATATYPE = "TestCompositeType"
	static val TEST_COMPOSITE_DATATYPE_PARENT = "TestCompositeTypeParent"

	@Test
	def void testCreateCompositeDataTypeConcept_UML() {
		initPCMRepository()

		changeUmlView[
			umlDatatypesPackage.createOwnedClass(TEST_COMPOSITE_DATATYPE, false)
		]

		validatePcmView[
			val compositeDataType = new FluentPCMCompositeDataTypeBuilder(TEST_COMPOSITE_DATATYPE).build
			val expectedPcmRepository = new FluentPCMRepositoryBuilder(PACKAGE_NAME_FIRST_UPPER).addDataType(
				compositeDataType).build

			assertEqualityOfPcmRepository(defaultPcmRepository, expectedPcmRepository)
		]
	}

	@Test
	def void testCreateCompositeDataTypeConcept_PCM() {
		initPCMRepository()

		changePcmView[
			var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
			pcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
			defaultPcmRepository.dataTypes__Repository += pcmCompositeType
		]

		validateUmlView[
			val umlTestCompositeType = new FluentUMLClassBuilder(TEST_COMPOSITE_DATATYPE, false).build
			val expectedDataTypesPackage = new FluentUMLPackageBuilder(DATATYPES_PACKAGE).addPackagedElement(
				umlTestCompositeType).build
			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel, String.join(".", PACKAGE_NAME, DATATYPES_PACKAGE),
				expectedDataTypesPackage)
		]
	}

	@Test
	def void testCreateCompositeDataType_withParent_UML() {
		initPCMRepository()

		changeUmlView[
			var umlCompositeTypeClass = umlDatatypesPackage.createOwnedClass(TEST_COMPOSITE_DATATYPE, false)
			var umlCompositeTypeParentClass = umlDatatypesPackage.createOwnedClass(TEST_COMPOSITE_DATATYPE_PARENT,
				false)
			umlCompositeTypeClass.createGeneralization(umlCompositeTypeParentClass)
		]

		validatePcmView[
			val parentCompositeDataType = new FluentPCMCompositeDataTypeBuilder(TEST_COMPOSITE_DATATYPE_PARENT).build
			val compositeDataType = new FluentPCMCompositeDataTypeBuilder(TEST_COMPOSITE_DATATYPE).addParentType(
				parentCompositeDataType).build
			val expectedPcmRepository = new FluentPCMRepositoryBuilder(PACKAGE_NAME_FIRST_UPPER).addDataType(
				compositeDataType).addDataType(parentCompositeDataType).build

			assertEqualityOfPcmRepository(defaultPcmRepository, expectedPcmRepository)
		]
	}

	@Test
	def void testCreateCompositeDataType_withParent_PCM() {
		initPCMRepository()

		changePcmView[
			var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
			pcmCompositeType.entityName = TEST_COMPOSITE_DATATYPE
			defaultPcmRepository.dataTypes__Repository += pcmCompositeType

			var pcmCompositeTypeParent = RepositoryFactory.eINSTANCE.createCompositeDataType
			pcmCompositeTypeParent.entityName = TEST_COMPOSITE_DATATYPE_PARENT
			defaultPcmRepository.dataTypes__Repository += pcmCompositeTypeParent

			pcmCompositeType.parentType_CompositeDataType += pcmCompositeTypeParent
		]

		validateUmlView[
			val umlTestCompositeTypeParent = new FluentUMLClassBuilder(TEST_COMPOSITE_DATATYPE_PARENT, false).build
			val umlTestCompositeType = new FluentUMLClassBuilder(TEST_COMPOSITE_DATATYPE, false).addGeneralization(
				umlTestCompositeTypeParent).build
			val expectedDataTypesPackage = new FluentUMLPackageBuilder(DATATYPES_PACKAGE).addPackagedElement(
				umlTestCompositeType).addPackagedElement(umlTestCompositeTypeParent).build
			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel, String.join(".", PACKAGE_NAME, DATATYPES_PACKAGE),
				expectedDataTypesPackage)
		]
	}
}
