package tools.vitruv.applications.umljava.util.java

import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.EnumConstant
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.operators.OperatorsFactory
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.parameters.Parametrizable
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.statements.ExpressionStatement
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.types.TypeReference
import static tools.vitruv.applications.umljava.util.java.JavaModifierUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaStatementUtil.*
import static tools.vitruv.applications.umljava.util.CommonUtil.*


class JavaMemberAndParameterUtil {
    private static val logger = Logger.getLogger(JavaMemberAndParameterUtil.simpleName)
    private new() {}
    
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
        setJavaVisibilityModifier(jMethod, visibility)
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
        setJavaVisibilityModifier(jAttribute, visibility)
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
    * Adds a constructor to the class
    */
   def static Constructor addJavaConstructorToClass(Class jClass, JavaVisibility visibility, List<Parameter> params) {
       val constructor = MembersFactory.eINSTANCE.createConstructor
       setName(constructor, jClass.name)
       addParametersIfNotNull(constructor, params)
       setJavaVisibilityModifier(constructor, visibility)
       jClass.members += constructor
       
       return constructor
   }
   
   def static createEnumConstantsFromList(List<String> enumConstantNames) {
       val  enumConstants = new ArrayList<EnumConstant>
       for (name : enumConstantNames) {
           enumConstants += createJavaEnumConstant(name)
       }
       return enumConstants
   }
   
   def static createJavaConstructorAndAddToClass(Class jClass) {
        val constructor = MembersFactory.eINSTANCE.createConstructor
        constructor.name = jClass.name
        constructor.makePublic
        jClass.members += constructor
        return constructor
   }
    
   def static ClassMethod createJavaGetterForAttribute(Field jAttribute, JavaVisibility visibility) {
       val getterMethod = createJavaClassMethod("get" + firstLettertoUppercase(jAttribute.name), EcoreUtil.copy(jAttribute.typeReference), visibility, false, false, null)
       getterMethod.statements += createReturnStatement(createSelfReferenceToAttribute(jAttribute))
       return getterMethod
   }
   
   def static createJavaSetterForAttribute(Field jAttribute, JavaVisibility visibility) {
       val param = createJavaParameter(firstLettertoLowercase(jAttribute.name), EcoreUtil.copy(jAttribute.typeReference))
       val setterMethod = createJavaClassMethod("set" + firstLettertoUppercase(jAttribute.name), null, visibility, false, false, #[param])
       val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
       val paramReference = ReferencesFactory.eINSTANCE.createIdentifierReference
       paramReference.target = param
       expressionStatement.expression = createAssignmentExpression(createSelfReferenceToAttribute(jAttribute), OperatorsFactory.eINSTANCE.createAssignment,paramReference)
       setterMethod.statements += expressionStatement
       return setterMethod
   }
   def static void addParametersIfNotNull(Parametrizable parametrizable, List<Parameter> params) {
        if (!params.nullOrEmpty) {
            parametrizable.parameters.addAll(params)
        }
    }
    
    def static boolean constructorContainsAttributeSelfReferenceStatement(Constructor cons, Field jAttribute) {
        if (cons.statements.nullOrEmpty || cons.statements.filter(ExpressionStatement).nullOrEmpty) {
            return false
        }
        if (!cons.statements.filter(ExpressionStatement).filter[ExpressionHasAttributeSelfReference(it, jAttribute)].nullOrEmpty) {
            return true
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
   
   def static List<ClassMethod> getJavaGettersOfAttribute(Field jAttribute) {
       if (jAttribute === null) {
           logger.warn("Cannot retrieve Getters for Java-Attribute null. Returning empty List.")
           return Collections.<ClassMethod>emptyList
       }
       val expectedSetterName = "get" + firstLettertoUppercase(jAttribute.name)
       val jClass = jAttribute.eContainer as Class
       if (jClass === null) {
           return Collections.<ClassMethod>emptyList 
       }
       return jClass.members.filter(ClassMethod)?.filter[expectedSetterName.equals(name)]?.toList
   }
   
   def static List<ClassMethod> getJavaSettersOfAttribute(Field jAttribute) {
       if (jAttribute === null) {
           logger.warn("Cannot retrieve Setters for Java-Attribute null. Returning empty List.")
           return Collections.<ClassMethod>emptyList
       }
       val expectedSetterName = "set" + firstLettertoUppercase(jAttribute.name)
       val jClass = jAttribute.eContainer as Class
       return jClass.members.filter(ClassMethod)?.filter[expectedSetterName.equals(name)]?.toList
   }
   
   def static void removeJavaGettersOfAttribute(Field jAttribute) {
       val getters = getJavaGettersOfAttribute(jAttribute)
       if (!getters.nullOrEmpty) {
           for (getter : getters) {
               EcoreUtil.remove(getter)
           }
       }
   }
   
   def static void removeJavaSettersOfAttribute(Field jAttribute) {
       val setters = getJavaSettersOfAttribute(jAttribute)
       if (!setters.nullOrEmpty) {
           for (setter : setters) {
               EcoreUtil.remove(setter)
           }
       }
    }
}