package tools.vitruv.applications.demo.local;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import tools.vitruv.applications.demo.FamiliesPersonsVsumWrapper;

public class LocalVitruvDemo {
	private LocalVitruvDemo() {}
	
	public static void main(String[] args) throws Exception {
		// Setup the V-SUMM.
		var wrapper = new FamiliesPersonsVsumWrapper();
		wrapper.initialize();
		
		// Obtain the family view.
		var familyView = wrapper.getFamilyView().withChangeRecordingTrait();
		// The view contains a family register, which is obtained from the view.
		var familyRegister = familyView.getRootObjects(FamilyRegister.class).stream().findFirst().get();
		
		// Fill family register with one family.
		familyRegister.setId("Family Register #1");
		var family = FamiliesFactory.eINSTANCE.createFamily();
		family.setLastName("Smith");
		familyRegister.getFamilies().add(family);
		
		var mother = FamiliesFactory.eINSTANCE.createMember();
		mother.setFirstName("A");
		family.setMother(mother);
		var father = FamiliesFactory.eINSTANCE.createMember();
		father.setFirstName("B");
		family.setFather(father);
		var son = FamiliesFactory.eINSTANCE.createMember();
		son.setFirstName("C");
		family.getSons().add(son);
		
		familyView.commitChanges();
		
		// Look at persons register (read-only).
		var personsView = wrapper.getPersonsView();
		var personRegister = personsView.getRootObjects(PersonRegister.class).stream().findFirst().get();
		personRegister.getPersons().forEach((person) -> {
			System.out.println(person.getFullName()); // Should be a first name with "Smith" as last name.
		});
		
		// Change the family name.
		family.setLastName("Musterfrau");
		familyView.commitChanges();

		// Check persons view.
		personsView.update();
		// Previous elements obtained from the view are outdated and not updated. Therefore, the current element must be again obtained from the view.
		personRegister = personsView.getRootObjects(PersonRegister.class).stream().findFirst().get();
		personRegister.getPersons().forEach((person) -> {
			System.out.println(person.getFullName()); // Should be now ending with "Musterfrau".
		});
		
		personsView.close();
		familyView.close();
		wrapper.close();
	}
}
