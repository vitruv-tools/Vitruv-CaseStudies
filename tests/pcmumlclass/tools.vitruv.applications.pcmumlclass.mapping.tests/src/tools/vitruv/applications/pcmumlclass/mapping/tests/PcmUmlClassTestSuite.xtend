package tools.vitruv.applications.pcmumlclass.mapping.tests

import org.junit.runner.RunWith
import org.junit.runners.Suite
import tools.vitruv.applications.pcmumlclass.mapping.tests.cases.DatatypesTest
import tools.vitruv.applications.pcmumlclass.mapping.tests.cases.InterfaceTest
import tools.vitruv.applications.pcmumlclass.mapping.tests.cases.ProvidedRoleTest
import tools.vitruv.applications.pcmumlclass.mapping.tests.cases.RepositoryComponentTest
import tools.vitruv.applications.pcmumlclass.mapping.tests.cases.RepositoryTest
import tools.vitruv.applications.pcmumlclass.mapping.tests.cases.RequiredRoleTest
import tools.vitruv.applications.pcmumlclass.mapping.tests.cases.SignatureTest

@RunWith(Suite)

@Suite.SuiteClasses(
    DatatypesTest,
    InterfaceTest,
    ProvidedRoleTest,
    RepositoryComponentTest,
    RepositoryTest,
    RequiredRoleTest,
    SignatureTest
    //,
   //mapping not implemented yet
   // ParameterTest,
   // MediaStoreRepositoryTest
    )
class PcmUmlClassTestSuite {
	
}