package mir.routines.class2comp;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateRequiredAndProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateRequiredAndProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Interface classInterface, final InterfaceRealization classIFRealizationReq, final org.eclipse.uml2.uml.Package interfacePackage, final org.eclipse.uml2.uml.Package classPackage, @Extension final RoutinesFacade _routinesFacade) {
      final Function1<org.eclipse.uml2.uml.Class, Boolean> _function = (org.eclipse.uml2.uml.Class e) -> {
        boolean _isEmpty = e.getInterfaceRealizations().isEmpty();
        return Boolean.valueOf((!_isEmpty));
      };
      final Iterable<org.eclipse.uml2.uml.Class> classesWithIFRealization = IterableExtensions.<org.eclipse.uml2.uml.Class>filter(Iterables.<org.eclipse.uml2.uml.Class>filter(interfacePackage.getPackagedElements(), org.eclipse.uml2.uml.Class.class), _function);
      final Function1<org.eclipse.uml2.uml.Class, EList<InterfaceRealization>> _function_1 = (org.eclipse.uml2.uml.Class e) -> {
        return e.getInterfaceRealizations();
      };
      final Iterable<InterfaceRealization> iFRealizations = Iterables.<InterfaceRealization>concat(IterableExtensions.<org.eclipse.uml2.uml.Class, EList<InterfaceRealization>>map(classesWithIFRealization, _function_1));
      final Function1<InterfaceRealization, Boolean> _function_2 = (InterfaceRealization e) -> {
        return Boolean.valueOf(e.getSuppliers().contains(classInterface));
      };
      final Iterable<InterfaceRealization> matchedIFRealizations = IterableExtensions.<InterfaceRealization>filter(iFRealizations, _function_2);
      InterfaceRealization _xifexpression = null;
      boolean _isEmpty = IterableExtensions.isEmpty(matchedIFRealizations);
      boolean _not = (!_isEmpty);
      if (_not) {
        _xifexpression = ((InterfaceRealization[])Conversions.unwrapArray(matchedIFRealizations, InterfaceRealization.class))[0];
      } else {
        _xifexpression = null;
      }
      final InterfaceRealization classIFRealizationProviding = _xifexpression;
      if ((classIFRealizationProviding != null)) {
        _routinesFacade.createCompInterface(classInterface);
        _routinesFacade.createRequiredRole(classIFRealizationReq, classInterface, classPackage);
        _routinesFacade.createProvidedRole(classIFRealizationProviding, classInterface, interfacePackage);
      }
    }
  }
  
  public CreateRequiredAndProvidedRoleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface classInterface, final InterfaceRealization classIFRealizationReq, final org.eclipse.uml2.uml.Package interfacePackage, final org.eclipse.uml2.uml.Package classPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateRequiredAndProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classInterface = classInterface;this.classIFRealizationReq = classIFRealizationReq;this.interfacePackage = interfacePackage;this.classPackage = classPackage;
  }
  
  private Interface classInterface;
  
  private InterfaceRealization classIFRealizationReq;
  
  private org.eclipse.uml2.uml.Package interfacePackage;
  
  private org.eclipse.uml2.uml.Package classPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRequiredAndProvidedRoleRoutine with input:");
    getLogger().debug("   classInterface: " + this.classInterface);
    getLogger().debug("   classIFRealizationReq: " + this.classIFRealizationReq);
    getLogger().debug("   interfacePackage: " + this.interfacePackage);
    getLogger().debug("   classPackage: " + this.classPackage);
    
    userExecution.callRoutine1(classInterface, classIFRealizationReq, interfacePackage, classPackage, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
