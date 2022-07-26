package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class RemoveMethodEvent extends ChangeClassifyingEvent implements HasLineInformation {

	public final MethodDeclaration method;
	public final TypeDeclaration typeAfterRemove;
	public final int line;

	public RemoveMethodEvent(TypeDeclaration typeAfterRemove, MethodDeclaration method, int line) {
		this.typeAfterRemove = typeAfterRemove;
		this.method = method;
		this.line = line;
	}

	@Override
	public String toString() {
		return "RemoveMethodEvent [method=" + this.method.getName().getIdentifier() + ", line=" + this.line + "]";
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
