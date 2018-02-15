package mir.routines.umlToJava;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.Interface;
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
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.CreateJavaAttributeRoutine routine = new mir.routines.umlToJavaAttribute.CreateJavaAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, umlAttribute);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaAttribute(final Property umlAttr) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.DeleteJavaAttributeRoutine routine = new mir.routines.umlToJavaAttribute.DeleteJavaAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlAttr);
    return routine.applyRoutine();
  }
  
  public boolean setJavaAttributeFinal(final Property umlAttr) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.SetJavaAttributeFinalRoutine routine = new mir.routines.umlToJavaAttribute.SetJavaAttributeFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, umlAttr);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaAttributeType(final Property uAttr, final Type uType) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.ChangeJavaAttributeTypeRoutine routine = new mir.routines.umlToJavaAttribute.ChangeJavaAttributeTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, uAttr, uType);
    return routine.applyRoutine();
  }
  
  public boolean handleMultiplicityForJavaAttribute(final Property uAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.HandleMultiplicityForJavaAttributeRoutine routine = new mir.routines.umlToJavaAttribute.HandleMultiplicityForJavaAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, uAttribute);
    return routine.applyRoutine();
  }
  
  public boolean createJavaGetter(final Field jAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.CreateJavaGetterRoutine routine = new mir.routines.umlToJavaAttribute.CreateJavaGetterRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttribute);
    return routine.applyRoutine();
  }
  
  public boolean createJavaSetter(final Field jAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.CreateJavaSetterRoutine routine = new mir.routines.umlToJavaAttribute.CreateJavaSetterRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttribute);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaAttribute(final String oldName, final String newName, final Property uAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.RenameJavaAttributeRoutine routine = new mir.routines.umlToJavaAttribute.RenameJavaAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, oldName, newName, uAttribute);
    return routine.applyRoutine();
  }
  
  public boolean createJavaClass(final Classifier umlClassifier) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaClassRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifier);
    return routine.applyRoutine();
  }
  
  public boolean createJavaCompilationUnit(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaCompilationUnitRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaCompilationUnitRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifier, jClassifier, uNamespace);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaClassifier(final Classifier umlClassifier) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.RenameJavaClassifierRoutine routine = new mir.routines.umlToJavaClassifier.RenameJavaClassifierRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifier);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaClass(final Classifier umlClassifer) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaClassRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaClassRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClassifer);
    return routine.applyRoutine();
  }
  
  public boolean setJavaClassFinal(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.SetJavaClassFinalRoutine routine = new mir.routines.umlToJavaClassifier.SetJavaClassFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean setJavaClassAbstract(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.SetJavaClassAbstractRoutine routine = new mir.routines.umlToJavaClassifier.SetJavaClassAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaSuperClass(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.ChangeJavaSuperClassRoutine routine = new mir.routines.umlToJavaClassifier.ChangeJavaSuperClassRoutine(_routinesFacade, _reactionExecutionState, _caller, superUMLClass, uClass);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaSuperClass(final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaSuperClassRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaSuperClassRoutine(_routinesFacade, _reactionExecutionState, _caller, uClass);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaImplementedInterface(final Interface uInterface, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.ChangeJavaImplementedInterfaceRoutine routine = new mir.routines.umlToJavaClassifier.ChangeJavaImplementedInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, uInterface, oldInterface, uClass);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaImplementedInterface(final Interface uInterface, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaImplementedInterfaceRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaImplementedInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, uInterface, uClass);
    return routine.applyRoutine();
  }
  
  public boolean createJavaInterface(final Interface umlInterface) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaInterfaceRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.ChangeJavaSuperInterfaceRoutine routine = new mir.routines.umlToJavaClassifier.ChangeJavaSuperInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, superUMLInterface, uI);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaSuperInterfaceRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaSuperInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, superUMLInterface, uI);
    return routine.applyRoutine();
  }
  
  public boolean createJavaEnum(final Enumeration uEnum) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaEnumRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, uEnum);
    return routine.applyRoutine();
  }
  
  public boolean createJavaEnumConstant(final EnumerationLiteral uLiteral, final Enumeration uEnum) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaEnumConstantRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaEnumConstantRoutine(_routinesFacade, _reactionExecutionState, _caller, uLiteral, uEnum);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaEnumConstant(final EnumerationLiteral uLiteral) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaEnumConstantRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaEnumConstantRoutine(_routinesFacade, _reactionExecutionState, _caller, uLiteral);
    return routine.applyRoutine();
  }
  
  public boolean createJavaPackage(final org.eclipse.uml2.uml.Package uPackage, final org.eclipse.uml2.uml.Package uSuperPackage) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CreateJavaPackageRoutine routine = new mir.routines.umlToJavaClassifier.CreateJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackage, uSuperPackage);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaPackage(final org.eclipse.uml2.uml.Package uPackage, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.RenameJavaPackageRoutine routine = new mir.routines.umlToJavaClassifier.RenameJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackage, uNamespace);
    return routine.applyRoutine();
  }
  
  public boolean changePackageOfJavaCompilationUnit(final org.emftext.language.java.containers.Package jPackage, final CompilationUnit jCompUnit, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.ChangePackageOfJavaCompilationUnitRoutine routine = new mir.routines.umlToJavaClassifier.ChangePackageOfJavaCompilationUnitRoutine(_routinesFacade, _reactionExecutionState, _caller, jPackage, jCompUnit, uNamespace);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaPackage(final org.eclipse.uml2.uml.Package uPackage) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.DeleteJavaPackageRoutine routine = new mir.routines.umlToJavaClassifier.DeleteJavaPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, uPackage);
    return routine.applyRoutine();
  }
  
  public boolean checkIfCorrespondingJavaPrimitiveTypeExists(final PrimitiveType uPrimType) {
    mir.routines.umlToJavaClassifier.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaClassifier").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaClassifier.CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine routine = new mir.routines.umlToJavaClassifier.CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine(_routinesFacade, _reactionExecutionState, _caller, uPrimType);
    return routine.applyRoutine();
  }
  
  public boolean createJavaMethod(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.CreateJavaMethodRoutine routine = new mir.routines.umlToJavaMethod.CreateJavaMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createJavaClassMethod(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.CreateJavaClassMethodRoutine routine = new mir.routines.umlToJavaMethod.CreateJavaClassMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createJavaConstructor(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.CreateJavaConstructorRoutine routine = new mir.routines.umlToJavaMethod.CreateJavaConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaMethod(final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.DeleteJavaMethodRoutine routine = new mir.routines.umlToJavaMethod.DeleteJavaMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean setJavaMethodReturnType(final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine routine = new mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean setStatic(final Feature uFeat) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.SetStaticRoutine routine = new mir.routines.umlToJavaMethod.SetStaticRoutine(_routinesFacade, _reactionExecutionState, _caller, uFeat);
    return routine.applyRoutine();
  }
  
  public boolean setJavaMethodAbstract(final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.SetJavaMethodAbstractRoutine routine = new mir.routines.umlToJavaMethod.SetJavaMethodAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createJavaInterfaceMethod(final Interface uInterface, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.CreateJavaInterfaceMethodRoutine routine = new mir.routines.umlToJavaMethod.CreateJavaInterfaceMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uInterface, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean setJavaMethodFinal(final Operation uOperation, final Boolean isFinal) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.SetJavaMethodFinalRoutine routine = new mir.routines.umlToJavaMethod.SetJavaMethodFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation, isFinal);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaElementVisibility(final NamedElement uElem) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.ChangeJavaElementVisibilityRoutine routine = new mir.routines.umlToJavaMethod.ChangeJavaElementVisibilityRoutine(_routinesFacade, _reactionExecutionState, _caller, uElem);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaNamedElement(final NamedElement uElem, final String name) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.RenameJavaNamedElementRoutine routine = new mir.routines.umlToJavaMethod.RenameJavaNamedElementRoutine(_routinesFacade, _reactionExecutionState, _caller, uElem, name);
    return routine.applyRoutine();
  }
  
  public boolean createJavaParameter(final Operation uMeth, final Parameter umlParam) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.CreateJavaParameterRoutine routine = new mir.routines.umlToJavaMethod.CreateJavaParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, uMeth, umlParam);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaParameter(final Parameter uParam) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.DeleteJavaParameterRoutine routine = new mir.routines.umlToJavaMethod.DeleteJavaParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, uParam);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaParameterType(final Parameter uParam, final Type uType) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.ChangeJavaParameterTypeRoutine routine = new mir.routines.umlToJavaMethod.ChangeJavaParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, uParam, uType);
    return routine.applyRoutine();
  }
  
  public boolean adaptJavaParametertoDirectionChange(final Operation uOperation, final Parameter uParam, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("umlToJava.UmlToJavaMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.AdaptJavaParametertoDirectionChangeRoutine routine = new mir.routines.umlToJavaMethod.AdaptJavaParametertoDirectionChangeRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation, uParam, oldDirection, newDirection);
    return routine.applyRoutine();
  }
}
