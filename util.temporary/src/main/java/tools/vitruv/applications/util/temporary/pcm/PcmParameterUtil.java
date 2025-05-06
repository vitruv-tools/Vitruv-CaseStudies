package tools.vitruv.applications.util.temporary.pcm;

import edu.kit.ipd.sdq.activextendannotations.Utility;
import edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil;

import java.util.Objects;

import org.eclipse.emf.ecore.EAttribute;
import org.palladiosimulator.pcm.repository.Parameter;

import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Utility class for working with PCM {@link Parameter} elements.
 */
public final class PcmParameterUtil {

    private PcmParameterUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Sets the name of the given PCM parameter.
     * If the 'entityName' attribute exists, it also updates that attribute explicitly.
     *
     * @param parameter The PCM parameter to update.
     * @param newName   The new name to assign.
     */
    public static void setParameterName(final Parameter parameter, final String newName) {
        Function1<EAttribute, Boolean> isEntityNameAttribute =
                attr -> Objects.equals(attr.getName(), "entityName");

        boolean hasEntityNameAttribute = IterableExtensions.exists(
                parameter.eClass().getEAllAttributes(), isEntityNameAttribute);

        if (hasEntityNameAttribute) {
            EAttribute entityNameAttr = IterableUtil.claimOne(
                    IterableExtensions.filter(parameter.eClass().getEAllAttributes(), isEntityNameAttribute));
            parameter.eSet(entityNameAttr, newName);
        }

        parameter.setParameterName(newName);
    }
}
