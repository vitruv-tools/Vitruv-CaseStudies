package mir.routines.pcmProvidedRoleReactions;

import java.io.IOException;
import mir.routines.pcmProvidedRoleReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class MoveCorrespondingProvidedGeneralizationRoutine extends AbstractRepairRoutineRealization {
  private MoveCorrespondingProvidedGeneralizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Generalization umlGeneralization) {
      return umlComponentImpl;
    }
    
    public void update0Element(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Generalization umlGeneralization) {
      EList<Generalization> _generalizations = umlComponentImpl.getGeneralizations();
      _generalizations.add(umlGeneralization);
    }
    
    public EObject getCorrepondenceSourceUmlComponentImpl(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return pcmIPRE;
    }
    
    public String getRetrieveTag1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlGeneralization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return pcmProvided;
    }
    
    public String getRetrieveTag2(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
  }
  
  public MoveCorrespondingProvidedGeneralizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.MoveCorrespondingProvidedGeneralizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationProvidedRole pcmProvided;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MoveCorrespondingProvidedGeneralizationRoutine with input:");
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
    org.eclipse.uml2.uml.Generalization umlGeneralization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlGeneralization(pcmProvided, pcmIPRE, umlComponentImpl), // correspondence source supplier
    	org.eclipse.uml2.uml.Generalization.class,
    	(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmProvided, pcmIPRE, umlComponentImpl), 
    	false // asserted
    	);
    if (umlGeneralization == null) {
    	return false;
    }
    registerObjectUnderModification(umlGeneralization);
    // val updatedElement userExecution.getElement1(pcmProvided, pcmIPRE, umlComponentImpl, umlGeneralization);
    userExecution.update0Element(pcmProvided, pcmIPRE, umlComponentImpl, umlGeneralization);
    
    postprocessElements();
    
    return true;
  }
}
