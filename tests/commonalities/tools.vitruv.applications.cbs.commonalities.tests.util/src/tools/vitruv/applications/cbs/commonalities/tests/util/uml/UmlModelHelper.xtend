package tools.vitruv.applications.cbs.commonalities.tests.util.uml

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package

@Utility
class UmlModelHelper {

	static def insertPackageWithClass(Model umlModel, Package umlPackage, Class umlClass) {
		return umlModel => [
			packagedElements += umlPackage => [
				packagedElements += umlClass
			]
		]
	}
}
