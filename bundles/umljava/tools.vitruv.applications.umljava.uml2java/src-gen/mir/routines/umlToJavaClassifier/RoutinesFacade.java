package mir.routines.umlToJavaClassifier;

import mir.routines.umlToJavaClassifier.ChangeJavaImplementedInterfaceRoutine;
import mir.routines.umlToJavaClassifier.ChangeJavaSuperClassRoutine;
import mir.routines.umlToJavaClassifier.ChangeJavaSuperInterfaceRoutine;
import mir.routines.umlToJavaClassifier.ChangePackageOfJavaCompilationUnitRoutine;
import mir.routines.umlToJavaClassifier.CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaClassRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaCompilationUnitRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaEnumConstantRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaEnumRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaInterfaceRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaPackageRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaClassRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaEnumConstantRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaImplementedInterfaceRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaPackageRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaSuperClassRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaSuperInterfaceRoutine;
import mir.routines.umlToJavaClassifier.RenameJavaClassifierRoutine;
import mir.routines.umlToJavaClassifier.RenameJavaPackageRoutine;
import mir.routines.umlToJavaClassifier.SetJavaClassAbstractRoutine;
import mir.routines.umlToJavaClassifier.SetJavaClassFinalRoutine;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.PrimitiveType;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
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
  
  public boolean createJavaClass(final Classifier umlClassifier) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaClassRoutine routine = new CreateJavaClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifier);
    return routine.applyRoutine();
  }
  
  public boolean createJavaCompilationUnit(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaCompilationUnitRoutine routine = new CreateJavaCompilationUnitRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifier, jClassifier, uNamespace);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaClassifier(final Classifier umlClassifier) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameJavaClassifierRoutine routine = new RenameJavaClassifierRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifier);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaClass(final Classifier umlClassifer) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaClassRoutine routine = new DeleteJavaClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifer);
    return routine.applyRoutine();
  }
  
  public boolean setJavaClassFinal(final org.eclipse.uml2.uml.Class umlClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetJavaClassFinalRoutine routine = new SetJavaClassFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean setJavaClassAbstract(final org.eclipse.uml2.uml.Class umlClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetJavaClassAbstractRoutine routine = new SetJavaClassAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaSuperClass(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeJavaSuperClassRoutine routine = new ChangeJavaSuperClassRoutine(_routinesFacade, _reactionExecutionState, _caller, superUMLClass, uClass);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaSuperClass(final org.eclipse.uml2.uml.Class uClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaSuperClassRoutine routine = new DeleteJavaSuperClassRoutine(_routinesFacade, _reactionExecutionState, _caller, uClass);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaImplementedInterface(final Interface uInterface, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeJavaImplementedInterfaceRoutine routine = new ChangeJavaImplementedInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, uInterface, oldInterface, uClass);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaImplementedInterface(final Interface uInterface, final org.eclipse.uml2.uml.Class uClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaImplementedInterfaceRoutine routine = new DeleteJavaImplementedInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, uInterface, uClass);
    return routine.applyRoutine();
  }
  
  public boolean createJavaInterface(final Interface umlInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaInterfaceRoutine routine = new CreateJavaInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeJavaSuperInterfaceRoutine routine = new ChangeJavaSuperInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, superUMLInterface, uI);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaSuperInterfaceRoutine routine = new DeleteJavaSuperInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, superUMLInterface, uI);
    return routine.applyRoutine();
  }
  
  public boolean createJavaEnum(final Enumeration uEnum) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaEnumRoutine routine = new CreateJavaEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, uEnum);
    return routine.applyRoutine();
  }
  
  public boolean createJavaEnumConstant(final EnumerationLiteral uLiteral, final Enumeration uEnum) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaEnumConstantRoutine routine = new CreateJavaEnumConstantRoutine(_routinesFacade, _reactionExecutionState, _caller, uLiteral, uEnum);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaEnumConstant(final EnumerationLiteral uLiteral) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaEnumConstantRoutine routine = new DeleteJavaEnumConstantRoutine(_routinesFacade, _reactionExecutionState, _caller, uLiteral);
    return routine.applyRoutine();
  }
  
  public boolean createJavaPackage(final org.eclipse.uml2.uml.Package uPackage, final org.eclipse.uml2.uml.Package uSuperPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaPackageRoutine routine = new CreateJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackage, uSuperPackage);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaPackage(final org.eclipse.uml2.uml.Package uPackage, final Namespace uNamespace) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameJavaPackageRoutine routine = new RenameJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackage, uNamespace);
    return routine.applyRoutine();
  }
  
  public boolean changePackageOfJavaCompilationUnit(final org.emftext.language.java.containers.Package jPackage, final CompilationUnit jCompUnit, final Namespace uNamespace) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangePackageOfJavaCompilationUnitRoutine routine = new ChangePackageOfJavaCompilationUnitRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage, jCompUnit, uNamespace);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaPackage(final org.eclipse.uml2.uml.Package uPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaPackageRoutine routine = new DeleteJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackage);
    return routine.applyRoutine();
  }
  
  public boolean checkIfCorrespondingJavaPrimitiveTypeExists(final PrimitiveType uPrimType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine routine = new CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine(_routinesFacade, _reactionExecutionState, _caller, uPrimType);
    return routine.applyRoutine();
  }
}
