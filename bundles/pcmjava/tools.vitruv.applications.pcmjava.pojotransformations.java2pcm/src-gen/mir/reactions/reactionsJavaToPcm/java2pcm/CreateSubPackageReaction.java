package mir.reactions.reactionsJavaToPcm.java2pcm;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mir.routines.java2pcm.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
class CreateSubPackageReaction extends AbstractReactionRealization {
  public CreateSubPackageReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<org.emftext.language.java.containers.Package, org.emftext.language.java.containers.Package> typedChange = (InsertEReference<org.emftext.language.java.containers.Package, org.emftext.language.java.containers.Package>)change;
    org.emftext.language.java.containers.Package affectedEObject = typedChange.getAffectedEObject();
    org.emftext.language.java.containers.Package newValue = typedChange.getNewValue();
    mir.routines.java2pcm.RoutinesFacade routinesFacade = new mir.routines.java2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.java2pcm.CreateSubPackageReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.java2pcm.CreateSubPackageReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.emftext.language.java.containers.Package, org.emftext.language.java.containers.Package> relevantChange = (InsertEReference<org.emftext.language.java.containers.Package, org.emftext.language.java.containers.Package>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.containers.Package)) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof org.emftext.language.java.containers.Package)) {
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
    InsertEReference<org.emftext.language.java.containers.Package, org.emftext.language.java.containers.Package> typedChange = (InsertEReference<org.emftext.language.java.containers.Package, org.emftext.language.java.containers.Package>)change;
    org.emftext.language.java.containers.Package affectedEObject = typedChange.getAffectedEObject();
    org.emftext.language.java.containers.Package newValue = typedChange.getNewValue();
    if (!checkUserDefinedPrecondition(affectedEObject, newValue)) {
    	return false;
    }
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final org.emftext.language.java.containers.Package affectedEObject, final org.emftext.language.java.containers.Package newValue) {
    return ((!newValue.getName().contains("contracts")) && (!newValue.getName().contains("datatypes")));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package affectedEObject, final org.emftext.language.java.containers.Package newValue, @Extension final RoutinesFacade _routinesFacade) {
      Set<Class<?>> architecturalElements = new HashSet<Class<?>>();
      Iterables.<Class<?>>addAll(architecturalElements, Collections.<Class<? extends InterfaceProvidingRequiringEntity>>unmodifiableList(CollectionLiterals.<Class<? extends InterfaceProvidingRequiringEntity>>newArrayList(BasicComponent.class, CompositeComponent.class, org.palladiosimulator.pcm.system.System.class, (Class<? extends InterfaceProvidingRequiringEntity>)null)));
      int _size = architecturalElements.size();
      final List<String> architecturalElementsNames = new ArrayList<String>(_size);
      for (final Class<?> architecturalElement : architecturalElements) {
        architecturalElementsNames.add(architecturalElement.getName());
      }
      final String selectElementMsg = "A package has been created. Please decide whether and which corresponding architectural element should be created";
      final int selectedType = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, selectElementMsg, ((String[])Conversions.unwrapArray(architecturalElementsNames, String.class)));
      if ((selectedType == 0)) {
        _routinesFacade.createBasicComponent(newValue);
      } else {
        if ((selectedType == 1)) {
          _routinesFacade.createCompositeComponent(newValue);
        } else {
          if ((selectedType == 2)) {
            _routinesFacade.createSystem(newValue);
          } else {
            if ((selectedType == 3)) {
            }
          }
        }
      }
    }
  }
}
