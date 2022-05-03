package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.uml2.uml.Feature
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Property
import java.util.List
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.EnumerationLiteral
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.VisibilityKind
import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.fail
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Enumeration
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.palladiosimulator.pcm.system.System

class PcmUmlElementEqualityValidation {

	// **************
	// PACKAGES
	// **************
	/**
	 * Does not compare the package contents
	 */
	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Package uPackage,
		org.palladiosimulator.pcm.system.System sysPackage) {
		assertEquals(uPackage.name.toFirstUpper, sysPackage.entityName, "Package names must be equal")
		println("uml namescpaces " + getUmlParentNamespaceAsStringList(uPackage))
		if (getUmlParentNamespaceAsStringList(uPackage).isEmpty()) {
			assertNull(sysPackage.eContainer, "Container namespaces must be null")
		} else {
			assertEquals(getUmlParentNamespaceAsStringList(uPackage), sysPackage.eContainer.toString,
			"Container namespaces names must be equal")
		}
		
	}


}