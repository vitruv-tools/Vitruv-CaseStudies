package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class AddMethodEvent extends ChangeClassifyingEvent implements HasLineInformation {

	public final TypeDeclaration typeBeforeAdd;
	public final MethodDeclaration method;
	public final int line;

	public AddMethodEvent(TypeDeclaration typeBeforeAdd, MethodDeclaration method, int line) {
		this.typeBeforeAdd = typeBeforeAdd;
		this.method = method;
		this.line = line;
	}

	@Override
	public String toString() {
		return "AddMethodEvent [method=" + this.method.getName().getIdentifier() + ", line=" + this.line + "]";
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
