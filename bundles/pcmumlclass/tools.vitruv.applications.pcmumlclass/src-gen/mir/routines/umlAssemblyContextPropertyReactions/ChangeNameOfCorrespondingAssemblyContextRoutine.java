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
public class ChangeNameOfCorrespondingAssemblyContextRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingAssemblyContextRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final String newName, final AssemblyContext pcmAssemblyContext) {
      return pcmAssemblyContext;
    }
    
    public void update0Element(final Property umlProperty, final String newName, final AssemblyContext pcmAssemblyContext) {
      pcmAssemblyContext.setEntityName(newName);
    }
    
    public String getRetrieveTag1(final Property umlProperty, final String newName) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getCorrepondenceSourcePcmAssemblyContext(final Property umlProperty, final String newName) {
      return umlProperty;
    }
  }
  
  public ChangeNameOfCorrespondingAssemblyContextRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlAssemblyContextPropertyReactions.ChangeNameOfCorrespondingAssemblyContextRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.newName = newName;
  }
  
  private Property umlProperty;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingAssemblyContextRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   newName: " + this.newName);
    
    org.palladiosimulator.pcm.core.composition.AssemblyContext pcmAssemblyContext = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmAssemblyContext(umlProperty, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.core.composition.AssemblyContext.class,
    	(org.palladiosimulator.pcm.core.composition.AssemblyContext _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, newName), 
    	false // asserted
    	);
    if (pcmAssemblyContext == null) {
    	return false;
    }
    registerObjectUnderModification(pcmAssemblyContext);
    // val updatedElement userExecution.getElement1(umlProperty, newName, pcmAssemblyContext);
    userExecution.update0Element(umlProperty, newName, pcmAssemblyContext);
    
    postprocessElements();
    
    return true;
  }
}
