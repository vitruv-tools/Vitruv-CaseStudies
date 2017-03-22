package mir.routines.javaToUml;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.applications.umljava.util.JavaUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlClassMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlClassMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCustomType(final ClassMethod jMeth, final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass) {
      TypeReference _typeReference = jMeth.getTypeReference();
      ConcreteClassifier _classifierfromTypeRef = JavaUtil.getClassifierfromTypeRef(_typeReference);
      return _classifierfromTypeRef;
    }
    
    public EObject getElement1(final ClassMethod jMeth, final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass, final org.eclipse.uml2.uml.Class customType, final Operation uMeth) {
      return uMeth;
    }
    
    public EObject getCorrepondenceSourceUClass(final ClassMethod jMeth, final org.emftext.language.java.classifiers.Class jClass) {
      return jClass;
    }
    
    public void update0Element(final ClassMethod jMeth, final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass, final org.eclipse.uml2.uml.Class customType, final Operation uMeth) {
      EList<Operation> _ownedOperations = uClass.getOwnedOperations();
      _ownedOperations.add(uMeth);
    }
    
    public EObject getElement2(final ClassMethod jMeth, final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass, final org.eclipse.uml2.uml.Class customType, final Operation uMeth) {
      return jMeth;
    }
    
    public void updateUMethElement(final ClassMethod jMeth, final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass, final org.eclipse.uml2.uml.Class customType, final Operation uMeth) {
      String _name = jMeth.getName();
      uMeth.setName(_name);
      TypeReference _typeReference = jMeth.getTypeReference();
      boolean _notEquals = (!Objects.equal(_typeReference, null));
      if (_notEquals) {
        TypeReference _typeReference_1 = jMeth.getTypeReference();
        Model _umlModel = JavaToUmlHelper.getUmlModel(this.correspondenceModel);
        Type _umlType = JavaToUmlHelper.getUmlType(_typeReference_1, customType, _umlModel);
        uMeth.setType(_umlType);
      }
    }
    
    public EObject getElement3(final ClassMethod jMeth, final org.emftext.language.java.classifiers.Class jClass, final org.eclipse.uml2.uml.Class uClass, final org.eclipse.uml2.uml.Class customType, final Operation uMeth) {
      return uClass;
    }
  }
  
  public CreateUmlClassMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ClassMethod jMeth, final org.emftext.language.java.classifiers.Class jClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.CreateUmlClassMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.jClass = jClass;
  }
  
  private ClassMethod jMeth;
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlClassMethodRoutine with input:");
    getLogger().debug("   ClassMethod: " + this.jMeth);
    getLogger().debug("   Class: " + this.jClass);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jMeth, jClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    if (uClass == null) {
    	return;
    }
    initializeRetrieveElementState(uClass);
    org.eclipse.uml2.uml.Class customType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomType(jMeth, jClass, uClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(customType);
    Operation uMeth = UMLFactoryImpl.eINSTANCE.createOperation();
    initializeCreateElementState(uMeth);
    userExecution.updateUMethElement(jMeth, jClass, uClass, customType, uMeth);
    
    addCorrespondenceBetween(userExecution.getElement1(jMeth, jClass, uClass, customType, uMeth), userExecution.getElement2(jMeth, jClass, uClass, customType, uMeth), "");
    
    // val updatedElement userExecution.getElement3(jMeth, jClass, uClass, customType, uMeth);
    userExecution.update0Element(jMeth, jClass, uClass, customType, uMeth);
    
    postprocessElementStates();
  }
}
