package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlElementToPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddUmlElementToPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final PackageableElement uPackageable, final org.eclipse.uml2.uml.Package uPackage, final EObject persistedObject) {
      return uPackage;
    }
    
    public void update0Element(final PackageableElement uPackageable, final org.eclipse.uml2.uml.Package uPackage, final EObject persistedObject) {
      EList<PackageableElement> _packagedElements = uPackage.getPackagedElements();
      _packagedElements.add(uPackageable);
    }
    
    public void callRoutine1(final PackageableElement uPackageable, final org.eclipse.uml2.uml.Package uPackage, final EObject persistedObject, @Extension final RoutinesFacade _routinesFacade) {
      if ((uPackage instanceof Model)) {
        int _size = ((Model)uPackage).getPackagedElements().size();
        boolean _equals = (_size == 1);
        if (_equals) {
          this.persistProjectRelative(persistedObject, uPackage, JavaToUmlHelper.getRootModelFile());
        }
      }
    }
  }
  
  public AddUmlElementToPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PackageableElement uPackageable, final org.eclipse.uml2.uml.Package uPackage, final EObject persistedObject) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlElementToPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.uPackageable = uPackageable;this.uPackage = uPackage;this.persistedObject = persistedObject;
  }
  
  private PackageableElement uPackageable;
  
  private org.eclipse.uml2.uml.Package uPackage;
  
  private EObject persistedObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlElementToPackageRoutine with input:");
    getLogger().debug("   uPackageable: " + this.uPackageable);
    getLogger().debug("   uPackage: " + this.uPackage);
    getLogger().debug("   persistedObject: " + this.persistedObject);
    
    // val updatedElement userExecution.getElement1(uPackageable, uPackage, persistedObject);
    userExecution.update0Element(uPackageable, uPackage, persistedObject);
    
    userExecution.callRoutine1(uPackageable, uPackage, persistedObject, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
