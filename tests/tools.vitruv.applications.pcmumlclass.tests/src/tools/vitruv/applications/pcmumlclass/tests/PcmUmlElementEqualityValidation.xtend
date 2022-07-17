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
import org.eclipse.emf.ecore.EObject
import tools.vitruv.applications.pcmumlclass.DefaultLiterals

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
		assertTrue(getUmlParentNamespaceAsStringList(uPackage).isEmpty(), "There should be no UML parent package")
		assertNull(sysPackage.eContainer, "There should be no PCM parent package")
		
	}

	def static dispatch void assertElementsEqual(org.eclipse.uml2.uml.Package uPackage,
		org.palladiosimulator.pcm.repository.Repository repoPackage) {
		assertEquals(uPackage.name.toFirstUpper, repoPackage.entityName, "Package names must be equal")
		
		if (getUmlParentNamespaceAsStringList(uPackage).isEmpty()) {
			assertNull(repoPackage.eContainer, "Container namespaces must be null")
		} else {
			assertEquals(getUmlParentNamespaceAsStringList(uPackage), repoPackage.eContainer.toString,
			"Container namespaces names must be equal")
		}
		
		for (EObject pcmChild : repoPackage.eContents) {
			assertTrue(uPackage.eContents.contains(pcmChild), "uml model is incomplete")
		}
	}
	
	def static dispatch void assertElementsEqual_deep(org.eclipse.uml2.uml.Package uPackage,
		org.palladiosimulator.pcm.repository.Repository repoPackage) {
		assertEquals(uPackage.name.toFirstUpper, repoPackage.entityName, "Package names must be equal")
		
		if (getUmlParentNamespaceAsStringList(uPackage).isEmpty()) {
			assertNull(repoPackage.eContainer, "Container namespaces must be null")
		} else {
			assertEquals(getUmlParentNamespaceAsStringList(uPackage), repoPackage.eContainer.toString,
			"Container namespaces names must be equal")
		}
		
		for (EObject pcmChild : repoPackage.eContents) {
			assertTrue(uPackage.eContents.contains(pcmChild), "uml model is incomplete")
		}
		
		assertTrue(uPackage.packagedElements.exists[it.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME])
		assertTrue(uPackage.packagedElements.exists[it.name == DefaultLiterals.DATATYPES_PACKAGE_NAME])
	}
}