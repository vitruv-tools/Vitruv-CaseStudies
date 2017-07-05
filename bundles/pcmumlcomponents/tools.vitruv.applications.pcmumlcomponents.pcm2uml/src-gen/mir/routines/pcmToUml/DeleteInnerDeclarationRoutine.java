package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
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
      EList<Property> _ownedAttributes = compositeType.getOwnedAttributes();
      _ownedAttributes.remove(umlProperty);
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
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteInnerDeclarationRoutine with input:");
    getLogger().debug("   CompositeDataType: " + this.dataType);
    getLogger().debug("   InnerDeclaration: " + this.innerDeclaration);
    
    DataType compositeType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeType(dataType, innerDeclaration), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	null);
    if (compositeType == null) {
    	return;
    }
    registerObjectUnderModification(compositeType);
    Property umlProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlProperty(dataType, innerDeclaration, compositeType), // correspondence source supplier
    	Property.class,
    	(Property _element) -> true, // correspondence precondition checker
    	null);
    if (umlProperty == null) {
    	return;
    }
    registerObjectUnderModification(umlProperty);
    userExecution.callRoutine1(dataType, innerDeclaration, compositeType, umlProperty, actionsFacade);
    
    postprocessElements();
  }
}
