package tools.vitruv.applications.umlclassumlcomponents.class2comp.tests

import org.junit.Test

import static org.junit.Assert.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil.*
import org.junit.Ignore

class PackageTest extends AbstractClass2CompTest{
	
   	/**********
	*Packages:*
	***********/
	
	@Test
	public def void testCreatePackageWithoutLink() {
		//Create Component without Package:
		val umlClass = createClass(CLASS_NAME, 0)
		saveAndSynchronizeWithInteractions(umlClass)
		assertComponentForClass(umlClass, CLASS_NAME)
		
		val classPackage = createPackage(CLASS_NAME)		
		saveAndSynchronizeWithInteractions(rootElement)
		
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classPackage]).flatten
		assertEquals(0, correspondingElements.size)
	}
		
	@Test
	public def void testPackageIllegalRename1() {
		//Create Package and try to rename to DataType-Package name:
		val classPackage = createPackage(PACKAGE_NAME)				
		saveAndSynchronizeWithInteractions(rootElement)
		classPackage.name = CLASS_DATATYPES_PACKAGE
		saveAndSynchronizeWithInteractions(rootElement)
		
		assertFalse(classPackage.name == CLASS_DATATYPES_PACKAGE_NAME)
	}
	
	@Test
	public def void testPackageIllegalRename2() {
		//Create Package and try to overwrite the pre-existing DataType-Package name:
		val dataTypePackage = createPackage(CLASS_DATATYPES_PACKAGE)
		val classPackage = createPackage(PACKAGE_NAME)				
		saveAndSynchronizeWithInteractions(rootElement)
		classPackage.name = CLASS_DATATYPES_PACKAGE
		saveAndSynchronizeWithInteractions(rootElement)
		
		assertFalse(classPackage.name == CLASS_DATATYPES_PACKAGE_NAME)
		assertTrue(dataTypePackage.name == CLASS_DATATYPES_PACKAGE_NAME)
	}
	
	@Ignore		
	@Test
	public def void testPackageIllegalRename3() {
		//Create Package with legal DataType-Package name, then try to rename it:
		val dataTypePackage = createPackage(CLASS_DATATYPES_PACKAGE)
				
		//Try to rename DataType-Package:
		dataTypePackage.name = PACKAGE_NAME
		saveAndSynchronizeWithInteractions(rootElement)
		
		assertTrue(dataTypePackage.name == CLASS_DATATYPES_PACKAGE_NAME)
	}
	
//	@Test
//	public def void testCreatePackageWithLinkSuccess() { //TODO Fix later with new package linking
//		//Create Component without Package:
//		val umlClass = createClass(CLASS_NAME, 0)
//		saveAndSynchronizeWithInteractions(umlClass)
//		assertComponent(umlClass, CLASS_NAME)
//		
//		//Create Package and try to link it:
//		val classPackage = createPackage(PACKAGE_NAME2, 0, 0)
//		saveAndSynchronizeWithInteractions(rootElement)
//		
//		assertPackageLinkedToComponent(classPackage, COMPONENT_NAME)		
//	}
		
	//TODO implement this
	//@Test
	//public def void testCreatePackageWithLinkSuccessMultiOptions() {
	//	assertTrue(false)
	//}
	
	@Test
	public def void testCreatePackageWithLinkButNoComponent() {
		//Create Package and try to link it no non-existing Component
		val classPackage = createPackage(PACKAGE_NAME, 0, 0)
		saveAndSynchronizeWithInteractions(classPackage)
		
		//No links should exist:
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classPackage]).flatten
		assertEquals(0, correspondingElements.size)
	}
	
//	@Test
//	public def void testCreatePackageWithLinkButComponentHasLink() { //TODO Fix later with new package linking
//		//Create a Class with a corresponding Component:
//		val classPackageOld = createPackage(CLASS_NAME)
//		val umlClass = createClass(CLASS_NAME, classPackageOld, 0)
//		saveAndSynchronizeWithInteractions(umlClass)
//		assertComponent(umlClass, CLASS_NAME)		
//		assertPackageLinkedToComponent(classPackageOld, CLASS_NAME)
//		
//		//Create a new Package and try to link it:
//		val classPackageNew = createPackage(PACKAGE_NAME2, 0, 0)
//		saveAndSynchronizeWithInteractions(rootElement)
//		
//		//No new link should have been created
//		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classPackageNew]).flatten
//		assertEquals(0, correspondingElements.size)
//		
//		//Old link should still exist
//		assertPackageLinkedToComponent(classPackageOld, CLASS_NAME)
//	}	
    	
}