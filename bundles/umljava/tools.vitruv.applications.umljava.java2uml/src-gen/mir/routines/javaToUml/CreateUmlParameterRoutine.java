package mir.routines.javaToUml;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
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
    
    public EObject getElement1(final Method jMeth, final OrdinaryParameter jParam, final Operation uMeth, final Parameter uParam) {
      return uParam;
    }
    
    public void updateUParamElement(final Method jMeth, final OrdinaryParameter jParam, final Operation uMeth, final Parameter uParam) {
      String _name = jParam.getName();
      boolean _notEquals = (!Objects.equal(_name, null));
      if (_notEquals) {
        String _name_1 = jParam.getName();
        uParam.setName(_name_1);
      } else {
        uParam.setName("DefaultUmlParameterName");
      }
      TypeReference _typeReference = jParam.getTypeReference();
      Type _umlType = JavaToUmlHelper.getUmlType(_typeReference);
      uParam.setType(_umlType);
    }
    
    public EObject getElement2(final Method jMeth, final OrdinaryParameter jParam, final Operation uMeth, final Parameter uParam) {
      return jParam;
    }
    
    public EObject getCorrepondenceSourceUMeth(final Method jMeth, final OrdinaryParameter jParam) {
      return jMeth;
    }
  }
  
  public CreateUmlParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method jMeth, final OrdinaryParameter jParam) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.CreateUmlParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.jParam = jParam;
  }
  
  private Method jMeth;
  
  private OrdinaryParameter jParam;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlParameterRoutine with input:");
    getLogger().debug("   Method: " + this.jMeth);
    getLogger().debug("   OrdinaryParameter: " + this.jParam);
    
    Operation uMeth = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUMeth(jMeth, jParam), // correspondence source supplier
    	Operation.class,
    	(Operation _element) -> true, // correspondence precondition checker
    	null);
    if (uMeth == null) {
    	return;
    }
    initializeRetrieveElementState(uMeth);
    Parameter uParam = UMLFactoryImpl.eINSTANCE.createParameter();
    initializeCreateElementState(uParam);
    userExecution.updateUParamElement(jMeth, jParam, uMeth, uParam);
    
    addCorrespondenceBetween(userExecution.getElement1(jMeth, jParam, uMeth, uParam), userExecution.getElement2(jMeth, jParam, uMeth, uParam), "");
    
    postprocessElementStates();
  }
}
