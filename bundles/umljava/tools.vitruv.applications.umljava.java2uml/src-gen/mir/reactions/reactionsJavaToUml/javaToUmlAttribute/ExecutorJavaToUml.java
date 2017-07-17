package mir.reactions.reactionsJavaToUml.javaToUmlAttribute;

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
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInClassReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInEnumReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInEnumReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeTypeChangedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeFinalReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeNonFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeNonFinalReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInInterfaceReaction());
  }
}
