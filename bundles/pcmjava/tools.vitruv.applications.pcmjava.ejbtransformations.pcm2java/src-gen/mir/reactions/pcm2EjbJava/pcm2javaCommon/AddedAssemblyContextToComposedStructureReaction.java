package mir.reactions.pcm2EjbJava.pcm2javaCommon;

import mir.routines.pcm2EjbJava.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
public class AddedAssemblyContextToComposedStructureReaction extends AbstractReactionRealization {
  private CreateEObject<AssemblyContext> createChange;
  
  private InsertEReference<ComposedStructure, AssemblyContext> insertChange;
  
  private int currentlyMatchedChange;
  
  public AddedAssemblyContextToComposedStructureReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.core.composition.ComposedStructure affectedEObject = insertChange.getAffectedEObject();
    EReference affectedFeature = insertChange.getAffectedFeature();
    org.palladiosimulator.pcm.core.composition.AssemblyContext newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.pcm2EjbJava.pcm2javaCommon.AddedAssemblyContextToComposedStructureReaction.ActionUserExecution userExecution = new mir.reactions.pcm2EjbJava.pcm2javaCommon.AddedAssemblyContextToComposedStructureReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(insertChange, affectedEObject, affectedFeature, newValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    createChange = null;
    insertChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchCreateChange(final EChange change) {
    if (change instanceof CreateEObject<?>) {
    	CreateEObject<org.palladiosimulator.pcm.core.composition.AssemblyContext> _localTypedChange = (CreateEObject<org.palladiosimulator.pcm.core.composition.AssemblyContext>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.core.composition.AssemblyContext)) {
    		return false;
    	}
    	this.createChange = (CreateEObject<org.palladiosimulator.pcm.core.composition.AssemblyContext>) change;
    	return true;
    }
    
    return false;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchCreateChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    	return false; // Only proceed on the last of the expected changes
    }
    if (currentlyMatchedChange == 1) {
    	if (!matchInsertChange(change)) {
    		resetChanges();
    		checkPrecondition(change); // Reexecute to potentially register this as first change
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchInsertChange(final EChange change) {
    if (change instanceof InsertEReference<?, ?>) {
    	InsertEReference<org.palladiosimulator.pcm.core.composition.ComposedStructure, org.palladiosimulator.pcm.core.composition.AssemblyContext> _localTypedChange = (InsertEReference<org.palladiosimulator.pcm.core.composition.ComposedStructure, org.palladiosimulator.pcm.core.composition.AssemblyContext>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.core.composition.ComposedStructure)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("assemblyContexts__ComposedStructure")) {
    		return false;
    	}
    	if (!(_localTypedChange.getNewValue() instanceof org.palladiosimulator.pcm.core.composition.AssemblyContext)) {
    		return false;
    	}
    	this.insertChange = (InsertEReference<org.palladiosimulator.pcm.core.composition.ComposedStructure, org.palladiosimulator.pcm.core.composition.AssemblyContext>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertEReference insertChange, final ComposedStructure affectedEObject, final EReference affectedFeature, final AssemblyContext newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
}
