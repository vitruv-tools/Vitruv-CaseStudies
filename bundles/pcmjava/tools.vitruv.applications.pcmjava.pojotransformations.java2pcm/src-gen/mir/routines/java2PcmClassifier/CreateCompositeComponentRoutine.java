package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompositeComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCompositeComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updatePcmCompositeComponentElement(final org.emftext.language.java.containers.Package javaPackage, final String name, final CompositeComponent pcmCompositeComponent) {
      pcmCompositeComponent.setEntityName(name);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package javaPackage, final String name, final CompositeComponent pcmCompositeComponent) {
      return pcmCompositeComponent;
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package javaPackage, final String name, final CompositeComponent pcmCompositeComponent) {
      return javaPackage;
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package javaPackage, final String name, final CompositeComponent pcmCompositeComponent, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addComponentToRepository(pcmCompositeComponent, Java2PcmHelper.findPcmRepository(this.correspondenceModel, name));
    }
  }
  
  public CreateCompositeComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage, final String name) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateCompositeComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;this.name = name;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  private String name;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompositeComponentRoutine with input:");
    getLogger().debug("   Package: " + this.javaPackage);
    getLogger().debug("   String: " + this.name);
    
    CompositeComponent pcmCompositeComponent = RepositoryFactoryImpl.eINSTANCE.createCompositeComponent();
    notifyObjectCreated(pcmCompositeComponent);
    userExecution.updatePcmCompositeComponentElement(javaPackage, name, pcmCompositeComponent);
    
    addCorrespondenceBetween(userExecution.getElement1(javaPackage, name, pcmCompositeComponent), userExecution.getElement2(javaPackage, name, pcmCompositeComponent), "");
    
    userExecution.callRoutine1(javaPackage, name, pcmCompositeComponent, actionsFacade);
    
    postprocessElements();
  }
}
