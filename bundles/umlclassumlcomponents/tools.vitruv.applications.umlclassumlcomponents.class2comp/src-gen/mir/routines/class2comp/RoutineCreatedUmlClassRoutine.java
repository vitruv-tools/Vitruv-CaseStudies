package mir.routines.class2comp;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.applications.umlclassumlcomponents.class2comp.class2compUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class RoutineCreatedUmlClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RoutineCreatedUmlClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public boolean checkMatcherPrecondition1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage) {
      String _name = umlClass.getName();
      String _plus = ("Create corresponding Component or DataType for created class \'" + _name);
      final String msg = (_plus + "\'?");
      return class2compUtil.modalTextYesNoUserInteracting(this.userInteracting, msg);
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package classPackage, @Extension final RoutinesFacade _routinesFacade) {
      String _name = umlClass.getName();
      String _plus = ("Should \'" + _name);
      final String msg = (_plus + "\' be represented by a DataType? If not, it will be a Component.");
      boolean _modalTextYesNoUserInteracting = class2compUtil.modalTextYesNoUserInteracting(this.userInteracting, msg);
      if (_modalTextYesNoUserInteracting) {
        _routinesFacade.createDataTypeForClass(umlClass);
      } else {
        boolean _not = (!(classPackage.getClass().isInstance(org.eclipse.uml2.uml.Package.class) || ((List<Type>)Conversions.doWrapArray(classPackage.getClass().getGenericInterfaces())).contains(org.eclipse.uml2.uml.Package.class)));
        if (_not) {
          String _name_1 = umlClass.getName();
          String _plus_1 = ("Can\'t create Component \'" + _name_1);
          String _plus_2 = (_plus_1 + "\' in Model \'");
          String _name_2 = classPackage.getName();
          String _plus_3 = (_plus_2 + _name_2);
          String _plus_4 = (_plus_3 + "\'. No new Component created. Please create \'");
          String _name_3 = umlClass.getName();
          String _plus_5 = (_plus_4 + _name_3);
          final String msg2 = (_plus_5 + "\' in a Package.");
          this.userInteracting.showMessage(UserInteractionType.MODELESS, msg2);
        } else {
          final Iterable<Component> comps = Iterables.<Component>filter(Iterables.<EObject>concat(this.correspondenceModel.getCorrespondingEObjects(Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(classPackage)))), Component.class);
          Component _xifexpression = null;
          int _size = IterableExtensions.size(comps);
          boolean _notEquals = (_size != 0);
          if (_notEquals) {
            _xifexpression = ((Component[])Conversions.unwrapArray(comps, Component.class))[0];
          } else {
            _xifexpression = null;
          }
          Component matchedPackageComponent = _xifexpression;
          if ((matchedPackageComponent == null)) {
            _routinesFacade.createUmlComponent(umlClass);
          } else {
            String _name_4 = classPackage.getName();
            String _plus_6 = ("Package \'" + _name_4);
            String _plus_7 = (_plus_6 + "\' already has a corresponding Component \'");
            String _name_5 = matchedPackageComponent.getName();
            String _plus_8 = (_plus_7 + _name_5);
            String _plus_9 = (_plus_8 + "\'. No new Component created. Create \'");
            String _name_6 = umlClass.getName();
            String _plus_10 = (_plus_9 + _name_6);
            final String msg3 = (_plus_10 + "\' in a different Package.");
            this.userInteracting.showMessage(UserInteractionType.MODELESS, msg3);
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
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RoutineCreatedUmlClassRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    getLogger().debug("   Package: " + this.classPackage);
    
    if (!userExecution.checkMatcherPrecondition1(umlClass, classPackage)) {
    	return;
    }
    userExecution.callRoutine1(umlClass, classPackage, actionsFacade);
    
    postprocessElements();
  }
}
