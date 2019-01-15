package mir.routines.umlInnerDeclarationPropertyReactions;

import java.io.IOException;
import mir.routines.umlInnerDeclarationPropertyReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingInnerDeclarationRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingInnerDeclarationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType, final InnerDeclaration pcmInnerDeclaration) {
      return pcmInnerDeclaration;
    }
    
    public EObject getCorrepondenceSource1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeType(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
      return umlCompositeType;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public EObject getElement2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType, final InnerDeclaration pcmInnerDeclaration) {
      return umlProperty;
    }
    
    public String getTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType, final InnerDeclaration pcmInnerDeclaration) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public void updatePcmInnerDeclarationElement(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType, final InnerDeclaration pcmInnerDeclaration) {
      pcmInnerDeclaration.setEntityName(umlProperty.getName());
      EList<InnerDeclaration> _innerDeclaration_CompositeDataType = pcmCompositeType.getInnerDeclaration_CompositeDataType();
      _innerDeclaration_CompositeDataType.add(pcmInnerDeclaration);
    }
    
    public void callRoutine1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType, final CompositeDataType pcmCompositeType, final InnerDeclaration pcmInnerDeclaration, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.propagateTypeChange(umlProperty);
    }
  }
  
  public CreateCorrespondingInnerDeclarationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInnerDeclarationPropertyReactions.CreateCorrespondingInnerDeclarationRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlCompositeType = umlCompositeType;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlCompositeType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingInnerDeclarationRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   umlCompositeType: " + this.umlCompositeType);
    
    org.palladiosimulator.pcm.repository.CompositeDataType pcmCompositeType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCompositeType(umlProperty, umlCompositeType), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CompositeDataType.class,
    	(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, umlCompositeType), 
    	false // asserted
    	);
    if (pcmCompositeType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCompositeType);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlProperty, umlCompositeType, pcmCompositeType), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.InnerDeclaration.class,
    	(org.palladiosimulator.pcm.repository.InnerDeclaration _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlProperty, umlCompositeType, pcmCompositeType)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.InnerDeclaration pcmInnerDeclaration = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createInnerDeclaration();
    notifyObjectCreated(pcmInnerDeclaration);
    userExecution.updatePcmInnerDeclarationElement(umlProperty, umlCompositeType, pcmCompositeType, pcmInnerDeclaration);
    
    addCorrespondenceBetween(userExecution.getElement1(umlProperty, umlCompositeType, pcmCompositeType, pcmInnerDeclaration), userExecution.getElement2(umlProperty, umlCompositeType, pcmCompositeType, pcmInnerDeclaration), userExecution.getTag1(umlProperty, umlCompositeType, pcmCompositeType, pcmInnerDeclaration));
    
    userExecution.callRoutine1(umlProperty, umlCompositeType, pcmCompositeType, pcmInnerDeclaration, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
