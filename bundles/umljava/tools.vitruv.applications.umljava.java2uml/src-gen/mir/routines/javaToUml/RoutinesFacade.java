package mir.routines.javaToUml;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.EnumConstant;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parametrizable;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createUmlAttributeInEnum(final Enumeration jEnum, final Field jAttr) {
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlAttribute.CreateUmlAttributeInEnumRoutine routine = new mir.routines.javaToUmlAttribute.CreateUmlAttributeInEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, jEnum, jAttr);
    return routine.applyRoutine();
  }
  
  public boolean createUmlAttributeInClass(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlAttribute.CreateUmlAttributeInClassRoutine routine = new mir.routines.javaToUmlAttribute.CreateUmlAttributeInClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jAttr);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlAttributeType(final Field jAttr, final TypeReference jType) {
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlAttribute.ChangeUmlAttributeTypeRoutine routine = new mir.routines.javaToUmlAttribute.ChangeUmlAttributeTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttr, jType);
    return routine.applyRoutine();
  }
  
  public boolean setUmlAttributeFinal(final Field jAttr, final Boolean isFinal) {
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlAttribute.SetUmlAttributeFinalRoutine routine = new mir.routines.javaToUmlAttribute.SetUmlAttributeFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttr, isFinal);
    return routine.applyRoutine();
  }
  
  public boolean createUmlClass(final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.CreateUmlClassRoutine routine = new mir.routines.javaToUmlClassifier.CreateUmlClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean addUmlElementToPackage(final PackageableElement uPackageable, final org.eclipse.uml2.uml.Package uPackage, final EObject persistedObject) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlElementToPackageRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlElementToPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackageable, uPackage, persistedObject);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlClassifier(final ConcreteClassifier jClassifier, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.DeleteUmlClassifierRoutine routine = new mir.routines.javaToUmlClassifier.DeleteUmlClassifierRoutine(_routinesFacade, _reactionExecutionState, _caller, jClassifier, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean setUmlClassAbstract(final org.emftext.language.java.classifiers.Class jClass, final Boolean isAbstract) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.SetUmlClassAbstractRoutine routine = new mir.routines.javaToUmlClassifier.SetUmlClassAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, isAbstract);
    return routine.applyRoutine();
  }
  
  public boolean setUmlClassFinal(final org.emftext.language.java.classifiers.Class jClass, final Boolean isFinal) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.SetUmlClassFinalRoutine routine = new mir.routines.javaToUmlClassifier.SetUmlClassFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, isFinal);
    return routine.applyRoutine();
  }
  
  public boolean addUmlSuperClass(final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class jSuperClass) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlSuperClassRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlSuperClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jSuperClass);
    return routine.applyRoutine();
  }
  
  public boolean clearUmlSuperClassifiers(final org.emftext.language.java.classifiers.Class jClass) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.ClearUmlSuperClassifiersRoutine routine = new mir.routines.javaToUmlClassifier.ClearUmlSuperClassifiersRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass);
    return routine.applyRoutine();
  }
  
  public boolean addUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final Classifier jInterface) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlClassImplementRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlClassImplementRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jInterface);
    return routine.applyRoutine();
  }
  
  public boolean removeUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final Interface jInterface) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.RemoveUmlClassImplementRoutine routine = new mir.routines.javaToUmlClassifier.RemoveUmlClassImplementRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jInterface);
    return routine.applyRoutine();
  }
  
  public boolean createUmlInterface(final Interface jInterface, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.CreateUmlInterfaceRoutine routine = new mir.routines.javaToUmlClassifier.CreateUmlInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, jInterface, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean addUmlSuperinterfaces(final Interface jInterface, final Classifier jSuperInterface) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlSuperinterfacesRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlSuperinterfacesRoutine(_routinesFacade, _reactionExecutionState, _caller, jInterface, jSuperInterface);
    return routine.applyRoutine();
  }
  
  public boolean removeUmlSuperInterface(final Interface jInterface, final Classifier jSuperClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.RemoveUmlSuperInterfaceRoutine routine = new mir.routines.javaToUmlClassifier.RemoveUmlSuperInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, jInterface, jSuperClassifier);
    return routine.applyRoutine();
  }
  
  public boolean createUmlPackage(final org.emftext.language.java.containers.Package jPackage) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.CreateUmlPackageRoutine routine = new mir.routines.javaToUmlClassifier.CreateUmlPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlPackage(final org.emftext.language.java.containers.Package jPackage) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.DeleteUmlPackageRoutine routine = new mir.routines.javaToUmlClassifier.DeleteUmlPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage);
    return routine.applyRoutine();
  }
  
  public boolean addUmlPackageOfClass(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlPackageOfClassRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlPackageOfClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean removeUmlPackageOfClass(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.RemoveUmlPackageOfClassRoutine routine = new mir.routines.javaToUmlClassifier.RemoveUmlPackageOfClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean addUmlElementToModelOrPackage(final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Classifier uClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.AddUmlElementToModelOrPackageRoutine routine = new mir.routines.javaToUmlClassifier.AddUmlElementToModelOrPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, jCompUnit, uClassifier);
    return routine.applyRoutine();
  }
  
  public boolean createUmlEnum(final Enumeration jEnum, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.CreateUmlEnumRoutine routine = new mir.routines.javaToUmlClassifier.CreateUmlEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, jEnum, jCompUnit);
    return routine.applyRoutine();
  }
  
  public boolean createUmlEnumLiteral(final Enumeration jEnum, final EnumConstant jConstant) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.CreateUmlEnumLiteralRoutine routine = new mir.routines.javaToUmlClassifier.CreateUmlEnumLiteralRoutine(_routinesFacade, _reactionExecutionState, _caller, jEnum, jConstant);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlEnumLiteral(final EnumConstant jConstant) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlClassifier.DeleteUmlEnumLiteralRoutine routine = new mir.routines.javaToUmlClassifier.DeleteUmlEnumLiteralRoutine(_routinesFacade, _reactionExecutionState, _caller, jConstant);
    return routine.applyRoutine();
  }
  
  public boolean createUmlClassMethod(final ClassMethod jMeth, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.CreateUmlClassMethodRoutine routine = new mir.routines.javaToUmlMethod.CreateUmlClassMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean addUmlOperationToClass(final org.eclipse.uml2.uml.Class uClass, final Operation uOperation) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.AddUmlOperationToClassRoutine routine = new mir.routines.javaToUmlMethod.AddUmlOperationToClassRoutine(_routinesFacade, _reactionExecutionState, _caller, uClass, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean addUmlOperationToEnum(final org.eclipse.uml2.uml.Enumeration uEnum, final Operation uOperation) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.AddUmlOperationToEnumRoutine routine = new mir.routines.javaToUmlMethod.AddUmlOperationToEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, uEnum, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createUmlInterfaceMethod(final InterfaceMethod jMeth, final Interface jInterface) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.CreateUmlInterfaceMethodRoutine routine = new mir.routines.javaToUmlMethod.CreateUmlInterfaceMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jInterface);
    return routine.applyRoutine();
  }
  
  public boolean createUmlConstructor(final Constructor jConstructor, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.CreateUmlConstructorRoutine routine = new mir.routines.javaToUmlMethod.CreateUmlConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, jConstructor, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlMethod(final Member jMem) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.DeleteUmlMethodRoutine routine = new mir.routines.javaToUmlMethod.DeleteUmlMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, jMem);
    return routine.applyRoutine();
  }
  
  public boolean setUmlMethodAbstract(final ClassMethod jMeth, final Boolean isAbstract) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.SetUmlMethodAbstractRoutine routine = new mir.routines.javaToUmlMethod.SetUmlMethodAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, isAbstract);
    return routine.applyRoutine();
  }
  
  public boolean setUmlMethodFinal(final Method jMethod, final Boolean isFinal) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.SetUmlMethodFinalRoutine routine = new mir.routines.javaToUmlMethod.SetUmlMethodFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, jMethod, isFinal);
    return routine.applyRoutine();
  }
  
  public boolean setUmlFeatureStatic(final AnnotableAndModifiable jElem, final Boolean isStatic) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.SetUmlFeatureStaticRoutine routine = new mir.routines.javaToUmlMethod.SetUmlFeatureStaticRoutine(_routinesFacade, _reactionExecutionState, _caller, jElem, isStatic);
    return routine.applyRoutine();
  }
  
  public boolean createUmlParameter(final Parametrizable jMeth, final OrdinaryParameter jParam) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.CreateUmlParameterRoutine routine = new mir.routines.javaToUmlMethod.CreateUmlParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jParam);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaParameter(final OrdinaryParameter jParam) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.DeleteJavaParameterRoutine routine = new mir.routines.javaToUmlMethod.DeleteJavaParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, jParam);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlParameterType(final OrdinaryParameter jParam, final TypeReference jType) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.ChangeUmlParameterTypeRoutine routine = new mir.routines.javaToUmlMethod.ChangeUmlParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, jParam, jType);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlReturnType(final Method jMeth, final TypeReference jType) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.ChangeUmlReturnTypeRoutine routine = new mir.routines.javaToUmlMethod.ChangeUmlReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jType);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlNamedElementVisibility(final AnnotableAndModifiable jElem, final Modifier mod) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.ChangeUmlNamedElementVisibilityRoutine routine = new mir.routines.javaToUmlMethod.ChangeUmlNamedElementVisibilityRoutine(_routinesFacade, _reactionExecutionState, _caller, jElem, mod);
    return routine.applyRoutine();
  }
  
  public boolean renameUmlNamedElement(final NamedElement jElement) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("javaToUml.JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.RenameUmlNamedElementRoutine routine = new mir.routines.javaToUmlMethod.RenameUmlNamedElementRoutine(_routinesFacade, _reactionExecutionState, _caller, jElement);
    return routine.applyRoutine();
  }
}
