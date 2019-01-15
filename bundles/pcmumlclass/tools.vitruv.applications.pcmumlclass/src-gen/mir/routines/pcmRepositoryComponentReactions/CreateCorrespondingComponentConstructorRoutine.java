package mir.routines.pcmRepositoryComponentReactions;

import java.io.IOException;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingComponentConstructorRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingComponentConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSource1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return pcmComponent;
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlComponentImplementation(final RepositoryComponent pcmComponent) {
      return pcmComponent;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public EObject getElement2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return umlComponentConstructor;
    }
    
    public String getTag1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public void updateUmlComponentConstructorElement(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation, final Operation umlComponentConstructor) {
      String _entityName = pcmComponent.getEntityName();
      String _plus = (_entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX);
      umlComponentConstructor.setName(_plus);
      EList<Operation> _ownedOperations = umlComponentImplementation.getOwnedOperations();
      _ownedOperations.add(umlComponentConstructor);
    }
  }
  
  public CreateCorrespondingComponentConstructorRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.CreateCorrespondingComponentConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;
  }
  
  private RepositoryComponent pcmComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingComponentConstructorRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    
    org.eclipse.uml2.uml.Class umlComponentImplementation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentImplementation(pcmComponent), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmComponent), 
    	false // asserted
    	);
    if (umlComponentImplementation == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentImplementation);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmComponent, umlComponentImplementation), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmComponent, umlComponentImplementation)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Operation umlComponentConstructor = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createOperation();
    notifyObjectCreated(umlComponentConstructor);
    userExecution.updateUmlComponentConstructorElement(pcmComponent, umlComponentImplementation, umlComponentConstructor);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmComponent, umlComponentImplementation, umlComponentConstructor), userExecution.getElement2(pcmComponent, umlComponentImplementation, umlComponentConstructor), userExecution.getTag1(pcmComponent, umlComponentImplementation, umlComponentConstructor));
    
    postprocessElements();
    
    return true;
  }
}
