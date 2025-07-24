package tools.vitruv.applications.cbs.testutils

import java.nio.file.Path
import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors
import org.emftext.language.java.JavaFactory
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.commons.CommonsFactory
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.change.utils.activeannotations.ModelCreators

@ModelCreators(factory=JavaFactory)
class JavaCreators {
	public static val java = new JavaCreators
	public val types = new JavaTypesCreators
	public val classifiers = new JavaClassifiersCreators
	public val members = new JavaMembersCreators
	public val commons = new JavaCommonsCreators
	public val containers = new JavaContainersCreators
	@Accessors
	val MetamodelDescriptor metamodel = new MetamodelDescriptor("java", Set.of("java"))

	def static java(Path path) {
		path.resolveSibling('''«path.fileName».«"java"»''')
	}

	def static java(String path) {
		Path.of(path).java
	}

	@ModelCreators(factory=ClassifiersFactory)
	static class JavaClassifiersCreators {
	}
	
	@ModelCreators(factory=MembersFactory)
	static class JavaMembersCreators {
	}
	
	@ModelCreators(factory=TypesFactory)
	static class JavaTypesCreators {
	}

	@ModelCreators(factory=CommonsFactory)
	static class JavaCommonsCreators {
	}

	@ModelCreators(factory=ContainersFactory)
	static class JavaContainersCreators {
	}
}
