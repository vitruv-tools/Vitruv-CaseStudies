package mir.reactions.reactionsJavaToPcm.ejbjava2pcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToPcm extends AbstractReactionsExecutor {
  public ExecutorJavaToPcm() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedFirstPackageReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedClassReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassAnnotationReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceAnnotationReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateAnnotationForFieldReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateImplementsReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceMethodReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateParameterInInterfaceMethodReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.ReturnTypeCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldInDatatypeClassReaction());
    this.addReaction(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassMethodInEjbClassReaction());
  }
}
