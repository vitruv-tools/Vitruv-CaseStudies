package mir.reactions.reactionsUmlToJava.umlToJavaAttribute;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI, org.emftext.language.java.impl.JavaPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeTypeChangedReaction(userInteracting));
  }
}
