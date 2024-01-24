package tools.vitruv.applications.util.temporary.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.mdsd.jamopp.model.java.types.Boolean
import tools.mdsd.jamopp.model.java.types.Byte
import tools.mdsd.jamopp.model.java.types.Char
import tools.mdsd.jamopp.model.java.types.Double
import tools.mdsd.jamopp.model.java.types.Float
import tools.mdsd.jamopp.model.java.types.Int
import tools.mdsd.jamopp.model.java.types.Long
import tools.mdsd.jamopp.model.java.types.PrimitiveType
import tools.mdsd.jamopp.model.java.types.Short
import tools.mdsd.jamopp.model.java.types.TypesFactory
import tools.mdsd.jamopp.model.java.types.Void

import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.*

/**
 * Util class with for Java-PrimitiveTypes and Java-String.
 * 
 * @author Fei
 */
@Utility
class JavaStandardType {
    public static val BOOLEAN = "boolean"
    public static val BYTE = "byte"
    public static val CHAR = "char"
    public static val DOUBLE = "double"
    public static val FLOAT = "float"
    public static val INT = "int"
    public static val LONG = "long"
    public static val SHORT = "short"
    public static val STRING = "String"
    public static val VOID = "void"

    def static dispatch String getTypeKeyword(Boolean bool) {
        return BOOLEAN
    }

    def static dispatch String getTypeKeyword(Byte by) {
        return BYTE
    }

    def static dispatch String getTypeKeyword(Char chr) {
        return CHAR
    }

    def static dispatch String getTypeKeyword(Double dbl) {
        return DOUBLE
    }

    def static dispatch String getTypeKeyword(Float flt) {
        return FLOAT
    }

    def static dispatch String getTypeKeyword(Int in) {
        return INT
    }

    def static dispatch String getTypeKeyword(Long lng) {
        return LONG
    }

    def static dispatch String getTypeKeyword(Short shrt) {
        return SHORT
    }

    def static dispatch String getTypeKeyword(Void vd) {
        return VOID
    }

    def static dispatch String getTypeKeyword(java.lang.Void vd) {
        throw new IllegalArgumentException("Unknown java primitive type: " + vd)
    }

    def static dispatch String getTypeKeyword(PrimitiveType ptype) {
        throw new IllegalArgumentException("Unknown java primitive type: " + ptype)
    }

    def static createJavaPrimitiveType(String standardTypeName) {
        switch (standardTypeName) {
            case BOOLEAN: return TypesFactory.eINSTANCE.createBoolean
            case BYTE: return TypesFactory.eINSTANCE.createByte
            case CHAR: return TypesFactory.eINSTANCE.createChar
            case DOUBLE: return TypesFactory.eINSTANCE.createDouble
            case FLOAT: return TypesFactory.eINSTANCE.createFloat
            case INT: return TypesFactory.eINSTANCE.createInt
            case LONG: return TypesFactory.eINSTANCE.createLong
            case SHORT: return TypesFactory.eINSTANCE.createShort
            case VOID: return TypesFactory.eINSTANCE.createVoid
            case STRING: return createNamespaceClassifierReference(createJavaClass(STRING, JavaVisibility.PUBLIC, false, false))
            default: throw new IllegalArgumentException("Unknown standard primitive type name: " + standardTypeName)
        }
    }

}
