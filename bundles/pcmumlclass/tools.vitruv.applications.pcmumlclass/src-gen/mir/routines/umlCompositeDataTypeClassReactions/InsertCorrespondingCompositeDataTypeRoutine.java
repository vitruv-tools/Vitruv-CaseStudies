package mir.routines.umlCompositeDataTypeClassReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlCompositeDataTypeClassReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingCompositeDataTypeRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingCompositeDataTypeRoutine.ActionUserExecution userExecution;
  
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
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final Optional<Repository> pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmRepository.isPresent();
      if (_isPresent) {
        _routinesFacade.detectOrCreateCorrespondingCompositeDataType(umlClass, umlPackage);
        _routinesFacade.moveCorrespondingCompositeDataType(umlClass, umlPackage);
      } else {
        _routinesFacade.deleteCorrespondingCompositeDataType(umlClass);
      }
    }
  }
  
  public InsertCorrespondingCompositeDataTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlCompositeDataTypeClassReactions.InsertCorrespondingCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlClass = umlClass;this.umlPackage = umlPackage;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private org.eclipse.uml2.uml.Package umlPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingCompositeDataTypeRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    getLogger().debug("   umlPackage: " + this.umlPackage);
    
    	Optional<org.palladiosimulator.pcm.repository.Repository> pcmRepository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmRepository(umlClass, umlPackage), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlClass, umlPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmRepository.isPresent() ? pcmRepository.get() : null);
    userExecution.callRoutine1(umlClass, umlPackage, pcmRepository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
