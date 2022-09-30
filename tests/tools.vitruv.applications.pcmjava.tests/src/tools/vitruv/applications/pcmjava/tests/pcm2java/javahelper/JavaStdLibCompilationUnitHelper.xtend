package tools.vitruv.applications.pcmjava.tests.pcm2java.javahelper

import tools.vitruv.framework.views.View
import org.emftext.language.java.containers.CompilationUnit
import static tools.vitruv.applications.pcmjava.tests.pcm2java.JavaQueryUtil.*
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import org.eclipse.emf.ecore.util.EcoreUtil

/*
 * This class is a workaround to save the time-consuming programmatic creation
 * of classes from the Java standard library. The required CompilationUnit is 
 * already present in the model to be tested (getJavaCompilationUnits) and is 
 * then copied instead of creating it from scratch.
 */
class JavaStdLibCompilationUnitHelper {
	final View view
	
	new(View view){
		this.view = view
	}
	
	def CompilationUnit getCompilationUnitFor(String name){
		val requestedCompilationUnit = 
			getJavaCompilationUnits(view)
			// make sure that only CompilationUnits of the standard library are used
			.filter(cu | cu.name.startsWith("java."))
			.filter(cu | cu.name == name)
			.claimOne
			
		return EcoreUtil.copy(requestedCompilationUnit)
	}
}
