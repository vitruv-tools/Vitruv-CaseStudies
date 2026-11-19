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
import tools.vitruv.change.utils.ProjectMarker;
import tools.vitruv.dsls.demo.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification;
import tools.vitruv.dsls.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

public class FamiliesPersonsVsumWrapper {
	private Path familiesModelPath;
	private URI familiesModelUri;
	private Path personsModelPath;
	private URI personsModelUri;
	
	private VirtualModel vsum;
	private Map<String, ViewType<?>> viewTypes;
	
	public void initialize() throws Exception {
		initialize(Paths.get("target", "root").toAbsolutePath());
	}

	public void initialize(Path rootPath) throws Exception {
		familiesModelPath = rootPath.resolve("model/families.families");
		familiesModelUri = URI.createFileURI(familiesModelPath.toString());
		personsModelPath = rootPath.resolve("model/persons.persons");
		personsModelUri = URI.createFileURI(personsModelPath.toString());
		
		DemoUtility.registerFactories();
		
		if (Files.exists(rootPath)) {
			deletePath(rootPath);
		} else {
			Files.createDirectories(rootPath);
		}
		ProjectMarker.markAsProjectRootFolder(rootPath);
		
		generateViewTypes();
		buildInternalVirtualModel(rootPath);
		
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
	
	private void buildInternalVirtualModel(Path storage) {
		this.vsum = new VirtualModelBuilder()
			.withChangePropagationSpecification(new FamiliesToPersonsChangePropagationSpecification())
			.withChangePropagationSpecification(new PersonsToFamiliesChangePropagationSpecification())
			.withStorageFolder(storage)
			.withViewTypes(viewTypes.values())
			.withUserInteractor(UserInteractionFactory.instance.createUserInteractor(
					UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
			.buildAndInitialize();
	}

	private void generateInitialFamilyRegister() throws Exception {
		var familyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister();
		familyRegister.setId("Family Register");
		
		var view = getFamilyView().withChangeRecordingTrait();
		view.registerRoot(familyRegister, familiesModelUri);
		// The consistency preservation is active and generates a person register automatically.
		view.commitChanges();
		view.close();
	}
	
	private View getView(String viewTypeName, URI srcUri) {
		var selector = vsum.createSelector(getViewType(viewTypeName));
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

	public ViewType<?> getViewType(String viewTypeName) {
		return viewTypes.get(viewTypeName);
	}
	
	public View getFamilyView() {
		return getView(DemoUtility.FAMILIES_VIEW_TYPE_NAME, familiesModelUri);
	}
	
	public View getPersonsView() {
		return getView(DemoUtility.PERSONS_VIEW_TYPE_NAME, personsModelUri);
	}
	
	public void close() {
		((InternalVirtualModel) this.vsum).dispose();
	}
}
