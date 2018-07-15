package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.expressions.AssignmentExpression;
import org.emftext.language.java.expressions.AssignmentExpressionChild;
import org.emftext.language.java.expressions.Expression;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.references.IdentifierReference;
import org.emftext.language.java.references.Reference;
import org.emftext.language.java.references.ReferenceableElement;
import org.emftext.language.java.references.SelfReference;
import org.emftext.language.java.statements.ExpressionStatement;
import org.emftext.language.java.statements.Statement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveParameterToFieldAssignmentFromConstructorRoutine extends AbstractRepairRoutineRealization {
  private RemoveParameterToFieldAssignmentFromConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Constructor ctor, final String fieldName) {
      return ctor;
    }
    
    public void update0Element(final Constructor ctor, final String fieldName) {
      EList<Statement> _statements = ctor.getStatements();
      for (final Statement statement : _statements) {
        if ((statement instanceof ExpressionStatement)) {
          final Expression assignmentExpression = ((ExpressionStatement)statement).getExpression();
          if ((assignmentExpression instanceof AssignmentExpression)) {
            final AssignmentExpressionChild selfReference = ((AssignmentExpression)assignmentExpression).getChild();
            if ((selfReference instanceof SelfReference)) {
              final Reference fieldReference = ((SelfReference)selfReference).getNext();
              if ((fieldReference instanceof IdentifierReference)) {
                final ReferenceableElement field = ((IdentifierReference)fieldReference).getTarget();
                if ((field instanceof Field)) {
                  boolean _equals = ((Field)field).getName().equals(fieldName);
                  if (_equals) {
                    ctor.getStatements().remove(statement);
                    return;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  public RemoveParameterToFieldAssignmentFromConstructorRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Constructor ctor, final String fieldName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.RemoveParameterToFieldAssignmentFromConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.ctor = ctor;this.fieldName = fieldName;
  }
  
  private Constructor ctor;
  
  private String fieldName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveParameterToFieldAssignmentFromConstructorRoutine with input:");
    getLogger().debug("   ctor: " + this.ctor);
    getLogger().debug("   fieldName: " + this.fieldName);
    
    // val updatedElement userExecution.getElement1(ctor, fieldName);
    userExecution.update0Element(ctor, fieldName);
    
    postprocessElements();
    
    return true;
  }
}
