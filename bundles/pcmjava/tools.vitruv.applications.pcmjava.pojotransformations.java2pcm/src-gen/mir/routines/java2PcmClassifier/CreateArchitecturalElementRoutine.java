package mir.routines.java2PcmClassifier;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.ContainersPackage;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmUserSelection;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

/**
 * *
 * nUser can select if he wants to create BasicComponent, CompositeComponent, System or do nothing.
 *  
 */
@SuppressWarnings("all")
public class CreateArchitecturalElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateArchitecturalElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
      return ContainersPackage.Literals.PACKAGE;
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName, @Extension final RoutinesFacade _routinesFacade) {
      final String userMsg = "A package has been created. Please decide whether and which corresponding architectural element should be created";
      String _message = Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getMessage();
      String _message_1 = Java2PcmUserSelection.SELECT_COMPOSITE_COMPONENT.getMessage();
      String _message_2 = Java2PcmUserSelection.SELECT_SYSTEM.getMessage();
      String _message_3 = Java2PcmUserSelection.SELECT_NOTHING_DECIDE_LATER.getMessage();
      final String[] selections = new String[] { _message, _message_1, _message_2, _message_3 };
      final int selected = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, userMsg, selections);
      boolean _matched = false;
      int _selection = Java2PcmUserSelection.SELECT_BASIC_COMPONENT.getSelection();
      if (Objects.equal(selected, _selection)) {
        _matched=true;
        _routinesFacade.createBasicComponent(javaPackage, name, rootPackageName);
      }
      if (!_matched) {
        int _selection_1 = Java2PcmUserSelection.SELECT_COMPOSITE_COMPONENT.getSelection();
        if (Objects.equal(selected, _selection_1)) {
          _matched=true;
          _routinesFacade.createCompositeComponent(javaPackage, name, rootPackageName);
        }
      }
      if (!_matched) {
        int _selection_2 = Java2PcmUserSelection.SELECT_SYSTEM.getSelection();
        if (Objects.equal(selected, _selection_2)) {
          _matched=true;
          _routinesFacade.createSystem(javaPackage, name);
        }
      }
    }
  }
  
  public CreateArchitecturalElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateArchitecturalElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;this.name = name;this.rootPackageName = rootPackageName;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  private String name;
  
  private String rootPackageName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateArchitecturalElementRoutine with input:");
    getLogger().debug("   javaPackage: " + this.javaPackage);
    getLogger().debug("   name: " + this.name);
    getLogger().debug("   rootPackageName: " + this.rootPackageName);
    
    if (getCorrespondingElement(
    	userExecution.getCorrepondenceSource1(javaPackage, name, rootPackageName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	) == null) {
    					return false;
    				}
    userExecution.callRoutine1(javaPackage, name, rootPackageName, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
