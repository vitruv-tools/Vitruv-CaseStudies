package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Usage;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUsageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUsageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface, final Usage usage) {
      return umlComp;
    }
    
    public void update0Element(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface, final Usage usage) {
      EList<PackageableElement> _packagedElements = umlComp.getPackagedElements();
      _packagedElements.add(usage);
    }
    
    public EObject getElement2(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface, final Usage usage) {
      return usage;
    }
    
    public void updateUsageElement(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface, final Usage usage) {
      String _name = classIFRealization.getName();
      String _plus = (_name + SharedUtil.COMP_IFR_AND_USAGE_SUFFIX);
      usage.setName(_plus);
      EList<NamedElement> _clients = usage.getClients();
      _clients.add(umlComp);
      EList<NamedElement> _suppliers = usage.getSuppliers();
      _suppliers.add(compInterface);
    }
    
    public EObject getElement3(final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface, final Usage usage) {
      return classIFRealization;
    }
  }
  
  public CreateUsageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization classIFRealization, final Component umlComp, final Interface compInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateUsageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classIFRealization = classIFRealization;this.umlComp = umlComp;this.compInterface = compInterface;
  }
  
  private InterfaceRealization classIFRealization;
  
  private Component umlComp;
  
  private Interface compInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUsageRoutine with input:");
    getLogger().debug("   classIFRealization: " + this.classIFRealization);
    getLogger().debug("   umlComp: " + this.umlComp);
    getLogger().debug("   compInterface: " + this.compInterface);
    
    org.eclipse.uml2.uml.Usage usage = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createUsage();
    notifyObjectCreated(usage);
    userExecution.updateUsageElement(classIFRealization, umlComp, compInterface, usage);
    
    // val updatedElement userExecution.getElement1(classIFRealization, umlComp, compInterface, usage);
    userExecution.update0Element(classIFRealization, umlComp, compInterface, usage);
    
    addCorrespondenceBetween(userExecution.getElement2(classIFRealization, umlComp, compInterface, usage), userExecution.getElement3(classIFRealization, umlComp, compInterface, usage), "");
    
    postprocessElements();
    
    return true;
  }
}
