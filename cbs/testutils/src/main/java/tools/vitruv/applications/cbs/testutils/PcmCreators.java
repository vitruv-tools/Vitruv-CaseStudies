package tools.vitruv.applications.cbs.testutils;

import static com.google.common.base.Preconditions.checkArgument;
import static tools.vitruv.applications.util.temporary.pcm.PcmNamespace.REPOSITORY_FILE_EXTENSION;
import static tools.vitruv.applications.util.temporary.pcm.PcmNamespace.SYSTEM_FILE_EXTENSION;

import java.nio.file.Path;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.repository.RepositoryPackage;

/**
 * Describes the PCM metamodel and builds paths to PCM files.
 */
public final class PcmCreators {
    public static final PcmCreators pcm = new PcmCreators();

    private final MetamodelDescriptor metamodel = new MetamodelDescriptor("pcm", Set.of("repository", "system"));

    private PcmCreators() {
    }

    public MetamodelDescriptor getMetamodel() {
        return metamodel;
    }

    /**
     * Creates an instance of the PCM repository metaclass with the same name as the provided interface, e.g.
     * {@code createRepositoryElement(BasicComponent.class)}. Use the {@link RepositoryFactory} directly if the
     * metaclass is known statically.
     */
    public static <T extends EObject> T createRepositoryElement(Class<T> type) {
        String className = type.getSimpleName();
        EClassifier classifier = RepositoryPackage.eINSTANCE.getEClassifier(className);
        checkArgument(classifier instanceof EClass, "%s is not a metaclass of the PCM repository package!",
                className);
        return type.cast(RepositoryFactory.eINSTANCE.create((EClass) classifier));
    }

    /**
     * Appends the PCM repository file extension to the provided path.
     */
    public static Path repository(Path path) {
        return path.resolveSibling(path.getFileName() + "." + REPOSITORY_FILE_EXTENSION);
    }

    /**
     * Appends the PCM repository file extension to the provided path.
     */
    public static Path repository(CharSequence path) {
        return repository(Path.of(path.toString()));
    }

    /**
     * Appends the PCM system file extension to the provided path.
     */
    public static Path system(Path path) {
        return path.resolveSibling(path.getFileName() + "." + SYSTEM_FILE_EXTENSION);
    }

    /**
     * Appends the PCM system file extension to the provided path.
     */
    public static Path system(CharSequence path) {
        return system(Path.of(path.toString()));
    }
}
