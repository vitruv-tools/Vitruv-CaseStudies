package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateBasicComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateBasicComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package javaPackage, final String name, final BasicComponent pcmBasicComponent) {
      return pcmBasicComponent;
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package javaPackage, final String name, final BasicComponent pcmBasicComponent) {
      return javaPackage;
    }
    
    public void updatePcmBasicComponentElement(final org.emftext.language.java.containers.Package javaPackage, final String name, final BasicComponent pcmBasicComponent) {
      pcmBasicComponent.setEntityName(name);
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package javaPackage, final String name, final BasicComponent pcmBasicComponent, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addComponentToRepository(pcmBasicComponent, Java2PcmHelper.findPcmRepository(this.correspondenceModel, name));
    }
  }
  
  public CreateBasicComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage, final String name) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateBasicComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;this.name = name;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  private String name;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateBasicComponentRoutine with input:");
    getLogger().debug("   Package: " + this.javaPackage);
    getLogger().debug("   String: " + this.name);
    
    BasicComponent pcmBasicComponent = RepositoryFactoryImpl.eINSTANCE.createBasicComponent();
    notifyObjectCreated(pcmBasicComponent);
    userExecution.updatePcmBasicComponentElement(javaPackage, name, pcmBasicComponent);
    
    addCorrespondenceBetween(userExecution.getElement1(javaPackage, name, pcmBasicComponent), userExecution.getElement2(javaPackage, name, pcmBasicComponent), "");
    
    userExecution.callRoutine1(javaPackage, name, pcmBasicComponent, actionsFacade);
    
    postprocessElements();
  }
}
