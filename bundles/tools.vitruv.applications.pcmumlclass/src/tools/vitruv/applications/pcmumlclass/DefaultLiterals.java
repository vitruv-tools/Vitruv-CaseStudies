package tools.vitruv.applications.pcmumlclass;

import java.util.Arrays;
import java.util.List;

public final class DefaultLiterals {
	private DefaultLiterals() {
	}

	public static final String MODEL_DIRECTORY = "model";
	public static final String PCM_REPOSITORY_FILE_NAME = "Repository";
	public static final String PCM_REPOSITORY_EXTENSION = ".repository";
	public static final String PCM_SYSTEM_FILE_NAME = "System";
	public static final String PCM_SYSTEM_EXTENSION = ".system";
	public static final String UML_MODEL_FILE_NAME = PCM_REPOSITORY_FILE_NAME;
	public static final String UML_EXTENSION = ".uml";

	public static final String ROOT_MODEL_NAME = "umlrootmodel";
	public static final String CONTRACTS_PACKAGE_NAME = "contracts";
	public static final String DATATYPES_PACKAGE_NAME = "datatypes";
	public static final String IMPLEMENTATION_SUFFIX = "Impl";

	public static final String RETURN_PARAM_NAME = "returnParam";

	public static final String INPUT_REQUEST_MODEL_PATH = "Please enter where to save the corresponding Uml-Model, e.g. \"model/repository.uml\"";

	public static final String INPUT_REQUEST_NEW_MODEL_PATH = "Please enter where to save the new corresponding model, e.g. \"model/<file-name>\"";

	public static final String INPUT_REQUEST_DELETE_CORRESPONDING_UML_MODEL = "Do you want to delete the UML model associated with this PCM repository?";

	public static final String USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__REQUEST = "This package can correspond to a new RepositoryComponent. Please select the component type you want to create:";
	public static final List<String> USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__OPTIONS = Arrays
			.asList("BasicComponent", "CompositeComponent", "SubSystem", "No Correspondence");
	public static final int USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT = 0;
	public static final int USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__COMPOSITE_COMPONENT = 1;
	public static final int USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__SUB_SYSTEM = 2;
	public static final int USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__NOTHING = 3;

	public static final String USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REQUEST = "This package can correspond to a PCM::Repository or a PCM::System model. Please select the correspondence you want to create:";
	public static final List<String> USER_DISAMBIGUATE_REPOSITORY_SYSTEM__OPTIONS = Arrays.asList("Repository",
			"System", "No Correspondence");
	public static final int USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY = 0;
	public static final int USER_DISAMBIGUATE_REPOSITORY_SYSTEM__SYSTEM = 1;
	public static final int USER_DISAMBIGUATE_REPOSITORY_SYSTEM__NOTHING = 2;

	public static final String WARNING_MULTIPLE_COMPOSITE_DATA_TYPE_CANDIDATES = "Found multiple possible CompositeDatatype correspondence candidates in the datatypes package for uml::Class =  ";
	public static final String WARNING_IPRE_IMPLEMENTATION_REMOVED = "An IPRE implementation class (Component or System) has been removed from its corresct package. This is against the convention. uml::Class = ";
	public static final String WARNING_NESTED_COLLECTION_DATA_TYPE = "The innerType of a pcm::CollectionDataType has been set to another CollectionDataType. This can not be propagated to UML. pcm::CollectionDataType[innerType_CollectionDataType] = ";
}
