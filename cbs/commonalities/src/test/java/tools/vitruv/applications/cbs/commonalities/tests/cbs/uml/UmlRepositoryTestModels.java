package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import java.util.List;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.RepositoryTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;

public class UmlRepositoryTestModels extends UmlTestModelsBase implements RepositoryTest.DomainModels {

	public UmlRepositoryTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	public DomainModel emptyRepositoryCreation() {
		return newModel(() -> {
			UmlRepositoryModel umlRepositoryModel = new UmlRepositoryModel();
			return List.of(umlRepositoryModel.getModel());
		});
	}
}
