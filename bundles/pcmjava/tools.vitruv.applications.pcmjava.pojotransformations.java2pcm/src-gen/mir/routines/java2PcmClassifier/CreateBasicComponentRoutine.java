package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.BasicComponent;
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
    
    public void updatePcmBasicComponentElement(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName, final BasicComponent pcmBasicComponent) {
      pcmBasicComponent.setEntityName(name);
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName, final BasicComponent pcmBasicComponent, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addcorrespondenceAndUpdateRepository(pcmBasicComponent, javaPackage);
    }
  }
  
  public CreateBasicComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateBasicComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;this.name = name;this.rootPackageName = rootPackageName;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  private String name;
  
  private String rootPackageName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateBasicComponentRoutine with input:");
    getLogger().debug("   javaPackage: " + this.javaPackage);
    getLogger().debug("   name: " + this.name);
    getLogger().debug("   rootPackageName: " + this.rootPackageName);
    
    org.palladiosimulator.pcm.repository.BasicComponent pcmBasicComponent = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createBasicComponent();
    notifyObjectCreated(pcmBasicComponent);
    userExecution.updatePcmBasicComponentElement(javaPackage, name, rootPackageName, pcmBasicComponent);
    
    userExecution.callRoutine1(javaPackage, name, rootPackageName, pcmBasicComponent, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
