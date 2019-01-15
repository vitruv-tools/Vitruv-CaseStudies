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
public class AddCorrespondenceForExistingAssemblyContextRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingAssemblyContextRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
      return pcmAssemblyContext;
    }
    
    public EObject getCorrepondenceSource1(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
      return pcmAssemblyContext;
    }
    
    public EObject getCorrepondenceSource2(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getElement2(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
      return umlProperty;
    }
    
    public String getTag1(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
  }
  
  public AddCorrespondenceForExistingAssemblyContextRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlAssemblyContextPropertyReactions.AddCorrespondenceForExistingAssemblyContextRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.pcmAssemblyContext = pcmAssemblyContext;
  }
  
  private Property umlProperty;
  
  private AssemblyContext pcmAssemblyContext;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingAssemblyContextRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   pcmAssemblyContext: " + this.pcmAssemblyContext);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlProperty, pcmAssemblyContext), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, pcmAssemblyContext)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(umlProperty, pcmAssemblyContext), // correspondence source supplier
    	org.palladiosimulator.pcm.core.composition.AssemblyContext.class,
    	(org.palladiosimulator.pcm.core.composition.AssemblyContext _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlProperty, pcmAssemblyContext)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(umlProperty, pcmAssemblyContext), userExecution.getElement2(umlProperty, pcmAssemblyContext), userExecution.getTag1(umlProperty, pcmAssemblyContext));
    
    postprocessElements();
    
    return true;
  }
}
