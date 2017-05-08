package mir.routines.javaToUmlClassifier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PackageableElement;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.EnumConstant;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.Modifier;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createUmlClass(final org.emftext.language.java.classifiers.Class jClass) {
    mir.routines.javaToUmlClassifier.CreateUmlClassRoutine effect = new mir.routines.javaToUmlClassifier.CreateUmlClassRoutine(this.executionState, calledBy,
    	jClass);
    effect.applyRoutine();
  }
  
  public void addUmlElementToRootModel(final PackageableElement uPackageable, final EObject persistedObject) {
    mir.routines.javaToUmlClassifier.AddUmlElementToRootModelRoutine effect = new mir.routines.javaToUmlClassifier.AddUmlElementToRootModelRoutine(this.executionState, calledBy,
    	uPackageable, persistedObject);
    effect.applyRoutine();
  }
  
  public void createUmlInterface(final Interface jInterface) {
    mir.routines.javaToUmlClassifier.CreateUmlInterfaceRoutine effect = new mir.routines.javaToUmlClassifier.CreateUmlInterfaceRoutine(this.executionState, calledBy,
    	jInterface);
    effect.applyRoutine();
  }
  
  public void deleteUmlClassifier(final ConcreteClassifier jClass, final CompilationUnit jCompUnit) {
    mir.routines.javaToUmlClassifier.DeleteUmlClassifierRoutine effect = new mir.routines.javaToUmlClassifier.DeleteUmlClassifierRoutine(this.executionState, calledBy,
    	jClass, jCompUnit);
    effect.applyRoutine();
  }
  
  public void changeUmlNamedElementVisibility(final AnnotableAndModifiable jElem, final Modifier mod) {
    mir.routines.javaToUmlClassifier.ChangeUmlNamedElementVisibilityRoutine effect = new mir.routines.javaToUmlClassifier.ChangeUmlNamedElementVisibilityRoutine(this.executionState, calledBy,
    	jElem, mod);
    effect.applyRoutine();
  }
  
  public void setUmlClassAbstract(final org.emftext.language.java.classifiers.Class jClass, final Boolean isAbstract) {
    mir.routines.javaToUmlClassifier.SetUmlClassAbstractRoutine effect = new mir.routines.javaToUmlClassifier.SetUmlClassAbstractRoutine(this.executionState, calledBy,
    	jClass, isAbstract);
    effect.applyRoutine();
  }
  
  public void addUmlSuperClassifier(final ConcreteClassifier jClass, final Classifier jSuperClassifier) {
    mir.routines.javaToUmlClassifier.AddUmlSuperClassifierRoutine effect = new mir.routines.javaToUmlClassifier.AddUmlSuperClassifierRoutine(this.executionState, calledBy,
    	jClass, jSuperClassifier);
    effect.applyRoutine();
  }
  
  public void clearUmlSuperClassifiers(final ConcreteClassifier jClass) {
    mir.routines.javaToUmlClassifier.ClearUmlSuperClassifiersRoutine effect = new mir.routines.javaToUmlClassifier.ClearUmlSuperClassifiersRoutine(this.executionState, calledBy,
    	jClass);
    effect.applyRoutine();
  }
  
  public void removeUmlSuperInterface(final Interface jI, final Classifier jSuper) {
    mir.routines.javaToUmlClassifier.RemoveUmlSuperInterfaceRoutine effect = new mir.routines.javaToUmlClassifier.RemoveUmlSuperInterfaceRoutine(this.executionState, calledBy,
    	jI, jSuper);
    effect.applyRoutine();
  }
  
  public void addUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final Interface jI) {
    mir.routines.javaToUmlClassifier.AddUmlClassImplementRoutine effect = new mir.routines.javaToUmlClassifier.AddUmlClassImplementRoutine(this.executionState, calledBy,
    	jClass, jI);
    effect.applyRoutine();
  }
  
  public void removeUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final Interface jI) {
    mir.routines.javaToUmlClassifier.RemoveUmlClassImplementRoutine effect = new mir.routines.javaToUmlClassifier.RemoveUmlClassImplementRoutine(this.executionState, calledBy,
    	jClass, jI);
    effect.applyRoutine();
  }
  
  public void setUmlClassFinal(final org.emftext.language.java.classifiers.Class jClass, final Boolean isFinal) {
    mir.routines.javaToUmlClassifier.SetUmlClassFinalRoutine effect = new mir.routines.javaToUmlClassifier.SetUmlClassFinalRoutine(this.executionState, calledBy,
    	jClass, isFinal);
    effect.applyRoutine();
  }
  
  public void renameUmlNamedElement(final NamedElement jElement) {
    mir.routines.javaToUmlClassifier.RenameUmlNamedElementRoutine effect = new mir.routines.javaToUmlClassifier.RenameUmlNamedElementRoutine(this.executionState, calledBy,
    	jElement);
    effect.applyRoutine();
  }
  
  public void createUmlPackage(final org.emftext.language.java.containers.Package jPackage) {
    mir.routines.javaToUmlClassifier.CreateUmlPackageRoutine effect = new mir.routines.javaToUmlClassifier.CreateUmlPackageRoutine(this.executionState, calledBy,
    	jPackage);
    effect.applyRoutine();
  }
  
  public void addUmlPackageOfClass(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlClassifier.AddUmlPackageOfClassRoutine effect = new mir.routines.javaToUmlClassifier.AddUmlPackageOfClassRoutine(this.executionState, calledBy,
    	jPackage, jClassifier);
    effect.applyRoutine();
  }
  
  public void removeUmlPackageOfClass(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlClassifier.RemoveUmlPackageOfClassRoutine effect = new mir.routines.javaToUmlClassifier.RemoveUmlPackageOfClassRoutine(this.executionState, calledBy,
    	jPackage, jClassifier);
    effect.applyRoutine();
  }
  
  public void createUmlEnum(final Enumeration jEnum) {
    mir.routines.javaToUmlClassifier.CreateUmlEnumRoutine effect = new mir.routines.javaToUmlClassifier.CreateUmlEnumRoutine(this.executionState, calledBy,
    	jEnum);
    effect.applyRoutine();
  }
  
  public void createUmlEnumLiteral(final Enumeration jEnum, final EnumConstant jConstant) {
    mir.routines.javaToUmlClassifier.CreateUmlEnumLiteralRoutine effect = new mir.routines.javaToUmlClassifier.CreateUmlEnumLiteralRoutine(this.executionState, calledBy,
    	jEnum, jConstant);
    effect.applyRoutine();
  }
  
  public void deleteUmlEnumLiteral(final EnumConstant jConstant) {
    mir.routines.javaToUmlClassifier.DeleteUmlEnumLiteralRoutine effect = new mir.routines.javaToUmlClassifier.DeleteUmlEnumLiteralRoutine(this.executionState, calledBy,
    	jConstant);
    effect.applyRoutine();
  }
}
