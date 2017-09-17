package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.apache.log4j.Logger;
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
      final Type uSuperInterface = JavaToUmlHelper.getUmlType(jSuperInterface, JavaToUmlHelper.getUmlModel(this.changePropagationObservable, this.correspondenceModel, this.userInteracting), this.correspondenceModel);
      if (((uSuperInterface != null) && (uSuperInterface instanceof org.eclipse.uml2.uml.Interface))) {
        UmlClassifierAndPackageUtil.addUmlSuperClassifier(uInterface, ((org.eclipse.uml2.uml.Interface) uSuperInterface));
      } else {
        Logger _logger = this.getLogger();
        String _name = jSuperInterface.getName();
        String _plus = ("Could not add " + _name);
        String _plus_1 = (_plus + " as super interface for ");
        String _plus_2 = (_plus_1 + uInterface);
        String _plus_3 = (_plus_2 + " because the corresponding UML-Superinterface is null");
        _logger.warn(_plus_3);
      }
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
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlSuperinterfacesRoutine with input:");
    getLogger().debug("   jInterface: " + this.jInterface);
    getLogger().debug("   jSuperInterface: " + this.jSuperInterface);
    
    org.eclipse.uml2.uml.Interface uInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUInterface(jInterface, jSuperInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uInterface == null) {
    	return false;
    }
    registerObjectUnderModification(uInterface);
    // val updatedElement userExecution.getElement1(jInterface, jSuperInterface, uInterface);
    userExecution.update0Element(jInterface, jSuperInterface, uInterface);
    
    postprocessElements();
    
    return true;
  }
}
