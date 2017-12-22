package mir.reactions.reactionsUmlToUml.comp2class;

import com.google.common.collect.Iterables;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Component;
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
 * ***********
 */
@SuppressWarnings("all")
class AddedProvidedRoleReaction extends AbstractReactionRealization {
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
    				
    mir.routines.comp2class.RoutinesFacade routinesFacade = new mir.routines.comp2class.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToUml.comp2class.AddedProvidedRoleReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToUml.comp2class.AddedProvidedRoleReaction.ActionUserExecution(this.executionState, this);
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
      final InterfaceRealization compIFRealizaion = affectedEObject;
      final Iterable<Component> clients = Iterables.<Component>filter(compIFRealizaion.getClients(), Component.class);
      boolean _isEmpty = IterableExtensions.isEmpty(clients);
      boolean _not = (!_isEmpty);
      if (_not) {
        final Component umlComp = ((Component[])Conversions.unwrapArray(clients, Component.class))[0];
        final Interface compInterface = newValue;
        _routinesFacade.createClassInterface(compInterface, umlComp);
        _routinesFacade.createClassInterfaceRealization(compIFRealizaion, umlComp);
        _routinesFacade.addClassInterfaceRealizationToClass(compIFRealizaion, compInterface, umlComp);
      }
    }
  }
}
