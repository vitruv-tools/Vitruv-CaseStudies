package tools.vitruv.applications.demo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.propagation.ProjectMarker;
import tools.vitruv.dsls.demo.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification;
import tools.vitruv.dsls.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.VirtualModelBuilder;

public class FamiliesPersonsVsumWrapper {
	private static final Path ROOT_PATH = Paths.get("target", "root").toAbsolutePath();
	private static final Path FAMILIES_MODEL_PATH = ROOT_PATH.resolve("model/families.families");
	private static final URI FAMILIES_MODEL_URI = URI.createFileURI(FAMILIES_MODEL_PATH.toString());
	
	private VirtualModel vsum;
	private Map<String, ViewType<?>> viewTypes;

	public void initialize() throws Exception {
		DemoUtility.registerFactories();
		
		if (Files.exists(ROOT_PATH)) {
			deletePath(ROOT_PATH);
		} else {
			Files.createDirectories(ROOT_PATH);
		}
		ProjectMarker.markAsProjectRootFolder(ROOT_PATH);
		
		generateViewTypes();
		buildInternalVirtualModel();
		
		generateInitialFamilyRegister();
	}
	
	private void deletePath(Path pathToDelete) {
		if (Files.isRegularFile(pathToDelete)) {
			try {
				Files.deleteIfExists(pathToDelete);
			} catch (IOException ioEx) {
				throw new IllegalStateException("The path " + pathToDelete.toString() + " should be removable.", ioEx);
			}
		} else if (Files.isDirectory(pathToDelete)) {
			try {
				Files.list(pathToDelete).forEach(this::deletePath);
			} catch (IOException ioEx) {
				throw new IllegalStateException("Could not iterate over the path " + pathToDelete.toString(), ioEx);
			}
		}
	}
	
	private void generateViewTypes() {
		viewTypes = new HashMap<>();
		viewTypes.put(DemoUtility.FAMILIES_VIEW_TYPE_NAME, ViewTypeFactory.createIdentityMappingViewType(DemoUtility.FAMILIES_VIEW_TYPE_NAME));
		viewTypes.put(DemoUtility.PERSONS_VIEW_TYPE_NAME, ViewTypeFactory.createIdentityMappingViewType(DemoUtility.PERSONS_VIEW_TYPE_NAME));
	}
	
	private void buildInternalVirtualModel() {
		this.vsum = new VirtualModelBuilder()
			.withChangePropagationSpecification(new FamiliesToPersonsChangePropagationSpecification())
			.withChangePropagationSpecification(new PersonsToFamiliesChangePropagationSpecification())
			.withStorageFolder(ROOT_PATH)
			.withViewTypes(viewTypes.values())
			.withUserInteractor(UserInteractionFactory.instance.createUserInteractor(
					UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
			.buildAndInitialize();
	}

	private void generateInitialFamilyRegister() throws Exception {
		var familyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		familyRegister.setId("Family Register");
		
		var view = getFamilyView().withChangeRecordingTrait();
		view.registerRoot(familyRegister, FAMILIES_MODEL_URI);
		// The consistency preservation is active and generates a person register automatically.
		view.commitChanges();
		view.close();
	}
	
	private View getView(String viewTypeName, URI srcUri) {
		var selector = vsum.createSelector(viewTypes.get(viewTypeName));
		selector
			.getSelectableElements()
			.stream()
			.filter(element -> element.eResource().getURI().equals(srcUri))
			.forEach(it -> selector.setSelected(it, true));
		return selector.createView();
	}
	
	//---------------------------------------------------------------------------------------------------------
	

	public VirtualModel getVsum() {
		return vsum;
	}
	
	public View getFamilyView() {
		return getView(DemoUtility.FAMILIES_VIEW_TYPE_NAME, FAMILIES_MODEL_URI);
	}
}
