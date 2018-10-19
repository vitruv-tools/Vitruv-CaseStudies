package mir.routines.umlToJavaTypePropagation;

import java.io.IOException;
import mir.routines.umlToJavaTypePropagation.RoutinesFacade;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper;
import tools.vitruv.domains.java.util.JavaModificationUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class PropagateTypedMultiplicityElementTypeChangedRoutine extends AbstractRepairRoutineRealization {
  private PropagateTypedMultiplicityElementTypeChangedRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final TypedElement uElement, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement, final ConcreteClassifier jType, final TypeReference defaultReference, @Extension final RoutinesFacade _routinesFacade) {
      TypeReference typeReference = UmlJavaTypePropagationHelper.createTypeReference(uElement.getType(), jType, defaultReference, this.userInteractor);
      if (((uMultiplicity.getLower() == 0) && (uMultiplicity.getUpper() == LiteralUnlimitedNatural.UNLIMITED))) {
        if ((typeReference == defaultReference)) {
          typeReference = JavaModificationUtil.createNamespaceClassifierReference(jElement.getObjectClass());
        }
        boolean _isCollectionTypeReference = UmlJavaTypePropagationHelper.isCollectionTypeReference(jElement.getTypeReference());
        if (_isCollectionTypeReference) {
          Classifier _classifier = UmlJavaTypePropagationHelper.getClassifier(jElement.getTypeReference());
          final ConcreteClassifier collectionClassifier = ((ConcreteClassifier) _classifier);
          typeReference = UmlJavaTypePropagationHelper.createCollectionTypeReference(collectionClassifier, typeReference);
        } else {
          final Class<?> collectionType = UmlJavaTypePropagationHelper.userSelectCollectionType(this.userInteractor);
          typeReference = UmlJavaTypePropagationHelper.createCollectionTypeReference(collectionType, typeReference);
        }
      }
      jElement.setTypeReference(typeReference);
      UmlJavaTypePropagationHelper.addJavaImport(jElement.getContainingCompilationUnit(), typeReference);
    }
    
    public boolean checkMatcherPrecondition1(final TypedElement uElement, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement, final ConcreteClassifier jType, final TypeReference defaultReference) {
      boolean _xblockexpression = false;
      {
        if ((uElement != uMultiplicity)) {
          throw new IllegalStateException(
            ("uml::TypedElement uElement and uml::MultiplicityElement uMultiplicity" + "have to be the same element (uml::Parameter or uml::Property) for this routine to work, but they were not."));
        }
        _xblockexpression = true;
      }
      return _xblockexpression;
    }
  }
  
  public PropagateTypedMultiplicityElementTypeChangedRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final TypedElement uElement, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement, final ConcreteClassifier jType, final TypeReference defaultReference) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaTypePropagation.PropagateTypedMultiplicityElementTypeChangedRoutine.ActionUserExecution(getExecutionState(), this);
    this.uElement = uElement;this.uMultiplicity = uMultiplicity;this.jElement = jElement;this.jType = jType;this.defaultReference = defaultReference;
  }
  
  private TypedElement uElement;
  
  private MultiplicityElement uMultiplicity;
  
  private org.emftext.language.java.types.TypedElement jElement;
  
  private ConcreteClassifier jType;
  
  private TypeReference defaultReference;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PropagateTypedMultiplicityElementTypeChangedRoutine with input:");
    getLogger().debug("   uElement: " + this.uElement);
    getLogger().debug("   uMultiplicity: " + this.uMultiplicity);
    getLogger().debug("   jElement: " + this.jElement);
    getLogger().debug("   jType: " + this.jType);
    getLogger().debug("   defaultReference: " + this.defaultReference);
    
    if (!userExecution.checkMatcherPrecondition1(uElement, uMultiplicity, jElement, jType, defaultReference)) {
    	return false;
    }
    userExecution.executeAction1(uElement, uMultiplicity, jElement, jType, defaultReference, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
