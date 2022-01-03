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
	static def Model claimUniqueUmlModel(View view) {
		view.rootObjects(Model).claimOne
	}
	
	private static def <E> E claimUniqueElementOfType(Package containingPackage, java.lang.Class<E> containedElementType) {
		containingPackage.packagedElements.filter(containedElementType).claimOne
	}
	
	static def Package claimUniqueUmlPackage(Package containingPackage) {
		containingPackage.claimUniqueElementOfType(Package)
	}
	
	static def Class claimUniqueUmlClass(Package containingPackage) {
		containingPackage.claimUniqueElementOfType(Class)
	}
	
	static def Interface claimUniqueUmlInterface(Package containingPackage) {
		containingPackage.claimUniqueElementOfType(Interface)
	}
	
	static def DataType claimUniqueUmlDataType(Package containingPackage) {
		containingPackage.claimUniqueElementOfType(DataType)
	}
	
	static def Model claimUmlModel(View view, String modelName) {
		view.rootObjects(Model).filter[it.name == modelName].claimOne
	}
	
	static def <T extends PackageableElement> T claimPackageableElement(Package containingPackage, java.lang.Class<T> packageableElementType, String packageableElementName) {
		containingPackage.packagedElements.filter(packageableElementType).filter[it.name == packageableElementName].claimOne
	}
	
	static def Package claimPackage(Package containingPackage, String packageName) {
		containingPackage.claimPackageableElement(Package, packageName)
	}
	
	static def Class claimClass(Package containingPackage, String className) {
		containingPackage.claimPackageableElement(Class, className)
	}
	
	static def Enumeration claimEnum(Package containingPackage, String enumName) {
		containingPackage.claimPackageableElement(Enumeration, enumName)
	}
	
	static def Interface claimInterface(Package containingPackage, String interfaceName) {
		containingPackage.claimPackageableElement(Interface, interfaceName)
	}
	
	static def Operation claimOperation(Classifier containingClassifier, String operationName) {
		containingClassifier.operations.filter[it.name == operationName].claimOne
	}
	
	static def Property claimAttribute(Classifier containingClassifier, String attributeName) {
		containingClassifier.attributes.filter[it.name == attributeName].claimOne
	}
	
}