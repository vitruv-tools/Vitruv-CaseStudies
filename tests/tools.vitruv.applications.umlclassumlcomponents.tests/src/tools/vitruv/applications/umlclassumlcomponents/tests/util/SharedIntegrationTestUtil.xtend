package tools.vitruv.applications.umlclassumlcomponents.tests.util

import java.util.List
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Package
import static org.junit.jupiter.api.Assertions.assertEquals
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.Resource

class SharedIntegrationTestUtil {
	public static val OUTPUT_NAME = "model/model.uml"

	static def Resource getTestModelResource(String fileName) {
		new ResourceSetImpl().getResource(URI.createFileURI("TestModels/" + fileName), true)
	}

	static def assertCountOfTypeInList(List<NamedElement> elementsList, Class<? extends NamedElement> umlType,
		int count) {
		val typeElements = elementsList.filter(umlType)
		assertEquals(count, typeElements.size)
	}

	static def assertCountOfTypeInPackage(List<NamedElement> elementsList, int packageNumber,
		Class<? extends NamedElement> umlType, int count) {
		val packages = elementsList.filter(Package)
		val packagedElements = packages.get(packageNumber).packagedElements
		assertCountOfTypeInList(packagedElements.map[e|e as NamedElement], umlType, count)
	}
}
