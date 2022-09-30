package tools.vitruv.applications.pcmjava.tests.pcm2java

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.classifiers.Enumeration
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.members.Method
import org.emftext.language.java.parameters.Parameter
import tools.vitruv.framework.views.View

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static extension tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.isInExistingLibrary

@Utility
class JavaQueryUtil {
	static def getJavaCompilationUnits(View view) {
		view.getRootObjects(CompilationUnit)
	}

	static def getJavaPackages(View view) {
		view.getRootObjects(Package)
	}

	static def getJavaClassifiers(View view) {
		view.getRootObjects(CompilationUnit).map[classifiers].flatten
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

	static def claimJavaCompilationUnit(View view, String compilationUnitName) {
		view.javaCompilationUnits.filter[it.name == compilationUnitName].claimOne
	}

	static def claimJavaPackage(View view, String name) {
		view.javaPackages.filter[it.name == name].claimOne
	}

	static def <T extends Classifier> T claimJavaClassifier(View view, java.lang.Class<T> classifierType,
		String classifierName) {
		view.getJavaClassifiersOfType(classifierType).filter [
			!it.isInExistingLibrary && it.name == classifierName
		].claimOne
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
		classifier.fields.filter[it.name == fieldName].claimOne
	}

}
