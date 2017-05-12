package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlSuperClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddUmlSuperClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class jSuperClass, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class jSuperClass) {
      return jClass;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class jSuperClass, final org.eclipse.uml2.uml.Class uClass) {
      UmlClassifierAndPackageUtil.addUmlSuperClassifier(uClass, 
        JavaToUmlHelper.<org.eclipse.uml2.uml.Class>findFirstCorrespondeningUmlElementByNameAndType(this.correspondenceModel, 
          jSuperClass.getName(), org.eclipse.uml2.uml.Class.class));
    }
  }
  
  public AddUmlSuperClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class jSuperClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlSuperClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;this.jSuperClass = jSuperClass;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private org.emftext.language.java.classifiers.Class jSuperClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlSuperClassRoutine with input:");
    getLogger().debug("   Class: " + this.jClass);
    getLogger().debug("   Class: " + this.jSuperClass);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, jSuperClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    if (uClass == null) {
    	return;
    }
    registerObjectUnderModification(uClass);
    // val updatedElement userExecution.getElement1(jClass, jSuperClass, uClass);
    userExecution.update0Element(jClass, jSuperClass, uClass);
    
    postprocessElements();
  }
}
