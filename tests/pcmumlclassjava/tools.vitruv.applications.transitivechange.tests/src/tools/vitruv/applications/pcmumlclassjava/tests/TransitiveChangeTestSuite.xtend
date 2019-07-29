package tools.vitruv.applications.pcmumlclassjava.tests

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite)
@Suite.SuiteClasses(
	AssemblyContextConceptTest,
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
