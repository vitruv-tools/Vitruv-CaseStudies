package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.mapping.DefaultLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlModelRootRoutine extends AbstractRepairRoutineRealization {
  private CreateUmlModelRootRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Repository repository, final org.eclipse.uml2.uml.Package repositoryPkg, @Extension final RoutinesFacade _routinesFacade) {
      final Model umlRootModel = UMLFactory.eINSTANCE.createModel();
      umlRootModel.setName("umlrootmodel");
      EList<org.eclipse.uml2.uml.Package> _nestedPackages = umlRootModel.getNestedPackages();
      _nestedPackages.add(repositoryPkg);
      this.persistProjectRelative(repository, umlRootModel, DefaultLiterals.UML_MODEL_FILE);
    }
  }
  
  public CreateUmlModelRootRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repository, final org.eclipse.uml2.uml.Package repositoryPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.CreateUmlModelRootRoutine.ActionUserExecution(getExecutionState(), this);
    this.repository = repository;this.repositoryPkg = repositoryPkg;
  }
  
  private Repository repository;
  
  private org.eclipse.uml2.uml.Package repositoryPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlModelRootRoutine with input:");
    getLogger().debug("   repository: " + this.repository);
    getLogger().debug("   repositoryPkg: " + this.repositoryPkg);
    
    userExecution.executeAction1(repository, repositoryPkg, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
