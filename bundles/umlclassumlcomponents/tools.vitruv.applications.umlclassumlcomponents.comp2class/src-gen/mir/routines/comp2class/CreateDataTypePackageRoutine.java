package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateDataTypePackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateDataTypePackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Model compModel, final Model classModel, final org.eclipse.uml2.uml.Package dataTypePackage) {
      return classModel;
    }
    
    public void update0Element(final Model compModel, final Model classModel, final org.eclipse.uml2.uml.Package dataTypePackage) {
      EList<PackageableElement> _packagedElements = classModel.getPackagedElements();
      _packagedElements.add(dataTypePackage);
    }
    
    public EObject getCorrepondenceSourceClassModel(final Model compModel) {
      return compModel;
    }
    
    public void updateDataTypePackageElement(final Model compModel, final Model classModel, final org.eclipse.uml2.uml.Package dataTypePackage) {
      dataTypePackage.setName(SharedUtil.CLASS_DATATYPES_PACKAGE_NAME);
    }
  }
  
  public CreateDataTypePackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model compModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateDataTypePackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compModel = compModel;
  }
  
  private Model compModel;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateDataTypePackageRoutine with input:");
    getLogger().debug("   compModel: " + this.compModel);
    
    org.eclipse.uml2.uml.Model classModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassModel(compModel), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (classModel == null) {
    	return false;
    }
    registerObjectUnderModification(classModel);
    org.eclipse.uml2.uml.Package dataTypePackage = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(dataTypePackage);
    userExecution.updateDataTypePackageElement(compModel, classModel, dataTypePackage);
    
    // val updatedElement userExecution.getElement1(compModel, classModel, dataTypePackage);
    userExecution.update0Element(compModel, classModel, dataTypePackage);
    
    postprocessElements();
    
    return true;
  }
}
