package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.Interface;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveUmlSuperInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveUmlSuperInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface jInterface, final Classifier jSuperClassifier, final org.eclipse.uml2.uml.Interface uInterface) {
      return uInterface;
    }
    
    public void update0Element(final Interface jInterface, final Classifier jSuperClassifier, final org.eclipse.uml2.uml.Interface uInterface) {
      final Type uSuperInterface = JavaToUmlHelper.getUmlType(jSuperClassifier, JavaToUmlHelper.getUmlModel(this.changePropagationObservable, this.correspondenceModel, this.userInteracting), this.correspondenceModel);
      if (((uSuperInterface != null) && (uSuperInterface instanceof org.eclipse.uml2.uml.Interface))) {
        UmlClassifierAndPackageUtil.removeUmlGeneralClassifier(uInterface, ((org.eclipse.uml2.uml.Interface) uSuperInterface));
      }
    }
    
    public EObject getCorrepondenceSourceUInterface(final Interface jInterface, final Classifier jSuperClassifier) {
      return jInterface;
    }
  }
  
  public RemoveUmlSuperInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface jInterface, final Classifier jSuperClassifier) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.RemoveUmlSuperInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jInterface = jInterface;this.jSuperClassifier = jSuperClassifier;
  }
  
  private Interface jInterface;
  
  private Classifier jSuperClassifier;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveUmlSuperInterfaceRoutine with input:");
    getLogger().debug("   jInterface: " + this.jInterface);
    getLogger().debug("   jSuperClassifier: " + this.jSuperClassifier);
    
    org.eclipse.uml2.uml.Interface uInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUInterface(jInterface, jSuperClassifier), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uInterface == null) {
    	return false;
    }
    registerObjectUnderModification(uInterface);
    // val updatedElement userExecution.getElement1(jInterface, jSuperClassifier, uInterface);
    userExecution.update0Element(jInterface, jSuperClassifier, uInterface);
    
    postprocessElements();
    
    return true;
  }
}
