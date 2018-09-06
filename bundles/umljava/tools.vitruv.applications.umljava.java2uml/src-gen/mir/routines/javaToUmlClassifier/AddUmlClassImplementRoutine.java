package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlClassImplementRoutine extends AbstractRepairRoutineRealization {
  private AddUmlClassImplementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUClass(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final Interface jInterface) {
      return jClass;
    }
    
    public EObject getCorrepondenceSource1(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final Interface jInterface, final org.eclipse.uml2.uml.Class uClass, final org.eclipse.uml2.uml.Interface uInterface) {
      return jReference;
    }
    
    public void executeAction1(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final Interface jInterface, final org.eclipse.uml2.uml.Class uClass, final org.eclipse.uml2.uml.Interface uInterface, @Extension final RoutinesFacade _routinesFacade) {
      if ((uInterface != null)) {
        String _name = uInterface.getName();
        String _name_1 = uInterface.getName();
        String _plus = (_name + _name_1);
        final InterfaceRealization uRealization = uClass.createInterfaceRealization(_plus, uInterface);
        _routinesFacade.addImplementsCorrespondence(jReference, uRealization);
      } else {
        Logger _logger = this.getLogger();
        String _name_2 = jInterface.getName();
        String _plus_1 = ("Could not add " + _name_2);
        String _plus_2 = (_plus_1 + " as implemented interface for ");
        String _plus_3 = (_plus_2 + uClass);
        String _plus_4 = (_plus_3 + " because the corresponding UML-Interface is null");
        _logger.warn(_plus_4);
      }
    }
    
    public EObject getCorrepondenceSourceUInterface(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final Interface jInterface, final org.eclipse.uml2.uml.Class uClass) {
      return jInterface;
    }
  }
  
  public AddUmlClassImplementRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final Interface jInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlClassImplementRoutine.ActionUserExecution(getExecutionState(), this);
    this.jClass = jClass;this.jReference = jReference;this.jInterface = jInterface;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private TypeReference jReference;
  
  private Interface jInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlClassImplementRoutine with input:");
    getLogger().debug("   jClass: " + this.jClass);
    getLogger().debug("   jReference: " + this.jReference);
    getLogger().debug("   jInterface: " + this.jInterface);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, jReference, jInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uClass == null) {
    	return false;
    }
    registerObjectUnderModification(uClass);
    org.eclipse.uml2.uml.Interface uInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUInterface(jClass, jReference, jInterface, uClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uInterface == null) {
    	return false;
    }
    registerObjectUnderModification(uInterface);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(jClass, jReference, jInterface, uClass, uInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    userExecution.executeAction1(jClass, jReference, jInterface, uClass, uInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
