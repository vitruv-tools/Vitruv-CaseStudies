package tools.vitruv.applications.cbs.commonalities.tests.cbs

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite)
@SuiteClasses(
	RepositoryTest,
	ComponentInterfaceTest,
	BasicComponentTest,
	CompositeDataTypeTest,
	MediaStoreTest
)
class CBSTestSuite {
}
