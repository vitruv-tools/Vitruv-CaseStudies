package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ClassTest.class, DataTypeTest.class, InterfaceTest.class, ModelTest.class, VisibilityTest.class,
	UserInputReuseTest.class}) // TODO DK: move test somewhere else?
public class Class2CompTestSuite {

}
