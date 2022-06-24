package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.PackageDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class ChangePackageDeclarationEvent extends ChangeClassifyingEvent {

	public final PackageDeclaration original;
	public final PackageDeclaration changed;

	public ChangePackageDeclarationEvent(PackageDeclaration original, PackageDeclaration changed) {
		this.original = original;
		this.changed = changed;
	}

	@Override
	public String toString() {
		return "ChangePackageDeclarationEvent [original=" + this.original.getName() + ", renamed="
				+ this.changed.getName() + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
