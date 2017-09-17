package mir.routines.class2comp;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface classInterface, final Interface compInterface) {
      return compInterface;
    }
    
    public EObject getCorrepondenceSourceCompInterface(final Interface classInterface) {
      return classInterface;
    }
    
    public void callRoutine1(final Interface classInterface, final Interface compInterface, @Extension final RoutinesFacade _routinesFacade) {
      final Model compModel = compInterface.getModel();
      final Function1<Component, Boolean> _function = (Component e) -> {
        boolean _isEmpty = IterableExtensions.isEmpty(Iterables.<Usage>filter(e.getPackagedElements(), Usage.class));
        return Boolean.valueOf((!_isEmpty));
      };
      final Iterable<Component> compsWithUsage = IterableExtensions.<Component>filter(Iterables.<Component>filter(compModel.getPackagedElements(), Component.class), _function);
      final Function1<Component, Boolean> _function_1 = (Component e) -> {
        return Boolean.valueOf(((Usage[])Conversions.unwrapArray(Iterables.<Usage>filter(e.getPackagedElements(), Usage.class), Usage.class))[0].getSuppliers().contains(compInterface));
      };
      final Iterable<Component> compsWithMatchingUsage = IterableExtensions.<Component>filter(compsWithUsage, _function_1);
      final Function1<Component, Usage> _function_2 = (Component e) -> {
        return ((Usage[])Conversions.unwrapArray(Iterables.<Usage>filter(e.getPackagedElements(), Usage.class), Usage.class))[0];
      };
      final Iterable<Usage> matchingUsages = IterableExtensions.<Component, Usage>map(compsWithMatchingUsage, _function_2);
      for (final Usage compUsage : matchingUsages) {
        compUsage.destroy();
      }
    }
  }
  
  public RemoveInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface classInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RemoveInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classInterface = classInterface;
  }
  
  private Interface classInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveInterfaceRoutine with input:");
    getLogger().debug("   classInterface: " + this.classInterface);
    
    org.eclipse.uml2.uml.Interface compInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompInterface(classInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compInterface == null) {
    	return false;
    }
    registerObjectUnderModification(compInterface);
    userExecution.callRoutine1(classInterface, compInterface, actionsFacade);
    
    deleteObject(userExecution.getElement1(classInterface, compInterface));
    
    postprocessElements();
    
    return true;
  }
}
