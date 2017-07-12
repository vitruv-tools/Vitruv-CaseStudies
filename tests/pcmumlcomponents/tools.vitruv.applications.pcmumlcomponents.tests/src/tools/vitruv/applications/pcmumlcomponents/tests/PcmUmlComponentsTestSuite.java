package tools.vitruv.applications.pcmumlcomponents.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruv.applications.pcmumlcomponents.pcm2uml.Pcm2UmlTestSuite;
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.Uml2PcmTestSuite;

@RunWith(Suite.class)

@SuiteClasses({ 
	Pcm2UmlTestSuite.class,
	Uml2PcmTestSuite.class
	})
public class PcmUmlComponentsTestSuite {

}
