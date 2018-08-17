package mir.routines.pcmAssemblyContextReactions;

import java.io.IOException;
import mir.routines.pcmAssemblyContextReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingAssemblyContextPropertyRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final AssemblyContext pcmAssembly, final Property umlProperty) {
      return pcmAssembly;
    }
    
    public String getRetrieveTag1(final AssemblyContext pcmAssembly) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlProperty(final AssemblyContext pcmAssembly) {
      return pcmAssembly;
    }
    
    public EObject getElement2(final AssemblyContext pcmAssembly, final Property umlProperty) {
      return umlProperty;
    }
    
    public EObject getElement3(final AssemblyContext pcmAssembly, final Property umlProperty) {
      return umlProperty;
    }
    
    public String getTag1(final AssemblyContext pcmAssembly, final Property umlProperty) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
  }
  
  public DeleteCorrespondingAssemblyContextPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AssemblyContext pcmAssembly) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmAssemblyContextReactions.DeleteCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAssembly = pcmAssembly;
  }
  
  private AssemblyContext pcmAssembly;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingAssemblyContextPropertyRoutine with input:");
    getLogger().debug("   pcmAssembly: " + this.pcmAssembly);
    
    org.eclipse.uml2.uml.Property umlProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlProperty(pcmAssembly), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmAssembly), 
    	false // asserted
    	);
    if (umlProperty == null) {
    	return false;
    }
    registerObjectUnderModification(umlProperty);
    removeCorrespondenceBetween(userExecution.getElement1(pcmAssembly, umlProperty), userExecution.getElement2(pcmAssembly, umlProperty), userExecution.getTag1(pcmAssembly, umlProperty));
    
    deleteObject(userExecution.getElement3(pcmAssembly, umlProperty));
    
    postprocessElements();
    
    return true;
  }
}
