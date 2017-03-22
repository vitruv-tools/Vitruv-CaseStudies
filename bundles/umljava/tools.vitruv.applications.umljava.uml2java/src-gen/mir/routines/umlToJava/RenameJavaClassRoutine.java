package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameJavaClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit javaCompilationUnit) {
      return javaClass;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit javaCompilationUnit) {
      String _name = umlClass.getName();
      javaClass.setName(_name);
    }
    
    public EObject getCorrepondenceSourceJavaClass(final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit javaCompilationUnit) {
      return javaCompilationUnit;
    }
    
    public EObject getCorrepondenceSourceJavaCompilationUnit(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class javaClass) {
      return umlClass;
    }
    
    public void update1Element(final org.eclipse.uml2.uml.Class umlClass, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit javaCompilationUnit) {
      String _name = umlClass.getName();
      javaCompilationUnit.setName(_name);
      String _buildJavaFilePath = JavaPersistenceHelper.buildJavaFilePath(javaCompilationUnit);
      this.persistProjectRelative(umlClass, javaCompilationUnit, _buildJavaFilePath);
    }
  }
  
  public RenameJavaClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.RenameJavaClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaClassRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClass(umlClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (javaClass == null) {
    	return;
    }
    initializeRetrieveElementState(javaClass);
    CompilationUnit javaCompilationUnit = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaCompilationUnit(umlClass, javaClass), // correspondence source supplier
    	CompilationUnit.class,
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	null);
    if (javaCompilationUnit == null) {
    	return;
    }
    initializeRetrieveElementState(javaCompilationUnit);
    // val updatedElement userExecution.getElement1(umlClass, javaClass, javaCompilationUnit);
    userExecution.update0Element(umlClass, javaClass, javaCompilationUnit);
    
    // val updatedElement userExecution.getElement2(umlClass, javaClass, javaCompilationUnit);
    userExecution.update1Element(umlClass, javaClass, javaCompilationUnit);
    
    postprocessElementStates();
  }
}
