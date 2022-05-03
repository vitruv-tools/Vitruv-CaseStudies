package tools.vitruv.applications.pcmumlclass.tests

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Enumeration
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.Property
import tools.vitruv.framework.views.View

import static tools.vitruv.applications.util.temporary.uml.UmlTypeUtil.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

@Utility
class UmlQueryUtil {
	static def loadUmlPrimitiveType(String name) {
		getUmlPrimitiveTypes(new ResourceSetImpl()).filter[it.name == name].claimOne
	}
	
	static def Model claimUmlModel(View view, String modelName) {
		view.getRootObjects(Model).filter[it.name == modelName].claimOne
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
	
	static def DataType claimDataType(Package containingPackage, String dataTypeName) {
		containingPackage.claimPackageableElement(DataType, dataTypeName)
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

	static def Parameter claimParameter(Operation containingOperation, String parameterName) {
		containingOperation.ownedParameters.filter[it.name == parameterName].claimOne
	}
	
	static def Property claimAttribute(Classifier containingClassifier, String attributeName) {
		containingClassifier.attributes.filter[it.name == attributeName].claimOne
	}
	
}