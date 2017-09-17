package mir.reactions.reactionsUmlToPcm.umlToPcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorUmlToPcm extends AbstractReactionsExecutor {
  public ExecutorUmlToPcm() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.RenamedElementReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedElementReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityLowerValueReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperValueReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityLowerReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedUmlModelReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedPrimitiveDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedPropertyForDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedPropertyFromDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedPropertyTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceOperationReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.AddedInterfaceOperationParameterReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterNameReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterDirectionReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedParameterReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedComponentReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.AddedUsesRelationshipReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedUsesRelationshipInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedUsesRelationshipInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedUsesRelationshipReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceRealizationRelationshipReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedInterfaceRealizationInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedInterfaceRealizationInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedInterfaceRealizationRelationshipReaction());
  }
}
