package tools.vitruv.applications.pcmumlclass.tests

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite)

@Suite.SuiteClasses(
    AssemblyContextConceptTest,
    AttributeConceptTest,
    CompositeDataTypeConceptTest,
    InterfaceConceptTest,
    MediaStoreRepositoryCreationTest,
    ParameterConceptTest,
    ProvidedRoleTest,
    RepositoryComponentConceptTest,
    RepositoryConceptTest,
    RequiredRoleConceptTest,
    SignatureConceptTest,
    SystemConceptTest)
class PcmUmlClassTestSuite {
	
}