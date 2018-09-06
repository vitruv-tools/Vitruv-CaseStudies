package mir.routines.umlToJavaClassifier;

import mir.routines.umlToJavaClassifier.AddGeneralizationCorrespondenceRoutine;
import mir.routines.umlToJavaClassifier.AddImplementsCorrespondenceRoutine;
import mir.routines.umlToJavaClassifier.AddJavaSuperClassRoutine;
import mir.routines.umlToJavaClassifier.AddJavaSuperInterfaceRoutine;
import mir.routines.umlToJavaClassifier.AddUmlModelCorrespondenceRoutine;
import mir.routines.umlToJavaClassifier.ChangePackageOfJavaCompilationUnitRoutine;
import mir.routines.umlToJavaClassifier.CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine;
import mir.routines.umlToJavaClassifier.CheckIfUmlModelCorrespondenceExistsRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaClassImplementsReferenceRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaClassRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaCompilationUnitRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaEnumConstantRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaEnumRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaInterfaceRoutine;
import mir.routines.umlToJavaClassifier.CreateJavaPackageRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaClassImplementsReferenceRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaClassRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaEnumConstantRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaPackageRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaSuperClassRoutine;
import mir.routines.umlToJavaClassifier.DeleteJavaSuperInterfaceRoutine;
import mir.routines.umlToJavaClassifier.RenameJavaClassifierRoutine;
import mir.routines.umlToJavaClassifier.RenameJavaPackageRoutine;
import mir.routines.umlToJavaClassifier.ReplaceJavaClassImplementsReferenceRoutine;
import mir.routines.umlToJavaClassifier.SetJavaClassAbstractRoutine;
import mir.routines.umlToJavaClassifier.SetJavaClassFinalRoutine;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.PrimitiveType;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.types.TypeReference;
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
  
  public boolean addJavaSuperClass(final org.eclipse.uml2.uml.Class uClass, final Generalization uGeneralization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddJavaSuperClassRoutine routine = new AddJavaSuperClassRoutine(_routinesFacade, _reactionExecutionState, _caller, uClass, uGeneralization);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaSuperClass(final Generalization uGeneralization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaSuperClassRoutine routine = new DeleteJavaSuperClassRoutine(_routinesFacade, _reactionExecutionState, _caller, uGeneralization);
    return routine.applyRoutine();
  }
  
  public boolean createJavaClassImplementsReference(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaClassImplementsReferenceRoutine routine = new CreateJavaClassImplementsReferenceRoutine(_routinesFacade, _reactionExecutionState, _caller, uRealization, uClass);
    return routine.applyRoutine();
  }
  
  public boolean addImplementsCorrespondence(final InterfaceRealization uRealization, final TypeReference jReference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddImplementsCorrespondenceRoutine routine = new AddImplementsCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, uRealization, jReference);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaClassImplementsReference(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaClassImplementsReferenceRoutine routine = new DeleteJavaClassImplementsReferenceRoutine(_routinesFacade, _reactionExecutionState, _caller, uRealization, uClass);
    return routine.applyRoutine();
  }
  
  public boolean replaceJavaClassImplementsReference(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ReplaceJavaClassImplementsReferenceRoutine routine = new ReplaceJavaClassImplementsReferenceRoutine(_routinesFacade, _reactionExecutionState, _caller, uRealization, uClass);
    return routine.applyRoutine();
  }
  
  public boolean createJavaInterface(final Interface umlInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaInterfaceRoutine routine = new CreateJavaInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean addJavaSuperInterface(final Interface uInterface, final Generalization uGeneralization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddJavaSuperInterfaceRoutine routine = new AddJavaSuperInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, uInterface, uGeneralization);
    return routine.applyRoutine();
  }
  
  public boolean addGeneralizationCorrespondence(final Generalization uGeneralization, final TypeReference jReference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddGeneralizationCorrespondenceRoutine routine = new AddGeneralizationCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, uGeneralization, jReference);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaSuperInterface(final Generalization uGeneralization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaSuperInterfaceRoutine routine = new DeleteJavaSuperInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, uGeneralization);
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
  
  public boolean checkIfUmlModelCorrespondenceExists(final Model newModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CheckIfUmlModelCorrespondenceExistsRoutine routine = new CheckIfUmlModelCorrespondenceExistsRoutine(_routinesFacade, _reactionExecutionState, _caller, newModel);
    return routine.applyRoutine();
  }
  
  public boolean addUmlModelCorrespondence(final Model newModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddUmlModelCorrespondenceRoutine routine = new AddUmlModelCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, newModel);
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
