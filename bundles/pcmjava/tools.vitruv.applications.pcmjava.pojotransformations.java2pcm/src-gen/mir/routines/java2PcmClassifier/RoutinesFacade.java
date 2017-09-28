package mir.routines.java2PcmClassifier;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import org.palladiosimulator.pcm.repository.OperationInterface;
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
  
  public boolean createRepository(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag) {
    mir.routines.java2PcmClassifier.CreateRepositoryRoutine effect = new mir.routines.java2PcmClassifier.CreateRepositoryRoutine(this.executionState, calledBy, javaPackage, packageName, newTag);
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
  
  public boolean addcorrespondenceAndUpdateRepository(final ImplementationComponentType pcmComponent, final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2PcmClassifier.AddcorrespondenceAndUpdateRepositoryRoutine effect = new mir.routines.java2PcmClassifier.AddcorrespondenceAndUpdateRepositoryRoutine(this.executionState, calledBy, pcmComponent, javaPackage);
    return effect.applyRoutine();
  }
  
  public boolean createPCMInterface(final Interface javaInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreatePCMInterfaceRoutine effect = new mir.routines.java2PcmClassifier.CreatePCMInterfaceRoutine(this.executionState, calledBy, javaInterface, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean createdInterfaceNotInContracts(final Interface javaInterface, final OperationInterface pcmInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreatedInterfaceNotInContractsRoutine effect = new mir.routines.java2PcmClassifier.CreatedInterfaceNotInContractsRoutine(this.executionState, calledBy, javaInterface, pcmInterface, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean addcorrespondenceToInterfaceAndUpdateRepository(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine effect = new mir.routines.java2PcmClassifier.AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine(this.executionState, calledBy, pcmInterface, javaInterface, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean classMapping(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2PcmClassifier.ClassMappingRoutine effect = new mir.routines.java2PcmClassifier.ClassMappingRoutine(this.executionState, calledBy, javaClass, compilationUnit, javaPackage);
    return effect.applyRoutine();
  }
  
  public boolean createDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreateDataTypeRoutine effect = new mir.routines.java2PcmClassifier.CreateDataTypeRoutine(this.executionState, calledBy, javaClass, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean createElement(final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.containers.Package javaPackage, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreateElementRoutine effect = new mir.routines.java2PcmClassifier.CreateElementRoutine(this.executionState, calledBy, javaClass, javaPackage, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean checkSystemAndComponent(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
    mir.routines.java2PcmClassifier.CheckSystemAndComponentRoutine effect = new mir.routines.java2PcmClassifier.CheckSystemAndComponentRoutine(this.executionState, calledBy, javaPackage, javaClass);
    return effect.applyRoutine();
  }
  
  public boolean createCompositeDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreateCompositeDataTypeRoutine effect = new mir.routines.java2PcmClassifier.CreateCompositeDataTypeRoutine(this.executionState, calledBy, javaClass, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean createCollectionDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.CreateCollectionDataTypeRoutine effect = new mir.routines.java2PcmClassifier.CreateCollectionDataTypeRoutine(this.executionState, calledBy, javaClass, compilationUnit);
    return effect.applyRoutine();
  }
  
  public boolean addDataTypeInRepository(final DataType pcmDataType) {
    mir.routines.java2PcmClassifier.AddDataTypeInRepositoryRoutine effect = new mir.routines.java2PcmClassifier.AddDataTypeInRepositoryRoutine(this.executionState, calledBy, pcmDataType);
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
