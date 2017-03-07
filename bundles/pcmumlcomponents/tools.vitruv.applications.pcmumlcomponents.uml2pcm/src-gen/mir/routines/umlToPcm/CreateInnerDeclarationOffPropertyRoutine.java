package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateInnerDeclarationOffPropertyRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateInnerDeclarationOffPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property property, final CompositeDataType pcmCompositeType, final InnerDeclaration declaration) {
      return pcmCompositeType;
    }
    
    public void update0Element(final Property property, final CompositeDataType pcmCompositeType, final InnerDeclaration declaration) {
      EList<InnerDeclaration> _innerDeclaration_CompositeDataType = pcmCompositeType.getInnerDeclaration_CompositeDataType();
      _innerDeclaration_CompositeDataType.add(declaration);
    }
    
    public EObject getCorrepondenceSourcePcmCompositeType(final Property property) {
      Element _owner = property.getOwner();
      return _owner;
    }
    
    public EObject getElement2(final Property property, final CompositeDataType pcmCompositeType, final InnerDeclaration declaration) {
      return property;
    }
    
    public EObject getElement3(final Property property, final CompositeDataType pcmCompositeType, final InnerDeclaration declaration) {
      return declaration;
    }
    
    public void updateDeclarationElement(final Property property, final CompositeDataType pcmCompositeType, final InnerDeclaration declaration) {
      String _name = property.getName();
      declaration.setEntityName(_name);
    }
  }
  
  public CreateInnerDeclarationOffPropertyRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property property) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreateInnerDeclarationOffPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.property = property;
  }
  
  private Property property;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInnerDeclarationOffPropertyRoutine with input:");
    getLogger().debug("   Property: " + this.property);
    
    CompositeDataType pcmCompositeType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeType(property), // correspondence source supplier
    	CompositeDataType.class,
    	(CompositeDataType _element) -> true, // correspondence precondition checker
    	null);
    if (pcmCompositeType == null) {
    	return;
    }
    initializeRetrieveElementState(pcmCompositeType);
    InnerDeclaration declaration = RepositoryFactoryImpl.eINSTANCE.createInnerDeclaration();
    initializeCreateElementState(declaration);
    userExecution.updateDeclarationElement(property, pcmCompositeType, declaration);
    
    // val updatedElement userExecution.getElement1(property, pcmCompositeType, declaration);
    userExecution.update0Element(property, pcmCompositeType, declaration);
    
    addCorrespondenceBetween(userExecution.getElement2(property, pcmCompositeType, declaration), userExecution.getElement3(property, pcmCompositeType, declaration), "");
    
    postprocessElementStates();
  }
}
