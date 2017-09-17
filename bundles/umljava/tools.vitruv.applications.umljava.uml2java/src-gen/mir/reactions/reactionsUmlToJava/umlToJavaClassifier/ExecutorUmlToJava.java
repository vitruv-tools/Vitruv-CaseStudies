package mir.reactions.reactionsUmlToJava.umlToJavaClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassCreatedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierRenamedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierDeletedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeFinalReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeAbstractReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassDeletedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceImplementerChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceDeletedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlDataTypeCreatedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceCreatedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceDeletedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumCreatedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralCreatedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralDeletedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageCreatedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageRenamedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageDeletedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPrimitiveTypeCreatedReaction());
  }
}
