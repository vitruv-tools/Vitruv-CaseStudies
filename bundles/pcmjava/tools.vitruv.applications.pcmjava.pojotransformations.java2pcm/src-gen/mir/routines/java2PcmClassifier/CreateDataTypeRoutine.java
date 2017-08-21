package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreateDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit, @Extension final RoutinesFacade _routinesFacade) {
      String _name = cls.getName();
      String _plus = ("Class " + _name);
      final String userMsg = (_plus + 
        "has been created in the datatypes pacakage. Please decide which kind of data type should be created.");
      final String[] selections = { "Create CompositeDataType", "CreateCollectionDataType", "Do not create a data type (not recommended)" };
      final int selected = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, userMsg, selections);
      switch (selected) {
        case 0:
          _routinesFacade.createCompositeDataType(cls, compilationUnit);
          break;
        case 1:
          _routinesFacade.createCollectionDataType(cls, compilationUnit);
          break;
      }
    }
  }
  
  public CreateDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.cls = cls;this.compilationUnit = compilationUnit;
  }
  
  private org.emftext.language.java.classifiers.Class cls;
  
  private CompilationUnit compilationUnit;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateDataTypeRoutine with input:");
    getLogger().debug("   Class: " + this.cls);
    getLogger().debug("   CompilationUnit: " + this.compilationUnit);
    
    userExecution.callRoutine1(cls, compilationUnit, actionsFacade);
    
    postprocessElements();
  }
}
