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
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedInDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedInDataTypeReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlClassMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlClassMethodDeletedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodDeletedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlDataTypeMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlDataTypeMethodDeletedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodReturnTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodReturnTypeChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlFeatureMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlFeatureMadeStaticReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeAbstractReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodCreatedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeFinalReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlElementVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlElementVisibilityChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlNamedElementRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlNamedElementRenamedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterCreatedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDeletedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterTypeChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionKindChangedInvalidReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionKindChangedInvalidReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodVisibilityChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodMadeFinalReaction());
  }
}
