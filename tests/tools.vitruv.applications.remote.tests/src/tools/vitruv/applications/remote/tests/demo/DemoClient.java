package tools.vitruv.applications.remote.tests.demo;

import java.nio.file.Path;
import java.nio.file.Paths;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import tools.vitruv.framework.remote.client.VitruvClientFactory;

public class DemoClient {
	private static final Path TEMPORARY_PATH = Paths.get("target", "vitruv-temp").toAbsolutePath();
	
	public static void main(String[] args) throws Exception {
		// Register the meta models used in the VSUM at the server side.
		DemoUtility.registerFactories();
		// Create a client.
		var client = VitruvClientFactory.create(DemoUtility.SERVER_HOST, DemoUtility.SERVER_PORT, TEMPORARY_PATH);
		
		var viewTypes = client.getViewTypes();
		
		var familiesViewType = viewTypes.stream().filter(vt -> vt.getName().equals(DemoUtility.FAMILIES_VIEW_TYPE_NAME)).findAny();
		if (familiesViewType.isEmpty()) {
			throw new IllegalStateException("Looked for the families view tpye, but did not find it.");
		}
		
		// Get a view and change the family name.
		var viewSource = familiesViewType.get().createSelector(null);
		viewSource.getSelectableElements().forEach(element -> viewSource.setSelected(element, true));
		var view = viewSource.createView().withChangeDerivingTrait();
		var familyRegister = view.getRootObjects(FamilyRegister.class).iterator().next();
		var family = familyRegister.getFamilies().get(0);
		family.setLastName("Musterfrau");
		// Commit changes.
		view.commitChangesAndUpdate();
		view.close();

		// Get changed persons.
		var personsViewType = viewTypes.stream().filter(vt -> vt.getName().equals(DemoUtility.PERSONS_VIEW_TYPE_NAME)).findAny();
		if (personsViewType.isEmpty()) {
			throw new IllegalStateException("Looked for the persons view type, but did not find it.");
		}
		var changedViewSource = personsViewType.get().createSelector(null);
		changedViewSource.getSelectableElements().forEach(element -> changedViewSource.setSelected(element, true));
		var changedView = changedViewSource.createView();
		var changedPersonsRegister = changedView.getRootObjects(PersonRegister.class).iterator().next();
		for (var person : changedPersonsRegister.getPersons()) {
			System.out.println("Found person " + person.getFullName());
		}
		changedView.close();
	}
}
