package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateInterfaceRealizationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateInterfaceRealizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface, final Component umlComp, final InterfaceRealization interfaceRealization) {
      return umlComp;
    }
    
    public void updateInterfaceRealizationElement(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface, final Component umlComp, final InterfaceRealization interfaceRealization) {
      interfaceRealization.setName(classInterface.getName());
      EList<NamedElement> _clients = interfaceRealization.getClients();
      _clients.add(umlComp);
      EList<NamedElement> _suppliers = interfaceRealization.getSuppliers();
      _suppliers.add(classInterface);
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface, final Component umlComp, final InterfaceRealization interfaceRealization) {
      EList<InterfaceRealization> _interfaceRealizations = umlComp.getInterfaceRealizations();
      _interfaceRealizations.add(interfaceRealization);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface, final Component umlComp, final InterfaceRealization interfaceRealization) {
      return interfaceRealization;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface, final Component umlComp, final InterfaceRealization interfaceRealization) {
      return classInterface;
    }
    
    public EObject getCorrepondenceSourceUmlComp(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface) {
      return umlClass;
    }
  }
  
  public CreateInterfaceRealizationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateInterfaceRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;this.classInterface = classInterface;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private Interface classInterface;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInterfaceRealizationRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    getLogger().debug("   Interface: " + this.classInterface);
    
    Component umlComp = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComp(umlClass, classInterface), // correspondence source supplier
    	Component.class,
    	(Component _element) -> true, // correspondence precondition checker
    	null);
    if (umlComp == null) {
    	return;
    }
    registerObjectUnderModification(umlComp);
    InterfaceRealization interfaceRealization = UMLFactoryImpl.eINSTANCE.createInterfaceRealization();
    userExecution.updateInterfaceRealizationElement(umlClass, classInterface, umlComp, interfaceRealization);
    
    // val updatedElement userExecution.getElement1(umlClass, classInterface, umlComp, interfaceRealization);
    userExecution.update0Element(umlClass, classInterface, umlComp, interfaceRealization);
    
    addCorrespondenceBetween(userExecution.getElement2(umlClass, classInterface, umlComp, interfaceRealization), userExecution.getElement3(umlClass, classInterface, umlComp, interfaceRealization), "");
    
    postprocessElements();
  }
}
