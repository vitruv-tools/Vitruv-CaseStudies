package mir.routines.class2comp;

import java.io.IOException;
import java.util.Collections;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlComponentForClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlComponentForClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComp) {
      return compModel;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComp) {
      EList<PackageableElement> _packagedElements = compModel.getPackagedElements();
      _packagedElements.add(umlComp);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComp) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSourceCompModel(final org.eclipse.uml2.uml.Class umlClass) {
      Model _model = umlClass.getModel();
      return _model;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComp) {
      return umlComp;
    }
    
    public void updateUmlCompElement(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComp) {
      umlComp.setName(umlClass.getName());
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final Component umlComp, @Extension final RoutinesFacade _routinesFacade) {
      final org.eclipse.uml2.uml.Package classPackage = umlClass.getPackage();
      this.correspondenceModel.createAndAddCorrespondence(Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(umlComp)), Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(classPackage)));
    }
  }
  
  public CreateUmlComponentForClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateUmlComponentForClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlComponentForClassRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    
    org.eclipse.uml2.uml.Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(umlClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compModel == null) {
    	return false;
    }
    registerObjectUnderModification(compModel);
    org.eclipse.uml2.uml.Component umlComp = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createComponent();
    notifyObjectCreated(umlComp);
    userExecution.updateUmlCompElement(umlClass, compModel, umlComp);
    
    // val updatedElement userExecution.getElement1(umlClass, compModel, umlComp);
    userExecution.update0Element(umlClass, compModel, umlComp);
    
    addCorrespondenceBetween(userExecution.getElement2(umlClass, compModel, umlComp), userExecution.getElement3(umlClass, compModel, umlComp), "");
    
    userExecution.callRoutine1(umlClass, compModel, umlComp, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
