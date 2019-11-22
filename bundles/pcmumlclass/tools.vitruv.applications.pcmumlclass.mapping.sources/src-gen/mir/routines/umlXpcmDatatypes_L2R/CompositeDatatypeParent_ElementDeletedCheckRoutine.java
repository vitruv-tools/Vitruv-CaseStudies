package mir.routines.umlXpcmDatatypes_L2R;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDatatypeParent_ElementDeletedCheckRoutine extends AbstractRepairRoutineRealization {
  private CompositeDatatypeParent_ElementDeletedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceType_correspondingTo_generalization(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class, final Optional<CompositeDataType> parentType_correspondingTo_class) {
      return affectedEObject;
    }
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class, final Optional<CompositeDataType> parentType_correspondingTo_class, final Optional<CompositeDataType> type_correspondingTo_generalization) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Generalization:generalization_with_CompositeDataType:parentType";
    }
    
    public EObject getCorrepondenceSourceParentType_correspondingTo_generalization(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class, final Optional<CompositeDataType> parentType_correspondingTo_class, final Optional<CompositeDataType> type_correspondingTo_generalization) {
      return affectedEObject;
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:parentType";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class, final Optional<CompositeDataType> parentType_correspondingTo_class) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Generalization:generalization_with_CompositeDataType:type";
    }
    
    public EObject getCorrepondenceSourceParentType_correspondingTo_class(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceType_correspondingTo_class(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class, final Optional<CompositeDataType> parentType_correspondingTo_class, final Optional<CompositeDataType> type_correspondingTo_generalization, final Optional<CompositeDataType> parentType_correspondingTo_generalization, @Extension final RoutinesFacade _routinesFacade) {
      CompositeDataType type_ = null;
      CompositeDataType parentType_ = null;
      boolean _isPresent = type_correspondingTo_class.isPresent();
      if (_isPresent) {
        type_ = type_correspondingTo_class.get();
      }
      boolean _isPresent_1 = parentType_correspondingTo_class.isPresent();
      if (_isPresent_1) {
        parentType_ = parentType_correspondingTo_class.get();
      }
      boolean _isPresent_2 = type_correspondingTo_generalization.isPresent();
      if (_isPresent_2) {
        type_ = type_correspondingTo_generalization.get();
      }
      boolean _isPresent_3 = parentType_correspondingTo_generalization.isPresent();
      if (_isPresent_3) {
        parentType_ = parentType_correspondingTo_generalization.get();
      }
      if (((type_ != null) && (parentType_ != null))) {
        _routinesFacade.compositeDatatypeParent_DeleteMapping(type_, parentType_);
      }
    }
  }
  
  public CompositeDatatypeParent_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDatatypeParent_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.palladiosimulator.pcm.repository.CompositeDataType> type_correspondingTo_class = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceType_correspondingTo_class(affectedEObject), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CompositeDataType.class,
    		(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(type_correspondingTo_class.isPresent() ? type_correspondingTo_class.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.CompositeDataType> parentType_correspondingTo_class = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceParentType_correspondingTo_class(affectedEObject, type_correspondingTo_class), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CompositeDataType.class,
    		(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, type_correspondingTo_class), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(parentType_correspondingTo_class.isPresent() ? parentType_correspondingTo_class.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.CompositeDataType> type_correspondingTo_generalization = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceType_correspondingTo_generalization(affectedEObject, type_correspondingTo_class, parentType_correspondingTo_class), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CompositeDataType.class,
    		(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, type_correspondingTo_class, parentType_correspondingTo_class), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(type_correspondingTo_generalization.isPresent() ? type_correspondingTo_generalization.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.CompositeDataType> parentType_correspondingTo_generalization = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceParentType_correspondingTo_generalization(affectedEObject, type_correspondingTo_class, parentType_correspondingTo_class, type_correspondingTo_generalization), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CompositeDataType.class,
    		(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, type_correspondingTo_class, parentType_correspondingTo_class, type_correspondingTo_generalization), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(parentType_correspondingTo_generalization.isPresent() ? parentType_correspondingTo_generalization.get() : null);
    userExecution.callRoutine1(affectedEObject, type_correspondingTo_class, parentType_correspondingTo_class, type_correspondingTo_generalization, parentType_correspondingTo_generalization, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
