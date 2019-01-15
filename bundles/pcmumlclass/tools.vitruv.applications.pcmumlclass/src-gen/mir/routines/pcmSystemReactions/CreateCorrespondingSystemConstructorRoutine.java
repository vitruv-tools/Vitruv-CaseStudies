package mir.routines.pcmSystemReactions;

import java.io.IOException;
import mir.routines.pcmSystemReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingSystemConstructorRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingSystemConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation, final Operation umlSystemConstructor) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSource1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSourceUmlSystemImplementation(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public String getRetrieveTag1(final org.palladiosimulator.pcm.system.System pcmSystem) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public EObject getElement2(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation, final Operation umlSystemConstructor) {
      return umlSystemConstructor;
    }
    
    public void updateUmlSystemConstructorElement(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation, final Operation umlSystemConstructor) {
      String _entityName = pcmSystem.getEntityName();
      String _plus = (_entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX);
      umlSystemConstructor.setName(_plus);
      EList<Operation> _ownedOperations = umlSystemImplementation.getOwnedOperations();
      _ownedOperations.add(umlSystemConstructor);
    }
    
    public String getTag1(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation, final Operation umlSystemConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
  }
  
  public CreateCorrespondingSystemConstructorRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System pcmSystem) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSystemReactions.CreateCorrespondingSystemConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSystem = pcmSystem;
  }
  
  private org.palladiosimulator.pcm.system.System pcmSystem;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingSystemConstructorRoutine with input:");
    getLogger().debug("   pcmSystem: " + this.pcmSystem);
    
    org.eclipse.uml2.uml.Class umlSystemImplementation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlSystemImplementation(pcmSystem), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSystem), 
    	false // asserted
    	);
    if (umlSystemImplementation == null) {
    	return false;
    }
    registerObjectUnderModification(umlSystemImplementation);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmSystem, umlSystemImplementation), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmSystem, umlSystemImplementation)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Operation umlSystemConstructor = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createOperation();
    notifyObjectCreated(umlSystemConstructor);
    userExecution.updateUmlSystemConstructorElement(pcmSystem, umlSystemImplementation, umlSystemConstructor);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmSystem, umlSystemImplementation, umlSystemConstructor), userExecution.getElement2(pcmSystem, umlSystemImplementation, umlSystemConstructor), userExecution.getTag1(pcmSystem, umlSystemImplementation, umlSystemConstructor));
    
    postprocessElements();
    
    return true;
  }
}
