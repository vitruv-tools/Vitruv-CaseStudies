package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlClassRoutine extends AbstractRepairRoutineRealization {
  private CreateUmlClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public EObject getCorrepondenceSource1(final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit) {
      return jClass;
    }
    
    public EObject getElement4(final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Class uClass) {
      return jCompUnit;
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Class uClass) {
      return jClass;
    }
    
    public EObject getElement3(final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public void updateUClassElement(final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Class uClass) {
      uClass.setName(jClass.getName());
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Class uClass, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.addUmlElementToModelOrPackage(jCompUnit, uClass);
    }
  }
  
  public CreateUmlClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.CreateUmlClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.jClass = jClass;this.jCompUnit = jCompUnit;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private CompilationUnit jCompUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlClassRoutine with input:");
    getLogger().debug("   jClass: " + this.jClass);
    getLogger().debug("   jCompUnit: " + this.jCompUnit);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(jClass, jCompUnit), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Class uClass = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createClass();
    notifyObjectCreated(uClass);
    userExecution.updateUClassElement(jClass, jCompUnit, uClass);
    
    addCorrespondenceBetween(userExecution.getElement1(jClass, jCompUnit, uClass), userExecution.getElement2(jClass, jCompUnit, uClass), "");
    
    addCorrespondenceBetween(userExecution.getElement3(jClass, jCompUnit, uClass), userExecution.getElement4(jClass, jCompUnit, uClass), "");
    
    userExecution.callRoutine1(jClass, jCompUnit, uClass, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
