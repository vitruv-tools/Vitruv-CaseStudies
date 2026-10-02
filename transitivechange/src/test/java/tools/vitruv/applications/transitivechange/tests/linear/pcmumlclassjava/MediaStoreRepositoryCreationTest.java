package tools.vitruv.applications.transitivechange.tests.linear.pcmumlclassjava;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.createJavaClass;
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.createJavaInterface;
import static tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil.createJavaPackage;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaAttribute;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaInterfaceMethod;
import static tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.createJavaParameter;
import static tools.vitruv.applications.util.temporary.java.JavaModificationUtil.createNamespaceClassifierReference;
import static tools.vitruv.applications.util.temporary.java.JavaStandardType.createJavaPrimitiveType;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.ParameterModifier;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper;
import tools.vitruv.applications.util.temporary.java.JavaStandardType;
import tools.vitruv.applications.util.temporary.java.JavaVisibility;

/**
 * Model creation tests, to test the set of transformations as a whole.
 * The tests mainly try to validate if the transformations terminate. To see if the results are correct manual verification of the output is necessary.
 *
 * Because of transitive change propagation and potential de-synchronization between view and model, it is necessary to stepwise simulate
 * the insertion of Uml/java-models and reload the view, in oder to achieve the wanted propagation of a correct ComponentRepository.
 * In a editor based creation process, this would automatically be done if the synchronization is called often enough (enough saves).
 * Because of the necessary reloads, the model is loaded (while the out-of-synch elements remain) and registered with new IDs in the UUID resolver.
 * This might make it necessary to provide the VM that runs the tests with additional heap space.
 * For the same reason, the uml-insertion test only uses a reduced repository model.
 */
public class MediaStoreRepositoryCreationTest extends PcmUmlJavaLinearTransitiveChangeTest {

    protected static final Logger logger = Logger.getLogger(MediaStoreRepositoryCreationTest.class.getSimpleName());

//	private static val PCM_MEDIA_STORE_REPOSITORY_PATH = "src/test/resources/model/ms.repository"
	// all SEFFs removed because the TUID-generator failed for ResourceDemandParameters
	private static final String PCM_MEDIA_STORE_REPOSITORY_PATH = "src/test/resources/model/ms_noSEFF.repository";
//	private static val UML_MEDIA_STORE_REPOSITORY_PATH = "src/test/resources/model/ms_repository_noSEFF_unedited.uml"
	private static final String UML_MEDIA_STORE_REDUCED_PATH = "src/test/resources/model/ms_repository_reduced.uml";

	private static final String UML_GENERATED_MEDIA_STORE_MODEL_PATH = "model-gen/ms_repository.uml";
	private static final String PCM_GENERATED_MEDIA_STORE_MODEL_PATH = "model-gen/ms_repository.repository";

	private Repository createRepository(){
		var pcmRepo = RepositoryFactory.eINSTANCE.createRepository();
		pcmRepo.setEntityName("TestRepository");

		var pcmCompositeTypeDummy = RepositoryFactory.eINSTANCE.createCompositeDataType();
		pcmRepo.getDataTypes__Repository().add(pcmCompositeTypeDummy);
		pcmCompositeTypeDummy.setEntityName("TestCompositeTypeDummy");

		var pcmPrimitiveType_Collection = RepositoryFactory.eINSTANCE.createCollectionDataType();
		pcmRepo.getDataTypes__Repository().add(pcmPrimitiveType_Collection);
		pcmPrimitiveType_Collection.setEntityName("TestPrimitiveType_Collection");
		pcmPrimitiveType_Collection.setInnerType_CollectionDataType(helper.PCM_INT);

		var pcmCompositeType_Collection = RepositoryFactory.eINSTANCE.createCollectionDataType();
		pcmRepo.getDataTypes__Repository().add(pcmCompositeType_Collection);
		pcmCompositeType_Collection.setEntityName("TestCompositeType_Collection");
		pcmCompositeType_Collection.setInnerType_CollectionDataType(pcmCompositeTypeDummy);

		var pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType();
		pcmRepo.getDataTypes__Repository().add(pcmCompositeType);
		pcmCompositeType.setEntityName("TestCompositeType");
		var pcmInnerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration();
		pcmCompositeType.getInnerDeclaration_CompositeDataType().add(pcmInnerDeclaration);
		pcmInnerDeclaration.setEntityName("primitiveAttribute");
		pcmInnerDeclaration.setDatatype_InnerDeclaration(helper.PCM_INT);
		//second attribute with compositeType
		pcmInnerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration();
		pcmCompositeType.getInnerDeclaration_CompositeDataType().add(pcmInnerDeclaration);
		pcmInnerDeclaration.setEntityName("compositeAttribute");
		pcmInnerDeclaration.setDatatype_InnerDeclaration(pcmCompositeTypeDummy);
		//third attribute with collection Type
		pcmInnerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration();
		pcmCompositeType.getInnerDeclaration_CompositeDataType().add(pcmInnerDeclaration);
		pcmInnerDeclaration.setEntityName("collectionAttribute");
		pcmInnerDeclaration.setDatatype_InnerDeclaration(pcmCompositeType_Collection);

		var pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
		pcmRepo.getInterfaces__Repository().add(pcmInterface);
		pcmInterface.setEntityName("TestInterface");

		var pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature();
		pcmInterface.getSignatures__OperationInterface().add(pcmSignature);
		pcmSignature.setEntityName("testSignature_compositeCollection");
		pcmSignature.setReturnType__OperationSignature(pcmCompositeType_Collection);
		var pcmParameter = RepositoryFactory.eINSTANCE.createParameter();
		pcmSignature.getParameters__OperationSignature().add(pcmParameter);
		pcmParameter.setParameterName("testParameter_compositeCollection");
		pcmParameter.setModifier__Parameter(ParameterModifier.IN);
		pcmParameter.setDataType__Parameter(pcmCompositeType_Collection);

		var pcmSignature2 = RepositoryFactory.eINSTANCE.createOperationSignature();
		pcmInterface.getSignatures__OperationInterface().add(pcmSignature2);
		pcmSignature2.setEntityName("testSignature_primitiveCollection");
		pcmSignature2.setReturnType__OperationSignature(pcmPrimitiveType_Collection);
		var pcmParameter2 = RepositoryFactory.eINSTANCE.createParameter();
		pcmSignature2.getParameters__OperationSignature().add(pcmParameter2);
		pcmParameter2.setParameterName("testParameter_primitiveCollection");
		pcmParameter2.setModifier__Parameter(ParameterModifier.IN);
		pcmParameter2.setDataType__Parameter(pcmPrimitiveType_Collection);

		var pcmInterface2 = RepositoryFactory.eINSTANCE.createOperationInterface();
		pcmRepo.getInterfaces__Repository().add(pcmInterface2);
		pcmInterface2.setEntityName("TestInterface2");
		pcmInterface2.getParentInterfaces__Interface().add(pcmInterface);

		var pcmComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
		pcmRepo.getComponents__Repository().add(pcmComponent);
		pcmComponent.setEntityName("TestComponent");
		var pcmProvided = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
		pcmComponent.getProvidedRoles_InterfaceProvidingEntity().add(pcmProvided);
		pcmProvided.setEntityName("testProvidedRole");
		pcmProvided.setProvidedInterface__OperationProvidedRole(pcmInterface);
		var pcmRequired = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
		pcmComponent.getRequiredRoles_InterfaceRequiringEntity().add(pcmRequired);
		pcmRequired.setEntityName("testRequiredRole");
		pcmRequired.setRequiredInterface__OperationRequiredRole(pcmInterface);

		return pcmRepo;
	}

	@Test
	public void testMinimalRepository_PcmUmlJava_collectionTypeReplace() {
		getUserInteraction().addNextTextInput(""); // uses default uml model path and name
		getUserInteraction().addNextSingleSelection(0); // uses default java collection type

		var pcmRepo = RepositoryFactory.eINSTANCE.createRepository();
		pcmRepo.setEntityName("TestRepository");

		var pcmCompositeTypeDummy = RepositoryFactory.eINSTANCE.createCompositeDataType();
		pcmRepo.getDataTypes__Repository().add(pcmCompositeTypeDummy);
		pcmCompositeTypeDummy.setEntityName("TestCompositeTypeDummy");

		var pcmPrimitiveType_Collection = RepositoryFactory.eINSTANCE.createCollectionDataType();
		pcmRepo.getDataTypes__Repository().add(pcmPrimitiveType_Collection);
		pcmPrimitiveType_Collection.setEntityName("TestPrimitiveType_Collection");
		pcmPrimitiveType_Collection.setInnerType_CollectionDataType(helper.PCM_INT);

		var pcmCompositeType_Collection = RepositoryFactory.eINSTANCE.createCollectionDataType();
		pcmRepo.getDataTypes__Repository().add(pcmCompositeType_Collection);
		pcmCompositeType_Collection.setEntityName("TestCompositeType_Collection");
		pcmCompositeType_Collection.setInnerType_CollectionDataType(pcmCompositeTypeDummy);

		var pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
		pcmRepo.getInterfaces__Repository().add(pcmInterface);
		pcmInterface.setEntityName("TestInterface");

		var pcmSignature2 = RepositoryFactory.eINSTANCE.createOperationSignature();
		pcmInterface.getSignatures__OperationInterface().add(pcmSignature2);
		pcmSignature2.setEntityName("testSignature_primitiveCollection");
		pcmSignature2.setReturnType__OperationSignature(pcmPrimitiveType_Collection);

		var pcmPath = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.PCM_REPOSITORY_FILE_NAME + DefaultLiterals.PCM_REPOSITORY_EXTENSION;
		var umlPath = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME + DefaultLiterals.UML_EXTENSION;
		startRecordingChanges(resourceAt(Path.of(pcmPath))).getContents().add(pcmRepo);
		propagate();
		assertModelExists(pcmPath);
		assertModelExists(umlPath);

		pcmSignature2.setReturnType__OperationSignature(pcmCompositeType_Collection);
		propagate();
	}

	@Test
	public void testMediaStoreCreation_UmlInserted_reduced() {
		var umlRepo_forward = (Model) Iterables.getFirst(getTestResource(URI.createURI(UML_MEDIA_STORE_REDUCED_PATH)).getContents(), null);

		simulateRepositoryInsertion_UML(umlRepo_forward, UML_GENERATED_MEDIA_STORE_MODEL_PATH, PCM_GENERATED_MEDIA_STORE_MODEL_PATH);
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH);
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH);
	}

	@Test
	@Disabled("Currently failing due to incomplete Java UUIDs")
	public void testMediaStoreCreation_JavaInserted_reducedAndManuallyReplicated() {
		final var REPOSITORY_PKG_NAME = "defaultRepository";
		final var CONTRACTS_PKG_NAME = "contracts";
		final var DATATYPES_PKG_NAME = "datatypes";
		final var DATATYPE_NAME_AudioCollectionRequest = "AudioCollectionRequest";
		final var DATATYPE_NAME_FileContent = "FileContent";
		final var ATTRIBUTE_NAME_Count = "Count";
		final var ATTRIBUTE_NAME_Size = "Size";
		final var INTERFACE_NAME_IFileStorage = "IFileStorage";
		final var METHOD_NAME_getFile = "getFile";
		final var PARAMETER_NAME_audioRequest = "audioRequest";
		final var INTERFACE_NAME_IMediaAccess = "IMediaAccess";
		final var METHOD_NAME_upload = "upload";
		final var PARAMETER_NAME_file = "file";
		final var COMPONENT_PKG_NAME = "mediaAccess";
		final var COMPONENT_IMPL_NAME = "MediaAccessImpl";
		final var ATTRIBUTE_NAME = "requiredIFileStorage";

		getUserInteraction().addNextTextInput("repository"); // uml model name
		getUserInteraction().addNextTextInput("model"); // uml model path
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY); // the package should correspond to a repository
		getUserInteraction().addNextTextInput("model/repository.repository"); // pcm repository file
		var jPkg_Repo = createJavaPackageAsModel(REPOSITORY_PKG_NAME, null);
		propagate();

		var jPkg_datatypes = getJavaPackage(REPOSITORY_PKG_NAME, DATATYPES_PKG_NAME);
		var jDt_FileContent = createJavaClassInPackage(jPkg_datatypes, DATATYPE_NAME_FileContent, JavaVisibility.PUBLIC, false, false);
		var jDt_AudioCollectionRequest = createJavaClassInPackage(jPkg_datatypes, DATATYPE_NAME_AudioCollectionRequest, JavaVisibility.PUBLIC, false, false);
		// It would be better to create and move the created classes, in order to test more changes,
		// but I don't know how to do a move of the saved resources, without breaking the ResourceSet.
		var jAtt_Count = createJavaAttribute(ATTRIBUTE_NAME_Count, createJavaPrimitiveType(JavaStandardType.INT), JavaVisibility.PUBLIC, false, false);
		var jAtt_Size = createJavaAttribute(ATTRIBUTE_NAME_Size, createJavaPrimitiveType(JavaStandardType.INT), JavaVisibility.PUBLIC, false, false);
		jDt_AudioCollectionRequest.getMembers().add(jAtt_Count);
		jDt_AudioCollectionRequest.getMembers().add(jAtt_Size);
		propagate();

		var jPkg_contracts = getJavaPackage(REPOSITORY_PKG_NAME, CONTRACTS_PKG_NAME);
		var jI_IFileStorage = createJavaInterfaceInPackage(jPkg_contracts, INTERFACE_NAME_IFileStorage, List.of());
		var jDtRef_AudioCollectionRequest = createNamespaceClassifierReference(jDt_AudioCollectionRequest);
		var jParam_audioRequest = createJavaParameter(PARAMETER_NAME_audioRequest, jDtRef_AudioCollectionRequest);
		var jDtRef_FileContent = createNamespaceClassifierReference(jDt_FileContent);
		var jMeth_getFile = createJavaInterfaceMethod(METHOD_NAME_getFile, jDtRef_FileContent, List.of(jParam_audioRequest));
		jI_IFileStorage.getMembers().add(jMeth_getFile);
		propagate();
		var jI_IMediaAccess = createJavaInterfaceInPackage(jPkg_contracts, INTERFACE_NAME_IMediaAccess, List.of());
		jDtRef_FileContent = createNamespaceClassifierReference(jDt_FileContent);
		var jParam_file = createJavaParameter(PARAMETER_NAME_file, jDtRef_FileContent);
		var jMeth_upload = createJavaInterfaceMethod(METHOD_NAME_upload, null, List.of(jParam_file));
		jI_IMediaAccess.getMembers().add(jMeth_upload);
		propagate();

		// TODO java -> uml -> pcm test error
		// At the moment it is possible to create a pcm::BasicComponent by inserting a package into the repository-package.
		// But it is not possible to edit the generated ComponentImpl-java::Class, because the containing CompilationUnit
		// can not be loaded/UUID-registered in the view-ResourceSet.
		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT); // the package should correspond to a BasicComponent
		createJavaPackageAsModel(COMPONENT_PKG_NAME, jPkg_Repo);
		propagate();
		var jClass_MediaAccessImpl = getJavaClassFromCompilationUnit(COMPONENT_IMPL_NAME, REPOSITORY_PKG_NAME, COMPONENT_PKG_NAME); // TODO here it fails
		assertNotNull(jClass_MediaAccessImpl);
		var jIRef_IFileStorage = createNamespaceClassifierReference(jI_IFileStorage); // required
		var jIRef_IMediaAccess = createNamespaceClassifierReference(jI_IFileStorage); // provided
		jClass_MediaAccessImpl.getImplements().add(jIRef_IMediaAccess);
		var jAtt_requiredIFileStorage = createJavaAttribute(ATTRIBUTE_NAME, jIRef_IFileStorage, JavaVisibility.PRIVATE, false, false);
		jClass_MediaAccessImpl.getMembers().add(jAtt_requiredIFileStorage);
		propagate();
	}

	@Test
	@Disabled("Requires the predefinition of necessary user interactions")
	public void testMediaStoreCreation_PcmInserted() {
		var pcmRepo_forward = (Repository) Iterables.getFirst(getTestResource(URI.createURI(PCM_MEDIA_STORE_REPOSITORY_PATH)).getContents(), null);

		var umlPath = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME + DefaultLiterals.UML_EXTENSION; // the default output if no input is given to the UserInteractor
		startRecordingChanges(resourceAt(Path.of(PCM_GENERATED_MEDIA_STORE_MODEL_PATH))).getContents().add(pcmRepo_forward);
		propagate();
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH);
		assertModelExists(umlPath);
	}

	@Test
	@Disabled("Requires the predefinition of necessary user interactions")
	public void testMinimalRepository_PcmUmlJava() {
		var pcmRepo_forward = createRepository();
		var pcmPath = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.PCM_REPOSITORY_FILE_NAME + DefaultLiterals.PCM_REPOSITORY_EXTENSION;
		var umlPath = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME + DefaultLiterals.UML_EXTENSION;
		startRecordingChanges(resourceAt(Path.of(pcmPath))).getContents().add(pcmRepo_forward);
		propagate();
		assertModelExists(pcmPath);
		assertModelExists(umlPath);
	}

	protected Package getJavaPackage(String qualifiedPackageName){
		var namespaces = qualifiedPackageName.split(".");
		return getJavaPackage(namespaces);
	}

	protected Package getJavaPackage(String... namespaces){
		var packageFileName = JavaPersistenceHelper.buildJavaFilePath(JavaPersistenceHelper.getPackageInfoClassName() + ".java", Arrays.asList(namespaces));
		var resource = resourceAt(Path.of(packageFileName));
		if (
			resource != null
			&& head(resource) != null
			&& head(resource) instanceof Package
		){
			var javaPackage = (Package) head(resource);
			return javaPackage;
		}
		return null;
	}

	/**
	 * Roundabout way to retrieve the jClass via uml correspondences because it fails when loading the CU directly.
	 */
	protected org.emftext.language.java.classifiers.Class getJavaClassFromCompilationUnit(String name, String... namespaces){
		// This roundabout way works to retrieve the read-only instance of the VSUM but still fails when trying to load the CU from the URI.
//		val javaPkg = getJavaPackageElement(namespaces)
//		val umlPkg = getCorrespondingEObjects(javaPkg, org.eclipse.uml2.uml.Package).head
//		val umlCompImpl = umlPkg.packagedElements.filterNull.filter(org.eclipse.uml2.uml.Class)
//			.findFirst[it.name.toLowerCase.contains(javaPkg.name.toLowerCase)]
//		val javaCompImpl = getCorrespondingEObjects(umlCompImpl, org.emftext.language.java.classifiers.Class).head
//		// here it fails, because the CU cannot be loaded into the view-ResourceSet
//		// because the UUID resolver fails on trying to register the 'Object extends Object'-ClassifierReference
//		val modifiableJavaCompImpl = getModelElement(EcoreUtil.getURI(javaCompImpl)) as org.emftext.language.java.classifiers.Class
//		return modifiableJavaCompImpl

		// fails because the compilationUnits reference is always empty on load
//		val package = getJavaPackageElement(namespaces)
//		val cu = package.compilationUnits.findFirst[it.name.contains(name)] // "endsWith" because the name might by qualified
//		return cu.containedClass

		// fails because the CU needs to load java.lang.Object
		// and UUID resolver fails on trying to register the 'Object extends Object'-ClassifierReference
		var cuFileName = JavaPersistenceHelper.buildJavaFilePath(name + ".java", Arrays.asList(namespaces));
		var resource = resourceAt(Path.of(cuFileName));
		if (
			resource != null
			&& head(resource) != null
			&& head(resource) instanceof Package
		){
			var cu = (CompilationUnit) head(resource);
			return cu.getContainedClass();
		}
		return null;
	}

	protected CompilationUnit createCompilationUnitInPackage(Package containingPackage, String cuName){
		List<String> namespace = containingPackage == null
			? List.of()
			: Stream.concat(containingPackage.getNamespaces().stream(), Stream.of(containingPackage.getName())).collect(Collectors.toList());
		var compilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit();
		compilationUnit.setName(Objects.toString(compilationUnit.getName(), "") + ".java");
		compilationUnit.getNamespaces().addAll(namespace);
//        cu.name = (namespace + #[cuName]).join(".")  + ".java"
        containingPackage.getCompilationUnits().add(compilationUnit);
        startRecordingChanges(resourceAt(Path.of(JavaPersistenceHelper.buildJavaFilePath(compilationUnit)))).getContents()
			.add(compilationUnit);
		propagate();
        return compilationUnit;
	}

	/**
	 * Implicitly creates the necessary CompilationUnit and propagates the changes.
	 */
	protected org.emftext.language.java.classifiers.Class createJavaClassInPackage(
		Package containingPackage,
		String cName, JavaVisibility visibility, boolean abstr, boolean fin
	){
		var cu = createCompilationUnitInPackage(containingPackage, cName);
		var javaClass = createJavaClass(cName, visibility, abstr, fin);
		cu.getClassifiers().add(javaClass);
		propagate();
		return javaClass;
	}

	/**
	 * Implicitly creates the necessary CompilationUnit and propagates the changes.
	 */
	protected Interface createJavaInterfaceInPackage(
		Package containingPackage,
		String cName, List<Interface> superInterfaces
	){
		var cu = createCompilationUnitInPackage(containingPackage, cName);
		var javaInterface = createJavaInterface(cName, superInterfaces);
		cu.getClassifiers().add(javaInterface);
		propagate();
		return javaInterface;
	}

//	############# copied from tools.vitruv.applications.umljava.java2uml.Java2UmlTransformationTest

    /**
     * Creates a new java package and synchronizes it as root model.
     *
     * @param name the name of the package
     * @param superPackage the package that contains the new package. Can be null if it is the default package.
     */
    protected Package createJavaPackageAsModel(String name, Package superPackage) {
        var jPackage = createJavaPackage(name, superPackage);
        startRecordingChanges(resourceAt(Path.of(JavaPersistenceHelper.buildJavaFilePath(jPackage)))).getContents()
			.add(jPackage);
		propagate();
        return jPackage;
    }

	private static EObject head(Resource resource) {
		return Iterables.getFirst(resource.getContents(), null);
	}

}
