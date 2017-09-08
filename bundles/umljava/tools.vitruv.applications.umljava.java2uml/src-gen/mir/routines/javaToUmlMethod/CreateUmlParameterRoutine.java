package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parametrizable;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parametrizable jMeth, final OrdinaryParameter jParam, final Operation uOperation, final Parameter uParam) {
      return uParam;
    }
    
    public void update0Element(final Parametrizable jMeth, final OrdinaryParameter jParam, final Operation uOperation, final Parameter uParam) {
      EList<Parameter> _ownedParameters = uOperation.getOwnedParameters();
      _ownedParameters.add(uParam);
    }
    
    public void updateUParamElement(final Parametrizable jMeth, final OrdinaryParameter jParam, final Operation uOperation, final Parameter uParam) {
      uParam.setName(jParam.getName());
    }
    
    public EObject getElement2(final Parametrizable jMeth, final OrdinaryParameter jParam, final Operation uOperation, final Parameter uParam) {
      return jParam;
    }
    
    public EObject getElement3(final Parametrizable jMeth, final OrdinaryParameter jParam, final Operation uOperation, final Parameter uParam) {
      return uOperation;
    }
    
    public EObject getCorrepondenceSourceUOperation(final Parametrizable jMeth, final OrdinaryParameter jParam) {
      return jMeth;
    }
  }
  
  public CreateUmlParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parametrizable jMeth, final OrdinaryParameter jParam) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.CreateUmlParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.jParam = jParam;
  }
  
  private Parametrizable jMeth;
  
  private OrdinaryParameter jParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlParameterRoutine with input:");
    getLogger().debug("   jMeth: " + this.jMeth);
    getLogger().debug("   jParam: " + this.jParam);
    
    org.eclipse.uml2.uml.Operation uOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUOperation(jMeth, jParam), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uOperation == null) {
    	return false;
    }
    registerObjectUnderModification(uOperation);
    org.eclipse.uml2.uml.Parameter uParam = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(uParam);
    userExecution.updateUParamElement(jMeth, jParam, uOperation, uParam);
    
    addCorrespondenceBetween(userExecution.getElement1(jMeth, jParam, uOperation, uParam), userExecution.getElement2(jMeth, jParam, uOperation, uParam), "");
    
    // val updatedElement userExecution.getElement3(jMeth, jParam, uOperation, uParam);
    userExecution.update0Element(jMeth, jParam, uOperation, uParam);
    
    postprocessElements();
    
    return true;
  }
}
