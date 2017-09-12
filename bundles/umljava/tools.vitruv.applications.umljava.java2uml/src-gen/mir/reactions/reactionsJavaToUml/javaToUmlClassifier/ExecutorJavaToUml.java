package mir.reactions.reactionsJavaToUml.javaToUmlClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToUml extends AbstractReactionsExecutor {
  public ExecutorJavaToUml() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompUnitDeletedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierDeletedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeAbstractReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonAbstractReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeFinalReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonFinalReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassChangedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassRemovedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementAddedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementRemovedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaInterfaceCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceAddedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceRemovedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageDeletedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitInsertedInPackageReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitRemovedFromPackageReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantDeletedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumerationImplementAddedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierMadeStaticReaction());
  }
}
