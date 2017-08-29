package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package jPackage, final org.eclipse.uml2.uml.Package uPackage) {
      return uPackage;
    }
    
    public void updateUPackageElement(final org.emftext.language.java.containers.Package jPackage, final org.eclipse.uml2.uml.Package uPackage) {
      uPackage.setName(jPackage.getName());
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package jPackage, final org.eclipse.uml2.uml.Package uPackage) {
      return jPackage;
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package jPackage, final org.eclipse.uml2.uml.Package uPackage, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(jPackage.getNamespaces());
      if (_isNullOrEmpty) {
        _routinesFacade.addUmlElementToPackage(uPackage, JavaToUmlHelper.getUmlModel(this.changePropagationObservable, this.correspondenceModel, this.userInteracting), jPackage);
      } else {
        _routinesFacade.addUmlElementToPackage(uPackage, JavaToUmlHelper.findUmlPackage(this.correspondenceModel, IterableExtensions.<String>last(jPackage.getNamespaces())), jPackage);
      }
    }
  }
  
  public CreateUmlPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package jPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.CreateUmlPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jPackage = jPackage;
  }
  
  private org.emftext.language.java.containers.Package jPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlPackageRoutine with input:");
    getLogger().debug("   jPackage: " + this.jPackage);
    
    org.eclipse.uml2.uml.Package uPackage = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(uPackage);
    userExecution.updateUPackageElement(jPackage, uPackage);
    
    addCorrespondenceBetween(userExecution.getElement1(jPackage, uPackage), userExecution.getElement2(jPackage, uPackage), "");
    
    userExecution.callRoutine1(jPackage, uPackage, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
