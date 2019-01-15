package mir.routines.pcmCompositeDataTypeReactions;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmCompositeDataTypeReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingCompositeTypeClassRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingCompositeTypeClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlCompositeClass(final CompositeDataType pcmType, final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlDatatypesPackage) {
      return pcmType;
    }
    
    public String getRetrieveTag1(final CompositeDataType pcmType, final Repository pcmRepo) {
      return TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlDatatypesPackage(final CompositeDataType pcmType, final Repository pcmRepo) {
      return pcmRepo;
    }
    
    public String getRetrieveTag2(final CompositeDataType pcmType, final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlDatatypesPackage) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public void callRoutine1(final CompositeDataType pcmType, final Repository pcmRepo, final org.eclipse.uml2.uml.Package umlDatatypesPackage, final Optional<org.eclipse.uml2.uml.Class> umlCompositeClass, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlCompositeClass.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<org.eclipse.uml2.uml.Class, Boolean> _function = (org.eclipse.uml2.uml.Class it) -> {
          String _name = it.getName();
          String _entityName = pcmType.getEntityName();
          return Boolean.valueOf(Objects.equal(_name, _entityName));
        };
        final org.eclipse.uml2.uml.Class umlCompositeCandidate = IterableExtensions.<org.eclipse.uml2.uml.Class>findFirst(Iterables.<org.eclipse.uml2.uml.Class>filter(umlDatatypesPackage.getPackagedElements(), org.eclipse.uml2.uml.Class.class), _function);
        if ((umlCompositeCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingCompositeTypeClass(pcmType, umlCompositeCandidate);
        } else {
          _routinesFacade.createCorrespondingCompositetypeClass(pcmType, pcmRepo);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingCompositeTypeClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType pcmType, final Repository pcmRepo) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmCompositeDataTypeReactions.DetectOrCreateCorrespondingCompositeTypeClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.pcmRepo = pcmRepo;
  }
  
  private CompositeDataType pcmType;
  
  private Repository pcmRepo;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingCompositeTypeClassRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   pcmRepo: " + this.pcmRepo);
    
    org.eclipse.uml2.uml.Package umlDatatypesPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlDatatypesPackage(pcmType, pcmRepo), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmType, pcmRepo), 
    	false // asserted
    	);
    if (umlDatatypesPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlDatatypesPackage);
    	Optional<org.eclipse.uml2.uml.Class> umlCompositeClass = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlCompositeClass(pcmType, pcmRepo, umlDatatypesPackage), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmType, pcmRepo, umlDatatypesPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlCompositeClass.isPresent() ? umlCompositeClass.get() : null);
    userExecution.callRoutine1(pcmType, pcmRepo, umlDatatypesPackage, umlCompositeClass, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
