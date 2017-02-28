package tools.vitruv.applications.umljava.uml2java

import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.types.NamespaceClassifierReference
import org.eclipse.uml2.uml.Type
import org.emftext.language.java.types.TypesFactory
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.DataType
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ConcreteClassifier
import tools.vitruv.framework.util.datatypes.ClaimableMap
import org.emftext.language.java.modifiers.Modifier
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.types.ClassifierReference
import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator

class UmlToJavaHelper {
	private static val log = Logger.getLogger(UmlToJavaHelper);
	private new() {
	    
	}


    def static Modifier getJavaVisibility(VisibilityKind v) {
        switch v.value {
                case VisibilityKind.PRIVATE : return ModifiersFactory.eINSTANCE.createPrivate
                case VisibilityKind.PROTECTED : return ModifiersFactory.eINSTANCE.createProtected
                default : return ModifiersFactory.eINSTANCE.createPublic
            }
    }
    
    def static removeJavaVisibilityModifiers(AnnotableAndModifiable a) {
        a.removeModifier(typeof(Private))
        a.removeModifier(typeof(Protected))
        a.removeModifier(typeof(Public))
    }
    /**
     * 
     * @param dType uml-DataType
     * @param cType java-Class
     */
	def static TypeReference createTypeReference(Type dType, Class cType) {
	    PropertyConfigurator.configure("log4j.properties")
		var TypeReference typeRef;
		if (dType == null && cType == null) {
		    log.info(cType == null)
		    log.info("   VOOOOOOOIDDDD")
		    return TypesFactory.eINSTANCE.createVoid();
		} else if (dType instanceof PrimitiveType) {
		    
		} else if (cType != null) {
		    typeRef = createClassifierReference(cType);
		} else {
		    throw new IllegalArgumentException("Invalid Type")
		}
		return typeRef;
	} 
	
	/**
	 * NameSpaceClassifierReference hat eine Liste von ClassifierReferences
	 * @param concreteClassifiers = Class, Interface, Enum or Annotation
	 */
	def static ClassifierReference createClassifierReference(ConcreteClassifier concreteClassifier) {
        //val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
        val classifierRef = TypesFactory.eINSTANCE.createClassifierReference
        classifierRef.target = EcoreUtil.copy(concreteClassifier)
        //namespaceClassifierReference.classifierReferences.add(classifierRef)

        return classifierRef
    }
    
    
}