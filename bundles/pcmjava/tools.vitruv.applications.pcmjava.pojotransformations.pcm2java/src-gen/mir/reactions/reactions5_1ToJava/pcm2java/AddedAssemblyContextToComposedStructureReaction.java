package mir.reactions.reactions5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
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
    CreateAndInsertNonRoot<ComposedStructure, AssemblyContext> typedChange = (CreateAndInsertNonRoot<ComposedStructure, AssemblyContext>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactions5_1ToJava.pcm2java.AddedAssemblyContextToComposedStructureReaction.ActionUserExecution userExecution = new mir.reactions.reactions5_1ToJava.pcm2java.AddedAssemblyContextToComposedStructureReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<ComposedStructure, AssemblyContext> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof AssemblyContext)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof ComposedStructure)) {
    	return false;
    }
    // Check feature
    if (!change.getInsertChange().getAffectedFeature().getName().equals("assemblyContexts__ComposedStructure")) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof AssemblyContext)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    CreateAndInsertNonRoot<ComposedStructure, AssemblyContext> typedChange = (CreateAndInsertNonRoot<ComposedStructure, AssemblyContext>)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final CreateAndInsertNonRoot<ComposedStructure, AssemblyContext> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertEReference<ComposedStructure, AssemblyContext> _insertChange = change.getInsertChange();
      ComposedStructure _affectedEObject = _insertChange.getAffectedEObject();
      InsertEReference<ComposedStructure, AssemblyContext> _insertChange_1 = change.getInsertChange();
      AssemblyContext _newValue = _insertChange_1.getNewValue();
      _routinesFacade.addAssemblyContextToComposedStructure(_affectedEObject, _newValue);
    }
  }
}
