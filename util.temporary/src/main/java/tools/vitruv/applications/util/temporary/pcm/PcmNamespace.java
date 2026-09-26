package tools.vitruv.applications.util.temporary.pcm;

import org.eclipse.emf.ecore.EPackage;
import org.palladiosimulator.pcm.PcmPackage;

/**
 * Defines file‐extension constants and metamodel namespace URIs for PCM elements.
 */
public final class PcmNamespace {

    private PcmNamespace() {
        // Prevent instantiation
    }

    // File extensions
    public static final String REPOSITORY_FILE_EXTENSION = "repository";
    public static final String SYSTEM_FILE_EXTENSION     = "system";

    // Root package & namespace URI
    public static final EPackage ROOT_PACKAGE         = PcmPackage.eINSTANCE;
    public static final String   METAMODEL_NAMESPACE  = PcmPackage.eNS_URI;

    // PCM attribute identifiers
    public static final String ATTRIBUTE_ENTITY_NAME               = "entityName";
    public static final String PARAMETER_ATTRIBUTE_DATA_TYPE       = "dataType__Parameter";
    public static final String OPERATION_SIGNATURE_RETURN_TYPE     = "returnType__OperationSignature";

    // Inner declarations and composite data types
    public static final String DATATYPE_INNER_DECLARATION                  = "datatype_InnerDeclaration";
    public static final String INNER_DECLARATION_COMPOSITE_DATA_TYPE       = "innerDeclaration_CompositeDataType";

    // Role mappings for operations and components
    public static final String OPERATION_PROVIDED_ROLE_PROVIDING_ENTITY        = "providingEntity_ProvidedRole";
    public static final String OPERATION_PROVIDED_ROLE_PROVIDED_INTERFACE      = "providedInterface__OperationProvidedRole";
    public static final String COMPONENT_PROVIDED_ROLES_INTERFACE_PROVIDING_ENTITY = "providedRoles_InterfaceProvidingEntity";
    public static final String COMPONENT_REQUIRED_ROLES_INTERFACE_REQUIRING_ENTITY  = "requiredRoles_InterfaceRequiringEntity";

    // Assembly context mappings
    public static final String SYSTEM_ASSEMBLY_CONTEXTS_COMPOSED_STRUCTURE  = "assemblyContexts__ComposedStructure";
    public static final String ASSEMBLY_CONTEXT_ENCAPSULATED_COMPONENT      = "encapsulatedComponent__AssemblyContext";
}
