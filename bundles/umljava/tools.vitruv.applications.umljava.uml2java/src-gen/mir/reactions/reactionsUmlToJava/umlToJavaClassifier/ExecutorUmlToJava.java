package mir.reactions.reactionsUmlToJava.umlToJavaClassifier;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava(final UserInteracting userInteracting) {
    super(userInteracting,
    	new UmlDomainProvider().getDomain(), 
    	new JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPackageDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassifierDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlClassMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperClassDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlDataTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlDataTypeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlCollectionDataTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlCollectionDataTypeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceImplementerChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceImplementerChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlInterfaceCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlSuperInterfaceDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlEnumLiteralDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlElementVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlElementVisibilityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlNamedElementRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlNamedElementRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPrimitiveTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlPrimitiveTypeCreatedReaction(userInteracting));
  }
}
