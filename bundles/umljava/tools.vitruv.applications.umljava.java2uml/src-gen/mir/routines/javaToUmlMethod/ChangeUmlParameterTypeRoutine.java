package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeUmlParameterTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeUmlParameterTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUParam(final OrdinaryParameter jParam, final TypeReference jType) {
      return jParam;
    }
    
    public EObject getElement1(final OrdinaryParameter jParam, final TypeReference jType, final Parameter uParam) {
      return uParam;
    }
    
    public void update0Element(final OrdinaryParameter jParam, final TypeReference jType, final Parameter uParam) {
      uParam.setType(JavaToUmlHelper.getUmlType(jType, JavaToUmlHelper.getUmlModel(this.changePropagationObservable, this.correspondenceModel, this.userInteracting), this.correspondenceModel));
    }
  }
  
  public ChangeUmlParameterTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OrdinaryParameter jParam, final TypeReference jType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.ChangeUmlParameterTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.jParam = jParam;this.jType = jType;
  }
  
  private OrdinaryParameter jParam;
  
  private TypeReference jType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlParameterTypeRoutine with input:");
    getLogger().debug("   jParam: " + this.jParam);
    getLogger().debug("   jType: " + this.jType);
    
    org.eclipse.uml2.uml.Parameter uParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUParam(jParam, jType), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uParam == null) {
    	return false;
    }
    registerObjectUnderModification(uParam);
    // val updatedElement userExecution.getElement1(jParam, jType, uParam);
    userExecution.update0Element(jParam, jType, uParam);
    
    postprocessElements();
    
    return true;
  }
}
