package tools.vitruv.applications.pcmjava.tests.pcm2java

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.mdsd.jamopp.model.java.containers.CompilationUnit
import tools.mdsd.jamopp.model.java.containers.Package
import tools.vitruv.framework.views.View

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

// TODO: merge this class with JavaQueryUtil of Java <-> UML tests and move code to testutils
@Utility
class JavaQueryUtil {
	static def getJavaCompilationUnits(View view) {
		view.getRootObjects(CompilationUnit)
	}

	static def getJavaPackages(View view) {
		view.getRootObjects(Package)
	}

	static def claimJavaCompilationUnit(View view, String compilationUnitName) {
		view.javaCompilationUnits.filter[it.name == compilationUnitName].claimOne
	}

	static def claimJavaPackage(View view, String name) {
		view.javaPackages.filter[it.name == name].claimOne
	}
}
