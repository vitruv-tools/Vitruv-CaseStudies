package tools.vitruv.applications.pcmumlcomponents.pcm2uml

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.constructionsimulation.DatatypeConstructionTest
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.versioning.VersioningTest

@RunWith(Suite)
@SuiteClasses(CollectionTypesTest, ComponentsTest, DataTypesTest, InterfacesTest, UmlModelTest, DatatypeConstructionTest,VersioningTest)
class Pcm2UmlTestSuite {
}
