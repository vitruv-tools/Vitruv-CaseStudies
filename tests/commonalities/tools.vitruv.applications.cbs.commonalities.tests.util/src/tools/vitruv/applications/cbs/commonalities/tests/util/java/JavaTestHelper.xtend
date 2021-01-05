package tools.vitruv.applications.cbs.commonalities.tests.util.java

import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static com.google.common.base.Preconditions.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.applications.cbs.commonalities.tests.util.ModelMatchers.*

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaFilePathHelper.*

class JavaTestHelper {

	val extension VitruvApplicationTestAdapter vitruvApplicationTestAdapter

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		checkNotNull(vitruvApplicationTestAdapter, "vitruvApplicationTestAdapter is null")
		this.vitruvApplicationTestAdapter = vitruvApplicationTestAdapter
	}

	def Package createAndSynchronizeJavaPackage(Package javaPackage) {
		createAndSynchronizeModel(javaPackage.javaPackageFilePath, javaPackage)
		return javaPackage
	}

	def CompilationUnit createAndSynchronizeJavaCompilationUnit(CompilationUnit compilationUnit) {
		createAndSynchronizeModel(compilationUnit.javaCompilationUnitFilePath, compilationUnit)
		return compilationUnit
	}

	def getJavaPackageResource(Package javaPackage) {
		return getResourceAt(javaPackage.javaPackageFilePath)
	}

	def getJavaCompilationUnitResource(CompilationUnit compilationUnit) {
		return getResourceAt(compilationUnit.javaCompilationUnitFilePath)
	}

	def assertJavaPackageExists(Package javaPackage) {
		assertThat(javaPackage.javaPackageResource, contains(javaPackage, ignoringUnsetFeatures))
	}

	def assertJavaCompilationUnitExists(CompilationUnit compilationUnit) {
		assertThat(compilationUnit.javaCompilationUnitResource, contains(compilationUnit, ignoringUnsetFeatures))
	}
}
