package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

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
        _routinesFacade.createdInterfaceNotInContracts(javaInterface, pcmIface, compilationUnit);
      } else {
        _routinesFacade.addCorrespondanceToInterfaceAndUpdateRepository(pcmIface, Java2PcmHelper.findPcmRepository(this.correspondenceModel), javaInterface, compilationUnit);
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
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMInterfaceRoutine with input:");
    getLogger().debug("   javaInterface: " + this.javaInterface);
    getLogger().debug("   compilationUnit: " + this.compilationUnit);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmIface = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    notifyObjectCreated(pcmIface);
    userExecution.updatePcmIfaceElement(javaInterface, compilationUnit, pcmIface);
    
    userExecution.callRoutine1(javaInterface, compilationUnit, pcmIface, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
