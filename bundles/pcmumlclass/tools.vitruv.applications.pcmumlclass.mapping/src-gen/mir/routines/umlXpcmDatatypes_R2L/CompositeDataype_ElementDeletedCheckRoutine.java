package mir.routines.umlXpcmDatatypes_R2L;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type, final Optional<org.eclipse.uml2.uml.Package> datatypesPackage_correspondingTo_type, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_repository) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Package:datatypesPackage_with_Repository:repository";
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Package:datatypesPackage_with_CompositeDataType:type";
    }
    
    public EObject getCorrepondenceSourceDatatypesPackage_correspondingTo_type(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type) {
      return affectedEObject;
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type, final Optional<org.eclipse.uml2.uml.Package> datatypesPackage_correspondingTo_type) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceDatatypesPackage_correspondingTo_repository(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type, final Optional<org.eclipse.uml2.uml.Package> datatypesPackage_correspondingTo_type, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_repository) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceClass_correspondingTo_repository(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type, final Optional<org.eclipse.uml2.uml.Package> datatypesPackage_correspondingTo_type) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceClass_correspondingTo_type(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type, final Optional<org.eclipse.uml2.uml.Package> datatypesPackage_correspondingTo_type, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Package> datatypesPackage_correspondingTo_repository, @Extension final RoutinesFacade _routinesFacade) {
      org.eclipse.uml2.uml.Class class_ = null;
      org.eclipse.uml2.uml.Package datatypesPackage_ = null;
      boolean _isPresent = class_correspondingTo_type.isPresent();
      if (_isPresent) {
        class_ = class_correspondingTo_type.get();
      }
      boolean _isPresent_1 = datatypesPackage_correspondingTo_type.isPresent();
      if (_isPresent_1) {
        datatypesPackage_ = datatypesPackage_correspondingTo_type.get();
      }
      boolean _isPresent_2 = class_correspondingTo_repository.isPresent();
      if (_isPresent_2) {
        class_ = class_correspondingTo_repository.get();
      }
      boolean _isPresent_3 = datatypesPackage_correspondingTo_repository.isPresent();
      if (_isPresent_3) {
        datatypesPackage_ = datatypesPackage_correspondingTo_repository.get();
      }
      if (((class_ != null) && (datatypesPackage_ != null))) {
        _routinesFacade.compositeDataype_DeleteMapping(class_, datatypesPackage_);
      }
    }
  }
  
  public CompositeDataype_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDataype_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceClass_correspondingTo_type(affectedEObject), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(class_correspondingTo_type.isPresent() ? class_correspondingTo_type.get() : null);
    	Optional<org.eclipse.uml2.uml.Package> datatypesPackage_correspondingTo_type = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceDatatypesPackage_correspondingTo_type(affectedEObject, class_correspondingTo_type), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, class_correspondingTo_type), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(datatypesPackage_correspondingTo_type.isPresent() ? datatypesPackage_correspondingTo_type.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_repository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceClass_correspondingTo_repository(affectedEObject, class_correspondingTo_type, datatypesPackage_correspondingTo_type), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, class_correspondingTo_type, datatypesPackage_correspondingTo_type), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(class_correspondingTo_repository.isPresent() ? class_correspondingTo_repository.get() : null);
    	Optional<org.eclipse.uml2.uml.Package> datatypesPackage_correspondingTo_repository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceDatatypesPackage_correspondingTo_repository(affectedEObject, class_correspondingTo_type, datatypesPackage_correspondingTo_type, class_correspondingTo_repository), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, class_correspondingTo_type, datatypesPackage_correspondingTo_type, class_correspondingTo_repository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(datatypesPackage_correspondingTo_repository.isPresent() ? datatypesPackage_correspondingTo_repository.get() : null);
    userExecution.callRoutine1(affectedEObject, class_correspondingTo_type, datatypesPackage_correspondingTo_type, class_correspondingTo_repository, datatypesPackage_correspondingTo_repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
