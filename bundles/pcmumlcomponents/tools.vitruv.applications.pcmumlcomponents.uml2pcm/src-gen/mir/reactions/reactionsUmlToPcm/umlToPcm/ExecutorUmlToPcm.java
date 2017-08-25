package mir.reactions.reactionsUmlToPcm.umlToPcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorUmlToPcm extends AbstractReactionsExecutor {
  public ExecutorUmlToPcm() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.RenamedElementReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.RenamedElementReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedElementReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedElementReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityLowerValueReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityLowerValueReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperValueReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperValueReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityLowerReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityLowerReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedUmlModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedUmlModelReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedPrimitiveDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedPrimitiveDataTypeReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedDataTypeReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedPropertyForDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedPropertyForDataTypeReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedPropertyFromDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedPropertyFromDataTypeReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedPropertyTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedPropertyTypeReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedDataTypeReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceOperationReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceOperationReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.AddedInterfaceOperationParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.AddedInterfaceOperationParameterReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterTypeReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterNameReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterNameReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterDirectionReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterDirectionReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedParameterReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedParameterReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedComponentReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.AddedUsesRelationshipReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.AddedUsesRelationshipReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedUsesRelationshipInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedUsesRelationshipInterfaceReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedUsesRelationshipInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedUsesRelationshipInterfaceReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedUsesRelationshipReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedUsesRelationshipReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceRealizationRelationshipReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceRealizationRelationshipReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedInterfaceRealizationInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedInterfaceRealizationInterfaceReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedInterfaceRealizationInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedInterfaceRealizationInterfaceReaction());
    this.addReaction(mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedInterfaceRealizationRelationshipReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedInterfaceRealizationRelationshipReaction());
  }
}
