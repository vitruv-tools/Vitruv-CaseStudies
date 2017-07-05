package tools.vitruv.applications.pcmjava.tests

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm.Java2PcmTestSuite
import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTestSuite

@RunWith(Suite)
@SuiteClasses(#[
	Pcm2JavaTestSuite,
	Java2PcmTestSuite
])
class PcmJavaPojoTransformationsTestSuite {
}
