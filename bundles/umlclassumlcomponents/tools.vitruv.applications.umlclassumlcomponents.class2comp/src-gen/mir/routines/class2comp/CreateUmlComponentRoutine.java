package mir.routines.class2comp;

import java.io.IOException;
import java.util.Collections;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComponent) {
      return compModel;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComponent) {
      EList<PackageableElement> _packagedElements = compModel.getPackagedElements();
      _packagedElements.add(umlComponent);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComponent) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSourceCompModel(final org.eclipse.uml2.uml.Class umlClass) {
      Model _model = umlClass.getModel();
      return _model;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComponent) {
      return umlComponent;
    }
    
    public void updateUmlComponentElement(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComponent) {
      umlComponent.setName(umlClass.getName());
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComponent, @Extension final RoutinesFacade _routinesFacade) {
      final org.eclipse.uml2.uml.Package classPackage = umlClass.getPackage();
      this.correspondenceModel.createAndAddCorrespondence(Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(umlComponent)), Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(classPackage)));
    }
  }
  
  public CreateUmlComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateUmlComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlComponentRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    
    Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(umlClass), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (compModel == null) {
    	return;
    }
    registerObjectUnderModification(compModel);
    Component umlComponent = UMLFactoryImpl.eINSTANCE.createComponent();
    userExecution.updateUmlComponentElement(umlClass, compModel, umlComponent);
    
    // val updatedElement userExecution.getElement1(umlClass, compModel, umlComponent);
    userExecution.update0Element(umlClass, compModel, umlComponent);
    
    addCorrespondenceBetween(userExecution.getElement2(umlClass, compModel, umlComponent), userExecution.getElement3(umlClass, compModel, umlComponent), "");
    
    userExecution.callRoutine1(umlClass, compModel, umlComponent, actionsFacade);
    
    postprocessElements();
  }
}
