package tools.vitruv.applications.remote.tests.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import edu.kit.ipd.sdq.metamodels.families.*;
import edu.kit.ipd.sdq.metamodels.families.impl.FamiliesPackageImpl;
import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage;
import edu.kit.ipd.sdq.metamodels.persons.impl.PersonsPackageImpl;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.propagation.ProjectMarker;
import tools.vitruv.dsls.demo.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification;
import tools.vitruv.dsls.reactions.runtime.correspondence.CorrespondencePackage;
import tools.vitruv.dsls.reactions.runtime.correspondence.impl.CorrespondencePackageImpl;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class FamiliesPersonsModel {
	private static final URI ROOT = URI.createFileURI(new File("").getAbsolutePath() + "/root/");
	private static final URI FAMILY_URI = URI.createFileURI(new File("").getAbsolutePath() + "/root/model/" + "register.families");
	
	private static boolean deleteDirectory(File directoryToBeDeleted) {
	    File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    return directoryToBeDeleted.delete();
	}
	
	private FamilyRegister familyRegister;
	private InternalVirtualModel vsum;
	private Map<String, ViewType<?>> viewTypes;

	public FamiliesPersonsModel(){
		registerFactories();
		new File(ROOT.path()).mkdir();
		try {
			ProjectMarker.markAsProjectRootFolder(Path.of(ROOT.path()));
		} catch (IOException ignored) {
		}
		generateViewTypes();
		buildInternalVirtualModel();
		initRegisters();
	}
	
	private void registerFactories() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		EPackage.Registry.INSTANCE.put(CorrespondencePackage.eNS_URI, CorrespondencePackageImpl.init());
		EPackage.Registry.INSTANCE.put(FamiliesPackage.eNS_URI, FamiliesPackageImpl.init());
		EPackage.Registry.INSTANCE.put(PersonsPackage.eNS_URI, PersonsPackageImpl.init());
	}
	
	private void generateViewTypes() {
		viewTypes = new HashMap<>();
		viewTypes.put("test", ViewTypeFactory.createIdentityMappingViewType("test"));
	}
	
	private void buildInternalVirtualModel() {
		this.vsum = new VirtualModelBuilder()
				.withChangePropagationSpecification(new FamiliesToPersonsChangePropagationSpecification())
				//.withChangePropagationSpecification(new PersonsToFamiliesChangePropagationSpecification())
				.withStorageFolder(new File(new File("").getAbsolutePath() + "/root/"))
				.withViewTypes(viewTypes.values())
				.withUserInteractor(UserInteractionFactory.instance.createUserInteractor(
						UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
				.buildAndInitialize();
	}
	
	private void initRegisters() {
		if (vsum.getViewSourceModels().size() > 0) {
			extractRegisters();
		} else {
			generateRegisters();
		}
	}

	private void extractRegisters() {
		vsum.getViewSourceModels().forEach(it -> {
			if (it.getContents().get(0) instanceof FamilyRegister) {
				this.familyRegister = (FamilyRegister) it.getContents().get(0);
			}
		});
	}

	private void generateRegisters() {
		familyRegister = (FamilyRegister) FamiliesFactory.eINSTANCE
				.create(FamiliesPackage.eINSTANCE.getFamilyRegister());
		familyRegister.setId("Family Register");
		var selector = vsum.createSelector(viewTypes.get("test"));
		CommittableView view = selector.createView().withChangeRecordingTrait();
		view.registerRoot(familyRegister, FAMILY_URI);
		// the consistency preservation is active and generates a person register
		// automatically
		view.commitChanges();
		extractRegisters();
	}
	
	private View getView(Resource resource, String description) {
		var selector = vsum.createSelector(viewTypes.get(description));
		selector.getSelectableElements().stream().filter(element -> element.eResource().equals(resource))
				.forEach(it -> selector.setSelected(it, true));
		return selector.createView();
	}
	
	//---------------------------------------------------------------------------------------------------------
	

	public InternalVirtualModel get() {
		return vsum;
	}
	
	public View getTestView() {
		return getView(familyRegister.eResource(), "test");
	}
	
	public void delete() {
		deleteDirectory(new File(ROOT.path()));
	}
}
