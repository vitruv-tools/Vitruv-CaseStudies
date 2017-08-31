package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlPropertyForDatatypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlPropertyForDatatypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType type, final InnerDeclaration counterpart, final DataType owner, final Property property) {
      return owner;
    }
    
    public void update0Element(final DataType type, final InnerDeclaration counterpart, final DataType owner, final Property property) {
      EList<Property> _ownedAttributes = owner.getOwnedAttributes();
      _ownedAttributes.add(property);
    }
    
    public void updatePropertyElement(final DataType type, final InnerDeclaration counterpart, final DataType owner, final Property property) {
      property.setName(counterpart.getEntityName());
      property.setType(type);
    }
    
    public EObject getElement2(final DataType type, final InnerDeclaration counterpart, final DataType owner, final Property property) {
      return counterpart;
    }
    
    public EObject getElement3(final DataType type, final InnerDeclaration counterpart, final DataType owner, final Property property) {
      return property;
    }
  }
  
  public CreateUmlPropertyForDatatypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType type, final InnerDeclaration counterpart, final DataType owner) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateUmlPropertyForDatatypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.type = type;this.counterpart = counterpart;this.owner = owner;
  }
  
  private DataType type;
  
  private InnerDeclaration counterpart;
  
  private DataType owner;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlPropertyForDatatypeRoutine with input:");
    getLogger().debug("   type: " + this.type);
    getLogger().debug("   counterpart: " + this.counterpart);
    getLogger().debug("   owner: " + this.owner);
    
    org.eclipse.uml2.uml.Property property = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createProperty();
    notifyObjectCreated(property);
    userExecution.updatePropertyElement(type, counterpart, owner, property);
    
    // val updatedElement userExecution.getElement1(type, counterpart, owner, property);
    userExecution.update0Element(type, counterpart, owner, property);
    
    addCorrespondenceBetween(userExecution.getElement2(type, counterpart, owner, property), userExecution.getElement3(type, counterpart, owner, property), "");
    
    postprocessElements();
    
    return true;
  }
}
