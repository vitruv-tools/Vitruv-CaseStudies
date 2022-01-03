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
		
	static def getUniqueJavaCompilationUnitWithName(View view, String name) {
		view.javaCompilationUnits.filter[it.name == name +  ".java"].claimOne
	}	
	
	static def getUniqueJavaPackageWithName(View view, String name) {
		view.javaPackages.filter[it.name == name].claimOne
	}
	
	static def <T extends Classifier> T getUniqueJavaClassifierWithName(View view, java.lang.Class<T> classifierType, String name) {
		view.getJavaClassifiersOfType(classifierType).filter[it.name == name].claimOne
	}
	
	static def getUniqueJavaClassWithName(View view, String name) {
		view.getUniqueJavaClassifierWithName(Class, name)
	}
	
	static def getUniqueJavaEnumWithName(View view, String name) {
		view.getUniqueJavaClassifierWithName(Enumeration, name)
	}
	
	static def getUniqueJavaInterfaceWithName(View view, String name) {
		view.getUniqueJavaClassifierWithName(Interface, name)
	}
	
	static def InterfaceMethod getUniqueJavaInterfaceOperationWithName(Interface interf, String operationName) {
		interf.members.filter(InterfaceMethod).filter[it.name == operationName].claimOne
	}
	
	static def ClassMethod getUniqueJavaClassOperationWithName(ConcreteClassifier classifier, String operationName) {
		classifier.members.filter(ClassMethod).filter[it.name == operationName].claimOne
	}
	
	static def Field getUniqueJavaClassFieldWithName(ConcreteClassifier classifier, String fieldName) {
		classifier.members.filter(Field).filter[it.name == fieldName].claimOne
	}

}