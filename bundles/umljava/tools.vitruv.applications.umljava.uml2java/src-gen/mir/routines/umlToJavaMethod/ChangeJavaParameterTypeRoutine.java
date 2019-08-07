package mir.routines.umlToJavaMethod;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.parameters.OrdinaryParameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeJavaParameterTypeRoutine extends AbstractRepairRoutineRealization {
  private ChangeJavaParameterTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJParam(final Parameter uParam, final Type uType) {
      return uParam;
    }
    
    public EObject getCorrepondenceSourceJCustomType(final Parameter uParam, final Type uType, final OrdinaryParameter jParam) {
      return uType;
    }
    
    public void callRoutine1(final Parameter uParam, final Type uType, final OrdinaryParameter jParam, final Optional<ConcreteClassifier> jCustomType, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.umlToJavaTypePropagation.propagateOrdinaryParameterTypeChanged(uParam, jParam, jCustomType.orElse(null));
    }
  }
  
  public ChangeJavaParameterTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter uParam, final Type uType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.ChangeJavaParameterTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.uParam = uParam;this.uType = uType;
  }
  
  private Parameter uParam;
  
  private Type uType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeJavaParameterTypeRoutine with input:");
    getLogger().debug("   uParam: " + this.uParam);
    getLogger().debug("   uType: " + this.uType);
    
    org.emftext.language.java.parameters.OrdinaryParameter jParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJParam(uParam, uType), // correspondence source supplier
    	org.emftext.language.java.parameters.OrdinaryParameter.class,
    	(org.emftext.language.java.parameters.OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jParam == null) {
    	return false;
    }
    registerObjectUnderModification(jParam);
    	Optional<org.emftext.language.java.classifiers.ConcreteClassifier> jCustomType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceJCustomType(uParam, uType, jParam), // correspondence source supplier
    		org.emftext.language.java.classifiers.ConcreteClassifier.class,
    		(org.emftext.language.java.classifiers.ConcreteClassifier _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(jCustomType.isPresent() ? jCustomType.get() : null);
    userExecution.callRoutine1(uParam, uType, jParam, jCustomType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
