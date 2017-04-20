package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.applications.umljava.util.JavaUtil;
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
    
    public EObject getCorrepondenceSourceCustomType(final OrdinaryParameter jParam, final TypeReference jType, final Parameter uParam) {
      Classifier _classifierFromTypeReference = JavaUtil.getClassifierFromTypeReference(jType);
      return _classifierFromTypeReference;
    }
    
    public EObject getElement1(final OrdinaryParameter jParam, final TypeReference jType, final Parameter uParam, final org.eclipse.uml2.uml.Class customType) {
      return uParam;
    }
    
    public void update0Element(final OrdinaryParameter jParam, final TypeReference jType, final Parameter uParam, final org.eclipse.uml2.uml.Class customType) {
      uParam.setType(JavaToUmlHelper.getUmlType(jType, customType, JavaToUmlHelper.getUmlModel(this.correspondenceModel, this.userInteracting)));
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
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlParameterTypeRoutine with input:");
    getLogger().debug("   OrdinaryParameter: " + this.jParam);
    getLogger().debug("   TypeReference: " + this.jType);
    
    Parameter uParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUParam(jParam, jType), // correspondence source supplier
    	Parameter.class,
    	(Parameter _element) -> true, // correspondence precondition checker
    	null);
    if (uParam == null) {
    	return;
    }
    registerObjectUnderModification(uParam);
    org.eclipse.uml2.uml.Class customType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomType(jParam, jType, uParam), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customType);
    // val updatedElement userExecution.getElement1(jParam, jType, uParam, customType);
    userExecution.update0Element(jParam, jType, uParam, customType);
    
    postprocessElements();
  }
}
