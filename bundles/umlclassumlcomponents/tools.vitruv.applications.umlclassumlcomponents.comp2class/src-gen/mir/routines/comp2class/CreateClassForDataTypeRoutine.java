package mir.routines.comp2class;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateClassForDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateClassForDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType compType, final Model classModel, final org.eclipse.uml2.uml.Class dataTypeClass) {
      return compType;
    }
    
    public EObject getElement2(final DataType compType, final Model classModel, final org.eclipse.uml2.uml.Class dataTypeClass) {
      return dataTypeClass;
    }
    
    public String getTag1(final DataType compType, final Model classModel, final org.eclipse.uml2.uml.Class dataTypeClass) {
      return SharedUtil.DATA_TYPE_REPRESENTATION_TAG;
    }
    
    public void updateDataTypeClassElement(final DataType compType, final Model classModel, final org.eclipse.uml2.uml.Class dataTypeClass) {
      dataTypeClass.setName(compType.getName());
      final Function1<org.eclipse.uml2.uml.Package, Boolean> _function = (org.eclipse.uml2.uml.Package it) -> {
        String _name = it.getName();
        return Boolean.valueOf(Objects.equal(_name, SharedUtil.CLASS_DATATYPES_PACKAGE_NAME));
      };
      dataTypeClass.setPackage(((org.eclipse.uml2.uml.Package[])Conversions.unwrapArray(IterableExtensions.<org.eclipse.uml2.uml.Package>filter(Iterables.<org.eclipse.uml2.uml.Package>filter(classModel.getPackagedElements(), org.eclipse.uml2.uml.Package.class), _function), org.eclipse.uml2.uml.Package.class))[0]);
    }
    
    public EObject getCorrepondenceSourceClassModel(final DataType compType) {
      Model _model = compType.getModel();
      return _model;
    }
  }
  
  public CreateClassForDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType compType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateClassForDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compType = compType;
  }
  
  private DataType compType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateClassForDataTypeRoutine with input:");
    getLogger().debug("   compType: " + this.compType);
    
    org.eclipse.uml2.uml.Model classModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassModel(compType), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (classModel == null) {
    	return false;
    }
    registerObjectUnderModification(classModel);
    org.eclipse.uml2.uml.Class dataTypeClass = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createClass();
    notifyObjectCreated(dataTypeClass);
    userExecution.updateDataTypeClassElement(compType, classModel, dataTypeClass);
    
    addCorrespondenceBetween(userExecution.getElement1(compType, classModel, dataTypeClass), userExecution.getElement2(compType, classModel, dataTypeClass), userExecution.getTag1(compType, classModel, dataTypeClass));
    
    postprocessElements();
    
    return true;
  }
}
