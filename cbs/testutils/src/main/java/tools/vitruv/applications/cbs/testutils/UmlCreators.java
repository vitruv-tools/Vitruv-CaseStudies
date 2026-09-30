package tools.vitruv.applications.cbs.testutils;

import java.nio.file.Path;
import java.util.Set;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.resource.UMLResource;

public class UmlCreators extends FactoryCreators {
	public static final UmlCreators uml = new UmlCreators();
	private final MetamodelDescriptor metamodel = new MetamodelDescriptor("uml", Set.of("uml"));

	public UmlCreators() {
		super(UMLFactory.eINSTANCE);
	}

	public MetamodelDescriptor getMetamodel() {
		return metamodel;
	}

	public static Path uml(Path path) {
		return path.resolveSibling(umlExtension(path.getFileName().toString()));
	}

	public static Path uml(String path) {
		return uml(Path.of(path));
	}

	public static String umlExtension(String string) {
		return string + "." + UMLResource.FILE_EXTENSION;
	}
}
