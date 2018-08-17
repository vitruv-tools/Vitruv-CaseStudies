package mir.routines.umlIPREConstructorOperationReactions;

import java.io.IOException;
import mir.routines.umlIPREConstructorOperationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeNameOfCorrespondingIPRE_ConstructorRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingIPRE_ConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOperation, final String newName, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return pcmIPRE;
    }
    
    public void update0Element(final Operation umlOperation, final String newName, final InterfaceProvidingRequiringEntity pcmIPRE) {
      boolean _endsWith = newName.endsWith(DefaultLiterals.IMPLEMENTATION_SUFFIX);
      if (_endsWith) {
        int _length = newName.length();
        int _length_1 = DefaultLiterals.IMPLEMENTATION_SUFFIX.length();
        int _minus = (_length - _length_1);
        pcmIPRE.setEntityName(StringExtensions.toFirstUpper(newName.substring(0, _minus)));
      } else {
        pcmIPRE.setEntityName(newName);
      }
    }
    
    public String getRetrieveTag1(final Operation umlOperation, final String newName) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public EObject getCorrepondenceSourcePcmIPRE(final Operation umlOperation, final String newName) {
      return umlOperation;
    }
  }
  
  public ChangeNameOfCorrespondingIPRE_ConstructorRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlIPREConstructorOperationReactions.ChangeNameOfCorrespondingIPRE_ConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlOperation = umlOperation;this.newName = newName;
  }
  
  private Operation umlOperation;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingIPRE_ConstructorRoutine with input:");
    getLogger().debug("   umlOperation: " + this.umlOperation);
    getLogger().debug("   newName: " + this.newName);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmIPRE = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmIPRE(umlOperation, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlOperation, newName), 
    	false // asserted
    	);
    if (pcmIPRE == null) {
    	return false;
    }
    registerObjectUnderModification(pcmIPRE);
    // val updatedElement userExecution.getElement1(umlOperation, newName, pcmIPRE);
    userExecution.update0Element(umlOperation, newName, pcmIPRE);
    
    postprocessElements();
    
    return true;
  }
}
