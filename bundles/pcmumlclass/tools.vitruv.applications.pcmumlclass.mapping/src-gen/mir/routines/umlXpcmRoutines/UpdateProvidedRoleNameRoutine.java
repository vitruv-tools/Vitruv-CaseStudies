package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateProvidedRoleNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateProvidedRoleNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final InterfaceRealization interfaceRealization, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final OperationProvidedRole role, @Extension final RoutinesFacade _routinesFacade) {
      role.setEntityName(interfaceRealization.getName());
    }
    
    public String getRetrieveTag1(final InterfaceRealization interfaceRealization, final org.eclipse.uml2.uml.Class implementation, final Interface interface_) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public EObject getCorrepondenceSourceRole(final InterfaceRealization interfaceRealization, final org.eclipse.uml2.uml.Class implementation, final Interface interface_) {
      return interfaceRealization;
    }
  }
  
  public UpdateProvidedRoleNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization interfaceRealization, final org.eclipse.uml2.uml.Class implementation, final Interface interface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateProvidedRoleNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.interfaceRealization = interfaceRealization;this.implementation = implementation;this.interface_ = interface_;
  }
  
  private InterfaceRealization interfaceRealization;
  
  private org.eclipse.uml2.uml.Class implementation;
  
  private Interface interface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateProvidedRoleNameRoutine with input:");
    getLogger().debug("   interfaceRealization: " + this.interfaceRealization);
    getLogger().debug("   implementation: " + this.implementation);
    getLogger().debug("   interface_: " + this.interface_);
    
    org.palladiosimulator.pcm.repository.OperationProvidedRole role = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRole(interfaceRealization, implementation, interface_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    	(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(interfaceRealization, implementation, interface_), 
    	false // asserted
    	);
    if (role == null) {
    	return false;
    }
    registerObjectUnderModification(role);
    userExecution.executeAction1(interfaceRealization, implementation, interface_, role, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
