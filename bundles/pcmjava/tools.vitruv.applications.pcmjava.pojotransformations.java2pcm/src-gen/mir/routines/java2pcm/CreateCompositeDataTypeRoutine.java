package mir.routines.java2pcm;

import java.io.IOException;
import mir.routines.java2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompositeDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCompositeDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceClsPackage(final org.emftext.language.java.classifiers.Class cls) {
      return cls;
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class cls, final org.emftext.language.java.containers.Package clsPackage, final Repository pcmRepository, final CompositeDataType pcmCompositeDataType) {
      return pcmCompositeDataType;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.emftext.language.java.classifiers.Class cls, final org.emftext.language.java.containers.Package clsPackage) {
      return clsPackage;
    }
    
    public String getRetrieveTag1(final org.emftext.language.java.classifiers.Class cls) {
      return "datatypes";
    }
    
    public void updatePcmCompositeDataTypeElement(final org.emftext.language.java.classifiers.Class cls, final org.emftext.language.java.containers.Package clsPackage, final Repository pcmRepository, final CompositeDataType pcmCompositeDataType) {
      pcmCompositeDataType.setEntityName(cls.getName());
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class cls, final org.emftext.language.java.containers.Package clsPackage, final Repository pcmRepository, final CompositeDataType pcmCompositeDataType) {
      return cls;
    }
  }
  
  public CreateCompositeDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class cls) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2pcm.CreateCompositeDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2pcm.RoutinesFacade(getExecutionState(), this);
    this.cls = cls;
  }
  
  private org.emftext.language.java.classifiers.Class cls;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompositeDataTypeRoutine with input:");
    getLogger().debug("   Class: " + this.cls);
    
    org.emftext.language.java.containers.Package clsPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClsPackage(cls), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(cls));
    if (clsPackage == null) {
    	return;
    }
    registerObjectUnderModification(clsPackage);
    Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(cls, clsPackage), // correspondence source supplier
    	Repository.class,
    	(Repository _element) -> true, // correspondence precondition checker
    	null);
    if (pcmRepository == null) {
    	return;
    }
    registerObjectUnderModification(pcmRepository);
    CompositeDataType pcmCompositeDataType = RepositoryFactoryImpl.eINSTANCE.createCompositeDataType();
    userExecution.updatePcmCompositeDataTypeElement(cls, clsPackage, pcmRepository, pcmCompositeDataType);
    
    addCorrespondenceBetween(userExecution.getElement1(cls, clsPackage, pcmRepository, pcmCompositeDataType), userExecution.getElement2(cls, clsPackage, pcmRepository, pcmCompositeDataType), "");
    
    postprocessElements();
  }
}
