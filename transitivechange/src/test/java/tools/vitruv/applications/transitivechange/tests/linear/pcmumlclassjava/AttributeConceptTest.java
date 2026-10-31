package tools.vitruv.applications.transitivechange.tests.linear.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.Objects;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTestHelper;

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::InnerDeclaration with
 * its corresponding uml::Property and test the propagation of type and multiplicity changes.
 * <br><br>
 * Related files: PcmInnerDeclaration.reactions, UmlInnerDeclarationProperty.reactions
 */
public class AttributeConceptTest extends PcmUmlJavaLinearTransitiveChangeTest {

	private static final String TEST_ATTRIBUTE = "testAttribute";

	public void checkAttributeConcept(
		InnerDeclaration pcmAttribute,
		Property umlAttribute
	) {
		assertNotNull(pcmAttribute);
		assertNotNull(umlAttribute);
		assertTrue(corresponds(pcmAttribute, umlAttribute, TagLiterals.INNER_DECLARATION__PROPERTY));
		assertTrue(Objects.equals(pcmAttribute.getEntityName(), umlAttribute.getName()));
		// parent CompositeType should correspond to parent uml::Class
		assertTrue(
			corresponds(pcmAttribute.getCompositeDataType_InnerDeclaration(), umlAttribute.getClass_(),
				TagLiterals.COMPOSITE_DATATYPE__CLASS));
		// types should correspond
		assertTrue(isCorrect_DataType_Property_Correspondence(pcmAttribute.getDatatype_InnerDeclaration(), umlAttribute));
	}

	protected void checkAttributeConcept(InnerDeclaration pcmAttribute) {
		Property umlAttribute = helper.getModifiableCorr(pcmAttribute, Property.class, TagLiterals.INNER_DECLARATION__PROPERTY);
		checkAttributeConcept(pcmAttribute, umlAttribute);
		checkJavaAttribute(umlAttribute);
	}

	protected void checkAttributeConcept(Property umlAttribute) {
		InnerDeclaration pcmAttribute = helper.getModifiableCorr(umlAttribute, InnerDeclaration.class,
			TagLiterals.INNER_DECLARATION__PROPERTY);
		checkAttributeConcept(pcmAttribute, umlAttribute);
		checkJavaAttribute(umlAttribute);
	}

	private Repository createRepository() {
		Repository pcmRepository = helper.createRepository();
		helper.createCompositeDataType(pcmRepository);
		var pcmCompositeType_2 = helper.createCompositeDataType_2(pcmRepository);
		helper.createCollectionDataType(pcmRepository, pcmCompositeType_2);
		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		getUserInteraction().addNextSingleSelection(ARRAY_LIST_SELECTION); // Mock user input
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(pcmRepository);
		propagate();
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);

		return clearResourcesAndReloadRoot(pcmRepository);
	}

	private void testCreateAttributeConcept_PCM(Repository inPcmRepository, DataType pcmType) {
		var pcmRepository = inPcmRepository;
		var pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository);

		var pcmAttribute = RepositoryFactory.eINSTANCE.createInnerDeclaration();
		pcmAttribute.setEntityName(TEST_ATTRIBUTE);
		pcmAttribute.setDatatype_InnerDeclaration(pcmType);
		pcmCompositeType.getInnerDeclaration_CompositeDataType().add(pcmAttribute);
		getUserInteraction().addNextSingleSelection(ARRAY_LIST_SELECTION); // Mock user input
		propagate();
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);
		pcmCompositeType = helper.getPcmCompositeDataType(pcmRepository);

		pcmAttribute = pcmCompositeType.getInnerDeclaration_CompositeDataType().stream()
			.filter(it -> Objects.equals(it.getEntityName(), TEST_ATTRIBUTE))
			.findFirst().orElse(null);
		assertNotNull(pcmAttribute);
		checkAttributeConcept(pcmAttribute);
		var reloadedPcmType = helper.getModifiableInstance(pcmType);
		assertNotNull(reloadedPcmType, "The DataType should not be null after reload");
		assertTrue(EcoreUtil.equals(pcmAttribute.getDatatype_InnerDeclaration(), reloadedPcmType));
	}

	@Test
	public void testCreateAttributeConcept_PCM_primitiveType() {
		var pcmRepository = createRepository();
		assertNotNull(helper.PCM_INT, "Initialization of PrimitiveTypes seems to have failed");
		testCreateAttributeConcept_PCM(pcmRepository, helper.PCM_INT);
	}

	@Test
	public void testCreateAttributeConcept_PCM_compositeType() {
		var pcmRepository = createRepository();
		// TODO innerDeclaration with same type as outer CompositeDataType doesn't trigger change event
		testCreateAttributeConcept_PCM(pcmRepository, helper.getPcmCompositeDataType_2(pcmRepository));
	}

	@Test
	public void testCreateAttributeConcept_PCM_collectionType() {
		var pcmRepository = createRepository();
		testCreateAttributeConcept_PCM(pcmRepository, helper.getPcmCollectionDataType(pcmRepository));
	}

	private void testCreateAttributeConcept_UML(Repository inPcmRepository, Type umlType, int lower, int upper) {
		var pcmRepository = inPcmRepository;
		var umlCompositeTypeClass = helper.getUmlCompositeDataTypeClass(pcmRepository);
		startRecordingChanges(umlCompositeTypeClass);

		var umlAttribute = umlCompositeTypeClass.createOwnedAttribute(TEST_ATTRIBUTE, null);
		// CollectionType propagation only works if the typeSet is propagated first or last,
		// otherwise it will overwrite one of the multiplicity changes on the back-propagation (pcm -> uml).
		// The failure is explicitly produced here to show case the problem as long as it exists.
		umlAttribute.setLower(lower);
		umlAttribute.setType(umlType);
		umlAttribute.setUpper(upper);

		propagate();
		clearResourcesAndReloadRoot(umlAttribute);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		umlAttribute = helper.getUmlCompositeDataTypeClass(pcmRepository).getOwnedAttributes().stream()
			.filter(it -> Objects.equals(it.getName(), TEST_ATTRIBUTE))
			.findFirst().orElse(null);
		assertNotNull(umlAttribute);
		checkAttributeConcept(umlAttribute);

		var reloadedUmlType = helper.getModifiableInstance(umlType);
		assertNotNull(reloadedUmlType, "The DataType should not be null after reload");

		assertUmlTypeEquality(umlAttribute.getType(), reloadedUmlType);
		/*
		if(umlType instanceof PrimitiveType) {
			val pcmPrimitiveTypes = PcmDataTypeUtil.getPcmPrimitiveTypes(pcmRepository)
			assertTrue(EcoreUtil.equals(PcmUmlClassHelper.mapUmlToPcmPrimitiveType(umlAttribute.type as PrimitiveType, pcmPrimitiveTypes), PcmUmlClassHelper.mapUmlToPcmPrimitiveType(reloadedUmlType as PrimitiveType, pcmPrimitiveTypes)))
		} else {
			assertTrue(EcoreUtil.equals(umlAttribute.type, reloadedUmlType))
		}
		*/
		assertTrue(umlAttribute.getLower() == lower);
		assertTrue(umlAttribute.getUpper() == upper);
	}

	@Test
	public void testCreateAttributeConcept_UML_primitiveType() {
		var pcmRepository = createRepository();
		assertNotNull(helper.UML_INT, "Initialization of PrimitiveTypes seems to have failed");
		testCreateAttributeConcept_UML(pcmRepository, helper.UML_INT, 1, 1);
	}

	@Test
	public void testCreateAttributeConcept_UML_compositeType() {
		var pcmRepository = createRepository();
		testCreateAttributeConcept_UML(pcmRepository, helper.getUmlCompositeDataTypeClass(pcmRepository), 1, 1);
	}

}
