package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class ChangeMethodParameterEvent extends ChangeMethodSignatureEvent {

	public ChangeMethodParameterEvent(MethodDeclaration original, MethodDeclaration renamed, int line) {
		super(original, renamed, line);
	}

	@Override
	public String toString() {
		return "ChangeMethodParameterEvent [original=" + this.original.getName().getIdentifier() + ", changed="
				+ this.renamed.getName().getIdentifier() + ", line=" + this.line + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
