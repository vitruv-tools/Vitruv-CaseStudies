package tools.vitruv.applications.umljava.tests.util

import org.eclipse.uml2.uml.Model
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.vsum.views.View
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.Enumeration
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property

// TODO HK These have to finally replace the old utilities
@Utility
class UmlQueryUtil {
	static def Model getUniqueUmlModel(View view) {
		view.rootObjects(Model).claimOne
	}
	
	private static def <E> E containedElement(Package containingPackage, java.lang.Class<E> containedElementType) {
		containingPackage.packagedElements.filter(containedElementType).claimOne
	}
	
	static def Package getUniqueUmlPackage(Package containingPackage) {
		containingPackage.containedElement(Package)
	}
	
	static def Class getUniqueUmlClass(Package containingPackage) {
		containingPackage.containedElement(Class)
	}
	
	static def Interface getUniqueUmlInterface(Package containingPackage) {
		containingPackage.containedElement(Interface)
	}
	
	static def DataType getUniqueUmlDataType(Package containingPackage) {
		containingPackage.containedElement(DataType)
	}
	
	static def Model containedUmlModel(View view, String modelName) {
		view.rootObjects(Model).filter[it.name == modelName].claimOne
	}
	
	static def <T extends PackageableElement> T containedPackageableElement(Package containingPackage, java.lang.Class<T> classifierType, String packageableElementName) {
		containingPackage.packagedElements.filter(classifierType).filter[it.name == packageableElementName].claimOne
	}
	
	static def Package containedPackage(Package containingPackage, String packageName) {
		containingPackage.containedPackageableElement(Package, packageName)
	}
	
	static def Class containedClass(Package containingPackage, String className) {
		containingPackage.containedPackageableElement(Class, className)
	}
	
	static def Enumeration containedEnum(Package containingPackage, String enumName) {
		containingPackage.containedPackageableElement(Enumeration, enumName)
	}
	
	static def Interface containedInterface(Package containingPackage, String interfaceName) {
		containingPackage.containedPackageableElement(Interface, interfaceName)
	}
	
	static def Operation containedOperation(Classifier containingClassifier, String operationName) {
		containingClassifier.operations.filter[it.name == operationName].claimOne
	}
	
	static def Property containedAttribute(Classifier containingClassifier, String attributeName) {
		containingClassifier.attributes.filter[it.name == attributeName].claimOne
	}
	
}