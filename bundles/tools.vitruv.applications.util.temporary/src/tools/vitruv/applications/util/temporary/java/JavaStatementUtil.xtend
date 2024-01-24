package tools.vitruv.applications.util.temporary.java

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.mdsd.jamopp.model.java.expressions.AssignmentExpression
import tools.mdsd.jamopp.model.java.expressions.AssignmentExpressionChild
import tools.mdsd.jamopp.model.java.expressions.EqualityExpression
import tools.mdsd.jamopp.model.java.expressions.EqualityExpressionChild
import tools.mdsd.jamopp.model.java.expressions.Expression
import tools.mdsd.jamopp.model.java.expressions.ExpressionsFactory
import tools.mdsd.jamopp.model.java.literals.LiteralsFactory
import tools.mdsd.jamopp.model.java.literals.This
import tools.mdsd.jamopp.model.java.members.Field
import tools.mdsd.jamopp.model.java.operators.AssignmentOperator
import tools.mdsd.jamopp.model.java.operators.EqualityOperator
import tools.mdsd.jamopp.model.java.references.IdentifierReference
import tools.mdsd.jamopp.model.java.references.ReferenceableElement
import tools.mdsd.jamopp.model.java.references.ReferencesFactory
import tools.mdsd.jamopp.model.java.references.SelfReference
import tools.mdsd.jamopp.model.java.statements.Condition
import tools.mdsd.jamopp.model.java.statements.ExpressionStatement
import tools.mdsd.jamopp.model.java.statements.Return
import tools.mdsd.jamopp.model.java.statements.Statement
import tools.mdsd.jamopp.model.java.statements.StatementsFactory

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
