package mir.routines.umlXpcmDatatypes_R2L;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type, final Optional<Generalization> generalization_correspondingTo_type, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_parentType) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Generalization:generalization_with_CompositeDataType:parentType";
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
    }
    
    public EObject getCorrepondenceSourceGeneralization_correspondingTo_type(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type) {
      return affectedEObject;
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Generalization:generalization_with_CompositeDataType:type";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type, final Optional<Generalization> generalization_correspondingTo_type) {
      return "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:parentType";
    }
    
    public EObject getCorrepondenceSourceClass_correspondingTo_parentType(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type, final Optional<Generalization> generalization_correspondingTo_type) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceGeneralization_correspondingTo_parentType(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type, final Optional<Generalization> generalization_correspondingTo_type, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_parentType) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceClass_correspondingTo_type(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_type, final Optional<Generalization> generalization_correspondingTo_type, final Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_parentType, final Optional<Generalization> generalization_correspondingTo_parentType, @Extension final RoutinesFacade _routinesFacade) {
      org.eclipse.uml2.uml.Class class_ = null;
      Generalization generalization_ = null;
      boolean _isPresent = class_correspondingTo_type.isPresent();
      if (_isPresent) {
        class_ = class_correspondingTo_type.get();
      }
      boolean _isPresent_1 = generalization_correspondingTo_type.isPresent();
      if (_isPresent_1) {
        generalization_ = generalization_correspondingTo_type.get();
      }
      boolean _isPresent_2 = class_correspondingTo_parentType.isPresent();
      if (_isPresent_2) {
        class_ = class_correspondingTo_parentType.get();
      }
      boolean _isPresent_3 = generalization_correspondingTo_parentType.isPresent();
      if (_isPresent_3) {
        generalization_ = generalization_correspondingTo_parentType.get();
      }
      if (((class_ != null) && (generalization_ != null))) {
        _routinesFacade.compositeDatatypeParent_DeleteMapping(class_, generalization_);
      }
    }
  }
  
  public CompositeDatatypeParent_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDatatypeParent_ElementDeletedCheckRoutine with input:");
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
    	Optional<org.eclipse.uml2.uml.Generalization> generalization_correspondingTo_type = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceGeneralization_correspondingTo_type(affectedEObject, class_correspondingTo_type), // correspondence source supplier
    		org.eclipse.uml2.uml.Generalization.class,
    		(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, class_correspondingTo_type), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(generalization_correspondingTo_type.isPresent() ? generalization_correspondingTo_type.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> class_correspondingTo_parentType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceClass_correspondingTo_parentType(affectedEObject, class_correspondingTo_type, generalization_correspondingTo_type), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, class_correspondingTo_type, generalization_correspondingTo_type), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(class_correspondingTo_parentType.isPresent() ? class_correspondingTo_parentType.get() : null);
    	Optional<org.eclipse.uml2.uml.Generalization> generalization_correspondingTo_parentType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceGeneralization_correspondingTo_parentType(affectedEObject, class_correspondingTo_type, generalization_correspondingTo_type, class_correspondingTo_parentType), // correspondence source supplier
    		org.eclipse.uml2.uml.Generalization.class,
    		(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, class_correspondingTo_type, generalization_correspondingTo_type, class_correspondingTo_parentType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(generalization_correspondingTo_parentType.isPresent() ? generalization_correspondingTo_parentType.get() : null);
    userExecution.callRoutine1(affectedEObject, class_correspondingTo_type, generalization_correspondingTo_type, class_correspondingTo_parentType, generalization_correspondingTo_parentType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
