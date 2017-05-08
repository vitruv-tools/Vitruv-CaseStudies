package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateClassInterfaceRealizationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateClassInterfaceRealizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement compIFRealization, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      return umlClass;
    }
    
    public void update0Element(final NamedElement compIFRealization, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      EList<InterfaceRealization> _interfaceRealizations = umlClass.getInterfaceRealizations();
      _interfaceRealizations.add(classIFRealization);
    }
    
    public EObject getElement4(final NamedElement compIFRealization, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      return classIFRealization;
    }
    
    public EObject getElement2(final NamedElement compIFRealization, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      org.eclipse.uml2.uml.Package _package = umlClass.getPackage();
      return _package;
    }
    
    public void updateClassIFRealizationElement(final NamedElement compIFRealization, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      String _name = compIFRealization.getName();
      String _plus = (_name + SharedUtil.CLASS_IF_REALIZATION_SUFFIX);
      classIFRealization.setName(_plus);
      EList<NamedElement> _clients = classIFRealization.getClients();
      _clients.add(umlClass);
    }
    
    public EObject getElement3(final NamedElement compIFRealization, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      return compIFRealization;
    }
    
    public void update1Element(final NamedElement compIFRealization, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      EList<InterfaceRealization> _interfaceRealizations = umlClass.getInterfaceRealizations();
      _interfaceRealizations.add(classIFRealization);
    }
    
    public EObject getCorrepondenceSourceUmlClass(final NamedElement compIFRealization, final Component umlComp) {
      return umlComp;
    }
  }
  
  public CreateClassInterfaceRealizationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement compIFRealization, final Component umlComp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateClassInterfaceRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compIFRealization = compIFRealization;this.umlComp = umlComp;
  }
  
  private NamedElement compIFRealization;
  
  private Component umlComp;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateClassInterfaceRealizationRoutine with input:");
    getLogger().debug("   NamedElement: " + this.compIFRealization);
    getLogger().debug("   Component: " + this.umlComp);
    
    org.eclipse.uml2.uml.Class umlClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlClass(compIFRealization, umlComp), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    if (umlClass == null) {
    	return;
    }
    registerObjectUnderModification(umlClass);
    InterfaceRealization classIFRealization = UMLFactoryImpl.eINSTANCE.createInterfaceRealization();
    userExecution.updateClassIFRealizationElement(compIFRealization, umlComp, umlClass, classIFRealization);
    
    // val updatedElement userExecution.getElement1(compIFRealization, umlComp, umlClass, classIFRealization);
    userExecution.update0Element(compIFRealization, umlComp, umlClass, classIFRealization);
    
    // val updatedElement userExecution.getElement2(compIFRealization, umlComp, umlClass, classIFRealization);
    userExecution.update1Element(compIFRealization, umlComp, umlClass, classIFRealization);
    
    addCorrespondenceBetween(userExecution.getElement3(compIFRealization, umlComp, umlClass, classIFRealization), userExecution.getElement4(compIFRealization, umlComp, umlClass, classIFRealization), "");
    
    postprocessElements();
  }
}
