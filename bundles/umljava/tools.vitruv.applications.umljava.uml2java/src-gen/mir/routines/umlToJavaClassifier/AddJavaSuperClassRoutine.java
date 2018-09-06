package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionOptions;

@SuppressWarnings("all")
public class AddJavaSuperClassRoutine extends AbstractRepairRoutineRealization {
  private AddJavaSuperClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJSuperClass(final org.eclipse.uml2.uml.Class uClass, final Generalization uGeneralization, final org.emftext.language.java.classifiers.Class jClass) {
      Classifier _general = uGeneralization.getGeneral();
      return _general;
    }
    
    public EObject getCorrepondenceSourceJClass(final org.eclipse.uml2.uml.Class uClass, final Generalization uGeneralization) {
      return uClass;
    }
    
    public EObject getCorrepondenceSource1(final org.eclipse.uml2.uml.Class uClass, final Generalization uGeneralization, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class jSuperClass) {
      return uGeneralization;
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Class uClass, final Generalization uGeneralization, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class jSuperClass, @Extension final RoutinesFacade _routinesFacade) {
      int _size = uClass.getGenerals().size();
      boolean _equals = (_size == 1);
      if (_equals) {
        Classifier _general = uGeneralization.getGeneral();
        TypeReference typeReference = UmlJavaTypePropagationHelper.createTypeReference(((org.eclipse.uml2.uml.Class) _general), Optional.<org.emftext.language.java.classifiers.Class>of(jSuperClass), null, this.userInteractor);
        UmlJavaTypePropagationHelper.addJavaImport(jClass.getContainingCompilationUnit(), typeReference);
        jClass.setExtends(typeReference);
        _routinesFacade.addGeneralizationCorrespondence(uGeneralization, typeReference);
      } else {
        this.userInteractor.getNotificationDialogBuilder().message(("Can not synchronize multiple inheritance for " + uClass)).title("Warning").notificationType(UserInteractionOptions.NotificationType.WARNING).windowModality(UserInteractionOptions.WindowModality.MODAL).startInteraction();
        this.getLogger().warn(("Routine not executed: Tried to set multiple inheritance for " + uClass));
      }
    }
  }
  
  public AddJavaSuperClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class uClass, final Generalization uGeneralization) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.AddJavaSuperClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.uClass = uClass;this.uGeneralization = uGeneralization;
  }
  
  private org.eclipse.uml2.uml.Class uClass;
  
  private Generalization uGeneralization;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddJavaSuperClassRoutine with input:");
    getLogger().debug("   uClass: " + this.uClass);
    getLogger().debug("   uGeneralization: " + this.uGeneralization);
    
    org.emftext.language.java.classifiers.Class jClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClass(uClass, uGeneralization), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jClass == null) {
    	return false;
    }
    registerObjectUnderModification(jClass);
    org.emftext.language.java.classifiers.Class jSuperClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJSuperClass(uClass, uGeneralization, jClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jSuperClass == null) {
    	return false;
    }
    registerObjectUnderModification(jSuperClass);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(uClass, uGeneralization, jClass, jSuperClass), // correspondence source supplier
    	org.emftext.language.java.types.TypeReference.class,
    	(org.emftext.language.java.types.TypeReference _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    userExecution.executeAction1(uClass, uGeneralization, jClass, jSuperClass, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
