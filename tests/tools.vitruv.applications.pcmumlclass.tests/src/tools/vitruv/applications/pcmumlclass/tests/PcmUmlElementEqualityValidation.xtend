package tools.vitruv.applications.pcmumlclass.tests

import java.util.List
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNull

class PcmUmlElementEqualityValidation {

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

		// repo package is not supposed to contain the following packages, relation is implicit in pcm
		assertTrue(uPackage.packagedElements.exists[it.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME],
			"uml model is incomplete, contracts missing")
		assertTrue(uPackage.packagedElements.exists[it.name == DefaultLiterals.DATATYPES_PACKAGE_NAME],
			"uml model is incomplete, data types missing")

		if (!uPackage.packagedElements.findFirst[it.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME].ownedElements.
			isEmpty || !repoPackage.interfaces__Repository.isEmpty) {
			assertElementsEqual(
				uPackage.packagedElements.findFirst[it.name == DefaultLiterals.CONTRACTS_PACKAGE_NAME].eContents,
				repoPackage.interfaces__Repository)
		}
	}

	def static dispatch void assertElementsEqual(List<org.eclipse.uml2.uml.Interface> umlContracts,
		List<org.palladiosimulator.pcm.repository.Interface> pcmInterfaces) {
		assertEquals(umlContracts.size, pcmInterfaces.size)

		for (org.eclipse.uml2.uml.Interface umlInterface : umlContracts) {
			assertTrue(pcmInterfaces.exists[it.entityName.equals(umlInterface.name)], "UML contracts incomplete")
		}
		for (org.palladiosimulator.pcm.repository.Interface pcmInterface : pcmInterfaces) {
			assertTrue(umlContracts.exists[it.name.equals(pcmInterface.entityName)], "PCM interfaces incomplete")
		}
	}

}
