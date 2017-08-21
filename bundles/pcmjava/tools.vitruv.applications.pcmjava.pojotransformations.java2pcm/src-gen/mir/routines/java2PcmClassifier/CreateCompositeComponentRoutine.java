package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
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
    
    public void updatePcmCompositeComponentElement(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName, final CompositeComponent pcmCompositeComponent) {
      pcmCompositeComponent.setEntityName(name);
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName, final CompositeComponent pcmCompositeComponent, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addCorrespondanceAndUpdateRepository(pcmCompositeComponent, Java2PcmHelper.findPcmRepository(this.correspondenceModel), javaPackage);
    }
  }
  
  public CreateCompositeComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateCompositeComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;this.name = name;this.rootPackageName = rootPackageName;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  private String name;
  
  private String rootPackageName;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompositeComponentRoutine with input:");
    getLogger().debug("   Package: " + this.javaPackage);
    getLogger().debug("   String: " + this.name);
    getLogger().debug("   String: " + this.rootPackageName);
    
    CompositeComponent pcmCompositeComponent = RepositoryFactoryImpl.eINSTANCE.createCompositeComponent();
    notifyObjectCreated(pcmCompositeComponent);
    userExecution.updatePcmCompositeComponentElement(javaPackage, name, rootPackageName, pcmCompositeComponent);
    
    userExecution.callRoutine1(javaPackage, name, rootPackageName, pcmCompositeComponent, actionsFacade);
    
    postprocessElements();
  }
}
