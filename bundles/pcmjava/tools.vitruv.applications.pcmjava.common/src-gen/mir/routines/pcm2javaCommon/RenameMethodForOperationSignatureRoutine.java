package mir.routines.pcm2javaCommon;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.HashSet;
import java.util.function.Consumer;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameMethodForOperationSignatureRoutine extends AbstractRepairRoutineRealization {
  private RenameMethodForOperationSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature operationSignature, final InterfaceMethod interfaceMethod) {
      return interfaceMethod;
    }
    
    public void update0Element(final OperationSignature operationSignature, final InterfaceMethod interfaceMethod) {
      interfaceMethod.setName(operationSignature.getEntityName());
    }
    
    public EObject getCorrepondenceSourceInterfaceMethod(final OperationSignature operationSignature) {
      return operationSignature;
    }
    
    public void callRoutine1(final OperationSignature operationSignature, final InterfaceMethod interfaceMethod, @Extension final RoutinesFacade _routinesFacade) {
      final OperationInterface operationInterface = operationSignature.getInterface__OperationSignature();
      final HashSet<InterfaceProvidingEntity> implementingComponents = Sets.<InterfaceProvidingEntity>newHashSet();
      final Consumer<RepositoryComponent> _function = (RepositoryComponent comp) -> {
        final Iterable<OperationProvidedRole> opProvRoles = Iterables.<OperationProvidedRole>filter(comp.getProvidedRoles_InterfaceProvidingEntity(), OperationProvidedRole.class);
        final Function1<OperationProvidedRole, Boolean> _function_1 = (OperationProvidedRole it) -> {
          String _id = it.getProvidedInterface__OperationProvidedRole().getId();
          String _id_1 = operationInterface.getId();
          return Boolean.valueOf(Objects.equal(_id, _id_1));
        };
        final Consumer<OperationProvidedRole> _function_2 = (OperationProvidedRole opProRole) -> {
          implementingComponents.add(opProRole.getProvidingEntity_ProvidedRole());
        };
        IterableExtensions.<OperationProvidedRole>filter(opProvRoles, _function_1).forEach(_function_2);
      };
      operationInterface.getRepository__Interface().getComponents__Repository().forEach(_function);
      final Iterable<BasicComponent> basicComponents = Iterables.<BasicComponent>filter(implementingComponents, BasicComponent.class);
      final Consumer<BasicComponent> _function_1 = (BasicComponent it) -> {
        final Consumer<ServiceEffectSpecification> _function_2 = (ServiceEffectSpecification it_1) -> {
          _routinesFacade.updateSEFFImplementingMethodName(it_1);
        };
        it.getServiceEffectSpecifications__BasicComponent().forEach(_function_2);
      };
      basicComponents.forEach(_function_1);
    }
  }
  
  public RenameMethodForOperationSignatureRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature operationSignature) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.RenameMethodForOperationSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationSignature = operationSignature;
  }
  
  private OperationSignature operationSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameMethodForOperationSignatureRoutine with input:");
    getLogger().debug("   operationSignature: " + this.operationSignature);
    
    org.emftext.language.java.members.InterfaceMethod interfaceMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceMethod(operationSignature), // correspondence source supplier
    	org.emftext.language.java.members.InterfaceMethod.class,
    	(org.emftext.language.java.members.InterfaceMethod _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (interfaceMethod == null) {
    	return false;
    }
    registerObjectUnderModification(interfaceMethod);
    // val updatedElement userExecution.getElement1(operationSignature, interfaceMethod);
    userExecution.update0Element(operationSignature, interfaceMethod);
    
    userExecution.callRoutine1(operationSignature, interfaceMethod, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
