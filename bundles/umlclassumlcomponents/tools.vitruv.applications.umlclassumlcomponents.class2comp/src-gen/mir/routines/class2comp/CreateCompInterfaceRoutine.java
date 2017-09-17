package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCompInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface classInterface, final Model compModel, final Interface compInterface) {
      return compModel;
    }
    
    public void update0Element(final Interface classInterface, final Model compModel, final Interface compInterface) {
      EList<PackageableElement> _packagedElements = compModel.getPackagedElements();
      _packagedElements.add(compInterface);
    }
    
    public void updateCompInterfaceElement(final Interface classInterface, final Model compModel, final Interface compInterface) {
      String _name = classInterface.getName();
      String _plus = (_name + SharedUtil.COMP_INTERFACE_SUFFIX);
      compInterface.setName(_plus);
    }
    
    public EObject getElement2(final Interface classInterface, final Model compModel, final Interface compInterface) {
      return classInterface;
    }
    
    public EObject getCorrepondenceSourceCompModel(final Interface classInterface) {
      Model _model = classInterface.getPackage().getModel();
      return _model;
    }
    
    public EObject getElement3(final Interface classInterface, final Model compModel, final Interface compInterface) {
      return compInterface;
    }
  }
  
  public CreateCompInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface classInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateCompInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classInterface = classInterface;
  }
  
  private Interface classInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompInterfaceRoutine with input:");
    getLogger().debug("   classInterface: " + this.classInterface);
    
    org.eclipse.uml2.uml.Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(classInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compModel == null) {
    	return false;
    }
    registerObjectUnderModification(compModel);
    org.eclipse.uml2.uml.Interface compInterface = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createInterface();
    notifyObjectCreated(compInterface);
    userExecution.updateCompInterfaceElement(classInterface, compModel, compInterface);
    
    // val updatedElement userExecution.getElement1(classInterface, compModel, compInterface);
    userExecution.update0Element(classInterface, compModel, compInterface);
    
    addCorrespondenceBetween(userExecution.getElement2(classInterface, compModel, compInterface), userExecution.getElement3(classInterface, compModel, compInterface), "");
    
    postprocessElements();
    
    return true;
  }
}
