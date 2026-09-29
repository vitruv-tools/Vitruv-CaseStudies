package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmUserSelection;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTestHelper;

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::CompositeDataType in a pcm::Repository
 * with its corresponding uml::Class (implementation) in an uml::Package (the datatypes package corresponding to the repository).
 * <br><br>
 * Related files:
 * 		PcmCompositeDataType.reactions,
 * 		UmlCompositeDataTypeClass.reactions,
 * 		UmlCompositeDataTypeGeneralization.reactions
 */
public class CompositeDataTypeConceptTest extends PcmUmlJavaTransitiveChangeTest {

	private static final String TEST_COMPOSITE_DATATYPE = "TestCompositeType";
	private static final String TEST_COMPOSITE_DATATYPE_PARENT = "TestCompositeTypeParent";

	public void checkCompositeDataTypeConcept(
		CompositeDataType pcmCompositeType,
		Class umlClass
	) {
		assertTrue(corresponds(pcmCompositeType, umlClass));
		assertTrue(Objects.equals(pcmCompositeType.getEntityName(), umlClass.getName()));
		// Repository should correspond to the datatypes package
		assertTrue(
			corresponds(pcmCompositeType.getRepository__DataType(), umlClass.getPackage(),
				TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE));
		// check that parent compositedatatypes and parent classes correspond
		List<Class> umlParentCorrespondences = pcmCompositeType.getParentType_CompositeDataType().stream()
			.map(pcmParent -> Iterables.getFirst(getCorrespondingEObjects(pcmParent, Class.class), null))
			.collect(Collectors.toList());
		assertFalse(umlParentCorrespondences.contains(null));
		assertFalse(
			umlParentCorrespondences.stream()
				.map(umlParent -> umlClass.getGeneralizations().stream()
					.anyMatch(gen -> EcoreUtil.equals(gen.getGeneral(), umlParent)))
				.anyMatch(it -> it == false)
		);
	}

	protected void checkCompositeDataTypeConcept(CompositeDataType pcmCompositeType) {
		Class umlClass = helper.getCorr(pcmCompositeType, Class.class, TagLiterals.COMPOSITE_DATATYPE__CLASS);
		checkCompositeDataTypeConcept(pcmCompositeType, umlClass);
	}

	protected void checkCompositeDataTypeConcept(Class umlClass) {
		CompositeDataType pcmCompositeType = helper.getCorr(umlClass, CompositeDataType.class,
			TagLiterals.COMPOSITE_DATATYPE__CLASS);
		checkCompositeDataTypeConcept(pcmCompositeType, umlClass);
	}

	protected void checkJavaCompositeDataTypeConcept(Class umlClass, CompositeDataType pcmCompositeType) {
		checkJavaType(umlClass);
		umlClass.getGeneralizations().forEach(it -> checkJavaGeneralization(it));
		umlClass.getGeneralizations().forEach(it -> checkJavaType(it.getGeneral()));
		umlClass.getGeneralizations().forEach(it -> checkJavaType(it.getSpecific()));
		// Created before test cases, should be still there:
		var umlPackage = helper.getUmlRepositoryPackage(pcmCompositeType.getRepository__DataType());
		checkJavaPackage(umlPackage);
		umlPackage.getNestedPackages().forEach(this::checkJavaPackage);
	}

	/**
	 * Initialize a pcm::Repository and its corresponding uml-counterparts.
	 */
	private Repository createRepositoryConcept() {
		Repository pcmRepository = helper.createRepository();

		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(pcmRepository);
		propagate();
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);

		return clearResourcesAndReloadRoot(pcmRepository);
	}

	@Test
	public void testCreateCompositeDataTypeConcept_UML() {
		var pcmRepository = createRepositoryConcept();
		var umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository);
		startRecordingChanges(umlDatatypesPkg);

		var umlCompositeTypeClass = umlDatatypesPkg.createOwnedClass(
			CompositeDataTypeConceptTest.TEST_COMPOSITE_DATATYPE, false);
		umlCompositeTypeClass.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection());
		propagate();

		clearResourcesAndReloadRoot(umlDatatypesPkg);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository);

		umlCompositeTypeClass = (Class) umlDatatypesPkg.getPackagedElements().stream()
			.filter(it -> Objects.equals(it.getName(), CompositeDataTypeConceptTest.TEST_COMPOSITE_DATATYPE))
			.findFirst().orElse(null);
		assertNotNull(umlCompositeTypeClass);
		checkCompositeDataTypeConcept(umlCompositeTypeClass);
	}

	@Test
	public void testCreateCompositeDataTypeConcept_PCM() {
		var pcmRepository = createRepositoryConcept();
		var umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository);

		var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType();
		pcmCompositeType.setEntityName(TEST_COMPOSITE_DATATYPE);
		pcmRepository.getDataTypes__Repository().add(pcmCompositeType);
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection());
		propagate();

		clearResourcesAndReloadRoot(umlDatatypesPkg);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository);

		pcmCompositeType = (CompositeDataType) Iterables.getFirst(pcmRepository.getDataTypes__Repository(), null);
		assertNotNull(pcmCompositeType);
		checkCompositeDataTypeConcept(pcmCompositeType);
	}

	@Test
	public void testCreateCompositeDataType_withParent_UML() {
		var pcmRepository = createRepositoryConcept();
		var umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository);
		startRecordingChanges(umlDatatypesPkg);

		var umlCompositeTypeClass = umlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE, false);
		umlCompositeTypeClass.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		var umlCompositeTypeParentClass = umlDatatypesPkg.createOwnedClass(TEST_COMPOSITE_DATATYPE_PARENT, false);
		umlCompositeTypeParentClass.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		umlCompositeTypeClass.createGeneralization(umlCompositeTypeParentClass);
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection());
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection());
		propagate();

		clearResourcesAndReloadRoot(umlDatatypesPkg);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		umlDatatypesPkg = helper.getUmlDataTypesPackage(pcmRepository);

		umlCompositeTypeClass = (Class) umlDatatypesPkg.getPackagedElements().stream()
			.filter(it -> Objects.equals(it.getName(), TEST_COMPOSITE_DATATYPE))
			.findFirst().orElse(null);
		assertNotNull(umlCompositeTypeClass);
		checkCompositeDataTypeConcept(umlCompositeTypeClass);
		umlCompositeTypeParentClass = (Class) umlDatatypesPkg.getPackagedElements().stream()
			.filter(it -> Objects.equals(it.getName(), TEST_COMPOSITE_DATATYPE_PARENT))
			.findFirst().orElse(null);
		assertNotNull(umlCompositeTypeParentClass);
		checkCompositeDataTypeConcept(umlCompositeTypeParentClass);
	}

	@Test
	public void testCreateCompositeDataType_withParent_PCM() {
		var pcmRepository = createRepositoryConcept();

		var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType();
		pcmCompositeType.setEntityName(TEST_COMPOSITE_DATATYPE);
		pcmRepository.getDataTypes__Repository().add(pcmCompositeType);

		var pcmCompositeTypeParent = RepositoryFactory.eINSTANCE.createCompositeDataType();
		pcmCompositeTypeParent.setEntityName(TEST_COMPOSITE_DATATYPE_PARENT);
		pcmRepository.getDataTypes__Repository().add(pcmCompositeTypeParent);
		pcmCompositeType.getParentType_CompositeDataType().add(pcmCompositeTypeParent);
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection());
		getUserInteraction().addNextSingleSelection(Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection());
		propagate();

		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		pcmCompositeType = pcmRepository.getDataTypes__Repository().stream()
			.filter(CompositeDataType.class::isInstance)
			.map(CompositeDataType.class::cast)
			.filter(it -> Objects.equals(it.getEntityName(), TEST_COMPOSITE_DATATYPE))
			.findFirst().orElse(null);
		assertNotNull(pcmCompositeType);
		checkCompositeDataTypeConcept(pcmCompositeType);
	}
}
