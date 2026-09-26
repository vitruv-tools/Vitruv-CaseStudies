package tools.vitruv.applications.util.temporary.pcm;

import com.google.common.collect.Iterators;
import edu.kit.ipd.sdq.activextendannotations.Utility;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import org.palladiosimulator.pcm.repository.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Utility class for working with PCM (Palladio Component Model) DataTypes.
 */
@Utility
@SuppressWarnings("all")
public final class PcmDataTypeUtil {

    // URI to the default primitive types repository
    private static final URI PCM_PRIMITIVE_TYPES_URI = URI.createURI("pathmap://PCM_MODELS/PrimitiveTypes.repository");

    // Private constructor to prevent instantiation
    private PcmDataTypeUtil() {}

    /**
     * Retrieves PCM primitive types using the EObject's resource set.
     */
    public static List<PrimitiveDataType> getPcmPrimitiveTypes(final EObject alreadyPersistedObject) {
        return getPcmPrimitiveTypes(alreadyPersistedObject.eResource().getResourceSet());
    }

    /**
     * Retrieves PCM primitive types using a function that loads a Resource from a URI.
     */
    public static List<PrimitiveDataType> getPcmPrimitiveTypes(final Function<URI, Resource> resourceRetriever) {
        final Resource resource = resourceRetriever.apply(PCM_PRIMITIVE_TYPES_URI);
        return IteratorExtensions.toList(Iterators.filter(resource.getAllContents(), PrimitiveDataType.class));
    }

    /**
     * Retrieves PCM primitive types from a given resource set.
     */
    public static List<PrimitiveDataType> getPcmPrimitiveTypes(final ResourceSet resourceSet) {
        final Resource resource = resourceSet.getResource(PCM_PRIMITIVE_TYPES_URI, true);
        return IteratorExtensions.toList(Iterators.filter(resource.getAllContents(), PrimitiveDataType.class));
    }

    /**
     * Checks if the given PCM primitive type is one of the supported base types.
     */
    public static boolean isSupportedPcmPrimitiveType(final PrimitiveDataType pcmPrimitiveType) {
        PrimitiveTypeEnum type = pcmPrimitiveType.getType();

        if (type != null) {
            switch (type) {
                case BOOL:
                case INT:
                case DOUBLE:
                case STRING:
                    return true;
                default:
                    return false;
            }
        }

        return false;
    }

    /**
     * Returns the human-readable name for any PCM DataType instance.
     * Supports Primitive, Collection, and Composite data types.
     *
     * @param dataType the PCM data type instance
     * @return the name of the data type
     * @throws IllegalArgumentException if the type is unsupported
     */
    @XbaseGenerated
    public static String getNameFromPCMDataType(final DataType dataType) {
        if (dataType instanceof PrimitiveDataType) {
            PrimitiveTypeEnum typeEnum = ((PrimitiveDataType) dataType).getType();
            return typeEnum != null ? typeEnum.getName() : "UNKNOWN_PRIMITIVE_TYPE";
        } else if (dataType instanceof CollectionDataType) {
            return ((CollectionDataType) dataType).getEntityName();
        } else if (dataType instanceof CompositeDataType) {
            return ((CompositeDataType) dataType).getEntityName();
        } else {
            throw new IllegalArgumentException("Unhandled PCM DataType: " + Arrays.asList(dataType).toString());
        }
    }
}
