package mir.reactions.reactionsJavaToPcm.java2PcmMethod;

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
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaNamedElementRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaNamedElementRenamedReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.CreateClassMethodReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.CreateClassMethodReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.CreateInterfaceMethodReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.CreateInterfaceMethodReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaReturnTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaReturnTypeChangedReaction());
  }
}
