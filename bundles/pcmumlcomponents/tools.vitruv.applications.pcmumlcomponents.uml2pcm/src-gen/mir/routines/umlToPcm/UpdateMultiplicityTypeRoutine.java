package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateMultiplicityTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private UpdateMultiplicityTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmElement(final MultiplicityElement umlElement) {
      return umlElement;
    }
    
    public void callRoutine1(final MultiplicityElement umlElement, final EObject pcmElement, @Extension final RoutinesFacade _routinesFacade) {
      if ((umlElement instanceof Parameter)) {
        ParameterDirectionKind _direction = ((Parameter)umlElement).getDirection();
        boolean _tripleEquals = (_direction == ParameterDirectionKind.RETURN_LITERAL);
        if (_tripleEquals) {
          _routinesFacade.changeInterfaceOperationType(((Parameter)umlElement).getOperation(), ((Parameter)umlElement));
        } else {
          _routinesFacade.changeParameterType(((Parameter)umlElement));
        }
      }
      if ((umlElement instanceof Property)) {
        DataType _xifexpression = null;
        Type _type = ((Property)umlElement).getType();
        if ((_type instanceof DataType)) {
          Type _type_1 = ((Property)umlElement).getType();
          _xifexpression = ((DataType) _type_1);
        } else {
          _xifexpression = null;
        }
        final DataType umlType = _xifexpression;
        _routinesFacade.changePropertyType(((Property)umlElement), umlType);
      }
    }
  }
  
  public UpdateMultiplicityTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final MultiplicityElement umlElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.UpdateMultiplicityTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlElement = umlElement;
  }
  
  private MultiplicityElement umlElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateMultiplicityTypeRoutine with input:");
    getLogger().debug("   umlElement: " + this.umlElement);
    
    org.eclipse.emf.ecore.EObject pcmElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmElement(umlElement), // correspondence source supplier
    	org.eclipse.emf.ecore.EObject.class,
    	(org.eclipse.emf.ecore.EObject _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmElement == null) {
    	return false;
    }
    registerObjectUnderModification(pcmElement);
    userExecution.callRoutine1(umlElement, pcmElement, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
