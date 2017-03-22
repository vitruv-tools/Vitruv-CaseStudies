package mir.reactions.reactionsUMLToJava.umlToJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUMLToJava extends AbstractReactionsExecutor {
  public ExecutorUMLToJava(final UserInteracting userInteracting) {
    super(userInteracting, new tools.vitruv.framework.util.datatypes.MetamodelPair(org.eclipse.uml2.uml.internal.impl.UMLPackageImpl.eNS_URI, org.emftext.language.java.impl.JavaPackageImpl.eNS_URI));
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlClassCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlClassCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlClassRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlClassRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlClassDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlClassDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlClassMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlClassMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlClassMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlClassMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlClassVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlClassVisibilityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlSuperClassChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlSuperClassChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlSuperClassDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlSuperClassDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceImplementCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceImplementCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceImplementDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceImplementDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlSuperInterfaceChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlSuperInterfaceChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlSuperInterfaceDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlSuperInterfaceDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlClassMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlClassMethodDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceMethodDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceMethodDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlParameterCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlParameterCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlParameterRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlParameterRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlParameterDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlParameterDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlParameterTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlParameterTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodReturnTypeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodReturnTypeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodReturnTypeRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodReturnTypeRemovedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlFeatureMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlFeatureMadeStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlAttributeVisibilityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlMethodVisibilityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.UmlInterfaceMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUMLToJava.umlToJava.LogReaction.getExpectedChangeType(), new mir.reactions.reactionsUMLToJava.umlToJava.LogReaction(userInteracting));
  }
}
