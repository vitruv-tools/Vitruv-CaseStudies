package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.commons.NamedElement;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateOperationInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateOperationInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
      return operationInterface;
    }
    
    public void update0Element(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
      repo.getInterfaces__Repository().add(operationInterface);
    }
    
    public EObject getElement2(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
      return namedElement;
    }
    
    public EObject getElement3(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
      return operationInterface;
    }
    
    public void updateOperationInterfaceElement(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
      operationInterface.setEntityName(namedElement.getName());
    }
  }
  
  public CreateOperationInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repo, final NamedElement namedElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreateOperationInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.repo = repo;this.namedElement = namedElement;
  }
  
  private Repository repo;
  
  private NamedElement namedElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationInterfaceRoutine with input:");
    getLogger().debug("   repo: " + this.repo);
    getLogger().debug("   namedElement: " + this.namedElement);
    
    org.palladiosimulator.pcm.repository.OperationInterface operationInterface = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    notifyObjectCreated(operationInterface);
    userExecution.updateOperationInterfaceElement(repo, namedElement, operationInterface);
    
    addCorrespondenceBetween(userExecution.getElement1(repo, namedElement, operationInterface), userExecution.getElement2(repo, namedElement, operationInterface), "");
    
    // val updatedElement userExecution.getElement3(repo, namedElement, operationInterface);
    userExecution.update0Element(repo, namedElement, operationInterface);
    
    postprocessElements();
    
    return true;
  }
}
