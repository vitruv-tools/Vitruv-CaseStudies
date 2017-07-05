package tools.vitruv.applications.pcmumlcomp.pcm2uml

import org.junit.runners.Suite.SuiteClasses
import org.junit.runner.RunWith
import org.junit.runners.Suite
import tools.vitruv.applications.pcmumlcomp.pcm2uml.constructionsimulation.DatatypeConstructionTest

@RunWith(Suite)
@SuiteClasses(CollectionTypesTest, ComponentsTest, DataTypesTest, InterfacesTest, UmlModelTest, DatatypeConstructionTest)
class Pcm2UmlTestSuite {
	
}