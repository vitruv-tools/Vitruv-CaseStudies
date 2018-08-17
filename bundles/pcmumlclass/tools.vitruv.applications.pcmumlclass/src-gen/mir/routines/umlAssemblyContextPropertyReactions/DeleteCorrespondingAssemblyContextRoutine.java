package mir.routines.umlAssemblyContextPropertyReactions;

import java.io.IOException;
import mir.routines.umlAssemblyContextPropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingAssemblyContextRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingAssemblyContextRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
      return pcmAssemblyContext;
    }
    
    public String getRetrieveTag1(final Property umlProperty) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getElement2(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
      return umlProperty;
    }
    
    public EObject getElement3(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
      return pcmAssemblyContext;
    }
    
    public String getTag1(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getCorrepondenceSourcePcmAssemblyContext(final Property umlProperty) {
      return umlProperty;
    }
  }
  
  public DeleteCorrespondingAssemblyContextRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlAssemblyContextPropertyReactions.DeleteCorrespondingAssemblyContextRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;
  }
  
  private Property umlProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingAssemblyContextRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    
    org.palladiosimulator.pcm.core.composition.AssemblyContext pcmAssemblyContext = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmAssemblyContext(umlProperty), // correspondence source supplier
    	org.palladiosimulator.pcm.core.composition.AssemblyContext.class,
    	(org.palladiosimulator.pcm.core.composition.AssemblyContext _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty), 
    	false // asserted
    	);
    if (pcmAssemblyContext == null) {
    	return false;
    }
    registerObjectUnderModification(pcmAssemblyContext);
    removeCorrespondenceBetween(userExecution.getElement1(umlProperty, pcmAssemblyContext), userExecution.getElement2(umlProperty, pcmAssemblyContext), userExecution.getTag1(umlProperty, pcmAssemblyContext));
    
    deleteObject(userExecution.getElement3(umlProperty, pcmAssemblyContext));
    
    postprocessElements();
    
    return true;
  }
}
