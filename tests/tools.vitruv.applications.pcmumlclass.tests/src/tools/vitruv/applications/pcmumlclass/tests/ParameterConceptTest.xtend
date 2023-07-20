package tools.vitruv.applications.pcmumlclass.tests

import edu.kit.ipd.sdq.commons.util.java.Pair
import edu.kit.ipd.sdq.commons.util.java.Quadruple
import java.util.List
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Type
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.palladiosimulator.pcm.core.entity.NamedElement
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.Interface
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMCollectionDataTypeBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMCompositeDataTypeBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMOperationInterfaceBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentPCMRepositoryBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLClassBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLInterfaceBuilder
import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLPackageBuilder
import tools.vitruv.applications.testutility.uml.UmlQueryUtil

/**
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::Parameter 
 * in an pcm::OperationSignature (regular Parameter) with an uml::Parameter in an uml::Operation corresponding to the signature.
 * <br><br>
 * Related files: PcmParameter.reactions, UmlRegularParameter.reactions, UmlReturnAndRegularParameterType.reactions
 */
class ParameterConceptTest extends PcmUmlClassApplicationTest {

	static val TEST_PARAMETER_NAME = "testParameter"

	private def void createRepositoryWithSignature() {
		initPCMRepository()

		changePcmView[
			PcmUmlClassApplicationTestHelper.createCompositeDataType(defaultPcmRepository)
			val pcmCompositeType_2 = PcmUmlClassApplicationTestHelper.createCompositeDataType_2(defaultPcmRepository)
			PcmUmlClassApplicationTestHelper.createCollectionDataType(defaultPcmRepository, pcmCompositeType_2)
			val pcmInterface = PcmUmlClassApplicationTestHelper.createOperationInterface(defaultPcmRepository)
			PcmUmlClassApplicationTestHelper.createOperationSignature(pcmInterface)
		]
	}

	private def void testCreateParameterConcept_UML(Function1<? super Type, Boolean> umlTypeSelector, int lower,
		int upper, List<DataType> expectedDataTypes, Interface expectedInterface) {
		createRepositoryWithSignature()

		changeUmlView[
			val umlType = it.getRootObjects(Model).flatMap[it.eAllContents.toList].filter[it instanceof Type].map [
				it as Type
			].findFirst(umlTypeSelector) as Type

			val umlInterface = UmlQueryUtil.claimInterface(umlContractsPackage,
				PcmUmlClassApplicationTestHelper.INTERFACE_NAME)
			val umlOperation = UmlQueryUtil.claimOperation(umlInterface,
				PcmUmlClassApplicationTestHelper.SIGNATURE_NAME)

			var umlParameter = umlOperation.createOwnedParameter(TEST_PARAMETER_NAME, null)
			umlParameter.direction = ParameterDirectionKind.INOUT_LITERAL
			umlParameter.type = umlType
			umlParameter.lower = lower
			umlParameter.upper = upper
		]

		validatePcmView[
			val pcmRepositoryBuilder = new FluentPCMRepositoryBuilder(PACKAGE_NAME_FIRST_UPPER).addInterface(
				expectedInterface)
			expectedDataTypes.forEach[pcmRepositoryBuilder.addDataType(it)]

			assertEqualityOfPcmRepository(defaultPcmRepository, pcmRepositoryBuilder.build)
		]
	}

	@Disabled("fix handling of primitive types")
	@Test
	def void testCreateParameterConcept_UML_primitiveType() {
		val pcmCompositeDataType2 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2).build
		val pcmCompositeDataType1 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME).build
		val pcmCollectionDataType = new FluentPCMCollectionDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME, pcmCompositeDataType2).build
		// TODO: fix handling of primitive types
		// this line is a placeholder and should be fixed if handling of PrimitiveTypes is fixed
		val pcmIntDataType = null
		val pcmInterface = new FluentPCMOperationInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).
			addSignature(PcmUmlClassApplicationTestHelper.SIGNATURE_NAME,
				#[new Pair(TEST_PARAMETER_NAME, pcmIntDataType)]).build
		testCreateParameterConcept_UML([it.name == "int"], 1, 1,
			#[pcmCompositeDataType1, pcmCompositeDataType2, pcmCollectionDataType], pcmInterface)
	}

	@Test
	def void testCreateParameterConcept_UML_compositeType() {
		val pcmCompositeDataType2 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2).build
		val pcmCompositeDataType1 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME).build
		val pcmCollectionDataType = new FluentPCMCollectionDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME, pcmCompositeDataType2).build
		val pcmInterface = new FluentPCMOperationInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).
			addSignature(PcmUmlClassApplicationTestHelper.SIGNATURE_NAME,
				#[new Pair(TEST_PARAMETER_NAME, pcmCompositeDataType1)]).build

		testCreateParameterConcept_UML([it.name == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME], 1, 1,
			#[pcmCompositeDataType1, pcmCompositeDataType2, pcmCollectionDataType], pcmInterface)
	}

	@Test
	def void testCreateParameterConcept_UML_collectionType() {
		val pcmCompositeDataType2 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2).build
		val pcmCompositeDataType1 = new FluentPCMCompositeDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME).build
		val pcmCollectionDataType = new FluentPCMCollectionDataTypeBuilder(
			PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME, pcmCompositeDataType2).build
		val pcmInterface = new FluentPCMOperationInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).
			addSignature(PcmUmlClassApplicationTestHelper.SIGNATURE_NAME,
				#[new Pair(TEST_PARAMETER_NAME, pcmCollectionDataType)]).build

		testCreateParameterConcept_UML([it.name == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2], 0,
			LiteralUnlimitedNatural.UNLIMITED, #[pcmCompositeDataType1, pcmCompositeDataType2, pcmCollectionDataType],
			pcmInterface)
	}

	private def void testCreateParameterConcept_PCM(Function1<Iterable<DataType>, DataType> pcmTypeSelector,
		String expectedParameterTypeName, int expectedParameterLowerValue, int expectedParameterUpperValue) {
		createRepositoryWithSignature()

		changePcmView[
			val pcmType = pcmTypeSelector.apply(it.getRootObjects(Repository).flatMap[it.dataTypes__Repository])
			var pcmInterface = PcmUmlClassApplicationTestHelper.getPcmOperationInterface(defaultPcmRepository)
			var pcmSignature = PcmUmlClassApplicationTestHelper.getPcmOperationSignature(pcmInterface)

			var pcmParameter = RepositoryFactory.eINSTANCE.createParameter
			pcmParameter.parameterName = TEST_PARAMETER_NAME
			pcmParameter.dataType__Parameter = pcmType
			pcmSignature.parameters__OperationSignature += pcmParameter
		]

		validateUmlView[
			val umlTestCompositeType2 = new FluentUMLClassBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2, false).build
			val umlTestCompositeType1 = new FluentUMLClassBuilder(
				PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME, false).build
			val expectedDataTypesPackage = new FluentUMLPackageBuilder(DATATYPES_PACKAGE).addPackagedElement(
				umlTestCompositeType1).addPackagedElement(umlTestCompositeType2).build

			val expectedParameterType = #[umlTestCompositeType1, umlTestCompositeType2].findFirst [
				it.name == expectedParameterTypeName
			]
			val umlInterface = new FluentUMLInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).
				addOperation(PcmUmlClassApplicationTestHelper.SIGNATURE_NAME,
					#[
						new Quadruple(TEST_PARAMETER_NAME, expectedParameterType, expectedParameterLowerValue,
							expectedParameterUpperValue)]).build
			val expectedContractsPackage = new FluentUMLPackageBuilder(CONTRACTS_PACKAGE).
				addPackagedElement(umlInterface).build

			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel, String.join(".", PACKAGE_NAME, DATATYPES_PACKAGE),
				expectedDataTypesPackage)
			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel, String.join(".", PACKAGE_NAME, CONTRACTS_PACKAGE),
				expectedContractsPackage)
		]
	}

	@Disabled("fix handling of primitive types")
	@Test
	def void testCreateParameterConcept_PCM_primitiveType() {
		// TODO: fix handling of primitive types
		testCreateParameterConcept_PCM([PcmUmlClassApplicationTestHelper.createPrimitiveDataType(PrimitiveTypeEnum.INT)],
			"TODO", 1, 1)
	}

	@Test
	def void testCreateParameterConcept_PCM_compositeType() {
		testCreateParameterConcept_PCM([
			it.findFirst [
				(it as NamedElement).entityName == PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME
			]
		], PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME, 1, 1)
	}

	@Test
	def void testCreateParameterConcept_PCM_collectionType() {
		testCreateParameterConcept_PCM([
			it.findFirst [
				(it as NamedElement).entityName == PcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME
			]
		], PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2, 0, -1)
	}
}
