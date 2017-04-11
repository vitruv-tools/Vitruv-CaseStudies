package mir.routines.umlToJava;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaPackageAndClassifierRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameJavaPackageAndClassifierRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJPackage(final org.eclipse.uml2.uml.Package uPackage) {
      return uPackage;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package uPackage, final org.emftext.language.java.containers.Package jPackage, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.renameJavaPackage(uPackage);
      Iterable<org.eclipse.uml2.uml.Package> _filter = Iterables.<org.eclipse.uml2.uml.Package>filter(uPackage.getPackagedElements(), org.eclipse.uml2.uml.Package.class);
      for (final org.eclipse.uml2.uml.Package subPackage : _filter) {
        _routinesFacade.renameJavaPackageAndClassifier(subPackage);
      }
      Iterable<Classifier> _filter_1 = Iterables.<Classifier>filter(uPackage.getPackagedElements(), Classifier.class);
      for (final Classifier containedClassifier : _filter_1) {
        _routinesFacade.changePackageOfJavaCompilationUnit(uPackage, containedClassifier);
      }
    }
  }
  
  public RenameJavaPackageAndClassifierRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package uPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.RenameJavaPackageAndClassifierRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uPackage = uPackage;
  }
  
  private org.eclipse.uml2.uml.Package uPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaPackageAndClassifierRoutine with input:");
    getLogger().debug("   Package: " + this.uPackage);
    
    org.emftext.language.java.containers.Package jPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJPackage(uPackage), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null);
    if (jPackage == null) {
    	return;
    }
    registerObjectUnderModification(jPackage);
    userExecution.callRoutine1(uPackage, jPackage, actionsFacade);
    
    postprocessElements();
  }
}
