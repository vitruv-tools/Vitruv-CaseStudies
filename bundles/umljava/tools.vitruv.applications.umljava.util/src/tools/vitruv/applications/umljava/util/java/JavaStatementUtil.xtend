package tools.vitruv.applications.umljava.util.java

import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.expressions.AssignmentExpression
import org.emftext.language.java.expressions.AssignmentExpressionChild
import org.emftext.language.java.expressions.Expression
import org.emftext.language.java.expressions.ExpressionsFactory
import org.emftext.language.java.instantiations.InstantiationsFactory
import org.emftext.language.java.instantiations.NewConstructorCall
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.literals.This
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.operators.AssignmentOperator
import org.emftext.language.java.operators.OperatorsFactory
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.references.IdentifierReference
import org.emftext.language.java.references.ReferenceableElement
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.references.SelfReference
import org.emftext.language.java.statements.ExpressionStatement
import org.emftext.language.java.statements.Return
import org.emftext.language.java.statements.StatementsFactory
import org.emftext.language.java.types.TypeReference
import static tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*


class JavaStatementUtil {
    private static val logger = Logger.getLogger(JavaStatementUtil.simpleName)
    private new() {}
    
    def static Return createReturnStatement(Expression returnValue) {
       val returnStatement = StatementsFactory.eINSTANCE.createReturn
       returnStatement.returnValue = returnValue
       return returnStatement
   }
   
   def static createNewForFieldInConstructor(Field jAttribute) {
        val classifier = jAttribute.containingConcreteClassifier
        if (classifier === null) {
            return null
        }
        val jClass = classifier as Class

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
            val constructorListOfClass = (classifierOfAttributeType as Class).members.filter(Constructor)
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
        AssignmentOperator operator, Expression rightSide) {
        val assignmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression
        assignmentExpression.child = leftSide
        assignmentExpression.assignmentOperator = operator
        assignmentExpression.value = rightSide
        return assignmentExpression
    }
    
    def static boolean ExpressionHasAttributeSelfReference(ExpressionStatement expressionStatement, Field jAttribute) {
        if (expressionStatement.expression !== null && expressionStatement.expression instanceof AssignmentExpression) {
            val assignmentExpression = expressionStatement.expression as AssignmentExpression
            if ((assignmentExpression.child as SelfReference).self instanceof This
                && ((assignmentExpression.child as SelfReference).next as IdentifierReference).target.name == jAttribute.name) {
                return true
            }
        }
        return false
    }
}