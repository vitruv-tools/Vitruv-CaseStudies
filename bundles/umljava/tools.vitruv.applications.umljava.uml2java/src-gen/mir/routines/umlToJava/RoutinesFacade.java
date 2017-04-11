package mir.routines.umlToJava;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createJavaClass(final Classifier umlClassifier) {
    mir.routines.umlToJava.CreateJavaClassRoutine effect = new mir.routines.umlToJava.CreateJavaClassRoutine(this.executionState, calledBy,
    	umlClassifier);
    effect.applyRoutine();
  }
  
  public void createJavaCompilationUnit(final Classifier umlClassifier, final ConcreteClassifier jClassifier, final Namespace uNamespace) {
    mir.routines.umlToJava.CreateJavaCompilationUnitRoutine effect = new mir.routines.umlToJava.CreateJavaCompilationUnitRoutine(this.executionState, calledBy,
    	umlClassifier, jClassifier, uNamespace);
    effect.applyRoutine();
  }
  
  public void createJavaPackage(final org.eclipse.uml2.uml.Package uPackage) {
    mir.routines.umlToJava.CreateJavaPackageRoutine effect = new mir.routines.umlToJava.CreateJavaPackageRoutine(this.executionState, calledBy,
    	uPackage);
    effect.applyRoutine();
  }
  
  public void renameJavaPackageAndClassifier(final org.eclipse.uml2.uml.Package uPackage) {
    mir.routines.umlToJava.RenameJavaPackageAndClassifierRoutine effect = new mir.routines.umlToJava.RenameJavaPackageAndClassifierRoutine(this.executionState, calledBy,
    	uPackage);
    effect.applyRoutine();
  }
  
  public void renameJavaPackage(final org.eclipse.uml2.uml.Package uPackage) {
    mir.routines.umlToJava.RenameJavaPackageRoutine effect = new mir.routines.umlToJava.RenameJavaPackageRoutine(this.executionState, calledBy,
    	uPackage);
    effect.applyRoutine();
  }
  
  public void changePackageOfJavaCompilationUnit(final org.eclipse.uml2.uml.Package uPackage, final Classifier uClassifier) {
    mir.routines.umlToJava.ChangePackageOfJavaCompilationUnitRoutine effect = new mir.routines.umlToJava.ChangePackageOfJavaCompilationUnitRoutine(this.executionState, calledBy,
    	uPackage, uClassifier);
    effect.applyRoutine();
  }
  
  public void deleteJavaPackage(final org.eclipse.uml2.uml.Package uPackage) {
    mir.routines.umlToJava.DeleteJavaPackageRoutine effect = new mir.routines.umlToJava.DeleteJavaPackageRoutine(this.executionState, calledBy,
    	uPackage);
    effect.applyRoutine();
  }
  
  public void renameJavaClassifier(final Classifier umlClassifier) {
    mir.routines.umlToJava.RenameJavaClassifierRoutine effect = new mir.routines.umlToJava.RenameJavaClassifierRoutine(this.executionState, calledBy,
    	umlClassifier);
    effect.applyRoutine();
  }
  
  public void deleteJavaClass(final Classifier umlClassifer) {
    mir.routines.umlToJava.DeleteJavaClassRoutine effect = new mir.routines.umlToJava.DeleteJavaClassRoutine(this.executionState, calledBy,
    	umlClassifer);
    effect.applyRoutine();
  }
  
  public void setJavaClassFinal(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJava.SetJavaClassFinalRoutine effect = new mir.routines.umlToJava.SetJavaClassFinalRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void setJavaClassAbstract(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJava.SetJavaClassAbstractRoutine effect = new mir.routines.umlToJava.SetJavaClassAbstractRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void changeJavaElementVisibility(final NamedElement uElem) {
    mir.routines.umlToJava.ChangeJavaElementVisibilityRoutine effect = new mir.routines.umlToJava.ChangeJavaElementVisibilityRoutine(this.executionState, calledBy,
    	uElem);
    effect.applyRoutine();
  }
  
  public void changeJavaSuperClass(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJava.ChangeJavaSuperClassRoutine effect = new mir.routines.umlToJava.ChangeJavaSuperClassRoutine(this.executionState, calledBy,
    	superUMLClass, uClass);
    effect.applyRoutine();
  }
  
  public void deleteJavaSuperClass(final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJava.DeleteJavaSuperClassRoutine effect = new mir.routines.umlToJava.DeleteJavaSuperClassRoutine(this.executionState, calledBy,
    	uClass);
    effect.applyRoutine();
  }
  
  public void createJavaEnum(final Enumeration uEnum) {
    mir.routines.umlToJava.CreateJavaEnumRoutine effect = new mir.routines.umlToJava.CreateJavaEnumRoutine(this.executionState, calledBy,
    	uEnum);
    effect.applyRoutine();
  }
  
  public void createJavaEnumConstant(final EnumerationLiteral uLiteral, final Enumeration uEnum) {
    mir.routines.umlToJava.CreateJavaEnumConstantRoutine effect = new mir.routines.umlToJava.CreateJavaEnumConstantRoutine(this.executionState, calledBy,
    	uLiteral, uEnum);
    effect.applyRoutine();
  }
  
  public void deleteJavaEnumConstant(final EnumerationLiteral uLiteral) {
    mir.routines.umlToJava.DeleteJavaEnumConstantRoutine effect = new mir.routines.umlToJava.DeleteJavaEnumConstantRoutine(this.executionState, calledBy,
    	uLiteral);
    effect.applyRoutine();
  }
  
  public void renameJavaNamedElement(final NamedElement uElem, final String name) {
    mir.routines.umlToJava.RenameJavaNamedElementRoutine effect = new mir.routines.umlToJava.RenameJavaNamedElementRoutine(this.executionState, calledBy,
    	uElem, name);
    effect.applyRoutine();
  }
  
  public void createJavaCollectionClass(final DataType dType, final Property innerType) {
    mir.routines.umlToJava.CreateJavaCollectionClassRoutine effect = new mir.routines.umlToJava.CreateJavaCollectionClassRoutine(this.executionState, calledBy,
    	dType, innerType);
    effect.applyRoutine();
  }
  
  public void changeJavaImplementedInterface(final Interface uI, final Interface oldInterface, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJava.ChangeJavaImplementedInterfaceRoutine effect = new mir.routines.umlToJava.ChangeJavaImplementedInterfaceRoutine(this.executionState, calledBy,
    	uI, oldInterface, uClass);
    effect.applyRoutine();
  }
  
  public void deleteJavaImplementedInterface(final Interface uI, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJava.DeleteJavaImplementedInterfaceRoutine effect = new mir.routines.umlToJava.DeleteJavaImplementedInterfaceRoutine(this.executionState, calledBy,
    	uI, uClass);
    effect.applyRoutine();
  }
  
  public void createJavaInterface(final Interface umlInterface) {
    mir.routines.umlToJava.CreateJavaInterfaceRoutine effect = new mir.routines.umlToJava.CreateJavaInterfaceRoutine(this.executionState, calledBy,
    	umlInterface);
    effect.applyRoutine();
  }
  
  public void changeJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    mir.routines.umlToJava.ChangeJavaSuperInterfaceRoutine effect = new mir.routines.umlToJava.ChangeJavaSuperInterfaceRoutine(this.executionState, calledBy,
    	superUMLInterface, uI);
    effect.applyRoutine();
  }
  
  public void deleteJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    mir.routines.umlToJava.DeleteJavaSuperInterfaceRoutine effect = new mir.routines.umlToJava.DeleteJavaSuperInterfaceRoutine(this.executionState, calledBy,
    	superUMLInterface, uI);
    effect.applyRoutine();
  }
  
  public void createJavaAttribute(final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute) {
    mir.routines.umlToJava.CreateJavaAttributeRoutine effect = new mir.routines.umlToJava.CreateJavaAttributeRoutine(this.executionState, calledBy,
    	umlClass, umlAttribute);
    effect.applyRoutine();
  }
  
  public void renameJavaAttribute(final Property umlAttr) {
    mir.routines.umlToJava.RenameJavaAttributeRoutine effect = new mir.routines.umlToJava.RenameJavaAttributeRoutine(this.executionState, calledBy,
    	umlAttr);
    effect.applyRoutine();
  }
  
  public void deleteJavaAttribute(final Property umlAttr) {
    mir.routines.umlToJava.DeleteJavaAttributeRoutine effect = new mir.routines.umlToJava.DeleteJavaAttributeRoutine(this.executionState, calledBy,
    	umlAttr);
    effect.applyRoutine();
  }
  
  public void createJavaMethod(final org.eclipse.uml2.uml.Class uClass, final Operation umlOp) {
    mir.routines.umlToJava.CreateJavaMethodRoutine effect = new mir.routines.umlToJava.CreateJavaMethodRoutine(this.executionState, calledBy,
    	uClass, umlOp);
    effect.applyRoutine();
  }
  
  public void renameJavaMethod(final Operation umlOp) {
    mir.routines.umlToJava.RenameJavaMethodRoutine effect = new mir.routines.umlToJava.RenameJavaMethodRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
  
  public void deleteJavaMethod(final Operation umlOp) {
    mir.routines.umlToJava.DeleteJavaMethodRoutine effect = new mir.routines.umlToJava.DeleteJavaMethodRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
  
  public void createJavaParameter(final Operation uMeth, final Parameter umlParam) {
    mir.routines.umlToJava.CreateJavaParameterRoutine effect = new mir.routines.umlToJava.CreateJavaParameterRoutine(this.executionState, calledBy,
    	uMeth, umlParam);
    effect.applyRoutine();
  }
  
  public void renameJavaParameter(final Parameter uParam) {
    mir.routines.umlToJava.RenameJavaParameterRoutine effect = new mir.routines.umlToJava.RenameJavaParameterRoutine(this.executionState, calledBy,
    	uParam);
    effect.applyRoutine();
  }
  
  public void deleteJavaParameter(final Parameter uParam) {
    mir.routines.umlToJava.DeleteJavaParameterRoutine effect = new mir.routines.umlToJava.DeleteJavaParameterRoutine(this.executionState, calledBy,
    	uParam);
    effect.applyRoutine();
  }
  
  public void changeJavaParameterType(final Parameter uParam, final Type uType) {
    mir.routines.umlToJava.ChangeJavaParameterTypeRoutine effect = new mir.routines.umlToJava.ChangeJavaParameterTypeRoutine(this.executionState, calledBy,
    	uParam, uType);
    effect.applyRoutine();
  }
  
  public void setJavaMethodReturnType(final Parameter uParam, final Integer changedToVoid) {
    mir.routines.umlToJava.SetJavaMethodReturnTypeRoutine effect = new mir.routines.umlToJava.SetJavaMethodReturnTypeRoutine(this.executionState, calledBy,
    	uParam, changedToVoid);
    effect.applyRoutine();
  }
  
  public void changeJavaAttributeType(final Property uAttr, final Type uType) {
    mir.routines.umlToJava.ChangeJavaAttributeTypeRoutine effect = new mir.routines.umlToJava.ChangeJavaAttributeTypeRoutine(this.executionState, calledBy,
    	uAttr, uType);
    effect.applyRoutine();
  }
  
  public void setJavaAttributeFinal(final Property umlAttr) {
    mir.routines.umlToJava.SetJavaAttributeFinalRoutine effect = new mir.routines.umlToJava.SetJavaAttributeFinalRoutine(this.executionState, calledBy,
    	umlAttr);
    effect.applyRoutine();
  }
  
  public void setStatic(final Feature uFeat) {
    mir.routines.umlToJava.SetStaticRoutine effect = new mir.routines.umlToJava.SetStaticRoutine(this.executionState, calledBy,
    	uFeat);
    effect.applyRoutine();
  }
  
  public void setJavaMethodAbstract(final Operation umlOp) {
    mir.routines.umlToJava.SetJavaMethodAbstractRoutine effect = new mir.routines.umlToJava.SetJavaMethodAbstractRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
  
  public void createJavaInterfaceMethod(final Operation umlOp) {
    mir.routines.umlToJava.CreateJavaInterfaceMethodRoutine effect = new mir.routines.umlToJava.CreateJavaInterfaceMethodRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
}
