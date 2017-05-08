package mir.reactions.reactionsJavaToUml.javaToUmlAttribute;

import mir.routines.javaToUmlAttribute.RoutinesFacade;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.members.Field;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class JavaAttributeCreatedReaction extends AbstractReactionRealization {
  public JavaAttributeCreatedReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<ConcreteClassifier, Field> typedChange = ((CreateAndInsertNonRoot<ConcreteClassifier, Field>)change).getInsertChange();
    ConcreteClassifier affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Field newValue = typedChange.getNewValue();
    mir.routines.javaToUmlAttribute.RoutinesFacade routinesFacade = new mir.routines.javaToUmlAttribute.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<ConcreteClassifier, Field> relevantChange = ((CreateAndInsertNonRoot<ConcreteClassifier, Field>)change).getInsertChange();
    if (!(relevantChange.getAffectedEObject() instanceof ConcreteClassifier)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("members")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof Field)) {
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
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final ConcreteClassifier affectedEObject, final EReference affectedFeature, final Field newValue, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof org.emftext.language.java.classifiers.Class)) {
        _routinesFacade.createUmlAttributeInClass(((org.emftext.language.java.classifiers.Class)affectedEObject), newValue);
      } else {
        if ((affectedEObject instanceof Enumeration)) {
          _routinesFacade.createUmlAttributeInEnum(((Enumeration)affectedEObject), newValue);
        } else {
          Logger _logger = this.getLogger();
          String _plus = (affectedEObject + " is neither a Class nor a Enum. JavaAttributeCreated-Reaction not executed.");
          _logger.warn(_plus);
        }
      }
    }
  }
}
