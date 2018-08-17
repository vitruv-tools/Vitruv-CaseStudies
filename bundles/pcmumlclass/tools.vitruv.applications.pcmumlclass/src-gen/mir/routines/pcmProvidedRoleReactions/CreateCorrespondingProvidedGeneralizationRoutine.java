package mir.routines.pcmProvidedRoleReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmProvidedRoleReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingProvidedGeneralizationRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingProvidedGeneralizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Interface> umlInterface, final Generalization umlGeneralization) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSource1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSourceUmlComponentImpl(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return pcmIPRE;
    }
    
    public String getRetrieveTag1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      OperationInterface _providedInterface__OperationProvidedRole = pcmProvided.getProvidedInterface__OperationProvidedRole();
      return _providedInterface__OperationProvidedRole;
    }
    
    public String getRetrieveTag2(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public EObject getElement2(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Interface> umlInterface, final Generalization umlGeneralization) {
      return umlGeneralization;
    }
    
    public String getRetrieveTag3(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getTag1(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Interface> umlInterface, final Generalization umlGeneralization) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public void updateUmlGeneralizationElement(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Interface> umlInterface, final Generalization umlGeneralization) {
      umlGeneralization.setSpecific(umlComponentImpl);
      umlGeneralization.setGeneral(umlInterface.orElse(null));
      EList<Generalization> _generalizations = umlComponentImpl.getGeneralizations();
      _generalizations.add(umlGeneralization);
    }
  }
  
  public CreateCorrespondingProvidedGeneralizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.CreateCorrespondingProvidedGeneralizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationProvidedRole pcmProvided;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingProvidedGeneralizationRoutine with input:");
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
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmProvided, pcmIPRE, umlComponentImpl), // correspondence source supplier
    	org.eclipse.uml2.uml.Generalization.class,
    	(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmProvided, pcmIPRE, umlComponentImpl)
    ).isEmpty()) {
    	return false;
    }
    	Optional<org.eclipse.uml2.uml.Interface> umlInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlInterface(pcmProvided, pcmIPRE, umlComponentImpl), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmProvided, pcmIPRE, umlComponentImpl), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlInterface.isPresent() ? umlInterface.get() : null);
    org.eclipse.uml2.uml.Generalization umlGeneralization = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createGeneralization();
    notifyObjectCreated(umlGeneralization);
    userExecution.updateUmlGeneralizationElement(pcmProvided, pcmIPRE, umlComponentImpl, umlInterface, umlGeneralization);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmProvided, pcmIPRE, umlComponentImpl, umlInterface, umlGeneralization), userExecution.getElement2(pcmProvided, pcmIPRE, umlComponentImpl, umlInterface, umlGeneralization), userExecution.getTag1(pcmProvided, pcmIPRE, umlComponentImpl, umlInterface, umlGeneralization));
    
    postprocessElements();
    
    return true;
  }
}
