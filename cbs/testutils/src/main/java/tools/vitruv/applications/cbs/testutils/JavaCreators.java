package tools.vitruv.applications.cbs.testutils;

import java.nio.file.Path;
import java.util.Set;

/**
 * Describes the Java (JaMoPP) metamodel and builds paths to Java files.
 */
public final class JavaCreators {
    public static final JavaCreators java = new JavaCreators();

    private final MetamodelDescriptor metamodel = new MetamodelDescriptor("java", Set.of("java"));

    private JavaCreators() {
    }

    public MetamodelDescriptor getMetamodel() {
        return metamodel;
    }

    /**
     * Appends the {@code .java} file extension to the provided path.
     */
    public static Path java(Path path) {
        return path.resolveSibling(path.getFileName() + ".java");
    }

    /**
     * Appends the {@code .java} file extension to the provided path.
     */
    public static Path java(String path) {
        return java(Path.of(path));
    }
}
