package mir.routines.pcmInnerDeclarationReactions;

import java.io.IOException;
import mir.routines.pcmInnerDeclarationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingAttributeRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateUmlAttributeElement(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite, final Property umlAttribute) {
      umlAttribute.setName(pcmAttribute.getEntityName());
    }
    
    public EObject getElement1(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite, final Property umlAttribute) {
      return pcmAttribute;
    }
    
    public EObject getCorrepondenceSourceUmlComposite(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
      return pcmComposite;
    }
    
    public EObject getCorrepondenceSource1(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite) {
      return pcmAttribute;
    }
    
    public String getRetrieveTag1(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
      return TagLiterals.COMPOSITE_DATATYPE__CLASS;
    }
    
    public String getRetrieveTag2(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public EObject getElement2(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite, final Property umlAttribute) {
      return umlAttribute;
    }
    
    public String getTag1(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite, final Property umlAttribute) {
      return TagLiterals.INNER_DECLARATION__PROPERTY;
    }
    
    public void callRoutine1(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite, final org.eclipse.uml2.uml.Class umlComposite, final Property umlAttribute, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeTypeOfCorrespondingAttribute(pcmAttribute, pcmAttribute.getDatatype_InnerDeclaration());
    }
  }
  
  public CreateCorrespondingAttributeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInnerDeclarationReactions.CreateCorrespondingAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAttribute = pcmAttribute;this.pcmComposite = pcmComposite;
  }
  
  private InnerDeclaration pcmAttribute;
  
  private CompositeDataType pcmComposite;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingAttributeRoutine with input:");
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
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmAttribute, pcmComposite, umlComposite), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmAttribute, pcmComposite, umlComposite)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Property umlAttribute = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createProperty();
    notifyObjectCreated(umlAttribute);
    userExecution.updateUmlAttributeElement(pcmAttribute, pcmComposite, umlComposite, umlAttribute);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmAttribute, pcmComposite, umlComposite, umlAttribute), userExecution.getElement2(pcmAttribute, pcmComposite, umlComposite, umlAttribute), userExecution.getTag1(pcmAttribute, pcmComposite, umlComposite, umlAttribute));
    
    userExecution.callRoutine1(pcmAttribute, pcmComposite, umlComposite, umlAttribute, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
