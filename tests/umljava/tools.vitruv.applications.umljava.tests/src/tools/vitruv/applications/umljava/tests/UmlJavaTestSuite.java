package tools.vitruv.applications.umljava.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruv.applications.umljava.java2uml.JavaToUmlTestSuite;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaTestSuite;

@RunWith(Suite.class)

@SuiteClasses({ 
	UmlToJavaTestSuite.class,
	JavaToUmlTestSuite.class
})
public class UmlJavaTestSuite {

}
