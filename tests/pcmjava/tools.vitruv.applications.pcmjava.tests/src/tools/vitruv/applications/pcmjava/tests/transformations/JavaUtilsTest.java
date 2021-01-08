package tools.vitruv.applications.pcmjava.tests.transformations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.emftext.language.java.containers.CompilationUnit;
import org.junit.jupiter.api.Test;

import tools.vitruv.domains.java.util.JavaModificationUtil;

/**
 * Test for the JaMoPPPCMUtil class - not a utility class. Tests the textual
 * creation of a CompilationUnit using JaMoPP.
 *
 * @author Langhamm
 *
 */
public class JavaUtilsTest {

	@Test
	public void testCreateCompilationUnit() throws IOException {
		final String className = "TestCollectionDataType";
		@SuppressWarnings("rawtypes")
		final Class<? extends Collection> selectedClass = ArrayList.class;
		final String content = "package " + "datatypes;" + "\n\n" + "import " + selectedClass.getPackage().getName()
				+ "." + selectedClass.getSimpleName() + ";\n\n" + "public class " + className + " extends "
				+ selectedClass.getSimpleName() + "<" + "String" + ">" + " {\n" + "\n\n" + "}";
		final CompilationUnit cu = JavaModificationUtil.createCompilationUnit(className, content);

		assertEquals("CompilationUnit name is wrong", cu.getName(), className + ".java");
		assertTrue("No classifier in compliation unit", cu.getClassifiers().size() == 1);
		assertEquals("ClassifierName name is wrong", cu.getClassifiers().get(0).getName(), className);
	}

}
