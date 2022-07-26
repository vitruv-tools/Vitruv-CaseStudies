package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class RenameInterfaceEvent extends RenameTypeEvent {

	public RenameInterfaceEvent(TypeDeclaration original, TypeDeclaration renamed) {
		super(original, renamed);
	}

	@Override
	public String toString() {
		return "RenameInterfaceEvent [original=" + original.getName().getIdentifier() + ", renamed="
				+ renamed.getName().getIdentifier() + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
