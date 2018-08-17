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
public class AddCorrespondenceForExistingAssemblyContextPropertyRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingAssemblyContextPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final AssemblyContext pcmAssembly, final Property umlProperty) {
      return pcmAssembly;
    }
    
    public EObject getCorrepondenceSource1(final AssemblyContext pcmAssembly, final Property umlProperty) {
      return pcmAssembly;
    }
    
    public EObject getCorrepondenceSource2(final AssemblyContext pcmAssembly, final Property umlProperty) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final AssemblyContext pcmAssembly, final Property umlProperty) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public String getRetrieveTag2(final AssemblyContext pcmAssembly, final Property umlProperty) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getElement2(final AssemblyContext pcmAssembly, final Property umlProperty) {
      return umlProperty;
    }
    
    public String getTag1(final AssemblyContext pcmAssembly, final Property umlProperty) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
  }
  
  public AddCorrespondenceForExistingAssemblyContextPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AssemblyContext pcmAssembly, final Property umlProperty) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmAssemblyContextReactions.AddCorrespondenceForExistingAssemblyContextPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAssembly = pcmAssembly;this.umlProperty = umlProperty;
  }
  
  private AssemblyContext pcmAssembly;
  
  private Property umlProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingAssemblyContextPropertyRoutine with input:");
    getLogger().debug("   pcmAssembly: " + this.pcmAssembly);
    getLogger().debug("   umlProperty: " + this.umlProperty);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmAssembly, umlProperty), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmAssembly, umlProperty)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmAssembly, umlProperty), // correspondence source supplier
    	org.palladiosimulator.pcm.core.composition.AssemblyContext.class,
    	(org.palladiosimulator.pcm.core.composition.AssemblyContext _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmAssembly, umlProperty)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmAssembly, umlProperty), userExecution.getElement2(pcmAssembly, umlProperty), userExecution.getTag1(pcmAssembly, umlProperty));
    
    postprocessElements();
    
    return true;
  }
}
