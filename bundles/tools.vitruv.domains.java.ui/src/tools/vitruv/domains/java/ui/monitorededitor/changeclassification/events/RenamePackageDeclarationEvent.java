package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.PackageDeclaration;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class RenamePackageDeclarationEvent extends ChangeClassifyingEvent {

	public final PackageDeclaration packageDeclaration;

	public RenamePackageDeclarationEvent(PackageDeclaration packageDeclaration) {
		this.packageDeclaration = packageDeclaration;
	}

	@Override
	public String toString() {
		return "AddPackageDeclarationEvent [package=" + this.packageDeclaration.getName().getFullyQualifiedName() + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
