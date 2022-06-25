package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

public class RenamePackageEvent extends ChangeClassifyingEvent {

	public final String originalPackageName;
	public final String renamedPackageName;
	public final IResource originalIResource;
	public final IResource renamedIResource;

	/**
	 * Creates a rename package event Note: since the transformation need to have
	 * *.java files we use the package-info.java file as original resource for the
	 * change
	 *
	 * @param originalPackageName
	 * @param renamedPackageName
	 * @param originalIResource
	 * @param renamedIResource
	 */
	public RenamePackageEvent(final String originalPackageName, final String renamedPackageName,
			final IResource originalIResource, final IResource renamedIResource) {
		this.originalPackageName = originalPackageName;
		this.renamedPackageName = renamedPackageName;
		this.originalIResource = this.ensurePackageInfoFile(originalIResource);
		this.renamedIResource = this.ensurePackageInfoFile(renamedIResource);
	}

	private IResource ensurePackageInfoFile(final IResource givenResource) {
		if (givenResource instanceof IFolder) {
			final IFolder iFolder = (IFolder) givenResource;
			final IFile iFile = iFolder.getFile("package-info.java");
			return iFile;
		}
		// already a file
		return givenResource;

	}

	@Override
	public String toString() {
		return "RenamePackageEvent [original=" + this.originalPackageName + ", renamed=" + this.renamedPackageName
				+ "]";
	}

	@Override
	public <T> T accept(final ChangeEventVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
