package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.util.ModifierUtil;

public class ChangeMethodModifiersEvent extends ChangeMethodSignatureEvent {

	public ChangeMethodModifiersEvent(MethodDeclaration original, MethodDeclaration renamed, int line) {
		super(original, renamed, line);
	}

	@Override
	public String toString() {
		String originalModifiers = ModifierUtil.toModifiersString(this.original.getModifiers());
		String changedModifiers = ModifierUtil.toModifiersString(this.renamed.getModifiers());

		return "ChangeMethodModifierEvent [original=" + originalModifiers + " "
				+ this.original.getName().getIdentifier() + ", changed=" + changedModifiers + " "
				+ this.renamed.getName().getIdentifier() + ", line=" + this.line + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
