package tools.vitruv.applications.pcmumlclass.tests

import java.util.List
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import static tools.vitruv.applications.util.temporary.uml.UmlClassifierAndPackageUtil.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNull
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

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

		if (!repoPackage.components__Repository.isEmpty || !uPackage.nestedPackages.filter [
			it.ownedMembers.exists[it.name.contains(DefaultLiterals.IMPLEMENTATION_SUFFIX)]
		].isEmpty) {
			assertComponentsEqual(uPackage.nestedPackages.filter [
				it.allOwnedElements.exists[it.toString.contains(DefaultLiterals.IMPLEMENTATION_SUFFIX)]
			].toList, repoPackage.components__Repository)
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

	def static void assertComponentsEqual(List<org.eclipse.uml2.uml.Package> umlComponents,
		List<org.palladiosimulator.pcm.repository.RepositoryComponent> pcmComponents) {
		assertEquals(umlComponents.size, pcmComponents.size)

		for (org.palladiosimulator.pcm.repository.RepositoryComponent pcmComponent : pcmComponents) {
			assertTrue(umlComponents.exists[it.name.equals(pcmComponent.entityName.toFirstLower)])

			// contains a class with implementation
			assertTrue(
				umlComponents.filter[it.name.equals(pcmComponent.entityName.toFirstLower)].claimOne.members.exists [
					it.name.startsWith(pcmComponent.entityName)
				]
			)
		}

		for (org.eclipse.uml2.uml.Package umlComponent : umlComponents) {
			assertTrue(pcmComponents.exists[it.entityName.equals(umlComponent.name.toFirstUpper)])
		}
	}

}
