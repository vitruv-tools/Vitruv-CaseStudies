package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompDataTypeAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCompDataTypeAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCompDataType(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute) {
      return umlClass;
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute, final DataType compDataType, final Property compAttribute) {
      return compDataType;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute, final DataType compDataType, final Property compAttribute) {
      EList<NamedElement> _members = compDataType.getMembers();
      _members.add(compAttribute);
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute) {
      return SharedUtil.DATA_TYPE_REPRESENTATION_TAG;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute, final DataType compDataType, final Property compAttribute) {
      return classAttribute;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute, final DataType compDataType, final Property compAttribute) {
      return compAttribute;
    }
    
    public void updateCompAttributeElement(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute, final DataType compDataType, final Property compAttribute) {
      String _name = classAttribute.getName();
      boolean _tripleEquals = (_name == null);
      if (_tripleEquals) {
        compAttribute.setName("DefaultAttributeName");
      } else {
        compAttribute.setName(classAttribute.getName());
      }
      compAttribute.setDatatype(classAttribute.getDatatype());
    }
  }
  
  public CreateCompDataTypeAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateCompDataTypeAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;this.classAttribute = classAttribute;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private Property classAttribute;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompDataTypeAttributeRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    getLogger().debug("   Property: " + this.classAttribute);
    
    DataType compDataType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompDataType(umlClass, classAttribute), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlClass, classAttribute));
    if (compDataType == null) {
    	return;
    }
    registerObjectUnderModification(compDataType);
    Property compAttribute = UMLFactoryImpl.eINSTANCE.createProperty();
    userExecution.updateCompAttributeElement(umlClass, classAttribute, compDataType, compAttribute);
    
    // val updatedElement userExecution.getElement1(umlClass, classAttribute, compDataType, compAttribute);
    userExecution.update0Element(umlClass, classAttribute, compDataType, compAttribute);
    
    addCorrespondenceBetween(userExecution.getElement2(umlClass, classAttribute, compDataType, compAttribute), userExecution.getElement3(umlClass, classAttribute, compDataType, compAttribute), "");
    
    postprocessElements();
  }
}
