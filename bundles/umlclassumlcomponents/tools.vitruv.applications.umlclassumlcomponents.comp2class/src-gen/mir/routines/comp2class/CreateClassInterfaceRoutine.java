package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.PackageableElement;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateClassInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateClassInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface compInterface, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface) {
      org.eclipse.uml2.uml.Package _package = umlClass.getPackage();
      return _package;
    }
    
    public void updateClassInterfaceElement(final Interface compInterface, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface) {
      String _name = compInterface.getName();
      String _plus = (_name + SharedUtil.CLASS_INTERFACE_SUFFIX);
      classInterface.setName(_plus);
    }
    
    public void update0Element(final Interface compInterface, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface) {
      EList<PackageableElement> _packagedElements = umlClass.getPackage().getPackagedElements();
      _packagedElements.add(classInterface);
    }
    
    public EObject getCorrepondenceSource1(final Interface compInterface, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass) {
      return compInterface;
    }
    
    public EObject getElement2(final Interface compInterface, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface) {
      return compInterface;
    }
    
    public EObject getElement3(final Interface compInterface, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface) {
      return classInterface;
    }
    
    public EObject getCorrepondenceSourceUmlClass(final Interface compInterface, final Component umlComp) {
      return umlComp;
    }
  }
  
  public CreateClassInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface compInterface, final Component umlComp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateClassInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compInterface = compInterface;this.umlComp = umlComp;
  }
  
  private Interface compInterface;
  
  private Component umlComp;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateClassInterfaceRoutine with input:");
    getLogger().debug("   compInterface: " + this.compInterface);
    getLogger().debug("   umlComp: " + this.umlComp);
    
    org.eclipse.uml2.uml.Class umlClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlClass(compInterface, umlComp), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlClass == null) {
    	return false;
    }
    registerObjectUnderModification(umlClass);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(compInterface, umlComp, umlClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Interface classInterface = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createInterface();
    notifyObjectCreated(classInterface);
    userExecution.updateClassInterfaceElement(compInterface, umlComp, umlClass, classInterface);
    
    // val updatedElement userExecution.getElement1(compInterface, umlComp, umlClass, classInterface);
    userExecution.update0Element(compInterface, umlComp, umlClass, classInterface);
    
    addCorrespondenceBetween(userExecution.getElement2(compInterface, umlComp, umlClass, classInterface), userExecution.getElement3(compInterface, umlComp, umlClass, classInterface), "");
    
    postprocessElements();
    
    return true;
  }
}
