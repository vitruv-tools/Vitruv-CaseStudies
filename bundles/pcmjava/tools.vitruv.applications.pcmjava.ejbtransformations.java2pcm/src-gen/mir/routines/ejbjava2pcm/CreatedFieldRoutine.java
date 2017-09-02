package mir.routines.ejbjava2pcm;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EjbJava2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatedFieldRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatedFieldRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceBasicComponent(final Field field) {
      return field;
    }
    
    public void callRoutine1(final Field field, final BasicComponent basicComponent, @Extension final RoutinesFacade _routinesFacade) {
      final String interfaceName = EjbJava2PcmHelper.getClassifier(field.getTypeReference()).getName();
      final Function1<OperationInterface, Boolean> _function = (OperationInterface it) -> {
        return Boolean.valueOf(it.getEntityName().equals(interfaceName));
      };
      final OperationInterface opInterface = IterableExtensions.<OperationInterface>findFirst(Iterables.<OperationInterface>filter(basicComponent.getRepository__RepositoryComponent().getInterfaces__Repository(), OperationInterface.class), _function);
      _routinesFacade.createOperationRequiredRole(basicComponent, opInterface, field);
    }
  }
  
  public CreatedFieldRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field field) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedFieldRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.field = field;
  }
  
  private Field field;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedFieldRoutine with input:");
    getLogger().debug("   field: " + this.field);
    
    org.palladiosimulator.pcm.repository.BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(field), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.BasicComponent.class,
    	(org.palladiosimulator.pcm.repository.BasicComponent _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (basicComponent == null) {
    	return false;
    }
    registerObjectUnderModification(basicComponent);
    userExecution.callRoutine1(field, basicComponent, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
