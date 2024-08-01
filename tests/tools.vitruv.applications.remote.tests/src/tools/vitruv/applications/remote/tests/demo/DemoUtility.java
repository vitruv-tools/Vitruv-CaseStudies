package tools.vitruv.applications.remote.tests.demo;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import edu.kit.ipd.sdq.metamodels.families.FamiliesPackage;
import edu.kit.ipd.sdq.metamodels.families.impl.FamiliesPackageImpl;
import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage;
import edu.kit.ipd.sdq.metamodels.persons.impl.PersonsPackageImpl;
import tools.vitruv.dsls.reactions.runtime.correspondence.CorrespondencePackage;
import tools.vitruv.dsls.reactions.runtime.correspondence.impl.CorrespondencePackageImpl;

public class DemoUtility {
	public static final String SERVER_HOST = "localhost";
	public static final int SERVER_PORT = 8080;
	public static final String FAMILIES_VIEW_TYPE_NAME = "families-view";
	public static final String PERSONS_VIEW_TYPE_NAME = "persons-view";
	
	public static void registerFactories() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		EPackage.Registry.INSTANCE.put(CorrespondencePackage.eNS_URI, CorrespondencePackageImpl.eINSTANCE);
		EPackage.Registry.INSTANCE.put(FamiliesPackage.eNS_URI, FamiliesPackageImpl.eINSTANCE);
		EPackage.Registry.INSTANCE.put(PersonsPackage.eNS_URI, PersonsPackageImpl.eINSTANCE);
	}
}
