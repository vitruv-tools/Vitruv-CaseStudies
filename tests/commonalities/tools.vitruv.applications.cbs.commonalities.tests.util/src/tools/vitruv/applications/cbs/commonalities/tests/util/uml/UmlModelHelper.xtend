package tools.vitruv.applications.cbs.commonalities.tests.util.uml

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PackageableElement

@Utility
class UmlModelHelper {

	static def <P extends Package> withElements(P umlPackage, PackageableElement... umlPackageableElements) {
		return umlPackage => [
			packagedElements += umlPackageableElements
		]
	}
}
