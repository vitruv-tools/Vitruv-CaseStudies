package tools.vitruv.applications.remote.tests.demo;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory;
import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.Member;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.remote.server.VitruvServer;

public class DemoServer implements IApplication {
	
	private VitruvServer server;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		server = new VitruvServer(() -> {
			FamiliesPersonsModel model = new FamiliesPersonsModel();
			CommittableView view = model.getTestView().withChangeRecordingTrait();
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
			return model.get();
		});
		server.start();
		server.awaitInit();
		return IApplicationContext.EXIT_ASYNC_RESULT;
	}

	@Override
	public void stop() {
		if (server != null) {
			server.stop();
		}
	}
}
