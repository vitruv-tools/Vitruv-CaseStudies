package tools.vitruv.applications.pcmumlclass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.kit.ipd.sdq.commons.util.java.util.ListUtil;

public class DefaultLiterals {
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
	public static final String REPOSITORY_PACKAGE_NAME = "repository";
	public static final String MAIN_PACKAGE_NAME = "main";
	public static final String CONTRACTS_PACKAGE_NAME = "contracts";
	public static final String DATATYPES_PACKAGE_NAME = "datatypes";
	public static final String IMPLEMENTATION_SUFFIX = "Impl";
	
	public static final String RETURN_PARAM_NAME = "returnParam";
	
	public static final String INPUT_REQUEST_MODEL_PATH = "Please enter where to save the corresponding Uml-Model, e.g. \"model/repository.uml\"";
	
	public static final String INPUT_REQUEST_NEW_MODEL_PATH = "Please enter where to save the new corresponding model, e.g. \"model/<file-name>\"";
	
	public static final String INPUT_REQUEST_DELETE_CORRESPONDING_UML_MODEL = "Do you want to delete the UML model associated with this PCM repository?";
	public static final int INPUT_REQUEST_DELETE_CORRESPONDING_UML_MODEL_YES = 0;
	public static final int INPUT_REQUEST_DELETE_CORRESPONDING_UML_MODEL_NO = 1;
	
	public static final String USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__REQUEST = "This package can correspond to a new RepositoryComponent. Please select the component type you want to create:";
	public static final List<String> USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__OPTIONS = Arrays.asList("BasicComponent", "CompositeComponent", "SubSystem", "No Correspondence");
	public static final int USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT = 0;
	public static final int USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__COMPOSITE_COMPONENT = 1;
	public static final int USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__SUB_SYSTEM = 2;
	public static final int USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__NOTHING = 3;
	
	public static final String USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REQUEST = "This package can correspond to a PCM::Repository or a PCM::System model. Please select the correspondence you want to create:";
	public static final List<String> USER_DISAMBIGUATE_REPOSITORY_SYSTEM__OPTIONS = Arrays.asList("Repository", "System", "No Correspondence");
	public static final int USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY = 0;
	public static final int USER_DISAMBIGUATE_REPOSITORY_SYSTEM__SYSTEM = 1;
	public static final int USER_DISAMBIGUATE_REPOSITORY_SYSTEM__NOTHING = 2;
}
