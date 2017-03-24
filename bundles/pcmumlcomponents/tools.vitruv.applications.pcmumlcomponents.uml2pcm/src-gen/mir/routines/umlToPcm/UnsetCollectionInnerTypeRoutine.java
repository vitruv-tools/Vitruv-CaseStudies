package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UnsetCollectionInnerTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private UnsetCollectionInnerTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmType(final DataType umlType) {
      return umlType;
    }
    
    public void callRoutine1(final DataType umlType, final CollectionDataType pcmType, @Extension final RoutinesFacade _routinesFacade) {
      int _length = ((Object[])Conversions.unwrapArray(umlType.getOwnedAttributes(), Object.class)).length;
      boolean _equals = (_length == 0);
      if (_equals) {
        pcmType.setInnerType_CollectionDataType(null);
      } else {
        boolean foundProperty = false;
        EList<Property> _ownedAttributes = umlType.getOwnedAttributes();
        for (final Property attr : _ownedAttributes) {
          if (((!foundProperty) && (attr.getType() instanceof DataType))) {
            Type _type = attr.getType();
            _routinesFacade.changeCollectionType(umlType, ((DataType) _type));
            foundProperty = true;
          }
        }
        if ((!foundProperty)) {
          pcmType.setInnerType_CollectionDataType(null);
        }
      }
    }
  }
  
  public UnsetCollectionInnerTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType umlType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.UnsetCollectionInnerTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlType = umlType;
  }
  
  private DataType umlType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine UnsetCollectionInnerTypeRoutine with input:");
    getLogger().debug("   DataType: " + this.umlType);
    
    CollectionDataType pcmType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmType(umlType), // correspondence source supplier
    	CollectionDataType.class,
    	(CollectionDataType _element) -> true, // correspondence precondition checker
    	null);
    if (pcmType == null) {
    	return;
    }
    initializeRetrieveElementState(pcmType);
    userExecution.callRoutine1(umlType, pcmType, actionsFacade);
    
    postprocessElementStates();
  }
}
