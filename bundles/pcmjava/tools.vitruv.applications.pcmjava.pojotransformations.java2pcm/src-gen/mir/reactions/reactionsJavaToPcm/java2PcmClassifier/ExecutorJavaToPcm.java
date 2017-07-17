package mir.reactions.reactionsJavaToPcm.java2PcmClassifier;

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
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmClassifier.PackageCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmClassifier.PackageCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmClassifier.CreateInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmClassifier.CreateInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmClassifier.CreateClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmClassifier.CreateClassReaction(userInteracting));
  }
}
