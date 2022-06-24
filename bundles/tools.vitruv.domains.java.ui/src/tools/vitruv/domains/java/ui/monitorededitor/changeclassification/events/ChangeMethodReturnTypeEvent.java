package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class ChangeMethodReturnTypeEvent extends ChangeMethodSignatureEvent {

	public ChangeMethodReturnTypeEvent(MethodDeclaration original, MethodDeclaration renamed, int line) {
		super(original, renamed, line);
	}

	@Override
	public String toString() {
		return "ChangeMethodReturnTypeEvent [original=" + original.getName().getIdentifier() + ", changed="
				+ renamed.getName().getIdentifier() + ", line=" + line + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
