package tools.vitruv.applications.umljava.util

import java.util.Iterator
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.Type
import org.emftext.language.java.generics.GenericsFactory
import org.emftext.language.java.containers.JavaRoot
import org.eclipse.emf.ecore.util.EcoreUtil

class JavaUtil {
    public static val BOOLEAN = "boolean";
    public static val BYTE = "byte";
    public static val CHAR = "char";
    public static val DOUBLE = "double";
    public static val FLOAT = "float";
    public static val INT = "int";
    public static val LONG = "long";
    public static val SHORT = "short";
    
    enum JavaVisibility {
        PUBLIC, PRIVATE, PROTECTED, PACKAGE
    }
    
    private new() {}
    
    
    /**
     * return null bei package-private
     */
    def static getJavaVisibilityModifierFromEnum(JavaVisibility vis) {
        switch vis {
            case JavaVisibility.PUBLIC: return ModifiersFactory.eINSTANCE.createPublic
            case JavaVisibility.PRIVATE: return ModifiersFactory.eINSTANCE.createPrivate
            case JavaVisibility.PROTECTED: return ModifiersFactory.eINSTANCE.createProtected
            case JavaVisibility.PACKAGE: return null
            default: throw new IllegalArgumentException("Invalid Visibility: " + vis)
        }
    }
    
    /**
     * The created Class is not contained in a compilationunit.
     */
    def static createJavaClass(String cName, JavaVisibility vis, boolean abstr, boolean fin) {
        val cls = ClassifiersFactory.eINSTANCE.createClass;
        if (cName == null) {
            throw new IllegalArgumentException("Classname is null");
        }
        cls.name = cName;
        if (vis != null && vis != JavaVisibility.PACKAGE) {
            cls.addModifier(getJavaVisibilityModifierFromEnum(vis))
        }
        if (abstr) {
            cls.addModifier(ModifiersFactory.eINSTANCE.createAbstract)
        }
        if (fin) {
            cls.addModifier(ModifiersFactory.eINSTANCE.createFinal)
        }
        return cls;
    }
    
    /**
     * The created Interface is not contained in a compilationunit.
     */
    def static createJavaInterface(String name, EList<Interface> superInterfaces) {
        val jI = ClassifiersFactory.eINSTANCE.createInterface;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create JavalInterface - name is null");
        }
        jI.name = name;
        jI.makePublic;
        if (!superInterfaces.nullOrEmpty) {
            jI.extends.addAll(createNamespaceReferenceFromList(superInterfaces));
        }
        return jI;
    }
    
    /**
     * .java Endung wird in der Methode eingefügt
     */
    def static createEmptyCompilationUnit(String name) {
        val cu = ContainersFactory.eINSTANCE.createCompilationUnit
        cu.name = name + ".java";
        return cu
    }
    
    /**
     * @return public Operation mit Namen; kein Return, Params oder Modifier
     */
    def static createSimpleJavaOperation(String name) {
        return createJavaClassMethod(name, null, JavaVisibility.PUBLIC, false, false, null)
    }
    
    /**
     * return und params kann null sein.
     */
    def static createJavaClassMethod(String name, TypeReference returnType, JavaVisibility vis, boolean abstr, boolean stat, EList<Parameter> params) {
        val jMeth = MembersFactory.eINSTANCE.createClassMethod;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create Java Method - name is null");
        }
        jMeth.name = name;
        if (returnType != null) {
            jMeth.typeReference = returnType;
        }
        if (vis != null && vis != JavaVisibility.PACKAGE) {
            jMeth.addModifier(getJavaVisibilityModifierFromEnum(vis));
        }
        if (abstr) {
            jMeth.addModifier(ModifiersFactory.eINSTANCE.createAbstract)
        }
        if (stat) {
            jMeth.addModifier(ModifiersFactory.eINSTANCE.createStatic)
        }
        if (!params.nullOrEmpty) {
            jMeth.parameters.addAll(params);
        }
        return jMeth;
    }
    
    
    
    /**
     * Return an InterfaceMethod (public, not static, not abstract)
     */
    def static createJavaInterfaceMethod(String name, TypeReference returnType, EList<Parameter> params) {
        val jMeth = MembersFactory.eINSTANCE.createInterfaceMethod;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create Java Method - name is null");
        }
        jMeth.name = name;
        if (returnType != null) {
            jMeth.typeReference = returnType;
        }
        jMeth.makePublic;
        if (!params.nullOrEmpty) {
            jMeth.parameters.addAll(params);
        }
        return jMeth;
    }
    
    def static  createJavaAttribute(String name, TypeReference type, JavaVisibility vis, boolean fin, boolean stat) {
        val attr = MembersFactory.eINSTANCE.createField;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create Java Field - name is null");
        }
        attr.name = name;
        if (vis != null && vis != JavaVisibility.PACKAGE) {
            attr.addModifier(getJavaVisibilityModifierFromEnum(vis))
        }
        if (fin) {
            attr.addModifier(ModifiersFactory.eINSTANCE.createFinal)
        }
        if (stat) {
            attr.addModifier(ModifiersFactory.eINSTANCE.createStatic)
        }
        if (type != null) {
            attr.typeReference = type;
        }
        return attr;
    }
    
    def static createJavaParameter(String name, TypeReference type) {
        val param = ParametersFactory.eINSTANCE.createOrdinaryParameter;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create JavaParameter - name is null");
        }
        param.name = name
        if (type != null) {
            param.typeReference = type;
        }
        return param;
    }
    
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
    def static setJavaModifier(AnnotableAndModifiable jModifiable, Modifier mod, boolean add) {
        if (add) {
            if (!jModifiable.hasModifier(mod.class)) {//TODO Logeintrag + Schauen, ob es einen Fehler gibt
                jModifiable.addModifier(mod)  
            } else {
            	System.out.println("The Java AnnotableAndModifiable " + jModifiable.class + " already has the modifier " + mod.class)
            	//TODO Durch Log ersetzen
            }
        } else {
            jModifiable.removeModifier(mod.class)
        }
    }
    
    /**
     * Adds mod to jModifiable if mod is not null.
     */
    def static addModifierIfNotNull(AnnotableAndModifiable jModifiable, Modifier mod) {
    	if (mod !== null) {
    		setJavaModifier(jModifiable, mod, true)
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
    
    /**
     * Converts a list with ConcreteClassifiers to a list with corresponding NamespaceClassifierRefrences.
     */
    def static EList<NamespaceClassifierReference> createNamespaceReferenceFromList(EList<? extends ConcreteClassifier> list) {
        val typeReferences = new BasicEList<NamespaceClassifierReference>
        for (ConcreteClassifier i : list) {
            typeReferences += createNamespaceReferenceFromClassifier(i)
        }
        return typeReferences
    }
    
    def static TypeReference createCollectiontypeReference(String collectionName, org.emftext.language.java.classifiers.Class innerTypeClass) {
    	val collectionClass = createJavaClass(collectionName, JavaVisibility.PUBLIC, false, false)
    	val innerTypeReference = createNamespaceReferenceFromClassifier(innerTypeClass)
    	val qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument();
		qualifiedTypeArgument.typeReference = innerTypeReference;
		val collectionClassNamespaceReference = createNamespaceReferenceFromClassifier(collectionClass)
		collectionClassNamespaceReference.classifierReferences.get(0).typeArguments += qualifiedTypeArgument;
		return collectionClassNamespaceReference
    }
    
}