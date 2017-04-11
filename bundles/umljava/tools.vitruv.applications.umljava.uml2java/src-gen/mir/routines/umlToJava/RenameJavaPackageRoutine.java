package mir.routines.umlToJava;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.List;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.applications.umljava.util.UmlUtil;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameJavaPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJPackage(final org.eclipse.uml2.uml.Package uPackage) {
      return uPackage;
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package uPackage, final org.emftext.language.java.containers.Package jPackage) {
      return jPackage;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Package uPackage, final org.emftext.language.java.containers.Package jPackage) {
      jPackage.getNamespaces().clear();
      EList<String> _namespaces = jPackage.getNamespaces();
      List<String> _umlParentNamespaceAsStringList = UmlUtil.getUmlParentNamespaceAsStringList(uPackage);
      Iterables.<String>addAll(_namespaces, _umlParentNamespaceAsStringList);
      jPackage.setName(uPackage.getName());
      this.persistProjectRelative(uPackage, jPackage, JavaPersistenceHelper.buildJavaFilePath(jPackage));
    }
  }
  
  public RenameJavaPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package uPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.RenameJavaPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uPackage = uPackage;
  }
  
  private org.eclipse.uml2.uml.Package uPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaPackageRoutine with input:");
    getLogger().debug("   Package: " + this.uPackage);
    
    org.emftext.language.java.containers.Package jPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJPackage(uPackage), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null);
    if (jPackage == null) {
    	return;
    }
    registerObjectUnderModification(jPackage);
    // val updatedElement userExecution.getElement1(uPackage, jPackage);
    userExecution.update0Element(uPackage, jPackage);
    
    postprocessElements();
  }
}
