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
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlAttribute")));
    return _routinesFacade.createUmlAttributeInEnum(jEnum, jAttr);
  }
  
  public boolean createUmlAttributeInClass(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlAttribute")));
    return _routinesFacade.createUmlAttributeInClass(jClass, jAttr);
  }
  
  public boolean changeUmlAttributeType(final Field jAttr, final TypeReference jType) {
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlAttribute")));
    return _routinesFacade.changeUmlAttributeType(jAttr, jType);
  }
  
  public boolean setUmlAttributeFinal(final Field jAttr, final Boolean isFinal) {
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlAttribute")));
    return _routinesFacade.setUmlAttributeFinal(jAttr, isFinal);
  }
  
  public boolean createUmlClass(final org.emftext.language.java.classifiers.Class jClass, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.createUmlClass(jClass, jCompUnit);
  }
  
  public boolean addUmlElementToPackage(final PackageableElement uPackageable, final org.eclipse.uml2.uml.Package uPackage, final EObject persistedObject) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.addUmlElementToPackage(uPackageable, uPackage, persistedObject);
  }
  
  public boolean deleteUmlClassifier(final ConcreteClassifier jClassifier, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.deleteUmlClassifier(jClassifier, jCompUnit);
  }
  
  public boolean setUmlClassAbstract(final org.emftext.language.java.classifiers.Class jClass, final Boolean isAbstract) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.setUmlClassAbstract(jClass, isAbstract);
  }
  
  public boolean setUmlClassFinal(final org.emftext.language.java.classifiers.Class jClass, final Boolean isFinal) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.setUmlClassFinal(jClass, isFinal);
  }
  
  public boolean addUmlSuperClass(final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Class jSuperClass) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.addUmlSuperClass(jClass, jSuperClass);
  }
  
  public boolean clearUmlSuperClassifiers(final org.emftext.language.java.classifiers.Class jClass) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.clearUmlSuperClassifiers(jClass);
  }
  
  public boolean addUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final Classifier jInterface) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.addUmlClassImplement(jClass, jInterface);
  }
  
  public boolean removeUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final Interface jInterface) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.removeUmlClassImplement(jClass, jInterface);
  }
  
  public boolean createUmlInterface(final Interface jInterface, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.createUmlInterface(jInterface, jCompUnit);
  }
  
  public boolean addUmlSuperinterfaces(final Interface jInterface, final Classifier jSuperInterface) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.addUmlSuperinterfaces(jInterface, jSuperInterface);
  }
  
  public boolean removeUmlSuperInterface(final Interface jInterface, final Classifier jSuperClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.removeUmlSuperInterface(jInterface, jSuperClassifier);
  }
  
  public boolean createUmlPackage(final org.emftext.language.java.containers.Package jPackage) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.createUmlPackage(jPackage);
  }
  
  public boolean deleteUmlPackage(final org.emftext.language.java.containers.Package jPackage) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.deleteUmlPackage(jPackage);
  }
  
  public boolean addUmlPackageOfClass(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.addUmlPackageOfClass(jPackage, jClassifier);
  }
  
  public boolean removeUmlPackageOfClass(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.removeUmlPackageOfClass(jPackage, jClassifier);
  }
  
  public boolean addUmlElementToModelOrPackage(final CompilationUnit jCompUnit, final org.eclipse.uml2.uml.Classifier uClassifier) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.addUmlElementToModelOrPackage(jCompUnit, uClassifier);
  }
  
  public boolean createUmlEnum(final Enumeration jEnum, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.createUmlEnum(jEnum, jCompUnit);
  }
  
  public boolean createUmlEnumLiteral(final Enumeration jEnum, final EnumConstant jConstant) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.createUmlEnumLiteral(jEnum, jConstant);
  }
  
  public boolean deleteUmlEnumLiteral(final EnumConstant jConstant) {
    mir.routines.javaToUmlClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlClassifier")));
    return _routinesFacade.deleteUmlEnumLiteral(jConstant);
  }
  
  public boolean createUmlClassMethod(final ClassMethod jMeth, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.createUmlClassMethod(jMeth, jClassifier);
  }
  
  public boolean addUmlOperationToClass(final org.eclipse.uml2.uml.Class uClass, final Operation uOperation) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.addUmlOperationToClass(uClass, uOperation);
  }
  
  public boolean addUmlOperationToEnum(final org.eclipse.uml2.uml.Enumeration uEnum, final Operation uOperation) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.addUmlOperationToEnum(uEnum, uOperation);
  }
  
  public boolean createUmlInterfaceMethod(final InterfaceMethod jMeth, final Interface jInterface) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.createUmlInterfaceMethod(jMeth, jInterface);
  }
  
  public boolean createUmlConstructor(final Constructor jConstructor, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.createUmlConstructor(jConstructor, jClassifier);
  }
  
  public boolean deleteUmlMethod(final Member jMem) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.deleteUmlMethod(jMem);
  }
  
  public boolean setUmlMethodAbstract(final ClassMethod jMeth, final Boolean isAbstract) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.setUmlMethodAbstract(jMeth, isAbstract);
  }
  
  public boolean setUmlMethodFinal(final Method jMethod, final Boolean isFinal) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.setUmlMethodFinal(jMethod, isFinal);
  }
  
  public boolean setUmlFeatureStatic(final AnnotableAndModifiable jElem, final Boolean isStatic) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.setUmlFeatureStatic(jElem, isStatic);
  }
  
  public boolean createUmlParameter(final Parametrizable jMeth, final OrdinaryParameter jParam) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.createUmlParameter(jMeth, jParam);
  }
  
  public boolean deleteJavaParameter(final OrdinaryParameter jParam) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.deleteJavaParameter(jParam);
  }
  
  public boolean changeUmlParameterType(final OrdinaryParameter jParam, final TypeReference jType) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.changeUmlParameterType(jParam, jType);
  }
  
  public boolean changeUmlReturnType(final Method jMeth, final TypeReference jType) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.changeUmlReturnType(jMeth, jType);
  }
  
  public boolean changeUmlNamedElementVisibility(final AnnotableAndModifiable jElem, final Modifier mod) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.changeUmlNamedElementVisibility(jElem, mod);
  }
  
  public boolean renameUmlNamedElement(final NamedElement jElement) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("JavaToUmlMethod")));
    return _routinesFacade.renameUmlNamedElement(jElement);
  }
}
