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
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.statements.Condition
import org.emftext.language.java.expressions.EqualityExpressionChild
import org.emftext.language.java.operators.EqualityOperator
import org.emftext.language.java.expressions.EqualityExpression

/**
 * Util class for the creation and retrieving of statements.
 * 
 * @author Fei
 */
class JavaStatementUtil {
    private static val logger = Logger.getLogger(JavaStatementUtil.simpleName)
    private new() {}
    
    /**
     * Creates:
     * 
     * return <returnValue>
     * 
     * @param returnValue the expression that should placed behind the return key word
     */
    def static Return createReturnStatement(Expression returnValue) {
       val returnStatement = StatementsFactory.eINSTANCE.createReturn
       returnStatement.returnValue = returnValue
       return returnStatement
   }
   
    
    /**
     * Creates a <this.jAttributename> expression
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
    
   /**
    * 
    * (taken from tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaUtils, but modified)
    */
   def static createNewForFieldInConstructor(Field jAttribute) {
        val classifier = jAttribute.containingConcreteClassifier
        if (classifier === null) {
            return null
        }
        val jClass = classifier as Class

        if (jClass.members.filter(Constructor).nullOrEmpty) {
            createJavaConstructorAndAddToClass(jClass, JavaVisibility.PUBLIC)
        }
        for (constructor : jClass.members.filter(Constructor)) {
            if (!constructorContainsAttributeSelfReferenceStatement(constructor, jAttribute)) {
                addNewStatementToConstructor(constructor, jAttribute, jClass.fields, constructor.parameters)
            }
        }
    }
   
   /**
    * (taken from tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaUtils, but modified)
    */
   def static addNewStatementToConstructor(Constructor constructor, Field jAttribute,
        Field[] fieldsToUseAsArgument, Parameter[] parametersToUseAsArgument) {
        val selfReference = createSelfReferenceToAttribute(jAttribute)
        val newConstructorCall = createNewConstructorCall(jAttribute, fieldsToUseAsArgument, parametersToUseAsArgument)
        val assignment = createAssignmentExpression(selfReference, 
            OperatorsFactory.eINSTANCE.createAssignment, newConstructorCall)
        constructor.statements += wrapExpressionInExpressionStatement(assignment)
        return newConstructorCall
    }
    
    /**
     * 
     * Updates the Arguments of a Constructor Call of a given field inside a constructor A. First, it retrieves
     * the constructor B of the type of the field. Then it tries to match the needed parameter
     * of this constructor B with the parameter of the constructor A by their type. The parameters of the 
     * constructor A is given by parametersToUseAsArguments. If there is a match, the parameterToUseAsArgument
     * will be used as argument for the needed parameters. Otherwise it will try to match the needed parameters
     * with the given fieldsToUseAsArguments.
     * If no match could could be found, the needed Parameters becomes null.
     * 
     * constructorA (parametersToUseAsArguments) {
     *     this.jAttribute = new ConstructorB(neededParameters);
     * }
     * 
     * (taken from tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaUtils)
     * 
     * @param jAttribute the field
     */
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
    
    
    /**
     * 
     * (taken from tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaUtils)
     */
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
     * Creates leftSide <AssignmentOperator> rightSide
     */
    def static AssignmentExpression createAssignmentExpression(AssignmentExpressionChild leftSide, 
        AssignmentOperator operator, Expression rightSide) {
        val assignmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression
        assignmentExpression.child = leftSide
        assignmentExpression.assignmentOperator = operator
        assignmentExpression.value = rightSide
        return assignmentExpression
    }
    
    def static boolean expressionHasAttributeSelfReference(ExpressionStatement expressionStatement, Field jAttribute) {
        if (expressionStatement === null) {
            return false
        }
        if (getAttributeSelfReferenceInExpressionStatement(expressionStatement, jAttribute.name) !== null) {
            return true
        }
        return false
    }
    
    def static getAttributeSelfReferenceInExpressionStatement(ExpressionStatement expressionStatement, String attributeName) {
        if (expressionStatement.expression !== null && expressionStatement.expression instanceof AssignmentExpression) {
            val assignmentExpression = expressionStatement.expression as AssignmentExpression
            if ((assignmentExpression.child as SelfReference).self instanceof This) {
                val selfReference = (assignmentExpression.child as SelfReference).next as IdentifierReference
                if (selfReference.target.name.equals(attributeName)) {
                    return selfReference
                }
                return null
            }
            return null
        }
        return null
        
    }
    
    def static IdentifierReference createIdentifierReference(ReferenceableElement elem) {
        val reference = ReferencesFactory.eINSTANCE.createIdentifierReference
        reference.target = EcoreUtil.copy(elem)
        return reference
    }
    
    /**
     * thenStatement and elseStatement can be null
     */
    def static Condition createCondition(Expression ifCondition, Statement thenStatement, Statement elseStatement) {
        if (ifCondition === null) {
            throw new IllegalArgumentException("Cannot create If block with 'null' as condition")
        }
        val condition = StatementsFactory.eINSTANCE.createCondition
        condition.condition = ifCondition
        if (condition !== null) {
            condition.statement = thenStatement
        }
        if (elseStatement !== null) {
            condition.elseStatement = elseStatement
        }
        return condition
    }
    
    /**
     * Creates leftSide <EqualityOperator> rightSide
     * EqualityOperator can be equal or notEqual
     */
    def static EqualityExpression createBinaryEqualityExpression(EqualityExpressionChild leftSide, EqualityOperator eqOperator, 
        EqualityExpressionChild rightSide) {
        val eqExpression = ExpressionsFactory.eINSTANCE.createEqualityExpression
        eqExpression.children += leftSide
        eqExpression.children += rightSide
        eqExpression.equalityOperators += eqOperator
        return eqExpression
    }
    
    def static ExpressionStatement wrapExpressionInExpressionStatement(Expression expressionToWrap) {
        val expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement
        expressionStatement.expression = expressionToWrap
        return expressionStatement
    }
}