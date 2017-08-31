package mir.reactions.reactionsUmlToJava.umlToJavaClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassCreatedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierRenamedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierDeletedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeFinalReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeAbstractReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassDeletedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceImplementerChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceImplementerChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceDeletedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlDataTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlDataTypeCreatedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceCreatedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceDeletedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumCreatedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralCreatedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralDeletedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageCreatedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageRenamedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageDeletedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPrimitiveTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPrimitiveTypeCreatedReaction());
  }
}
