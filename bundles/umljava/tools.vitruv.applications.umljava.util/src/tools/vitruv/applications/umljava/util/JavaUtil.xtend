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
import java.util.ArrayList
import java.util.HashSet
import java.util.LinkedList
import java.util.Set
import java.util.List
import org.emftext.language.java.imports.ClassifierImport
import tools.vitruv.domains.java.util.jamoppparser.JamoppParser
import java.io.ByteArrayInputStream
import org.emftext.language.java.containers.CompilationUnit
import org.eclipse.emf.common.util.URI
import org.emftext.language.java.members.Constructor
import org.apache.log4j.Logger
import org.emftext.language.java.parameters.Parametrizable
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.members.Method
import org.emftext.language.java.members.EnumConstant

class JavaUtil {
	private static val logger = Logger.getLogger(JavaUtil)
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
    
    def static void addJavaVisibilityModifier(AnnotableAndModifiable modifiable, JavaVisibility visibility) {
    	val visibilityModifier = getJavaVisibilityModifierFromEnum(visibility)
    	addModifierIfNotNull(modifiable, visibilityModifier)
    }
        
    
    /**
     * Returns the Java Modifier corresponding to the JavaVisibility-Enum Constant.
     * Returns null if visibility is JavaVisibility.PACKAGE.
     */
    def static getJavaVisibilityModifierFromEnum(JavaVisibility visibility) {
        switch visibility {
            case JavaVisibility.PUBLIC: return ModifiersFactory.eINSTANCE.createPublic
            case JavaVisibility.PRIVATE: return ModifiersFactory.eINSTANCE.createPrivate
            case JavaVisibility.PROTECTED: return ModifiersFactory.eINSTANCE.createProtected
            case JavaVisibility.PACKAGE: return null
            default: throw new IllegalArgumentException("Invalid Visibility: " + visibility)
        }
    }
    
    def static JavaVisibility getEnumConstantFromJavaVisibility(Class<? extends Modifier> modifier) {
    	
    }
    
    /**
     * The created Class is not contained in a compilationunit.
     */
    def static createJavaClass(String name, JavaVisibility visibility, boolean abstr, boolean fin) {
        val jClass = ClassifiersFactory.eINSTANCE.createClass;
        setName(jClass, name)
        addJavaVisibilityModifier(jClass, visibility)
        setAbstract(jClass, abstr)
        setFinal(jClass, fin)

        return jClass;
    }
    
    /**
     * The created Interface is not contained in a compilationunit.
     */
    def static createJavaInterface(String name, EList<Interface> superInterfaces) {
        val jInterface = ClassifiersFactory.eINSTANCE.createInterface;
        setName(jInterface, name)
        jInterface.makePublic;
        if (!superInterfaces.nullOrEmpty) {
            jInterface.extends.addAll(createNamespaceReferenceFromList(superInterfaces));
        }
        return jInterface;
    }
    
    /**
     * The Method automatically sets the .java FileExtension
     */
    def static createEmptyCompilationUnit(String nameWithoutFileExtension) {
        val cu = ContainersFactory.eINSTANCE.createCompilationUnit
        cu.name = nameWithoutFileExtension + ".java";
        return cu
    }
    
    /**
     * @return public Operation with name; no return, params or modifier
     */
    def static createSimpleJavaOperation(String name) {
        return createJavaClassMethod(name, null, JavaVisibility.PUBLIC, false, false, null)
    }
    
    /**
     * return and params can be null
     */
    def static createJavaClassMethod(String name, TypeReference returnType, JavaVisibility visibility, boolean abstr, boolean stat, EList<Parameter> params) {
        val jMethod = MembersFactory.eINSTANCE.createClassMethod;
        setName(jMethod, name)
        setTypeReferenceIfNotNull(jMethod, returnType)
        addJavaVisibilityModifier(jMethod, visibility)
		setAbstract(jMethod, abstr)
		setStatic(jMethod, stat)
        addParametersIfNotNull(jMethod, params)
        return jMethod;
    }
    
    
    
    /**
     * Return an InterfaceMethod (public, not static, not abstract)
     */
    def static createJavaInterfaceMethod(String name, TypeReference returnType, EList<Parameter> params) {
        val jMethod = MembersFactory.eINSTANCE.createInterfaceMethod;
        setName(jMethod, name)
        setTypeReferenceIfNotNull(jMethod, returnType)
        jMethod.makePublic;
        addParametersIfNotNull(jMethod, params)
        return jMethod;
    }
    
    def static  createJavaAttribute(String name, TypeReference type, JavaVisibility visibility, boolean fin, boolean stat) {
        val jAttribute = MembersFactory.eINSTANCE.createField;
        setName(jAttribute, name)
        addJavaVisibilityModifier(jAttribute, visibility)
        setFinal(jAttribute, fin)
        setStatic(jAttribute, stat)
        setTypeReferenceIfNotNull(jAttribute, type)
        return jAttribute;
    }
    
    def static createJavaParameter(String name, TypeReference type) {
        val param = ParametersFactory.eINSTANCE.createOrdinaryParameter;
        setName(param, name)
        setTypeReferenceIfNotNull(param, type)
        return param;
    }
    
    def static createJavaEnumConstant(String name) {
    	val constant = MembersFactory.eINSTANCE.createEnumConstant
    	constant.name = name
    	return constant
    }
    
    /**
     * Gibt den ersten Java-Classifier zurück, der zur Java-TypeReference type gehört.
     * Ist type kein NameSpaceClassifierreference, kommt null zurück.
     
    def static ConcreteClassifier getClassifierfromTypeRef(TypeReference type) {
        if (type instanceof NamespaceClassifierReference) {
            return (type as NamespaceClassifierReference).classifierReferences.head.target as ConcreteClassifier;
        }
        return null;
    }*/
    
    /**
     * Verpackt ein Java-ConcreteClassifier in ein NamespaceClassifierReference
     */
    def static NamespaceClassifierReference createNamespaceReferenceFromClassifier(ConcreteClassifier concreteClassifier) {
        if (concreteClassifier === null) {
        	throw new IllegalArgumentException("Cannot create a NamespaceClassifierReference for null")
        }
        val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
        var classifierRef = TypesFactory.eINSTANCE.createClassifierReference
        classifierRef.target = concreteClassifier
        namespaceClassifierReference.classifierReferences.add(classifierRef)
        return namespaceClassifierReference
    }
    
    def static Classifier getClassifierFromTypeReference(TypeReference typeRef) {
        val type = getJavaTypeFromTypeReference(typeRef)
        if (type instanceof Classifier) {
            return type as Classifier
        } else {
            logger.warn("The TypeReference " + typeRef + " does not contain a Classifier. Returning null.")
            return null
        }
    }
    
    def static dispatch Type getJavaTypeFromTypeReference(TypeReference typeRef) {
        logger.warn(typeRef + " is neither a NamespaceClassifierReference nor a PrimitiveType. Returning null.")
        return null
    }
    
    def static dispatch Type getJavaTypeFromTypeReference(NamespaceClassifierReference namespaceRef) {
        if (namespaceRef.classifierReferences.nullOrEmpty) {
            throw new IllegalArgumentException(namespaceRef + " has no classifierReferences")
        } else if (namespaceRef.classifierReferences.head.target === null) {
            logger.warn("The first target of the classifierReference of " + namespaceRef + " is null")
            return null
        } else {
            return namespaceRef.classifierReferences.head.target
        }
    }
    def static dispatch Type getJavaTypeFromTypeReference(PrimitiveType primType) {
        return primType
    }
    def static dispatch Type getJavaTypeFromTypeReference(java.lang.Void nullReference) {
        logger.warn("Cannot get Type of a null-TypeReference. Returning null.")
        return null
    }
    
    /**
     * @param add true -> hinzufügen, sonst entfernen
     */
    def static setJavaModifier(AnnotableAndModifiable jModifiable, Modifier mod, boolean add) {
        if (add) {
            if (!jModifiable.hasModifier(mod.class)) {//TODO Schauen, ob es einen Fehler gibt
                jModifiable.addModifier(mod)  
            } else {
            	logger.warn("The Java AnnotableAndModifiable " + jModifiable.class + " already has the modifier " + mod.class)
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
    
    def static void setName(org.emftext.language.java.commons.NamedElement namedElement, String name) {
    	if (name === null) {
    		throw new IllegalArgumentException("Cannot set name of " + namedElement + " to null")
    	}
    	namedElement.name = name
    }
    
    def static void setFinal(AnnotableAndModifiable modifiable, boolean toAdd) {
    	setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createFinal, toAdd)
    }
    
    def static void setAbstract(AnnotableAndModifiable modifiable, boolean toAdd) {
    	setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createAbstract, toAdd)
    }
    
    def static void setStatic(AnnotableAndModifiable modifiable, boolean toAdd) {
    	setJavaModifier(modifiable, ModifiersFactory.eINSTANCE.createStatic, toAdd)
    }
    
    def static void setTypeReferenceIfNotNull(org.emftext.language.java.types.TypedElement typedElement, TypeReference typeRef) {
    	if (typeRef !== null) {
    		typedElement.typeReference = typeRef
    	}
    }
    
    def static void addParametersIfNotNull(Parametrizable parametrizable, List<Parameter> params) {
    	if (!params.nullOrEmpty) {
    		parametrizable.parameters.addAll(params)
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
     * Removes all Private, Public and Protected modifiers from a modifiable.
     */
    def static removeJavaVisibilityModifiers(AnnotableAndModifiable modifiable) {
        modifiable.removeModifier(typeof(Private))
        modifiable.removeModifier(typeof(Protected))
        modifiable.removeModifier(typeof(Public))
    }
    
    /**
     * Removes a modifier from a modifiable
     */
    def static <T extends Modifier> removeJavaModifier(AnnotableAndModifiable modifiable, Class<T> modifier ) {
        modifiable.removeModifier(modifier)
    }
    
    /**
     * Converts a list with ConcreteClassifiers to a list with corresponding NamespaceClassifierRefrences.
     */
    def static EList<NamespaceClassifierReference> createNamespaceReferenceFromList(List<? extends ConcreteClassifier> list) {
        val typeReferences = new BasicEList<NamespaceClassifierReference>
        for (ConcreteClassifier i : list) {
            typeReferences += createNamespaceReferenceFromClassifier(i)
        }
        return typeReferences
    }
    
    def static TypeReference createCollectiontypeReference(String collectionQualifiedName, org.emftext.language.java.classifiers.Class innerTypeClass) {
    	val innerTypeReference = createNamespaceReferenceFromClassifier(innerTypeClass)
    	val qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument();
		qualifiedTypeArgument.typeReference = innerTypeReference;
		val collectionClassNamespaceReference = createNamespaceReferenceFromClassifier(tools.vitruv.applications.umljava.util.JavaUtil.createJavaClassImport(collectionQualifiedName).classifier)
		collectionClassNamespaceReference.classifierReferences.get(0).typeArguments += qualifiedTypeArgument;
		return collectionClassNamespaceReference
    }
    
    /**
     * Creates a Java-ClassifierImport from a qualified name
     */
    def static ClassifierImport createJavaClassImport(String qualifiedName) {
		val content = "package dummyPackage;\n " +
				"import " + qualifiedName + ";\n" +
				"public class DummyClass {}";
		val dummyCU = createJavaRoot("DummyClass", content) as CompilationUnit;
		val classifierImport = (dummyCU.getImports().get(0) as ClassifierImport)
		//EcoreUtil.copy(classifierImport);
		return classifierImport;
		
	}
	
	/**
	 * Creates a JavaRoot Object with the given content
	 * 
	 */
	def static JavaRoot createJavaRoot(String name, String content) {
		val JamoppParser jaMoPPParser = new JamoppParser
		val inStream = new ByteArrayInputStream(content.bytes)
		val javaRoot = jaMoPPParser.parseCompilationUnitFromInputStream(URI.createFileURI(name + ".java"),
			inStream)
		javaRoot.name = name + ".java"
		EcoreUtil.remove(javaRoot)
		return javaRoot
	}
   
   /**
    * Adds a constructor to the class
    */
   def static Constructor addJavaConstructorToClass(org.emftext.language.java.classifiers.Class jClass, JavaVisibility visibility, List<Parameter> params) {
   	   val constructor = MembersFactory.eINSTANCE.createConstructor
   	   setName(constructor, jClass.name)
   	   addParametersIfNotNull(constructor, params)
   	   addJavaVisibilityModifier(constructor, visibility)
   	   jClass.members += constructor
   	   
   	   return constructor
   }
   
   def static getJavaVisibilityConstantFromUmlVisibilityKind(VisibilityKind uVisibility) {
   	   switch(uVisibility) {
   	   	case VisibilityKind.PUBLIC_LITERAL: return JavaVisibility.PUBLIC
   	   	case VisibilityKind.PROTECTED_LITERAL: return JavaVisibility.PROTECTED
   	   	case VisibilityKind.PACKAGE_LITERAL : return JavaVisibility.PACKAGE
   	   	case VisibilityKind.PRIVATE_LITERAL: return JavaVisibility.PRIVATE
   	   	default: throw new IllegalArgumentException("Unknown VisibilityKind: " + uVisibility)
   	   }
   }
   
   def static createEnumConstantsFromList(List<String> enumConstantNames) {
   	   val  enumConstants = new ArrayList<EnumConstant>
   	   for (name : enumConstantNames) {
   	   	   enumConstants += createJavaEnumConstant(name)
   	   }
   	   return enumConstants
   }
}