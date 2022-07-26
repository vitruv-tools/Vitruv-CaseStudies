package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.core.resources.IResource;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class DeletePackageEvent extends PackageEvent {

	public DeletePackageEvent(String packageName, IResource iResource) {
		super(packageName, iResource);
	}

	@Override
	public String toString() {
		return "DeletePackageEvent [package=" + this.packageName + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
