package mir.reactions.javaToUmlClassifier;

import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
public class JavaInterfaceCreatedReaction extends AbstractReactionRealization {
  private CreateEObject<Interface> createChange;
  
  private InsertEReference<CompilationUnit, Interface> insertChange;
  
  private int currentlyMatchedChange;
  
  public JavaInterfaceCreatedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.emftext.language.java.containers.CompilationUnit affectedEObject = insertChange.getAffectedEObject();
    EReference affectedFeature = insertChange.getAffectedFeature();
    org.emftext.language.java.classifiers.Interface newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.javaToUmlClassifier.JavaInterfaceCreatedReaction.ActionUserExecution userExecution = new mir.reactions.javaToUmlClassifier.JavaInterfaceCreatedReaction.ActionUserExecution(this.executionState, this);
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
    	CreateEObject<org.emftext.language.java.classifiers.Interface> _localTypedChange = (CreateEObject<org.emftext.language.java.classifiers.Interface>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.emftext.language.java.classifiers.Interface)) {
    		return false;
    	}
    	this.createChange = (CreateEObject<org.emftext.language.java.classifiers.Interface>) change;
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
    	InsertEReference<org.emftext.language.java.containers.CompilationUnit, org.emftext.language.java.classifiers.Interface> _localTypedChange = (InsertEReference<org.emftext.language.java.containers.CompilationUnit, org.emftext.language.java.classifiers.Interface>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.emftext.language.java.containers.CompilationUnit)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("classifiers")) {
    		return false;
    	}
    	if (!(_localTypedChange.getNewValue() instanceof org.emftext.language.java.classifiers.Interface)) {
    		return false;
    	}
    	this.insertChange = (InsertEReference<org.emftext.language.java.containers.CompilationUnit, org.emftext.language.java.classifiers.Interface>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertEReference insertChange, final CompilationUnit affectedEObject, final EReference affectedFeature, final Interface newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateUmlModel(affectedEObject);
      UmlJavaTypePropagationHelper.registerPredefinedUmlPrimitiveTypes(this.correspondenceModel, affectedEObject.eResource().getResourceSet());
      _routinesFacade.createOrFindUmlInterface(newValue, affectedEObject);
    }
  }
}
