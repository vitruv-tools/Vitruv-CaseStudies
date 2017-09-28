package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateSystemRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateSystemRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package javaPackage, final String name, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public void updatePcmSystemElement(final org.emftext.language.java.containers.Package javaPackage, final String name, final org.palladiosimulator.pcm.system.System pcmSystem) {
      pcmSystem.setEntityName(name);
      String _entityName = pcmSystem.getEntityName();
      String _plus = ("model/" + _entityName);
      String _plus_1 = (_plus + ".system");
      this.persistProjectRelative(javaPackage, pcmSystem, _plus_1);
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package javaPackage, final String name, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return javaPackage;
    }
  }
  
  public CreateSystemRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage, final String name) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateSystemRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;this.name = name;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  private String name;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSystemRoutine with input:");
    getLogger().debug("   javaPackage: " + this.javaPackage);
    getLogger().debug("   name: " + this.name);
    
    org.palladiosimulator.pcm.system.System pcmSystem = org.palladiosimulator.pcm.system.impl.SystemFactoryImpl.eINSTANCE.createSystem();
    notifyObjectCreated(pcmSystem);
    userExecution.updatePcmSystemElement(javaPackage, name, pcmSystem);
    
    addCorrespondenceBetween(userExecution.getElement1(javaPackage, name, pcmSystem), userExecution.getElement2(javaPackage, name, pcmSystem), "");
    
    postprocessElements();
    
    return true;
  }
}
