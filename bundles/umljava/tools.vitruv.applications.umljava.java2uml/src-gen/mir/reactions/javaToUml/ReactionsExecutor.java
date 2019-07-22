package mir.reactions.javaToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.javaToUml.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeCreatedInClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlAttribute"))));
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeCreatedInEnumReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlAttribute"))));
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlAttribute"))));
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlAttribute"))));
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeMadeNonFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlAttribute"))));
    this.addReaction(new mir.reactions.javaToUmlAttribute.JavaAttributeCreatedInInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlAttribute"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaCompUnitCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaCompUnitDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassifierDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassMadeNonAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassMadeNonFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaSuperClassChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassImplementAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassImplementRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaInterfaceCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaSuperInterfaceAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaSuperInterfaceRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaPackageCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaPackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaCompilationUnitInsertedInPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaCompilationUnitRemovedFromPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaEnumCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaEnumConstantCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaEnumConstantDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaEnumerationImplementAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassifierMadeStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaClassMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaInterfaceMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaConstructorCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMemberDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMethodMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMethodMadeNonAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMethodMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMethodMadeNonFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaElementMadeStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMethodMadeNonStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaParameterCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaParameterTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaReturnTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaElementVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaElementMadePackagePrivateReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaNamedElementRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaCompilationUnitRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaClassMethodCreatedInInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaParameterMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUml.javaToUmlMethod"))));
  }
}
