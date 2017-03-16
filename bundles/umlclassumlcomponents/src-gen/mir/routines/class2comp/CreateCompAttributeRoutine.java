package mir.routines.class2comp;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCompAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute, final Component component, final Property componentAttribute) {
      return component;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute, final Component component, final Property componentAttribute) {
      EList<NamedElement> _members = component.getMembers();
      _members.add(componentAttribute);
    }
    
    public EObject getCorrepondenceSourceComponent(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute) {
      return umlClass;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute, final Component component, final Property componentAttribute) {
      return classAttribute;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute, final Component component, final Property componentAttribute) {
      return componentAttribute;
    }
    
    public void updateComponentAttributeElement(final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute, final Component component, final Property componentAttribute) {
      String _name = classAttribute.getName();
      boolean _equals = Objects.equal(_name, null);
      if (_equals) {
        componentAttribute.setName("DefaultAttributeName");
      } else {
        componentAttribute.setName(classAttribute.getName());
      }
      componentAttribute.setDatatype(classAttribute.getDatatype());
    }
  }
  
  public CreateCompAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final Property classAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateCompAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;this.classAttribute = classAttribute;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private Property classAttribute;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompAttributeRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    getLogger().debug("   Property: " + this.classAttribute);
    
    Component component = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComponent(umlClass, classAttribute), // correspondence source supplier
    	Component.class,
    	(Component _element) -> true, // correspondence precondition checker
    	null);
    if (component == null) {
    	return;
    }
    registerObjectUnderModification(component);
    Property componentAttribute = UMLFactoryImpl.eINSTANCE.createProperty();
    userExecution.updateComponentAttributeElement(umlClass, classAttribute, component, componentAttribute);
    
    // val updatedElement userExecution.getElement1(umlClass, classAttribute, component, componentAttribute);
    userExecution.update0Element(umlClass, classAttribute, component, componentAttribute);
    
    addCorrespondenceBetween(userExecution.getElement2(umlClass, classAttribute, component, componentAttribute), userExecution.getElement3(umlClass, classAttribute, component, componentAttribute), "");
    
    postprocessElements();
  }
}
