package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateOrFindUmlInterfaceRoutine extends AbstractRepairRoutineRealization {
  private CreateOrFindUmlInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUModel(final Interface jInterface, final CompilationUnit jCompUnit) {
      return UMLPackage.Literals.MODEL;
    }
    
    public EObject getCorrepondenceSource1(final Interface jInterface, final CompilationUnit jCompUnit, final Model uModel) {
      return jInterface;
    }
    
    public void callRoutine1(final Interface jInterface, final CompilationUnit jCompUnit, final Model uModel, @Extension final RoutinesFacade _routinesFacade) {
      final org.eclipse.uml2.uml.Interface uInterface = JavaToUmlHelper.findUmlInterface(uModel, jInterface.getName(), IterableExtensions.<String>last(jCompUnit.getNamespaces()));
      if ((uInterface == null)) {
        _routinesFacade.createUmlInterface(jInterface, jCompUnit);
      } else {
        _routinesFacade.addInterfaceCorrespondence(jInterface, uInterface, jCompUnit);
      }
    }
  }
  
  public CreateOrFindUmlInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface jInterface, final CompilationUnit jCompUnit) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.CreateOrFindUmlInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.jInterface = jInterface;this.jCompUnit = jCompUnit;
  }
  
  private Interface jInterface;
  
  private CompilationUnit jCompUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOrFindUmlInterfaceRoutine with input:");
    getLogger().debug("   jInterface: " + this.jInterface);
    getLogger().debug("   jCompUnit: " + this.jCompUnit);
    
    org.eclipse.uml2.uml.Model uModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUModel(jInterface, jCompUnit), // correspondence source supplier
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
    	userExecution.getCorrepondenceSource1(jInterface, jCompUnit, uModel), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    userExecution.callRoutine1(jInterface, jCompUnit, uModel, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
