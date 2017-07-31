package mir.routines.java2PcmClassifier;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createArchitecturalElement(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    mir.routines.java2PcmClassifier.CreateArchitecturalElementRoutine effect = new mir.routines.java2PcmClassifier.CreateArchitecturalElementRoutine(this.executionState, calledBy,
    	javaPackage, name, rootPackageName);
    effect.applyRoutine();
  }
  
  public void createPCMRepository(final EObject sourceElementMappedToRepository, final String packageName, final String newTag) {
    mir.routines.java2PcmClassifier.CreatePCMRepositoryRoutine effect = new mir.routines.java2PcmClassifier.CreatePCMRepositoryRoutine(this.executionState, calledBy,
    	sourceElementMappedToRepository, packageName, newTag);
    effect.applyRoutine();
  }
  
  public void createSystem(final org.emftext.language.java.containers.Package javaPackage, final String name) {
    mir.routines.java2PcmClassifier.CreateSystemRoutine effect = new mir.routines.java2PcmClassifier.CreateSystemRoutine(this.executionState, calledBy,
    	javaPackage, name);
    effect.applyRoutine();
  }
  
  public void createBasicComponent(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    mir.routines.java2PcmClassifier.CreateBasicComponentRoutine effect = new mir.routines.java2PcmClassifier.CreateBasicComponentRoutine(this.executionState, calledBy,
    	javaPackage, name, rootPackageName);
    effect.applyRoutine();
  }
  
  public void createCompositeComponent(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    mir.routines.java2PcmClassifier.CreateCompositeComponentRoutine effect = new mir.routines.java2PcmClassifier.CreateCompositeComponentRoutine(this.executionState, calledBy,
    	javaPackage, name, rootPackageName);
    effect.applyRoutine();
  }
  
  public void addComponentToRepository(final ImplementationComponentType pcmComponent, final Repository pcmRepository) {
    mir.routines.java2PcmClassifier.AddComponentToRepositoryRoutine effect = new mir.routines.java2PcmClassifier.AddComponentToRepositoryRoutine(this.executionState, calledBy,
    	pcmComponent, pcmRepository);
    effect.applyRoutine();
  }
  
  public void createPCMInterface(final Interface javaInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreatePCMInterfaceRoutine effect = new mir.routines.java2PcmClassifier.CreatePCMInterfaceRoutine(this.executionState, calledBy,
    	javaInterface, compilationUnit);
    effect.applyRoutine();
  }
  
  public void addInterfaceToRepository(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface javaInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.AddInterfaceToRepositoryRoutine effect = new mir.routines.java2PcmClassifier.AddInterfaceToRepositoryRoutine(this.executionState, calledBy,
    	pcmInterface, pcmRepository, javaInterface, compilationUnit);
    effect.applyRoutine();
  }
  
  public void createElement(final Repository repository, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreateElementRoutine effect = new mir.routines.java2PcmClassifier.CreateElementRoutine(this.executionState, calledBy,
    	repository, javaClass, compilationUnit);
    effect.applyRoutine();
  }
  
  public void checkSystemAndComponent(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
    mir.routines.java2PcmClassifier.CheckSystemAndComponentRoutine effect = new mir.routines.java2PcmClassifier.CheckSystemAndComponentRoutine(this.executionState, calledBy,
    	javaPackage, javaClass);
    effect.applyRoutine();
  }
  
  public void createCompositeDataType(final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreateCompositeDataTypeRoutine effect = new mir.routines.java2PcmClassifier.CreateCompositeDataTypeRoutine(this.executionState, calledBy,
    	cls, compilationUnit);
    effect.applyRoutine();
  }
  
  public void addDataTypeInRepository(final Repository pcmRepository, final CompositeDataType pcmDataType) {
    mir.routines.java2PcmClassifier.AddDataTypeInRepositoryRoutine effect = new mir.routines.java2PcmClassifier.AddDataTypeInRepositoryRoutine(this.executionState, calledBy,
    	pcmRepository, pcmDataType);
    effect.applyRoutine();
  }
  
  public void createJavaSubPackages(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2PcmClassifier.CreateJavaSubPackagesRoutine effect = new mir.routines.java2PcmClassifier.CreateJavaSubPackagesRoutine(this.executionState, calledBy,
    	javaPackage);
    effect.applyRoutine();
  }
  
  public void createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.java2PcmClassifier.CreateJavaPackageRoutine effect = new mir.routines.java2PcmClassifier.CreateJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, parentPackage, packageName, newTag);
    effect.applyRoutine();
  }
}
