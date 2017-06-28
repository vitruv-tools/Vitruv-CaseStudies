package tools.vitruv.applications.umljava.java2uml

import org.junit.runner.RunWith
import org.junit.runners.Suite
import tools.vitruv.applications.umljava.java2uml.tests.JavaToUmlAttributeTest
import tools.vitruv.applications.umljava.java2uml.tests.JavaToUmlClassMethodTest
import tools.vitruv.applications.umljava.java2uml.tests.JavaToUmlClassTest
import tools.vitruv.applications.umljava.java2uml.tests.JavaToUmlEnumTest
import tools.vitruv.applications.umljava.java2uml.tests.JavaToUmlInterfaceMethodTest
import tools.vitruv.applications.umljava.java2uml.tests.JavaToUmlInterfaceTest
import tools.vitruv.applications.umljava.java2uml.tests.JavaToUmlPackageTest

@RunWith(Suite)

@Suite.SuiteClasses(
    JavaToUmlClassTest,
    JavaToUmlClassMethodTest,
    JavaToUmlAttributeTest,
    JavaToUmlInterfaceTest,
    JavaToUmlPackageTest,
    JavaToUmlEnumTest,
    JavaToUmlInterfaceMethodTest)
class JavaToUmlTestSuite {
    
}