package mir.routines.pcmInnerDeclarationReactions;

import java.io.IOException;
import mir.routines.pcmInnerDeclarationReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCorrespondingAttributeRoutine extends AbstractRepairRoutineRealization {
  private RemoveCorrespondingAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite, final Property umlAttribute) {
      return umlComposite;
    }
    
    public void update0Element(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite, final Property umlAttribute) {
      EList<Property> _ownedAttributes = umlComposite.getOwnedAttributes();
      _ownedAttributes.remove(umlAttribute);
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
  }
  
  public RemoveCorrespondingAttributeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInnerDeclarationReactions.RemoveCorrespondingAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAttribute = pcmAttribute;this.pcmComposite = pcmComposite;
  }
  
  private InnerDeclaration pcmAttribute;
  
  private CompositeDataType pcmComposite;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondingAttributeRoutine with input:");
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
    org.eclipse.uml2.uml.Property umlAttribute = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlAttribute(pcmAttribute, pcmComposite, umlComposite), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmAttribute, pcmComposite, umlComposite), 
    	false // asserted
    	);
    if (umlAttribute == null) {
    	return false;
    }
    registerObjectUnderModification(umlAttribute);
    // val updatedElement userExecution.getElement1(pcmAttribute, pcmComposite, umlComposite, umlAttribute);
    userExecution.update0Element(pcmAttribute, pcmComposite, umlComposite, umlAttribute);
    
    postprocessElements();
    
    return true;
  }
}
