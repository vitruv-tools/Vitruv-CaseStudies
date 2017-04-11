package mir.routines.umlToJava;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.List;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.impl.ContainersFactoryImpl;
import tools.vitruv.applications.umljava.util.UmlUtil;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package uPackage, final org.emftext.language.java.containers.Package jPackage) {
      return jPackage;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Package uPackage, final org.emftext.language.java.containers.Package jPackage) {
      return uPackage;
    }
    
    public void updateJPackageElement(final org.eclipse.uml2.uml.Package uPackage, final org.emftext.language.java.containers.Package jPackage) {
      Namespace _namespace = uPackage.getNamespace();
      boolean _tripleNotEquals = (_namespace != null);
      if (_tripleNotEquals) {
        EList<String> _namespaces = jPackage.getNamespaces();
        List<String> _umlParentNamespaceAsStringList = UmlUtil.getUmlParentNamespaceAsStringList(uPackage);
        Iterables.<String>addAll(_namespaces, _umlParentNamespaceAsStringList);
      }
      jPackage.setName(uPackage.getName());
      this.persistProjectRelative(uPackage, jPackage, JavaPersistenceHelper.buildJavaFilePath(jPackage));
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package uPackage, final org.emftext.language.java.containers.Package jPackage, @Extension final RoutinesFacade _routinesFacade) {
      Iterable<org.eclipse.uml2.uml.Package> _filter = Iterables.<org.eclipse.uml2.uml.Package>filter(uPackage.getPackagedElements(), org.eclipse.uml2.uml.Package.class);
      for (final org.eclipse.uml2.uml.Package subPackage : _filter) {
        _routinesFacade.createJavaPackage(subPackage);
      }
      Iterable<Classifier> _filter_1 = Iterables.<Classifier>filter(uPackage.getPackagedElements(), Classifier.class);
      for (final Classifier containedClassifier : _filter_1) {
        _routinesFacade.changePackageOfJavaCompilationUnit(uPackage, containedClassifier);
      }
    }
  }
  
  public CreateJavaPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package uPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uPackage = uPackage;
  }
  
  private org.eclipse.uml2.uml.Package uPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaPackageRoutine with input:");
    getLogger().debug("   Package: " + this.uPackage);
    
    org.emftext.language.java.containers.Package jPackage = ContainersFactoryImpl.eINSTANCE.createPackage();
    userExecution.updateJPackageElement(uPackage, jPackage);
    
    addCorrespondenceBetween(userExecution.getElement1(uPackage, jPackage), userExecution.getElement2(uPackage, jPackage), "");
    
    userExecution.callRoutine1(uPackage, jPackage, actionsFacade);
    
    postprocessElements();
  }
}
