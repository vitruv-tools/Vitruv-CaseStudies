package mir.routines.pcmProvidedRoleReactions;

import java.io.IOException;
import mir.routines.pcmProvidedRoleReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class MoveCorrespondingProvidedRealizationRoutine extends AbstractRepairRoutineRealization {
  private MoveCorrespondingProvidedRealizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final InterfaceRealization umlRealization) {
      return umlComponentImpl;
    }
    
    public void update0Element(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final InterfaceRealization umlRealization) {
      EList<InterfaceRealization> _interfaceRealizations = umlComponentImpl.getInterfaceRealizations();
      _interfaceRealizations.add(umlRealization);
    }
    
    public EObject getCorrepondenceSourceUmlComponentImpl(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return pcmIPRE;
    }
    
    public String getRetrieveTag1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public EObject getCorrepondenceSourceUmlRealization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return pcmProvided;
    }
  }
  
  public MoveCorrespondingProvidedRealizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.MoveCorrespondingProvidedRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationProvidedRole pcmProvided;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MoveCorrespondingProvidedRealizationRoutine with input:");
    getLogger().debug("   pcmProvided: " + this.pcmProvided);
    getLogger().debug("   pcmIPRE: " + this.pcmIPRE);
    
    org.eclipse.uml2.uml.Class umlComponentImpl = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentImpl(pcmProvided, pcmIPRE), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmProvided, pcmIPRE), 
    	false // asserted
    	);
    if (umlComponentImpl == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentImpl);
    org.eclipse.uml2.uml.InterfaceRealization umlRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRealization(pcmProvided, pcmIPRE, umlComponentImpl), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmProvided, pcmIPRE, umlComponentImpl), 
    	false // asserted
    	);
    if (umlRealization == null) {
    	return false;
    }
    registerObjectUnderModification(umlRealization);
    // val updatedElement userExecution.getElement1(pcmProvided, pcmIPRE, umlComponentImpl, umlRealization);
    userExecution.update0Element(pcmProvided, pcmIPRE, umlComponentImpl, umlRealization);
    
    postprocessElements();
    
    return true;
  }
}
