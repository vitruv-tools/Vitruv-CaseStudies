package tools.vitruv.applications.cbs.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruv.applications.pcmjava.tests.PcmJavaTestSuite;
import tools.vitruv.applications.pcmumlcomponents.tests.PcmUmlComponentsTestSuite;
import tools.vitruv.applications.umljava.tests.UmlJavaTestSuite;

@RunWith(Suite.class)

@SuiteClasses({ 
	UmlJavaTestSuite.class,
	PcmUmlComponentsTestSuite.class,
	PcmJavaTestSuite.class
	})
public class ComponentBasedSystemsTestSuite {

}
