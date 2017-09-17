package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.InterfaceMethod;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlInterfaceMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlInterfaceMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceMethod jMeth, final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface, final Operation uOperation) {
      return uOperation;
    }
    
    public void update0Element(final InterfaceMethod jMeth, final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface, final Operation uOperation) {
      EList<Operation> _ownedOperations = uInterface.getOwnedOperations();
      _ownedOperations.add(uOperation);
    }
    
    public EObject getElement2(final InterfaceMethod jMeth, final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface, final Operation uOperation) {
      return jMeth;
    }
    
    public EObject getElement3(final InterfaceMethod jMeth, final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface, final Operation uOperation) {
      return uInterface;
    }
    
    public EObject getCorrepondenceSourceUInterface(final InterfaceMethod jMeth, final Interface jInterface) {
      return jInterface;
    }
    
    public void updateUOperationElement(final InterfaceMethod jMeth, final Interface jInterface, final org.eclipse.uml2.uml.Interface uInterface, final Operation uOperation) {
      uOperation.setName(jMeth.getName());
      uOperation.setIsAbstract(true);
    }
  }
  
  public CreateUmlInterfaceMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod jMeth, final Interface jInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.CreateUmlInterfaceMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.jInterface = jInterface;
  }
  
  private InterfaceMethod jMeth;
  
  private Interface jInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlInterfaceMethodRoutine with input:");
    getLogger().debug("   jMeth: " + this.jMeth);
    getLogger().debug("   jInterface: " + this.jInterface);
    
    org.eclipse.uml2.uml.Interface uInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUInterface(jMeth, jInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uInterface == null) {
    	return false;
    }
    registerObjectUnderModification(uInterface);
    org.eclipse.uml2.uml.Operation uOperation = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createOperation();
    notifyObjectCreated(uOperation);
    userExecution.updateUOperationElement(jMeth, jInterface, uInterface, uOperation);
    
    addCorrespondenceBetween(userExecution.getElement1(jMeth, jInterface, uInterface, uOperation), userExecution.getElement2(jMeth, jInterface, uInterface, uOperation), "");
    
    // val updatedElement userExecution.getElement3(jMeth, jInterface, uInterface, uOperation);
    userExecution.update0Element(jMeth, jInterface, uInterface, uOperation);
    
    postprocessElements();
    
    return true;
  }
}
