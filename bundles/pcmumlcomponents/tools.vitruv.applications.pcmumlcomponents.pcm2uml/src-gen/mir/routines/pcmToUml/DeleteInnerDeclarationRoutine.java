package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteInnerDeclarationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteInnerDeclarationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlProperty(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final DataType compositeType) {
      return innerDeclaration;
    }
    
    public EObject getCorrepondenceSourceCompositeType(final CompositeDataType dataType, final InnerDeclaration innerDeclaration) {
      return dataType;
    }
    
    public void callRoutine1(final CompositeDataType dataType, final InnerDeclaration innerDeclaration, final DataType compositeType, final Property umlProperty, @Extension final RoutinesFacade _routinesFacade) {
      compositeType.getOwnedAttributes().remove(umlProperty);
    }
  }
  
  public DeleteInnerDeclarationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType dataType, final InnerDeclaration innerDeclaration) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.DeleteInnerDeclarationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.dataType = dataType;this.innerDeclaration = innerDeclaration;
  }
  
  private CompositeDataType dataType;
  
  private InnerDeclaration innerDeclaration;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteInnerDeclarationRoutine with input:");
    getLogger().debug("   dataType: " + this.dataType);
    getLogger().debug("   innerDeclaration: " + this.innerDeclaration);
    
    org.eclipse.uml2.uml.DataType compositeType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeType(dataType, innerDeclaration), // correspondence source supplier
    	org.eclipse.uml2.uml.DataType.class,
    	(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    	null, 
    	true // asserted
    	);
    if (compositeType == null) {
    	return false;
    }
    registerObjectUnderModification(compositeType);
    org.eclipse.uml2.uml.Property umlProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlProperty(dataType, innerDeclaration, compositeType), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	null, 
    	true // asserted
    	);
    if (umlProperty == null) {
    	return false;
    }
    registerObjectUnderModification(umlProperty);
    userExecution.callRoutine1(dataType, innerDeclaration, compositeType, umlProperty, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
