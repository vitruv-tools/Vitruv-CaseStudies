package mir.routines.java2PcmClassifier;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
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
  
  public boolean createArchitecturalElement(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    mir.routines.java2PcmClassifier.CreateArchitecturalElementRoutine effect = new mir.routines.java2PcmClassifier.CreateArchitecturalElementRoutine(this.executionState, calledBy, javaPackage, name, rootPackageName);
    return effect.applyRoutine();
  }
  
  public boolean createRepository(final EObject sourceElementMappedToRepository, final String packageName, final String newTag) {
    mir.routines.java2PcmClassifier.CreateRepositoryRoutine effect = new mir.routines.java2PcmClassifier.CreateRepositoryRoutine(this.executionState, calledBy, sourceElementMappedToRepository, packageName, newTag);
    return effect.applyRoutine();
  }
  
  public boolean createSystem(final org.emftext.language.java.containers.Package javaPackage, final String name) {
    mir.routines.java2PcmClassifier.CreateSystemRoutine effect = new mir.routines.java2PcmClassifier.CreateSystemRoutine(this.executionState, calledBy, javaPackage, name);
    return effect.applyRoutine();
  }
  
  public boolean createBasicComponent(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    mir.routines.java2PcmClassifier.CreateBasicComponentRoutine effect = new mir.routines.java2PcmClassifier.CreateBasicComponentRoutine(this.executionState, calledBy, javaPackage, name, rootPackageName);
    return effect.applyRoutine();
  }
  
  public boolean createCompositeComponent(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    mir.routines.java2PcmClassifier.CreateCompositeComponentRoutine effect = new mir.routines.java2PcmClassifier.CreateCompositeComponentRoutine(this.executionState, calledBy, javaPackage, name, rootPackageName);
    return effect.applyRoutine();
  }
  
  public boolean addCorrespondanceAndUpdateRepository(final ImplementationComponentType pcmComponent, final Repository pcmRepository, final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2PcmClassifier.AddCorrespondanceAndUpdateRepositoryRoutine effect = new mir.routines.java2PcmClassifier.AddCorrespondanceAndUpdateRepositoryRoutine(this.executionState, calledBy, pcmComponent, pcmRepository, javaPackage);
    return effect.applyRoutine();
  }
  
  public boolean createPCMInterface(final Interface javaInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreatePCMInterfaceRoutine effect = new mir.routines.java2PcmClassifier.CreatePCMInterfaceRoutine(this.executionState, calledBy, javaInterface, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean createdInterfaceNotInContracts(final Interface javaInterface, final OperationInterface pcmIface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreatedInterfaceNotInContractsRoutine effect = new mir.routines.java2PcmClassifier.CreatedInterfaceNotInContractsRoutine(this.executionState, calledBy, javaInterface, pcmIface, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean addCorrespondanceToInterfaceAndUpdateRepository(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface javaInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.AddCorrespondanceToInterfaceAndUpdateRepositoryRoutine effect = new mir.routines.java2PcmClassifier.AddCorrespondanceToInterfaceAndUpdateRepositoryRoutine(this.executionState, calledBy, pcmInterface, pcmRepository, javaInterface, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean createDataType(final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreateDataTypeRoutine effect = new mir.routines.java2PcmClassifier.CreateDataTypeRoutine(this.executionState, calledBy, cls, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean createElement(final Repository repository, final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreateElementRoutine effect = new mir.routines.java2PcmClassifier.CreateElementRoutine(this.executionState, calledBy, repository, javaClass, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean checkSystemAndComponent(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
    mir.routines.java2PcmClassifier.CheckSystemAndComponentRoutine effect = new mir.routines.java2PcmClassifier.CheckSystemAndComponentRoutine(this.executionState, calledBy, javaPackage, javaClass);
    return effect.applyRoutine();
  }
  
  public boolean createCompositeDataType(final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreateCompositeDataTypeRoutine effect = new mir.routines.java2PcmClassifier.CreateCompositeDataTypeRoutine(this.executionState, calledBy, cls, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean createCollectionDataType(final org.emftext.language.java.classifiers.Class cls, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreateCollectionDataTypeRoutine effect = new mir.routines.java2PcmClassifier.CreateCollectionDataTypeRoutine(this.executionState, calledBy, cls, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean addDataTypeInRepository(final Repository pcmRepository, final DataType pcmDataType) {
    mir.routines.java2PcmClassifier.AddDataTypeInRepositoryRoutine effect = new mir.routines.java2PcmClassifier.AddDataTypeInRepositoryRoutine(this.executionState, calledBy, pcmRepository, pcmDataType);
    return effect.applyRoutine();
  }
  
  public boolean createOperationProvidedRole(final TypeReference typeReference) {
    mir.routines.java2PcmClassifier.CreateOperationProvidedRoleRoutine effect = new mir.routines.java2PcmClassifier.CreateOperationProvidedRoleRoutine(this.executionState, calledBy, typeReference);
    return effect.applyRoutine();
  }
  
  public boolean createOperationProvidedRoleFromTypeReference(final Classifier classifierInterface, final org.emftext.language.java.classifiers.Class javaClass, final TypeReference reference) {
    mir.routines.java2PcmClassifier.CreateOperationProvidedRoleFromTypeReferenceRoutine effect = new mir.routines.java2PcmClassifier.CreateOperationProvidedRoleFromTypeReferenceRoutine(this.executionState, calledBy, classifierInterface, javaClass, reference);
    return effect.applyRoutine();
  }
  
  public boolean createJavaSubPackages(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2PcmClassifier.CreateJavaSubPackagesRoutine effect = new mir.routines.java2PcmClassifier.CreateJavaSubPackagesRoutine(this.executionState, calledBy, javaPackage);
    return effect.applyRoutine();
  }
  
  public boolean createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.java2PcmClassifier.CreateJavaPackageRoutine effect = new mir.routines.java2PcmClassifier.CreateJavaPackageRoutine(this.executionState, calledBy, sourceElementMappedToPackage, parentPackage, packageName, newTag);
    return effect.applyRoutine();
  }
}
