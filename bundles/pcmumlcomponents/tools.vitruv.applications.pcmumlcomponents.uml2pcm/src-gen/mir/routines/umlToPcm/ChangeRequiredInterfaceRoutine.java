package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class ChangeRequiredInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeRequiredInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Usage umlUsage, final Interface umlInterface, final OperationInterface pcmInterface, final OperationRequiredRole pcmRole) {
      return pcmRole;
    }
    
    public void update0Element(final Usage umlUsage, final Interface umlInterface, final OperationInterface pcmInterface, final OperationRequiredRole pcmRole) {
      int _length = ((Object[])Conversions.unwrapArray(umlUsage.getSuppliers(), Object.class)).length;
      boolean _equals = (_length == 0);
      if (_equals) {
        pcmRole.setRequiredInterface__OperationRequiredRole(null);
      } else {
        int _length_1 = ((Object[])Conversions.unwrapArray(umlUsage.getSuppliers(), Object.class)).length;
        boolean _equals_1 = (_length_1 == 1);
        if (_equals_1) {
          pcmRole.setRequiredInterface__OperationRequiredRole(pcmInterface);
        } else {
          this.userInteracting.showMessage(UserInteractionType.MODAL, "Further interfaces will not be required in the PCM");
        }
      }
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Usage umlUsage, final Interface umlInterface) {
      return umlInterface;
    }
    
    public EObject getCorrepondenceSourcePcmRole(final Usage umlUsage, final Interface umlInterface, final OperationInterface pcmInterface) {
      return umlUsage;
    }
  }
  
  public ChangeRequiredInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Usage umlUsage, final Interface umlInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.ChangeRequiredInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlUsage = umlUsage;this.umlInterface = umlInterface;
  }
  
  private Usage umlUsage;
  
  private Interface umlInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeRequiredInterfaceRoutine with input:");
    getLogger().debug("   umlUsage: " + this.umlUsage);
    getLogger().debug("   umlInterface: " + this.umlInterface);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlUsage, umlInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    org.palladiosimulator.pcm.repository.OperationRequiredRole pcmRole = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRole(umlUsage, umlInterface, pcmInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmRole == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRole);
    // val updatedElement userExecution.getElement1(umlUsage, umlInterface, pcmInterface, pcmRole);
    userExecution.update0Element(umlUsage, umlInterface, pcmInterface, pcmRole);
    
    postprocessElements();
    
    return true;
  }
}
