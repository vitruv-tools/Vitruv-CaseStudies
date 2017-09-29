package mir.routines.java2PcmClassifier;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmUserSelection;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

/**
 * *
 * nUser can choose if a composite or collection data type should be created.
 *  
 */
@SuppressWarnings("all")
public class CreateDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, @Extension final RoutinesFacade _routinesFacade) {
      String _name = javaClass.getName();
      String _plus = ("Class " + _name);
      final String userMsg = (_plus + 
        "has been created in the datatypes pacakage. Please decide which kind of data type should be created.");
      String _message = Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getMessage();
      String _message_1 = Java2PcmUserSelection.SELECT_COLLECTION_DATA_TYPE.getMessage();
      String _message_2 = Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getMessage();
      final String[] selections = new String[] { _message, _message_1, _message_2 };
      final int selected = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, userMsg, selections);
      boolean _matched = false;
      int _selection = Java2PcmUserSelection.SELECT_COMPOSITE_DATA_TYPE.getSelection();
      if (Objects.equal(selected, _selection)) {
        _matched=true;
        _routinesFacade.createCompositeDataType(javaClass, compilationUnit);
      }
      if (!_matched) {
        int _selection_1 = Java2PcmUserSelection.SELECT_COLLECTION_DATA_TYPE.getSelection();
        if (Objects.equal(selected, _selection_1)) {
          _matched=true;
          _routinesFacade.createCollectionDataType(javaClass, compilationUnit);
        }
      }
    }
  }
  
  public CreateDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaClass = javaClass;this.compilationUnit = compilationUnit;
  }
  
  private org.emftext.language.java.classifiers.Class javaClass;
  
  private CompilationUnit compilationUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateDataTypeRoutine with input:");
    getLogger().debug("   javaClass: " + this.javaClass);
    getLogger().debug("   compilationUnit: " + this.compilationUnit);
    
    userExecution.callRoutine1(javaClass, compilationUnit, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
