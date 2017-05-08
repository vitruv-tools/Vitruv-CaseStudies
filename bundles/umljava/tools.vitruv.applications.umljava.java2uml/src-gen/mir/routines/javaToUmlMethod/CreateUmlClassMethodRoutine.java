package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.ClassMethod;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlClassMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlClassMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ClassMethod jMeth, final ConcreteClassifier jClassifier, final org.eclipse.uml2.uml.Class uClassifier, final Operation uOperation) {
      return uOperation;
    }
    
    public void update0Element(final ClassMethod jMeth, final ConcreteClassifier jClassifier, final org.eclipse.uml2.uml.Class uClassifier, final Operation uOperation) {
      EList<Operation> _ownedOperations = uClassifier.getOwnedOperations();
      _ownedOperations.add(uOperation);
    }
    
    public EObject getCorrepondenceSourceUClassifier(final ClassMethod jMeth, final ConcreteClassifier jClassifier) {
      return jClassifier;
    }
    
    public EObject getElement2(final ClassMethod jMeth, final ConcreteClassifier jClassifier, final org.eclipse.uml2.uml.Class uClassifier, final Operation uOperation) {
      return jMeth;
    }
    
    public EObject getElement3(final ClassMethod jMeth, final ConcreteClassifier jClassifier, final org.eclipse.uml2.uml.Class uClassifier, final Operation uOperation) {
      return uClassifier;
    }
    
    public void updateUOperationElement(final ClassMethod jMeth, final ConcreteClassifier jClassifier, final org.eclipse.uml2.uml.Class uClassifier, final Operation uOperation) {
      uOperation.setName(jMeth.getName());
    }
  }
  
  public CreateUmlClassMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ClassMethod jMeth, final ConcreteClassifier jClassifier) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.CreateUmlClassMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.jClassifier = jClassifier;
  }
  
  private ClassMethod jMeth;
  
  private ConcreteClassifier jClassifier;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlClassMethodRoutine with input:");
    getLogger().debug("   ClassMethod: " + this.jMeth);
    getLogger().debug("   ConcreteClassifier: " + this.jClassifier);
    
    org.eclipse.uml2.uml.Class uClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClassifier(jMeth, jClassifier), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    if (uClassifier == null) {
    	return;
    }
    registerObjectUnderModification(uClassifier);
    Operation uOperation = UMLFactoryImpl.eINSTANCE.createOperation();
    userExecution.updateUOperationElement(jMeth, jClassifier, uClassifier, uOperation);
    
    addCorrespondenceBetween(userExecution.getElement1(jMeth, jClassifier, uClassifier, uOperation), userExecution.getElement2(jMeth, jClassifier, uClassifier, uOperation), "");
    
    // val updatedElement userExecution.getElement3(jMeth, jClassifier, uClassifier, uOperation);
    userExecution.update0Element(jMeth, jClassifier, uClassifier, uOperation);
    
    postprocessElements();
  }
}
