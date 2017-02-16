package mir.reactions.reactions5_1ToUML.pcmToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class Executor5_1ToUML extends AbstractReactionsExecutor {
  public Executor5_1ToUML(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI, org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPcmRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPcmRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.RenamedPcmRepositoryReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.RenamedPcmRepositoryReaction(userInteracting));
    this.addReaction(mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPcmComponentReaction.getExpectedChangeType(), new mir.reactions.reactions5_1ToUML.pcmToUml.CreatedPcmComponentReaction(userInteracting));
  }
}
