package mir.reactions.reactionsPcmToUml.pcmToUml;

import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorPcmToUml extends AbstractReactionsExecutor {
  public ExecutorPcmToUml(final UserInteracting userInteracting) {
    super(userInteracting,
    	new PcmDomainProvider().getDomain(), 
    	new UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmComponentReaction.getExpectedChangeType(), new mir.reactions.reactionsPcmToUml.pcmToUml.CreatedPcmComponentReaction(userInteracting));
  }
}
