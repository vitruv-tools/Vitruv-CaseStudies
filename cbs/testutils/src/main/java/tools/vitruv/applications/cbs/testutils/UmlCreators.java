package tools.vitruv.applications.cbs.testutils;

import java.nio.file.Path;
import java.util.Set;

import org.eclipse.uml2.uml.resource.UMLResource;

/**
 * Describes the UML metamodel and builds paths to UML files.
 */
public final class UmlCreators {
    public static final UmlCreators uml = new UmlCreators();

    private final MetamodelDescriptor metamodel = new MetamodelDescriptor("uml", Set.of("uml"));

    private UmlCreators() {
    }

    public MetamodelDescriptor getMetamodel() {
        return metamodel;
    }

    /**
     * Appends the UML file extension to the provided path.
     */
    public static Path uml(Path path) {
        return path.resolveSibling(umlExtension(path.getFileName().toString()));
    }

    /**
     * Appends the UML file extension to the provided path.
     */
    public static Path uml(String path) {
        return uml(Path.of(path));
    }

    /**
     * Appends the UML file extension to the provided file name.
     */
    public static String umlExtension(String string) {
        return string + "." + UMLResource.FILE_EXTENSION;
    }
}
