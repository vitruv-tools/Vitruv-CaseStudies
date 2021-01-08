package tools.vitruv.applications.pcmumlclass;

public final class TagLiterals {
	private TagLiterals() {
	}

	public static final String ASSEMBLY_CONTEXT__PROPERTY = "assembly-context-field";

	public static final String COLLECTION_DATATYPE__PARAMETER = "collection-typed-parameter";
	public static final String COLLECTION_DATATYPE__PROPERTY = "collection-typed-property";

	public static final String COMPOSITE_DATATYPE__CLASS = "composite-type-class";

	/**
	 * should be used for both PrimitiveDatatype and CompositeDatatype
	 * correspondences
	 */
	public static final String DATATYPE__TYPE = "datatype";

	public static final String INNER_DECLARATION__PROPERTY = "composite-inner-property";

	public static final String INTERFACE_TO_INTERFACE = "interface";

	public static final String PARAMETER__REGULAR_PARAMETER = "interface-operation-parameter";

	public static final String REPOSITORY_TO_CONTRACTS_PACKAGE = "contracts-package";
	public static final String REPOSITORY_TO_DATATYPES_PACKAGE = "datatypes-package";
	public static final String REPOSITORY_TO_REPOSITORY_PACKAGE = "repository-package";

	public static final String PROVIDED_ROLE__INTERFACE_REALIZATION = "provided-interface-generalization";

	public static final String REQUIRED_ROLE__PARAMETER = "required-constructor-parameter";
	public static final String REQUIRED_ROLE__PROPERTY = "required-instance-property";

	public static final String SIGNATURE__OPERATION = "method";
	public static final String SIGNATURE__RETURN_PARAMETER = "return-parameter";

	// RepositoryComponent- and System-reactions manage the correspondences below
	// (create, move, delete, rename),
	// but the shared reference-features regarding ProvidedRole, RequiredRole are
	// synchronized across the more general
	// InterfaceProvidingRequiringEntity (IPRE) correspondence relations.
	// Similarly ComposedProvidingRequiringEntity (CPRE), a subclass of IPRE, uses
	// the shared IPRE__IMPLEMENTATION correspondence
	// to propagate AssemblyContext elements.
	public static final String REPOSITORY_COMPONENT__PACKAGE = "component-package";
	public static final String SYSTEM__SYSTEM_PACKAGE = "system-package";
	public static final String IPRE__IMPLEMENTATION = "ipre-implementation"; // IPRE = InterfaceProvidingRequiringEntity
	public static final String IPRE__CONSTRUCTOR = "ipre-constructor";
}
