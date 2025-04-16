package tools.vitruv.applications.testutility.uml;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil;
import java.util.Objects;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.applications.util.temporary.uml.UmlTypeUtil;
import tools.vitruv.framework.views.View;

public final class UmlQueryUtil {

    public static PrimitiveType loadUmlPrimitiveType(final String name) {
        ResourceSetImpl resourceSet = new ResourceSetImpl();
        return IterableUtil.claimOne(
                IterableExtensions.filter(
                        UmlTypeUtil.getUmlPrimitiveTypes(resourceSet),
                        primitiveType -> Objects.equals(primitiveType.getName(), name)
                )
        );
    }

    public static Model claimUmlModel(final View view, final String modelName) {
        return IterableUtil.claimOne(
                IterableExtensions.filter(
                        view.getRootObjects(Model.class),
                        model -> Objects.equals(model.getName(), modelName)
                )
        );
    }

    public static <T extends PackageableElement> T claimPackageableElement(
            final org.eclipse.uml2.uml.Package containingPackage,
            final Class<T> packageableElementType,
            final String packageableElementName) {
        return IterableUtil.claimOne(
                IterableExtensions.filter(
                        Iterables.filter(containingPackage.getPackagedElements(), packageableElementType),
                        element -> Objects.equals(element.getName(), packageableElementName)
                )
        );
    }

    public static org.eclipse.uml2.uml.Package claimPackage(
            final org.eclipse.uml2.uml.Package containingPackage,
            final String packageName) {
        return claimPackageableElement(containingPackage, org.eclipse.uml2.uml.Package.class, packageName);
    }

    public static org.eclipse.uml2.uml.Class claimClass(
            final org.eclipse.uml2.uml.Package containingPackage,
            final String className) {
        return claimPackageableElement(containingPackage, org.eclipse.uml2.uml.Class.class, className);
    }

    public static DataType claimDataType(
            final org.eclipse.uml2.uml.Package containingPackage,
            final String dataTypeName) {
        return claimPackageableElement(containingPackage, DataType.class, dataTypeName);
    }

    public static Enumeration claimEnum(
            final org.eclipse.uml2.uml.Package containingPackage,
            final String enumName) {
        return claimPackageableElement(containingPackage, Enumeration.class, enumName);
    }

    public static Interface claimInterface(
            final org.eclipse.uml2.uml.Package containingPackage,
            final String interfaceName) {
        return claimPackageableElement(containingPackage, Interface.class, interfaceName);
    }

    public static Operation claimOperation(
            final Classifier containingClassifier,
            final String operationName) {
        return IterableUtil.claimOne(
                IterableExtensions.filter(
                        containingClassifier.getOperations(),
                        operation -> Objects.equals(operation.getName(), operationName)
                )
        );
    }

    public static Parameter claimParameter(
            final Operation containingOperation,
            final String parameterName) {
        return IterableUtil.claimOne(
                IterableExtensions.filter(
                        containingOperation.getOwnedParameters(),
                        parameter -> Objects.equals(parameter.getName(), parameterName)
                )
        );
    }

    public static Property claimAttribute(
            final Classifier containingClassifier,
            final String attributeName) {
        return IterableUtil.claimOne(
                IterableExtensions.filter(
                        containingClassifier.getAttributes(),
                        attribute -> Objects.equals(attribute.getName(), attributeName)
                )
        );
    }

    private UmlQueryUtil() {
        // Utility class – no instances allowed
    }
}
