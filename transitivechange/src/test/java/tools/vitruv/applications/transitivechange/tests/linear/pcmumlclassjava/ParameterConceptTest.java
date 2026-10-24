package tools.vitruv.applications.transitivechange.tests.linear.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.Objects;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTestHelper;

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::Parameter
 * in an pcm::OperationSignature (regular Parameter) with an uml::Parameter in an uml::Operation corresponding to the signature.
 * <br><br>
 * Related files: pcmParameter.reactions, UmlRegularParameter.reactions, UmlReturnAndRegularParameterType.reactions
 */
public class ParameterConceptTest extends PcmUmlJavaLinearTransitiveChangeTest {

	private static final String TEST_PARAMETER_NAME = "testParameter";

	private static boolean checkParameterModifiers(ParameterModifier pcmModifier,
		ParameterDirectionKind umlDirection) {
		return umlDirection == PcmUmlClassHelper.getMatchingParameterDirection(pcmModifier);
	}

	public void checkParameterConcept(
		Parameter pcmParameter,
		org.eclipse.uml2.uml.Parameter umlParameter
	) {
		assertNotNull(pcmParameter);
		assertNotNull(umlParameter);
		assertTrue(corresponds(pcmParameter, umlParameter, TagLiterals.PARAMETER__REGULAR_PARAMETER));
		assertTrue(Objects.equals(pcmParameter.getParameterName(), umlParameter.getName()));
		assertTrue(checkParameterModifiers(pcmParameter.getModifier__Parameter(), umlParameter.getDirection()));
		assertTrue(isCorrect_DataType_Parameter_Correspondence(pcmParameter.getDataType__Parameter(), umlParameter));
		assertTrue(
			corresponds(pcmParameter.getOperationSignature__Parameter(), umlParameter.getOperation(),
				TagLiterals.SIGNATURE__OPERATION));
	}

	protected void checkParameterConcept(Parameter pcmParameter) {
		org.eclipse.uml2.uml.Parameter mumlParameter = helper.getModifiableCorr(pcmParameter, org.eclipse.uml2.uml.Parameter.class,
			TagLiterals.PARAMETER__REGULAR_PARAMETER);
		checkParameterConcept(pcmParameter, mumlParameter);
	}

	protected void checkParameterConcept(org.eclipse.uml2.uml.Parameter umlParameter) {
		Parameter pcmParameter = helper.getModifiableCorr(umlParameter, Parameter.class, TagLiterals.PARAMETER__REGULAR_PARAMETER);
		checkParameterConcept(pcmParameter, umlParameter);
		checkJavaParameterConcept(umlParameter, pcmParameter);
	}

	protected void checkJavaParameterConcept(org.eclipse.uml2.uml.Parameter umlParameter, Parameter pcmParameter) {
		checkJavaMethod(umlParameter.getOperation());
		// Created before test cases, should be still there:
		var pcmRepository = pcmParameter.getOperationSignature__Parameter().getInterface__OperationSignature()
			.getRepository__Interface();
		var umlPackage = helper.getUmlRepositoryPackage(pcmRepository);
		assertNotNull(umlPackage, "Could not retrieve UML package of " + pcmRepository);
		checkJavaPackage(umlPackage);
		umlPackage.getNestedPackages().forEach(this::checkJavaPackage);
		checkJavaType(helper.getUmlInterface(pcmRepository));
		checkJavaType(helper.getUmlCompositeDataTypeClass(pcmRepository));
		checkJavaType(helper.getUmlCompositeDataTypeClass_2(pcmRepository));
	}

	private Repository createRepositoryWithSignature() {
		Repository pcmRepository = helper.createRepository();
		helper.createCompositeDataType(pcmRepository);
		var pcmCompositeType_2 = helper.createCompositeDataType_2(pcmRepository);
		helper.createCollectionDataType(pcmRepository, pcmCompositeType_2);
		var pcmInterface = helper.createOperationInterface(pcmRepository);
		helper.createOperationSignature(pcmInterface);

		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		getUserInteraction().addNextSingleSelection(ARRAY_LIST_SELECTION);
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(pcmRepository);
		propagate();
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);

		return clearResourcesAndReloadRoot(pcmRepository);
	}

	private void testCreateParameterConcept_UML(Repository inPcmRepository, Type umlType, int lower, int upper) {
		var pcmRepository = inPcmRepository;
		var pcmInterface = helper.getPcmOperationInterface(pcmRepository);
		var umlOperation = helper.getUmlOperation(pcmInterface);
		startRecordingChanges(umlOperation);

		var umlParameter = umlOperation.createOwnedParameter(TEST_PARAMETER_NAME, null);
		umlParameter.setDirection(ParameterDirectionKind.IN_LITERAL);
		umlParameter.setType(umlType);
		umlParameter.setLower(lower);
		umlParameter.setUpper(upper);
		getUserInteraction().addNextSingleSelection(ARRAY_LIST_SELECTION);
		propagate();

		clearResourcesAndReloadRoot(umlParameter);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		pcmInterface = helper.getPcmOperationInterface(pcmRepository);
		umlOperation = helper.getUmlOperation(pcmInterface);

		umlParameter = umlOperation.getOwnedParameters().stream()
			.filter(it -> Objects.equals(it.getName(), TEST_PARAMETER_NAME))
			.findFirst().orElse(null);
		assertNotNull(umlParameter);
		checkParameterConcept(umlParameter);
		var reloadedUmlType = helper.getModifiableInstance(umlType);
		assertNotNull(reloadedUmlType, "The DataType should not be null after reload");

		assertUmlTypeEquality(umlParameter.getType(), reloadedUmlType);

		/*
		if(umlType instanceof PrimitiveType) {
			val pcmPrimitiveTypes = PcmDataTypeUtil.getPcmPrimitiveTypes(pcmRepository)
			assertTrue(EcoreUtil.equals(PcmUmlClassHelper.mapUmlToPcmPrimitiveType(umlParameter.type as PrimitiveType, pcmPrimitiveTypes), PcmUmlClassHelper.mapUmlToPcmPrimitiveType(reloadedUmlType as PrimitiveType, pcmPrimitiveTypes)))
		} else {
			assertTrue(EcoreUtil.equals(umlParameter.type, reloadedUmlType))
		}
		*/
		assertTrue(umlParameter.getLower() == lower);
		assertTrue(umlParameter.getUpper() == upper);
	}

	@Test
	public void testCreateParameterConcept_UML_primitiveType() {
		var pcmRepository = createRepositoryWithSignature();
		assertNotNull(helper.UML_INT, "Initialization of PrimitiveTypes seems to have failed");
		testCreateParameterConcept_UML(pcmRepository, helper.UML_INT, 1, 1);
	}

	@Test
	public void testCreateParameterConcept_UML_compositeType() {
		var pcmRepository = createRepositoryWithSignature();
		testCreateParameterConcept_UML(pcmRepository, helper.getUmlCompositeDataTypeClass(pcmRepository), 1, 1);
	}

	@Test
	public void testCreateParameterConcept_UML_collectionType() {
		var pcmRepository = createRepositoryWithSignature();
		testCreateParameterConcept_UML(pcmRepository, helper.getUmlCompositeDataTypeClass_2(pcmRepository), 0,
			LiteralUnlimitedNatural.UNLIMITED);
	}

	private void _testCreateParameterConcept_PCM_withType(Repository inPcmRepository, DataType pcmType) {
		var pcmRepository = inPcmRepository;
		var pcmInterface = helper.getPcmOperationInterface(pcmRepository);
		var pcmSignature = helper.getPcmOperationSignature(pcmInterface);

		var pcmParameter = RepositoryFactory.eINSTANCE.createParameter();
		pcmParameter.setParameterName(ParameterConceptTest.TEST_PARAMETER_NAME);
		pcmParameter.setDataType__Parameter(pcmType);
		pcmSignature.getParameters__OperationSignature().add(pcmParameter);
		getUserInteraction().addNextSingleSelection(ARRAY_LIST_SELECTION); // Mock user input
		propagate();
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		pcmInterface = helper.getPcmOperationInterface(pcmRepository);
		pcmSignature = helper.getPcmOperationSignature(pcmInterface);

		pcmParameter = pcmSignature.getParameters__OperationSignature().stream()
			.filter(it -> Objects.equals(it.getParameterName(), ParameterConceptTest.TEST_PARAMETER_NAME))
			.findFirst().orElse(null);
		assertNotNull(pcmParameter);
		checkParameterConcept(pcmParameter);
		var reloadedPcmType = helper.getModifiableInstance(pcmType);
		assertNotNull(reloadedPcmType, "The DataType should not be null after reload");
		assertTrue(EcoreUtil.equals(pcmParameter.getDataType__Parameter(), reloadedPcmType));
	}

	@Test
	public void testCreateParameterConcept_PCM_primitiveType() {
		var pcmRepository = createRepositoryWithSignature();
		assertNotNull(helper.PCM_INT, "Initialization of PrimitiveTypes seems to have failed");
		_testCreateParameterConcept_PCM_withType(pcmRepository, helper.PCM_INT);
	}

	@Test
	public void testCreateParameterConcept_PCM_compositeType() {
		var pcmRepository = createRepositoryWithSignature();
		_testCreateParameterConcept_PCM_withType(pcmRepository, helper.getPcmCompositeDataType(pcmRepository));
	}

	@Test
	public void testCreateParameterConcept_PCM_collectionType() {
		var pcmRepository = createRepositoryWithSignature();
		_testCreateParameterConcept_PCM_withType(pcmRepository, helper.getPcmCollectionDataType(pcmRepository));
	}

}
