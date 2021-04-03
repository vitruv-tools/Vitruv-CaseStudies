package tools.vitruv.applications.util.temporary.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.ECollections
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.expressions.AssignmentExpression
import org.emftext.language.java.expressions.ExpressionsFactory
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.EnumConstant
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.operators.OperatorsFactory
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.parameters.Parametrizable
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.statements.ExpressionStatement
import org.emftext.language.java.statements.Return
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory

import static tools.vitruv.applications.util.temporary.java.JavaModifierUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaStatementUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.domains.java.util.JavaModificationUtil.*

/**
 * A util class for field, method and parameter related util functions.
 * 
 * @author Fei
 */
@Utility
class JavaMemberAndParameterUtil {
    static val logger = Logger.getLogger(JavaMemberAndParameterUtil.simpleName)

    /**
     * @return public Operation with name no return, params or modifier
     */
    def static createSimpleJavaOperation(String name) {
        return createJavaClassMethod(name, null, JavaVisibility.PUBLIC, false, false, null)
    }

    /**
     * Creates a new java class method with the given properties.
     * The class method is not contained in any classifiers.
     * 
     * @param name the name of the class method
     * @param returnType the return type of the class method
     * @param visibility the visibility of the class method
     * @param abstr if the class method should be abstract
     * @param stat if the class method should be static
     * @param params list of parameters for the class method
     * @return the new class method
     */
    def static createJavaClassMethod(String name, TypeReference returnType, JavaVisibility visibility, boolean abstr, boolean stat, List<Parameter> params) {
        val jMethod = MembersFactory.eINSTANCE.createClassMethod
        setName(jMethod, name)
        setTypeReference(jMethod, returnType)
        setJavaVisibilityModifier(jMethod, visibility)
        setAbstract(jMethod, abstr)
        setStatic(jMethod, stat)
        addParametersIfNotNull(jMethod, params)
        return jMethod
    }

    /**
     * Creates a new java interface method with the given properties.
     * The interface method is not contained in any classifiers.
     * The visibility of the interface method is set to public.
     * 
     * @param name the name of the interface method
     * @param returnType return type of the interface method
     * @param params list of parameters for the interface method
     * @return the new interface method
     */
    def static createJavaInterfaceMethod(String name, TypeReference returnType, List<Parameter> params) {
        val jMethod = MembersFactory.eINSTANCE.createInterfaceMethod
        setName(jMethod, name)
        setTypeReference(jMethod, returnType)
        jMethod.makePublic
        addParametersIfNotNull(jMethod, params)
        return jMethod
    }

    /**
     * Creates a new java attribute with the given properties.
     * The attribute is not contained in any classifiers.
     * 
     * @param name the name of attribute
     * @param visibility the visibility of the attribute
     * @param fin if the attribute should be final
     * @param stat if the attribute should be static
     * @return the new attribute
     */
    def static createJavaAttribute(String name, TypeReference type, JavaVisibility visibility, boolean fin, boolean stat) {
        val jAttribute = MembersFactory.eINSTANCE.createField
        setName(jAttribute, name)
        setJavaVisibilityModifier(jAttribute, visibility)
        setFinal(jAttribute, fin)
        setStatic(jAttribute, stat)
        setTypeReference(jAttribute, type)
        return jAttribute
    }

    def static createJavaParameter(String name, TypeReference type) {
        val param = ParametersFactory.eINSTANCE.createOrdinaryParameter
        param.name = name
        param.typeReference = type
        return param
    }

    def static createJavaEnumConstant(String name) {
        val constant = MembersFactory.eINSTANCE.createEnumConstant
        constant.name = name
        return constant
    }

    def static List<EnumConstant> createJavaEnumConstantsFromList(List<String> enumConstantNames) {
        val enumConstantList = new ArrayList<EnumConstant>
        for (name : enumConstantNames) {
            enumConstantList += createJavaEnumConstant(name)
        }
        return enumConstantList
    }

    /**
     * Creates a getter for the attribute and adds it t its class.
     * @param jAttribute the attribute for which a getter should be created
     */
    def static createGetterForAttribute(Field jAttribute) {
        createJavaGetterForAttribute(jAttribute, JavaVisibility.PUBLIC)
    }

    /**
     * Creates a setter for the attribute and adds it to its class.
     * @param jAttribute the attribute for which a getter should be created
     */
    def static createSetterForAttribute(Field jAttribute) {
        createJavaSetterForAttribute(jAttribute, JavaVisibility.PUBLIC)
    }

    /**
     * Creates and adds a constructor to the given class
     */
    def static Constructor addJavaConstructorToClass(Class jClass, JavaVisibility visibility, List<Parameter> params) {
        val constructor = MembersFactory.eINSTANCE.createConstructor
        setName(constructor, jClass.name)
        addParametersIfNotNull(constructor, params)
        setJavaVisibilityModifier(constructor, visibility)
        jClass.members += constructor

        return constructor
    }

    /**
     * Creates a constructor with the given visibility and adds it to the given class.
     * 
     * @param jClass the class for which the constructor should be created
     * @param visibility the visibility of the constructor
     * @return the new constructor
     */
    def static createJavaConstructorAndAddToClass(Class jClass, JavaVisibility visibility) {
        val constructor = MembersFactory.eINSTANCE.createConstructor
        constructor.name = jClass.name
        setJavaVisibilityModifier(constructor, visibility)
        jClass.members += constructor
        return constructor
    }

    def static Constructor getOrCreateConstructorToClass(Class javaClass) {
        val constructors = javaClass.members.filter[it instanceof Constructor].map[it as Constructor]
        if (constructors.nullOrEmpty) {
            val Constructor constructor = MembersFactory.eINSTANCE.createConstructor
            return addConstructorToClass(constructor, javaClass)
        }
        return constructors.iterator.next
    }

    /**
     * @param visibility Visibility of the Getter
     */
    def static ClassMethod createJavaGetterForAttribute(Field jAttribute, JavaVisibility visibility) {
        val getterMethod = createJavaClassMethod(buildGetterName(jAttribute.name), EcoreUtil.copy(jAttribute.typeReference), visibility, false, false, null)
        getterMethod.statements += createReturnStatement(createSelfReferenceToAttribute(jAttribute))
        return getterMethod
    }

    /**
     * @param visibility Visibility of the Setter
     */
    def static createJavaSetterForAttribute(Field jAttribute, JavaVisibility visibility) {
        val param = createJavaParameter(jAttribute.name.toFirstLower, EcoreUtil.copy(jAttribute.typeReference))
        val setterMethod = createJavaClassMethod(buildSetterName(jAttribute.name), null, visibility, false, false, #[param])
        val paramReference = createIdentifierReference(param)
        val attributeAssignment = createAssignmentExpression(createSelfReferenceToAttribute(jAttribute), OperatorsFactory.eINSTANCE.createAssignment,
            EcoreUtil.copy(paramReference))
        setterMethod.statements += wrapExpressionInExpressionStatement(attributeAssignment)
        return setterMethod
    }

    def static createSetter(Field field, ClassMethod method) {
        method.name = "set" + field.name.toFirstUpper
        method.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
        method.typeReference = TypesFactory.eINSTANCE.createVoid
        val parameter = ParametersFactory.eINSTANCE.createOrdinaryParameter
        parameter.name = field.name
        parameter.typeReference = EcoreUtil.copy(field.typeReference)
        method.parameters.add(parameter)
        val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
        val assigmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression

        // this.
        val selfReference = ReferencesFactory.eINSTANCE.createSelfReference
        assigmentExpression.child = selfReference

        // .fieldname
        val fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference
        fieldReference.target = field
        selfReference.next = fieldReference
        selfReference.^self = LiteralsFactory.eINSTANCE.createThis()
        // =
        assigmentExpression.assignmentOperator = OperatorsFactory.eINSTANCE.createAssignment

        // name     
        val identifierReference = ReferencesFactory.eINSTANCE.createIdentifierReference
        identifierReference.target = parameter

        assigmentExpression.value = identifierReference
        expressionStatement.expression = assigmentExpression
        method.statements.add(expressionStatement)
        return method
    }

    def static createGetter(Field field, ClassMethod method) {
        method.name = "get" + field.name.toFirstUpper
        method.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
        method.typeReference = EcoreUtil.copy(field.typeReference)

        // this.fieldname
        val identifierRef = ReferencesFactory.eINSTANCE.createIdentifierReference
        identifierRef.target = field

        // return
        val ret = StatementsFactory.eINSTANCE.createReturn
        ret.returnValue = identifierRef
        method.statements.add(ret)
        return method
    }

    def static   addParametersIfNotNull(Parametrizable parametrizable, List<Parameter> params) {
        if (!params.nullOrEmpty) {
            parametrizable.parameters.addAll(params)
        }
    }

    /**
     * Checks if a Java-Method is Getter for a given Java-Attribute by name.
     * Returns true if the name is "getAttributeName"
     */
    def static boolean javaGetterForAttributeExists(Field jAttribute) {
        val possibleGetters = getJavaGettersOfAttribute(jAttribute)
        if (!possibleGetters.nullOrEmpty) {
            return true
        }
        return false
    }

    /**
     * Checks if a Java-Method is Setter for a given Java-Attribute by name.
     * Returns true if the name is "setAttributeName"
     */
    def static boolean javaSetterForAttributeExists(Field jAttribute) {
        val possibleSetters = getJavaSettersOfAttribute(jAttribute)
        if (!possibleSetters.nullOrEmpty) {
            return true
        }
        return false
    }

    /**
     * Returns the the methods with the name getAttributeName
     */
    def static List<ClassMethod> getJavaGettersOfAttribute(Field jAttribute) {
        if (jAttribute === null) {
            logger.warn("Cannot retrieve Getters for Java-Attribute null. Returning empty List.")
            return Collections.<ClassMethod>emptyList
        }
        return getJavaGettersOfAttribute(jAttribute.containingConcreteClassifier, jAttribute.name)
    }

    /**
     * Returns the the methods with the name setAttributeName
     */
    def static List<ClassMethod> getJavaSettersOfAttribute(Field jAttribute) {
        if (jAttribute === null) {
            logger.warn("Cannot retrieve Setters for Java-Attribute null. Returning empty List.")
            return Collections.<ClassMethod>emptyList
        }
        return getJavaSettersOfAttribute(jAttribute.containingConcreteClassifier, jAttribute.name)
    }

    def static List<ClassMethod> getJavaClassMethodsWithName(ConcreteClassifier jClass, String name) {
        if (jClass === null) {
            return Collections.<ClassMethod>emptyList
        }
        return jClass.members.filter(ClassMethod).filter[it.name.equals(name)].toList
    }

    def static List<ClassMethod> getJavaSettersOfAttribute(ConcreteClassifier jClass, String jAttributeName) {
        return getJavaClassMethodsWithName(jClass, buildSetterName(jAttributeName))
    }

    def static List<ClassMethod> getJavaGettersOfAttribute(ConcreteClassifier jClass, String jAttributeName) {
        return getJavaClassMethodsWithName(jClass, buildGetterName(jAttributeName))
    }

    /**
     * Renames the given setter so that it matches the name of the given attribute
     * @param oldName the name of jAttribute before it was renamed
     */
    def static renameSetter(ClassMethod setter, Field jAttribute, String oldName) {
        setter.name = buildSetterName(jAttribute.name)
        setter.parameters.head?.setName(jAttribute.name.toFirstLower)
        for (expStatement : setter.statements.filter(ExpressionStatement)) {
            val selfReference = getAttributeSelfReferenceInExpressionStatement(expStatement, oldName)
            selfReference?.setTarget(jAttribute)
        }
    }

    /**
     * Assumption: Setter has one parameter and one assignment for the attribute
     * 
     */
    def static updateAttributeTypeInSetter(ClassMethod setter, Field jAttribute) {
        val param = setter.parameters.head
        if (param !== null) {
            param.typeReference = EcoreUtil.copy(jAttribute.typeReference)
            val expStatement = setter.statements.filter(ExpressionStatement).head
            if (expressionHasAttributeSelfReference(expStatement, jAttribute)) {
                (expStatement.expression as AssignmentExpression).value = createIdentifierReference(param)
            }
        }
    }

    /**
     * Renames the given getter so that it matches the name of the given attribute
     * Assumption: standard getter that only returns the attribute
     */
    def static renameGetterOfAttribute(ClassMethod getter, Field jAttribute) {
        getter.name = buildGetterName(jAttribute.name)
        val returnStatement = getter.statements.filter(Return).head
        if (returnStatement !== null) {
            returnStatement.returnValue = createSelfReferenceToAttribute(jAttribute)
        }
    }

    /**
     * Updates the return type of the given getter to match the return type of the given attribute.
     */
    def static updateAttributeTypeInGetter(ClassMethod getter, Field jAttribute) {
        getter.typeReference = EcoreUtil.copy(jAttribute.typeReference)
    }

    def static String buildSetterName(String attributeName) {
        return "set" + attributeName.toFirstUpper
    }

    def static String buildGetterName(String attributeName) {
        return "get" + attributeName.toFirstUpper
    }

    /**
     * Signatures are considered equal if methods have the same name, the same parameter types and the same return type
     * We do not consider modifiers (e.g. public or private here)
     */
    def static boolean hasSameSignature(Method method1, Method method2) {
        if (method1 == method2) {
            return true
        }
        if (!method1.name.equals(method2.name)) {
            return false
        }
        if (!hasSameTargetReference(method1.typeReference, method2.typeReference)) {
            return false
        }
        if (method1.parameters.size != method2.parameters.size) {
            return false
        }
        var int i = 0
        for (param1 : method1.parameters) {
            if (!hasSameTargetReference(param1.typeReference, method2.parameters.get(i).typeReference)) {
                return false
            }
            i++
        }
        return true
    }

    def static initializeClassMethod(ClassMethod classMethod, Method implementedMethod, boolean ensurePublic) {
        initializeClassMethod(classMethod, implementedMethod.name, implementedMethod.typeReference, implementedMethod.modifiers, implementedMethod.parameters,
            ensurePublic)
    }

    def static initializeClassMethod(ClassMethod classMethod, String name, TypeReference typeReference, Modifier[] modifiers,
        Parameter[] parameters, boolean ensurePublic) {
        classMethod.name = name
        if (null !== typeReference) {
            classMethod.typeReference = EcoreUtil.copy(typeReference)
        }
        if (null !== modifiers) {
            classMethod.annotationsAndModifiers.addAll(EcoreUtil.copyAll(modifiers))
        }
        if (ensurePublic) {
            val alreadyPublic = classMethod.annotationsAndModifiers.filter[modifier|modifier instanceof Public].size > 0
            if (!alreadyPublic) {
                classMethod.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
            }
        }
        if (null !== parameters) {
            classMethod.parameters.addAll(EcoreUtil.copyAll(parameters))
        }
    }

    def static ClassMethod findMethodInClass(ConcreteClassifier concreteClassifier, ClassMethod method) {
        for (Method currentMethod : concreteClassifier.methods) {
            if (currentMethod instanceof ClassMethod && currentMethod.name.equals(method.name) &&
                currentMethod.typeParameters.size == method.typeParameters.size) {
                // TODO finish check by comparing type reference and type of each parameter 
                return currentMethod as ClassMethod
            }
        }
        return null
    }

    /**
     * sorts the member list to ensure that fields are printed before constructors and constructors before methods
     */
    def static sortMembers(EList<? extends EObject> members) {
        ECollections.sort(members, new Comparator<EObject> {

            override compare(EObject o1, EObject o2) {
                // fields before constructors and methods
                if (o1 instanceof Field && (o2 instanceof Method || o2 instanceof Constructor)) {
                    return -1
                } else if ((o1 instanceof Method || o1 instanceof Constructor) && o2 instanceof Field) {
                    return 1

                // constructors before Methods  
                } else if (o1 instanceof Constructor && o2 instanceof Method) {
                    return -1
                } else if (o1 instanceof Method && o2 instanceof Constructor) {
                    return 1
                }
                return 0
            }

            override equals(Object obj) {
                return this == obj
            }

        })
    }
}
