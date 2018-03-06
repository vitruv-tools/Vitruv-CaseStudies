package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateOperationRequiredRoleCorrespondingToFieldRoutine extends AbstractRepairRoutineRealization {
  private CreateOperationRequiredRoleCorrespondingToFieldRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Field javaField, final OperationInterface operationInterface, final RepositoryComponent repoComponent, final OperationRequiredRole operationRequiredRole) {
      return operationRequiredRole;
    }
    
    public EObject getElement2(final Field javaField, final OperationInterface operationInterface, final RepositoryComponent repoComponent, final OperationRequiredRole operationRequiredRole) {
      return javaField;
    }
    
    public void updateOperationRequiredRoleElement(final Field javaField, final OperationInterface operationInterface, final RepositoryComponent repoComponent, final OperationRequiredRole operationRequiredRole) {
      operationRequiredRole.setRequiredInterface__OperationRequiredRole(operationInterface);
      operationRequiredRole.setRequiringEntity_RequiredRole(repoComponent);
      String _entityName = repoComponent.getEntityName();
      String _plus = ("Component_" + _entityName);
      String _plus_1 = (_plus + "_requires_");
      String _entityName_1 = operationInterface.getEntityName();
      String _plus_2 = (_plus_1 + _entityName_1);
      operationRequiredRole.setEntityName(_plus_2);
    }
  }
  
  public CreateOperationRequiredRoleCorrespondingToFieldRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field javaField, final OperationInterface operationInterface, final RepositoryComponent repoComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateOperationRequiredRoleCorrespondingToFieldRoutine.ActionUserExecution(getExecutionState(), this);
    this.javaField = javaField;this.operationInterface = operationInterface;this.repoComponent = repoComponent;
  }
  
  private Field javaField;
  
  private OperationInterface operationInterface;
  
  private RepositoryComponent repoComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationRequiredRoleCorrespondingToFieldRoutine with input:");
    getLogger().debug("   javaField: " + this.javaField);
    getLogger().debug("   operationInterface: " + this.operationInterface);
    getLogger().debug("   repoComponent: " + this.repoComponent);
    
    org.palladiosimulator.pcm.repository.OperationRequiredRole operationRequiredRole = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationRequiredRole();
    notifyObjectCreated(operationRequiredRole);
    userExecution.updateOperationRequiredRoleElement(javaField, operationInterface, repoComponent, operationRequiredRole);
    
    addCorrespondenceBetween(userExecution.getElement1(javaField, operationInterface, repoComponent, operationRequiredRole), userExecution.getElement2(javaField, operationInterface, repoComponent, operationRequiredRole), "");
    
    postprocessElements();
    
    return true;
  }
}
