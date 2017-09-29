package mir.routines.class2comp;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Collections;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinePackageDeletedRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RoutinePackageDeletedRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package classPackage, final Model classModel, @Extension final RoutinesFacade _routinesFacade) {
      final Iterable<Component> comps = Iterables.<Component>filter(Iterables.<EObject>concat(this.correspondenceModel.getCorrespondingEObjects(Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(classPackage)))), Component.class);
      Component _xifexpression = null;
      boolean _isEmpty = IterableExtensions.isEmpty(comps);
      boolean _not = (!_isEmpty);
      if (_not) {
        _xifexpression = ((Component[])Conversions.unwrapArray(comps, Component.class))[0];
      } else {
        _xifexpression = null;
      }
      Component comp = _xifexpression;
      if ((Objects.equal(classPackage.getName(), SharedUtil.CLASS_DATATYPES_PACKAGE_NAME) || (comp != null))) {
      }
    }
  }
  
  public RoutinePackageDeletedRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package classPackage, final Model classModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RoutinePackageDeletedRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classPackage = classPackage;this.classModel = classModel;
  }
  
  private org.eclipse.uml2.uml.Package classPackage;
  
  private Model classModel;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RoutinePackageDeletedRoutine with input:");
    getLogger().debug("   classPackage: " + this.classPackage);
    getLogger().debug("   classModel: " + this.classModel);
    
    userExecution.callRoutine1(classPackage, classModel, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
