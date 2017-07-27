package tools.vitruv.applications.pcmumlcomponents.pcm2uml

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.constructionsimulation.DatatypeConstructionTest

@RunWith(Suite)
@SuiteClasses(CollectionTypesTest, ComponentsTest, DataTypesTest, InterfacesTest, UmlModelTest, DatatypeConstructionTest)
class Pcm2UmlTestSuite {
}
