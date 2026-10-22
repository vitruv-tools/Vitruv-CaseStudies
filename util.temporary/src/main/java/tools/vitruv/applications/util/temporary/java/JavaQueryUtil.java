package tools.vitruv.applications.util.temporary.java;

import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;

/**
 * Utility class for resolving the names of JaMoPP type references.
 */
@SuppressWarnings("all")
public class JavaQueryUtil {

    protected static String _getNameFromJaMoPPType(ClassifierReference reference) {
        return reference.getTarget().getName();
    }

    protected static String _getNameFromJaMoPPType(NamespaceClassifierReference reference) {
        ClassifierReference classRef = reference.getPureClassifierReference();
        return classRef.getTarget().getName();
    }

    protected static String _getNameFromJaMoPPType(org.emftext.language.java.types.Boolean reference) {
        return "boolean";
    }

    protected static String _getNameFromJaMoPPType(org.emftext.language.java.types.Byte reference) {
        return "byte";
    }

    protected static String _getNameFromJaMoPPType(org.emftext.language.java.types.Char reference) {
        return "char";
    }

    protected static String _getNameFromJaMoPPType(org.emftext.language.java.types.Double reference) {
        return "double";
    }

    protected static String _getNameFromJaMoPPType(org.emftext.language.java.types.Float reference) {
        return "float";
    }

    protected static String _getNameFromJaMoPPType(org.emftext.language.java.types.Int reference) {
        return "int";
    }

    protected static String _getNameFromJaMoPPType(org.emftext.language.java.types.Long reference) {
        return "long";
    }

    protected static String _getNameFromJaMoPPType(org.emftext.language.java.types.Short reference) {
        return "short";
    }

    protected static String _getNameFromJaMoPPType(org.emftext.language.java.types.Void reference) {
        return "void";
    }

    @XbaseGenerated
    public static String getNameFromJaMoPPType(TypeReference reference) {
        if (reference instanceof org.emftext.language.java.types.Boolean) {
            return _getNameFromJaMoPPType((org.emftext.language.java.types.Boolean) reference);
        } else if (reference instanceof org.emftext.language.java.types.Byte) {
            return _getNameFromJaMoPPType((org.emftext.language.java.types.Byte) reference);
        } else if (reference instanceof org.emftext.language.java.types.Char) {
            return _getNameFromJaMoPPType((org.emftext.language.java.types.Char) reference);
        } else if (reference instanceof org.emftext.language.java.types.Double) {
            return _getNameFromJaMoPPType((org.emftext.language.java.types.Double) reference);
        } else if (reference instanceof org.emftext.language.java.types.Float) {
            return _getNameFromJaMoPPType((org.emftext.language.java.types.Float) reference);
        } else if (reference instanceof org.emftext.language.java.types.Int) {
            return _getNameFromJaMoPPType((org.emftext.language.java.types.Int) reference);
        } else if (reference instanceof org.emftext.language.java.types.Long) {
            return _getNameFromJaMoPPType((org.emftext.language.java.types.Long) reference);
        } else if (reference instanceof org.emftext.language.java.types.Short) {
            return _getNameFromJaMoPPType((org.emftext.language.java.types.Short) reference);
        } else if (reference instanceof org.emftext.language.java.types.Void) {
            return _getNameFromJaMoPPType((org.emftext.language.java.types.Void) reference);
        } else if (reference instanceof ClassifierReference) {
            return _getNameFromJaMoPPType((ClassifierReference) reference);
        } else if (reference instanceof NamespaceClassifierReference) {
            return _getNameFromJaMoPPType((NamespaceClassifierReference) reference);
        } else {
            throw new IllegalArgumentException("Unhandled parameter type: " + reference.getClass().getName());
        }
    }
}
