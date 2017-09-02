package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.classifiers.Classifier;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlClassImplementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddUmlClassImplementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class jClass, final Classifier jInterface, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final org.emftext.language.java.classifiers.Class jClass, final Classifier jInterface) {
      return jClass;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class jClass, final Classifier jInterface, final org.eclipse.uml2.uml.Class uClass) {
      final Type uInterface = JavaToUmlHelper.getUmlType(jInterface, JavaToUmlHelper.getUmlModel(this.changePropagationObservable, this.correspondenceModel, this.userInteracting), this.correspondenceModel);
      if (((uInterface != null) && (uInterface instanceof Interface))) {
        String _name = uInterface.getName();
        String _name_1 = uInterface.getName();
        String _plus = (_name + _name_1);
        uClass.createInterfaceRealization(_plus, ((Interface) uInterface));
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
  }
  
  public AddUmlClassImplementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final Classifier jInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlClassImplementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;this.jInterface = jInterface;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private Classifier jInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlClassImplementRoutine with input:");
    getLogger().debug("   jClass: " + this.jClass);
    getLogger().debug("   jInterface: " + this.jInterface);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, jInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uClass == null) {
    	return false;
    }
    registerObjectUnderModification(uClass);
    // val updatedElement userExecution.getElement1(jClass, jInterface, uClass);
    userExecution.update0Element(jClass, jInterface, uClass);
    
    postprocessElements();
    
    return true;
  }
}
