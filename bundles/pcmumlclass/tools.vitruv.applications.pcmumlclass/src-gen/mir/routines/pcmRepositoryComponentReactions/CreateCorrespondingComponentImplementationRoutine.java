package mir.routines.pcmRepositoryComponentReactions;

import java.io.IOException;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PackageableElement;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingComponentImplementationRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingComponentImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateUmlComponentImplementationElement(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      String _entityName = pcmComponent.getEntityName();
      String _plus = (_entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX);
      umlComponentImplementation.setName(_plus);
      umlComponentImplementation.setIsFinalSpecialization(true);
      EList<PackageableElement> _packagedElements = umlComponentPackage.getPackagedElements();
      _packagedElements.add(umlComponentImplementation);
    }
    
    public EObject getElement1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSource1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSourceUmlComponentPackage(final RepositoryComponent pcmComponent) {
      return pcmComponent;
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getElement2(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return umlComponentImplementation;
    }
    
    public String getTag1(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
  }
  
  public CreateCorrespondingComponentImplementationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.CreateCorrespondingComponentImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;
  }
  
  private RepositoryComponent pcmComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingComponentImplementationRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    
    org.eclipse.uml2.uml.Package umlComponentPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentPackage(pcmComponent), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmComponent), 
    	false // asserted
    	);
    if (umlComponentPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentPackage);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmComponent, umlComponentPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmComponent, umlComponentPackage)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Class umlComponentImplementation = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createClass();
    notifyObjectCreated(umlComponentImplementation);
    userExecution.updateUmlComponentImplementationElement(pcmComponent, umlComponentPackage, umlComponentImplementation);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmComponent, umlComponentPackage, umlComponentImplementation), userExecution.getElement2(pcmComponent, umlComponentPackage, umlComponentImplementation), userExecution.getTag1(pcmComponent, umlComponentPackage, umlComponentImplementation));
    
    postprocessElements();
    
    return true;
  }
}
