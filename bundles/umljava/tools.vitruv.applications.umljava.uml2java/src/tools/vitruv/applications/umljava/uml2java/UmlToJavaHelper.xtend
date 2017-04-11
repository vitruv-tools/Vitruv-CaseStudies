package tools.vitruv.applications.umljava.uml2java

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.EnumerationLiteral
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.members.Field
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory

import static tools.vitruv.applications.umljava.util.JavaUtil.*
import org.emftext.language.java.members.MembersFactory

class UmlToJavaHelper {
    
	private static val log = Logger.getLogger(UmlToJavaHelper);
	private new() {}

    def static void setJavaVisibility(AnnotableAndModifiable jModifiable, VisibilityKind vis) {
        removeJavaVisibilityModifiers(jModifiable);
        val v = getJavaVisibility(vis);
        if (v != null) {
            jModifiable.addModifier(v);
        }
        
    }
    
    /**
     * Liefert zu einem UML-VisibilityKind den entsprechenden Java-Sichtbarkeitsmodifier.
     * Default-Rückgabe: null -> Package-Private
     */
    def static Modifier getJavaVisibility(VisibilityKind v) {
        switch v {
                case VisibilityKind.PRIVATE_LITERAL : return ModifiersFactory.eINSTANCE.createPrivate
                case VisibilityKind.PROTECTED_LITERAL : return ModifiersFactory.eINSTANCE.createProtected
                case VisibilityKind.PUBLIC_LITERAL : return ModifiersFactory.eINSTANCE.createPublic
                case VisibilityKind.PACKAGE_LITERAL : return null
                default : throw new IllegalArgumentException("Invalid VisibilityKind: " + v)
            }
    }

    /**
 	 * Creates a Java-NamespaceReference if cType != null
     * Creates a PrimitiveType if dType != null && cType == null
     * Creates Void if both Types are null
     * 
     * @param dType uml-DataType
     * @param cType java-Class
     */
	def static TypeReference createTypeReference(Type dType, ConcreteClassifier cType) {
		if (dType == null && cType == null) {
		    return TypesFactory.eINSTANCE.createVoid();
		} else if (cType != null) {
	        return createNamespaceReferenceFromClassifier(cType);
	    } else if (dType instanceof PrimitiveType) {
	        return mapToJavaPrimitiveType(dType);
	    } else {//TODO dType ist DataType
	        val dClass = ClassifiersFactory.eINSTANCE.createClass;
	        dClass.name = dType.name;
	        return createNamespaceReferenceFromClassifier(dClass)
		}
	} 
	
	def static org.emftext.language.java.types.PrimitiveType mapToJavaPrimitiveType(PrimitiveType pType) {
	    switch pType.name {
	        case BOOLEAN : return TypesFactory.eINSTANCE.createBoolean
	        case BYTE : return TypesFactory.eINSTANCE.createByte
	        case CHAR : return TypesFactory.eINSTANCE.createChar
	        case DOUBLE : return TypesFactory.eINSTANCE.createDouble
	        case FLOAT : return TypesFactory.eINSTANCE.createFloat
	        case INT : return TypesFactory.eINSTANCE.createInt
	        case LONG : return TypesFactory.eINSTANCE.createLong
	        case SHORT : return TypesFactory.eINSTANCE.createShort
	        default: throw new IllegalArgumentException("Invalid PrimitiveType: " + pType.name)
	    }
	}
	
	def static void handleMultiplicityAndAddToClass(Property umlAttribute, Field javaAttribute, Class jClass) {
		if (umlAttribute.lowerBound == 1 && umlAttribute.upperBound == 1) {
			//javaAttribute final setzen oder im Konstruktor initialisieren + getter und setter
		} else if (umlAttribute.lowerBound == 0) {
			if (umlAttribute.upperBound == 1) {
				jClass.members += javaAttribute
			} else if (umlAttribute.upperBound == LiteralUnlimitedNatural.UNLIMITED) {
                //Collection
			} else if (umlAttribute.upperBound > 1) {
				//Auch Collection
			}
		}
		jClass.members += javaAttribute
	}
	
	def static createJavaEnumConstant(EnumerationLiteral enumLiteral) {
		val enumConstant = MembersFactory.eINSTANCE.createEnumConstant
		enumConstant.name = enumLiteral.name
		return enumConstant
	}
}