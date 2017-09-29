package mir.routines.class2comp;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Collections;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCompInterface(final InterfaceRealization classIFRealization, final Interface classInterface, final org.eclipse.uml2.uml.Package affectedPackage) {
      return classInterface;
    }
    
    public void callRoutine1(final InterfaceRealization classIFRealization, final Interface classInterface, final org.eclipse.uml2.uml.Package affectedPackage, final Interface compInterface, @Extension final RoutinesFacade _routinesFacade) {
      final Iterable<Component> comps = Iterables.<Component>filter(Iterables.<EObject>concat(this.correspondenceModel.getCorrespondingEObjects(Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(affectedPackage)))), Component.class);
      Component _xifexpression = null;
      boolean _isEmpty = IterableExtensions.isEmpty(comps);
      boolean _not = (!_isEmpty);
      if (_not) {
        _xifexpression = ((Component[])Conversions.unwrapArray(comps, Component.class))[0];
      } else {
        _xifexpression = null;
      }
      Component matchedComponent = _xifexpression;
      if ((matchedComponent != null)) {
        _routinesFacade.createInterfaceRealization(classIFRealization, matchedComponent, compInterface);
      }
    }
  }
  
  public CreateProvidedRoleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization classIFRealization, final Interface classInterface, final org.eclipse.uml2.uml.Package affectedPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classIFRealization = classIFRealization;this.classInterface = classInterface;this.affectedPackage = affectedPackage;
  }
  
  private InterfaceRealization classIFRealization;
  
  private Interface classInterface;
  
  private org.eclipse.uml2.uml.Package affectedPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateProvidedRoleRoutine with input:");
    getLogger().debug("   classIFRealization: " + this.classIFRealization);
    getLogger().debug("   classInterface: " + this.classInterface);
    getLogger().debug("   affectedPackage: " + this.affectedPackage);
    
    org.eclipse.uml2.uml.Interface compInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompInterface(classIFRealization, classInterface, affectedPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compInterface == null) {
    	return false;
    }
    registerObjectUnderModification(compInterface);
    userExecution.callRoutine1(classIFRealization, classInterface, affectedPackage, compInterface, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
