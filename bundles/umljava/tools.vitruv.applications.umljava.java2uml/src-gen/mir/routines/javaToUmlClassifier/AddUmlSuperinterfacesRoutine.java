package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.Interface;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlSuperinterfacesRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddUmlSuperinterfacesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface jInterface, final Classifier jSuperInterface, final org.eclipse.uml2.uml.Interface uInterface) {
      return uInterface;
    }
    
    public void update0Element(final Interface jInterface, final Classifier jSuperInterface, final org.eclipse.uml2.uml.Interface uInterface) {
      UmlClassifierAndPackageUtil.addUmlSuperClassifier(uInterface, 
        JavaToUmlHelper.<org.eclipse.uml2.uml.Interface>findFirstCorrespondeningUmlElementByNameAndType(this.correspondenceModel, 
          jSuperInterface.getName(), org.eclipse.uml2.uml.Interface.class));
    }
    
    public EObject getCorrepondenceSourceUInterface(final Interface jInterface, final Classifier jSuperInterface) {
      return jInterface;
    }
  }
  
  public AddUmlSuperinterfacesRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface jInterface, final Classifier jSuperInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlSuperinterfacesRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jInterface = jInterface;this.jSuperInterface = jSuperInterface;
  }
  
  private Interface jInterface;
  
  private Classifier jSuperInterface;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlSuperinterfacesRoutine with input:");
    getLogger().debug("   Interface: " + this.jInterface);
    getLogger().debug("   Classifier: " + this.jSuperInterface);
    
    org.eclipse.uml2.uml.Interface uInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUInterface(jInterface, jSuperInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (uInterface == null) {
    	return;
    }
    registerObjectUnderModification(uInterface);
    // val updatedElement userExecution.getElement1(jInterface, jSuperInterface, uInterface);
    userExecution.update0Element(jInterface, jSuperInterface, uInterface);
    
    postprocessElements();
  }
}
