package tools.vitruv.applications.umlclassumlcomponentstests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruv.applications.umlclassumlcomponents.class2comp.tests.UmlClassToUmlComponentTestSuite;
import tools.vitruv.applications.umlclassumlcomponents.comp2class.tests.UmlComponentToUmlClassTestSuite;

@RunWith(Suite.class)

@SuiteClasses({ 
	UmlClassToUmlComponentTestSuite.class,
	UmlComponentToUmlClassTestSuite.class
})
public class UmlClassUmlComponentsTestSuite {

}
