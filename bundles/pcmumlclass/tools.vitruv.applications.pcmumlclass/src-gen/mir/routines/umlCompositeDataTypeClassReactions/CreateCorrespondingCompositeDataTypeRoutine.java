package mir.routines.umlCompositeDataTypeClassReactions;

import java.io.IOException;
import mir.routines.umlCompositeDataTypeClassReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingCompositeDataTypeRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingCompositeDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updatePcmCompositeTypeElement(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository, final CompositeDataType pcmCompositeType) {
      pcmCompositeType.setEntityName(umlClass.getName());
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository, final CompositeDataType pcmCompositeType) {
      return pcmCompositeType;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
      return umlPackage;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
      return TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository, final CompositeDataType pcmCompositeType) {
      return umlClass;
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository, final CompositeDataType pcmCompositeType) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
  }
  
  public CreateCorrespondingCompositeDataTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlCompositeDataTypeClassReactions.CreateCorrespondingCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlClass = umlClass;this.umlPackage = umlPackage;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private org.eclipse.uml2.uml.Package umlPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingCompositeDataTypeRoutine with input:");
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
    org.palladiosimulator.pcm.repository.CompositeDataType pcmCompositeType = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createCompositeDataType();
    notifyObjectCreated(pcmCompositeType);
    userExecution.updatePcmCompositeTypeElement(umlClass, umlPackage, pcmRepository, pcmCompositeType);
    
    addCorrespondenceBetween(userExecution.getElement1(umlClass, umlPackage, pcmRepository, pcmCompositeType), userExecution.getElement2(umlClass, umlPackage, pcmRepository, pcmCompositeType), userExecution.getTag1(umlClass, umlPackage, pcmRepository, pcmCompositeType));
    
    postprocessElements();
    
    return true;
  }
}
