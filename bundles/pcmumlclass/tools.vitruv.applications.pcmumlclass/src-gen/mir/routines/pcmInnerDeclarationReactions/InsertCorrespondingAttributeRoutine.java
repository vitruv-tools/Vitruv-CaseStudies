package mir.routines.pcmInnerDeclarationReactions;

import java.io.IOException;
import mir.routines.pcmInnerDeclarationReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingAttributeRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.moveCorrespondingAttribute(pcmAttribute, pcmComposite);
    }
    
    public void callRoutine1(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateCorrespondingAttribute(pcmAttribute, pcmComposite);
    }
  }
  
  public InsertCorrespondingAttributeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInnerDeclarationReactions.InsertCorrespondingAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAttribute = pcmAttribute;this.pcmComposite = pcmComposite;
  }
  
  private InnerDeclaration pcmAttribute;
  
  private CompositeDataType pcmComposite;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingAttributeRoutine with input:");
    getLogger().debug("   pcmAttribute: " + this.pcmAttribute);
    getLogger().debug("   pcmComposite: " + this.pcmComposite);
    
    userExecution.callRoutine1(pcmAttribute, pcmComposite, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmAttribute, pcmComposite, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
