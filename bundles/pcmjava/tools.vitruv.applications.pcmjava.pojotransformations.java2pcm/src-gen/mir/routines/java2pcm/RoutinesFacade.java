package mir.routines.java2pcm;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createArchitecturalElement(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2pcm.CreateArchitecturalElementRoutine effect = new mir.routines.java2pcm.CreateArchitecturalElementRoutine(this.executionState, calledBy,
    	javaPackage);
    effect.applyRoutine();
  }
  
  public void createPCMRepository(final EObject sourceElementMappedToRepository, final String packageName, final String newTag) {
    mir.routines.java2pcm.CreatePCMRepositoryRoutine effect = new mir.routines.java2pcm.CreatePCMRepositoryRoutine(this.executionState, calledBy,
    	sourceElementMappedToRepository, packageName, newTag);
    effect.applyRoutine();
  }
  
  public void createSystem(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2pcm.CreateSystemRoutine effect = new mir.routines.java2pcm.CreateSystemRoutine(this.executionState, calledBy,
    	javaPackage);
    effect.applyRoutine();
  }
  
  public void createBasicComponent(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2pcm.CreateBasicComponentRoutine effect = new mir.routines.java2pcm.CreateBasicComponentRoutine(this.executionState, calledBy,
    	javaPackage);
    effect.applyRoutine();
  }
  
  public void createCompositeComponent(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2pcm.CreateCompositeComponentRoutine effect = new mir.routines.java2pcm.CreateCompositeComponentRoutine(this.executionState, calledBy,
    	javaPackage);
    effect.applyRoutine();
  }
  
  public void addComponentToRepository(final ImplementationComponentType pcmComponent, final Repository pcmRepository) {
    mir.routines.java2pcm.AddComponentToRepositoryRoutine effect = new mir.routines.java2pcm.AddComponentToRepositoryRoutine(this.executionState, calledBy,
    	pcmComponent, pcmRepository);
    effect.applyRoutine();
  }
  
  public void createPCMInterface(final Interface javaInterface, final CompilationUnit javaPackage) {
    mir.routines.java2pcm.CreatePCMInterfaceRoutine effect = new mir.routines.java2pcm.CreatePCMInterfaceRoutine(this.executionState, calledBy,
    	javaInterface, javaPackage);
    effect.applyRoutine();
  }
  
  public void createCompositeDataType(final org.emftext.language.java.classifiers.Class cls) {
    mir.routines.java2pcm.CreateCompositeDataTypeRoutine effect = new mir.routines.java2pcm.CreateCompositeDataTypeRoutine(this.executionState, calledBy,
    	cls);
    effect.applyRoutine();
  }
  
  public void createJavaSubPackages(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2pcm.CreateJavaSubPackagesRoutine effect = new mir.routines.java2pcm.CreateJavaSubPackagesRoutine(this.executionState, calledBy,
    	javaPackage);
    effect.applyRoutine();
  }
  
  public void createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.java2pcm.CreateJavaPackageRoutine effect = new mir.routines.java2pcm.CreateJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, parentPackage, packageName, newTag);
    effect.applyRoutine();
  }
}
