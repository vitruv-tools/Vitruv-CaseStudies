package tools.vitruv.applications.pcmjava.tests.ejbtransformations.java2pcm

import org.junit.runners.Suite.SuiteClasses
import org.junit.runners.Suite
import org.junit.runner.RunWith

@RunWith(Suite)

@SuiteClasses( #[
	EjbClassMappingTest, 
	EjbFieldMappingTest,
	EjbImplementsMappingTest,
	EjbInterfaceMappingTest,
	EjbPackageMappingTest
])
class EjbJava2PcmTransformationsTestSuite {
	
}