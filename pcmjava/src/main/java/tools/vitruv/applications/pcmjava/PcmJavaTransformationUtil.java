package tools.vitruv.applications.pcmjava;

import java.util.Objects;
import org.emftext.language.java.containers.Package;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

public final class PcmJavaTransformationUtil {

    private PcmJavaTransformationUtil() {
    }

    public static String getCorrespondingPackageName(Entity entity) {
        return toFirstLower(entity.getEntityName());
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

    private static String toFirstLower(String s) {
        if (s == null || s.length() == 0)
            return s;
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        if (s.length() == 1)
            return s.toLowerCase();
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }
}
