package mir.reactions.javaToUmlClassifier;

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
    return new mir.routines.javaToUmlClassifier.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaCompUnitCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaCompUnitDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassifierDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassMadeNonAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassMadeNonFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaSuperClassChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassImplementAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassImplementRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaInterfaceCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaSuperInterfaceAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaSuperInterfaceRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaPackageCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaPackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaCompilationUnitInsertedInPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaCompilationUnitRemovedFromPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaEnumCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaEnumConstantCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaEnumConstantDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaEnumerationImplementAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
    this.addReaction(new mir.reactions.javaToUmlClassifier.JavaClassifierMadeStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("javaToUmlClassifier"))));
  }
}
