package mir.routines.class2comp;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Collections;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinePackageRenamedRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RoutinePackageRenamedRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package classPackage, final String newName, final String oldName, @Extension final RoutinesFacade _routinesFacade) {
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
      if (((Objects.equal(oldName, SharedUtil.CLASS_DATATYPES_PACKAGE_NAME) || (comp != null)) || Objects.equal(newName, SharedUtil.CLASS_DATATYPES_PACKAGE_NAME))) {
      }
      boolean _equals = Objects.equal(oldName, SharedUtil.CLASS_DATATYPES_PACKAGE_NAME);
      if (_equals) {
        classPackage.setName(SharedUtil.CLASS_DATATYPES_PACKAGE_NAME);
      } else {
        if ((Objects.equal(newName, SharedUtil.CLASS_DATATYPES_PACKAGE_NAME) && (oldName != null))) {
          final Function1<org.eclipse.uml2.uml.Package, Boolean> _function = (org.eclipse.uml2.uml.Package it) -> {
            String _name = it.getName();
            return Boolean.valueOf(Objects.equal(_name, SharedUtil.CLASS_DATATYPES_PACKAGE_NAME));
          };
          final Iterable<org.eclipse.uml2.uml.Package> dataTypePackageExistence = IterableExtensions.<org.eclipse.uml2.uml.Package>filter(Iterables.<org.eclipse.uml2.uml.Package>filter(classPackage.getModel().getPackagedElements(), org.eclipse.uml2.uml.Package.class), _function);
          boolean _isEmpty_1 = IterableExtensions.isEmpty(dataTypePackageExistence);
          boolean _not_1 = (!_isEmpty_1);
          if (_not_1) {
            classPackage.setName("DefaultPackageName");
          }
        }
      }
    }
  }
  
  public RoutinePackageRenamedRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package classPackage, final String newName, final String oldName) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RoutinePackageRenamedRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classPackage = classPackage;this.newName = newName;this.oldName = oldName;
  }
  
  private org.eclipse.uml2.uml.Package classPackage;
  
  private String newName;
  
  private String oldName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RoutinePackageRenamedRoutine with input:");
    getLogger().debug("   classPackage: " + this.classPackage);
    getLogger().debug("   newName: " + this.newName);
    getLogger().debug("   oldName: " + this.oldName);
    
    userExecution.callRoutine1(classPackage, newName, oldName, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
