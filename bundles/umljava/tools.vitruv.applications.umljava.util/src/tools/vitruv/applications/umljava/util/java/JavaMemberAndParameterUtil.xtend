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
import org.emftext.language.java.statements.ExpressionStatement
import org.emftext.language.java.types.TypeReference
import static tools.vitruv.applications.umljava.util.java.JavaModifierUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaStatementUtil.*
import static tools.vitruv.applications.umljava.util.CommonUtil.*
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.expressions.AssignmentExpression
import org.emftext.language.java.statements.Return

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
    
    def static List<EnumConstant> createJavaEnumConstantsFromList(List<String> enumConstantNames) {
        val enumConstantList = new ArrayList<EnumConstant>
        for (name : enumConstantNames) {
            enumConstantList += createJavaEnumConstant(name)
        }
        return enumConstantList
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
   
   def static createJavaConstructorAndAddToClass(Class jClass, JavaVisibility visibility) {
        val constructor = MembersFactory.eINSTANCE.createConstructor
        constructor.name = jClass.name
        setJavaVisibilityModifier(constructor, visibility) 
        jClass.members += constructor
        return constructor
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
   def static createJavaSetterForAttributeWithNullCheck(Field jAttribute, JavaVisibility visibility) {
       val param = createJavaParameter(firstLettertoLowercase(jAttribute.name), EcoreUtil.copy(jAttribute.typeReference))
       val setterMethod = createJavaClassMethod(buildSetterName(jAttribute.name), null, visibility, false, false, #[param])
       val paramReference = createIdentifierReference(param)
       val attributeAssignment = createAssignmentExpression(createSelfReferenceToAttribute(jAttribute), OperatorsFactory.eINSTANCE.createAssignment, EcoreUtil.copy(paramReference))
       val assignmentStatement = wrapExpressionInExpressionStatement(attributeAssignment)
       val ifCondition = createBinaryEqualityExpression(paramReference, OperatorsFactory.eINSTANCE.createNotEqual, LiteralsFactory.eINSTANCE.createNullLiteral)
       val condition = createCondition(ifCondition, assignmentStatement, null)
       setterMethod.statements += condition
       return setterMethod
   }
   
   /**
    * @param visibility Visibility of the Setter
    */
   def static createJavaSetterForAttribute(Field jAttribute, JavaVisibility visibility) {
       val param = createJavaParameter(firstLettertoLowercase(jAttribute.name), EcoreUtil.copy(jAttribute.typeReference))
       val setterMethod = createJavaClassMethod(buildSetterName(jAttribute.name), null, visibility, false, false, #[param])
       val paramReference = createIdentifierReference(param)
       val attributeAssignment = createAssignmentExpression(createSelfReferenceToAttribute(jAttribute), OperatorsFactory.eINSTANCE.createAssignment, EcoreUtil.copy(paramReference))
       setterMethod.statements += wrapExpressionInExpressionStatement(attributeAssignment)
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
        } else if (!cons.statements.filter(ExpressionStatement).filter[expressionHasAttributeSelfReference(it, jAttribute)].nullOrEmpty) {
            return true
        }
        return false
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
    
    /**
     * @param jAttributeWithOldName the Attribute with the old  Name
     */
    def static void renameSettersOfAttribute(Field jAttributeWithnewName, String oldName) {
        val setters = getJavaSettersOfAttribute(jAttributeWithnewName.containingConcreteClassifier, oldName)
        for (setter : setters) {
            renameSetter(setter, jAttributeWithnewName, oldName)
        }
        
    }
    /**
     * @param newName new Setter name without set-Prefix
     */
    def static void renameSetter(ClassMethod setter, Field jAttribute, String oldName) {
        setter.name = buildSetterName(jAttribute.name)
        for (expStatement : setter.statements.filter(ExpressionStatement)) {
            val selfReference = getAttributeSelfReferenceInExpressionStatement(expStatement, oldName)
            selfReference.target = EcoreUtil.copy(jAttribute)
        }
        
    }
    
    def static void updateAttributeTypeInSetters(Field jAttribute) {
        val setters = getJavaSettersOfAttribute(jAttribute)
        for (setter : setters) {
            updateAttributeTypeInSetter(setter, jAttribute)
        }
    }
    

    /**
     * Assumption: Setter has one parameter and one assignment for the attribute
     * 
     */
    def static void updateAttributeTypeInSetter(ClassMethod setter, Field jAttribute) {
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
     * 
     * @param jAttribute the attribute with the new name
     */
    def static void renameGettersOfAttribute(Field jAttribute, String oldName) {
        for (getter : getJavaGettersOfAttribute(jAttribute.containingConcreteClassifier, oldName)) {
            renameGetterOfAttribute(getter, jAttribute)
        }
    }
    
    /**
     * Assumption: standard getter that only returns the attribute
     */
    def static void renameGetterOfAttribute(ClassMethod getter, Field jAttribute) {
        getter.name = buildGetterName(jAttribute.name)
        val returnStatement = getter.statements.filter(Return).head
        if (returnStatement !== null) {
            returnStatement.returnValue = createSelfReferenceToAttribute(jAttribute)
        }
    }
    
    def static void updateAttributeTypeInGetters(Field jAttribute) {
        for (getter : getJavaGettersOfAttribute(jAttribute)) {
            updateAttributeTypeInGetter(getter, jAttribute)
        }
    }
    
    def static void updateAttributeTypeInGetter(ClassMethod getter, Field jAttribute) {
        getter.typeReference = EcoreUtil.copy(jAttribute.typeReference)
    }
    
    def static String buildSetterName(String attributeName) {
        return "set" + firstLettertoUppercase(attributeName)
    }
    
    def static String buildGetterName(String attributeName) {
        return "get" + firstLettertoUppercase(attributeName)
    }
}