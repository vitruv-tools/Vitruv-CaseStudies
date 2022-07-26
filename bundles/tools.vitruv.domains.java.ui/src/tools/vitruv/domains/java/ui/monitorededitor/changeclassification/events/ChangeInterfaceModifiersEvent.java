package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;
import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events.util.ModifierUtil;

public class ChangeInterfaceModifiersEvent extends ChangeTypeModifiersEvent {

	public ChangeInterfaceModifiersEvent(TypeDeclaration original, TypeDeclaration changed) {
		super(original, changed);
	}

	@Override
	public String toString() {
		String originalModifiers = ModifierUtil.toModifiersString(this.original.getModifiers());
		String changedModifiers = ModifierUtil.toModifiersString(this.changed.getModifiers());

		return "ChangeInterfaceModifierEvent [original=" + originalModifiers + " "
				+ this.original.getName().getIdentifier() + ", changed=" + changedModifiers + " "
				+ this.changed.getName().getIdentifier() + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
