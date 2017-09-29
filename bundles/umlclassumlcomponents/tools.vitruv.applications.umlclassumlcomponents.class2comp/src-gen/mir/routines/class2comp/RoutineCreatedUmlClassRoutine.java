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
public class RoutineCreatedUmlClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RoutineCreatedUmlClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCompModel(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage) {
      Model _model = umlClass.getModel();
      return _model;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage, final Model compModel, @Extension final RoutinesFacade _routinesFacade) {
      if ((!(classPackage instanceof Model))) {
        String _name = classPackage.getName();
        boolean _equals = Objects.equal(_name, SharedUtil.CLASS_DATATYPES_PACKAGE_NAME);
        if (_equals) {
          _routinesFacade.createDataTypeForClass(umlClass);
        } else {
          final Iterable<Component> comps = Iterables.<Component>filter(Iterables.<EObject>concat(this.correspondenceModel.getCorrespondingEObjects(Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(classPackage)))), Component.class);
          Component _xifexpression = null;
          boolean _isEmpty = IterableExtensions.isEmpty(comps);
          boolean _not = (!_isEmpty);
          if (_not) {
            _xifexpression = ((Component[])Conversions.unwrapArray(comps, Component.class))[0];
          } else {
            _xifexpression = null;
          }
          Component matchedPackageComponent = _xifexpression;
          if ((matchedPackageComponent == null)) {
            String _name_1 = umlClass.getName();
            String _plus = ("Should \'" + _name_1);
            final String question = (_plus + "\' be represented by a Component?");
            boolean _modalTextYesNoUserInteracting = SharedUtil.modalTextYesNoUserInteracting(this.userInteracting, question);
            if (_modalTextYesNoUserInteracting) {
              _routinesFacade.createUmlComponentForClass(umlClass);
            }
          }
        }
      }
    }
  }
  
  public RoutineCreatedUmlClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RoutineCreatedUmlClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;this.classPackage = classPackage;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private org.eclipse.uml2.uml.Package classPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RoutineCreatedUmlClassRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    getLogger().debug("   classPackage: " + this.classPackage);
    
    org.eclipse.uml2.uml.Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(umlClass, classPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compModel == null) {
    	return false;
    }
    registerObjectUnderModification(compModel);
    userExecution.callRoutine1(umlClass, classPackage, compModel, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
