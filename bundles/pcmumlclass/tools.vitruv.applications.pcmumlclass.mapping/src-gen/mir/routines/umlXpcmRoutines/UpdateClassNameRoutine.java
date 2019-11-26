package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateClassNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateClassNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlCompositeClass(final CompositeDataType type, final Repository repository) {
      return type;
    }
    
    public void executeAction1(final CompositeDataType type, final Repository repository, final org.eclipse.uml2.uml.Class umlCompositeClass, @Extension final RoutinesFacade _routinesFacade) {
      umlCompositeClass.setName(type.getEntityName());
    }
    
    public String getRetrieveTag1(final CompositeDataType type, final Repository repository) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
  }
  
  public UpdateClassNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType type, final Repository repository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateClassNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.type = type;this.repository = repository;
  }
  
  private CompositeDataType type;
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateClassNameRoutine with input:");
    getLogger().debug("   type: " + this.type);
    getLogger().debug("   repository: " + this.repository);
    
    org.eclipse.uml2.uml.Class umlCompositeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlCompositeClass(type, repository), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(type, repository), 
    	false // asserted
    	);
    if (umlCompositeClass == null) {
    	return false;
    }
    registerObjectUnderModification(umlCompositeClass);
    userExecution.executeAction1(type, repository, umlCompositeClass, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
