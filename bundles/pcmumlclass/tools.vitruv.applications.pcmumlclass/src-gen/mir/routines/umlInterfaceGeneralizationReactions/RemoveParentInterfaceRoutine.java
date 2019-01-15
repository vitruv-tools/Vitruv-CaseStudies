package mir.routines.umlInterfaceGeneralizationReactions;

import java.io.IOException;
import mir.routines.umlInterfaceGeneralizationReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveParentInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RemoveParentInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Interface umlInterface, final Classifier umlOldParent, final OperationInterface pcmInterface, final OperationInterface pcmOldParent, @Extension final RoutinesFacade _routinesFacade) {
      boolean _contains = pcmInterface.getParentInterfaces__Interface().contains(pcmOldParent);
      if (_contains) {
        EList<org.palladiosimulator.pcm.repository.Interface> _parentInterfaces__Interface = pcmInterface.getParentInterfaces__Interface();
        _parentInterfaces__Interface.remove(pcmOldParent);
      }
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Interface umlInterface, final Classifier umlOldParent) {
      return umlInterface;
    }
    
    public String getRetrieveTag1(final Interface umlInterface, final Classifier umlOldParent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag2(final Interface umlInterface, final Classifier umlOldParent, final OperationInterface pcmInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getCorrepondenceSourcePcmOldParent(final Interface umlInterface, final Classifier umlOldParent, final OperationInterface pcmInterface) {
      return umlOldParent;
    }
  }
  
  public RemoveParentInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface, final Classifier umlOldParent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInterfaceGeneralizationReactions.RemoveParentInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlInterface = umlInterface;this.umlOldParent = umlOldParent;
  }
  
  private Interface umlInterface;
  
  private Classifier umlOldParent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveParentInterfaceRoutine with input:");
    getLogger().debug("   umlInterface: " + this.umlInterface);
    getLogger().debug("   umlOldParent: " + this.umlOldParent);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlInterface, umlOldParent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlInterface, umlOldParent), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    org.palladiosimulator.pcm.repository.OperationInterface pcmOldParent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmOldParent(umlInterface, umlOldParent, pcmInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlInterface, umlOldParent, pcmInterface), 
    	false // asserted
    	);
    if (pcmOldParent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmOldParent);
    userExecution.executeAction1(umlInterface, umlOldParent, pcmInterface, pcmOldParent, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
