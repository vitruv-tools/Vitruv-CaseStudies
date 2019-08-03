package mir.routines.umlToJavaMethod;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypesFactory;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetJavaMethodReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private SetJavaMethodReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJMethod(final Operation uOperation, final Parameter uParam) {
      return uOperation;
    }
    
    public void executeAction1(final Operation uOperation, final Parameter uParam, final Method jMethod, final Optional<ConcreteClassifier> jCustomType, @Extension final RoutinesFacade _routinesFacade) {
      if ((uParam != null)) {
        _routinesFacade.umlToJavaTypePropagation.propagateReturnParameterTypeChanged(uParam, jMethod, jCustomType.orElse(null));
      } else {
        jMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid());
      }
    }
    
    public EObject getCorrepondenceSourceJCustomType(final Operation uOperation, final Parameter uParam, final Method jMethod) {
      Type _type = null;
      if (uParam!=null) {
        _type=uParam.getType();
      }
      return _type;
    }
  }
  
  public SetJavaMethodReturnTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uOperation, final Parameter uParam) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.uOperation = uOperation;this.uParam = uParam;
  }
  
  private Operation uOperation;
  
  private Parameter uParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetJavaMethodReturnTypeRoutine with input:");
    getLogger().debug("   uOperation: " + this.uOperation);
    getLogger().debug("   uParam: " + this.uParam);
    
    org.emftext.language.java.members.Method jMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJMethod(uOperation, uParam), // correspondence source supplier
    	org.emftext.language.java.members.Method.class,
    	(org.emftext.language.java.members.Method _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jMethod == null) {
    	return false;
    }
    registerObjectUnderModification(jMethod);
    	Optional<org.emftext.language.java.classifiers.ConcreteClassifier> jCustomType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceJCustomType(uOperation, uParam, jMethod), // correspondence source supplier
    		org.emftext.language.java.classifiers.ConcreteClassifier.class,
    		(org.emftext.language.java.classifiers.ConcreteClassifier _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(jCustomType.isPresent() ? jCustomType.get() : null);
    userExecution.executeAction1(uOperation, uParam, jMethod, jCustomType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
