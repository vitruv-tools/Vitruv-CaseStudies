package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveUmlClassImplementRoutine extends AbstractRepairRoutineRealization {
  private RemoveUmlClassImplementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final org.eclipse.uml2.uml.Class uClass, final InterfaceRealization uRealization) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference) {
      return jClass;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final org.eclipse.uml2.uml.Class uClass, final InterfaceRealization uRealization) {
      EList<InterfaceRealization> _interfaceRealizations = uClass.getInterfaceRealizations();
      _interfaceRealizations.remove(uRealization);
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final org.eclipse.uml2.uml.Class uClass, final InterfaceRealization uRealization) {
      return uRealization;
    }
    
    public EObject getCorrepondenceSourceURealization(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final org.eclipse.uml2.uml.Class uClass) {
      return jReference;
    }
  }
  
  public RemoveUmlClassImplementRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.RemoveUmlClassImplementRoutine.ActionUserExecution(getExecutionState(), this);
    this.jClass = jClass;this.jReference = jReference;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private TypeReference jReference;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveUmlClassImplementRoutine with input:");
    getLogger().debug("   jClass: " + this.jClass);
    getLogger().debug("   jReference: " + this.jReference);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, jReference), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uClass == null) {
    	return false;
    }
    registerObjectUnderModification(uClass);
    org.eclipse.uml2.uml.InterfaceRealization uRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceURealization(jClass, jReference, uClass), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uRealization == null) {
    	return false;
    }
    registerObjectUnderModification(uRealization);
    // val updatedElement userExecution.getElement1(jClass, jReference, uClass, uRealization);
    userExecution.update0Element(jClass, jReference, uClass, uRealization);
    
    deleteObject(userExecution.getElement2(jClass, jReference, uClass, uRealization));
    
    postprocessElements();
    
    return true;
  }
}
