package mir.routines.javaToUmlmethod;

import java.io.IOException;
import mir.routines.javaToUmlmethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.applications.umljava.util.JavaUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeUmlReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeUmlReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCustomType(final Method jMeth, final TypeReference jType, final Operation uOperation) {
      Classifier _classifierFromTypeReference = JavaUtil.getClassifierFromTypeReference(jType);
      return _classifierFromTypeReference;
    }
    
    public EObject getElement1(final Method jMeth, final TypeReference jType, final Operation uOperation, final org.eclipse.uml2.uml.Class customType) {
      return uOperation;
    }
    
    public void update0Element(final Method jMeth, final TypeReference jType, final Operation uOperation, final org.eclipse.uml2.uml.Class customType) {
      uOperation.setType(JavaToUmlHelper.getUmlType(jType, customType, JavaToUmlHelper.getUmlModel(this.correspondenceModel, this.userInteracting)));
    }
    
    public EObject getCorrepondenceSourceUOperation(final Method jMeth, final TypeReference jType) {
      return jMeth;
    }
  }
  
  public ChangeUmlReturnTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method jMeth, final TypeReference jType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlmethod.ChangeUmlReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlmethod.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.jType = jType;
  }
  
  private Method jMeth;
  
  private TypeReference jType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlReturnTypeRoutine with input:");
    getLogger().debug("   Method: " + this.jMeth);
    getLogger().debug("   TypeReference: " + this.jType);
    
    Operation uOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUOperation(jMeth, jType), // correspondence source supplier
    	Operation.class,
    	(Operation _element) -> true, // correspondence precondition checker
    	null);
    if (uOperation == null) {
    	return;
    }
    registerObjectUnderModification(uOperation);
    org.eclipse.uml2.uml.Class customType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomType(jMeth, jType, uOperation), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customType);
    // val updatedElement userExecution.getElement1(jMeth, jType, uOperation, customType);
    userExecution.update0Element(jMeth, jType, uOperation, customType);
    
    postprocessElements();
  }
}