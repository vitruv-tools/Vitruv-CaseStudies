package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlSuperinterfacesRoutine extends AbstractRepairRoutineRealization {
  private AddUmlSuperinterfacesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUModel(final Interface jInterface, final TypeReference jReference, final Interface jSuperInterface) {
      return UMLPackage.Literals.MODEL;
    }
    
    public EObject getCorrepondenceSource1(final Interface jInterface, final TypeReference jReference, final Interface jSuperInterface, final Model uModel, final org.eclipse.uml2.uml.Interface uInterface, final org.eclipse.uml2.uml.Interface uSuperInterface) {
      return jReference;
    }
    
    public void executeAction1(final Interface jInterface, final TypeReference jReference, final Interface jSuperInterface, final Model uModel, final org.eclipse.uml2.uml.Interface uInterface, final org.eclipse.uml2.uml.Interface uSuperInterface, @Extension final RoutinesFacade _routinesFacade) {
      if (((uSuperInterface != null) && (uSuperInterface instanceof org.eclipse.uml2.uml.Interface))) {
        final Generalization uGeneralization = uInterface.createGeneralization(((org.eclipse.uml2.uml.Interface) uSuperInterface));
        _routinesFacade.addGeneralizationCorrespondence(uGeneralization, jReference);
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
    
    public EObject getCorrepondenceSourceUSuperInterface(final Interface jInterface, final TypeReference jReference, final Interface jSuperInterface, final Model uModel, final org.eclipse.uml2.uml.Interface uInterface) {
      return jSuperInterface;
    }
    
    public EObject getCorrepondenceSourceUInterface(final Interface jInterface, final TypeReference jReference, final Interface jSuperInterface, final Model uModel) {
      return jInterface;
    }
  }
  
  public AddUmlSuperinterfacesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface jInterface, final TypeReference jReference, final Interface jSuperInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlSuperinterfacesRoutine.ActionUserExecution(getExecutionState(), this);
    this.jInterface = jInterface;this.jReference = jReference;this.jSuperInterface = jSuperInterface;
  }
  
  private Interface jInterface;
  
  private TypeReference jReference;
  
  private Interface jSuperInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlSuperinterfacesRoutine with input:");
    getLogger().debug("   jInterface: " + this.jInterface);
    getLogger().debug("   jReference: " + this.jReference);
    getLogger().debug("   jSuperInterface: " + this.jSuperInterface);
    
    org.eclipse.uml2.uml.Model uModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUModel(jInterface, jReference, jSuperInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uModel == null) {
    	return false;
    }
    registerObjectUnderModification(uModel);
    org.eclipse.uml2.uml.Interface uInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUInterface(jInterface, jReference, jSuperInterface, uModel), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uInterface == null) {
    	return false;
    }
    registerObjectUnderModification(uInterface);
    org.eclipse.uml2.uml.Interface uSuperInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUSuperInterface(jInterface, jReference, jSuperInterface, uModel, uInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uSuperInterface == null) {
    	return false;
    }
    registerObjectUnderModification(uSuperInterface);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(jInterface, jReference, jSuperInterface, uModel, uInterface, uSuperInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Generalization.class,
    	(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    userExecution.executeAction1(jInterface, jReference, jSuperInterface, uModel, uInterface, uSuperInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
