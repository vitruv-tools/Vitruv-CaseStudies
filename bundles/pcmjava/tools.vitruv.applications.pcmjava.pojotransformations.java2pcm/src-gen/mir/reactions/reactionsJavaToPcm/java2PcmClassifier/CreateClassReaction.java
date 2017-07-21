package mir.reactions.reactionsJavaToPcm.java2PcmClassifier;

import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class CreateClassReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    InsertEReference<CompilationUnit, org.emftext.language.java.classifiers.Class> typedChange = (InsertEReference<CompilationUnit, org.emftext.language.java.classifiers.Class>)change;
    CompilationUnit affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.emftext.language.java.classifiers.Class newValue = typedChange.getNewValue();
    mir.routines.java2PcmClassifier.RoutinesFacade routinesFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.java2PcmClassifier.CreateClassReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.java2PcmClassifier.CreateClassReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<CompilationUnit, org.emftext.language.java.classifiers.Class> relevantChange = (InsertEReference<CompilationUnit, org.emftext.language.java.classifiers.Class>)change;
    if (!(relevantChange.getAffectedEObject() instanceof CompilationUnit)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("classifiers")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof org.emftext.language.java.classifiers.Class)) {
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
    
    public void callRoutine1(final CompilationUnit affectedEObject, final EReference affectedFeature, final org.emftext.language.java.classifiers.Class newValue, @Extension final RoutinesFacade _routinesFacade) {
      boolean _equals = IterableExtensions.<String>last(affectedEObject.getNamespaces()).equals("datatypes");
      if (_equals) {
        _routinesFacade.createCompositeDataType(newValue, affectedEObject);
      } else {
        final org.emftext.language.java.containers.Package package_ = Java2PcmHelper.getContainingPackageFromCorrespondanceModel(affectedEObject.getNamespaces(), this.correspondenceModel);
        _routinesFacade.checkComponentCorrespondance(package_, newValue);
        _routinesFacade.checkSystemCorrespondance(package_, newValue);
        boolean _hasCorrespondance = Java2PcmHelper.hasCorrespondance(newValue, this.correspondenceModel);
        boolean _not = (!_hasCorrespondance);
        if (_not) {
          _routinesFacade.createArchitecturalElement(package_, newValue.getName());
          _routinesFacade.checkComponentCorrespondance(package_, newValue);
          _routinesFacade.checkSystemCorrespondance(package_, newValue);
        }
      }
    }
  }
}