package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
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
      String _name = jPackage.getName();
      uPackage.setName(_name);
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package jPackage, final org.eclipse.uml2.uml.Package uPackage) {
      return jPackage;
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package jPackage, final org.eclipse.uml2.uml.Package uPackage, @Extension final RoutinesFacade _routinesFacade) {
      EList<String> _namespaces = jPackage.getNamespaces();
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(_namespaces);
      if (_isNullOrEmpty) {
        Model _umlModel = JavaToUmlHelper.getUmlModel(this.changePropagationObservable, this.correspondenceModel, this.userInteracting);
        _routinesFacade.addUmlElementToPackage(uPackage, _umlModel, jPackage);
      } else {
        EList<String> _namespaces_1 = jPackage.getNamespaces();
        String _last = IterableExtensions.<String>last(_namespaces_1);
        org.eclipse.uml2.uml.Package _findUmlPackage = JavaToUmlHelper.findUmlPackage(this.correspondenceModel, _last);
        _routinesFacade.addUmlElementToPackage(uPackage, _findUmlPackage, jPackage);
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
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlPackageRoutine with input:");
    getLogger().debug("   Package: " + this.jPackage);
    
    org.eclipse.uml2.uml.Package uPackage = UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(uPackage);
    userExecution.updateUPackageElement(jPackage, uPackage);
    
    addCorrespondenceBetween(userExecution.getElement1(jPackage, uPackage), userExecution.getElement2(jPackage, uPackage), "");
    
    userExecution.callRoutine1(jPackage, uPackage, actionsFacade);
    
    postprocessElements();
  }
}
