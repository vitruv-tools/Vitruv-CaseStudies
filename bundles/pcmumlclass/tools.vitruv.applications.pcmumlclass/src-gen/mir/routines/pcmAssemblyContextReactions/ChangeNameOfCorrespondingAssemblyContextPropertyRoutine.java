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
public class ChangeNameOfCorrespondingAssemblyContextPropertyRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final AssemblyContext pcmAssembly, final String newName, final Property umlProperty) {
      return umlProperty;
    }
    
    public void update0Element(final AssemblyContext pcmAssembly, final String newName, final Property umlProperty) {
      umlProperty.setName(newName);
    }
    
    public String getRetrieveTag1(final AssemblyContext pcmAssembly, final String newName) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlProperty(final AssemblyContext pcmAssembly, final String newName) {
      return pcmAssembly;
    }
  }
  
  public ChangeNameOfCorrespondingAssemblyContextPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AssemblyContext pcmAssembly, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmAssemblyContextReactions.ChangeNameOfCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAssembly = pcmAssembly;this.newName = newName;
  }
  
  private AssemblyContext pcmAssembly;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingAssemblyContextPropertyRoutine with input:");
    getLogger().debug("   pcmAssembly: " + this.pcmAssembly);
    getLogger().debug("   newName: " + this.newName);
    
    org.eclipse.uml2.uml.Property umlProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlProperty(pcmAssembly, newName), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmAssembly, newName), 
    	false // asserted
    	);
    if (umlProperty == null) {
    	return false;
    }
    registerObjectUnderModification(umlProperty);
    // val updatedElement userExecution.getElement1(pcmAssembly, newName, umlProperty);
    userExecution.update0Element(pcmAssembly, newName, umlProperty);
    
    postprocessElements();
    
    return true;
  }
}
