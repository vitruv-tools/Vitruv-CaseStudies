package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlSuperClassGeneralizationRoutine extends AbstractRepairRoutineRealization {
  private AddUmlSuperClassGeneralizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUClass(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final org.emftext.language.java.classifiers.Class jSuperClass) {
      return jClass;
    }
    
    public EObject getCorrepondenceSource1(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final org.emftext.language.java.classifiers.Class jSuperClass, final org.eclipse.uml2.uml.Class uClass, final org.eclipse.uml2.uml.Class uSuperClass) {
      return jReference;
    }
    
    public void executeAction1(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final org.emftext.language.java.classifiers.Class jSuperClass, final org.eclipse.uml2.uml.Class uClass, final org.eclipse.uml2.uml.Class uSuperClass, @Extension final RoutinesFacade _routinesFacade) {
      if ((uSuperClass != null)) {
        final Generalization uGeneralization = uClass.createGeneralization(uSuperClass);
        _routinesFacade.addGeneralizationCorrespondence(uGeneralization, jReference);
      } else {
        Logger _logger = this.getLogger();
        String _name = jSuperClass.getName();
        String _plus = ("Could not add " + _name);
        String _plus_1 = (_plus + " as super class for ");
        String _plus_2 = (_plus_1 + uClass);
        String _plus_3 = (_plus_2 + " because the corresponding UML-SuperClass is null");
        _logger.warn(_plus_3);
      }
    }
    
    public EObject getCorrepondenceSourceUSuperClass(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final org.emftext.language.java.classifiers.Class jSuperClass, final org.eclipse.uml2.uml.Class uClass) {
      return jSuperClass;
    }
  }
  
  public AddUmlSuperClassGeneralizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final org.emftext.language.java.classifiers.Class jSuperClass) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlSuperClassGeneralizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.jClass = jClass;this.jReference = jReference;this.jSuperClass = jSuperClass;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private TypeReference jReference;
  
  private org.emftext.language.java.classifiers.Class jSuperClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlSuperClassGeneralizationRoutine with input:");
    getLogger().debug("   jClass: " + this.jClass);
    getLogger().debug("   jReference: " + this.jReference);
    getLogger().debug("   jSuperClass: " + this.jSuperClass);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, jReference, jSuperClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uClass == null) {
    	return false;
    }
    registerObjectUnderModification(uClass);
    org.eclipse.uml2.uml.Class uSuperClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUSuperClass(jClass, jReference, jSuperClass, uClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uSuperClass == null) {
    	return false;
    }
    registerObjectUnderModification(uSuperClass);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(jClass, jReference, jSuperClass, uClass, uSuperClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Generalization.class,
    	(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    userExecution.executeAction1(jClass, jReference, jSuperClass, uClass, uSuperClass, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
