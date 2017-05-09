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
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class InterfaceRealizedReaction extends AbstractReactionRealization {
  public InterfaceRealizedReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<InterfaceRealization, Interface> typedChange = (InsertEReference<InterfaceRealization, Interface>)change;
    InterfaceRealization affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Interface newValue = typedChange.getNewValue();
    mir.routines.class2comp.RoutinesFacade routinesFacade = new mir.routines.class2comp.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToUml.class2comp.InterfaceRealizedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToUml.class2comp.InterfaceRealizedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<InterfaceRealization, Interface> relevantChange = (InsertEReference<InterfaceRealization, Interface>)change;
    if (!(relevantChange.getAffectedEObject() instanceof InterfaceRealization)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("supplier")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof Interface)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InterfaceRealization affectedEObject, final EReference affectedFeature, final Interface newValue, @Extension final RoutinesFacade _routinesFacade) {
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
