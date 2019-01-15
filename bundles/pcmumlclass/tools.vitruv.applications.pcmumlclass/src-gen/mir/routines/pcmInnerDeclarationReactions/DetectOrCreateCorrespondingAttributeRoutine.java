package mir.routines.pcmInnerDeclarationReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmInnerDeclarationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingAttributeRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlComposite(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
      return pcmComposite;
    }
    
    public String getRetrieveTag1(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public String getRetrieveTag2(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlAttribute(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite) {
      return pcmAttribute;
    }
    
    public void callRoutine1(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite, final Optional<Property> umlAttribute, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlAttribute.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<Property, Boolean> _function = (Property it) -> {
          String _name = it.getName();
          String _entityName = pcmAttribute.getEntityName();
          return Boolean.valueOf(Objects.equal(_name, _entityName));
        };
        final Property umlAttributeCandidate = IterableExtensions.<Property>findFirst(umlComposite.getOwnedAttributes(), _function);
        if ((umlAttributeCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingAttribute(pcmAttribute, umlAttributeCandidate);
        } else {
          _routinesFacade.createCorrespondingAttribute(pcmAttribute, pcmComposite);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingAttributeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInnerDeclarationReactions.DetectOrCreateCorrespondingAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAttribute = pcmAttribute;this.pcmComposite = pcmComposite;
  }
  
  private InnerDeclaration pcmAttribute;
  
  private CompositeDataType pcmComposite;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingAttributeRoutine with input:");
    getLogger().debug("   pcmAttribute: " + this.pcmAttribute);
    getLogger().debug("   pcmComposite: " + this.pcmComposite);
    
    org.eclipse.uml2.uml.Class umlComposite = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComposite(pcmAttribute, pcmComposite), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmAttribute, pcmComposite), 
    	false // asserted
    	);
    if (umlComposite == null) {
    	return false;
    }
    registerObjectUnderModification(umlComposite);
    	Optional<org.eclipse.uml2.uml.Property> umlAttribute = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlAttribute(pcmAttribute, pcmComposite, umlComposite), // correspondence source supplier
    		org.eclipse.uml2.uml.Property.class,
    		(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmAttribute, pcmComposite, umlComposite), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlAttribute.isPresent() ? umlAttribute.get() : null);
    userExecution.callRoutine1(pcmAttribute, pcmComposite, umlComposite, umlAttribute, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
