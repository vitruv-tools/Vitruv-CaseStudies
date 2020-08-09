package tools.vitruv.applications.cbs.commonalities.tests.oo

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite)
@SuiteClasses(
	PackageTest,
	InterfaceTest,
	ClassTest,
	ClassPropertyTest,
	InterfaceMethodTest,
	ClassMethodTest
)
class OOTestSuite {
}
