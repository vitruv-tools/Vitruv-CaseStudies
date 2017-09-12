package mir.reactions.reactionsUmlToJava.umlToJavaMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedInDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlClassMethodDeletedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodDeletedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlDataTypeMethodDeletedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodReturnTypeChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlFeatureMadeStaticReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeAbstractReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodCreatedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeFinalReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlElementVisibilityChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlNamedElementRenamedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterCreatedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDeletedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterTypeChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionKindChangedInvalidReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodVisibilityChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodMadeFinalReaction());
  }
}
