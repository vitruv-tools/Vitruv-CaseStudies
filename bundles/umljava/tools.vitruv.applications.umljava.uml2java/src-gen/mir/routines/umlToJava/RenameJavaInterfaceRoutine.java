package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameJavaInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface jInterface, final CompilationUnit jCompUnit) {
      return jInterface;
    }
    
    public void update0Element(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface jInterface, final CompilationUnit jCompUnit) {
      String _name = umlInterface.getName();
      jInterface.setName(_name);
    }
    
    public EObject getElement2(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface jInterface, final CompilationUnit jCompUnit) {
      return jCompUnit;
    }
    
    public EObject getCorrepondenceSourceJCompUnit(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface jInterface) {
      return umlInterface;
    }
    
    public void update1Element(final Interface umlInterface, final org.emftext.language.java.classifiers.Interface jInterface, final CompilationUnit jCompUnit) {
      String _name = umlInterface.getName();
      jCompUnit.setName(_name);
      String _buildJavaFilePath = JavaPersistenceHelper.buildJavaFilePath(jCompUnit);
      this.persistProjectRelative(umlInterface, jCompUnit, _buildJavaFilePath);
    }
    
    public EObject getCorrepondenceSourceJInterface(final Interface umlInterface) {
      return umlInterface;
    }
  }
  
  public RenameJavaInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.RenameJavaInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlInterface = umlInterface;
  }
  
  private Interface umlInterface;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.umlInterface);
    
    org.emftext.language.java.classifiers.Interface jInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJInterface(umlInterface), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (jInterface == null) {
    	return;
    }
    initializeRetrieveElementState(jInterface);
    CompilationUnit jCompUnit = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJCompUnit(umlInterface, jInterface), // correspondence source supplier
    	CompilationUnit.class,
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	null);
    if (jCompUnit == null) {
    	return;
    }
    initializeRetrieveElementState(jCompUnit);
    // val updatedElement userExecution.getElement1(umlInterface, jInterface, jCompUnit);
    userExecution.update0Element(umlInterface, jInterface, jCompUnit);
    
    // val updatedElement userExecution.getElement2(umlInterface, jInterface, jCompUnit);
    userExecution.update1Element(umlInterface, jInterface, jCompUnit);
    
    postprocessElementStates();
  }
}
