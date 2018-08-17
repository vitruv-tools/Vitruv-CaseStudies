package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingRequiredFieldRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingRequiredFieldRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlComponentImpl(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return pcmIPRE;
    }
    
    public String getRetrieveTag1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlRequiredField(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return pcmRequired;
    }
    
    public void callRoutine1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Property> umlRequiredField, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlRequiredField.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        _routinesFacade.createCorrespondingRequiredField(pcmRequired, pcmIPRE);
      }
    }
  }
  
  public DetectOrCreateCorrespondingRequiredFieldRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.DetectOrCreateCorrespondingRequiredFieldRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationRequiredRole pcmRequired;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingRequiredFieldRoutine with input:");
    getLogger().debug("   pcmRequired: " + this.pcmRequired);
    getLogger().debug("   pcmIPRE: " + this.pcmIPRE);
    
    org.eclipse.uml2.uml.Class umlComponentImpl = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentImpl(pcmRequired, pcmIPRE), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmRequired, pcmIPRE), 
    	false // asserted
    	);
    if (umlComponentImpl == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentImpl);
    	Optional<org.eclipse.uml2.uml.Property> umlRequiredField = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlRequiredField(pcmRequired, pcmIPRE, umlComponentImpl), // correspondence source supplier
    		org.eclipse.uml2.uml.Property.class,
    		(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmRequired, pcmIPRE, umlComponentImpl), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlRequiredField.isPresent() ? umlRequiredField.get() : null);
    userExecution.callRoutine1(pcmRequired, pcmIPRE, umlComponentImpl, umlRequiredField, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
