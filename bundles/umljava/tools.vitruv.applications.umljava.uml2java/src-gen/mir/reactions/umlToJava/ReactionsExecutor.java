package mir.reactions.umlToJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.umlToJava.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeCreatedInClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeCreatedInDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeDeletedFromClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeDeletedFromDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlLowerMultiplicityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlUpperMultiplicityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeCreatedInInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlInterfaceMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlClassCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlClassifierRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlClassifierDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlClassMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlClassMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlSuperClassAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlSuperClassDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlSuperClassReplacedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlInterfaceRealizationCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlInterfaceRealizationRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlInterfaceRealizationReplacedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlDataTypeCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlInterfaceCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlSuperInterfaceAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlSuperInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlSuperInterfaceReplacedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlEnumCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlEnumLiteralCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlEnumLiteralDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlModelCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlPackageCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlPackageRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlPackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaClassifier.UmlPrimitiveTypeCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaClassifier"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlMethodCreatedInDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlClassMethodDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlInterfaceMethodDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlDataTypeMethodDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlMethodReturnTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlFeatureMadeStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlMethodMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlInterfaceMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlMethodMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlElementVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlNamedElementRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlReturnParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDirectionChangedFromInReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDirectionChangedFromReturnReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDirectionChangedToInReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDirectionChangedToReturnReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDirectionKindChangedInvalidReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlInterfaceMethodVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlInterfaceMethodMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
  }
}
