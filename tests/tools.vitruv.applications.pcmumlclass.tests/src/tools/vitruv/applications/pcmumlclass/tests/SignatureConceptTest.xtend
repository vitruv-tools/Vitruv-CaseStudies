package tools.vitruv.applications.pcmumlclass.tests

import edu.kit.ipd.sdq.commons.util.java.Triple
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Type
import org.eclipse.xtext.xbase.lib.Functions.Function1
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
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMOperationInterfaceBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMRepositoryBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLInterfaceBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLPackageBuilder
import tools.vitruv.applications.testutility.uml.UmlQueryUtil
import org.junit.jupiter.api.Disabled

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationSignature with its
 * corresponding uml::Operation and the return type of the signature with an uml::Parameter (return parameter) in the uml::Operation.
 * <br><br>
 * Related files: PcmSignature.reactions, UmlSignatureOperation.reactions, UmlReturnAndRegularParameterType.reactions
 */
class SignatureConceptTest extends PcmUmlClassApplicationTest {

	static val TEST_SIGNATURE_NAME = "testSignature"

	def private void createRepositoryWithInterface() {
		initPCMRepository()

		changePcmView[
			PcmUmlClassApplicationTestHelper.createOperationInterface(defaultPcmRepository)
			PcmUmlClassApplicationTestHelper.createCompositeDataType(defaultPcmRepository)
			val pcmCompositeType_2 = PcmUmlClassApplicationTestHelper.createCompositeDataType_2(defaultPcmRepository)
			PcmUmlClassApplicationTestHelper.createCollectionDataType(defaultPcmRepository, pcmCompositeType_2)
		]
	}

	private def _testCreateSignatureConceptUML() {
		changeUmlView[
			val umlInterface = UmlQueryUtil.claimInterface(umlContractsPackage,
				PcmUmlClassApplicationTestHelper.INTERFACE_NAME);
			umlInterface.createOwnedOperation(TEST_SIGNATURE_NAME, null, null)
		]

		validatePcmView[
			val pcmInterface = new FluentPCMOperationInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).
				addSignature(TEST_SIGNATURE_NAME, #[]).build
			val pcmCompositeDataType1 = new FluentPCMCompositeDataTypeBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME).build
			val pcmCompositeDataType2 = new FluentPCMCompositeDataTypeBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2).build
			val pcmCollectionDataType = new FluentPCMCollectionDataTypeBuilder(
				PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME, pcmCompositeDataType2).build
			val expectedPcmRepository = new FluentPCMRepositoryBuilder(PACKAGE_NAME_FIRST_UPPER).addInterface(
				pcmInterface).addDataType(pcmCompositeDataType1).addDataType(pcmCompositeDataType2).addDataType(
				pcmCollectionDataType).build

			assertEqualityOfPcmRepository(defaultPcmRepository, expectedPcmRepository)
		]
	}

	private def _testReturnTypePropagation_UML(Function1<? super Type, Boolean> umlTypeSelector, int lower, int upper,
		Function1<? super DataType, Boolean> pcmTypeSelector) {
		changeUmlView[
			val umlType = it.getRootObjects(Model).flatMap[it.eAllContents.toList].filter[it instanceof Type].map [
				it as Type
			].findFirst(umlTypeSelector) as Type

			val umlInterface = UmlQueryUtil.claimInterface(umlContractsPackage,
				PcmUmlClassApplicationTestHelper.INTERFACE_NAME);
			var umlOperation = umlInterface.ownedOperations.head
			var umlReturnParameter = umlOperation.ownedParameters.findFirst [ param |
				param.direction === ParameterDirectionKind.RETURN_LITERAL
			]

			umlReturnParameter.type = umlType
			umlReturnParameter.lower = lower
			umlReturnParameter.upper = upper
		]

		validatePcmView[
			val pcmIntegerDataType = PcmUmlClassApplicationTestHelper.createPrimitiveDataType(PrimitiveTypeEnum.INT)
			val pcmCompositeDataType1 = new FluentPCMCompositeDataTypeBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME).build
			val pcmCompositeDataType2 = new FluentPCMCompositeDataTypeBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2).build
			val pcmCollectionDataType = new FluentPCMCollectionDataTypeBuilder(
				PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME, pcmCompositeDataType2).build
			val interfaceReturnType = #[pcmCompositeDataType1, pcmCompositeDataType2, pcmCollectionDataType,
				pcmIntegerDataType].findFirst(pcmTypeSelector)
			val pcmInterface = new FluentPCMOperationInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).
				addSignatureWithReturnType(TEST_SIGNATURE_NAME, interfaceReturnType, #[]).build
			val expectedPcmRepository = new FluentPCMRepositoryBuilder(PACKAGE_NAME_FIRST_UPPER).addInterface(
				pcmInterface).addDataType(pcmCompositeDataType1).addDataType(pcmCompositeDataType2).addDataType(
				pcmCollectionDataType).build

			assertEqualityOfPcmRepository(defaultPcmRepository, expectedPcmRepository)
		]
	}

	@Disabled("fix handling of primitive types")
	@Test
	// TODO: fix handling of primitive types
	def void testCreateSignatureConcept_UML_primitiveReturnType() {
		createRepositoryWithInterface()

		_testCreateSignatureConceptUML()
		_testReturnTypePropagation_UML([it.name == "int"], 0, LiteralUnlimitedNatural.UNLIMITED, [
			it instanceof PrimitiveDataType && (it as PrimitiveDataType).type == PrimitiveTypeEnum.INT
		])
	}

	@Test
	def void testCreateSignatureConcept_UML_compositeReturnType() {
		createRepositoryWithInterface()

		_testCreateSignatureConceptUML()
		_testReturnTypePropagation_UML(
			[it.name == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME],
			1,
			1,
			[
				it instanceof CompositeDataType &&
					(it as CompositeDataType).entityName == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME
			]
		)
	}

	@Test
	def void testCreateSignatureConcept_UML_collectionReturnType() {
		createRepositoryWithInterface()

		_testCreateSignatureConceptUML()
		_testReturnTypePropagation_UML([it.name == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2], 0,
			LiteralUnlimitedNatural.UNLIMITED, [
				it instanceof CollectionDataType &&
					(it as CollectionDataType).entityName == PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME
			])
	}

	private def void _testCreateSignatureConcept_PCM_withReturnType(
		Function1<? super DataType, Boolean> pcmTypeSelector, int lower, int upper,
		Function1<? super Type, Boolean> umlTypeSelector) {
		changePcmView[
			val pcmType = it.getRootObjects(Repository).flatMap[it.dataTypes__Repository].findFirst(pcmTypeSelector)

			var pcmInterface = PcmUmlClassApplicationTestHelper.getPcmOperationInterface(defaultPcmRepository)
			var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
			pcmSignature.entityName = TEST_SIGNATURE_NAME
			pcmSignature.returnType__OperationSignature = pcmType
			pcmInterface.signatures__OperationInterface += pcmSignature
		]

		validateUmlView [
			val umlReturnType = it.getRootObjects(Model).flatMap[it.eAllContents.toList].filter[it instanceof Type].map [
				it as Type
			].findFirst(umlTypeSelector)
			val expectedInterface = new FluentUMLInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).
				addOperation(TEST_SIGNATURE_NAME, new Triple(umlReturnType, lower, upper), #[]).build
			val expectedContractsPackage = new FluentUMLPackageBuilder(CONTRACTS_PACKAGE).
				addPackagedElement(expectedInterface).build
			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel, String.join(".", PACKAGE_NAME, CONTRACTS_PACKAGE),
				expectedContractsPackage)
		]
	}

	@Test
	def void testCreateSignatureConcept_PCM_primitiveReturnType() {
		createRepositoryWithInterface()
		_testCreateSignatureConcept_PCM_withReturnType([
			it instanceof PrimitiveDataType && (it as PrimitiveDataType).type == PrimitiveTypeEnum.INT
		], 1, 1, [it.name == "int"])
	}

	@Test
	def void testCreateSignatureConcept_PCM_compositeReturnType() {
		createRepositoryWithInterface()

		_testCreateSignatureConcept_PCM_withReturnType([
			it instanceof CompositeDataType &&
				(it as CompositeDataType).entityName == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME
		], 1, 1, [it.name == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME])
	}

	@Test
	def void testCreateSignatureConcept_PCM_collectionReturnType() {
		createRepositoryWithInterface()
		_testCreateSignatureConcept_PCM_withReturnType([
			it instanceof CollectionDataType &&
				(it as CollectionDataType).entityName == PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME
		], 0,
			LiteralUnlimitedNatural.UNLIMITED, [it.name == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2])
	}
}
