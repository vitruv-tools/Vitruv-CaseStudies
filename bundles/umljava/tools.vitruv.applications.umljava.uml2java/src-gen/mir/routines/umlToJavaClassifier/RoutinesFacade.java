package mir.routines.umlToJavaClassifier;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.PrimitiveType;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createJavaClass(final Classifier umlClassifier) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaClassRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifier);
    return routine.applyRoutine();
  }
  
  public boolean createJavaCompilationUnit(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaCompilationUnitRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaCompilationUnitRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifier, jClassifier, uNamespace);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaClassifier(final Classifier umlClassifier) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.RenameJavaClassifierRoutine routine = new mir.routines.umlToJavaClassifier.RenameJavaClassifierRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifier);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaClass(final Classifier umlClassifer) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaClassRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifer);
    return routine.applyRoutine();
  }
  
  public boolean setJavaClassFinal(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.SetJavaClassFinalRoutine routine = new mir.routines.umlToJavaClassifier.SetJavaClassFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean setJavaClassAbstract(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.SetJavaClassAbstractRoutine routine = new mir.routines.umlToJavaClassifier.SetJavaClassAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaSuperClass(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.ChangeJavaSuperClassRoutine routine = new mir.routines.umlToJavaClassifier.ChangeJavaSuperClassRoutine(_routinesFacade, _reactionExecutionState, _caller, superUMLClass, uClass);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaSuperClass(final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaSuperClassRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaSuperClassRoutine(_routinesFacade, _reactionExecutionState, _caller, uClass);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaImplementedInterface(final Interface uInterface, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.ChangeJavaImplementedInterfaceRoutine routine = new mir.routines.umlToJavaClassifier.ChangeJavaImplementedInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, uInterface, oldInterface, uClass);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaImplementedInterface(final Interface uInterface, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaImplementedInterfaceRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaImplementedInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, uInterface, uClass);
    return routine.applyRoutine();
  }
  
  public boolean createJavaInterface(final Interface umlInterface) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaInterfaceRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.ChangeJavaSuperInterfaceRoutine routine = new mir.routines.umlToJavaClassifier.ChangeJavaSuperInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, superUMLInterface, uI);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaSuperInterfaceRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaSuperInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, superUMLInterface, uI);
    return routine.applyRoutine();
  }
  
  public boolean createJavaEnum(final Enumeration uEnum) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaEnumRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, uEnum);
    return routine.applyRoutine();
  }
  
  public boolean createJavaEnumConstant(final EnumerationLiteral uLiteral, final Enumeration uEnum) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaEnumConstantRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaEnumConstantRoutine(_routinesFacade, _reactionExecutionState, _caller, uLiteral, uEnum);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaEnumConstant(final EnumerationLiteral uLiteral) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaEnumConstantRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaEnumConstantRoutine(_routinesFacade, _reactionExecutionState, _caller, uLiteral);
    return routine.applyRoutine();
  }
  
  public boolean createJavaPackage(final org.eclipse.uml2.uml.Package uPackage, final org.eclipse.uml2.uml.Package uSuperPackage) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaPackageRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackage, uSuperPackage);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaPackage(final org.eclipse.uml2.uml.Package uPackage, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.RenameJavaPackageRoutine routine = new mir.routines.umlToJavaClassifier.RenameJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackage, uNamespace);
    return routine.applyRoutine();
  }
  
  public boolean changePackageOfJavaCompilationUnit(final org.emftext.language.java.containers.Package jPackage, final CompilationUnit jCompUnit, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.ChangePackageOfJavaCompilationUnitRoutine routine = new mir.routines.umlToJavaClassifier.ChangePackageOfJavaCompilationUnitRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage, jCompUnit, uNamespace);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaPackage(final org.eclipse.uml2.uml.Package uPackage) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaPackageRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackage);
    return routine.applyRoutine();
  }
  
  public boolean checkIfCorrespondingJavaPrimitiveTypeExists(final PrimitiveType uPrimType) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine routine = new mir.routines.umlToJavaClassifier.CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine(_routinesFacade, _reactionExecutionState, _caller, uPrimType);
    return routine.applyRoutine();
  }
}
