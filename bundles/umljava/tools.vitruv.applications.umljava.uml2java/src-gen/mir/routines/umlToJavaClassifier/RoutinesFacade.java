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
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public boolean createJavaClass(final Classifier umlClassifier) {
    mir.routines.umlToJavaClassifier.CreateJavaClassRoutine effect = new mir.routines.umlToJavaClassifier.CreateJavaClassRoutine(this.executionState, calledBy, umlClassifier);
    return effect.applyRoutine();
  }
  
  public boolean createJavaCompilationUnit(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.CreateJavaCompilationUnitRoutine effect = new mir.routines.umlToJavaClassifier.CreateJavaCompilationUnitRoutine(this.executionState, calledBy, umlClassifier, jClassifier, uNamespace);
    return effect.applyRoutine();
  }
  
  public boolean renameJavaClassifier(final Classifier umlClassifier) {
    mir.routines.umlToJavaClassifier.RenameJavaClassifierRoutine effect = new mir.routines.umlToJavaClassifier.RenameJavaClassifierRoutine(this.executionState, calledBy, umlClassifier);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaClass(final Classifier umlClassifer) {
    mir.routines.umlToJavaClassifier.DeleteJavaClassRoutine effect = new mir.routines.umlToJavaClassifier.DeleteJavaClassRoutine(this.executionState, calledBy, umlClassifer);
    return effect.applyRoutine();
  }
  
  public boolean setJavaClassFinal(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJavaClassifier.SetJavaClassFinalRoutine effect = new mir.routines.umlToJavaClassifier.SetJavaClassFinalRoutine(this.executionState, calledBy, umlClass);
    return effect.applyRoutine();
  }
  
  public boolean setJavaClassAbstract(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJavaClassifier.SetJavaClassAbstractRoutine effect = new mir.routines.umlToJavaClassifier.SetJavaClassAbstractRoutine(this.executionState, calledBy, umlClass);
    return effect.applyRoutine();
  }
  
  public boolean changeJavaSuperClass(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.ChangeJavaSuperClassRoutine effect = new mir.routines.umlToJavaClassifier.ChangeJavaSuperClassRoutine(this.executionState, calledBy, superUMLClass, uClass);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaSuperClass(final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.DeleteJavaSuperClassRoutine effect = new mir.routines.umlToJavaClassifier.DeleteJavaSuperClassRoutine(this.executionState, calledBy, uClass);
    return effect.applyRoutine();
  }
  
  public boolean changeJavaImplementedInterface(final Interface uInterface, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.ChangeJavaImplementedInterfaceRoutine effect = new mir.routines.umlToJavaClassifier.ChangeJavaImplementedInterfaceRoutine(this.executionState, calledBy, uInterface, oldInterface, uClass);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaImplementedInterface(final Interface uInterface, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJavaClassifier.DeleteJavaImplementedInterfaceRoutine effect = new mir.routines.umlToJavaClassifier.DeleteJavaImplementedInterfaceRoutine(this.executionState, calledBy, uInterface, uClass);
    return effect.applyRoutine();
  }
  
  public boolean createJavaInterface(final Interface umlInterface) {
    mir.routines.umlToJavaClassifier.CreateJavaInterfaceRoutine effect = new mir.routines.umlToJavaClassifier.CreateJavaInterfaceRoutine(this.executionState, calledBy, umlInterface);
    return effect.applyRoutine();
  }
  
  public boolean changeJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    mir.routines.umlToJavaClassifier.ChangeJavaSuperInterfaceRoutine effect = new mir.routines.umlToJavaClassifier.ChangeJavaSuperInterfaceRoutine(this.executionState, calledBy, superUMLInterface, uI);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    mir.routines.umlToJavaClassifier.DeleteJavaSuperInterfaceRoutine effect = new mir.routines.umlToJavaClassifier.DeleteJavaSuperInterfaceRoutine(this.executionState, calledBy, superUMLInterface, uI);
    return effect.applyRoutine();
  }
  
  public boolean createJavaEnum(final Enumeration uEnum) {
    mir.routines.umlToJavaClassifier.CreateJavaEnumRoutine effect = new mir.routines.umlToJavaClassifier.CreateJavaEnumRoutine(this.executionState, calledBy, uEnum);
    return effect.applyRoutine();
  }
  
  public boolean createJavaEnumConstant(final EnumerationLiteral uLiteral, final Enumeration uEnum) {
    mir.routines.umlToJavaClassifier.CreateJavaEnumConstantRoutine effect = new mir.routines.umlToJavaClassifier.CreateJavaEnumConstantRoutine(this.executionState, calledBy, uLiteral, uEnum);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaEnumConstant(final EnumerationLiteral uLiteral) {
    mir.routines.umlToJavaClassifier.DeleteJavaEnumConstantRoutine effect = new mir.routines.umlToJavaClassifier.DeleteJavaEnumConstantRoutine(this.executionState, calledBy, uLiteral);
    return effect.applyRoutine();
  }
  
  public boolean createJavaPackage(final org.eclipse.uml2.uml.Package uPackage, final org.eclipse.uml2.uml.Package uSuperPackage) {
    mir.routines.umlToJavaClassifier.CreateJavaPackageRoutine effect = new mir.routines.umlToJavaClassifier.CreateJavaPackageRoutine(this.executionState, calledBy, uPackage, uSuperPackage);
    return effect.applyRoutine();
  }
  
  public boolean renameJavaPackage(final org.eclipse.uml2.uml.Package uPackage, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.RenameJavaPackageRoutine effect = new mir.routines.umlToJavaClassifier.RenameJavaPackageRoutine(this.executionState, calledBy, uPackage, uNamespace);
    return effect.applyRoutine();
  }
  
  public boolean changePackageOfJavaCompilationUnit(final org.emftext.language.java.containers.Package jPackage, final CompilationUnit jCompUnit, final Namespace uNamespace) {
    mir.routines.umlToJavaClassifier.ChangePackageOfJavaCompilationUnitRoutine effect = new mir.routines.umlToJavaClassifier.ChangePackageOfJavaCompilationUnitRoutine(this.executionState, calledBy, jPackage, jCompUnit, uNamespace);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaPackage(final org.eclipse.uml2.uml.Package uPackage) {
    mir.routines.umlToJavaClassifier.DeleteJavaPackageRoutine effect = new mir.routines.umlToJavaClassifier.DeleteJavaPackageRoutine(this.executionState, calledBy, uPackage);
    return effect.applyRoutine();
  }
  
  public boolean checkIfCorrespondingJavaPrimitiveTypeExists(final PrimitiveType uPrimType) {
    mir.routines.umlToJavaClassifier.CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine effect = new mir.routines.umlToJavaClassifier.CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine(this.executionState, calledBy, uPrimType);
    return effect.applyRoutine();
  }
}
