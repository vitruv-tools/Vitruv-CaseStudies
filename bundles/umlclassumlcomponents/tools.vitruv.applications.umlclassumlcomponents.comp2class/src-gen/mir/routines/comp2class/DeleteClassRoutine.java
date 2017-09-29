package mir.routines.comp2class;

import java.io.IOException;
import java.util.Iterator;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSourceClassPackage(final Component umlComp, final org.eclipse.uml2.uml.Class umlClass) {
      return umlComp;
    }
    
    public EObject getCorrepondenceSourceUmlClass(final Component umlComp) {
      return umlComp;
    }
    
    public void callRoutine1(final Component umlComp, final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isEmpty = classPackage.getPackagedElements().isEmpty();
      if (_isEmpty) {
        classPackage.destroy();
      } else {
        String _name = classPackage.getName();
        String _plus = ("Delete the corresponding Package \'" + _name);
        final String question = (_plus + "\' and all its contained elements?");
        boolean _modalTextYesNoUserInteracting = SharedUtil.modalTextYesNoUserInteracting(this.userInteracting, question);
        if (_modalTextYesNoUserInteracting) {
          for (final Iterator<PackageableElement> iter = classPackage.getPackagedElements().iterator(); iter.hasNext();) {
            {
              final PackageableElement classElement = iter.next();
              iter.remove();
              classElement.destroy();
            }
          }
          classPackage.destroy();
        }
      }
    }
  }
  
  public DeleteClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.DeleteClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.umlComp = umlComp;
  }
  
  private Component umlComp;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteClassRoutine with input:");
    getLogger().debug("   umlComp: " + this.umlComp);
    
    org.eclipse.uml2.uml.Class umlClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlClass(umlComp), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlClass == null) {
    	return false;
    }
    registerObjectUnderModification(umlClass);
    org.eclipse.uml2.uml.Package classPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassPackage(umlComp, umlClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (classPackage == null) {
    	return false;
    }
    registerObjectUnderModification(classPackage);
    deleteObject(userExecution.getElement1(umlComp, umlClass, classPackage));
    
    userExecution.callRoutine1(umlComp, umlClass, classPackage, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
