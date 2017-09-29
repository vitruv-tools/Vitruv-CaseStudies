package mir.reactions.reactionsUmlToUml.comp2class;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorUmlToUml extends AbstractReactionsExecutor {
  public ExecutorUmlToUml() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.CreatedCompModelReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.RenamedComponentModelReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.RenamedElementReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.ElementVisibilityChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.CreatedUmlComponentReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.RenameComponentReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.DeletedCompReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.CreatedDatatypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.AddedDataTypePropertyReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.ChangedDataTypePropertyReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.AddedDataTypeOperationReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.ChangedDataTypeOperationReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.AddedProvidedRoleReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.AddedRequiredRoleReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.RemovedInterfaceRealizationReaction());
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.RemovedUsageReaction());
  }
}
