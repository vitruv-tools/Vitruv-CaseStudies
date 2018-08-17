package mir.routines.pcmInterfaceReactions;

import java.io.IOException;
import mir.routines.pcmInterfaceReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveParentInterfaceFromCorrespondingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RemoveParentInterfaceFromCorrespondingInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlParentInterface(final OperationInterface pcmInterface, final OperationInterface pcmParentInterface, final Interface umlInterface) {
      return pcmParentInterface;
    }
    
    public void executeAction1(final OperationInterface pcmInterface, final OperationInterface pcmParentInterface, final Interface umlInterface, final Interface umlParentInterface, @Extension final RoutinesFacade _routinesFacade) {
      final Function1<Generalization, Boolean> _function = (Generalization it) -> {
        Classifier _general = it.getGeneral();
        return Boolean.valueOf((_general == umlParentInterface));
      };
      Generalization _findFirst = IterableExtensions.<Generalization>findFirst(umlInterface.getGeneralizations(), _function);
      if (_findFirst!=null) {
        _findFirst.destroy();
      }
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
  }
  
  public RemoveParentInterfaceFromCorrespondingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface pcmInterface, final OperationInterface pcmParentInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInterfaceReactions.RemoveParentInterfaceFromCorrespondingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.pcmParentInterface = pcmParentInterface;
  }
  
  private OperationInterface pcmInterface;
  
  private OperationInterface pcmParentInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveParentInterfaceFromCorrespondingInterfaceRoutine with input:");
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
    userExecution.executeAction1(pcmInterface, pcmParentInterface, umlInterface, umlParentInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
