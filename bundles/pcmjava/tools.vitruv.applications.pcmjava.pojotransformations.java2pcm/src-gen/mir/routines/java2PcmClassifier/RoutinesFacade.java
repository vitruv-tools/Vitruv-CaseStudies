package mir.routines.java2PcmClassifier;

import mir.routines.java2PcmClassifier.AddDataTypeInRepositoryRoutine;
import mir.routines.java2PcmClassifier.AddcorrespondenceAndUpdateRepositoryRoutine;
import mir.routines.java2PcmClassifier.AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine;
import mir.routines.java2PcmClassifier.CheckSystemAndComponentRoutine;
import mir.routines.java2PcmClassifier.ClassMappingRoutine;
import mir.routines.java2PcmClassifier.CreateArchitecturalElementRoutine;
import mir.routines.java2PcmClassifier.CreateBasicComponentRoutine;
import mir.routines.java2PcmClassifier.CreateCollectionDataTypeRoutine;
import mir.routines.java2PcmClassifier.CreateCompositeComponentRoutine;
import mir.routines.java2PcmClassifier.CreateCompositeDataTypeRoutine;
import mir.routines.java2PcmClassifier.CreateDataTypeRoutine;
import mir.routines.java2PcmClassifier.CreateElementRoutine;
import mir.routines.java2PcmClassifier.CreateJavaPackageRoutine;
import mir.routines.java2PcmClassifier.CreateJavaSubPackagesRoutine;
import mir.routines.java2PcmClassifier.CreateOperationProvidedRoleFromTypeReferenceRoutine;
import mir.routines.java2PcmClassifier.CreateOperationProvidedRoleRoutine;
import mir.routines.java2PcmClassifier.CreatePCMInterfaceRoutine;
import mir.routines.java2PcmClassifier.CreateRepositoryRoutine;
import mir.routines.java2PcmClassifier.CreateSystemRoutine;
import mir.routines.java2PcmClassifier.CreatedInterfaceNotInContractsRoutine;
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
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createArchitecturalElement(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateArchitecturalElementRoutine routine = new CreateArchitecturalElementRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, name, rootPackageName);
    return routine.applyRoutine();
  }
  
  public boolean createRepository(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateRepositoryRoutine routine = new CreateRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, packageName, newTag);
    return routine.applyRoutine();
  }
  
  public boolean createSystem(final org.emftext.language.java.containers.Package javaPackage, final String name) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateSystemRoutine routine = new CreateSystemRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, name);
    return routine.applyRoutine();
  }
  
  public boolean createBasicComponent(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateBasicComponentRoutine routine = new CreateBasicComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, name, rootPackageName);
    return routine.applyRoutine();
  }
  
  public boolean createCompositeComponent(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCompositeComponentRoutine routine = new CreateCompositeComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, name, rootPackageName);
    return routine.applyRoutine();
  }
  
  public boolean addcorrespondenceAndUpdateRepository(final ImplementationComponentType pcmComponent, final org.emftext.language.java.containers.Package javaPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddcorrespondenceAndUpdateRepositoryRoutine routine = new AddcorrespondenceAndUpdateRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, javaPackage);
    return routine.applyRoutine();
  }
  
  public boolean createPCMInterface(final Interface javaInterface, final CompilationUnit compilationUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatePCMInterfaceRoutine routine = new CreatePCMInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, javaInterface, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean createdInterfaceNotInContracts(final Interface javaInterface, final OperationInterface pcmInterface, final CompilationUnit compilationUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatedInterfaceNotInContractsRoutine routine = new CreatedInterfaceNotInContractsRoutine(_routinesFacade, _reactionExecutionState, _caller, javaInterface, pcmInterface, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean addcorrespondenceToInterfaceAndUpdateRepository(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine routine = new AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, javaInterface, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean classMapping(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final org.emftext.language.java.containers.Package javaPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ClassMappingRoutine routine = new ClassMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, javaClass, compilationUnit, javaPackage);
    return routine.applyRoutine();
  }
  
  public boolean createDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateDataTypeRoutine routine = new CreateDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, javaClass, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean createElement(final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.containers.Package javaPackage, final CompilationUnit compilationUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateElementRoutine routine = new CreateElementRoutine(_routinesFacade, _reactionExecutionState, _caller, javaClass, javaPackage, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean checkSystemAndComponent(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CheckSystemAndComponentRoutine routine = new CheckSystemAndComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, javaClass);
    return routine.applyRoutine();
  }
  
  public boolean createCompositeDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCompositeDataTypeRoutine routine = new CreateCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, javaClass, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean createCollectionDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCollectionDataTypeRoutine routine = new CreateCollectionDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, javaClass, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean addDataTypeInRepository(final DataType pcmDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddDataTypeInRepositoryRoutine routine = new AddDataTypeInRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmDataType);
    return routine.applyRoutine();
  }
  
  public boolean createOperationProvidedRole(final TypeReference typeReference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateOperationProvidedRoleRoutine routine = new CreateOperationProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, typeReference);
    return routine.applyRoutine();
  }
  
  public boolean createOperationProvidedRoleFromTypeReference(final Classifier classifierInterface, final org.emftext.language.java.classifiers.Class javaClass, final TypeReference reference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateOperationProvidedRoleFromTypeReferenceRoutine routine = new CreateOperationProvidedRoleFromTypeReferenceRoutine(_routinesFacade, _reactionExecutionState, _caller, classifierInterface, javaClass, reference);
    return routine.applyRoutine();
  }
  
  public boolean createJavaSubPackages(final org.emftext.language.java.containers.Package javaPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaSubPackagesRoutine routine = new CreateJavaSubPackagesRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage);
    return routine.applyRoutine();
  }
  
  public boolean createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaPackageRoutine routine = new CreateJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, sourceElementMappedToPackage, parentPackage, packageName, newTag);
    return routine.applyRoutine();
  }
}
