package mir.reactions.reactionsPcmToJava.pcm2depInjectJava;

import com.google.common.base.Objects;
import mir.routines.pcm2depInjectJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class AddedAssemblyContextToComposedStructureReaction extends AbstractReactionRealization {
  public AddedAssemblyContextToComposedStructureReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<ComposedStructure, AssemblyContext> typedChange = ((CreateAndInsertNonRoot<ComposedStructure, AssemblyContext>)change).getInsertChange();
    ComposedStructure affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    AssemblyContext newValue = typedChange.getNewValue();
    mir.routines.pcm2depInjectJava.RoutinesFacade routinesFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2depInjectJava.AddedAssemblyContextToComposedStructureReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.AddedAssemblyContextToComposedStructureReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<ComposedStructure, AssemblyContext> relevantChange = ((CreateAndInsertNonRoot<ComposedStructure, AssemblyContext>)change).getInsertChange();
    if (!(relevantChange.getAffectedEObject() instanceof ComposedStructure)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("assemblyContexts__ComposedStructure")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof AssemblyContext)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    InsertEReference<ComposedStructure, AssemblyContext> typedChange = ((CreateAndInsertNonRoot<ComposedStructure, AssemblyContext>)change).getInsertChange();
    ComposedStructure affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    AssemblyContext newValue = typedChange.getNewValue();
    if (!checkUserDefinedPrecondition(affectedEObject, affectedFeature, newValue)) {
    	return false;
    }
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final ComposedStructure affectedEObject, final EReference affectedFeature, final AssemblyContext newValue) {
    ComposedStructure _parentStructure__AssemblyContext = newValue.getParentStructure__AssemblyContext();
    final EList<AssemblyContext> assemblyContexts = _parentStructure__AssemblyContext.getAssemblyContexts__ComposedStructure();
    final RepositoryComponent component = newValue.getEncapsulatedComponent__AssemblyContext();
    for (final AssemblyContext ac : assemblyContexts) {
      if (((!Objects.equal(ac, newValue)) && ac.getEncapsulatedComponent__AssemblyContext().equals(component))) {
        String _entityName = component.getEntityName();
        String _plus = ("Assembly context for " + _entityName);
        final String msg = (_plus + 
          "already exists. Only one assembly context per basic component is allowed.");
        return false;
      }
    }
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ComposedStructure affectedEObject, final EReference affectedFeature, final AssemblyContext newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addAssemblyContextToComposedStructure(affectedEObject, newValue);
    }
  }
}
