package mir.reactions.reactionsJavaToPcm.java2PcmMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToPcm extends AbstractReactionsExecutor {
  public ExecutorJavaToPcm() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaNamedElementRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaNamedElementRenamedReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterDeletedReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterNameChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ParameterNameChangedReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.FieldCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.FieldCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.FieldTypeChangeReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.FieldTypeChangeReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.ClassMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.ClassMethodCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.InterfaceMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.InterfaceMethodCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaReturnTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.java2PcmMethod.JavaReturnTypeChangedReaction());
  }
}
