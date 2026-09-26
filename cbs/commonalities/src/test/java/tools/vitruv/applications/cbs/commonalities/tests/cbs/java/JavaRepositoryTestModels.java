package tools.vitruv.applications.cbs.commonalities.tests.cbs.java;

import tools.vitruv.applications.cbs.commonalities.tests.cbs.RepositoryTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase;

public class JavaRepositoryTestModels extends JavaTestModelsBase implements RepositoryTest.DomainModels {

    public JavaRepositoryTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    public DomainModel emptyRepositoryCreation() {
        return newModel(() -> {
            JavaRepositoryModel javaRepositoryModel = new JavaRepositoryModel();
            return javaRepositoryModel.getRootObjects();
        });
    }
}