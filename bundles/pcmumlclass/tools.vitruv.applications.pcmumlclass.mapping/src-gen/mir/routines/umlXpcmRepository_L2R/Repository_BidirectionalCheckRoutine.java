package mir.routines.umlXpcmRepository_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRepository_L2R.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Repository_BidirectionalCheckRoutine extends AbstractRepairRoutineRealization {
  private Repository_BidirectionalCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, final String routineName, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Package)) {
        org.eclipse.uml2.uml.Package repositoryPkg_ = ((org.eclipse.uml2.uml.Package)affectedEObject);
        EList<PackageableElement> _packagedElements = repositoryPkg_.getPackagedElements();
        for (final PackageableElement datatypesPkg__candidate : _packagedElements) {
          if (((datatypesPkg__candidate != null) && (datatypesPkg__candidate instanceof org.eclipse.uml2.uml.Package))) {
            org.eclipse.uml2.uml.Package datatypesPkg_ = ((org.eclipse.uml2.uml.Package) datatypesPkg__candidate);
            String _name = datatypesPkg_.getName();
            boolean _tripleEquals = ("datatypes" == _name);
            if (_tripleEquals) {
              EList<PackageableElement> _packagedElements_1 = repositoryPkg_.getPackagedElements();
              for (final PackageableElement contractsPkg__candidate : _packagedElements_1) {
                if (((contractsPkg__candidate != null) && (contractsPkg__candidate instanceof org.eclipse.uml2.uml.Package))) {
                  org.eclipse.uml2.uml.Package contractsPkg_ = ((org.eclipse.uml2.uml.Package) contractsPkg__candidate);
                  String _name_1 = contractsPkg_.getName();
                  boolean _tripleEquals_1 = ("contracts" == _name_1);
                  if (_tripleEquals_1) {
                    if ((routineName == "updateRepoName")) {
                      _routinesFacade.updateRepoName(repositoryPkg_, contractsPkg_, datatypesPkg_);
                    }
                    return;
                  }
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Package)) {
        org.eclipse.uml2.uml.Package contractsPkg__1 = ((org.eclipse.uml2.uml.Package)affectedEObject);
        String _name_2 = contractsPkg__1.getName();
        boolean _tripleEquals_2 = ("contracts" == _name_2);
        if (_tripleEquals_2) {
          EObject repositoryPkg__candidate = contractsPkg__1.eContainer();
          if (((repositoryPkg__candidate != null) && (repositoryPkg__candidate instanceof org.eclipse.uml2.uml.Package))) {
            org.eclipse.uml2.uml.Package repositoryPkg__1 = ((org.eclipse.uml2.uml.Package) repositoryPkg__candidate);
            EList<PackageableElement> _packagedElements_2 = repositoryPkg__1.getPackagedElements();
            for (final PackageableElement datatypesPkg__candidate_1 : _packagedElements_2) {
              if (((datatypesPkg__candidate_1 != null) && (datatypesPkg__candidate_1 instanceof org.eclipse.uml2.uml.Package))) {
                org.eclipse.uml2.uml.Package datatypesPkg__1 = ((org.eclipse.uml2.uml.Package) datatypesPkg__candidate_1);
                String _name_3 = datatypesPkg__1.getName();
                boolean _tripleEquals_3 = ("datatypes" == _name_3);
                if (_tripleEquals_3) {
                  if ((routineName == "updateRepoName")) {
                    _routinesFacade.updateRepoName(repositoryPkg__1, contractsPkg__1, datatypesPkg__1);
                  }
                  return;
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Package)) {
        org.eclipse.uml2.uml.Package datatypesPkg__2 = ((org.eclipse.uml2.uml.Package)affectedEObject);
        String _name_4 = datatypesPkg__2.getName();
        boolean _tripleEquals_4 = ("datatypes" == _name_4);
        if (_tripleEquals_4) {
          EObject repositoryPkg__candidate_1 = datatypesPkg__2.eContainer();
          if (((repositoryPkg__candidate_1 != null) && (repositoryPkg__candidate_1 instanceof org.eclipse.uml2.uml.Package))) {
            org.eclipse.uml2.uml.Package repositoryPkg__2 = ((org.eclipse.uml2.uml.Package) repositoryPkg__candidate_1);
            EList<PackageableElement> _packagedElements_3 = repositoryPkg__2.getPackagedElements();
            for (final PackageableElement contractsPkg__candidate_1 : _packagedElements_3) {
              if (((contractsPkg__candidate_1 != null) && (contractsPkg__candidate_1 instanceof org.eclipse.uml2.uml.Package))) {
                org.eclipse.uml2.uml.Package contractsPkg__2 = ((org.eclipse.uml2.uml.Package) contractsPkg__candidate_1);
                String _name_5 = contractsPkg__2.getName();
                boolean _tripleEquals_5 = ("contracts" == _name_5);
                if (_tripleEquals_5) {
                  if ((routineName == "updateRepoName")) {
                    _routinesFacade.updateRepoName(repositoryPkg__2, contractsPkg__2, datatypesPkg__2);
                  }
                  return;
                }
              }
            }
          }
        }
      }
    }
  }
  
  public Repository_BidirectionalCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject, final String routineName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_L2R.Repository_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;this.routineName = routineName;
  }
  
  private EObject affectedEObject;
  
  private String routineName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Repository_BidirectionalCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    getLogger().debug("   routineName: " + this.routineName);
    
    userExecution.callRoutine1(affectedEObject, routineName, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
