package mir.routines.javaToUmlmethod;

import java.io.IOException;
import mir.routines.javaToUmlmethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.InterfaceMethod;
import tools.vitruv.applications.umljava.util.JavaUtil;
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
    
    public EObject getCorrepondenceSourceCustomType(final InterfaceMethod jMeth, final Interface jI, final org.eclipse.uml2.uml.Interface uI) {
      ConcreteClassifier _classifierfromTypeRef = JavaUtil.getClassifierfromTypeRef(jMeth.getTypeReference());
      return _classifierfromTypeRef;
    }
    
    public EObject getElement1(final InterfaceMethod jMeth, final Interface jI, final org.eclipse.uml2.uml.Interface uI, final org.eclipse.uml2.uml.Class customType, final Operation uMeth) {
      return uMeth;
    }
    
    public void update0Element(final InterfaceMethod jMeth, final Interface jI, final org.eclipse.uml2.uml.Interface uI, final org.eclipse.uml2.uml.Class customType, final Operation uMeth) {
      EList<Operation> _ownedOperations = uI.getOwnedOperations();
      _ownedOperations.add(uMeth);
    }
    
    public EObject getElement2(final InterfaceMethod jMeth, final Interface jI, final org.eclipse.uml2.uml.Interface uI, final org.eclipse.uml2.uml.Class customType, final Operation uMeth) {
      return jMeth;
    }
    
    public void updateUMethElement(final InterfaceMethod jMeth, final Interface jI, final org.eclipse.uml2.uml.Interface uI, final org.eclipse.uml2.uml.Class customType, final Operation uMeth) {
      uMeth.setName(jMeth.getName());
    }
    
    public EObject getElement3(final InterfaceMethod jMeth, final Interface jI, final org.eclipse.uml2.uml.Interface uI, final org.eclipse.uml2.uml.Class customType, final Operation uMeth) {
      return uI;
    }
    
    public EObject getCorrepondenceSourceUI(final InterfaceMethod jMeth, final Interface jI) {
      return jI;
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
    
    org.eclipse.uml2.uml.Interface uI = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUI(jMeth, jI), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (uI == null) {
    	return;
    }
    registerObjectUnderModification(uI);
    org.eclipse.uml2.uml.Class customType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomType(jMeth, jI, uI), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customType);
    Operation uMeth = UMLFactoryImpl.eINSTANCE.createOperation();
    userExecution.updateUMethElement(jMeth, jI, uI, customType, uMeth);
    
    addCorrespondenceBetween(userExecution.getElement1(jMeth, jI, uI, customType, uMeth), userExecution.getElement2(jMeth, jI, uI, customType, uMeth), "");
    
    // val updatedElement userExecution.getElement3(jMeth, jI, uI, customType, uMeth);
    userExecution.update0Element(jMeth, jI, uI, customType, uMeth);
    
    postprocessElements();
  }
}
