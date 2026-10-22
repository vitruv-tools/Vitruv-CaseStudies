package tools.vitruv.applications.c4cbse2joana.tests.constructionsimulationtests;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.util.PcmResourceFactoryImpl;

import edu.kit.ipd.sdq.metamodels.confidentiality4cbse.ConfidentialitySpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.C4C2joanaChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.JaMoPP2joanaChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.Joana2c4cChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.changepropagationspecification.PCM2c4cChangePropagationSpecification;
import tools.vitruv.applications.c4cbse2joana.tests.util.MetamodelSetup;
import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.applications.util.temporary.java.JavaSetup;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.testutils.integration.TestViewFactory;
import tools.vitruv.framework.testutils.integration.ViewBasedVitruvApplicationTest;
import tools.vitruv.framework.views.View;

/**
 * Constructs the Java and JOANA models for the evaluation case study from its PCM repository and
 * Confidentiality4CBSE specification. Its result is the basis of the comparison in {@link EvaluationTest}.
 */
public class PCMC4CConstructionSimulationTest extends ViewBasedVitruvApplicationTest {

	@BeforeAll
	public static void registerCaseStudyMetamodels() {
		MetamodelSetup.registerMetamodels();
	}
	private static final String MODEL_FOLDER = "src/test/resources/model/";

	private ResourceSet resourceSetForLoading;
	private TestViewFactory viewFactory;

	@Override
	protected Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(new Pcm2JavaChangePropagationSpecification(),
				new Java2PcmChangePropagationSpecification(),
				new C4C2joanaChangePropagationSpecification(),
				new JaMoPP2joanaChangePropagationSpecification(),
				new Joana2c4cChangePropagationSpecification(),
				new PCM2c4cChangePropagationSpecification());
	}

	@Override
	protected boolean enableTransitiveCyclicChangePropagation() {
		return false;
	}

	@BeforeEach
	public void prepareResourceSet() {
		EcorePlugin.ExtensionProcessor.process(null);
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
		JavaSetup.prepareFactories();
		resourceSetForLoading = withGlobalFactories(new ResourceSetImpl());
		resourceSetForLoading.getResourceFactoryRegistry().getExtensionToFactoryMap().put("repository", new PcmResourceFactoryImpl());
		viewFactory = new TestViewFactory(getVirtualModel());
	}

	protected void createAndRegisterRoot(View view, EObject rootObject, URI persistenceUri) {
		view.registerRoot(rootObject, persistenceUri);
	}

	private ConfidentialitySpecification loadConfidentialitySpecification() {
		return (ConfidentialitySpecification) loadResource(URI.createURI(MODEL_FOLDER + "default.confidentiality")).getContents().get(0);
	}

	private Repository loadRespository() {
		return (Repository) loadResource(URI.createURI(MODEL_FOLDER + "default.repository")).getContents().get(0);
	}

	private Resource loadResource(URI model) {
		var resource = resourceSetForLoading.getResource(model, true);
		EcoreUtil.resolveAll(resource);
		return resource;
	}

	@Disabled("Propagating the evaluation models fails because pcmjava cannot yet relate PCM types to Java standard library types")
	@Test
	public void constructJavaJoanaTest() {
		var confidentialitySpecification = loadConfidentialitySpecification();
		var repository = loadRespository();
		// use an arraylist
		getUserInteraction().addNextSingleSelection(0);
		// ignore datatypes
		getUserInteraction().addNextSingleSelection(2);
		getUserInteraction().addNextSingleSelection(2);
		getUserInteraction().addNextSingleSelection(2);
		getUserInteraction().addNextSingleSelection(2);
		getUserInteraction().addNextSingleSelection(2);
		getUserInteraction().addNextSingleSelection(2);

		var view = viewFactory.createViewOfElements("confidentiality", List.of()).withChangeRecordingTrait();
		createAndRegisterRoot(view, repository, getUri(Path.of("model", "default.repository")));
		view.commitChanges();

		view = viewFactory.createViewOfElements("confidentiality", List.of(Repository.class)).withChangeRecordingTrait();
		createAndRegisterRoot(view, confidentialitySpecification, getUri(Path.of("model", "default.confidentiality")));
		view.commitChanges();
	}
}
