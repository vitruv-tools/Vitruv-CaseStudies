package mir.routines.java2pcm;

import java.io.IOException;
import mir.routines.java2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.system.impl.SystemFactoryImpl;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
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
    
    public EObject getElement1(final org.emftext.language.java.containers.Package javaPackage, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public void updatePcmSystemElement(final org.emftext.language.java.containers.Package javaPackage, final org.palladiosimulator.pcm.system.System pcmSystem) {
      pcmSystem.setEntityName(Java2PcmHelper.getLastPackageName(javaPackage.getName()));
      String _entityName = pcmSystem.getEntityName();
      String _plus = ("model/" + _entityName);
      String _plus_1 = (_plus + ".system");
      this.persistProjectRelative(javaPackage, pcmSystem, _plus_1);
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package javaPackage, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return javaPackage;
    }
  }
  
  public CreateSystemRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2pcm.CreateSystemRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2pcm.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSystemRoutine with input:");
    getLogger().debug("   Package: " + this.javaPackage);
    
    org.palladiosimulator.pcm.system.System pcmSystem = SystemFactoryImpl.eINSTANCE.createSystem();
    userExecution.updatePcmSystemElement(javaPackage, pcmSystem);
    
    addCorrespondenceBetween(userExecution.getElement1(javaPackage, pcmSystem), userExecution.getElement2(javaPackage, pcmSystem), "");
    
    postprocessElements();
  }
}
