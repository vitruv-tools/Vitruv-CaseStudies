package mir.routines.umlInterfaceGeneralizationReactions;

import java.io.IOException;
import mir.routines.umlInterfaceGeneralizationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceParentInterfaceRoutine extends AbstractRepairRoutineRealization {
  private ReplaceParentInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Generalization gen, final Classifier newParent, final Classifier oldParent) {
      Classifier _specific = gen.getSpecific();
      return _specific;
    }
    
    public String getRetrieveTag1(final Generalization gen, final Classifier newParent, final Classifier oldParent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public void callRoutine1(final Generalization gen, final Classifier newParent, final Classifier oldParent, final OperationInterface pcmInterface, @Extension final RoutinesFacade _routinesFacade) {
      Classifier _specific = gen.getSpecific();
      _routinesFacade.removeParentInterface(((Interface) _specific), oldParent);
      Classifier _general = gen.getGeneral();
      boolean _tripleEquals = (_general == newParent);
      if (_tripleEquals) {
        Classifier _specific_1 = gen.getSpecific();
        _routinesFacade.addParentInterface(((Interface) _specific_1), newParent);
      }
    }
  }
  
  public ReplaceParentInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Generalization gen, final Classifier newParent, final Classifier oldParent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInterfaceGeneralizationReactions.ReplaceParentInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.gen = gen;this.newParent = newParent;this.oldParent = oldParent;
  }
  
  private Generalization gen;
  
  private Classifier newParent;
  
  private Classifier oldParent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceParentInterfaceRoutine with input:");
    getLogger().debug("   gen: " + this.gen);
    getLogger().debug("   newParent: " + this.newParent);
    getLogger().debug("   oldParent: " + this.oldParent);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(gen, newParent, oldParent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(gen, newParent, oldParent), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    userExecution.callRoutine1(gen, newParent, oldParent, pcmInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
