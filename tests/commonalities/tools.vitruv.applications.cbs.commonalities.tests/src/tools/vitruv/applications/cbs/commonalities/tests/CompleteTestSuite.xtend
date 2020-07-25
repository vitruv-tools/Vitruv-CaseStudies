package tools.vitruv.applications.cbs.commonalities.tests

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import tools.vitruv.applications.cbs.commonalities.tests.cbs.CBSTestSuite
import tools.vitruv.applications.cbs.commonalities.tests.oo.OOTestSuite

@RunWith(Suite)
@SuiteClasses(
	OOTestSuite,
	CBSTestSuite
)
class CompleteTestSuite {
}
