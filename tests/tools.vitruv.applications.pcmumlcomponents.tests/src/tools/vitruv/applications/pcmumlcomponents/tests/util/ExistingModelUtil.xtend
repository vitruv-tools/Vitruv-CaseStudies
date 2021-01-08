package tools.vitruv.applications.pcmumlcomponents.tests.util

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class ExistingModelUtil {
	static def loadModel(String path) {
		new ResourceSetImpl().getResource(URI.createURI(path), true)
	}
}
