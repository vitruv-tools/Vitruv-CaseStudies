package tools.vitruv.applications.cbs.testutils

import tools.vitruv.testutils.activeannotations.ModelCreators
import org.emftext.language.java.JavaFactory
import org.emftext.language.java.types.TypesFactory
import org.emftext.language.java.commons.CommonsFactory
import tools.vitruv.domains.java.JavaDomainProvider
import org.eclipse.xtend.lib.annotations.Accessors
import java.nio.file.Path
import tools.vitruv.domains.java.JavaNamespace
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.classifiers.ClassifiersFactory

@ModelCreators(factory=JavaFactory)
class JavaCreators {
	public static val java = new JavaCreators
	public val types = new JavaTypesCreators
	public val classifiers = new JavaClassifiersCreators
	public val commons = new JavaCommonsCreators
	public val containers = new JavaContainersCreators
	@Accessors
	val domain = new JavaDomainProvider().domain

	def static java(Path path) {
		path.resolveSibling('''«path.fileName».«JavaNamespace.FILE_EXTENSION»''')
	}

	def static java(String path) {
		Path.of(path).java
	}

	@ModelCreators(factory=ClassifiersFactory)
	static class JavaClassifiersCreators {
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
