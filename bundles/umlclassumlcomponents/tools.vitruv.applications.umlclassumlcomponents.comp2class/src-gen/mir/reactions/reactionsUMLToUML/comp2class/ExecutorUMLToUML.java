package mir.reactions.reactionsUmlToUml.comp2class;

import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUmlToUml extends AbstractReactionsExecutor {
  public ExecutorUmlToUml(final UserInteracting userInteracting) {
    super(userInteracting,
    	new UmlDomainProvider().getDomain(), 
    	new UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.CreatedCompModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.CreatedCompModelReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.RenamedComponentModelReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.RenamedComponentModelReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.RenamedElementReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.RenamedElementReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.ElementVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.ElementVisibilityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.CreatedUmlComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.CreatedUmlComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.RenameComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.RenameComponentReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.DeletedCompReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.DeletedCompReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.CreatedDatatypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.CreatedDatatypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.AddedDataTypePropertyReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.AddedDataTypePropertyReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.ChangedDataTypePropertyReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.ChangedDataTypePropertyReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.AddedDataTypeOperationReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.AddedDataTypeOperationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.ChangedDataTypeOperationReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.ChangedDataTypeOperationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.AddedProvidedRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.AddedProvidedRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.AddedRequiredRoleReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.AddedRequiredRoleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.RemovedInterfaceRealizationReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.RemovedInterfaceRealizationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToUml.comp2class.RemovedUsageReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToUml.comp2class.RemovedUsageReaction(userInteracting));
  }
}
