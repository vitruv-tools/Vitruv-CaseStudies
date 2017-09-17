package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.emftext.language.java.members.EnumConstant;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaEnumConstantRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteJavaEnumConstantRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final EnumerationLiteral uLiteral, final EnumConstant jConst) {
      return jConst;
    }
    
    public EObject getCorrepondenceSourceJConst(final EnumerationLiteral uLiteral) {
      return uLiteral;
    }
  }
  
  public DeleteJavaEnumConstantRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EnumerationLiteral uLiteral) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.DeleteJavaEnumConstantRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.uLiteral = uLiteral;
  }
  
  private EnumerationLiteral uLiteral;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaEnumConstantRoutine with input:");
    getLogger().debug("   uLiteral: " + this.uLiteral);
    
    org.emftext.language.java.members.EnumConstant jConst = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJConst(uLiteral), // correspondence source supplier
    	org.emftext.language.java.members.EnumConstant.class,
    	(org.emftext.language.java.members.EnumConstant _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jConst == null) {
    	return false;
    }
    registerObjectUnderModification(jConst);
    deleteObject(userExecution.getElement1(uLiteral, jConst));
    
    postprocessElements();
    
    return true;
  }
}
