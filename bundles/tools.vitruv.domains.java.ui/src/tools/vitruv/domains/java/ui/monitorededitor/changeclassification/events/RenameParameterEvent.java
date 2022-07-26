package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class RenameParameterEvent extends ChangeMethodParameterEvent {

	public final VariableDeclaration originalParam;
	public final VariableDeclaration changedParam;

	public RenameParameterEvent(VariableDeclaration original, VariableDeclaration renamed, int line) {
		super((MethodDeclaration) original.getParent(), (MethodDeclaration) renamed.getParent(), line);
		this.originalParam = original;
		this.changedParam = renamed;
	}

	@Override
	public String toString() {
		return "RenameParameterEvent [original=" + this.originalParam.toString().replace(";\n", "") + ", changed="
				+ this.changedParam.toString().replace(";\n", "") + ", line=" + this.line + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
