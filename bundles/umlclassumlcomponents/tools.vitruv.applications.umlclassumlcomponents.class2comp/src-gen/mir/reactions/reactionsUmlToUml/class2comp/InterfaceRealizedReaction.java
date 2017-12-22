package mir.reactions.reactionsUmlToUml.class2comp;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

/**
 * ***********
 * *Interfaces:*
 * ************
 */
@SuppressWarnings("all")
class InterfaceRealizedReaction extends AbstractReactionRealization {
  private InsertEReference<InterfaceRealization, Interface> insertChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.InterfaceRealization affectedEObject = insertChange.getAffectedEObject();
    EReference affectedFeature = insertChange.getAffectedFeature();
    org.eclipse.uml2.uml.Interface newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.class2comp.RoutinesFacade routinesFacade = new mir.routines.class2comp.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToUml.class2comp.InterfaceRealizedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToUml.class2comp.InterfaceRealizedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(insertChange, affectedEObject, affectedFeature, newValue, index, routinesFacade);
    
    resetChanges();
  }
  
  private void resetChanges() {
    insertChange = null;
    currentlyMatchedChange = 0;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchInsertChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchInsertChange(final EChange change) {
    if (change instanceof InsertEReference<?, ?>) {
    	InsertEReference<org.eclipse.uml2.uml.InterfaceRealization, org.eclipse.uml2.uml.Interface> _localTypedChange = (InsertEReference<org.eclipse.uml2.uml.InterfaceRealization, org.eclipse.uml2.uml.Interface>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.InterfaceRealization)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("supplier")) {
    		return false;
    	}
    	if (!(_localTypedChange.getNewValue() instanceof org.eclipse.uml2.uml.Interface)) {
    		return false;
    	}
    	this.insertChange = (InsertEReference<org.eclipse.uml2.uml.InterfaceRealization, org.eclipse.uml2.uml.Interface>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertEReference insertChange, final InterfaceRealization affectedEObject, final EReference affectedFeature, final Interface newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      final InterfaceRealization classIFRealization = affectedEObject;
      final Iterable<org.eclipse.uml2.uml.Class> interfaceClients = Iterables.<org.eclipse.uml2.uml.Class>filter(classIFRealization.getClients(), org.eclipse.uml2.uml.Class.class);
      boolean _isEmpty = IterableExtensions.isEmpty(interfaceClients);
      boolean _not = (!_isEmpty);
      if (_not) {
        final org.eclipse.uml2.uml.Class affectedClass = ((org.eclipse.uml2.uml.Class[])Conversions.unwrapArray(interfaceClients, org.eclipse.uml2.uml.Class.class))[0];
        final Interface classInterface = newValue;
        final org.eclipse.uml2.uml.Package classPackage = affectedClass.getPackage();
        final org.eclipse.uml2.uml.Package interfacePackage = classInterface.getPackage();
        boolean _notEquals = (!Objects.equal(classPackage, interfacePackage));
        if (_notEquals) {
          _routinesFacade.createRequiredAndProvidedRole(classInterface, classIFRealization, interfacePackage, classPackage);
        }
      }
    }
  }
}
