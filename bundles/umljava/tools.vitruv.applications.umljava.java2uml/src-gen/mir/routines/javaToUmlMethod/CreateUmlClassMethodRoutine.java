package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public EObject getElement1(final ClassMethod jMeth, final ConcreteClassifier jClassifier, final Classifier uClassifier, final Operation uOperation) {
      return uOperation;
    }
    
    public EObject getCorrepondenceSourceUClassifier(final ClassMethod jMeth, final ConcreteClassifier jClassifier) {
      return jClassifier;
    }
    
    public EObject getElement2(final ClassMethod jMeth, final ConcreteClassifier jClassifier, final Classifier uClassifier, final Operation uOperation) {
      return jMeth;
    }
    
    public void updateUOperationElement(final ClassMethod jMeth, final ConcreteClassifier jClassifier, final Classifier uClassifier, final Operation uOperation) {
      uOperation.setName(jMeth.getName());
    }
    
    public void callRoutine1(final ClassMethod jMeth, final ConcreteClassifier jClassifier, final Classifier uClassifier, final Operation uOperation, @Extension final RoutinesFacade _routinesFacade) {
      if ((uClassifier instanceof org.eclipse.uml2.uml.Class)) {
        _routinesFacade.addUmlOperationToClass(((org.eclipse.uml2.uml.Class)uClassifier), uOperation);
      } else {
        if ((uClassifier instanceof Enumeration)) {
          _routinesFacade.addUmlOperationToEnum(((Enumeration)uClassifier), uOperation);
        } else {
          this.getLogger().warn(("Can not add ClassMethod to " + uClassifier));
        }
      }
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
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlClassMethodRoutine with input:");
    getLogger().debug("   jMeth: " + this.jMeth);
    getLogger().debug("   jClassifier: " + this.jClassifier);
    
    org.eclipse.uml2.uml.Classifier uClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClassifier(jMeth, jClassifier), // correspondence source supplier
    	org.eclipse.uml2.uml.Classifier.class,
    	(org.eclipse.uml2.uml.Classifier _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uClassifier == null) {
    	return false;
    }
    registerObjectUnderModification(uClassifier);
    org.eclipse.uml2.uml.Operation uOperation = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createOperation();
    notifyObjectCreated(uOperation);
    userExecution.updateUOperationElement(jMeth, jClassifier, uClassifier, uOperation);
    
    addCorrespondenceBetween(userExecution.getElement1(jMeth, jClassifier, uClassifier, uOperation), userExecution.getElement2(jMeth, jClassifier, uClassifier, uOperation), "");
    
    userExecution.callRoutine1(jMeth, jClassifier, uClassifier, uOperation, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
