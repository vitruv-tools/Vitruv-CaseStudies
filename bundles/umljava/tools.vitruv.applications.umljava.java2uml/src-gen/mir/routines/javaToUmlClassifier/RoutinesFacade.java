package mir.routines.javaToUmlClassifier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PackageableElement;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.EnumConstant;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createUmlClass(final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.CreateUmlClassRoutine routine = new mir.routines.javaToUmlClassifier.CreateUmlClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean addUmlElementToPackage(final PackageableElement uPackageable, final org.eclipse.uml2.uml.Package uPackage, final EObject persistedObject) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlElementToPackageRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlElementToPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackageable, uPackage, persistedObject);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlClassifier(final ConcreteClassifier jClassifier, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.DeleteUmlClassifierRoutine routine = new mir.routines.javaToUmlClassifier.DeleteUmlClassifierRoutine(_routinesFacade, _reactionExecutionState, _caller, jClassifier, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean setUmlClassAbstract(final org.emftext.language.java.classifiers.Class jClass, final Boolean isAbstract) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.SetUmlClassAbstractRoutine routine = new mir.routines.javaToUmlClassifier.SetUmlClassAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, isAbstract);
    return routine.applyRoutine();
  }
  
  public boolean setUmlClassFinal(final org.emftext.language.java.classifiers.Class jClass, final Boolean isFinal) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.SetUmlClassFinalRoutine routine = new mir.routines.javaToUmlClassifier.SetUmlClassFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, isFinal);
    return routine.applyRoutine();
  }
  
  public boolean addUmlSuperClass(final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class jSuperClass) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlSuperClassRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlSuperClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jSuperClass);
    return routine.applyRoutine();
  }
  
  public boolean clearUmlSuperClassifiers(final org.emftext.language.java.classifiers.Class jClass) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.ClearUmlSuperClassifiersRoutine routine = new mir.routines.javaToUmlClassifier.ClearUmlSuperClassifiersRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass);
    return routine.applyRoutine();
  }
  
  public boolean addUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final Classifier jInterface) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlClassImplementRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlClassImplementRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jInterface);
    return routine.applyRoutine();
  }
  
  public boolean removeUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final Interface jInterface) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.RemoveUmlClassImplementRoutine routine = new mir.routines.javaToUmlClassifier.RemoveUmlClassImplementRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jInterface);
    return routine.applyRoutine();
  }
  
  public boolean createUmlInterface(final Interface jInterface, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.CreateUmlInterfaceRoutine routine = new mir.routines.javaToUmlClassifier.CreateUmlInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, jInterface, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean addUmlSuperinterfaces(final Interface jInterface, final Classifier jSuperInterface) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlSuperinterfacesRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlSuperinterfacesRoutine(_routinesFacade, _reactionExecutionState, _caller, jInterface, jSuperInterface);
    return routine.applyRoutine();
  }
  
  public boolean removeUmlSuperInterface(final Interface jInterface, final Classifier jSuperClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.RemoveUmlSuperInterfaceRoutine routine = new mir.routines.javaToUmlClassifier.RemoveUmlSuperInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, jInterface, jSuperClassifier);
    return routine.applyRoutine();
  }
  
  public boolean createUmlPackage(final org.emftext.language.java.containers.Package jPackage) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.CreateUmlPackageRoutine routine = new mir.routines.javaToUmlClassifier.CreateUmlPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlPackage(final org.emftext.language.java.containers.Package jPackage) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.DeleteUmlPackageRoutine routine = new mir.routines.javaToUmlClassifier.DeleteUmlPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage);
    return routine.applyRoutine();
  }
  
  public boolean addUmlPackageOfClass(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlPackageOfClassRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlPackageOfClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean removeUmlPackageOfClass(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.RemoveUmlPackageOfClassRoutine routine = new mir.routines.javaToUmlClassifier.RemoveUmlPackageOfClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean addUmlElementToModelOrPackage(final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Classifier uClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlElementToModelOrPackageRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlElementToModelOrPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, jCompUnit, uClassifier);
    return routine.applyRoutine();
  }
  
  public boolean createUmlEnum(final Enumeration jEnum, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.CreateUmlEnumRoutine routine = new mir.routines.javaToUmlClassifier.CreateUmlEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, jEnum, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean createUmlEnumLiteral(final Enumeration jEnum, final EnumConstant jConstant) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.CreateUmlEnumLiteralRoutine routine = new mir.routines.javaToUmlClassifier.CreateUmlEnumLiteralRoutine(_routinesFacade, _reactionExecutionState, _caller, jEnum, jConstant);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlEnumLiteral(final EnumConstant jConstant) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.DeleteUmlEnumLiteralRoutine routine = new mir.routines.javaToUmlClassifier.DeleteUmlEnumLiteralRoutine(_routinesFacade, _reactionExecutionState, _caller, jConstant);
    return routine.applyRoutine();
  }
}
