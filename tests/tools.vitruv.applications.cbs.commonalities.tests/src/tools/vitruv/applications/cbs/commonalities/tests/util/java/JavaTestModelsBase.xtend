package tools.vitruv.applications.cbs.commonalities.tests.util.java

import tools.vitruv.applications.cbs.commonalities.tests.util.DomainTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.domains.java.util.JavaModificationUtil
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.JavaUniquePathConstructor

class JavaTestModelsBase extends DomainTestModelsBase {
	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override protected createModelTester() {
		return new JavaModelTester(vitruvApplicationTestAdapter)
	}
	
	def referenceJamoppType(Class<?> type) {
		referenceJamoppType(type.name)
	}
	
	def referenceJamoppType(String fullyQualifiedName) {
		JavaModificationUtil.createNamespaceClassifierReference(
			ConcreteClassifier.at(JavaUniquePathConstructor.getClassifierURI(fullyQualifiedName))
		)
	}
}
