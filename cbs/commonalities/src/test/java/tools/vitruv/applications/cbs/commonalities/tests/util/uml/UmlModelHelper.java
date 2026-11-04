package tools.vitruv.applications.cbs.commonalities.tests.util.uml;

import java.util.Collections;

import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

public final class UmlModelHelper {

    private UmlModelHelper() {
    }

    public static <P extends Package> P withElements(P umlPackage, PackageableElement... umlPackageableElements) {
        Collections.addAll(umlPackage.getPackagedElements(), umlPackageableElements);
        return umlPackage;
    }
}
