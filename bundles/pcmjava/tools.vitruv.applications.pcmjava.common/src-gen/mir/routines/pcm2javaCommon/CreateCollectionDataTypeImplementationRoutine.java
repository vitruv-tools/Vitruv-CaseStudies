package mir.routines.pcm2javaCommon;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaHelper;
import tools.vitruv.domains.java.util.JavaModificationUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.WindowModality;

@SuppressWarnings("all")
public class CreateCollectionDataTypeImplementationRoutine extends AbstractRepairRoutineRealization {
  private CreateCollectionDataTypeImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceInnerTypeClass(final CollectionDataType dataType) {
      DataType _innerType_CollectionDataType = dataType.getInnerType_CollectionDataType();
      return _innerType_CollectionDataType;
    }
    
    public boolean getCorrespondingModelElementsPreconditionDatatypesPackage(final CollectionDataType dataType, final Optional<org.emftext.language.java.classifiers.Class> innerTypeClass, final org.emftext.language.java.containers.Package datatypesPackage) {
      String _name = datatypesPackage.getName();
      boolean _equals = Objects.equal(_name, "datatypes");
      return _equals;
    }
    
    public EObject getCorrepondenceSourceDatatypesPackage(final CollectionDataType dataType, final Optional<org.emftext.language.java.classifiers.Class> innerTypeClass) {
      Repository _repository__DataType = dataType.getRepository__DataType();
      return _repository__DataType;
    }
    
    public void callRoutine1(final CollectionDataType dataType, final Optional<org.emftext.language.java.classifiers.Class> innerTypeClass, final org.emftext.language.java.containers.Package datatypesPackage, @Extension final RoutinesFacade _routinesFacade) {
      final TypeReference innerTypeRef = Pcm2JavaHelper.createTypeReference(dataType.getInnerType_CollectionDataType(), innerTypeClass);
      TypeReference innerTypeClassOrWrapper = innerTypeRef;
      if ((innerTypeRef instanceof PrimitiveType)) {
        innerTypeClassOrWrapper = JavaModificationUtil.getWrapperTypeReferenceForPrimitiveType(innerTypeRef);
      }
      Set<Class<?>> collectionDataTypes = new HashSet<Class<?>>();
      Iterables.<Class<?>>addAll(collectionDataTypes, Collections.<Class<? extends AbstractCollection>>unmodifiableList(CollectionLiterals.<Class<? extends AbstractCollection>>newArrayList(ArrayList.class, LinkedList.class, Vector.class, Stack.class, HashSet.class)));
      int _size = collectionDataTypes.size();
      final List<String> collectionDataTypeNames = new ArrayList<String>(_size);
      for (final Class<?> collectionDataType : collectionDataTypes) {
        collectionDataTypeNames.add(collectionDataType.getName());
      }
      final String selectTypeMsg = "Please select type (or interface) that should be used for the type";
      final int selectedType = (this.userInteractor.getSingleSelectionDialogBuilder().message(selectTypeMsg).choices(((String[])Conversions.unwrapArray(collectionDataTypeNames, String.class))).windowModality(WindowModality.MODAL).startInteraction()).intValue();
      final Set<Class<?>> _converted_collectionDataTypes = (Set<Class<?>>)collectionDataTypes;
      final Class<?> selectedClass = ((Class<?>[])Conversions.unwrapArray(_converted_collectionDataTypes, Class.class))[selectedType];
      _routinesFacade.createJavaClass(dataType, datatypesPackage, dataType.getEntityName());
      _routinesFacade.addSuperTypeToDataType(dataType, innerTypeClassOrWrapper, selectedClass.getName());
    }
  }
  
  public CreateCollectionDataTypeImplementationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType dataType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.CreateCollectionDataTypeImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.dataType = dataType;
  }
  
  private CollectionDataType dataType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCollectionDataTypeImplementationRoutine with input:");
    getLogger().debug("   dataType: " + this.dataType);
    
    	Optional<org.emftext.language.java.classifiers.Class> innerTypeClass = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInnerTypeClass(dataType), // correspondence source supplier
    		org.emftext.language.java.classifiers.Class.class,
    		(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(innerTypeClass.isPresent() ? innerTypeClass.get() : null);
    org.emftext.language.java.containers.Package datatypesPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDatatypesPackage(dataType, innerTypeClass), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> userExecution.getCorrespondingModelElementsPreconditionDatatypesPackage(dataType, innerTypeClass, _element), // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (datatypesPackage == null) {
    	return false;
    }
    registerObjectUnderModification(datatypesPackage);
    userExecution.callRoutine1(dataType, innerTypeClass, datatypesPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
