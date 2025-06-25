package tools.vitruv.applications.util.temporary.java;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import org.emftext.language.java.types.Boolean;
import org.emftext.language.java.types.Byte;
import org.emftext.language.java.types.Char;
import org.emftext.language.java.types.Double;
import org.emftext.language.java.types.Float;
import org.emftext.language.java.types.Int;
import org.emftext.language.java.types.Long;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.Short;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;
import org.emftext.language.java.types.Void;

/**
 * A utility class providing constants and helper methods for handling standard Java
 * types within the JaMoPP (Java Model Parser and Printer) EMF model.
 *
 * This class cannot be instantiated.
 */
@Utility
public final class JavaStandardType {

    // --- Constants for Java Type Keywords ---

    /** The keyword for the boolean primitive type: "boolean". */
    public static final String BOOLEAN = "boolean";
    /** The keyword for the byte primitive type: "byte". */
    public static final String BYTE = "byte";
    /** The keyword for the char primitive type: "char". */
    public static final String CHAR = "char";
    /** The keyword for the double primitive type: "double". */
    public static final String DOUBLE = "double";
    /** The keyword for the float primitive type: "float". */
    public static final String FLOAT = "float";
    /** The keyword for the int primitive type: "int". */
    public static final String INT = "int";
    /** The keyword for the long primitive type: "long". */
    public static final String LONG = "long";
    /** The keyword for the short primitive type: "short". */
    public static final String SHORT = "short";
    /** The keyword for the String class: "String". */
    public static final String STRING = "String";
    /** The keyword for the void type: "void". */
    public static final String VOID = "void";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JavaStandardType() {
        // This class is not meant to be instantiated.
    }

    /**
     * Creates a JaMoPP EMF model representation of a standard Java type from its string name.
     * This supports all primitive types and the {@code String} class.
     *
     * @param standardTypeName The name of the type (e.g., "int", "boolean", "String").
     * @return A {@link TypeReference} representing the corresponding JaMoPP type model object.
     * @throws IllegalArgumentException if {@code standardTypeName} is null or not a recognized
     *         standard primitive type name or "String".
     */
    public static TypeReference createJavaPrimitiveType(String standardTypeName) {
        if (standardTypeName == null) {
            throw new IllegalArgumentException("Unknown standard primitive type name: null");
        }

        switch (standardTypeName) {
            case BOOLEAN:
                return TypesFactory.eINSTANCE.createBoolean();
            case BYTE:
                return TypesFactory.eINSTANCE.createByte();
            case CHAR:
                return TypesFactory.eINSTANCE.createChar();
            case DOUBLE:
                return TypesFactory.eINSTANCE.createDouble();
            case FLOAT:
                return TypesFactory.eINSTANCE.createFloat();
            case INT:
                return TypesFactory.eINSTANCE.createInt();
            case LONG:
                return TypesFactory.eINSTANCE.createLong();
            case SHORT:
                return TypesFactory.eINSTANCE.createShort();
            case VOID:
                return TypesFactory.eINSTANCE.createVoid();
            // String is a class, not a primitive, so it requires special handling.
            case STRING:
                return JavaModificationUtil.createNamespaceClassifierReference(
                        JavaContainerAndClassifierUtil.createJavaClass("String", JavaVisibility.PUBLIC, false, false)
                );
            default:
                throw new IllegalArgumentException("Unknown standard primitive type name: " + standardTypeName);
        }
    }

    /**
     * Returns the Java language keyword for a given JaMoPP primitive type object.
     *
     * @param primitiveType The JaMoPP {@link PrimitiveType} object (e.g., an instance of {@link Int}, {@link Boolean}).
     * @return The corresponding Java keyword as a {@link String} (e.g., "int", "boolean").
     * @throws IllegalArgumentException if the provided {@code primitiveType} is null or an unknown subclass of {@link PrimitiveType}.
     */
    public static String getTypeKeyword(PrimitiveType primitiveType) {
        if (primitiveType == null) {
            throw new IllegalArgumentException("PrimitiveType cannot be null.");
        }

        if (primitiveType instanceof Boolean) {
            return BOOLEAN;
        } else if (primitiveType instanceof Byte) {
            return BYTE;
        } else if (primitiveType instanceof Char) {
            return CHAR;
        } else if (primitiveType instanceof Double) {
            return DOUBLE;
        } else if (primitiveType instanceof Float) {
            return FLOAT;
        } else if (primitiveType instanceof Int) {
            return INT;
        } else if (primitiveType instanceof Long) {
            return LONG;
        } else if (primitiveType instanceof Short) {
            return SHORT;
        } else if (primitiveType instanceof Void) {
            return VOID;
        } else {
            // This case handles any unknown or unexpected subclasses of PrimitiveType.
            throw new IllegalArgumentException("Unknown java primitive type: " + primitiveType.getClass().getName());
        }
    }
}