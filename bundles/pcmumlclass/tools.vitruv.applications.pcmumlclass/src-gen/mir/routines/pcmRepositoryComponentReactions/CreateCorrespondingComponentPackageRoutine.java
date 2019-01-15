package mir.routines.pcmRepositoryComponentReactions;

import java.io.IOException;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingComponentPackageRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingComponentPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSource1(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage) {
      return pcmComponent;
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent, final Repository pcmRepository) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlRepositoryPackage(final RepositoryComponent pcmComponent, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public EObject getElement2(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return umlComponentPackage;
    }
    
    public String getTag1(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public void updateUmlComponentPackageElement(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      umlComponentPackage.setName(StringExtensions.toFirstLower(pcmComponent.getEntityName()));
      EList<PackageableElement> _packagedElements = umlRepositoryPackage.getPackagedElements();
      _packagedElements.add(umlComponentPackage);
    }
  }
  
  public CreateCorrespondingComponentPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent, final Repository pcmRepository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.CreateCorrespondingComponentPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.pcmRepository = pcmRepository;
  }
  
  private RepositoryComponent pcmComponent;
  
  private Repository pcmRepository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingComponentPackageRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   pcmRepository: " + this.pcmRepository);
    
    org.eclipse.uml2.uml.Package umlRepositoryPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRepositoryPackage(pcmComponent, pcmRepository), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmComponent, pcmRepository), 
    	false // asserted
    	);
    if (umlRepositoryPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlRepositoryPackage);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmComponent, pcmRepository, umlRepositoryPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmComponent, pcmRepository, umlRepositoryPackage)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Package umlComponentPackage = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(umlComponentPackage);
    userExecution.updateUmlComponentPackageElement(pcmComponent, pcmRepository, umlRepositoryPackage, umlComponentPackage);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmComponent, pcmRepository, umlRepositoryPackage, umlComponentPackage), userExecution.getElement2(pcmComponent, pcmRepository, umlRepositoryPackage, umlComponentPackage), userExecution.getTag1(pcmComponent, pcmRepository, umlRepositoryPackage, umlComponentPackage));
    
    postprocessElements();
    
    return true;
  }
}
