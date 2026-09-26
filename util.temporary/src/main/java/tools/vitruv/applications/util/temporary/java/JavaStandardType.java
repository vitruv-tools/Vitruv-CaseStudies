package tools.vitruv.applications.util.temporary.java;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
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
 * A utility class for handling Java's standard types (primitives and String)
 * within an EMF-based Java model context.
 * <p>
 * This class provides constants for standard type keywords and factory methods
 * to create corresponding EMF model elements. It is a non-instantiable utility class.
 */
@Utility
public final class JavaStandardType {

    /** The keyword for the Java {@code boolean} primitive type. */
    public static final String BOOLEAN = "boolean";
    /** The keyword for the Java {@code byte} primitive type. */
    public static final String BYTE = "byte";
    /** The keyword for the Java {@code char} primitive type. */
    public static final String CHAR = "char";
    /** The keyword for the Java {@code double} primitive type. */
    public static final String DOUBLE = "double";
    /** The keyword for the Java {@code float} primitive type. */
    public static final String FLOAT = "float";
    /** The keyword for the Java {@code int} primitive type. */
    public static final String INT = "int";
    /** The keyword for the Java {@code long} primitive type. */
    public static final String LONG = "long";
    /** The keyword for the Java {@code short} primitive type. */
    public static final String SHORT = "short";
    /** The name of the standard Java {@code String} class. */
    public static final String STRING = "String";
    /** The keyword for the Java {@code void} type. */
    public static final String VOID = "void";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JavaStandardType() {
        // Utility class should not be instantiated.
    }

    /**
     * Resolves the keyword for a {@link Boolean} type.
     * @param bool The boolean type object.
     * @return The string "boolean".
     */
    private static String resolveTypeKeyword(Boolean bool) {
        return BOOLEAN;
    }

    /**
     * Resolves the keyword for a {@link Byte} type.
     * @param by The byte type object.
     * @return The string "byte".
     */
    private static String resolveTypeKeyword(Byte by) {
        return BYTE;
    }

    /**
     * Resolves the keyword for a {@link Char} type.
     * @param chr The char type object.
     * @return The string "char".
     */
    private static String resolveTypeKeyword(Char chr) {
        return CHAR;
    }

    /**
     * Resolves the keyword for a {@link Double} type.
     * @param dbl The double type object.
     * @return The string "double".
     */
    private static String resolveTypeKeyword(Double dbl) {
        return DOUBLE;
    }

    /**
     * Resolves the keyword for a {@link Float} type.
     * @param flt The float type object.
     * @return The string "float".
     */
    private static String resolveTypeKeyword(Float flt) {
        return FLOAT;
    }

    /**
     * Resolves the keyword for a {@link Int} type.
     * @param in The int type object.
     * @return The string "int".
     */
    private static String resolveTypeKeyword(Int in) {
        return INT;
    }

    /**
     * Resolves the keyword for a {@link Long} type.
     * @param lng The long type object.
     * @return The string "long".
     */
    private static String resolveTypeKeyword(Long lng) {
        return LONG;
    }

    /**
     * Resolves the keyword for a {@link Short} type.
     * @param shrt The short type object.
     * @return The string "short".
     */
    private static String resolveTypeKeyword(Short shrt) {
        return SHORT;
    }

    /**
     * Resolves the keyword for a {@link Void} type.
     * @param vd The void type object.
     * @return The string "void".
     */
    private static String resolveTypeKeyword(Void vd) {
        return VOID;
    }

    private static String resolveTypeKeyword(java.lang.Void vd) {
        throw new IllegalArgumentException("Unknown java primitive type: " + vd);
    }

    private static String resolveTypeKeyword(PrimitiveType ptype) {
        throw new IllegalArgumentException("Unknown java primitive type: " + ptype);
    }

    /**
     * Creates an EMF {@link TypeReference} for a given standard Java type name.
     * <p>
     * This factory method supports all primitive types (e.g., "int", "boolean")
     * and the "String" type.
     *
     * @param standardTypeName The name of the standard type (e.g., "int", "String"). Must not be null.
     * @return The corresponding EMF {@link TypeReference} object.
     * @throws IllegalArgumentException if {@code standardTypeName} is null or not a recognized
     * standard primitive or String type name.
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
            case STRING:
                // Note: This relies on other utility classes which are not provided.
                // The logic remains the same.
                return JavaModificationUtil.createNamespaceClassifierReference(
                        JavaContainerAndClassifierUtil.createJavaClass("String", JavaVisibility.PUBLIC, false, false));
            default:
                throw new IllegalArgumentException("Unknown standard primitive type name: " + standardTypeName);
        }
    }

    /**
     * Returns the Java keyword for a given EMF {@link PrimitiveType} model element.
     * <p>
     * This method dispatches based on the concrete type of the provided {@code primitiveType}
     * (e.g., {@link Int}, {@link Boolean}) to return the correct keyword.
     *
     * @param primitiveType The EMF object representing a Java primitive type.
     * @return The corresponding Java language keyword (e.g., "int", "boolean").
     * @throws IllegalArgumentException if {@code primitiveType} is null or an unknown subtype of {@link PrimitiveType}.
     */
    @XbaseGenerated
    public static String getTypeKeyword(PrimitiveType primitiveType) {
        if (primitiveType instanceof Boolean) {
            return resolveTypeKeyword((Boolean) primitiveType);
        } else if (primitiveType instanceof Byte) {
            return resolveTypeKeyword((Byte) primitiveType);
        } else if (primitiveType instanceof Char) {
            return resolveTypeKeyword((Char) primitiveType);
        } else if (primitiveType instanceof Double) {
            return resolveTypeKeyword((Double) primitiveType);
        } else if (primitiveType instanceof Float) {
            return resolveTypeKeyword((Float) primitiveType);
        } else if (primitiveType instanceof Int) {
            return resolveTypeKeyword((Int) primitiveType);
        } else if (primitiveType instanceof Long) {
            return resolveTypeKeyword((Long) primitiveType);
        } else if (primitiveType instanceof Short) {
            return resolveTypeKeyword((Short) primitiveType);
        } else if (primitiveType instanceof Void) {
            return resolveTypeKeyword((Void) primitiveType);
        } else {
            return primitiveType != null ? resolveTypeKeyword(primitiveType) : resolveTypeKeyword((java.lang.Void) null);
        }
    }
}