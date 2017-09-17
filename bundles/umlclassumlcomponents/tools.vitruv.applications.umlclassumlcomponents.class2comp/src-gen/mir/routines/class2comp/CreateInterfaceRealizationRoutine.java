package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
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
    
    public EObject getElement1(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface, final InterfaceRealization compIFRealization) {
      return umlComp;
    }
    
    public void update0Element(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface, final InterfaceRealization compIFRealization) {
      EList<InterfaceRealization> _interfaceRealizations = umlComp.getInterfaceRealizations();
      _interfaceRealizations.add(compIFRealization);
    }
    
    public void updateCompIFRealizationElement(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface, final InterfaceRealization compIFRealization) {
      String _name = classIFRealization.getName();
      String _plus = (_name + SharedUtil.COMP_IFR_AND_USAGE_SUFFIX);
      compIFRealization.setName(_plus);
      EList<NamedElement> _clients = compIFRealization.getClients();
      _clients.add(umlComp);
      compIFRealization.setContract(compInterface);
      EList<NamedElement> _suppliers = compIFRealization.getSuppliers();
      _suppliers.add(compInterface);
    }
    
    public EObject getElement2(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface, final InterfaceRealization compIFRealization) {
      return compIFRealization;
    }
    
    public EObject getElement3(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface, final InterfaceRealization compIFRealization) {
      return classIFRealization;
    }
  }
  
  public CreateInterfaceRealizationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateInterfaceRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classIFRealization = classIFRealization;this.umlComp = umlComp;this.compInterface = compInterface;
  }
  
  private InterfaceRealization classIFRealization;
  
  private Component umlComp;
  
  private Interface compInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInterfaceRealizationRoutine with input:");
    getLogger().debug("   classIFRealization: " + this.classIFRealization);
    getLogger().debug("   umlComp: " + this.umlComp);
    getLogger().debug("   compInterface: " + this.compInterface);
    
    org.eclipse.uml2.uml.InterfaceRealization compIFRealization = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createInterfaceRealization();
    notifyObjectCreated(compIFRealization);
    userExecution.updateCompIFRealizationElement(classIFRealization, umlComp, compInterface, compIFRealization);
    
    // val updatedElement userExecution.getElement1(classIFRealization, umlComp, compInterface, compIFRealization);
    userExecution.update0Element(classIFRealization, umlComp, compInterface, compIFRealization);
    
    addCorrespondenceBetween(userExecution.getElement2(classIFRealization, umlComp, compInterface, compIFRealization), userExecution.getElement3(classIFRealization, umlComp, compInterface, compIFRealization), "");
    
    postprocessElements();
    
    return true;
  }
}
