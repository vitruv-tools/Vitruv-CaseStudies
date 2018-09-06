package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.UMLPackage;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeUmlParameterTypeRoutine extends AbstractRepairRoutineRealization {
  private ChangeUmlParameterTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUParam(final OrdinaryParameter jParam, final TypeReference jType) {
      return jParam;
    }
    
    public EObject getCorrepondenceSourceUModel(final OrdinaryParameter jParam, final TypeReference jType, final Parameter uParam) {
      return UMLPackage.Literals.MODEL;
    }
    
    public EObject getElement1(final OrdinaryParameter jParam, final TypeReference jType, final Parameter uParam, final Model uModel) {
      return uParam;
    }
    
    public void update0Element(final OrdinaryParameter jParam, final TypeReference jType, final Parameter uParam, final Model uModel) {
      UmlJavaTypePropagationHelper.propagateTypeChangeToTypedMultiplicityElement(uParam, uParam, jParam, this.correspondenceModel);
    }
  }
  
  public ChangeUmlParameterTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OrdinaryParameter jParam, final TypeReference jType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.ChangeUmlParameterTypeRoutine.ActionUserExecution(getExecutionState(), this);
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
    org.eclipse.uml2.uml.Model uModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUModel(jParam, jType, uParam), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uModel == null) {
    	return false;
    }
    registerObjectUnderModification(uModel);
    // val updatedElement userExecution.getElement1(jParam, jType, uParam, uModel);
    userExecution.update0Element(jParam, jType, uParam, uModel);
    
    postprocessElements();
    
    return true;
  }
}
