package tools.vitruv.applications.cbs.testutils

import tools.vitruv.domains.uml.UmlDomainProvider
import org.eclipse.xtend.lib.annotations.Accessors
import java.nio.file.Path
import org.eclipse.uml2.uml.resource.UMLResource
import tools.vitruv.testutils.activeannotations.ModelCreators
import org.eclipse.uml2.uml.UMLFactory

@ModelCreators(factory = UMLFactory)
class UmlCreators {
	public static val uml = new UmlCreators
	@Accessors
	val domain = new UmlDomainProvider().domain

	def static uml(Path path) {
		path.resolveSibling(path.fileName.toString.umlExtension)
	}

	def static uml(String path) {
		Path.of(path).uml
	}
	
	def static String umlExtension(String string) {
		'''«string».«UMLResource.FILE_EXTENSION»'''
	}
}