package mir.reactions.reactionsJavaToPcm.java2PcmMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToPcm extends AbstractReactionsExecutor {
  public ExecutorJavaToPcm() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaNamedElementRenamedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterDeletedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterNameChangedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.FieldCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.FieldTypeChangeReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ClassMethodCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.InterfaceMethodCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaReturnTypeChangedReaction());
  }
}
