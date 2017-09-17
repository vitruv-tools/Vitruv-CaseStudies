package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.PackageableElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType classType, final DataType compType) {
      org.eclipse.uml2.uml.Package _package = compType.getPackage();
      return _package;
    }
    
    public void update0Element(final DataType classType, final DataType compType) {
      EList<PackageableElement> _packagedElements = compType.getPackage().getPackagedElements();
      _packagedElements.add(compType);
    }
    
    public EObject getElement2(final DataType classType, final DataType compType) {
      return classType;
    }
    
    public void updateCompTypeElement(final DataType classType, final DataType compType) {
      compType.setName(classType.getName());
    }
    
    public EObject getElement3(final DataType classType, final DataType compType) {
      return compType;
    }
  }
  
  public CreateDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType classType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classType = classType;
  }
  
  private DataType classType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateDataTypeRoutine with input:");
    getLogger().debug("   classType: " + this.classType);
    
    org.eclipse.uml2.uml.DataType compType = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createDataType();
    notifyObjectCreated(compType);
    userExecution.updateCompTypeElement(classType, compType);
    
    // val updatedElement userExecution.getElement1(classType, compType);
    userExecution.update0Element(classType, compType);
    
    addCorrespondenceBetween(userExecution.getElement2(classType, compType), userExecution.getElement3(classType, compType), "");
    
    postprocessElements();
    
    return true;
  }
}
