package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.core.resources.IResource;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class CreatePackageEvent extends PackageEvent {

	public CreatePackageEvent(String packageName, IResource iResource) {
		super(packageName, iResource);
	}

	@Override
	public String toString() {
		return "CreatePackageEvent [package=" + this.packageName + "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
