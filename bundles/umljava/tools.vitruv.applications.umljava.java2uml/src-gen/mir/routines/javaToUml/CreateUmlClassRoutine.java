package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public EObject getElement4(final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass) {
      EObject _eContainer = jClass.eContainer();
      return _eContainer;
    }
    
    public EObject getElement2(final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass) {
      return jClass;
    }
    
    public EObject getElement3(final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public void updateUClassElement(final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass) {
      String _name = jClass.getName();
      uClass.setName(_name);
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass, @Extension final RoutinesFacade _routinesFacade) {
      final Model uModel = JavaToUmlHelper.getUmlModel(this.correspondenceModel);
      EList<PackageableElement> _packagedElements = uModel.getPackagedElements();
      _packagedElements.add(uClass);
      this.persistProjectRelative(jClass, uModel, "model/model.uml");
    }
  }
  
  public CreateUmlClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.CreateUmlClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlClassRoutine with input:");
    getLogger().debug("   Class: " + this.jClass);
    
    org.eclipse.uml2.uml.Class uClass = UMLFactoryImpl.eINSTANCE.createClass();
    initializeCreateElementState(uClass);
    userExecution.updateUClassElement(jClass, uClass);
    
    userExecution.callRoutine1(jClass, uClass, actionsFacade);
    
    addCorrespondenceBetween(userExecution.getElement1(jClass, uClass), userExecution.getElement2(jClass, uClass), "");
    
    addCorrespondenceBetween(userExecution.getElement3(jClass, uClass), userExecution.getElement4(jClass, uClass), "");
    
    postprocessElementStates();
  }
}
