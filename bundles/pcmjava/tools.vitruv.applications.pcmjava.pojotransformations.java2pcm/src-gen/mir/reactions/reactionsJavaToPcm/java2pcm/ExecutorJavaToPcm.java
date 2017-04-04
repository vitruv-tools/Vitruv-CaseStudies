<<<<<<< HEAD:bundles/pcmjava/tools.vitruv.applications.pcmjava.pojotransformations.java2pcm/src-gen/mir/reactions/reactionsJavaTo5_1/java2pcm/ExecutorJavaTo5_1.java
package mir.reactions.reactionsJavaTo5_1.java2pcm;
=======
package mir.reactions.reactionsJavaToPcm.java2pcm;
>>>>>>> master:bundles/pcmjava/tools.vitruv.applications.pcmjava.pojotransformations.java2pcm/src-gen/mir/reactions/reactionsJavaToPcm/java2pcm/ExecutorJavaToPcm.java

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
<<<<<<< HEAD:bundles/pcmjava/tools.vitruv.applications.pcmjava.pojotransformations.java2pcm/src-gen/mir/reactions/reactionsJavaTo5_1/java2pcm/ExecutorJavaTo5_1.java
public class ExecutorJavaTo5_1 extends AbstractReactionsExecutor {
  public ExecutorJavaTo5_1(final UserInteracting userInteracting) {
=======
public class ExecutorJavaToPcm extends AbstractReactionsExecutor {
  public ExecutorJavaToPcm(final UserInteracting userInteracting) {
>>>>>>> master:bundles/pcmjava/tools.vitruv.applications.pcmjava.pojotransformations.java2pcm/src-gen/mir/reactions/reactionsJavaToPcm/java2pcm/ExecutorJavaToPcm.java
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.emftext.language.java.impl.JavaPackageImpl.eNS_URI, org.palladiosimulator.pcm.impl.PcmPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
  }
}
