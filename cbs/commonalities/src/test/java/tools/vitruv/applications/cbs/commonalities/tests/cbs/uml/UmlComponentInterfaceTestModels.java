package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import java.util.List;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.UMLFactory;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.ComponentInterfaceTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;

public class UmlComponentInterfaceTestModels extends UmlTestModelsBase implements ComponentInterfaceTest.DomainModels {

	public UmlComponentInterfaceTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	public DomainModel emptyComponentInterfaceCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();
			Interface umlInterface = UMLFactory.eINSTANCE.createInterface();
			umlInterface.setName(INTERFACE_NAME);
			umlRepositoryModel.getContractsPackage().getPackagedElements().add(umlInterface);
			return List.of(umlRepositoryModel.getModel());
		});
	}
}
