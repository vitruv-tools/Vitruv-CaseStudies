package tools.vitruv.applications.transitivechange.tests.circular.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Objects;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.applications.testutility.integration.LegacyPcmUmlClassApplicationTestHelper;

/**
 * This class is based on the correlating PCM/UML test class. It is extended to include Java in the network.
 * This test class tests the reactions and routines that are supposed to synchronize a pcm::AssemblyContext
 * in a pcm::ComposedProvidingRequiringEntity (CPRE) with a uml::Property in an uml::Class (the implementation class to the CPRE).
 * <br><br>
 * Related files: PcmAssemblyContext.reactions, UmlAssemblyContextProperty.reactions
 */
public class AssemblyContextConceptTest extends PcmUmlJavaTransitiveChangeTest {

	private static final String PROPERTY_NAME = "testAssemblyContextField";

	public void checkAssemblyContextConcept(
		AssemblyContext pcmAssemblyContext,
		Property umlAssemblyContextProperty
	) {
		assertNotNull(pcmAssemblyContext);
		assertNotNull(umlAssemblyContextProperty);
		assertTrue(
			corresponds(pcmAssemblyContext, umlAssemblyContextProperty, TagLiterals.ASSEMBLY_CONTEXT__PROPERTY));
		assertTrue(
			corresponds(pcmAssemblyContext.getParentStructure__AssemblyContext(), umlAssemblyContextProperty.getOwner(),
				TagLiterals.IPRE__IMPLEMENTATION));
		assertTrue(
			corresponds(pcmAssemblyContext.getEncapsulatedComponent__AssemblyContext(), umlAssemblyContextProperty.getType(),
				TagLiterals.IPRE__IMPLEMENTATION));
		assertTrue(Objects.equals(pcmAssemblyContext.getEntityName(), umlAssemblyContextProperty.getName()));
	}

	protected void checkAssemblyContextConcept(AssemblyContext pcmAssemblyContext) {
		Property umlAssemblyContextProperty = helper.getModifiableCorr(pcmAssemblyContext, Property.class,
			TagLiterals.ASSEMBLY_CONTEXT__PROPERTY);
		checkAssemblyContextConcept(pcmAssemblyContext, umlAssemblyContextProperty);
		checkJavaAssemblyContextConcept(umlAssemblyContextProperty, pcmAssemblyContext);
	}

	protected void checkAssemblyContextConcept(Property umlAssemblyContextProperty) {
		AssemblyContext pcmAssemblyContext = helper.getModifiableCorr(umlAssemblyContextProperty, AssemblyContext.class,
			TagLiterals.ASSEMBLY_CONTEXT__PROPERTY);
		checkAssemblyContextConcept(pcmAssemblyContext, umlAssemblyContextProperty);
		checkJavaAssemblyContextConcept(umlAssemblyContextProperty, pcmAssemblyContext);
	}

	protected void checkJavaAssemblyContextConcept(Property umlProperty, AssemblyContext pcmAssemblyContext) {
		checkJavaAttribute(umlProperty); // check element that was actually created by test
		Repository pcmRepository = pcmAssemblyContext.getEncapsulatedComponent__AssemblyContext().getRepository__RepositoryComponent();
		// Created before test cases, should be still there:
		Package umlPackage = helper.getUmlRepositoryPackage(pcmRepository);
		checkJavaPackage(umlPackage);
		umlPackage.getNestedPackages().forEach(this::checkJavaPackage);
		checkJavaType(helper.getUmlComponentImpl(pcmRepository));
		checkJavaType(helper.getUmlComponentImpl_2(pcmRepository));
	}

	/**
	 * Initialize a pcm::Repository with two CompositeComponents and synchronize them with their uml-counterparts.
	 */
	private Repository createRepository_2Components() {

		Repository pcmRepository = helper.createRepository();
		helper.createComponent(pcmRepository);
		helper.createComponent_2(pcmRepository);

		getUserInteraction().addNextTextInput(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);
		startRecordingChanges(resourceAt(Path.of(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE))).getContents()
			.add(pcmRepository);
		propagate();
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.PCM_MODEL_FILE);
		assertModelExists(LegacyPcmUmlClassApplicationTestHelper.UML_MODEL_FILE);

		return clearResourcesAndReloadRoot(pcmRepository);
	}

	@Test
	public void testCreateAssemblyContextConcept_PCM() {
		var pcmRepository = createRepository_2Components();
		var pcmComponent = helper.getPcmComponent(pcmRepository);

		var pcmAssemblyContext = CompositionFactory.eINSTANCE.createAssemblyContext();
		pcmAssemblyContext.setEntityName(PROPERTY_NAME);
		// TODO setting the same component as container and encapsulated doesn't seem to trigger change event
		pcmAssemblyContext.setEncapsulatedComponent__AssemblyContext(helper.getPcmComponent_2(pcmRepository));
		pcmComponent.getAssemblyContexts__ComposedStructure().add(pcmAssemblyContext);

		propagate();
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		pcmAssemblyContext = Iterables.getFirst(helper.getPcmComponent(pcmRepository).getAssemblyContexts__ComposedStructure(),
			null);
		assertNotNull(pcmAssemblyContext);
		checkAssemblyContextConcept(pcmAssemblyContext);
	}

	@Test
	public void testCreateAssemblyContextConcept_UML() {
		var pcmRepository = createRepository_2Components();
		var umlComponent = helper.getUmlComponentImpl(pcmRepository);
		startRecordingChanges(umlComponent);

		var umlAssemblyContextProperty = umlComponent.createOwnedAttribute(PROPERTY_NAME,
			helper.getUmlComponentImpl_2(pcmRepository));

		propagate();
		clearResourcesAndReloadRoot(umlAssemblyContextProperty);
		pcmRepository = clearResourcesAndReloadRoot(pcmRepository);

		umlAssemblyContextProperty = helper.getUmlComponentImpl(pcmRepository).getOwnedAttributes().stream()
			.filter(it -> Objects.equals(it.getName(), PROPERTY_NAME))
			.findFirst().orElse(null);
		assertNotNull(umlAssemblyContextProperty);
		checkAssemblyContextConcept(umlAssemblyContextProperty);
	}

}
