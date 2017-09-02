package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.applications.pcmumlcomponents.uml2pcm.UmlToPcmTypesUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameCollectionTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameCollectionTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement umlElement, final CollectionDataType pcmCollectionType) {
      return pcmCollectionType;
    }
    
    public void update0Element(final NamedElement umlElement, final CollectionDataType pcmCollectionType) {
      String _name = umlElement.getName();
      String _plus = (_name + UmlToPcmTypesUtil.COLLECTION_TYPE_SUFFIX);
      pcmCollectionType.setEntityName(_plus);
    }
    
    public String getRetrieveTag1(final NamedElement umlElement) {
      return UmlToPcmTypesUtil.COLLECTION_TYPE_TAG;
    }
    
    public EObject getCorrepondenceSourcePcmCollectionType(final NamedElement umlElement) {
      return umlElement;
    }
  }
  
  public RenameCollectionTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement umlElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.RenameCollectionTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlElement = umlElement;
  }
  
  private NamedElement umlElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameCollectionTypeRoutine with input:");
    getLogger().debug("   umlElement: " + this.umlElement);
    
    org.palladiosimulator.pcm.repository.CollectionDataType pcmCollectionType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCollectionType(umlElement), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CollectionDataType.class,
    	(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlElement), 
    	false // asserted
    	);
    if (pcmCollectionType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCollectionType);
    // val updatedElement userExecution.getElement1(umlElement, pcmCollectionType);
    userExecution.update0Element(umlElement, pcmCollectionType);
    
    postprocessElements();
    
    return true;
  }
}
