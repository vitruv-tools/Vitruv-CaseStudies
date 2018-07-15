package mir.routines.pcm2javaCommon;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.generics.GenericsFactory;
import org.emftext.language.java.generics.QualifiedTypeArgument;
import org.emftext.language.java.generics.TypeArgument;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.domains.java.util.JavaModificationUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddSuperTypeToDataTypeRoutine extends AbstractRepairRoutineRealization {
  private AddSuperTypeToDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU) {
      return dataTypeImplementationCU;
    }
    
    public void update0Element(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU) {
      final ClassifierImport collectionTypeClassImport = JavaModificationUtil.createJavaClassImport(superTypeQualifiedName);
      EList<Import> _imports = dataTypeImplementationCU.getImports();
      _imports.add(collectionTypeClassImport);
    }
    
    public EObject getElement4(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
      return dataTypeImplementation;
    }
    
    public void updateNamespaceClassifierElement(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
      JavaModificationUtil.createNamespaceClassifierReference(namespaceClassifier, IterableExtensions.<ClassifierImport>last(Iterables.<ClassifierImport>filter(dataTypeImplementationCU.getImports(), ClassifierImport.class)).getClassifier());
      final QualifiedTypeArgument qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument();
      qualifiedTypeArgument.setTypeReference(innerTypeReference);
      EList<TypeArgument> _typeArguments = namespaceClassifier.getClassifierReferences().get(0).getTypeArguments();
      _typeArguments.add(qualifiedTypeArgument);
    }
    
    public EObject getElement2(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
      return namespaceClassifier;
    }
    
    public EObject getCorrepondenceSourceDataTypeImplementationCU(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation) {
      return dataType;
    }
    
    public EObject getElement3(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
      return dataType;
    }
    
    public EObject getCorrepondenceSourceDataTypeImplementation(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
      return dataType;
    }
    
    public void update1Element(final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName, final org.emftext.language.java.classifiers.Class dataTypeImplementation, final CompilationUnit dataTypeImplementationCU, final NamespaceClassifierReference namespaceClassifier) {
      dataTypeImplementation.setExtends(namespaceClassifier);
    }
  }
  
  public AddSuperTypeToDataTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType dataType, final TypeReference innerTypeReference, final String superTypeQualifiedName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.AddSuperTypeToDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.dataType = dataType;this.innerTypeReference = innerTypeReference;this.superTypeQualifiedName = superTypeQualifiedName;
  }
  
  private DataType dataType;
  
  private TypeReference innerTypeReference;
  
  private String superTypeQualifiedName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddSuperTypeToDataTypeRoutine with input:");
    getLogger().debug("   dataType: " + this.dataType);
    getLogger().debug("   innerTypeReference: " + this.innerTypeReference);
    getLogger().debug("   superTypeQualifiedName: " + this.superTypeQualifiedName);
    
    org.emftext.language.java.classifiers.Class dataTypeImplementation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDataTypeImplementation(dataType, innerTypeReference, superTypeQualifiedName), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (dataTypeImplementation == null) {
    	return false;
    }
    registerObjectUnderModification(dataTypeImplementation);
    org.emftext.language.java.containers.CompilationUnit dataTypeImplementationCU = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDataTypeImplementationCU(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation), // correspondence source supplier
    	org.emftext.language.java.containers.CompilationUnit.class,
    	(org.emftext.language.java.containers.CompilationUnit _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (dataTypeImplementationCU == null) {
    	return false;
    }
    registerObjectUnderModification(dataTypeImplementationCU);
    // val updatedElement userExecution.getElement1(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU);
    userExecution.update0Element(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU);
    
    org.emftext.language.java.types.NamespaceClassifierReference namespaceClassifier = org.emftext.language.java.types.impl.TypesFactoryImpl.eINSTANCE.createNamespaceClassifierReference();
    notifyObjectCreated(namespaceClassifier);
    userExecution.updateNamespaceClassifierElement(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier);
    
    addCorrespondenceBetween(userExecution.getElement2(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier), userExecution.getElement3(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier), "");
    
    // val updatedElement userExecution.getElement4(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier);
    userExecution.update1Element(dataType, innerTypeReference, superTypeQualifiedName, dataTypeImplementation, dataTypeImplementationCU, namespaceClassifier);
    
    postprocessElements();
    
    return true;
  }
}
