package tools.vitruv.applications.remote.tests.demo;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import edu.kit.ipd.sdq.metamodels.families.FamiliesPackage;
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.families.impl.FamiliesPackageImpl;
import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage;
import edu.kit.ipd.sdq.metamodels.persons.impl.PersonsPackageImpl;
import tools.vitruv.dsls.reactions.runtime.correspondence.CorrespondencePackage;
import tools.vitruv.dsls.reactions.runtime.correspondence.impl.CorrespondencePackageImpl;
import tools.vitruv.framework.remote.client.VitruvRemoteConnection;

public class DemoClient implements IApplication {


	@Override
	public Object start(IApplicationContext context) throws Exception {
		// Register meta models the VSUM is using at the server side
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		EPackage.Registry.INSTANCE.put(CorrespondencePackage.eNS_URI, CorrespondencePackageImpl.init());
		EPackage.Registry.INSTANCE.put(FamiliesPackage.eNS_URI, FamiliesPackageImpl.init());
		EPackage.Registry.INSTANCE.put(PersonsPackage.eNS_URI, PersonsPackageImpl.init());
		// Create a client
		var client = new VitruvRemoteConnection("localhost");

		// Get a view and change the family name
		var view = client.getView("test").withChangeDerivingTrait();
		var register = view.getRootObjects(FamilyRegister.class).iterator().next();
		var family = register.getFamilies().get(0);
		family.setLastName("Musterfrau");

		// Commit changes
		view.commitChanges();

		// Get changed family
		var changedView = client.getView("test");
		var changedRegister = changedView.getRootObjects(FamilyRegister.class).iterator().next();
		var changedFamily = changedRegister.getFamilies().get(0);

		System.out.println(changedFamily.getLastName());
		return IApplicationContext.EXIT_ASYNC_RESULT;
	}

	@Override
	public void stop() {
		//NO OP
	}
}
