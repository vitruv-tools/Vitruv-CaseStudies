package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.FieldDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class ChangeFieldTypeEvent extends ChangeFieldEvent {

	public ChangeFieldTypeEvent(FieldDeclaration original, FieldDeclaration changed, int line) {
		super(original, changed, line);
	}

	@Override
	public String toString() {
		return "ChangeFieldTypeEvent [original=" + this.original.toString().replace(";\n", "") + ", changed="
				+ this.changed.toString().replace(";\n", "") + ", line=" + this.line + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
