package tools.vitruv.applications.pcmumlcomponents.uml2pcm

import org.junit.runners.Suite.SuiteClasses
import org.junit.runner.RunWith
import org.junit.runners.Suite
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.constructionsimulation.DatatypeConstructionTest

@RunWith(Suite)
@SuiteClasses(ComponentsTest, DataTypesTest, ImportedDataTypesTest, InterfacesTest, ModelTest, MultiplicityTest, DatatypeConstructionTest)
class Uml2PcmTestSuite {
}
