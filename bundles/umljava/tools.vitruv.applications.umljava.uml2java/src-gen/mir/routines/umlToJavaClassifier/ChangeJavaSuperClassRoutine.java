package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class ChangeJavaSuperClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeJavaSuperClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJClass(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceSuperJavaClass(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass) {
      return superUMLClass;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class superJavaClass, @Extension final RoutinesFacade _routinesFacade) {
      EList<Classifier> _generals = uClass.getGenerals();
      int _size = _generals.size();
      boolean _equals = (_size == 1);
      if (_equals) {
        CompilationUnit _containingCompilationUnit = jClass.getContainingCompilationUnit();
        TypeReference _createTypeReferenceAndUpdateImport = UmlToJavaHelper.createTypeReferenceAndUpdateImport(null, superJavaClass, _containingCompilationUnit, this.userInteracting);
        jClass.setExtends(_createTypeReferenceAndUpdateImport);
      } else {
        this.userInteracting.showMessage(UserInteractionType.MODAL, ("Warning: Can not synchronize multiple inheritance for " + uClass));
        Logger _logger = this.getLogger();
        _logger.warn(("Routine not executed: Tried to set multiple inheritance for " + uClass));
      }
    }
  }
  
  public ChangeJavaSuperClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.ChangeJavaSuperClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.superUMLClass = superUMLClass;this.uClass = uClass;
  }
  
  private org.eclipse.uml2.uml.Class superUMLClass;
  
  private org.eclipse.uml2.uml.Class uClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeJavaSuperClassRoutine with input:");
    getLogger().debug("   Class: " + this.superUMLClass);
    getLogger().debug("   Class: " + this.uClass);
    
    org.emftext.language.java.classifiers.Class jClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClass(superUMLClass, uClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (jClass == null) {
    	return;
    }
    registerObjectUnderModification(jClass);
    org.emftext.language.java.classifiers.Class superJavaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceSuperJavaClass(superUMLClass, uClass, jClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (superJavaClass == null) {
    	return;
    }
    registerObjectUnderModification(superJavaClass);
    userExecution.callRoutine1(superUMLClass, uClass, jClass, superJavaClass, actionsFacade);
    
    postprocessElements();
  }
}
