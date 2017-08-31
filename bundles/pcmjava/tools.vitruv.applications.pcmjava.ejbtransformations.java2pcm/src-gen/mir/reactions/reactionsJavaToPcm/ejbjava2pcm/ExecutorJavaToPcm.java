package mir.reactions.reactionsJavaToPcm.ejbjava2pcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToPcm extends AbstractReactionsExecutor {
  public ExecutorJavaToPcm() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedFirstPackageReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedFirstPackageReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedClassReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassAnnotationReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassAnnotationReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedInterfaceReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceAnnotationReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceAnnotationReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateAnnotationForFieldReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateAnnotationForFieldReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateImplementsReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateImplementsReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceMethodReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceMethodReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateParameterInInterfaceMethodReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateParameterInInterfaceMethodReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.ReturnTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.ReturnTypeCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldInDatatypeClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldInDatatypeClassReaction());
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassMethodInEjbClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassMethodInEjbClassReaction());
  }
}
