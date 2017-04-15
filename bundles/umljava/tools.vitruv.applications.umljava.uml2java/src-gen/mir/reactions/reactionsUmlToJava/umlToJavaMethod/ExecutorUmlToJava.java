package mir.reactions.reactionsUmlToJava.umlToJavaMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI, org.emftext.language.java.impl.JavaPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlClassMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlClassMethodDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodReturnTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodReturnTypeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodReturnTypeRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodReturnTypeRemovedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlFeatureMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlFeatureMadeStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlUpperMuliplicityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlUpperMuliplicityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlLowerMuliplicityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlLowerMuliplicityChangedReaction(userInteracting));
  }
}
