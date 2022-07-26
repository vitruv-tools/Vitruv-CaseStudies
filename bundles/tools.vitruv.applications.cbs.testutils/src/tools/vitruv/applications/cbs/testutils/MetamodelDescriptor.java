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
		return fileExtensions.equals(fileExtensions);
	}
	
	@Override
	public int hashCode() {
		return fileExtensions.hashCode();
	}
}
