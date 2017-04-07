package mir.reactions.reactionsJavaToPcm.ejbjava2pcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaToPcm extends AbstractReactionsExecutor {
  public ExecutorJavaToPcm(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.emftext.language.java.impl.JavaPackageImpl.eNS_URI, org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedFirstPackageReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedFirstPackageReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassAnnotationReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassAnnotationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreatedInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceAnnotationReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceAnnotationReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateAnnotationForFieldReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateAnnotationForFieldReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateImplementsReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateImplementsReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceMethodReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateInterfaceMethodReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateParameterInInterfaceMethodReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateParameterInInterfaceMethodReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.ReturnTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.ReturnTypeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldInDatatypeClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateFieldInDatatypeClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassMethodInEjbClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassMethodInEjbClassReaction(userInteracting));
  }
}
