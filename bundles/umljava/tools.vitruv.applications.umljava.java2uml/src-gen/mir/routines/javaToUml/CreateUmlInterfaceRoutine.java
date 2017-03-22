package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface jI, final org.eclipse.uml2.uml.Interface uI) {
      return uI;
    }
    
    public EObject getElement4(final Interface jI, final org.eclipse.uml2.uml.Interface uI) {
      EObject _eContainer = jI.eContainer();
      return _eContainer;
    }
    
    public EObject getElement2(final Interface jI, final org.eclipse.uml2.uml.Interface uI) {
      return jI;
    }
    
    public EObject getElement3(final Interface jI, final org.eclipse.uml2.uml.Interface uI) {
      return uI;
    }
    
    public void updateUIElement(final Interface jI, final org.eclipse.uml2.uml.Interface uI) {
      String _name = jI.getName();
      uI.setName(_name);
      uI.setVisibility(VisibilityKind.PUBLIC_LITERAL);
    }
    
    public void callRoutine1(final Interface jI, final org.eclipse.uml2.uml.Interface uI, @Extension final RoutinesFacade _routinesFacade) {
      final Model uModel = JavaToUmlHelper.getUmlModel(this.correspondenceModel);
      EList<PackageableElement> _packagedElements = uModel.getPackagedElements();
      _packagedElements.add(uI);
      this.persistProjectRelative(jI, uModel, "model/model.uml");
    }
  }
  
  public CreateUmlInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface jI) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.CreateUmlInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jI = jI;
  }
  
  private Interface jI;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.jI);
    
    org.eclipse.uml2.uml.Interface uI = UMLFactoryImpl.eINSTANCE.createInterface();
    initializeCreateElementState(uI);
    userExecution.updateUIElement(jI, uI);
    
    userExecution.callRoutine1(jI, uI, actionsFacade);
    
    addCorrespondenceBetween(userExecution.getElement1(jI, uI), userExecution.getElement2(jI, uI), "");
    
    addCorrespondenceBetween(userExecution.getElement3(jI, uI), userExecution.getElement4(jI, uI), "");
    
    postprocessElementStates();
  }
}
