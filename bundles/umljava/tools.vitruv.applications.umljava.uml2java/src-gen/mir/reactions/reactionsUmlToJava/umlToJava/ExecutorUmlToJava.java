package mir.reactions.reactionsUmlToJava.umlToJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI, org.emftext.language.java.impl.JavaPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlClassCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlClassCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlPackageCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlPackageCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlPackageRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlPackageRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlClassifierPackageChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlClassifierPackageChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlPackageDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlPackageDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlClassifierRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlClassifierRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlClassifierDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlClassifierDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlClassMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlClassMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlClassMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlClassMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlElementVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlElementVisibilityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlSuperClassChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlSuperClassChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlSuperClassDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlSuperClassDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlEnumCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlEnumCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlEnumLiteralCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlEnumLiteralCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlEnumLiteralDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlEnumLiteralDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlNamedElementRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlNamedElementRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlDataTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlDataTypeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlCollectionDataTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlCollectionDataTypeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlInterfaceImplementerChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlInterfaceImplementerChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlImplementedInterfaceChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlImplementedInterfaceChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlImplementedInterfaceDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlImplementedInterfaceDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlInterfaceCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlInterfaceCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlSuperInterfaceChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlSuperInterfaceChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlSuperInterfaceDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlSuperInterfaceDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlMethodRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlMethodRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlClassMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlClassMethodDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlInterfaceMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlInterfaceMethodDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlParameterCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlParameterCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlParameterRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlParameterRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlParameterDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlParameterDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlParameterTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlParameterTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlMethodReturnTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlMethodReturnTypeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlMethodReturnTypeRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlMethodReturnTypeRemovedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlAttributeMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlFeatureMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlFeatureMadeStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlMethodMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlMethodMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.UmlInterfaceMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.UmlInterfaceMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJava.LogReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJava.LogReaction(userInteracting));
  }
}
