package tools.vitruv.applications.util.temporary.java;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import java.util.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.expressions.AssignmentExpression;
import org.emftext.language.java.expressions.AssignmentExpressionChild;
import org.emftext.language.java.expressions.EqualityExpression;
import org.emftext.language.java.expressions.EqualityExpressionChild;
import org.emftext.language.java.expressions.Expression;
import org.emftext.language.java.expressions.ExpressionsFactory;
import org.emftext.language.java.literals.LiteralsFactory;
import org.emftext.language.java.literals.Self;
import org.emftext.language.java.literals.This;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.operators.AssignmentOperator;
import org.emftext.language.java.operators.EqualityOperator;
import org.emftext.language.java.references.IdentifierReference;
import org.emftext.language.java.references.Reference;
import org.emftext.language.java.references.ReferenceableElement;
import org.emftext.language.java.references.ReferencesFactory;
import org.emftext.language.java.references.SelfReference;
import org.emftext.language.java.statements.Condition;
import org.emftext.language.java.statements.ExpressionStatement;
import org.emftext.language.java.statements.Return;
import org.emftext.language.java.statements.Statement;
import org.emftext.language.java.statements.StatementsFactory;

/**
 * Util class for the creation and retrieving of statements.
 *
 * @author Fei
 */
@Utility
@SuppressWarnings("all")
public final class JavaStatementUtil {
    /**
     * Creates:
     *
     * return <returnValue>
     *
     * @param returnValue the expression that should placed behind the return key word
     */
    public static Return createReturnStatement(final Expression returnValue) {
        final Return returnStatement = StatementsFactory.eINSTANCE.createReturn();
        returnStatement.setReturnValue(returnValue);
        return returnStatement;
    }

    /**
     * Creates a <this.jAttributename> expression
     */
    public static SelfReference createSelfReferenceToAttribute(final Field jAttribute) {
        final SelfReference selfReference = ReferencesFactory.eINSTANCE.createSelfReference();
        selfReference.setSelf(LiteralsFactory.eINSTANCE.createThis());
        final IdentifierReference fieldReference = ReferencesFactory.eINSTANCE.createIdentifierReference();
        fieldReference.setTarget(jAttribute);
        selfReference.setNext(EcoreUtil.<IdentifierReference>copy(fieldReference));
        return selfReference;
    }

    /**
     * Creates leftSide <AssignmentOperator> rightSide
     */
    public static AssignmentExpression createAssignmentExpression(final AssignmentExpressionChild leftSide, final AssignmentOperator operator, final Expression rightSide) {
        final AssignmentExpression assignmentExpression = ExpressionsFactory.eINSTANCE.createAssignmentExpression();
        assignmentExpression.setChild(leftSide);
        assignmentExpression.setAssignmentOperator(operator);
        assignmentExpression.setValue(rightSide);
        return assignmentExpression;
    }

    public static boolean expressionHasAttributeSelfReference(final ExpressionStatement expressionStatement, final Field jAttribute) {
        if ((expressionStatement == null)) {
            return false;
        }
        IdentifierReference _attributeSelfReferenceInExpressionStatement = JavaStatementUtil.getAttributeSelfReferenceInExpressionStatement(expressionStatement, jAttribute.getName());
        boolean _tripleNotEquals = (_attributeSelfReferenceInExpressionStatement != null);
        if (_tripleNotEquals) {
            return true;
        }
        return false;
    }

    public static IdentifierReference getAttributeSelfReferenceInExpressionStatement(final ExpressionStatement expressionStatement, final String attributeName) {
        if (((expressionStatement.getExpression() != null) && (expressionStatement.getExpression() instanceof AssignmentExpression))) {
            Expression _expression = expressionStatement.getExpression();
            final AssignmentExpression assignmentExpression = ((AssignmentExpression) _expression);
            AssignmentExpressionChild _child = assignmentExpression.getChild();
            Self _self = ((SelfReference) _child).getSelf();
            if ((_self instanceof This)) {
                AssignmentExpressionChild _child_1 = assignmentExpression.getChild();
                Reference _next = ((SelfReference) _child_1).getNext();
                final IdentifierReference selfReference = ((IdentifierReference) _next);
                String _name = selfReference.getTarget().getName();
                boolean _equals = Objects.equals(_name, attributeName);
                if (_equals) {
                    return selfReference;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public static IdentifierReference createIdentifierReference(final ReferenceableElement elem) {
        final IdentifierReference reference = ReferencesFactory.eINSTANCE.createIdentifierReference();
        reference.setTarget(elem);
        return reference;
    }

    /**
     * thenStatement and elseStatement can be null
     */
    public static Condition createCondition(final Expression ifCondition, final Statement thenStatement, final Statement elseStatement) {
        if ((ifCondition == null)) {
            throw new IllegalArgumentException("Cannot create If block with \'null\' as condition");
        }
        final Condition condition = StatementsFactory.eINSTANCE.createCondition();
        condition.setCondition(ifCondition);
        if ((condition != null)) {
            condition.setStatement(thenStatement);
        }
        if ((elseStatement != null)) {
            condition.setElseStatement(elseStatement);
        }
        return condition;
    }

    /**
     * Creates leftSide <EqualityOperator> rightSide
     * EqualityOperator can be equal or notEqual
     */
    public static EqualityExpression createBinaryEqualityExpression(final EqualityExpressionChild leftSide, final EqualityOperator eqOperator, final EqualityExpressionChild rightSide) {
        final EqualityExpression eqExpression = ExpressionsFactory.eINSTANCE.createEqualityExpression();
        EList<EqualityExpressionChild> _children = eqExpression.getChildren();
        _children.add(leftSide);
        EList<EqualityExpressionChild> _children_1 = eqExpression.getChildren();
        _children_1.add(rightSide);
        EList<EqualityOperator> _equalityOperators = eqExpression.getEqualityOperators();
        _equalityOperators.add(eqOperator);
        return eqExpression;
    }

    public static ExpressionStatement wrapExpressionInExpressionStatement(final Expression expressionToWrap) {
        final ExpressionStatement expressionStatement = StatementsFactory.eINSTANCE.createExpressionStatement();
        expressionStatement.setExpression(expressionToWrap);
        return expressionStatement;
    }

    private JavaStatementUtil() {

    }
}
