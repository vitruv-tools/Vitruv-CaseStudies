package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateOrFindUmlPackageRoutine extends AbstractRepairRoutineRealization {
  private CreateOrFindUmlPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUModel(final org.emftext.language.java.containers.Package jPackage) {
      return UMLPackage.Literals.MODEL;
    }
    
    public EObject getCorrepondenceSource1(final org.emftext.language.java.containers.Package jPackage, final Model uModel) {
      return jPackage;
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package jPackage, final Model uModel, @Extension final RoutinesFacade _routinesFacade) {
      final org.eclipse.uml2.uml.Package uPackage = JavaToUmlHelper.findUmlPackage(uModel, jPackage.getName());
      if ((uPackage == null)) {
        _routinesFacade.createUmlPackage(jPackage, uModel);
      } else {
        _routinesFacade.addPackageCorrespondence(jPackage, uPackage);
      }
    }
  }
  
  public CreateOrFindUmlPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package jPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.CreateOrFindUmlPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.jPackage = jPackage;
  }
  
  private org.emftext.language.java.containers.Package jPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOrFindUmlPackageRoutine with input:");
    getLogger().debug("   jPackage: " + this.jPackage);
    
    org.eclipse.uml2.uml.Model uModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUModel(jPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uModel == null) {
    	return false;
    }
    registerObjectUnderModification(uModel);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(jPackage, uModel), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    userExecution.callRoutine1(jPackage, uModel, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
