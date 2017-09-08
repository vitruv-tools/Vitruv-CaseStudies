package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface pcmInterface, final Model umlModel, final org.eclipse.uml2.uml.Interface umlInterface) {
      return umlModel;
    }
    
    public void update0Element(final Interface pcmInterface, final Model umlModel, final org.eclipse.uml2.uml.Interface umlInterface) {
      EList<PackageableElement> _packagedElements = umlModel.getPackagedElements();
      _packagedElements.add(umlInterface);
    }
    
    public void updateUmlInterfaceElement(final Interface pcmInterface, final Model umlModel, final org.eclipse.uml2.uml.Interface umlInterface) {
      umlInterface.setName(pcmInterface.getEntityName());
    }
    
    public EObject getElement2(final Interface pcmInterface, final Model umlModel, final org.eclipse.uml2.uml.Interface umlInterface) {
      return umlInterface;
    }
    
    public EObject getElement3(final Interface pcmInterface, final Model umlModel, final org.eclipse.uml2.uml.Interface umlInterface) {
      return pcmInterface;
    }
    
    public EObject getCorrepondenceSourceUmlModel(final Interface pcmInterface) {
      Repository _repository__Interface = pcmInterface.getRepository__Interface();
      return _repository__Interface;
    }
  }
  
  public CreateUmlInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface pcmInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateUmlInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmInterface = pcmInterface;
  }
  
  private Interface pcmInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlInterfaceRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    
    org.eclipse.uml2.uml.Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(pcmInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlModel == null) {
    	return false;
    }
    registerObjectUnderModification(umlModel);
    org.eclipse.uml2.uml.Interface umlInterface = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createInterface();
    notifyObjectCreated(umlInterface);
    userExecution.updateUmlInterfaceElement(pcmInterface, umlModel, umlInterface);
    
    // val updatedElement userExecution.getElement1(pcmInterface, umlModel, umlInterface);
    userExecution.update0Element(pcmInterface, umlModel, umlInterface);
    
    addCorrespondenceBetween(userExecution.getElement2(pcmInterface, umlModel, umlInterface), userExecution.getElement3(pcmInterface, umlModel, umlInterface), "");
    
    postprocessElements();
    
    return true;
  }
}
