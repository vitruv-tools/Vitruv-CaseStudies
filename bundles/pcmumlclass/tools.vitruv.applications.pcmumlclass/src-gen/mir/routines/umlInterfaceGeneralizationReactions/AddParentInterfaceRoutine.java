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
public class AddParentInterfaceRoutine extends AbstractRepairRoutineRealization {
  private AddParentInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Interface umlInterface, final Classifier umlNewParent, final OperationInterface pcmInterface, final OperationInterface pcmNewParent, @Extension final RoutinesFacade _routinesFacade) {
      boolean _contains = pcmInterface.getParentInterfaces__Interface().contains(pcmNewParent);
      boolean _not = (!_contains);
      if (_not) {
        EList<org.palladiosimulator.pcm.repository.Interface> _parentInterfaces__Interface = pcmInterface.getParentInterfaces__Interface();
        _parentInterfaces__Interface.add(pcmNewParent);
      }
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Interface umlInterface, final Classifier umlNewParent) {
      return umlInterface;
    }
    
    public String getRetrieveTag1(final Interface umlInterface, final Classifier umlNewParent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag2(final Interface umlInterface, final Classifier umlNewParent, final OperationInterface pcmInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getCorrepondenceSourcePcmNewParent(final Interface umlInterface, final Classifier umlNewParent, final OperationInterface pcmInterface) {
      return umlNewParent;
    }
  }
  
  public AddParentInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface, final Classifier umlNewParent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInterfaceGeneralizationReactions.AddParentInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlInterface = umlInterface;this.umlNewParent = umlNewParent;
  }
  
  private Interface umlInterface;
  
  private Classifier umlNewParent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddParentInterfaceRoutine with input:");
    getLogger().debug("   umlInterface: " + this.umlInterface);
    getLogger().debug("   umlNewParent: " + this.umlNewParent);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlInterface, umlNewParent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlInterface, umlNewParent), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    org.palladiosimulator.pcm.repository.OperationInterface pcmNewParent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmNewParent(umlInterface, umlNewParent, pcmInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlInterface, umlNewParent, pcmInterface), 
    	false // asserted
    	);
    if (pcmNewParent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmNewParent);
    userExecution.executeAction1(umlInterface, umlNewParent, pcmInterface, pcmNewParent, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
