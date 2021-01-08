package tools.vitruv.applications.pcmumlcomponents.tests.util

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI

class ExistingModelUtil {
	static def loadModel(String path) {
		new ResourceSetImpl().getResource(URI.createURI(path), true)
	}
}