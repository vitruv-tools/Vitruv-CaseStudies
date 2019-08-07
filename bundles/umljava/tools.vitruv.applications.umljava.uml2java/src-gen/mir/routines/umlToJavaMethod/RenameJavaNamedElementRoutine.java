package mir.routines.umlToJavaMethod;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaNamedElementRoutine extends AbstractRepairRoutineRealization {
  private RenameJavaNamedElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final NamedElement uElem, final String name, final Optional<org.emftext.language.java.commons.NamedElement> jElem, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = jElem.isPresent();
      if (_isPresent) {
        org.emftext.language.java.commons.NamedElement _get = jElem.get();
        _get.setName(name);
      } else {
        if ((uElem instanceof Parameter)) {
          ParameterDirectionKind _direction = ((Parameter)uElem).getDirection();
          boolean _equals = Objects.equal(_direction, ParameterDirectionKind.IN_LITERAL);
          if (_equals) {
            _routinesFacade.createJavaParameter(((Parameter)uElem).getOperation(), ((Parameter)uElem));
          }
        }
      }
    }
    
    public EObject getCorrepondenceSourceJElem(final NamedElement uElem, final String name) {
      return uElem;
    }
  }
  
  public RenameJavaNamedElementRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement uElem, final String name) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.RenameJavaNamedElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.uElem = uElem;this.name = name;
  }
  
  private NamedElement uElem;
  
  private String name;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaNamedElementRoutine with input:");
    getLogger().debug("   uElem: " + this.uElem);
    getLogger().debug("   name: " + this.name);
    
    	Optional<org.emftext.language.java.commons.NamedElement> jElem = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceJElem(uElem, name), // correspondence source supplier
    		org.emftext.language.java.commons.NamedElement.class,
    		(org.emftext.language.java.commons.NamedElement _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(jElem.isPresent() ? jElem.get() : null);
    userExecution.executeAction1(uElem, name, jElem, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
