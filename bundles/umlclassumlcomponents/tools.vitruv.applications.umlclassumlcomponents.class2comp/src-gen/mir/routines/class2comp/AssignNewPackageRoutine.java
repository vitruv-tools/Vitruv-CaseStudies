package mir.routines.class2comp;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Collections;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class AssignNewPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AssignNewPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package newPackage, final Component umlComponent, @Extension final RoutinesFacade _routinesFacade) {
      final Iterable<org.eclipse.uml2.uml.Package> packages = Iterables.<org.eclipse.uml2.uml.Package>filter(Iterables.<EObject>concat(this.correspondenceModel.getCorrespondingEObjects(Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(umlComponent)))), org.eclipse.uml2.uml.Package.class);
      org.eclipse.uml2.uml.Package _xifexpression = null;
      boolean _isEmpty = IterableExtensions.isEmpty(packages);
      boolean _not = (!_isEmpty);
      if (_not) {
        _xifexpression = ((org.eclipse.uml2.uml.Package[])Conversions.unwrapArray(packages, org.eclipse.uml2.uml.Package.class))[0];
      } else {
        _xifexpression = null;
      }
      org.eclipse.uml2.uml.Package oldPackage = _xifexpression;
      if ((oldPackage == null)) {
        this.correspondenceModel.createAndAddCorrespondence(Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(umlComponent)), Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(newPackage)));
      } else {
        String _name = oldPackage.getName();
        String _plus = ("Chosen Component is already linked to existing Package \'" + _name);
        final String errorMsg = (_plus + "\'.");
        this.userInteracting.showMessage(UserInteractionType.MODELESS, errorMsg);
      }
    }
  }
  
  public AssignNewPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package newPackage, final Component umlComponent) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.AssignNewPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.newPackage = newPackage;this.umlComponent = umlComponent;
  }
  
  private org.eclipse.uml2.uml.Package newPackage;
  
  private Component umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AssignNewPackageRoutine with input:");
    getLogger().debug("   newPackage: " + this.newPackage);
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    userExecution.callRoutine1(newPackage, umlComponent, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
