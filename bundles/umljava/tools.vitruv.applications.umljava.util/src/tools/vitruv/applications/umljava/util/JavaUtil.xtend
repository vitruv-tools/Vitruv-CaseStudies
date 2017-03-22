package tools.vitruv.applications.umljava.util

import java.util.Iterator
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory

class JavaUtil {
    public static val BOOLEAN = "boolean";
    public static val BYTE = "byte";
    public static val CHAR = "char";
    public static val DOUBLE = "double";
    public static val FLOAT = "float";
    public static val INT = "int";
    public static val LONG = "long";
    public static val SHORT = "short";
    
    private new() {}
    
    /**
     * Gibt den ersten Java-Classifier zurück, der zur Java-TypeReference type gehört.
     * Ist type kein NameSpaceClassifierreference, kommt null zurück.
     */
    def static ConcreteClassifier getClassifierfromTypeRef(TypeReference type) {
        if (type instanceof NamespaceClassifierReference) {
            return (type as NamespaceClassifierReference).classifierReferences.head.target as ConcreteClassifier;
        }
        return null;
    }
    
    /**
     * Verpackt ein Java-ConcreteClassifier in ein NamespaceClassifierReference
     */
    def static NamespaceClassifierReference createNamespaceReferenceFromClassifier(ConcreteClassifier concreteClassifier) {
        val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
        val classifierRef = TypesFactory.eINSTANCE.createClassifierReference
        classifierRef.target = concreteClassifier
        namespaceClassifierReference.classifierReferences.add(classifierRef)
        return namespaceClassifierReference
    }
    
    /**
     * @param add true -> hinzufügen, sonst entfernen
     */
    def static setJavaModifier(AnnotableAndModifiable jMem, Modifier mod, boolean add) {
        if (add) {
                if (!jMem.hasModifier(mod.class)) {
                    jMem.addModifier(mod)
                }
            } else {
                jMem.removeModifier(mod.class)
                
            }
                
     }
     
     /**
     * Entfernt alle Classifier von Iterator anhand des Namens.
     * 
     * @param iter Iterator über eine Liste von TypeReferences
     * @param classif Klasse oder Interface, das entfernt werden soll
     */
    def static removeClassifierFromIterator(Iterator<TypeReference> iter, ConcreteClassifier classif) {
        while (iter.hasNext) {
                val type = (iter.next as NamespaceClassifierReference).classifierReferences.head.target
                if (classif.name.equals(type.name)) {
                    iter.remove;
                }
        }
    }
    
    /**
     * Entfernt alle Sichtbarkeiten-Modifier eines AnnotableAndModifiables.
     * (Klassen, Interfaces, Methoden, Attribute)
     */
    def static removeJavaVisibilityModifiers(AnnotableAndModifiable a) {
        a.removeModifier(typeof(Private))
        a.removeModifier(typeof(Protected))
        a.removeModifier(typeof(Public))
    }
    
    /**
     * @param a         Objekt, dessen Modifer entfernt werden soll
     * @param modifier  Klasse des Modifiers, das von Objekt a entfernt werden soll 
     */
    def static <T extends Modifier> removeJavaModifier(AnnotableAndModifiable a, Class<T> modifier ) {
        a.removeModifier(modifier)
    }
            
}