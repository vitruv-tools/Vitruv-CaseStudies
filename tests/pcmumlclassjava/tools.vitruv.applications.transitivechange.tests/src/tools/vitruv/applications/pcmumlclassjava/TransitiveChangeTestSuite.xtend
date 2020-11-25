package tools.vitruv.applications.pcmumlclassjava

import org.junit.runner.RunWith
import org.junit.runners.Suite
import tools.vitruv.applications.pcmumlclassjava.tests.AssemblyContextConceptTest
import tools.vitruv.applications.pcmumlclassjava.tests.AttributeConceptTest
import tools.vitruv.applications.pcmumlclassjava.tests.CompositeDataTypeConceptTest
import tools.vitruv.applications.pcmumlclassjava.tests.InterfaceConceptTest
import tools.vitruv.applications.pcmumlclassjava.tests.ParameterConceptTest
import tools.vitruv.applications.pcmumlclassjava.tests.ProvidedRoleTest
import tools.vitruv.applications.pcmumlclassjava.tests.RepositoryComponentConceptTest
import tools.vitruv.applications.pcmumlclassjava.tests.RepositoryConceptTest
import tools.vitruv.applications.pcmumlclassjava.tests.RequiredRoleConceptTest
import tools.vitruv.applications.pcmumlclassjava.tests.SignatureConceptTest
import tools.vitruv.applications.pcmumlclassjava.tests.SystemConceptTest

@RunWith(Suite)
@Suite.SuiteClasses(AssemblyContextConceptTest,
    AttributeConceptTest,
    CompositeDataTypeConceptTest,
    InterfaceConceptTest,
    ParameterConceptTest,
    ProvidedRoleTest,
    RepositoryComponentConceptTest,
    RepositoryConceptTest,
    RequiredRoleConceptTest,
    SignatureConceptTest,
    SystemConceptTest)
class TransitiveChangeTestSuite {
}
