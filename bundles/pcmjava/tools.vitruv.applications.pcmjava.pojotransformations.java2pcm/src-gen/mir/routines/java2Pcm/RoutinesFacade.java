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
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createArchitecturalElement(javaPackage, name, rootPackageName);
  }
  
  public boolean createRepository(final org.emftext.language.java.containers.Package javaPackage, final String packageName, final String newTag) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createRepository(javaPackage, packageName, newTag);
  }
  
  public boolean createSystem(final org.emftext.language.java.containers.Package javaPackage, final String name) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createSystem(javaPackage, name);
  }
  
  public boolean createBasicComponent(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createBasicComponent(javaPackage, name, rootPackageName);
  }
  
  public boolean createCompositeComponent(final org.emftext.language.java.containers.Package javaPackage, final String name, final String rootPackageName) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createCompositeComponent(javaPackage, name, rootPackageName);
  }
  
  public boolean addcorrespondenceAndUpdateRepository(final ImplementationComponentType pcmComponent, final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.addcorrespondenceAndUpdateRepository(pcmComponent, javaPackage);
  }
  
  public boolean createPCMInterface(final Interface javaInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createPCMInterface(javaInterface, compilationUnit);
  }
  
  public boolean createdInterfaceNotInContracts(final Interface javaInterface, final OperationInterface pcmInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createdInterfaceNotInContracts(javaInterface, pcmInterface, compilationUnit);
  }
  
  public boolean addcorrespondenceToInterfaceAndUpdateRepository(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.addcorrespondenceToInterfaceAndUpdateRepository(pcmInterface, javaInterface, compilationUnit);
  }
  
  public boolean classMapping(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit, final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.classMapping(javaClass, compilationUnit, javaPackage);
  }
  
  public boolean createDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createDataType(javaClass, compilationUnit);
  }
  
  public boolean createElement(final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.containers.Package javaPackage, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createElement(javaClass, javaPackage, compilationUnit);
  }
  
  public boolean checkSystemAndComponent(final org.emftext.language.java.containers.Package javaPackage, final org.emftext.language.java.classifiers.Class javaClass) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.checkSystemAndComponent(javaPackage, javaClass);
  }
  
  public boolean createCompositeDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createCompositeDataType(javaClass, compilationUnit);
  }
  
  public boolean createCollectionDataType(final org.emftext.language.java.classifiers.Class javaClass, final CompilationUnit compilationUnit) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createCollectionDataType(javaClass, compilationUnit);
  }
  
  public boolean addDataTypeInRepository(final DataType pcmDataType) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.addDataTypeInRepository(pcmDataType);
  }
  
  public boolean createOperationProvidedRole(final TypeReference typeReference) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createOperationProvidedRole(typeReference);
  }
  
  public boolean createOperationProvidedRoleFromTypeReference(final Classifier classifierInterface, final org.emftext.language.java.classifiers.Class javaClass, final TypeReference reference) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createOperationProvidedRoleFromTypeReference(classifierInterface, javaClass, reference);
  }
  
  public boolean createJavaSubPackages(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createJavaSubPackages(javaPackage);
  }
  
  public boolean createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.java2PcmClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmClassifier")));
    return _routinesFacade.createJavaPackage(sourceElementMappedToPackage, parentPackage, packageName, newTag);
  }
  
  public boolean renameNamedElement(final NamedElement javaElement) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.renameNamedElement(javaElement);
  }
  
  public boolean createParameter(final OrdinaryParameter javaParameter, final Parametrizable javaMethod) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.createParameter(javaParameter, javaMethod);
  }
  
  public boolean deleteParameter(final OrdinaryParameter javaParameter) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.deleteParameter(javaParameter);
  }
  
  public boolean changeParameterName(final String newName, final Parameter javaParameter) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.changeParameterName(newName, javaParameter);
  }
  
  public boolean createInnerDeclaration(final ConcreteClassifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.createInnerDeclaration(classifier, javaField);
  }
  
  public boolean createAssemblyContext(final ConcreteClassifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.createAssemblyContext(classifier, javaField);
  }
  
  public boolean fieldCreatedCorrespondingToOperationInterface(final Classifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.fieldCreatedCorrespondingToOperationInterface(classifier, javaField);
  }
  
  public boolean fieldCreatedCorrespondingToRepositoryComponent(final Classifier classifier, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.fieldCreatedCorrespondingToRepositoryComponent(classifier, javaField);
  }
  
  public boolean createOperationRequiredRoleCorrespondingToField(final Field javaField, final OperationInterface operationInterface, final RepositoryComponent repoComponent) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.createOperationRequiredRoleCorrespondingToField(javaField, operationInterface, repoComponent);
  }
  
  public boolean changeInnerDeclarationType(final TypeReference typeReference, final Field javaField) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.changeInnerDeclarationType(typeReference, javaField);
  }
  
  public boolean createSeffFromImplementingInterfaces(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.createSeffFromImplementingInterfaces(classMethod, javaClass);
  }
  
  public boolean createSeffFromImplementingInterface(final ClassMethod classMethod, final org.emftext.language.java.classifiers.Class javaClass, final Interface javaInterface) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.createSeffFromImplementingInterface(classMethod, javaClass, javaInterface);
  }
  
  public boolean createSEFF(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.createSEFF(javaMethod, javaClass, classMethod);
  }
  
  public boolean createPCMSignature(final InterfaceMethod interfaceMethod) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.createPCMSignature(interfaceMethod);
  }
  
  public boolean changeReturnType(final Method javaMethod, final TypeReference typeReference) {
    mir.routines.java2PcmMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("java2PcmMethod")));
    return _routinesFacade.changeReturnType(javaMethod, typeReference);
  }
}
