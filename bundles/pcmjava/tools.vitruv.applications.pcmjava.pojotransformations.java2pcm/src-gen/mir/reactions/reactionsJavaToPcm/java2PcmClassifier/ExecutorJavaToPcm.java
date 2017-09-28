package mir.reactions.reactionsJavaToPcm.java2PcmClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToPcm extends AbstractReactionsExecutor {
  public ExecutorJavaToPcm() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmClassifier.PackageCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmClassifier.InterfaceCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmClassifier.ClassCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmClassifier.TypeReferenceCreatedReaction());
  }
}
