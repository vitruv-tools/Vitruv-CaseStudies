package tools.vitruv.applications.cbs.commonalities.tests.cbs.java

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractRepositoryTest
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class JavaRepositoryTestModels extends JavaTestModelsBase implements AbstractRepositoryTest.DomainModels {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override emptyRepositoryCreation() {
		return newModel [
			val javaRepositoryModel = new JavaRepositoryModel()
			return javaRepositoryModel.rootObjects
		]
	}
}
