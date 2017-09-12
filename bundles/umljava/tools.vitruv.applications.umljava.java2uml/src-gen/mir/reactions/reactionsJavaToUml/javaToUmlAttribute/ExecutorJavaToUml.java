package mir.reactions.reactionsJavaToUml.javaToUmlAttribute;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToUml extends AbstractReactionsExecutor {
  public ExecutorJavaToUml() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInClassReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInEnumReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeTypeChangedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeFinalReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeNonFinalReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInInterfaceReaction());
  }
}
