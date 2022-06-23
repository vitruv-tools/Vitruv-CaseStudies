package tools.vitruv.applications.cbs.testutils

import java.nio.file.Path
import org.eclipse.uml2.uml.resource.UMLResource
import tools.vitruv.testutils.activeannotations.ModelCreators
import org.eclipse.uml2.uml.UMLFactory
import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors

@ModelCreators(factory = UMLFactory)
class UmlCreators {
	public static val uml = new UmlCreators
	@Accessors
	val MetamodelDescriptor metamodel = new MetamodelDescriptor("uml", Set.of("uml"))

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