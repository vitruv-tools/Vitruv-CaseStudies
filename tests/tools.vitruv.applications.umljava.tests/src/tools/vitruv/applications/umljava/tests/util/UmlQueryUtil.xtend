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

// TODO HK These have to finally replace the old utilities
@Utility
class UmlQueryUtil {
	static def Model getUniqueUmlModel(View view) {
		view.rootObjects(Model).claimOne
	}
	
	private static def <E> E getUnique(Package containingPackage, java.lang.Class<E> type) {
		containingPackage.packagedElements.filter(type).claimOne
	}
	
	static def Package getUniqueUmlPackage(Package containingPackage) {
		containingPackage.getUnique(Package)
	}
	
	static def Class getUniqueUmlClass(Package containingPackage) {
		containingPackage.getUnique(Class)
	}
	
	static def Interface getUniqueUmlInterface(Package containingPackage) {
		containingPackage.getUnique(Interface)
	}
	
	static def DataType getUniqueUmlDataType(Package containingPackage) {
		containingPackage.getUnique(DataType)
	}
	
	static def Model getUniqueUmlModelWithName(View view, String name) {
		view.rootObjects(Model).filter[it.name == name].claimOne
	}
	
	static def Package getUniqueUmlPackageWithName(Package containingPackage, String name) {
		containingPackage.packagedElements.filter(Package).filter[it.name == name].claimOne
	}
	
	static def Class getUniqueUmlClassWithName(Package containingPackage, String name) {
		containingPackage.packagedElements.filter(Class).filter[it.name == name].claimOne
	}
	
	static def Interface getUniqueUmlInterfaceWithName(Package containingPackage, String name) {
		containingPackage.packagedElements.filter(Interface).filter[it.name == name].claimOne
	}
	
	static def Operation getUniqueUmlInterfaceOperationWithName(Interface interf, String operationName) {
		interf.operations.filter[it.name == operationName].claimOne
	}	

}