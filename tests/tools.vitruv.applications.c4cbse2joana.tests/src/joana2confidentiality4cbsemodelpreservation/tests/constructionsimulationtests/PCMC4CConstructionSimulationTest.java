package joana2confidentiality4cbsemodelpreservation.tests.constructionsimulationtests;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.emftext.language.java.resource.JavaSourceOrClassFileResourceFactoryImpl;
import org.emftext.language.java.resource.java.IJavaOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.util.PcmResourceFactoryImpl;

import edu.kit.kastel.scbs.confidentiality.ConfidentialitySpecification;
import edu.kit.kastel.scbs.pcm2java4joana.correspondencemodel.CorrespondencemodelPackage;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.SourcecodePackage;
import joana2c4cbsemodelpreservation.changepropagationspecification.C4C2joanaChangePropagationSpecification;
import joana2c4cbsemodelpreservation.changepropagationspecification.JaMoPP2joanaChangePropagationSpecification;
import joana2c4cbsemodelpreservation.changepropagationspecification.Joana2c4cChangePropagationSpecification;
import joana2c4cbsemodelpreservation.changepropagationspecification.PCM2c4cChangePropagationSpecification;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;

import java.util.List;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.tests.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.util.temporary.java.JavaSetup;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.impl.IdentityMappingViewType;

public class PCMC4CConstructionSimulationTest extends Pcm2JavaTransformationTest {
	private ResourceSet resourceSetForLoading;
	
	@Override
	protected Iterable<? extends ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(new Pcm2JavaChangePropagationSpecification(), 
				new Java2PcmChangePropagationSpecification(),
				new C4C2joanaChangePropagationSpecification(),
				new JaMoPP2joanaChangePropagationSpecification(),
				new Joana2c4cChangePropagationSpecification(),
				new PCM2c4cChangePropagationSpecification());
	}
	
	@BeforeEach
	public void prepareResourceSet() {
		resourceSetForLoading = withGlobalFactories(new ResourceSetImpl());
		resourceSetForLoading.getResourceFactoryRegistry().getContentTypeToFactoryMap().put("repository", new PcmResourceFactoryImpl());
		resourceSetForLoading.getResourceFactoryRegistry().getContentTypeToFactoryMap().put("java", new JavaSourceOrClassFileResourceFactoryImpl());
		resourceSetForLoading.getResourceFactoryRegistry().getContentTypeToFactoryMap().put("class", new JavaSourceOrClassFileResourceFactoryImpl());

		resourceSetForLoading.getLoadOptions().put(IJavaOptions.DISABLE_LAYOUT_INFORMATION_RECORDING, true);
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
		JavaSetup.prepareFactories();
		EcorePlugin.ExtensionProcessor.process(null);
	}
	
	protected void createAndRegisterRoot(View view, EObject rootObject, URI persistenceUri) {
		view.registerRoot(rootObject, persistenceUri);
	}
	
	private ConfidentialitySpecification loadConfidentialitySpecification() {
		return (ConfidentialitySpecification) loadResource(URI.createURI("resources/model/default.confidentiality")).getContents().get(0);
	}
	
	private Repository loadRespository() {
		return (Repository) loadResource(URI.createURI("resources/model/default.repository")).getContents().get(0);
	}

	private Resource loadResource(URI model) {
		var resource = resourceSetForLoading.getResource(model, true);
		EcoreUtil.resolveAll(resource);
		return resource;
	}
	
	private Resource loadComparisonModel() {
		return loadResource(URI.createURI("resources/model/validate/models.haering"));
//		return loadResource(URI.createURI("resources/model/validate2/security.joana"));
		
	}
	
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
		createAndRegisterRoot(view, repository, repository.eResource().getURI());
		view.commitChanges();
		
		view = viewFactory.createViewOfElements("confidentiality", List.of(Repository.class)).withChangeRecordingTrait();
		createAndRegisterRoot(view, confidentialitySpecification, confidentialitySpecification.eResource().getURI());
		view.commitChanges();
	}
	
	@Test
	public void loadComparisonModelTest() {
		EPackage.Registry.INSTANCE.put(CorrespondencemodelPackage.eNS_URI, CorrespondencemodelPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(SourcecodePackage.eNS_URI, SourcecodePackage.eINSTANCE);

		EcorePlugin.ExtensionProcessor.process(null);
		resourceSetForLoading.getLoadOptions().put(XMIResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
		var everything = loadComparisonModel();
		var joanaRoot = everything.getContents().get(1);
		System.out.println(joanaRoot);
	}
}
