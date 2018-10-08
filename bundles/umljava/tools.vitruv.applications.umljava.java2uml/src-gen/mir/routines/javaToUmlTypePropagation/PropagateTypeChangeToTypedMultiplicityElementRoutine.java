package mir.routines.javaToUmlTypePropagation;

import java.io.IOException;
import mir.routines.javaToUmlTypePropagation.RoutinesFacade;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.util.UmlJavaTypePropagationHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class PropagateTypeChangeToTypedMultiplicityElementRoutine extends AbstractRepairRoutineRealization {
  private PropagateTypeChangeToTypedMultiplicityElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final TypedElement uTyped, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement, @Extension final RoutinesFacade _routinesFacade) {
      final TypeReference jType = jElement.getTypeReference();
      final boolean isCollectionReference = UmlJavaTypePropagationHelper.isCollectionTypeReference(jType);
      if (isCollectionReference) {
        uMultiplicity.setLower(0);
        uMultiplicity.setUpper(LiteralUnlimitedNatural.UNLIMITED);
        final TypeReference innerTypeRef = UmlJavaTypePropagationHelper.getInnerTypeRefOfCollectionReference(jType);
        Type _xifexpression = null;
        if ((innerTypeRef != null)) {
          _xifexpression = UmlJavaTypePropagationHelper.getUmlTypeFromReference(innerTypeRef, this.correspondenceModel);
        } else {
          _xifexpression = null;
        }
        final Type innerType = _xifexpression;
        uTyped.setType(innerType);
      } else {
        uTyped.setType(UmlJavaTypePropagationHelper.getUmlTypeFromReference(jType, this.correspondenceModel));
      }
    }
  }
  
  public PropagateTypeChangeToTypedMultiplicityElementRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final TypedElement uTyped, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlTypePropagation.PropagateTypeChangeToTypedMultiplicityElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.uTyped = uTyped;this.uMultiplicity = uMultiplicity;this.jElement = jElement;
  }
  
  private TypedElement uTyped;
  
  private MultiplicityElement uMultiplicity;
  
  private org.emftext.language.java.types.TypedElement jElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PropagateTypeChangeToTypedMultiplicityElementRoutine with input:");
    getLogger().debug("   uTyped: " + this.uTyped);
    getLogger().debug("   uMultiplicity: " + this.uMultiplicity);
    getLogger().debug("   jElement: " + this.jElement);
    
    userExecution.executeAction1(uTyped, uMultiplicity, jElement, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
