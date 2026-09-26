package tools.vitruv.applications.cbs.commonalities.tests.util.java;

import org.emftext.language.java.JavaUniquePathConstructor;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.types.TypeReference;

import tools.vitruv.applications.cbs.commonalities.tests.util.DomainTestModelsBase;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.util.temporary.java.JavaModificationUtil;

public class JavaTestModelsBase extends DomainTestModelsBase {

    public JavaTestModelsBase(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
    }

    @Override
    protected JavaModelTester createModelTester() {
        return new JavaModelTester(vitruvApplicationTestAdapter);
    }

    protected TypeReference referenceJamoppType(Class<?> type) {
        return referenceJamoppType(type.getName());
    }

    protected TypeReference referenceJamoppType(String fullyQualifiedName) {
        return JavaModificationUtil.createNamespaceClassifierReference(
                this.vitruvApplicationTestAdapter.<ConcreteClassifier>at(ConcreteClassifier.class,
                        JavaUniquePathConstructor.getClassifierURI(fullyQualifiedName)));
    }
}