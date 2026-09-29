package tools.vitruv.applications.transitivechange.tests.linear.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Objects;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTestHelper;

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::OperationSignature with its
 * corresponding uml::Operation and the return type of the signature with an uml::Parameter (return parameter) in the uml::Operation.
 * <br><br>
 * Related files: PcmSignature.reactions, UmlSignatureOperation.reactions, UmlReturnAndRegularParameterType.reactions
 */
public class SignatureConceptTest extends PcmUmlJavaLinearTransitiveChangeTest {

	private static final String TEST_SIGNATURE_NAME = "testSignature";

	public void checkSignatureConcept(
		OperationSignature pcmSignature,
		Operation umlOperation
	) {
		var returnParam = umlOperation.getOwnedParameters().stream()
			.filter(param -> param.getDirection() == ParameterDirectionKind.RETURN_LITERAL)
			.findFirst().orElse(null);
		assertNotNull(pcmSignature);
		assertNotNull(umlOperation);
		assertNotNull(returnParam);
		assertTrue(corresponds(pcmSignature, umlOperation, TagLiterals.SIGNATURE__OPERATION));
		assertTrue(corresponds(pcmSignature, returnParam, TagLiterals.SIGNATURE__RETURN_PARAMETER));
		assertTrue(Objects.equals(pcmSignature.getEntityName(), umlOperation.getName()));
		// the name needs to be set, so that its TUID is distinct and the object is not confused with new instances
		assertTrue(Objects.equals(returnParam.getName(), DefaultLiterals.RETURN_PARAM_NAME));
		// return types of both model elements should correspond to each other if they are set
		assertTrue(
			isCorrect_DataType_Parameter_Correspondence(pcmSignature.getReturnType__OperationSignature(), returnParam));
		// should both be contained in corresponding interfaces
		assertTrue(
			corresponds(pcmSignature.getInterface__OperationSignature(), umlOperation.getInterface(),
				TagLiterals.INTERFACE_TO_INTERFACE));
	}

	protected void checkSignatureConcept(OperationSignature pcmSignature) {
		Operation umlOperation = helper.getModifiableCorr(pcmSignature, Operation.class, TagLiterals.SIGNATURE__OPERATION);
		checkSignatureConcept(pcmSignature, umlOperation);
		checkJavaSignatureConcept(umlOperation, pcmSignature);
	}

	protected void checkSignatureConcept(Operation umlOperation) {
		OperationSignature pcmSignature = helper.getModifiableCorr(umlOperation, OperationSignature.class, TagLiterals.SIGNATURE__OPERATION);
		checkSignatureConcept(pcmSignature, umlOperation);
		checkJavaSignatureConcept(umlOperation, pcmSignature);
	}

	protected void checkJavaSignatureConcept(Operation umlOperation, OperationSignature pcmSignature) {
		var pcmRepository = pcmSignature.getInterface__OperationSignature().getRepository__Interface();
		checkJavaType((Interface) umlOperation.eContainer());
		checkJavaMethod(umlOperation);
		// Created before test cases, should be still there:
		var umlPackage = helper.getUmlRepositoryPackage(pcmRepository);
		checkJavaPackage(umlPackage);
		umlPackage.getNestedPackages().forEach(this::checkJavaPackage);
		checkJavaType(helper.getUmlCompositeDataTypeClass(pcmRepository));
		checkJavaType(helper.getUmlCompositeDataTypeClass_2(pcmRepository));
	}

	private Repository createRepositoryWithInterface() {
		Repository pcmRepository = helper.createRepository();
		helper.createOperationInterface(pcmRepository);
		helper.createCompositeDataType(pcmRepository);
		var pcmCompositeType_2 = helper.createCompositeDataType_2(pcmRepository);
		helper.createCollectionDataType(pcmRepository, pcmCompositeType_2);

		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		getUserInteraction().addNextSingleSelection(ARRAY_LIST_SELECTION);
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(pcmRepository);
		propagate();

		return clearResourcesAndReloadRoot(pcmRepository);
	}

	private Repository _testCreateSignatureConcept_UML() {
		var pcmRepository = createRepositoryWithInterface();
		var umlInterface = helper.getUmlInterface(pcmRepository);
		startRecordingChanges(umlInterface);

		var umlOperation = umlInterface.createOwnedOperation(TEST_SIGNATURE_NAME, null, null);

		propagate();
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		umlInterface = helper.getUmlInterface(pcmRepository);
		startRecordingChanges(umlInterface);

		umlOperation = Iterables.getFirst(umlInterface.getOwnedOperations(), null);
		assertNotNull(umlOperation);
		assertTrue(Objects.equals(umlOperation.getName(), TEST_SIGNATURE_NAME));
		checkSignatureConcept(umlOperation);
		return pcmRepository;
	}

	private void _testReturnTypePropagation_UML(Repository inPcmRepository, Type umlType, int lower, int upper) {
		var pcmRepository = inPcmRepository;
		var umlInterface = helper.getUmlInterface(pcmRepository);
		var umlOperation = Iterables.getFirst(umlInterface.getOwnedOperations(), null);
		var umlReturnParameter = umlOperation.getOwnedParameters().stream()
			.filter(param -> param.getDirection() == ParameterDirectionKind.RETURN_LITERAL)
			.findFirst().orElse(null);

		umlReturnParameter.setType(umlType);
		umlReturnParameter.setLower(lower);
		umlReturnParameter.setUpper(upper);

		getUserInteraction().addNextSingleSelection(ARRAY_LIST_SELECTION);
		propagate();
		clearResourcesAndReloadRoot(umlInterface);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		umlInterface = helper.getUmlInterface(pcmRepository);
		umlOperation = Iterables.getFirst(umlInterface.getOwnedOperations(), null);

		checkSignatureConcept(umlOperation);
		var reloadedUmlType = helper.getModifiableInstance(umlType);
		assertNotNull(reloadedUmlType, "The DataType should not be null after reload");
		assertTrue(EcoreUtil.equals(umlOperation.getType(), reloadedUmlType));
		assertEquals(lower, umlOperation.getLower());
		assertEquals(upper, umlOperation.getUpper());
		assertEquals(
			1,
			umlOperation.getOwnedParameters().size(),
			"The Operation should have only a return parameter, since no other parameters were supposed to be added by this test."
		);
	}

	@Test
	public void testCreateSignatureConcept_UML_primitiveReturnType() {
		var pcmRepository = _testCreateSignatureConcept_UML();
		assertNotNull(helper.UML_STRING, "Initialization of PrimitiveTypes seems to have failed");
		_testReturnTypePropagation_UML(pcmRepository, helper.UML_STRING, 1, 1);
	}

	@Test
	public void testCreateSignatureConcept_UML_compositeReturnType() {
		var pcmRepository = _testCreateSignatureConcept_UML();
		_testReturnTypePropagation_UML(pcmRepository, helper.getUmlCompositeDataTypeClass(pcmRepository), 1, 1);
	}

	@Test
	public void testCreateSignatureConcept_UML_collectionReturnType() {
		var pcmRepository = _testCreateSignatureConcept_UML();
		_testReturnTypePropagation_UML(pcmRepository, helper.getUmlCompositeDataTypeClass_2(pcmRepository), 0,
			LiteralUnlimitedNatural.UNLIMITED);
	}

	private void _testCreateSignatureConcept_PCM_withReturnType(Repository inPcmRepository, DataType pcmType) {
		var pcmRepository = inPcmRepository;
		var pcmInterface = helper.getPcmOperationInterface(pcmRepository);

		var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature();
		pcmSignature.setEntityName(TEST_SIGNATURE_NAME);
		pcmSignature.setReturnType__OperationSignature(pcmType);
		pcmInterface.getSignatures__OperationInterface().add(pcmSignature);
		getUserInteraction().addNextSingleSelection(ARRAY_LIST_SELECTION); // Mock user input
		propagate();

		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		pcmInterface = helper.getPcmOperationInterface(pcmRepository);

		pcmSignature = Iterables.getFirst(pcmInterface.getSignatures__OperationInterface(), null);
		assertNotNull(pcmSignature);
		checkSignatureConcept(pcmSignature);
		assertTrue(Objects.equals(pcmSignature.getEntityName(), TEST_SIGNATURE_NAME));
		var reloadedPcmType = helper.getModifiableInstance(pcmType);
		assertNotNull(reloadedPcmType, "The DataType should not be null after reload");
		assertTrue(EcoreUtil.equals(pcmSignature.getReturnType__OperationSignature(), reloadedPcmType));

		assertEquals(
			0,
			pcmSignature.getParameters__OperationSignature().size(),
			"The Signature should have no parameter, since none were supposed to be added by this test."
		);
	}

	@Test
	public void testCreateSignatureConcept_PCM_primitiveReturnType() {
		var pcmRepository = createRepositoryWithInterface();
		assertNotNull(helper.PCM_STRING, "Initialization of PrimitiveTypes seems to have failed");
		_testCreateSignatureConcept_PCM_withReturnType(pcmRepository, helper.PCM_STRING);
	}

	@Test
	public void testCreateSignatureConcept_PCM_compositeReturnType() {
		var pcmRepository = createRepositoryWithInterface();
		_testCreateSignatureConcept_PCM_withReturnType(pcmRepository, helper.getPcmCompositeDataType(pcmRepository));
	}

	@Test
	public void testCreateSignatureConcept_PCM_collectionReturnType() {
		var pcmRepository = createRepositoryWithInterface();
		_testCreateSignatureConcept_PCM_withReturnType(pcmRepository, helper.getPcmCollectionDataType(pcmRepository));
	}
}
