package tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper

import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.containers.CompilationUnit
import tools.vitruv.framework.views.View

import static tools.vitruv.applications.pcmjava.tests.pcm2java.JavaQueryUtil.getJavaCompilationUnits

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

/*
 * This class is a workaround to save the time-consuming programmatic creation
 * of classes from the Java standard library. The required CompilationUnit is 
 * already present in the model to be tested (getJavaCompilationUnits) and is 
 * then copied instead of creating it from scratch.
 */
class JavaStdLibCompilationUnitHelper {
	final View view

	new(View view) {
		this.view = view
	}

	private def CompilationUnit getCompilationUnitByName(String name) {
		val requestedCompilationUnit = getJavaCompilationUnits(view).filter(cu|cu.name == name).claimOne

		return EcoreUtil.copy(requestedCompilationUnit)
	}

	def CompilationUnit getArrayListCompilationUnit() {
		return getCompilationUnitByName("java.util.ArrayList.java")
	}

	def CompilationUnit getIntegerCompilationUnit() {
		return getCompilationUnitByName("java.lang.Integer.java")
	}

	def CompilationUnit getVoidCompilationUnit() {
		return getCompilationUnitByName("java.lang.Void.java")
	}
}
