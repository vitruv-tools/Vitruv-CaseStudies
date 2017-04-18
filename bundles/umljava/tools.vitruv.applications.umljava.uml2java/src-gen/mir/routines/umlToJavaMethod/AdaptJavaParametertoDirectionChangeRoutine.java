package mir.routines.umlToJavaMethod;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypesFactory;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AdaptJavaParametertoDirectionChangeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AdaptJavaParametertoDirectionChangeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJMethod(final Operation uOperation, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection) {
      return uOperation;
    }
    
    public EObject getElement1(final Operation uOperation, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection, final Method jMethod, final org.emftext.language.java.classifiers.Class customTypeClass) {
      return jMethod;
    }
    
    public void update0Element(final Operation uOperation, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection, final Method jMethod, final org.emftext.language.java.classifiers.Class customTypeClass) {
      boolean _equals = Objects.equal(newDirection, ParameterDirectionKind.RETURN_LITERAL);
      if (_equals) {
        jMethod.setTypeReference(UmlToJavaHelper.createTypeReference(uOperation.getType(), customTypeClass));
      } else {
        boolean _equals_1 = Objects.equal(oldDirection, ParameterDirectionKind.RETURN_LITERAL);
        if (_equals_1) {
          jMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid());
        }
      }
    }
    
    public EObject getCorrepondenceSourceCustomTypeClass(final Operation uOperation, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection, final Method jMethod) {
      Type _type = uOperation.getType();
      return _type;
    }
  }
  
  public AdaptJavaParametertoDirectionChangeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uOperation, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.AdaptJavaParametertoDirectionChangeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uOperation = uOperation;this.oldDirection = oldDirection;this.newDirection = newDirection;
  }
  
  private Operation uOperation;
  
  private ParameterDirectionKind oldDirection;
  
  private ParameterDirectionKind newDirection;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AdaptJavaParametertoDirectionChangeRoutine with input:");
    getLogger().debug("   Operation: " + this.uOperation);
    getLogger().debug("   ParameterDirectionKind: " + this.oldDirection);
    getLogger().debug("   ParameterDirectionKind: " + this.newDirection);
    
    Method jMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJMethod(uOperation, oldDirection, newDirection), // correspondence source supplier
    	Method.class,
    	(Method _element) -> true, // correspondence precondition checker
    	null);
    if (jMethod == null) {
    	return;
    }
    registerObjectUnderModification(jMethod);
    org.emftext.language.java.classifiers.Class customTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomTypeClass(uOperation, oldDirection, newDirection, jMethod), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customTypeClass);
    // val updatedElement userExecution.getElement1(uOperation, oldDirection, newDirection, jMethod, customTypeClass);
    userExecution.update0Element(uOperation, oldDirection, newDirection, jMethod, customTypeClass);
    
    postprocessElements();
  }
}
