package tools.vitruv.applications.umljava.java2uml

import org.junit.runner.RunWith
import org.junit.runners.Suite
import tools.vitruv.applications.umljava.java2uml.tests.*

@RunWith(Suite)

@Suite.SuiteClasses(
    JavaToUmlClassTest,
    JavaToUmlClassMethodTest,
    JavaToUmlAttributeTest,
    JavaToUmlInterfaceTest,
    JavaToUmlInterfaceMethodTest)
class JavaToUmlTestSuite {
    
}