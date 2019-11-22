package mir.routines.umlXpcmDatatypes_L2R;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDataype_ElementUpdatedCheckRoutine extends AbstractRepairRoutineRealization {
  private CompositeDataype_ElementUpdatedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Class)) {
        org.eclipse.uml2.uml.Class class_ = ((org.eclipse.uml2.uml.Class)affectedEObject);
        {
          EObject datatypesPackage__candidate = class_.eContainer();
          if (((datatypesPackage__candidate != null) && (datatypesPackage__candidate instanceof org.eclipse.uml2.uml.Package))) {
            org.eclipse.uml2.uml.Package datatypesPackage_ = ((org.eclipse.uml2.uml.Package) datatypesPackage__candidate);
            {
              _routinesFacade.compositeDataype_CreateMapping(class_, datatypesPackage_);
              return;
            }
          }
        }
      }
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Package)) {
        org.eclipse.uml2.uml.Package datatypesPackage_ = ((org.eclipse.uml2.uml.Package)affectedEObject);
        EList<PackageableElement> _packagedElements = datatypesPackage_.getPackagedElements();
        for (final PackageableElement class__candidate : _packagedElements) {
          if (((class__candidate != null) && (class__candidate instanceof org.eclipse.uml2.uml.Class))) {
            org.eclipse.uml2.uml.Class class__1 = ((org.eclipse.uml2.uml.Class) class__candidate);
            {
              _routinesFacade.compositeDataype_CreateMapping(class__1, datatypesPackage_);
              return;
            }
          }
        }
      }
      _routinesFacade.compositeDataype_ElementDeletedCheck(affectedEObject);
    }
  }
  
  public CompositeDataype_ElementUpdatedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_ElementUpdatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDataype_ElementUpdatedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
