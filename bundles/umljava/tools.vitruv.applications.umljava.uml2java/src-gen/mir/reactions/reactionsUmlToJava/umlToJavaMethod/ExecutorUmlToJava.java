package mir.reactions.reactionsUmlToJava.umlToJavaMethod;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava() {
    super(new UmlDomainProvider().getDomain(), 
    	new JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedInDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedInDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlClassMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlClassMethodDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlDataTypeMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlDataTypeMethodDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodReturnTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodReturnTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlFeatureMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlFeatureMadeStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlElementVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlElementVisibilityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlNamedElementRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlNamedElementRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionKindChangedInvalidReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionKindChangedInvalidReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodVisibilityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodMadeFinalReaction(userInteracting));
  }
}
