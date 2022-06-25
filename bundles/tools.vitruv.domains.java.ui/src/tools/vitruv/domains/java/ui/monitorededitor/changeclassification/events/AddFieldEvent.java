package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class AddFieldEvent extends ChangeClassifyingEvent implements HasLineInformation {

	public final TypeDeclaration typeBeforeAdd;
	public final FieldDeclaration field;
	public final VariableDeclarationFragment fieldFragment;
	public final int line;

	public AddFieldEvent(TypeDeclaration typeBeforeAdd, VariableDeclarationFragment fieldFragment, int line) {
		this.typeBeforeAdd = typeBeforeAdd;
		this.field = (FieldDeclaration) fieldFragment.getParent();
		this.fieldFragment = fieldFragment;
		this.line = line;
	}

	@Override
	public String toString() {
		return "AddFieldEvent [field=" + this.field.toString().replace(";\n", "") + ", fragment=" + this.fieldFragment
				+ ", line=" + this.line + "]";
	}

	@Override
	public int getLine() {
		return this.line;
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
