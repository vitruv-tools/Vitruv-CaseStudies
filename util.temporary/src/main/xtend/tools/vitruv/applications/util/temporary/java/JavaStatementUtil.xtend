package tools.vitruv.applications.util.temporary.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.expressions.AssignmentExpression
import org.emftext.language.java.expressions.AssignmentExpressionChild
import org.emftext.language.java.expressions.EqualityExpression
import org.emftext.language.java.expressions.EqualityExpressionChild
import org.emftext.language.java.expressions.Expression
import org.emftext.language.java.expressions.ExpressionsFactory
import org.emftext.language.java.literals.LiteralsFactory
import org.emftext.language.java.literals.This
import org.emftext.language.java.members.Field
import org.emftext.language.java.operators.AssignmentOperator
import org.emftext.language.java.operators.EqualityOperator
import org.emftext.language.java.references.IdentifierReference
import org.emftext.language.java.references.ReferenceableElement
import org.emftext.language.java.references.ReferencesFactory
import org.emftext.language.java.references.SelfReference
import org.emftext.language.java.statements.Condition
import org.emftext.language.java.statements.ExpressionStatement
import org.emftext.language.java.statements.Return
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.statements.StatementsFactory

/**
 * Util class for the creation and retrieving of statements.
 * 
 * @author Fei
 */
@Utility
class JavaStatementUtil {

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
        fieldReference.target = jAttribute
        selfReference.next = EcoreUtil.copy(fieldReference)
        return selfReference
    }

    /**
     * Creates leftSide <AssignmentOperator> rightSide
     */
    def static AssignmentExpression createAssignmentExpression(AssignmentExpressionChild leftSide, AssignmentOperator operator, Expression rightSide) {
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
                if (selfReference.target.name == attributeName) {
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
        reference.target = elem
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
