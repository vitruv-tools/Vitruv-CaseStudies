package tool.vitruv.applications.umljava.uml2java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tool.vitruv.applications.umljava.uml2java.tests.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    //UmlToJavaClassTest.class,
    UmlToJavaInterfaceMethodTest.class,
    UmlToJavaInterfaceTest.class,
    UmlToJavaClassMethodTest.class
})
public class UmlToJavaTestSuite {

}
