package tools.vitruv.applications.umljava.uml2java

import org.junit.runner.RunWith
import org.junit.runners.Suite
import tools.vitruv.applications.umljava.uml2java.tests.UmlToJavaAssociationTest
import tools.vitruv.applications.umljava.uml2java.tests.UmlToJavaAttributeTest
import tools.vitruv.applications.umljava.uml2java.tests.UmlToJavaClassMethodTest
import tools.vitruv.applications.umljava.uml2java.tests.UmlToJavaClassTest
import tools.vitruv.applications.umljava.uml2java.tests.UmlToJavaEnumTest
import tools.vitruv.applications.umljava.uml2java.tests.UmlToJavaInterfaceMethodTest
import tools.vitruv.applications.umljava.uml2java.tests.UmlToJavaInterfaceTest
import tools.vitruv.applications.umljava.uml2java.tests.UmlToJavaPackageTest
import tools.vitruv.applications.umljava.uml2java.tests.UmlToJavaParameterTest

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