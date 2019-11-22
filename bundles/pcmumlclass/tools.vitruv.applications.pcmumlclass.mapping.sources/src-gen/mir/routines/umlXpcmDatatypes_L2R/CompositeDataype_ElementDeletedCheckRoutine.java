package mir.routines.umlXpcmDatatypes_L2R;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDataype_ElementDeletedCheckRoutine extends AbstractRepairRoutineRealization {
  private CompositeDataype_ElementDeletedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class, final Optional<Repository> repository_correspondingTo_class, final Optional<CompositeDataType> type_correspondingTo_datatypesPackage) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Package:datatypesPackage_with_Repository:repository";
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_Repository:repository";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class, final Optional<Repository> repository_correspondingTo_class) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Package:datatypesPackage_with_CompositeDataType:type";
    }
    
    public EObject getCorrepondenceSourceType_correspondingTo_datatypesPackage(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class, final Optional<Repository> repository_correspondingTo_class) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceType_correspondingTo_class(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceRepository_correspondingTo_datatypesPackage(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class, final Optional<Repository> repository_correspondingTo_class, final Optional<CompositeDataType> type_correspondingTo_datatypesPackage) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceRepository_correspondingTo_class(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<CompositeDataType> type_correspondingTo_class, final Optional<Repository> repository_correspondingTo_class, final Optional<CompositeDataType> type_correspondingTo_datatypesPackage, final Optional<Repository> repository_correspondingTo_datatypesPackage, @Extension final RoutinesFacade _routinesFacade) {
      CompositeDataType type_ = null;
      Repository repository_ = null;
      boolean _isPresent = type_correspondingTo_class.isPresent();
      if (_isPresent) {
        type_ = type_correspondingTo_class.get();
      }
      boolean _isPresent_1 = repository_correspondingTo_class.isPresent();
      if (_isPresent_1) {
        repository_ = repository_correspondingTo_class.get();
      }
      boolean _isPresent_2 = type_correspondingTo_datatypesPackage.isPresent();
      if (_isPresent_2) {
        type_ = type_correspondingTo_datatypesPackage.get();
      }
      boolean _isPresent_3 = repository_correspondingTo_datatypesPackage.isPresent();
      if (_isPresent_3) {
        repository_ = repository_correspondingTo_datatypesPackage.get();
      }
      if (((type_ != null) && (repository_ != null))) {
        _routinesFacade.compositeDataype_DeleteMapping(type_, repository_);
      }
    }
  }
  
  public CompositeDataype_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDataype_ElementDeletedCheckRoutine with input:");
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
    	Optional<org.palladiosimulator.pcm.repository.Repository> repository_correspondingTo_class = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepository_correspondingTo_class(affectedEObject, type_correspondingTo_class), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, type_correspondingTo_class), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repository_correspondingTo_class.isPresent() ? repository_correspondingTo_class.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.CompositeDataType> type_correspondingTo_datatypesPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceType_correspondingTo_datatypesPackage(affectedEObject, type_correspondingTo_class, repository_correspondingTo_class), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CompositeDataType.class,
    		(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, type_correspondingTo_class, repository_correspondingTo_class), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(type_correspondingTo_datatypesPackage.isPresent() ? type_correspondingTo_datatypesPackage.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.Repository> repository_correspondingTo_datatypesPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepository_correspondingTo_datatypesPackage(affectedEObject, type_correspondingTo_class, repository_correspondingTo_class, type_correspondingTo_datatypesPackage), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, type_correspondingTo_class, repository_correspondingTo_class, type_correspondingTo_datatypesPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repository_correspondingTo_datatypesPackage.isPresent() ? repository_correspondingTo_datatypesPackage.get() : null);
    userExecution.callRoutine1(affectedEObject, type_correspondingTo_class, repository_correspondingTo_class, type_correspondingTo_datatypesPackage, repository_correspondingTo_datatypesPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
