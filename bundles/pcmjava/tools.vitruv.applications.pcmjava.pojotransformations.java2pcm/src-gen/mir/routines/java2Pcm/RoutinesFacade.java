package mir.routines.java2Pcm;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.Parametrizable;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createArchitecturalElement(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateArchitecturalElementRoutine routine = new mir.routines.java2PcmClassifier.CreateArchitecturalElementRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, name, rootPackageName);
    return routine.applyRoutine();
  }
  
  public boolean createRepository(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateRepositoryRoutine routine = new mir.routines.java2PcmClassifier.CreateRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, packageName, newTag);
    return routine.applyRoutine();
  }
  
  public boolean createSystem(final org.emftext.language.java.containers.Package javaPackage, final String name) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateSystemRoutine routine = new mir.routines.java2PcmClassifier.CreateSystemRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, name);
    return routine.applyRoutine();
  }
  
  public boolean createBasicComponent(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateBasicComponentRoutine routine = new mir.routines.java2PcmClassifier.CreateBasicComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, name, rootPackageName);
    return routine.applyRoutine();
  }
  
  public boolean createCompositeComponent(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateCompositeComponentRoutine routine = new mir.routines.java2PcmClassifier.CreateCompositeComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, name, rootPackageName);
    return routine.applyRoutine();
  }
  
  public boolean addcorrespondenceAndUpdateRepository(final ImplementationComponentType pcmComponent, final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.AddcorrespondenceAndUpdateRepositoryRoutine routine = new mir.routines.java2PcmClassifier.AddcorrespondenceAndUpdateRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, javaPackage);
    return routine.applyRoutine();
  }
  
  public boolean createPCMInterface(final Interface javaInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreatePCMInterfaceRoutine routine = new mir.routines.java2PcmClassifier.CreatePCMInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, javaInterface, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean createdInterfaceNotInContracts(final Interface javaInterface, final OperationInterface pcmInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreatedInterfaceNotInContractsRoutine routine = new mir.routines.java2PcmClassifier.CreatedInterfaceNotInContractsRoutine(_routinesFacade, _reactionExecutionState, _caller, javaInterface, pcmInterface, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean addcorrespondenceToInterfaceAndUpdateRepository(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine routine = new mir.routines.java2PcmClassifier.AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, javaInterface, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean classMapping(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.ClassMappingRoutine routine = new mir.routines.java2PcmClassifier.ClassMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, javaClass, compilationUnit, javaPackage);
    return routine.applyRoutine();
  }
  
  public boolean createDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateDataTypeRoutine routine = new mir.routines.java2PcmClassifier.CreateDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, javaClass, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean createElement(final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.containers.Package javaPackage, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateElementRoutine routine = new mir.routines.java2PcmClassifier.CreateElementRoutine(_routinesFacade, _reactionExecutionState, _caller, javaClass, javaPackage, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean checkSystemAndComponent(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CheckSystemAndComponentRoutine routine = new mir.routines.java2PcmClassifier.CheckSystemAndComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage, javaClass);
    return routine.applyRoutine();
  }
  
  public boolean createCompositeDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateCompositeDataTypeRoutine routine = new mir.routines.java2PcmClassifier.CreateCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, javaClass, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean createCollectionDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateCollectionDataTypeRoutine routine = new mir.routines.java2PcmClassifier.CreateCollectionDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, javaClass, compilationUnit);
    return routine.applyRoutine();
  }
  
  public boolean addDataTypeInRepository(final DataType pcmDataType) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.AddDataTypeInRepositoryRoutine routine = new mir.routines.java2PcmClassifier.AddDataTypeInRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmDataType);
    return routine.applyRoutine();
  }
  
  public boolean createOperationProvidedRole(final TypeReference typeReference) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateOperationProvidedRoleRoutine routine = new mir.routines.java2PcmClassifier.CreateOperationProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, typeReference);
    return routine.applyRoutine();
  }
  
  public boolean createOperationProvidedRoleFromTypeReference(final Classifier classifierInterface, final org.emftext.language.java.classifiers.Class javaClass, final TypeReference reference) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateOperationProvidedRoleFromTypeReferenceRoutine routine = new mir.routines.java2PcmClassifier.CreateOperationProvidedRoleFromTypeReferenceRoutine(_routinesFacade, _reactionExecutionState, _caller, classifierInterface, javaClass, reference);
    return routine.applyRoutine();
  }
  
  public boolean createJavaSubPackages(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateJavaSubPackagesRoutine routine = new mir.routines.java2PcmClassifier.CreateJavaSubPackagesRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage);
    return routine.applyRoutine();
  }
  
  public boolean createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmClassifier.CreateJavaPackageRoutine routine = new mir.routines.java2PcmClassifier.CreateJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, sourceElementMappedToPackage, parentPackage, packageName, newTag);
    return routine.applyRoutine();
  }
  
  public boolean renameNamedElement(final NamedElement javaElement) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.RenameNamedElementRoutine routine = new mir.routines.java2PcmMethod.RenameNamedElementRoutine(_routinesFacade, _reactionExecutionState, _caller, javaElement);
    return routine.applyRoutine();
  }
  
  public boolean createParameter(final OrdinaryParameter javaParameter, final Parametrizable javaMethod) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateParameterRoutine routine = new mir.routines.java2PcmMethod.CreateParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, javaParameter, javaMethod);
    return routine.applyRoutine();
  }
  
  public boolean deleteParameter(final OrdinaryParameter javaParameter) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.DeleteParameterRoutine routine = new mir.routines.java2PcmMethod.DeleteParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, javaParameter);
    return routine.applyRoutine();
  }
  
  public boolean changeParameterName(final String newName, final Parameter javaParameter) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.ChangeParameterNameRoutine routine = new mir.routines.java2PcmMethod.ChangeParameterNameRoutine(_routinesFacade, _reactionExecutionState, _caller, newName, javaParameter);
    return routine.applyRoutine();
  }
  
  public boolean createInnerDeclaration(final ConcreteClassifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateInnerDeclarationRoutine routine = new mir.routines.java2PcmMethod.CreateInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean createAssemblyContext(final ConcreteClassifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateAssemblyContextRoutine routine = new mir.routines.java2PcmMethod.CreateAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean fieldCreatedCorrespondingToOperationInterface(final Classifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.FieldCreatedCorrespondingToOperationInterfaceRoutine routine = new mir.routines.java2PcmMethod.FieldCreatedCorrespondingToOperationInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean fieldCreatedCorrespondingToRepositoryComponent(final Classifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.FieldCreatedCorrespondingToRepositoryComponentRoutine routine = new mir.routines.java2PcmMethod.FieldCreatedCorrespondingToRepositoryComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, classifier, javaField);
    return routine.applyRoutine();
  }
  
  public boolean createOperationRequiredRoleCorrespondingToField(final Field javaField, final OperationInterface operationInterface, final RepositoryComponent repoComponent) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateOperationRequiredRoleCorrespondingToFieldRoutine routine = new mir.routines.java2PcmMethod.CreateOperationRequiredRoleCorrespondingToFieldRoutine(_routinesFacade, _reactionExecutionState, _caller, javaField, operationInterface, repoComponent);
    return routine.applyRoutine();
  }
  
  public boolean changeInnerDeclarationType(final TypeReference typeReference, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.ChangeInnerDeclarationTypeRoutine routine = new mir.routines.java2PcmMethod.ChangeInnerDeclarationTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, typeReference, javaField);
    return routine.applyRoutine();
  }
  
  public boolean createSeffFromImplementingInterfaces(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfacesRoutine routine = new mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfacesRoutine(_routinesFacade, _reactionExecutionState, _caller, classMethod, javaClass);
    return routine.applyRoutine();
  }
  
  public boolean createSeffFromImplementingInterface(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass, final Interface javaInterface) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfaceRoutine routine = new mir.routines.java2PcmMethod.CreateSeffFromImplementingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, classMethod, javaClass, javaInterface);
    return routine.applyRoutine();
  }
  
  public boolean createSEFF(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreateSEFFRoutine routine = new mir.routines.java2PcmMethod.CreateSEFFRoutine(_routinesFacade, _reactionExecutionState, _caller, javaMethod, javaClass, classMethod);
    return routine.applyRoutine();
  }
  
  public boolean createPCMSignature(final InterfaceMethod interfaceMethod) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.CreatePCMSignatureRoutine routine = new mir.routines.java2PcmMethod.CreatePCMSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, interfaceMethod);
    return routine.applyRoutine();
  }
  
  public boolean changeReturnType(final Method javaMethod, final TypeReference typeReference) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("java2Pcm.Java2PcmMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.java2PcmMethod.ChangeReturnTypeRoutine routine = new mir.routines.java2PcmMethod.ChangeReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, javaMethod, typeReference);
    return routine.applyRoutine();
  }
}
