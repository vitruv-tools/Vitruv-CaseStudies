package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Constructor;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlConstructorRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Constructor jConstructor, final ConcreteClassifier jClassifier, final org.eclipse.uml2.uml.Class uClassifier, final Operation uConstructor) {
      return uConstructor;
    }
    
    public void update0Element(final Constructor jConstructor, final ConcreteClassifier jClassifier, final org.eclipse.uml2.uml.Class uClassifier, final Operation uConstructor) {
      EList<Operation> _ownedOperations = uClassifier.getOwnedOperations();
      _ownedOperations.add(uConstructor);
    }
    
    public EObject getCorrepondenceSourceUClassifier(final Constructor jConstructor, final ConcreteClassifier jClassifier) {
      return jClassifier;
    }
    
    public void updateUConstructorElement(final Constructor jConstructor, final ConcreteClassifier jClassifier, final org.eclipse.uml2.uml.Class uClassifier, final Operation uConstructor) {
      uConstructor.setName(jConstructor.getName());
    }
    
    public EObject getElement2(final Constructor jConstructor, final ConcreteClassifier jClassifier, final org.eclipse.uml2.uml.Class uClassifier, final Operation uConstructor) {
      return jConstructor;
    }
    
    public EObject getElement3(final Constructor jConstructor, final ConcreteClassifier jClassifier, final org.eclipse.uml2.uml.Class uClassifier, final Operation uConstructor) {
      return uClassifier;
    }
  }
  
  public CreateUmlConstructorRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Constructor jConstructor, final ConcreteClassifier jClassifier) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.CreateUmlConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.jConstructor = jConstructor;this.jClassifier = jClassifier;
  }
  
  private Constructor jConstructor;
  
  private ConcreteClassifier jClassifier;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlConstructorRoutine with input:");
    getLogger().debug("   jConstructor: " + this.jConstructor);
    getLogger().debug("   jClassifier: " + this.jClassifier);
    
    org.eclipse.uml2.uml.Class uClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClassifier(jConstructor, jClassifier), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uClassifier == null) {
    	return false;
    }
    registerObjectUnderModification(uClassifier);
    org.eclipse.uml2.uml.Operation uConstructor = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createOperation();
    notifyObjectCreated(uConstructor);
    userExecution.updateUConstructorElement(jConstructor, jClassifier, uClassifier, uConstructor);
    
    addCorrespondenceBetween(userExecution.getElement1(jConstructor, jClassifier, uClassifier, uConstructor), userExecution.getElement2(jConstructor, jClassifier, uClassifier, uConstructor), "");
    
    // val updatedElement userExecution.getElement3(jConstructor, jClassifier, uClassifier, uConstructor);
    userExecution.update0Element(jConstructor, jClassifier, uClassifier, uConstructor);
    
    postprocessElements();
    
    return true;
  }
}
