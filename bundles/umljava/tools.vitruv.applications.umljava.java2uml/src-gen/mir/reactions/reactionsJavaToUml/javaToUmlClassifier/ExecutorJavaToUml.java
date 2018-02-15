package mir.reactions.reactionsJavaToUml.javaToUmlClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
class ExecutorJavaToUml extends AbstractReactionsExecutor {
  public ExecutorJavaToUml() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.javaToUmlClassifier.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompUnitDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaInterfaceCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitInsertedInPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitRemovedFromPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumerationImplementAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierMadeStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlClassifier"))));
  }
}
