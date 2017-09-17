package mir.routines.class2comp;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.List;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreatedPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatedPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCompModel(final org.eclipse.uml2.uml.Package classPackage) {
      Model _model = classPackage.getModel();
      return _model;
    }
    
    public boolean checkMatcherPrecondition1(final org.eclipse.uml2.uml.Package classPackage, final Model compModel) {
      String _name = classPackage.getName();
      String _plus = ("Do you want to link this Package \'" + _name);
      final String question = (_plus + "\' to an existing Component?");
      return SharedUtil.modalTextYesNoUserInteracting(this.userInteracting, question);
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package classPackage, final Model compModel, @Extension final RoutinesFacade _routinesFacade) {
      String _name = classPackage.getName();
      String _plus = ("Choose an existing Component to link Package \'" + _name);
      final String question = (_plus + "\' to:");
      final List<Component> componentsList = IterableExtensions.<Component>toList(Iterables.<Component>filter(compModel.getPackagedElements(), Component.class));
      boolean _isEmpty = componentsList.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        final Function1<Component, String> _function = (Component e) -> {
          return e.getName();
        };
        final List<String> options = ListExtensions.<Component, String>map(componentsList, _function);
        final int selection = SharedUtil.modalTextUserinteracting(this.userInteracting, question, ((String[])Conversions.unwrapArray(options, String.class)));
        final Component umlComp = componentsList.get(selection);
        _routinesFacade.assignNewPackage(classPackage, umlComp);
      } else {
        this.userInteracting.showMessage(UserInteractionType.MODELESS, "No available Component found");
      }
    }
  }
  
  public CreatedPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package classPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreatedPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classPackage = classPackage;
  }
  
  private org.eclipse.uml2.uml.Package classPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedPackageRoutine with input:");
    getLogger().debug("   classPackage: " + this.classPackage);
    
    org.eclipse.uml2.uml.Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(classPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compModel == null) {
    	return false;
    }
    registerObjectUnderModification(compModel);
    if (!userExecution.checkMatcherPrecondition1(classPackage, compModel)) {
    	return false;
    }
    userExecution.callRoutine1(classPackage, compModel, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
