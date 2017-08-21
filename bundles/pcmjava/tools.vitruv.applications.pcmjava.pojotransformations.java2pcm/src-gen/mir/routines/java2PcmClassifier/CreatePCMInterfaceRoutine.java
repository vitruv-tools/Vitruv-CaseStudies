package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreatePCMInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePCMInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updatePcmIfaceElement(final Interface javaInterface, final CompilationUnit compilationUnit, final OperationInterface pcmIface) {
      pcmIface.setEntityName(javaInterface.getName());
    }
    
    public void callRoutine1(final Interface javaInterface, final CompilationUnit compilationUnit, final OperationInterface pcmIface, @Extension final RoutinesFacade _routinesFacade) {
      boolean _equals = IterableExtensions.<String>last(compilationUnit.getNamespaces()).equals("contracts");
      boolean _not = (!_equals);
      if (_not) {
        String _name = javaInterface.getName();
        String _plus = ("The created interface is not in the contracts packages. Should an architectural interface be created for the interface " + _name);
        final String userMsg = (_plus + " ?");
        final String[] selections = { "yes", "no" };
        final int selected = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, userMsg, selections);
        if ((selected == 0)) {
          final Repository repo = Java2PcmHelper.findPcmRepository(this.correspondenceModel);
          _routinesFacade.addCorrespondanceToInterfaceAndUpdateRepository(pcmIface, repo, javaInterface, compilationUnit);
        }
      } else {
        final Repository repo_1 = Java2PcmHelper.findPcmRepository(this.correspondenceModel);
        _routinesFacade.addCorrespondanceToInterfaceAndUpdateRepository(pcmIface, repo_1, javaInterface, compilationUnit);
      }
    }
  }
  
  public CreatePCMInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface javaInterface, final CompilationUnit compilationUnit) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreatePCMInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaInterface = javaInterface;this.compilationUnit = compilationUnit;
  }
  
  private Interface javaInterface;
  
  private CompilationUnit compilationUnit;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.javaInterface);
    getLogger().debug("   CompilationUnit: " + this.compilationUnit);
    
    OperationInterface pcmIface = RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    notifyObjectCreated(pcmIface);
    userExecution.updatePcmIfaceElement(javaInterface, compilationUnit, pcmIface);
    
    userExecution.callRoutine1(javaInterface, compilationUnit, pcmIface, actionsFacade);
    
    postprocessElements();
  }
}
