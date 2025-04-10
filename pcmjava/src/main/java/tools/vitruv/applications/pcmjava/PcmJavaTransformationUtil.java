package tools.vitruv.applications.pcmjava;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.emftext.language.java.containers.Package;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

@Utility
public final class PcmJavaTransformationUtil {
    public static String getCorrespondingPackageName(Entity entity) {
        return StringExtensions.toFirstLower(entity.getEntityName());
    }

    // Method to check if the package corresponds to the given entity.
    public static boolean isPackageFor(Package pkg, Entity entity) {
        // Ensure that both the package and entity are not null
        if (pkg == null || entity == null) {
            return false;
        }

        // If the entity is a Repository or System, we check if the package has no namespaces.
        if (entity instanceof Repository || entity instanceof System) {
            return pkg.getNamespaces().isEmpty();
        }

        // Otherwise, compare the lowercased entity name with the lowercased package name.
        return Objects.equals(pkg.getName().toLowerCase(), entity.getEntityName().toLowerCase());
    }

    private PcmJavaTransformationUtil() {
    }
}
