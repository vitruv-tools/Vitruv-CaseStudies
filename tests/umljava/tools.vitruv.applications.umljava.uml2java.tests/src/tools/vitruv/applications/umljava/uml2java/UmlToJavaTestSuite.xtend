package tools.vitruv.applications.umljava.uml2java

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tools.vitruv.applications.umljava.uml2java.tests.*

@RunWith(Suite)

@Suite.SuiteClasses(
    UmlToJavaClassTest,
    UmlToJavaEnumTest,
    UmlToJavaInterfaceMethodTest,
    UmlToJavaInterfaceTest,
    UmlToJavaClassMethodTest,
    UmlToJavaAssociationTest,
    UmlToJavaParameterTest,
    UmlToJavaPackageTest,
    UmlToJavaAttributeTest)
class UmlToJavaTestSuite {
    
}