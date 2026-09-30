package tools.vitruv.applications.cbs.commonalities.tests.util.uml;

import java.util.List;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

public final class UmlModelHelper {

	private UmlModelHelper() {
	}

	public static <P extends Package> P withElements(P umlPackage, PackageableElement... umlPackageableElements) {
		umlPackage.getPackagedElements().addAll(List.of(umlPackageableElements));
		return umlPackage;
	}
}
