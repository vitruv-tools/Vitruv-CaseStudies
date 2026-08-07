package tools.vitruv.applications.demo.remote;

import java.io.IOException;
import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.applications.demo.DemoUtility;
import tools.vitruv.applications.demo.FamiliesPersonsVsumWrapper;
import tools.vitruv.framework.remote.server.VitruvServer;

public class DemoServer {
	public static void main(String[] args) throws IOException {
		var server = new VitruvServer(() -> {
			FamiliesPersonsVsumWrapper vsumWrapper = new FamiliesPersonsVsumWrapper();
			try {
				vsumWrapper.initialize();
			} catch (Exception ex) {
				throw new RuntimeException("Could not initialize the VSUM.", ex);
			}
			
			// Create a family.
			CommittableView view = vsumWrapper.getFamilyView().withChangeRecordingTrait();
			FamilyRegister database = view.getRootObjects(FamilyRegister.class).iterator().next();
			Family family = FamiliesFactory.eINSTANCE.createFamily();
			family.setLastName("Mustermann");
			Member father = FamiliesFactory.eINSTANCE.createMember();
			father.setFirstName("Max");
			Member mother = FamiliesFactory.eINSTANCE.createMember();
			mother.setFirstName("Erika");
			
			family.setFather(father);
			family.setMother(mother);
			database.getFamilies().add(family);
			view.commitChanges();
			try {
				view.close();
			} catch (Exception ex) {
				throw new RuntimeException("Could not close view.", ex);
			}
			
			return vsumWrapper.getVsum();
		}, DemoUtility.SERVER_PORT);

		try {
			server.start();
		} catch (Exception e) {
			throw new RuntimeException("Could not start server", e);
		}
		
		System.out.println("Vitruvius server started on: " + DemoUtility.SERVER_PORT);
	}
}
