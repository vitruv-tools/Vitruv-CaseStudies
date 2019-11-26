package mir.routines.javaToUmlClassifier;

import mir.routines.javaToUmlClassifier.AddGeneralizationCorrespondenceRoutine;
import mir.routines.javaToUmlClassifier.AddImplementsCorrespondenceRoutine;
import mir.routines.javaToUmlClassifier.AddPackageCorrespondenceRoutine;
import mir.routines.javaToUmlClassifier.AddUmlClassImplementRoutine;
import mir.routines.javaToUmlClassifier.AddUmlElementToModelOrPackageRoutine;
import mir.routines.javaToUmlClassifier.AddUmlElementToPackageRoutine;
import mir.routines.javaToUmlClassifier.AddUmlPackageOfClassRoutine;
import mir.routines.javaToUmlClassifier.AddUmlSuperClassGeneralizationRoutine;
import mir.routines.javaToUmlClassifier.AddUmlSuperinterfacesRoutine;
import mir.routines.javaToUmlClassifier.CreateOrFindUmlPackageRoutine;
import mir.routines.javaToUmlClassifier.CreateUmlClassRoutine;
import mir.routines.javaToUmlClassifier.CreateUmlEnumLiteralRoutine;
import mir.routines.javaToUmlClassifier.CreateUmlEnumRoutine;
import mir.routines.javaToUmlClassifier.CreateUmlInterfaceRoutine;
import mir.routines.javaToUmlClassifier.CreateUmlPackageRoutine;
import mir.routines.javaToUmlClassifier.DeleteUmlClassifierRoutine;
import mir.routines.javaToUmlClassifier.DeleteUmlEnumLiteralRoutine;
import mir.routines.javaToUmlClassifier.DeleteUmlPackageRoutine;
import mir.routines.javaToUmlClassifier.DeleteUmlSuperClassGeneralizationRoutine;
import mir.routines.javaToUmlClassifier.DetectOrCreateUmlModelRoutine;
import mir.routines.javaToUmlClassifier.PotentiallyMoveClassRoutine;
import mir.routines.javaToUmlClassifier.RegisterUmlModelInCorrespondenceModelRoutine;
import mir.routines.javaToUmlClassifier.RemoveUmlClassImplementRoutine;
import mir.routines.javaToUmlClassifier.RemoveUmlPackageOfClassRoutine;
import mir.routines.javaToUmlClassifier.RemoveUmlSuperInterfaceRoutine;
import mir.routines.javaToUmlClassifier.SetUmlClassAbstractRoutine;
import mir.routines.javaToUmlClassifier.SetUmlClassFinalRoutine;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.EnumConstant;
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
  
  public boolean detectOrCreateUmlModel(final EObject alreadyPersistedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateUmlModelRoutine routine = new DetectOrCreateUmlModelRoutine(_routinesFacade, _reactionExecutionState, _caller, alreadyPersistedEObject);
    return routine.applyRoutine();
  }
  
  public boolean registerUmlModelInCorrespondenceModel(final Model uModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RegisterUmlModelInCorrespondenceModelRoutine routine = new RegisterUmlModelInCorrespondenceModelRoutine(_routinesFacade, _reactionExecutionState, _caller, uModel);
    return routine.applyRoutine();
  }
  
  public boolean createUmlClass(final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlClassRoutine routine = new CreateUmlClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean addUmlElementToPackage(final PackageableElement uPackageable, final org.eclipse.uml2.uml.Package uPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddUmlElementToPackageRoutine routine = new AddUmlElementToPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackageable, uPackage);
    return routine.applyRoutine();
  }
  
  public boolean potentiallyMoveClass(final CompilationUnit compUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    PotentiallyMoveClassRoutine routine = new PotentiallyMoveClassRoutine(_routinesFacade, _reactionExecutionState, _caller, compUnit);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlClassifier(final ConcreteClassifier jClassifier, final CompilationUnit jCompUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteUmlClassifierRoutine routine = new DeleteUmlClassifierRoutine(_routinesFacade, _reactionExecutionState, _caller, jClassifier, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean setUmlClassAbstract(final org.emftext.language.java.classifiers.Class jClass, final Boolean isAbstract) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetUmlClassAbstractRoutine routine = new SetUmlClassAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, isAbstract);
    return routine.applyRoutine();
  }
  
  public boolean setUmlClassFinal(final org.emftext.language.java.classifiers.Class jClass, final Boolean isFinal) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetUmlClassFinalRoutine routine = new SetUmlClassFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, isFinal);
    return routine.applyRoutine();
  }
  
  public boolean addUmlSuperClassGeneralization(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final org.emftext.language.java.classifiers.Class jSuperClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddUmlSuperClassGeneralizationRoutine routine = new AddUmlSuperClassGeneralizationRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jReference, jSuperClass);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlSuperClassGeneralization(final TypeReference jReference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteUmlSuperClassGeneralizationRoutine routine = new DeleteUmlSuperClassGeneralizationRoutine(_routinesFacade, _reactionExecutionState, _caller, jReference);
    return routine.applyRoutine();
  }
  
  public boolean addUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference, final Interface jInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddUmlClassImplementRoutine routine = new AddUmlClassImplementRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jReference, jInterface);
    return routine.applyRoutine();
  }
  
  public boolean addImplementsCorrespondence(final TypeReference jReference, final InterfaceRealization uRealization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddImplementsCorrespondenceRoutine routine = new AddImplementsCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, jReference, uRealization);
    return routine.applyRoutine();
  }
  
  public boolean removeUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final TypeReference jReference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveUmlClassImplementRoutine routine = new RemoveUmlClassImplementRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jReference);
    return routine.applyRoutine();
  }
  
  public boolean createUmlInterface(final Interface jInterface, final CompilationUnit jCompUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlInterfaceRoutine routine = new CreateUmlInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, jInterface, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean addUmlSuperinterfaces(final Interface jInterface, final TypeReference jReference, final Interface jSuperInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddUmlSuperinterfacesRoutine routine = new AddUmlSuperinterfacesRoutine(_routinesFacade, _reactionExecutionState, _caller, jInterface, jReference, jSuperInterface);
    return routine.applyRoutine();
  }
  
  public boolean addGeneralizationCorrespondence(final Generalization uGeneralization, final TypeReference jReference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddGeneralizationCorrespondenceRoutine routine = new AddGeneralizationCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, uGeneralization, jReference);
    return routine.applyRoutine();
  }
  
  public boolean removeUmlSuperInterface(final Interface jInterface, final TypeReference jReference) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveUmlSuperInterfaceRoutine routine = new RemoveUmlSuperInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, jInterface, jReference);
    return routine.applyRoutine();
  }
  
  public boolean createOrFindUmlPackage(final org.emftext.language.java.containers.Package jPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateOrFindUmlPackageRoutine routine = new CreateOrFindUmlPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage);
    return routine.applyRoutine();
  }
  
  public boolean addPackageCorrespondence(final org.emftext.language.java.containers.Package jPackage, final org.eclipse.uml2.uml.Package uPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddPackageCorrespondenceRoutine routine = new AddPackageCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage, uPackage);
    return routine.applyRoutine();
  }
  
  public boolean createUmlPackage(final org.emftext.language.java.containers.Package jPackage, final Model uModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlPackageRoutine routine = new CreateUmlPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage, uModel);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlPackage(final org.emftext.language.java.containers.Package jPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteUmlPackageRoutine routine = new DeleteUmlPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage);
    return routine.applyRoutine();
  }
  
  public boolean addUmlPackageOfClass(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddUmlPackageOfClassRoutine routine = new AddUmlPackageOfClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean removeUmlPackageOfClass(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveUmlPackageOfClassRoutine routine = new RemoveUmlPackageOfClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean addUmlElementToModelOrPackage(final CompilationUnit jCompUnit, final Classifier uClassifier) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddUmlElementToModelOrPackageRoutine routine = new AddUmlElementToModelOrPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, jCompUnit, uClassifier);
    return routine.applyRoutine();
  }
  
  public boolean createUmlEnum(final Enumeration jEnum, final CompilationUnit jCompUnit) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlEnumRoutine routine = new CreateUmlEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, jEnum, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean createUmlEnumLiteral(final Enumeration jEnum, final EnumConstant jConstant) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlEnumLiteralRoutine routine = new CreateUmlEnumLiteralRoutine(_routinesFacade, _reactionExecutionState, _caller, jEnum, jConstant);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlEnumLiteral(final EnumConstant jConstant) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteUmlEnumLiteralRoutine routine = new DeleteUmlEnumLiteralRoutine(_routinesFacade, _reactionExecutionState, _caller, jConstant);
    return routine.applyRoutine();
  }
}
