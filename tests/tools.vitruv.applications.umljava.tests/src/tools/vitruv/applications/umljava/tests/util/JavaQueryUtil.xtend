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
import org.emftext.language.java.members.Method
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.members.Constructor

// TODO HK These have to finally replace the old utilities
@Utility
class JavaQueryUtil {
	static def getJavaCompilationUnits(View view) {
		view.rootObjects(CompilationUnit)
	}
	
	static def getJavaPackages(View view) {
		view.rootObjects(Package)
	}
	
	static def getJavaClassifiers(View view) {
		view.rootObjects(CompilationUnit).map[classifiers].flatten
	}
	
	static def <T extends Classifier> Iterable<T> getJavaClassifiersOfType(View view, java.lang.Class<T> type) {
		view.javaClassifiers.filter(type)
	}
	
	static def getJavaClasses(View view) {
		view.getJavaClassifiersOfType(Class)
	}
	
	static def getJavaInterfaces(View view) {
		view.getJavaClassifiersOfType(Interface)
	}
		
	static def claimJavaCompilationUnit(View view, String compilationUnitNameWithoutJavaSuffic) {
		view.javaCompilationUnits.filter[it.name == compilationUnitNameWithoutJavaSuffic +  ".java"].claimOne
	}	
	
	static def claimJavaPackage(View view, String name) {
		view.javaPackages.filter[it.name == name].claimOne
	}
	
	static def <T extends Classifier> T claimJavaClassifier(View view, java.lang.Class<T> classifierType, String classifiedName) {
		view.getJavaClassifiersOfType(classifierType).filter[it.name == classifiedName].claimOne
	}
	
	static def claimJavaClass(View view, String className) {
		view.claimJavaClassifier(Class, className)
	}
	
	static def claimJavaEnum(View view, String enumName) {
		view.claimJavaClassifier(Enumeration, enumName)
	}
	
	static def claimJavaInterface(View view, String interfaceName) {
		view.claimJavaClassifier(Interface, interfaceName)
	}
	
	static def InterfaceMethod claimInterfaceMethod(Interface interf, String methodName) {
		interf.members.filter(InterfaceMethod).filter[it.name == methodName].claimOne
	}
	
	static def ClassMethod claimClassMethod(ConcreteClassifier classifier, String methodName) {
		classifier.members.filter(ClassMethod).filter[it.name == methodName].claimOne
	}
	
	static def Constructor claimConstructor(ConcreteClassifier classifier) {
		classifier.members.filter(Constructor).filter[it.name == classifier.name].claimOne
	}
	
	static def Parameter claimParameter(Method method, String parameterName) {
		method.parameters.filter[it.name == parameterName].claimOne
	}
	
	static def Field claimField(ConcreteClassifier classifier, String fieldName) {
		classifier.members.filter(Field).filter[it.name == fieldName].claimOne
	}

}