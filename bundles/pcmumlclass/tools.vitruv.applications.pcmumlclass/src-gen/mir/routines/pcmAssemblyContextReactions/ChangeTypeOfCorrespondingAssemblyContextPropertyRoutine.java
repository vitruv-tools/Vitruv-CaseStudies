package mir.routines.pcmAssemblyContextReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmAssemblyContextReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfCorrespondingAssemblyContextPropertyRoutine extends AbstractRepairRoutineRealization {
  private ChangeTypeOfCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final AssemblyContext pcmAssembly, final RepositoryComponent pcmComonent, final Property umlProperty, final Optional<org.eclipse.uml2.uml.Class> umlComponentImplementation) {
      return umlProperty;
    }
    
    public void update0Element(final AssemblyContext pcmAssembly, final RepositoryComponent pcmComonent, final Property umlProperty, final Optional<org.eclipse.uml2.uml.Class> umlComponentImplementation) {
      umlProperty.setType(umlComponentImplementation.orElse(null));
    }
    
    public String getRetrieveTag1(final AssemblyContext pcmAssembly, final RepositoryComponent pcmComonent) {
      return TagLiterals.ASSEMBLY_CONTEXT__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlProperty(final AssemblyContext pcmAssembly, final RepositoryComponent pcmComonent) {
      return pcmAssembly;
    }
    
    public EObject getCorrepondenceSourceUmlComponentImplementation(final AssemblyContext pcmAssembly, final RepositoryComponent pcmComonent, final Property umlProperty) {
      return pcmComonent;
    }
    
    public String getRetrieveTag2(final AssemblyContext pcmAssembly, final RepositoryComponent pcmComonent, final Property umlProperty) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
  }
  
  public ChangeTypeOfCorrespondingAssemblyContextPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AssemblyContext pcmAssembly, final RepositoryComponent pcmComonent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmAssemblyContextReactions.ChangeTypeOfCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAssembly = pcmAssembly;this.pcmComonent = pcmComonent;
  }
  
  private AssemblyContext pcmAssembly;
  
  private RepositoryComponent pcmComonent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingAssemblyContextPropertyRoutine with input:");
    getLogger().debug("   pcmAssembly: " + this.pcmAssembly);
    getLogger().debug("   pcmComonent: " + this.pcmComonent);
    
    org.eclipse.uml2.uml.Property umlProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlProperty(pcmAssembly, pcmComonent), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmAssembly, pcmComonent), 
    	false // asserted
    	);
    if (umlProperty == null) {
    	return false;
    }
    registerObjectUnderModification(umlProperty);
    	Optional<org.eclipse.uml2.uml.Class> umlComponentImplementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlComponentImplementation(pcmAssembly, pcmComonent, umlProperty), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmAssembly, pcmComonent, umlProperty), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlComponentImplementation.isPresent() ? umlComponentImplementation.get() : null);
    // val updatedElement userExecution.getElement1(pcmAssembly, pcmComonent, umlProperty, umlComponentImplementation);
    userExecution.update0Element(pcmAssembly, pcmComonent, umlProperty, umlComponentImplementation);
    
    postprocessElements();
    
    return true;
  }
}
