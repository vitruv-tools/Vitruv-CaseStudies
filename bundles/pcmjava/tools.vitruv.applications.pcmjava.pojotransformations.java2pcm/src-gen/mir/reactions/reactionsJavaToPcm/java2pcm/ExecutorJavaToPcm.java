package mir.reactions.reactionsJavaToPcm.java2pcm;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToPcm extends AbstractReactionsExecutor {
  public ExecutorJavaToPcm() {
    super(new JavaDomainProvider().getDomain(), 
    	new PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2pcm.CreatePackageReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2pcm.CreatePackageReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2pcm.CreateSubPackageReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2pcm.CreateSubPackageReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2pcm.CreateInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2pcm.CreateInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2pcm.CreateClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2pcm.CreateClassReaction(userInteracting));
  }
}
