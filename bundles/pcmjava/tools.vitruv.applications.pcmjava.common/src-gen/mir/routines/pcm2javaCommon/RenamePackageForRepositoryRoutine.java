package mir.routines.pcm2javaCommon;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenamePackageForRepositoryRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenamePackageForRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Repository repository, final org.emftext.language.java.containers.Package rootPackage) {
      return rootPackage;
    }
    
    public void update0Element(final Repository repository, final org.emftext.language.java.containers.Package rootPackage) {
      rootPackage.setName(repository.getEntityName());
    }
    
    public String getRetrieveTag1(final Repository repository) {
      return "repository_root";
    }
    
    public EObject getCorrepondenceSourceRootPackage(final Repository repository) {
      return repository;
    }
    
    public void callRoutine1(final Repository repository, final org.emftext.language.java.containers.Package rootPackage, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.renameJavaPackage(repository, rootPackage, "contracts", "contracts");
      _routinesFacade.renameJavaPackage(repository, rootPackage, "datatypes", "datatypes");
      Iterable<BasicComponent> _filter = Iterables.<BasicComponent>filter(repository.getComponents__Repository(), BasicComponent.class);
      for (final BasicComponent component : _filter) {
        _routinesFacade.renameComponentPackageAndClass(component);
      }
      Iterable<OperationInterface> _filter_1 = Iterables.<OperationInterface>filter(repository.getInterfaces__Repository(), OperationInterface.class);
      for (final OperationInterface interface_ : _filter_1) {
        _routinesFacade.renameInterface(interface_);
      }
      Iterable<CompositeDataType> _filter_2 = Iterables.<CompositeDataType>filter(repository.getDataTypes__Repository(), CompositeDataType.class);
      for (final CompositeDataType dataType : _filter_2) {
        _routinesFacade.renameCompositeDataType(dataType);
      }
      Iterable<CollectionDataType> _filter_3 = Iterables.<CollectionDataType>filter(repository.getDataTypes__Repository(), CollectionDataType.class);
      for (final CollectionDataType dataType_1 : _filter_3) {
        _routinesFacade.renameCollectionDataType(dataType_1);
      }
      this.persistProjectRelative(repository, rootPackage, JavaPersistenceHelper.buildJavaFilePath(rootPackage));
    }
  }
  
  public RenamePackageForRepositoryRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repository) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.RenamePackageForRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(getExecutionState(), this);
    this.repository = repository;
  }
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenamePackageForRepositoryRoutine with input:");
    getLogger().debug("   repository: " + this.repository);
    
    org.emftext.language.java.containers.Package rootPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRootPackage(repository), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(repository), 
    	false // asserted
    	);
    if (rootPackage == null) {
    	return false;
    }
    registerObjectUnderModification(rootPackage);
    // val updatedElement userExecution.getElement1(repository, rootPackage);
    userExecution.update0Element(repository, rootPackage);
    
    userExecution.callRoutine1(repository, rootPackage, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
