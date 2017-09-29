package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmUserSelection;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

/**
 * *
 * nUser selects if interface should be created if interface was not created into contract package.
 *  
 */
@SuppressWarnings("all")
public class CreatedInterfaceNotInContractsRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatedInterfaceNotInContractsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Interface javaInterface, final OperationInterface pcmInterface, final CompilationUnit compilationUnit, @Extension final RoutinesFacade _routinesFacade) {
      String _name = javaInterface.getName();
      String _plus = ("The created interface is not in the contracts packages. Should an architectural interface be created for the interface " + _name);
      final String userMsg = (_plus + " ?");
      String _message = Java2PcmUserSelection.SELECT_CREATE_INTERFACE_NOT_IN_CONTRACTS.getMessage();
      String _message_1 = Java2PcmUserSelection.SELECT_DONT_CREATE_INTERFACE_NOT_IN_CONTRACTS.getMessage();
      final String[] selections = new String[] { _message, _message_1 };
      final int selected = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, userMsg, selections);
      int _selection = Java2PcmUserSelection.SELECT_CREATE_INTERFACE_NOT_IN_CONTRACTS.getSelection();
      boolean _equals = (selected == _selection);
      if (_equals) {
        _routinesFacade.addcorrespondenceToInterfaceAndUpdateRepository(pcmInterface, javaInterface, compilationUnit);
      }
    }
  }
  
  public CreatedInterfaceNotInContractsRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface javaInterface, final OperationInterface pcmInterface, final CompilationUnit compilationUnit) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreatedInterfaceNotInContractsRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaInterface = javaInterface;this.pcmInterface = pcmInterface;this.compilationUnit = compilationUnit;
  }
  
  private Interface javaInterface;
  
  private OperationInterface pcmInterface;
  
  private CompilationUnit compilationUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedInterfaceNotInContractsRoutine with input:");
    getLogger().debug("   javaInterface: " + this.javaInterface);
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   compilationUnit: " + this.compilationUnit);
    
    userExecution.callRoutine1(javaInterface, pcmInterface, compilationUnit, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
