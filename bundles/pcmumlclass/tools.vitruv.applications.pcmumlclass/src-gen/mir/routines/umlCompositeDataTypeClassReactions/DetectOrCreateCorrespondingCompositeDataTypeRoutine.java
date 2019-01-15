package mir.routines.umlCompositeDataTypeClassReactions;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Optional;
import mir.routines.umlCompositeDataTypeClassReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingCompositeDataTypeRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingCompositeDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
      return umlPackage;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
      return TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE;
    }
    
    public EObject getCorrepondenceSourcePcmCompositeType(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository) {
      return umlClass;
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository, final Optional<CompositeDataType> pcmCompositeType, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmCompositeType.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<CompositeDataType, Boolean> _function = (CompositeDataType type) -> {
          String _entityName = type.getEntityName();
          String _name = umlClass.getName();
          return Boolean.valueOf(Objects.equal(_entityName, _name));
        };
        final Iterable<CompositeDataType> candidates = IterableExtensions.<CompositeDataType>filter(Iterables.<CompositeDataType>filter(pcmRepository.getDataTypes__Repository(), CompositeDataType.class), _function);
        int _size = IterableExtensions.size(candidates);
        switch (_size) {
          case 0:
            _routinesFacade.createCorrespondingCompositeDataType(umlClass, umlPackage);
            break;
          case 1:
            _routinesFacade.addCorrespondenceForExistingCompositeDataType(IterableExtensions.<CompositeDataType>head(candidates), umlClass);
            break;
          default:
            {
              this.getLogger().warn((DefaultLiterals.WARNING_MULTIPLE_COMPOSITE_DATA_TYPE_CANDIDATES + umlClass));
              _routinesFacade.addCorrespondenceForExistingCompositeDataType(IterableExtensions.<CompositeDataType>head(candidates), umlClass);
            }
            break;
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingCompositeDataTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlCompositeDataTypeClassReactions.DetectOrCreateCorrespondingCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlClass = umlClass;this.umlPackage = umlPackage;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private org.eclipse.uml2.uml.Package umlPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingCompositeDataTypeRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    getLogger().debug("   umlPackage: " + this.umlPackage);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlClass, umlPackage), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlClass, umlPackage), 
    	true // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    	Optional<org.palladiosimulator.pcm.repository.CompositeDataType> pcmCompositeType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmCompositeType(umlClass, umlPackage, pcmRepository), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.CompositeDataType.class,
    		(org.palladiosimulator.pcm.repository.CompositeDataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlClass, umlPackage, pcmRepository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmCompositeType.isPresent() ? pcmCompositeType.get() : null);
    userExecution.callRoutine1(umlClass, umlPackage, pcmRepository, pcmCompositeType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
