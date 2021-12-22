package tools.vitruv.applications.umljava.tests.util

import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.classifiers.Class
import tools.vitruv.framework.vsum.views.View
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import org.emftext.language.java.classifiers.Interface
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.emftext.language.java.containers.Package

// TODO HK These have to finally replace the old utilities
@Utility
class JavaQueryUtil {
	static def getUniqueJavaClassWithName(View view, String name) {
		view.javaClasses.filter[it.name == name].claimOne
	}
	
	static def getUniqueJavaPackageWithName(View view, String name) {
		view.javaPackages.filter[it.name == name].claimOne
	}
	
	private static def getJavaClassifiers(View view) {
		view.rootObjects(CompilationUnit).map[classifiers].flatten
	}
	
	static def getJavaPackages(View view) {
		view.rootObjects(Package)
	}
	
	static def getJavaClasses(View view) {
		view.javaClassifiers.filter(Class)
	}
	
	static def getUniqueJavaInterfaceWithName(View view, String name) {
		view.javaInterfaces.filter[it.name == name].claimOne
	}
	
	static def getJavaInterfaces(View view) {
		view.javaClassifiers.filter(Interface)
	}
}