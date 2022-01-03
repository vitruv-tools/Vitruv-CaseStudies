package tools.vitruv.applications.umljava.tests.util

import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.classifiers.Class
import tools.vitruv.framework.vsum.views.View
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import org.emftext.language.java.classifiers.Interface
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.emftext.language.java.containers.Package
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.Enumeration
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.members.Field

// TODO HK These have to finally replace the old utilities
@Utility
class JavaQueryUtil {
	static def containedJavaCompilationUnits(View view) {
		view.rootObjects(CompilationUnit)
	}
	
	static def containedJavaPackages(View view) {
		view.rootObjects(Package)
	}
	
	static def containedJavaClassifiers(View view) {
		view.rootObjects(CompilationUnit).map[classifiers].flatten
	}
	
	static def <T extends Classifier> Iterable<T> containedJavaClassifiersOfType(View view, java.lang.Class<T> type) {
		view.containedJavaClassifiers.filter(type)
	}
	
	static def containedJavaClasses(View view) {
		view.containedJavaClassifiersOfType(Class)
	}
	
	static def containedJavaInterfaces(View view) {
		view.containedJavaClassifiersOfType(Interface)
	}
		
	static def containedJavaCompilationUnit(View view, String compilationUnitNameWithoutJavaSuffic) {
		view.containedJavaCompilationUnits.filter[it.name == compilationUnitNameWithoutJavaSuffic +  ".java"].claimOne
	}	
	
	static def containedJavaPackage(View view, String name) {
		view.containedJavaPackages.filter[it.name == name].claimOne
	}
	
	static def <T extends Classifier> T containedJavaClassifier(View view, java.lang.Class<T> classifierType, String classifiedName) {
		view.containedJavaClassifiersOfType(classifierType).filter[it.name == classifiedName].claimOne
	}
	
	static def containedJavaClass(View view, String className) {
		view.containedJavaClassifier(Class, className)
	}
	
	static def containedJavaEnum(View view, String enumName) {
		view.containedJavaClassifier(Enumeration, enumName)
	}
	
	static def containedJavaInterface(View view, String interfaceName) {
		view.containedJavaClassifier(Interface, interfaceName)
	}
	
	static def InterfaceMethod containedInterfaceMethod(Interface interf, String methodName) {
		interf.members.filter(InterfaceMethod).filter[it.name == methodName].claimOne
	}
	
	static def ClassMethod containedClassMethod(ConcreteClassifier classifier, String methodName) {
		classifier.members.filter(ClassMethod).filter[it.name == methodName].claimOne
	}
	
	static def Field containedField(ConcreteClassifier classifier, String fieldName) {
		classifier.members.filter(Field).filter[it.name == fieldName].claimOne
	}

}