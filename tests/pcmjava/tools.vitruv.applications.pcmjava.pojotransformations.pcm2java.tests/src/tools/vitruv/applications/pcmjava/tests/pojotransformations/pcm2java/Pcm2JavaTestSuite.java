package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository.Pcm2JavaRepositoryTestSuite;
import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.system.Pcm2JavaSystemTestSuite;

@RunWith(Suite.class)

@SuiteClasses({ DataTypeCorrespondenceHelperTest.class,
	Pcm2JavaRepositoryTestSuite.class, Pcm2JavaSystemTestSuite.class })
public class Pcm2JavaTestSuite {

}
