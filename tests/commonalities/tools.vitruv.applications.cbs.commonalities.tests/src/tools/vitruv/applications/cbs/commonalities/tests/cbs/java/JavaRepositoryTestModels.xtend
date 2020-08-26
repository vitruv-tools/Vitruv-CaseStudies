package tools.vitruv.applications.cbs.commonalities.tests.cbs.java

import tools.vitruv.applications.cbs.commonalities.tests.cbs.RepositoryTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsBase

class JavaRepositoryTestModels extends JavaTestModelsBase implements RepositoryTest.DomainModels {

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
