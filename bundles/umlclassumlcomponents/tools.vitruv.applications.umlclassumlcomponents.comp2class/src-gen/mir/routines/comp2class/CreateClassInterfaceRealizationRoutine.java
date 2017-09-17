package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
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
    
    public EObject getElement1(final NamedElement iFRealizationOrUsage, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      return umlClass;
    }
    
    public void update0Element(final NamedElement iFRealizationOrUsage, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      EList<InterfaceRealization> _interfaceRealizations = umlClass.getInterfaceRealizations();
      _interfaceRealizations.add(classIFRealization);
    }
    
    public EObject getElement4(final NamedElement iFRealizationOrUsage, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      return classIFRealization;
    }
    
    public EObject getElement2(final NamedElement iFRealizationOrUsage, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      org.eclipse.uml2.uml.Package _package = umlClass.getPackage();
      return _package;
    }
    
    public void updateClassIFRealizationElement(final NamedElement iFRealizationOrUsage, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      String _name = iFRealizationOrUsage.getName();
      String _plus = (_name + SharedUtil.CLASS_IFR_AND_USAGE_SUFFIX);
      classIFRealization.setName(_plus);
      EList<NamedElement> _clients = classIFRealization.getClients();
      _clients.add(umlClass);
    }
    
    public EObject getElement3(final NamedElement iFRealizationOrUsage, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      return iFRealizationOrUsage;
    }
    
    public void update1Element(final NamedElement iFRealizationOrUsage, final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final InterfaceRealization classIFRealization) {
      EList<InterfaceRealization> _interfaceRealizations = umlClass.getInterfaceRealizations();
      _interfaceRealizations.add(classIFRealization);
    }
    
    public EObject getCorrepondenceSourceUmlClass(final NamedElement iFRealizationOrUsage, final Component umlComp) {
      return umlComp;
    }
  }
  
  public CreateClassInterfaceRealizationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement iFRealizationOrUsage, final Component umlComp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateClassInterfaceRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.iFRealizationOrUsage = iFRealizationOrUsage;this.umlComp = umlComp;
  }
  
  private NamedElement iFRealizationOrUsage;
  
  private Component umlComp;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateClassInterfaceRealizationRoutine with input:");
    getLogger().debug("   iFRealizationOrUsage: " + this.iFRealizationOrUsage);
    getLogger().debug("   umlComp: " + this.umlComp);
    
    org.eclipse.uml2.uml.Class umlClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlClass(iFRealizationOrUsage, umlComp), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlClass == null) {
    	return false;
    }
    registerObjectUnderModification(umlClass);
    org.eclipse.uml2.uml.InterfaceRealization classIFRealization = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createInterfaceRealization();
    notifyObjectCreated(classIFRealization);
    userExecution.updateClassIFRealizationElement(iFRealizationOrUsage, umlComp, umlClass, classIFRealization);
    
    // val updatedElement userExecution.getElement1(iFRealizationOrUsage, umlComp, umlClass, classIFRealization);
    userExecution.update0Element(iFRealizationOrUsage, umlComp, umlClass, classIFRealization);
    
    // val updatedElement userExecution.getElement2(iFRealizationOrUsage, umlComp, umlClass, classIFRealization);
    userExecution.update1Element(iFRealizationOrUsage, umlComp, umlClass, classIFRealization);
    
    addCorrespondenceBetween(userExecution.getElement3(iFRealizationOrUsage, umlComp, umlClass, classIFRealization), userExecution.getElement4(iFRealizationOrUsage, umlComp, umlClass, classIFRealization), "");
    
    postprocessElements();
    
    return true;
  }
}
