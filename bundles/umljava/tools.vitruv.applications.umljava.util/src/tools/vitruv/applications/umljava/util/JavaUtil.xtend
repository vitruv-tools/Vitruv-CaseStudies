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
import org.emftext.language.java.containers.Package
import org.emftext.language.java.members.EnumConstant
import java.util.Collections
import org.emftext.language.java.members.Field
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.expressions.ExpressionsFactory
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.operators.OperatorsFactory
import org.emftext.language.java.instantiations.InstantiationsFactory
import org.emftext.language.java.references.SelfReference
import org.emftext.language.java.instantiations.NewConstructorCall
import org.emftext.language.java.expressions.AssignmentExpressionChild
import org.emftext.language.java.expressions.AssignmentExpression
import org.emftext.language.java.operators.AssignmentOperator
import org.emftext.language.java.references.ReferenceableElement
import org.emftext.language.java.statements.ExpressionStatement
import org.emftext.language.java.references.IdentifierReference
import org.hamcrest.Condition.Step
import org.emftext.language.java.generics.QualifiedTypeArgument
import org.emftext.language.java.expressions.Expression
import org.emftext.language.java.statements.Return
import org.emftext.language.java.members.ClassMethod

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
    
    def static void setJavaVisibilityModifier(AnnotableAndModifiable modifiable, JavaVisibility visibility) {
    	val visibilityModifier = getJavaVisibilityModifierFromEnum(visibility)
    	if (visibilityModifier !== null) {
    	    removeJavaVisibilityModifiers(modifiable)
    	    setJavaModifier(modifiable, visibilityModifier, true)
    	} else {
    	    logger.warn("No corresponding Java-Visibility-Modifier found for " + visibility)
    	}
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
        tools.vitruv.applications.umljava.util.JavaUtil.setJavaVisibilityModifier(jClass, visibility)
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
    
    def static createJavaCompilationUnitWithClassifierInPackage(ConcreteClassifier jClassifier, Package jPackage) {
        val compUnit = createEmptyCompilationUnit(jClassifier.name)
        compUnit.classifiers += jClassifier
        compUnit.namespaces.addAll(getJavaPackageAsStringList(jPackage))
        return compUnit
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
    def static createJavaClassMethod(String name, TypeReference returnType, JavaVisibility visibility, boolean abstr, boolean stat, List<Parameter> params) {
        val jMethod = MembersFactory.eINSTANCE.createClassMethod;
        setName(jMethod, name)
        setTypeReference(jMethod, returnType)
        tools.vitruv.applications.umljava.util.JavaUtil.setJavaVisibilityModifier(jMethod, visibility)
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
        setTypeReference(jMethod, returnType)
        jMethod.makePublic;
        addParametersIfNotNull(jMethod, params)
        return jMethod;
    }
    
    def static  createJavaAttribute(String name, TypeReference type, JavaVisibility visibility, boolean fin, boolean stat) {
        val jAttribute = MembersFactory.eINSTANCE.createField;
        setName(jAttribute, name)
        tools.vitruv.applications.umljava.util.JavaUtil.setJavaVisibilityModifier(jAttribute, visibility)
        setFinal(jAttribute, fin)
        setStatic(jAttribute, stat)
        setTypeReference(jAttribute, type)
        return jAttribute;
    }
    
    def static createJavaParameter(String name, TypeReference type) {
        val param = ParametersFactory.eINSTANCE.createOrdinaryParameter;
        setName(param, name)
        setTypeReference(param, type)
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
    
    def static void setTypeReference(org.emftext.language.java.types.TypedElement typedElement, TypeReference typeRef) {
    	if (typeRef !== null) {
    		typedElement.typeReference = typeRef
    	} else {
    	    typedElement.typeReference = TypesFactory.eINSTANCE.createVoid
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
   	   tools.vitruv.applications.umljava.util.JavaUtil.setJavaVisibilityModifier(constructor, visibility)
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
   
   def static getJavaPackageAsStringList(Package jPackage) {
       if (jPackage === null) { //Defaultpackage
           return Collections.<String>emptyList()
       }
       val packageStringList = EcoreUtil.copyAll(jPackage.namespaces) //TODO Was ist, wenn namespaces null
       packageStringList += jPackage.name
       return packageStringList
   }
   
   def static createJavaConstructorAndAddToClass(org.emftext.language.java.classifiers.Class jClass) {
        val constructor = MembersFactory.eINSTANCE.createConstructor
        constructor.name = jClass.name
        constructor.makePublic
        jClass.members += constructor
        return constructor
   }
   
   def static createNewForFieldInConstructor(Field jAttribute) {
        val classifier = jAttribute.containingConcreteClassifier
        if (classifier === null) {
            return null
        }
        val jClass = classifier as org.emftext.language.java.classifiers.Class

        // create constructor if none exists
        if (jClass.members.filter(Constructor).nullOrEmpty) {
            createJavaConstructorAndAddToClass(jClass)
        }
        
        for (constructor : jClass.members.filter(Constructor)) {
            if (!constructorContainsAttributeSelfReferenceStatement(constructor, jAttribute)) {
                addNewStatementToConstructor(constructor, jAttribute, jClass.fields, constructor.parameters)
            }
        }
    }
   
   def static addNewStatementToConstructor(Constructor constructor, Field jAttribute,
        Field[] fieldsToUseAsArgument, Parameter[] parametersToUseAsArgument) {
        val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
        val selfReference = createSelfReferenceToAttribute(jAttribute)
        val newConstructorCall = createNewConstructorCall(jAttribute, fieldsToUseAsArgument, parametersToUseAsArgument)
        val assignment = createAssignmentExpression(selfReference, 
            OperatorsFactory.eINSTANCE.createAssignment, newConstructorCall)
        expressionStatement.expression = assignment
        constructor.statements += expressionStatement
        return newConstructorCall
    }
    
    /**
     * Creates a this.jAttributename expression
     */
    def static SelfReference createSelfReferenceToAttribute(Field jAttribute) {
        val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
        selfReference.self = LiteralsFactory.eINSTANCE.createThis

        val fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference
        fieldReference.target = EcoreUtil.copy(jAttribute)
        selfReference.next = EcoreUtil.copy(fieldReference)
        return selfReference
    }
    
    def static NewConstructorCall createNewConstructorCall(Field jAttribute, 
        Field[] fieldsToUseAsArgument, Parameter[] parametersToUseAsArgument) {
        val newConstructorCall = InstantiationsFactory.eINSTANCE.createNewConstructorCall
        newConstructorCall.typeReference = EcoreUtil.copy(jAttribute.typeReference)
        updateArgumentsOfConstructorCall(jAttribute, fieldsToUseAsArgument, parametersToUseAsArgument, newConstructorCall)
        
        return newConstructorCall
    }
    
    def static void updateArgumentsOfConstructorCall(Field jAttribute, Field[] fieldsToUseAsArgument,
        Parameter[] parametersToUseAsArgument, NewConstructorCall newConstructorCall) {
        val List<TypeReference> typeListForConstructor = new ArrayList<TypeReference>
        val classifierOfAttributeType = getClassifierFromTypeReference(jAttribute.typeReference)
        if (classifierOfAttributeType !== null) {
            val constructorListOfClass = (classifierOfAttributeType as org.emftext.language.java.classifiers.Class).members.filter(Constructor)
            if (!constructorListOfClass.nullOrEmpty) {
                val firstConstructor = constructorListOfClass.head
                for (constructorParam : firstConstructor.parameters) {
                    typeListForConstructor += constructorParam.typeReference
                }
            }
        }
        for (typeRef : typeListForConstructor) {
            val refElement = typeRef.findMatchingTypeInParametersOrFields(fieldsToUseAsArgument,
                parametersToUseAsArgument)
            if (refElement !== null) {
                val identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference
                identifierReference.target = refElement
                newConstructorCall.arguments += identifierReference
            } else {
                newConstructorCall.arguments += LiteralsFactory.eINSTANCE.createNullLiteral
            }
        }
    }
    
    def static ReferenceableElement findMatchingTypeInParametersOrFields(TypeReference typeReferenceToFind,
        Field[] fieldsToUseAsArgument, Parameter[] parametersToUseAsArgument) {
        for (parameter : parametersToUseAsArgument) {
            if (typeReferenceEquals(typeReferenceToFind, parameter.typeReference)) {
                return parameter
            }
        }
        for (field : fieldsToUseAsArgument) {
            if (typeReferenceEquals(typeReferenceToFind, field.typeReference)) {
                return field
            }
        }
        return null
    }
    
    /**
     * Creates leftSide <operator> rightSide
     */
    def static AssignmentExpression createAssignmentExpression(AssignmentExpressionChild leftSide, 
        AssignmentOperator operator, org.emftext.language.java.expressions.Expression rightSide) {
        val assignmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression
        assignmentExpression.child = leftSide
        assignmentExpression.assignmentOperator = operator
        assignmentExpression.value = rightSide
        return assignmentExpression
    }
    
    def static dispatch typeReferenceEquals(TypeReference typeRef1, TypeReference typeRef2) {
        logger.warn("No dispatch Method found for the typeReferences " + typeRef1 + " and " + typeRef2 + ". Returning false.")
        return false
    }
    
    def static dispatch typeReferenceEquals(NamespaceClassifierReference typeRef1, NamespaceClassifierReference typeRef2) {
        return getClassifierFromTypeReference(typeRef1).name.equals(getClassifierFromTypeReference(typeRef2).name)
    }
    
    def static dispatch typeReferenceEquals(PrimitiveType primType1, PrimitiveType primtype2) {
        return primType1.class.equals(primtype2.class)
    }
    def static dispatch typeReferenceEquals(Void type1, Void typ22) {
        logger.warn("Both TypeReferences to compare are null. Returning true.")
        return true
    }
    
    def static boolean constructorContainsAttributeSelfReferenceStatement(Constructor cons, Field jAttribute) {
        if (cons.statements.nullOrEmpty || cons.statements.filter(ExpressionStatement).nullOrEmpty) {
            return false
        }
        if (!cons.statements.filter(ExpressionStatement).filter[ExpressionHasAttributeSelfReference(it, jAttribute)].nullOrEmpty) {
            return true
        }
    }
    
    def static boolean ExpressionHasAttributeSelfReference(ExpressionStatement expressionStatement, Field jAttribute) {
        if (expressionStatement.expression !== null && expressionStatement.expression instanceof AssignmentExpression) {
            val assignmentExpression = expressionStatement.expression as AssignmentExpression
            if ((assignmentExpression.child as SelfReference).self instanceof org.emftext.language.java.literals.This
                && ((assignmentExpression.child as SelfReference).next as IdentifierReference).target.name == jAttribute.name) {
                return true
            }
        }
        return false
    }
    
    
   def static Field getJavaAttributeByName(org.emftext.language.java.classifiers.Class jClass, String attributeName) {
       val candidates = jClass.members.filter(Field)
       for (member : candidates) {
           if (member.name == attributeName) {
               return member as Field
           }
       }
       return null
   }
   
   def static Constructor getFirstJavaConstructor(org.emftext.language.java.classifiers.Class jClass) {
       val candidates = jClass.members.filter(Constructor)
       if (!candidates.nullOrEmpty) {
           return candidates.head
       } else {
           return null
       }
   }
   
   def static getInnerTypeReferenceOfCollectionTypeReference(TypeReference typeRef) {
       if (typeRef instanceof NamespaceClassifierReference) {
           return (typeRef.classifierReferences.head.typeArguments.head as QualifiedTypeArgument).typeReference
       }
       logger.warn("Cannot get inner TypeReference of a non-NamespaceClassifierReference. Returning null.")
       return null
   }
   
   def static ClassMethod createJavaGetterForAttribute(Field jAttribute, JavaVisibility visibility) {
       val getterMethod = createJavaClassMethod("get" + firstLettertoUppercase(jAttribute.name), jAttribute.typeReference, visibility, false, false, null)
       getterMethod.statements += createReturnStatement(createSelfReferenceToAttribute(jAttribute))
       return getterMethod
   }
   
   def static createJavaSetterForAttribute(Field jAttribute, JavaVisibility visibility, Parameter param) {
       val setterMethod = createJavaClassMethod("get" + firstLettertoUppercase(jAttribute.name), null, visibility, false, false, #[param])
       val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
       val paramReference = ReferencesFactory.eINSTANCE.createIdentifierReference
       paramReference.target = param
       expressionStatement.expression = createAssignmentExpression(createSelfReferenceToAttribute(jAttribute), OperatorsFactory.eINSTANCE.createAssignment,paramReference)
       setterMethod.statements += expressionStatement
       return setterMethod
   }
   
   def static Return createReturnStatement(Expression returnValue) {
       val returnStatement = StatementsFactory.eINSTANCE.createReturn
       returnStatement.returnValue = returnValue
       return returnStatement
   }
   
   def static private String firstLettertoUppercase(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1)
    }
   
}