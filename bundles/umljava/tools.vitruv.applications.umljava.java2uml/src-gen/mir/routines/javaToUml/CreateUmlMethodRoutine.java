package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Method;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Method jMeth, final ConcreteClassifier jClass, final org.eclipse.uml2.uml.Class uClass, final Operation uMeth) {
      return uMeth;
    }
    
    public EObject getCorrepondenceSourceUClass(final Method jMeth, final ConcreteClassifier jClass) {
      return jClass;
    }
    
    public void update0Element(final Method jMeth, final ConcreteClassifier jClass, final org.eclipse.uml2.uml.Class uClass, final Operation uMeth) {
      EList<Operation> _ownedOperations = uClass.getOwnedOperations();
      _ownedOperations.add(uMeth);
    }
    
    public EObject getElement2(final Method jMeth, final ConcreteClassifier jClass, final org.eclipse.uml2.uml.Class uClass, final Operation uMeth) {
      return jMeth;
    }
    
    public void updateUMethElement(final Method jMeth, final ConcreteClassifier jClass, final org.eclipse.uml2.uml.Class uClass, final Operation uMeth) {
      String _name = jMeth.getName();
      uMeth.setName(_name);
    }
    
    public EObject getElement3(final Method jMeth, final ConcreteClassifier jClass, final org.eclipse.uml2.uml.Class uClass, final Operation uMeth) {
      return uClass;
    }
  }
  
  public CreateUmlMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method jMeth, final ConcreteClassifier jClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.CreateUmlMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.jClass = jClass;
  }
  
  private Method jMeth;
  
  private ConcreteClassifier jClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlMethodRoutine with input:");
    getLogger().debug("   Method: " + this.jMeth);
    getLogger().debug("   ConcreteClassifier: " + this.jClass);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jMeth, jClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    if (uClass == null) {
    	return;
    }
    initializeRetrieveElementState(uClass);
    Operation uMeth = UMLFactoryImpl.eINSTANCE.createOperation();
    initializeCreateElementState(uMeth);
    userExecution.updateUMethElement(jMeth, jClass, uClass, uMeth);
    
    addCorrespondenceBetween(userExecution.getElement1(jMeth, jClass, uClass, uMeth), userExecution.getElement2(jMeth, jClass, uClass, uMeth), "");
    
    // val updatedElement userExecution.getElement3(jMeth, jClass, uClass, uMeth);
    userExecution.update0Element(jMeth, jClass, uClass, uMeth);
    
    postprocessElementStates();
  }
}
