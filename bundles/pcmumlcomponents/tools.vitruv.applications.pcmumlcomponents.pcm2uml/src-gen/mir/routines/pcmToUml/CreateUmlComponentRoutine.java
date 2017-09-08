package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final RepositoryComponent pcmComponent, final String correspondenceTag, final Model umlModel, final Component umlComponent) {
      return umlModel;
    }
    
    public void update0Element(final RepositoryComponent pcmComponent, final String correspondenceTag, final Model umlModel, final Component umlComponent) {
      EList<PackageableElement> _packagedElements = umlModel.getPackagedElements();
      _packagedElements.add(umlComponent);
    }
    
    public EObject getElement2(final RepositoryComponent pcmComponent, final String correspondenceTag, final Model umlModel, final Component umlComponent) {
      return pcmComponent;
    }
    
    public EObject getElement3(final RepositoryComponent pcmComponent, final String correspondenceTag, final Model umlModel, final Component umlComponent) {
      return umlComponent;
    }
    
    public void updateUmlComponentElement(final RepositoryComponent pcmComponent, final String correspondenceTag, final Model umlModel, final Component umlComponent) {
      umlComponent.setName(pcmComponent.getEntityName());
    }
    
    public String getTag1(final RepositoryComponent pcmComponent, final String correspondenceTag, final Model umlModel, final Component umlComponent) {
      return correspondenceTag;
    }
    
    public EObject getCorrepondenceSourceUmlModel(final RepositoryComponent pcmComponent, final String correspondenceTag) {
      Repository _repository__RepositoryComponent = pcmComponent.getRepository__RepositoryComponent();
      return _repository__RepositoryComponent;
    }
  }
  
  public CreateUmlComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent, final String correspondenceTag) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateUmlComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.correspondenceTag = correspondenceTag;
  }
  
  private RepositoryComponent pcmComponent;
  
  private String correspondenceTag;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlComponentRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   correspondenceTag: " + this.correspondenceTag);
    
    org.eclipse.uml2.uml.Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(pcmComponent, correspondenceTag), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlModel == null) {
    	return false;
    }
    registerObjectUnderModification(umlModel);
    org.eclipse.uml2.uml.Component umlComponent = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createComponent();
    notifyObjectCreated(umlComponent);
    userExecution.updateUmlComponentElement(pcmComponent, correspondenceTag, umlModel, umlComponent);
    
    // val updatedElement userExecution.getElement1(pcmComponent, correspondenceTag, umlModel, umlComponent);
    userExecution.update0Element(pcmComponent, correspondenceTag, umlModel, umlComponent);
    
    addCorrespondenceBetween(userExecution.getElement2(pcmComponent, correspondenceTag, umlModel, umlComponent), userExecution.getElement3(pcmComponent, correspondenceTag, umlModel, umlComponent), userExecution.getTag1(pcmComponent, correspondenceTag, umlModel, umlComponent));
    
    postprocessElements();
    
    return true;
  }
}
