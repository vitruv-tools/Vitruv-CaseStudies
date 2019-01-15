package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddJavaSuperInterfaceRoutine extends AbstractRepairRoutineRealization {
  private AddJavaSuperInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final Interface uInterface, final Generalization uGeneralization, final org.emftext.language.java.classifiers.Interface jInterface, final org.emftext.language.java.classifiers.Interface jSuperInterface) {
      return uGeneralization;
    }
    
    public void executeAction1(final Interface uInterface, final Generalization uGeneralization, final org.emftext.language.java.classifiers.Interface jInterface, final org.emftext.language.java.classifiers.Interface jSuperInterface, @Extension final RoutinesFacade _routinesFacade) {
      Classifier _general = uGeneralization.getGeneral();
      TypeReference typeReference = UmlJavaTypePropagationHelper.createTypeReference(((Interface) _general), Optional.<org.emftext.language.java.classifiers.Interface>of(jSuperInterface), null, this.userInteractor);
      UmlJavaTypePropagationHelper.addJavaImport(jInterface.getContainingCompilationUnit(), typeReference);
      EList<TypeReference> _extends = jInterface.getExtends();
      _extends.add(typeReference);
      _routinesFacade.addGeneralizationCorrespondence(uGeneralization, typeReference);
    }
    
    public EObject getCorrepondenceSourceJInterface(final Interface uInterface, final Generalization uGeneralization) {
      return uInterface;
    }
    
    public EObject getCorrepondenceSourceJSuperInterface(final Interface uInterface, final Generalization uGeneralization, final org.emftext.language.java.classifiers.Interface jInterface) {
      Classifier _general = uGeneralization.getGeneral();
      return _general;
    }
  }
  
  public AddJavaSuperInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface uInterface, final Generalization uGeneralization) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.AddJavaSuperInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.uInterface = uInterface;this.uGeneralization = uGeneralization;
  }
  
  private Interface uInterface;
  
  private Generalization uGeneralization;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddJavaSuperInterfaceRoutine with input:");
    getLogger().debug("   uInterface: " + this.uInterface);
    getLogger().debug("   uGeneralization: " + this.uGeneralization);
    
    org.emftext.language.java.classifiers.Interface jInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJInterface(uInterface, uGeneralization), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jInterface == null) {
    	return false;
    }
    registerObjectUnderModification(jInterface);
    org.emftext.language.java.classifiers.Interface jSuperInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJSuperInterface(uInterface, uGeneralization, jInterface), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jSuperInterface == null) {
    	return false;
    }
    registerObjectUnderModification(jSuperInterface);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(uInterface, uGeneralization, jInterface, jSuperInterface), // correspondence source supplier
    	org.emftext.language.java.types.TypeReference.class,
    	(org.emftext.language.java.types.TypeReference _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    userExecution.executeAction1(uInterface, uGeneralization, jInterface, jSuperInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
