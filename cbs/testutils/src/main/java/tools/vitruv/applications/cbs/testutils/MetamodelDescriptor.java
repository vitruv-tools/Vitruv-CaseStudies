package tools.vitruv.applications.cbs.testutils;

import java.util.HashSet;
import java.util.Set;

public class MetamodelDescriptor {
	public final String name;
	public final Set<String> fileExtensions;

	public MetamodelDescriptor(String name, Set<String> fileExtensions) {
		this.name = name;
		this.fileExtensions = new HashSet<>(fileExtensions);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MetamodelDescriptor) {
			return fileExtensions.equals(((MetamodelDescriptor) obj).fileExtensions);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return fileExtensions.hashCode();
	}

	public Set<String> getFileExtensions() {
		return new HashSet<>(fileExtensions);
	}

	public String getName() {
		return name;
	}
}
