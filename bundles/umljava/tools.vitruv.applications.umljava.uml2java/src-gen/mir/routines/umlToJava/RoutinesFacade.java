package mir.routines.umlToJava;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.Field;
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
  
  public boolean createJavaAttribute(final Classifier uClassifier, final Property umlAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaAttribute")));
    return _routinesFacade.createJavaAttribute(uClassifier, umlAttribute);
  }
  
  public boolean deleteJavaAttribute(final Property umlAttr) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaAttribute")));
    return _routinesFacade.deleteJavaAttribute(umlAttr);
  }
  
  public boolean setJavaAttributeFinal(final Property umlAttr) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaAttribute")));
    return _routinesFacade.setJavaAttributeFinal(umlAttr);
  }
  
  public boolean changeJavaAttributeType(final Property uAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaAttribute")));
    return _routinesFacade.changeJavaAttributeType(uAttribute);
  }
  
  public boolean createJavaGetter(final Field jAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaAttribute")));
    return _routinesFacade.createJavaGetter(jAttribute);
  }
  
  public boolean createJavaSetter(final Field jAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaAttribute")));
    return _routinesFacade.createJavaSetter(jAttribute);
  }
  
  public boolean renameJavaAttribute(final String oldName, final String newName, final Property uAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaAttribute")));
    return _routinesFacade.renameJavaAttribute(oldName, newName, uAttribute);
  }
  
  public boolean createJavaClass(final Classifier umlClassifier) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.createJavaClass(umlClassifier);
  }
  
  public boolean createJavaCompilationUnit(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.createJavaCompilationUnit(umlClassifier, jClassifier, uNamespace);
  }
  
  public boolean renameJavaClassifier(final Classifier umlClassifier) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.renameJavaClassifier(umlClassifier);
  }
  
  public boolean deleteJavaClass(final Classifier umlClassifer) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.deleteJavaClass(umlClassifer);
  }
  
  public boolean setJavaClassFinal(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.setJavaClassFinal(umlClass);
  }
  
  public boolean setJavaClassAbstract(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.setJavaClassAbstract(umlClass);
  }
  
  public boolean addJavaSuperClass(final org.eclipse.uml2.uml.Class uClass, final Generalization uGeneralization) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.addJavaSuperClass(uClass, uGeneralization);
  }
  
  public boolean deleteJavaSuperClass(final Generalization uGeneralization) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.deleteJavaSuperClass(uGeneralization);
  }
  
  public boolean createJavaClassImplementsReference(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.createJavaClassImplementsReference(uRealization, uClass);
  }
  
  public boolean addImplementsCorrespondence(final InterfaceRealization uRealization, final TypeReference jReference) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.addImplementsCorrespondence(uRealization, jReference);
  }
  
  public boolean deleteJavaClassImplementsReference(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.deleteJavaClassImplementsReference(uRealization, uClass);
  }
  
  public boolean createJavaInterface(final Interface umlInterface) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.createJavaInterface(umlInterface);
  }
  
  public boolean addJavaSuperInterface(final Interface uInterface, final Generalization uGeneralization) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.addJavaSuperInterface(uInterface, uGeneralization);
  }
  
  public boolean addGeneralizationCorrespondence(final Generalization uGeneralization, final TypeReference jReference) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.addGeneralizationCorrespondence(uGeneralization, jReference);
  }
  
  public boolean deleteJavaSuperInterface(final Generalization uGeneralization) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.deleteJavaSuperInterface(uGeneralization);
  }
  
  public boolean createJavaEnum(final Enumeration uEnum) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.createJavaEnum(uEnum);
  }
  
  public boolean createJavaEnumConstant(final EnumerationLiteral uLiteral, final Enumeration uEnum) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.createJavaEnumConstant(uLiteral, uEnum);
  }
  
  public boolean deleteJavaEnumConstant(final EnumerationLiteral uLiteral) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.deleteJavaEnumConstant(uLiteral);
  }
  
  public boolean checkIfUmlModelCorrespondenceExists(final Model newModel) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.checkIfUmlModelCorrespondenceExists(newModel);
  }
  
  public boolean addUmlModelCorrespondence(final Model newModel) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.addUmlModelCorrespondence(newModel);
  }
  
  public boolean createJavaPackage(final org.eclipse.uml2.uml.Package uPackage, final org.eclipse.uml2.uml.Package uSuperPackage) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.createJavaPackage(uPackage, uSuperPackage);
  }
  
  public boolean renameJavaPackage(final org.eclipse.uml2.uml.Package uPackage, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.renameJavaPackage(uPackage, uNamespace);
  }
  
  public boolean changePackageOfJavaCompilationUnit(final org.emftext.language.java.containers.Package jPackage, final CompilationUnit jCompUnit, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.changePackageOfJavaCompilationUnit(jPackage, jCompUnit, uNamespace);
  }
  
  public boolean deleteJavaPackage(final org.eclipse.uml2.uml.Package uPackage) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.deleteJavaPackage(uPackage);
  }
  
  public boolean checkIfCorrespondingJavaPrimitiveTypeExists(final PrimitiveType uPrimType) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaClassifier")));
    return _routinesFacade.checkIfCorrespondingJavaPrimitiveTypeExists(uPrimType);
  }
  
  public boolean createJavaMethod(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.createJavaMethod(uClassifier, uOperation);
  }
  
  public boolean createJavaClassMethod(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.createJavaClassMethod(uClassifier, uOperation);
  }
  
  public boolean createJavaConstructor(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.createJavaConstructor(uClassifier, uOperation);
  }
  
  public boolean deleteJavaMethod(final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.deleteJavaMethod(uOperation);
  }
  
  public boolean setJavaMethodReturnType(final Operation uOperation, final Parameter uParam) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.setJavaMethodReturnType(uOperation, uParam);
  }
  
  public boolean setStatic(final Feature uFeat) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.setStatic(uFeat);
  }
  
  public boolean setJavaMethodAbstract(final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.setJavaMethodAbstract(uOperation);
  }
  
  public boolean createJavaInterfaceMethod(final Interface uInterface, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.createJavaInterfaceMethod(uInterface, uOperation);
  }
  
  public boolean setJavaMethodFinal(final Operation uOperation, final Boolean isFinal) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.setJavaMethodFinal(uOperation, isFinal);
  }
  
  public boolean changeJavaElementVisibility(final NamedElement uElem) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.changeJavaElementVisibility(uElem);
  }
  
  public boolean renameJavaNamedElement(final NamedElement uElem, final String name) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.renameJavaNamedElement(uElem, name);
  }
  
  public boolean createJavaParameter(final Operation uMeth, final Parameter umlParam) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.createJavaParameter(uMeth, umlParam);
  }
  
  public boolean deleteJavaParameter(final Parameter uParam) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.deleteJavaParameter(uParam);
  }
  
  public boolean changeJavaParameterType(final Parameter uParam, final Type uType) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.changeJavaParameterType(uParam, uType);
  }
  
  public boolean adaptJavaParametertoDirectionChange(final Operation uOperation, final Parameter uParam, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaMethod")));
    return _routinesFacade.adaptJavaParametertoDirectionChange(uOperation, uParam, oldDirection, newDirection);
  }
}
