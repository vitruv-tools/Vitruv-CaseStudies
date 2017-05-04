package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface, final Component umlComp, final Usage usage) {
      return umlComp;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface, final Component umlComp, final Usage usage) {
      EList<PackageableElement> _packagedElements = umlComp.getPackagedElements();
      _packagedElements.add(usage);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface, final Component umlComp, final Usage usage) {
      return usage;
    }
    
    public void updateUsageElement(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface, final Component umlComp, final Usage usage) {
      usage.setName(classInterface.getName());
      EList<NamedElement> _clients = usage.getClients();
      _clients.add(umlComp);
      EList<NamedElement> _suppliers = usage.getSuppliers();
      _suppliers.add(classInterface);
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface, final Component umlComp, final Usage usage) {
      return classInterface;
    }
    
    public EObject getCorrepondenceSourceUmlComp(final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface) {
      return umlClass;
    }
  }
  
  public AddRequiredRoleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final Interface classInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.AddRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;this.classInterface = classInterface;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private Interface classInterface;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddRequiredRoleRoutine with input:");
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
    Usage usage = UMLFactoryImpl.eINSTANCE.createUsage();
    userExecution.updateUsageElement(umlClass, classInterface, umlComp, usage);
    
    // val updatedElement userExecution.getElement1(umlClass, classInterface, umlComp, usage);
    userExecution.update0Element(umlClass, classInterface, umlComp, usage);
    
    addCorrespondenceBetween(userExecution.getElement2(umlClass, classInterface, umlComp, usage), userExecution.getElement3(umlClass, classInterface, umlComp, usage), "");
    
    postprocessElements();
  }
}
