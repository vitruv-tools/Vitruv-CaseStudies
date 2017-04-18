package mir.routines.javaToUmlmethod;

import java.io.IOException;
import mir.routines.javaToUmlmethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
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
    
    public EObject getElement1(final InterfaceMethod jMeth, final Interface jI, final org.eclipse.uml2.uml.Interface uInterface, final Operation uOperation) {
      return uOperation;
    }
    
    public void update0Element(final InterfaceMethod jMeth, final Interface jI, final org.eclipse.uml2.uml.Interface uInterface, final Operation uOperation) {
      EList<Operation> _ownedOperations = uInterface.getOwnedOperations();
      _ownedOperations.add(uOperation);
    }
    
    public EObject getElement2(final InterfaceMethod jMeth, final Interface jI, final org.eclipse.uml2.uml.Interface uInterface, final Operation uOperation) {
      return jMeth;
    }
    
    public EObject getElement3(final InterfaceMethod jMeth, final Interface jI, final org.eclipse.uml2.uml.Interface uInterface, final Operation uOperation) {
      return uInterface;
    }
    
    public EObject getCorrepondenceSourceUInterface(final InterfaceMethod jMeth, final Interface jI) {
      return jI;
    }
    
    public void updateUOperationElement(final InterfaceMethod jMeth, final Interface jI, final org.eclipse.uml2.uml.Interface uInterface, final Operation uOperation) {
      uOperation.setName(jMeth.getName());
    }
  }
  
  public CreateUmlInterfaceMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod jMeth, final Interface jI) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlmethod.CreateUmlInterfaceMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlmethod.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.jI = jI;
  }
  
  private InterfaceMethod jMeth;
  
  private Interface jI;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlInterfaceMethodRoutine with input:");
    getLogger().debug("   InterfaceMethod: " + this.jMeth);
    getLogger().debug("   Interface: " + this.jI);
    
    org.eclipse.uml2.uml.Interface uInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUInterface(jMeth, jI), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (uInterface == null) {
    	return;
    }
    registerObjectUnderModification(uInterface);
    Operation uOperation = UMLFactoryImpl.eINSTANCE.createOperation();
    userExecution.updateUOperationElement(jMeth, jI, uInterface, uOperation);
    
    addCorrespondenceBetween(userExecution.getElement1(jMeth, jI, uInterface, uOperation), userExecution.getElement2(jMeth, jI, uInterface, uOperation), "");
    
    // val updatedElement userExecution.getElement3(jMeth, jI, uInterface, uOperation);
    userExecution.update0Element(jMeth, jI, uInterface, uOperation);
    
    postprocessElements();
  }
}
