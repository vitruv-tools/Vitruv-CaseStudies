package tools.vitruv.applications.util.temporary.uml;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.activextendannotations.Utility;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.uml2.uml.PrimitiveType;

import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

/**
 * Utility class for retrieving UML primitive types from standard UML libraries.
 */
@Utility
@SuppressWarnings("all")
public final class UmlTypeUtil {

    private static final URI UML_JAVA_PRIMITIVE_TYPES_URI =
            URI.createURI("pathmap://UML_LIBRARIES/JavaPrimitiveTypes.library.uml");

    private static final URI UML_PRIMITIVE_TYPES_URI =
            URI.createURI("pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml");

    private UmlTypeUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Retrieves a list of UML primitive types from the standard UML libraries using a ResourceSet.
     *
     * @param resourceSet the resource set to retrieve resources from
     * @return a list of {@link PrimitiveType} elements
     */
    public static List<PrimitiveType> getUmlPrimitiveTypes(final ResourceSet resourceSet) {
        Function<URI, Resource> resourceRetriever = uri -> resourceSet.getResource(uri, true);
        return getUmlPrimitiveTypes(resourceRetriever);
    }

    /**
     * Retrieves a list of UML primitive types using a custom resource retriever function.
     *
     * @param resourceRetriever function to retrieve a Resource from a URI
     * @return a list of {@link PrimitiveType} elements
     */
    public static List<PrimitiveType> getUmlPrimitiveTypes(final Function<URI, Resource> resourceRetriever) {
        Resource javaPrimitiveResource = resourceRetriever.apply(UML_JAVA_PRIMITIVE_TYPES_URI);
        Resource umlPrimitiveResource = resourceRetriever.apply(UML_PRIMITIVE_TYPES_URI);

        List<Resource> resources = Collections.unmodifiableList(
                CollectionLiterals.newArrayList(javaPrimitiveResource, umlPrimitiveResource));

        Function1<Resource, List<EObject>> extractContents = resource ->
                IteratorExtensions.toList(resource.getAllContents());

        Iterable<EObject> allContents = IterableExtensions.flatMap(resources, extractContents);

        return IterableExtensions.toList(Iterables.filter(allContents, PrimitiveType.class));
    }
}
