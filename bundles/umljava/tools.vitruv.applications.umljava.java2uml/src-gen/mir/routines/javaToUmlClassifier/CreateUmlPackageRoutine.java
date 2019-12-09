package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlPackageRoutine extends AbstractRepairRoutineRealization {
  private CreateUmlPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package jPackage, final Model uModel, final org.eclipse.uml2.uml.Package uPackage) {
      return uPackage;
    }
    
    public void updateUPackageElement(final org.emftext.language.java.containers.Package jPackage, final Model uModel, final org.eclipse.uml2.uml.Package uPackage) {
      uPackage.setName(jPackage.getName());
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package jPackage, final Model uModel, final org.eclipse.uml2.uml.Package uPackage) {
      return jPackage;
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package jPackage, final Model uModel, final org.eclipse.uml2.uml.Package uPackage, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(jPackage.getNamespaces());
      if (_isNullOrEmpty) {
        _routinesFacade.addUmlElementToPackage(uPackage, uModel);
      } else {
        _routinesFacade.addUmlElementToPackage(uPackage, JavaToUmlHelper.findUmlPackage(uModel, IterableExtensions.<String>last(jPackage.getNamespaces())));
      }
    }
  }
  
  public CreateUmlPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package jPackage, final Model uModel) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.CreateUmlPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.jPackage = jPackage;this.uModel = uModel;
  }
  
  private org.emftext.language.java.containers.Package jPackage;
  
  private Model uModel;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlPackageRoutine with input:");
    getLogger().debug("   jPackage: " + this.jPackage);
    getLogger().debug("   uModel: " + this.uModel);
    
    org.eclipse.uml2.uml.Package uPackage = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(uPackage);
    userExecution.updateUPackageElement(jPackage, uModel, uPackage);
    
    addCorrespondenceBetween(userExecution.getElement1(jPackage, uModel, uPackage), userExecution.getElement2(jPackage, uModel, uPackage), "");
    
    userExecution.callRoutine1(jPackage, uModel, uPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
