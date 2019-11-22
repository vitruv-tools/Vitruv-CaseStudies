package mir.routines.umlXpcmComponent_L2R;

import java.io.IOException;
import mir.routines.umlXpcmComponent_L2R.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RepositoryComponent_BidirectionalCheckRoutine extends AbstractRepairRoutineRealization {
  private RepositoryComponent_BidirectionalCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, final String routineName, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Package)) {
        org.eclipse.uml2.uml.Package componentPackage_ = ((org.eclipse.uml2.uml.Package)affectedEObject);
        EList<PackageableElement> _packagedElements = componentPackage_.getPackagedElements();
        for (final PackageableElement implementation__candidate : _packagedElements) {
          if (((implementation__candidate != null) && (implementation__candidate instanceof org.eclipse.uml2.uml.Class))) {
            org.eclipse.uml2.uml.Class implementation_ = ((org.eclipse.uml2.uml.Class) implementation__candidate);
            {
              EObject repositoryPackage__candidate = componentPackage_.eContainer();
              if (((repositoryPackage__candidate != null) && (repositoryPackage__candidate instanceof org.eclipse.uml2.uml.Package))) {
                org.eclipse.uml2.uml.Package repositoryPackage_ = ((org.eclipse.uml2.uml.Package) repositoryPackage__candidate);
                EList<Operation> _ownedOperations = implementation_.getOwnedOperations();
                for (final Operation constructor__candidate : _ownedOperations) {
                  if (((constructor__candidate != null) && (constructor__candidate instanceof Operation))) {
                    Operation constructor_ = ((Operation) constructor__candidate);
                    {
                      if ((routineName == "updateComponentName")) {
                        _routinesFacade.updateComponentName(componentPackage_, repositoryPackage_, implementation_, constructor_);
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
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Package)) {
        org.eclipse.uml2.uml.Package repositoryPackage_ = ((org.eclipse.uml2.uml.Package)affectedEObject);
        EList<PackageableElement> _packagedElements_1 = repositoryPackage_.getPackagedElements();
        for (final PackageableElement componentPackage__candidate : _packagedElements_1) {
          if (((componentPackage__candidate != null) && (componentPackage__candidate instanceof org.eclipse.uml2.uml.Package))) {
            org.eclipse.uml2.uml.Package componentPackage__1 = ((org.eclipse.uml2.uml.Package) componentPackage__candidate);
            EList<PackageableElement> _packagedElements_2 = componentPackage__1.getPackagedElements();
            for (final PackageableElement implementation__candidate_1 : _packagedElements_2) {
              if (((implementation__candidate_1 != null) && (implementation__candidate_1 instanceof org.eclipse.uml2.uml.Class))) {
                org.eclipse.uml2.uml.Class implementation__1 = ((org.eclipse.uml2.uml.Class) implementation__candidate_1);
                EList<Operation> _ownedOperations = implementation__1.getOwnedOperations();
                for (final Operation constructor__candidate : _ownedOperations) {
                  if (((constructor__candidate != null) && (constructor__candidate instanceof Operation))) {
                    Operation constructor_ = ((Operation) constructor__candidate);
                    {
                      if ((routineName == "updateComponentName")) {
                        _routinesFacade.updateComponentName(componentPackage__1, repositoryPackage_, implementation__1, constructor_);
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
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Class)) {
        org.eclipse.uml2.uml.Class implementation__2 = ((org.eclipse.uml2.uml.Class)affectedEObject);
        {
          EObject componentPackage__candidate_1 = implementation__2.eContainer();
          if (((componentPackage__candidate_1 != null) && (componentPackage__candidate_1 instanceof org.eclipse.uml2.uml.Package))) {
            org.eclipse.uml2.uml.Package componentPackage__2 = ((org.eclipse.uml2.uml.Package) componentPackage__candidate_1);
            {
              EObject repositoryPackage__candidate = componentPackage__2.eContainer();
              if (((repositoryPackage__candidate != null) && (repositoryPackage__candidate instanceof org.eclipse.uml2.uml.Package))) {
                org.eclipse.uml2.uml.Package repositoryPackage__1 = ((org.eclipse.uml2.uml.Package) repositoryPackage__candidate);
                EList<Operation> _ownedOperations_1 = implementation__2.getOwnedOperations();
                for (final Operation constructor__candidate_1 : _ownedOperations_1) {
                  if (((constructor__candidate_1 != null) && (constructor__candidate_1 instanceof Operation))) {
                    Operation constructor__1 = ((Operation) constructor__candidate_1);
                    {
                      if ((routineName == "updateComponentName")) {
                        _routinesFacade.updateComponentName(componentPackage__2, repositoryPackage__1, implementation__2, constructor__1);
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
      if ((affectedEObject instanceof Operation)) {
        Operation constructor__1 = ((Operation)affectedEObject);
        {
          EObject implementation__candidate_2 = constructor__1.eContainer();
          if (((implementation__candidate_2 != null) && (implementation__candidate_2 instanceof org.eclipse.uml2.uml.Class))) {
            org.eclipse.uml2.uml.Class implementation__3 = ((org.eclipse.uml2.uml.Class) implementation__candidate_2);
            {
              EObject componentPackage__candidate_1 = implementation__3.eContainer();
              if (((componentPackage__candidate_1 != null) && (componentPackage__candidate_1 instanceof org.eclipse.uml2.uml.Package))) {
                org.eclipse.uml2.uml.Package componentPackage__2 = ((org.eclipse.uml2.uml.Package) componentPackage__candidate_1);
                {
                  EObject repositoryPackage__candidate = componentPackage__2.eContainer();
                  if (((repositoryPackage__candidate != null) && (repositoryPackage__candidate instanceof org.eclipse.uml2.uml.Package))) {
                    org.eclipse.uml2.uml.Package repositoryPackage__1 = ((org.eclipse.uml2.uml.Package) repositoryPackage__candidate);
                    {
                      if ((routineName == "updateComponentName")) {
                        _routinesFacade.updateComponentName(componentPackage__2, repositoryPackage__1, implementation__3, constructor__1);
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
    }
  }
  
  public RepositoryComponent_BidirectionalCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject, final String routineName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_L2R.RepositoryComponent_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;this.routineName = routineName;
  }
  
  private EObject affectedEObject;
  
  private String routineName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RepositoryComponent_BidirectionalCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    getLogger().debug("   routineName: " + this.routineName);
    
    userExecution.callRoutine1(affectedEObject, routineName, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
