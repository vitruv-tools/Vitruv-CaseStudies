package tool.vitruv.applications.umljava.uml2java

import tool.vitruv.applications.umljava.uml2java.AbstractUmlJavaTest
import org.junit.Test
import org.eclipse.uml2.uml.UMLFactory
import static tools.vitruv.domains.java.util.JavaPersistenceHelper.*;
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.DataType

class UmlToJavaClassTest extends AbstractUmlJavaTest {
	private static val CLASS_NAME = "UmlClass";

	@Test
	public def void testCreateClass() {
		val umlClass = UMLFactory.eINSTANCE.createClass();
		umlClass.name = CLASS_NAME;
		rootElement.packagedElements += umlClass;
		saveAndSynchronizeChanges(umlClass);
		assertModelExists(buildJavaFilePath(CLASS_NAME));
	}
	
	@Test
	public def void testDeletedClass() {
	    val umlClass = UMLFactory.eINSTANCE.createClass();
        umlClass.name = CLASS_NAME;
        rootElement.packagedElements += umlClass;
        saveAndSynchronizeChanges(umlClass);
        assertModelExists(buildJavaFilePath(CLASS_NAME));
        
        umlClass.destroy;
        saveAndSynchronizeChanges(umlClass);
        
        assertModelNotExists(buildJavaFilePath(CLASS_NAME));
        
        
	}
	
	@Test
	public def void testCreateAttribute() {
	    val umlClass = UMLFactory.eINSTANCE.createClass();
        umlClass.name = CLASS_NAME;
        rootElement.packagedElements += umlClass;
        saveAndSynchronizeChanges(umlClass);
        assertModelExists(buildJavaFilePath(CLASS_NAME));
        
        val datatype = UMLFactory.eINSTANCE.createDataType;
        umlClass.createOwnedAttribute("Attr", datatype);
        saveAndSynchronizeChanges(umlClass);
        val iter = getModelResource(buildJavaFilePath(CLASS_NAME)).allContents
        /*
        while (iter.hasNext) {
            iter.
        }*/
	}
}