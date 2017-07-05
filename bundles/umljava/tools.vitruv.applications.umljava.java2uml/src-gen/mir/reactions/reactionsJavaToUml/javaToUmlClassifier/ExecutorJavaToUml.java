package mir.reactions.reactionsJavaToUml.javaToUmlClassifier;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToUml extends AbstractReactionsExecutor {
  public ExecutorJavaToUml() {
    super(new JavaDomainProvider().getDomain(), 
    	new UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompUnitDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompUnitDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassMadeNonFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperClassRemovedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementAddedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementAddedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassImplementRemovedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaInterfaceCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaInterfaceCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceAddedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceAddedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceRemovedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaSuperInterfaceRemovedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaPackageDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitInsertedInPackageReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitInsertedInPackageReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitRemovedFromPackageReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaCompilationUnitRemovedFromPackageReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumConstantDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumerationImplementAddedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaEnumerationImplementAddedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.JavaClassifierMadeStaticReaction(userInteracting));
  }
}
