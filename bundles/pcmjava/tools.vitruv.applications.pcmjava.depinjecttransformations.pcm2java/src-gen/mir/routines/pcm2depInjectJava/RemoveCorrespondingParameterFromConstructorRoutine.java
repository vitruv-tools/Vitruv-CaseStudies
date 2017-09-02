package mir.routines.pcm2depInjectJava;

import java.io.IOException;
import mir.routines.pcm2depInjectJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCorrespondingParameterFromConstructorRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveCorrespondingParameterFromConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Constructor ctor, final NamedElement correspondenceSource, final OrdinaryParameter param) {
      return param;
    }
    
    public boolean getCorrespondingModelElementsPreconditionParam(final Constructor ctor, final NamedElement correspondenceSource, final OrdinaryParameter param) {
      boolean _contains = ctor.getParameters().contains(param);
      return _contains;
    }
    
    public EObject getCorrepondenceSourceParam(final Constructor ctor, final NamedElement correspondenceSource) {
      return correspondenceSource;
    }
  }
  
  public RemoveCorrespondingParameterFromConstructorRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Constructor ctor, final NamedElement correspondenceSource) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2depInjectJava.RemoveCorrespondingParameterFromConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(getExecutionState(), this);
    this.ctor = ctor;this.correspondenceSource = correspondenceSource;
  }
  
  private Constructor ctor;
  
  private NamedElement correspondenceSource;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondingParameterFromConstructorRoutine with input:");
    getLogger().debug("   ctor: " + this.ctor);
    getLogger().debug("   correspondenceSource: " + this.correspondenceSource);
    
    org.emftext.language.java.parameters.OrdinaryParameter param = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceParam(ctor, correspondenceSource), // correspondence source supplier
    	org.emftext.language.java.parameters.OrdinaryParameter.class,
    	(org.emftext.language.java.parameters.OrdinaryParameter _element) -> userExecution.getCorrespondingModelElementsPreconditionParam(ctor, correspondenceSource, _element), // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (param == null) {
    	return false;
    }
    registerObjectUnderModification(param);
    deleteObject(userExecution.getElement1(ctor, correspondenceSource, param));
    
    postprocessElements();
    
    return true;
  }
}
