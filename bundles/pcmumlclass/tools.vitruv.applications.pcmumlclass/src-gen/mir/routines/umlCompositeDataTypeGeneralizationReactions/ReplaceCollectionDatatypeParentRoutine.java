package mir.routines.umlCompositeDataTypeGeneralizationReactions;

import java.io.IOException;
import mir.routines.umlCompositeDataTypeGeneralizationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceCollectionDatatypeParentRoutine extends AbstractRepairRoutineRealization {
  private ReplaceCollectionDatatypeParentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final Generalization gen, final Classifier umlNewParent, final Classifier umlOldParent) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeDataType(final Generalization gen, final Classifier umlNewParent, final Classifier umlOldParent) {
      Classifier _specific = gen.getSpecific();
      return _specific;
    }
    
    public void callRoutine1(final Generalization gen, final Classifier umlNewParent, final Classifier umlOldParent, final CompositeDataType pcmCompositeDataType, @Extension final RoutinesFacade _routinesFacade) {
      Classifier _specific = gen.getSpecific();
      _routinesFacade.removeCompositeDatatypeParent(((org.eclipse.uml2.uml.Class) _specific), umlOldParent);
      Classifier _general = gen.getGeneral();
      boolean _tripleEquals = (_general == umlNewParent);
      if (_tripleEquals) {
        Classifier _specific_1 = gen.getSpecific();
        _routinesFacade.addCompositeDatatypeParent(((org.eclipse.uml2.uml.Class) _specific_1), umlNewParent);
      }
    }
  }
  
  public ReplaceCollectionDatatypeParentRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Generalization gen, final Classifier umlNewParent, final Classifier umlOldParent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlCompositeDataTypeGeneralizationReactions.ReplaceCollectionDatatypeParentRoutine.ActionUserExecution(getExecutionState(), this);
    this.gen = gen;this.umlNewParent = umlNewParent;this.umlOldParent = umlOldParent;
  }
  
  private Generalization gen;
  
  private Classifier umlNewParent;
  
  private Classifier umlOldParent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceCollectionDatatypeParentRoutine with input:");
    getLogger().debug("   gen: " + this.gen);
    getLogger().debug("   umlNewParent: " + this.umlNewParent);
    getLogger().debug("   umlOldParent: " + this.umlOldParent);
    
    org.palladiosimulator.pcm.repository.CompositeDataType pcmCompositeDataType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeDataType(gen, umlNewParent, umlOldParent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(gen, umlNewParent, umlOldParent), 
    	false // asserted
    	);
    if (pcmCompositeDataType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCompositeDataType);
    userExecution.callRoutine1(gen, umlNewParent, umlOldParent, pcmCompositeDataType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
