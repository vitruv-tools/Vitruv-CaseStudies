package mir.routines.pcmInterfaceReactions;

import java.io.IOException;
import mir.routines.pcmInterfaceReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddParentInterfaceToCorrespondingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private AddParentInterfaceToCorrespondingInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlParentInterface(final OperationInterface pcmInterface, final OperationInterface pcmParentInterface, final Interface umlInterface) {
      return pcmParentInterface;
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationInterface pcmInterface, final OperationInterface pcmParentInterface) {
      return pcmInterface;
    }
    
    public String getRetrieveTag1(final OperationInterface pcmInterface, final OperationInterface pcmParentInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag2(final OperationInterface pcmInterface, final OperationInterface pcmParentInterface, final Interface umlInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public void updateGeneralizationElement(final OperationInterface pcmInterface, final OperationInterface pcmParentInterface, final Interface umlInterface, final Interface umlParentInterface, final Generalization generalization) {
      generalization.setSpecific(umlInterface);
      generalization.setGeneral(umlParentInterface);
    }
  }
  
  public AddParentInterfaceToCorrespondingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface pcmInterface, final OperationInterface pcmParentInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInterfaceReactions.AddParentInterfaceToCorrespondingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.pcmParentInterface = pcmParentInterface;
  }
  
  private OperationInterface pcmInterface;
  
  private OperationInterface pcmParentInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddParentInterfaceToCorrespondingInterfaceRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   pcmParentInterface: " + this.pcmParentInterface);
    
    org.eclipse.uml2.uml.Interface umlInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInterface(pcmInterface, pcmParentInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmInterface, pcmParentInterface), 
    	false // asserted
    	);
    if (umlInterface == null) {
    	return false;
    }
    registerObjectUnderModification(umlInterface);
    org.eclipse.uml2.uml.Interface umlParentInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParentInterface(pcmInterface, pcmParentInterface, umlInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmInterface, pcmParentInterface, umlInterface), 
    	false // asserted
    	);
    if (umlParentInterface == null) {
    	return false;
    }
    registerObjectUnderModification(umlParentInterface);
    org.eclipse.uml2.uml.Generalization generalization = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createGeneralization();
    notifyObjectCreated(generalization);
    userExecution.updateGeneralizationElement(pcmInterface, pcmParentInterface, umlInterface, umlParentInterface, generalization);
    
    postprocessElements();
    
    return true;
  }
}
