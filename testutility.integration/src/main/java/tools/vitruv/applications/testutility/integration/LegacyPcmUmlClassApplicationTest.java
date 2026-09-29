package tools.vitruv.applications.testutility.integration;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.change.atomic.hid.ObjectResolutionUtil.getHierarchicUriFragment;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.isNoResource;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.isResource;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification;
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;

@ExtendWith(RegisterMetamodelsInStandalone.class)
public abstract class LegacyPcmUmlClassApplicationTest extends NonTransactionalVitruvApplicationTest {
	@Override
	protected Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(
			new CombinedPcmToUmlClassReactionsChangePropagationSpecification(),
			new CombinedUmlClassToPcmReactionsChangePropagationSpecification()
		);
	}

	protected LegacyPcmUmlClassApplicationTestHelper helper;
	protected ResourceSet testResourceSet;

	protected Resource getTestResource(URI uri) {
		return testResourceSet.getResource(uri, true);
	}

	@BeforeEach
	protected void setup() {
		helper = new LegacyPcmUmlClassApplicationTestHelper(this, uri -> startRecordingChanges(resourceAt(uri)));
		testResourceSet = new ResourceSetImpl();
	}

	@AfterEach
	protected void cleanup() {
		testResourceSet = null;
		helper = null;
	}

	protected void assertModelExists(String modelPathWithinProject) {
		URI modelUri = getUri(Path.of(modelPathWithinProject));
		assertThat(modelUri, isResource());
	}

	protected void assertModelNotExists(String modelPathWithinProject) {
		URI modelUri = getUri(Path.of(modelPathWithinProject));
		assertThat(modelUri, isNoResource());
	}

	/**
	 * Clears all test resources, reloads the resource of the given root element and returns the reloaded root element.
	 * <br><br>
	 * Changes to a resource that are resolved by the change propagation framework, may not reflect in local instances.
	 * The VSUM lives in its own ResourceSet and works on its own copies of the instances.
	 * When an instance in the VSUM is changed by the change propagation, the instance in the local ResourceSet of the Test can become desynchronized
	 * if those changes diverge from the manual changes performed by the developer (e.g. round-trip changes to the transformation's source).
	 * <br><br>
	 * WARNING: This invalidates all {@link EObject} instances in the {@link Resource} of the passed element (they turn into proxy elements).
	 * To do anything with them, best re-retrieve them from the reloaded resource by traversing the references of the returned root element,
	 * or via the element's URI, however, the proxy's URI will not work, if the element was desynchronized before the reload.
	 *
	 * @param modelElement
	 * 		any eObject in the Resource you want to reload
	 * @return the root element in the reloaded Resource, or null if none is present
	 */
	@SuppressWarnings("unchecked")
	protected <O extends EObject> O clearResourcesAndReloadRoot(O modelElement) {
		stopRecordingChanges(modelElement.eResource());
		URI resourceURI = modelElement.eResource().getURI();
		disposeViewResources();

		O rootElement = (O) from(EObject.class, resourceURI);
		if (rootElement != null) {
			startRecordingChanges(rootElement);
		}
		return rootElement;
	}

	protected boolean corresponds(EObject a, EObject b) {
		return Iterables.any(getCorrespondingEObjects(a, EObject.class), it -> EcoreUtil.equals(it, b));
	}

	protected boolean corresponds(EObject a, EObject b, String tag) {
		return Iterables.any(getCorrespondingEObjects(a, b.getClass(), tag), it -> EcoreUtil.equals(it, b));
	}

	// DataType consistency constraints defined here because it is used in multiple tests
	private boolean isCorrectSimpleTypeCorrespondence(
		DataType pcmDatatype,
		Type umlType,
		int lower,
		int upper
	) {
		boolean correspondingPrimitiveType = corresponds(pcmDatatype, umlType, TagLiterals.DATATYPE__TYPE)
			|| corresponds(pcmDatatype, umlType, TagLiterals.DATATYPE__TYPE__ALTERNATIVE);
		boolean correspondingCompositeType = corresponds(pcmDatatype, umlType, TagLiterals.COMPOSITE_DATATYPE__CLASS);
		return (correspondingPrimitiveType || correspondingCompositeType) // inner collection types are not supported
			&& upper == 1; // && lower == 1 // lower could also be 0
	}

	private boolean isCorrectCollectionTypeCorrespondence(
		CollectionDataType pcmCollection,
		Type umlType,
		int lower,
		int upper
	) {
		return lower == 0 && upper == LiteralUnlimitedNatural.UNLIMITED
			&& isCorrectSimpleTypeCorrespondence(pcmCollection.getInnerType_CollectionDataType(), umlType, 1, 1);
	}

	protected boolean isCorrect_DataType_Property_Correspondence(DataType pcmDatatype, Property umlProperty) {
		if (pcmDatatype == null || umlProperty.getType() == null) {
			return pcmDatatype == null && umlProperty.getType() == null;
		}

		boolean simpleTypeCorrespondence = isCorrectSimpleTypeCorrespondence(pcmDatatype, umlProperty.getType(),
			umlProperty.getLower(), umlProperty.getUpper());
		boolean collectionTypeCorrespondenceExists = corresponds(pcmDatatype, umlProperty,
			TagLiterals.COLLECTION_DATATYPE__PROPERTY);
		Boolean collectionTypeCorrespondenceIsCorrect = pcmDatatype instanceof CollectionDataType pcmCollection
			? isCorrectCollectionTypeCorrespondence(pcmCollection, umlProperty.getType(), umlProperty.getLower(),
				umlProperty.getUpper())
			: null;
		return simpleTypeCorrespondence || (collectionTypeCorrespondenceExists && collectionTypeCorrespondenceIsCorrect);
	}

	protected boolean isCorrect_DataType_Parameter_Correspondence(DataType pcmDatatype, Parameter umlParam) {
		if (pcmDatatype == null || umlParam.getType() == null) {
			return pcmDatatype == null && umlParam.getType() == null;
		}
		boolean simpleTypeCorrespondence = isCorrectSimpleTypeCorrespondence(pcmDatatype, umlParam.getType(),
			umlParam.getLower(), umlParam.getUpper());
		boolean collectionTypeCorrespondenceExists = corresponds(pcmDatatype, umlParam,
			TagLiterals.COLLECTION_DATATYPE__PARAMETER);
		Boolean collectionTypeCorrespondenceIsCorrect = pcmDatatype instanceof CollectionDataType pcmCollection
			? isCorrectCollectionTypeCorrespondence(pcmCollection, umlParam.getType(), umlParam.getLower(),
				umlParam.getUpper())
			: null;
		return simpleTypeCorrespondence || (collectionTypeCorrespondenceExists && collectionTypeCorrespondenceIsCorrect);
	}

	/* Because of transitive change propagation between the Pcm and Uml domains, it is necessary to stepwise simulate
	 * the insertion of Uml-models and reload the view, in oder to achieve the wanted propagation of a correct ComponentRepository.
	 * In a editor based creation process, this would automatically be done if the synchronization is called often enough (enough saves).
	 * Because of the necessary reloads, the model is loaded (while the out-of-synch elements remain) and registered with new IDs in the UUID resolver.
	 * This might make it necessary to provide the VM that runs the tests with additional heap space.
	 */
	protected Repository simulateRepositoryInsertion_PCM(Repository originalRepository, String pcmOutputPath,
		String umlOutputPath) {
		getUserInteraction().addNextTextInput(umlOutputPath); // answers where to save the corresponding .uml model
		startRecordingChanges(resourceAt(Path.of(pcmOutputPath))).getContents().add(originalRepository);
		propagate();
		Repository generatedRepository = clearResourcesAndReloadRoot(originalRepository);
		return generatedRepository;
	}

	private Model simulateDataTypeInsertion_UML(Model umlRepositoryModel, PackageableElement umlDataType) {
		org.eclipse.uml2.uml.Package repositoryPackage = Iterables.getFirst(umlRepositoryModel.getNestedPackages(), null);
		assertNotNull(repositoryPackage);
		org.eclipse.uml2.uml.Package datatypesPackage = repositoryPackage.getNestedPackages().stream()
			.filter(it -> Objects.equals(it.getName(), DefaultLiterals.DATATYPES_PACKAGE_NAME))
			.findFirst().orElse(null);
		assertNotNull(datatypesPackage);

		datatypesPackage.getPackagedElements().add(umlDataType);
		propagate();
		return clearResourcesAndReloadRoot(umlRepositoryModel);
	}

	private Model simulateContractsPackageElementInsertion_UML(Model umlRepositoryModel,
		PackageableElement originalContractsPackageElement) {
		org.eclipse.uml2.uml.Package repositoryPackage = Iterables.getFirst(umlRepositoryModel.getNestedPackages(), null);
		assertNotNull(repositoryPackage);
		org.eclipse.uml2.uml.Package contractsPackage = repositoryPackage.getNestedPackages().stream()
			.filter(it -> Objects.equals(it.getName(), DefaultLiterals.CONTRACTS_PACKAGE_NAME))
			.findFirst().orElse(null);
		assertNotNull(contractsPackage);

		if (!(originalContractsPackageElement instanceof Interface)) {
			contractsPackage.getPackagedElements().add(originalContractsPackageElement);
			return umlRepositoryModel;
		}
		Interface originalInterface = (Interface) originalContractsPackageElement;
		resolveElements(originalInterface, umlRepositoryModel.eResource());

		// add each operation without its parameters because at least the returnParameters will be already generated
		HashMap<String, List<Parameter>> originalOperationParameterMapping = new HashMap<>();
		for (Operation originalOperation : originalInterface.getOwnedOperations()) {
			originalOperationParameterMapping.put(originalOperation.getName(),
				new ArrayList<>(originalOperation.getOwnedParameters()));
			originalOperation.getOwnedParameters().clear();
		}
		contractsPackage.getPackagedElements().add(originalInterface);
		propagate();
		Model generatedModel = clearResourcesAndReloadRoot(umlRepositoryModel);

		// retrieve elements after reload
		repositoryPackage = Iterables.getFirst(generatedModel.getNestedPackages(), null);
		assertNotNull(repositoryPackage);
		contractsPackage = repositoryPackage.getNestedPackages().stream()
			.filter(it -> Objects.equals(it.getName(), DefaultLiterals.CONTRACTS_PACKAGE_NAME))
			.findFirst().orElse(null);
		assertNotNull(contractsPackage);
		Interface generatedInterface = contractsPackage.getPackagedElements().stream()
			.filter(Interface.class::isInstance)
			.map(Interface.class::cast)
			.filter(it -> Objects.equals(it.getName(), originalInterface.getName()))
			.findFirst().orElse(null);
		assertNotNull(generatedInterface);

		// merge each Parameter from the originalOperation with the generated Operation's parameters
		for (Operation generatedOperation : generatedInterface.getOwnedOperations()) {
			for (Parameter originalParameter : originalOperationParameterMapping.getOrDefault(generatedOperation.getName(),
				new ArrayList<>())) {
				Parameter generatedParameter = generatedOperation.getOwnedParameters().stream()
					.filter(it -> Objects.equals(it.getName(), originalParameter.getName()))
					.findFirst().orElse(null);
				resolveElements(originalParameter, generatedModel.eResource(), "name");
				if (generatedParameter != null) {
					mergeElements(originalParameter, generatedParameter, "name");
				} else {
					generatedOperation.getOwnedParameters().add(originalParameter);
				}
			}
		}

		// propagate
		return clearResourcesAndReloadRoot(generatedModel);
	}

	private Model simulateComponentInsertion_UML(Model inUmlRepositoryModel,
		org.eclipse.uml2.uml.Package originalComponentPackage, int userDisambigutationComponentType) {
		Model generatedModel = inUmlRepositoryModel;
		org.eclipse.uml2.uml.Package generatedRepositoryPackage = Iterables.getFirst(generatedModel.getNestedPackages(),
			null);
		assertNotNull(generatedRepositoryPackage);

		// Simulate adding a Component via round-trip by adding the components package.
		// For that, first remove the implementation from the original package to avoid duplication.
		org.eclipse.uml2.uml.Class originalComponentImpl = Iterables.getFirst(
			Iterables.filter(originalComponentPackage.getPackagedElements(), org.eclipse.uml2.uml.Class.class), null);
		assertNotNull(originalComponentImpl);
		originalComponentPackage.getPackagedElements().remove(originalComponentImpl);
		assertTrue(originalComponentPackage.getPackagedElements().isEmpty());

		getUserInteraction().addNextSingleSelection(userDisambigutationComponentType);
		generatedRepositoryPackage.getNestedPackages().add(originalComponentPackage); // throws UUID error when tested on its own
		propagate(); // should generate generatedComponentImpl
		generatedModel = clearResourcesAndReloadRoot(generatedModel);

		generatedRepositoryPackage = Iterables.getFirst(generatedModel.getNestedPackages(), null);
		assertNotNull(generatedRepositoryPackage);
		org.eclipse.uml2.uml.Package generatedComponentPackage = generatedRepositoryPackage.getNestedPackages().stream()
			.filter(it -> Objects.equals(it.getName(), originalComponentPackage.getName()))
			.findFirst().orElse(null);
		assertNotNull(generatedComponentPackage);
		org.eclipse.uml2.uml.Class generatedComponentImpl = findClassByName(generatedComponentPackage,
			originalComponentImpl.getName());
		assertNotNull(generatedComponentImpl);

		// merge everything except operations which are separately handled, because some of the constructor parameters will be generated.
		resolveElements(originalComponentImpl, generatedModel.eResource(), "ownedOperation", "interfaceRealization");
		mergeElements(originalComponentImpl, generatedComponentImpl, "ownedOperation", "interfaceRealization");
		for (InterfaceRealization originalRealization : new ArrayList<>(originalComponentImpl.getInterfaceRealizations())) {
			resolveElements(originalRealization, generatedModel.eResource());
			originalComponentImpl.getInterfaceRealizations().remove(originalRealization);
			originalRealization.getClients().remove(originalComponentImpl); // needs to be manually cleared, or else originalComponentImpl is still set as a client and causes UUID error
			generatedComponentImpl.getInterfaceRealizations().add(originalRealization);
		}

		propagate(); // should generate the constructor Parameters corresponding to RequiredRoles
		generatedModel = clearResourcesAndReloadRoot(generatedModel);
		generatedRepositoryPackage = Iterables.getFirst(generatedModel.getNestedPackages(), null);
		assertNotNull(generatedRepositoryPackage);
		generatedComponentPackage = generatedRepositoryPackage.getNestedPackages().stream()
			.filter(it -> Objects.equals(it.getName(), originalComponentPackage.getName()))
			.findFirst().orElse(null);
		assertNotNull(generatedComponentPackage);
		generatedComponentImpl = findClassByName(generatedComponentPackage, originalComponentImpl.getName());
		assertNotNull(generatedComponentImpl);

		// merge the original with the generated constructor
		Operation originalConstructor = originalComponentImpl.getOwnedOperations().stream()
			.filter(it -> Objects.equals(it.getName(), originalComponentImpl.getName()))
			.findFirst().orElse(null);
		Operation generatedConstructor = generatedComponentImpl.getOwnedOperations().stream()
			.filter(it -> Objects.equals(it.getName(), originalComponentImpl.getName()))
			.findFirst().orElse(null);
		assertNotNull(originalConstructor);
		assertNotNull(generatedConstructor);
		resolveElements(originalConstructor, generatedModel.eResource(), "ownedParameter");
		mergeElements(originalConstructor, generatedConstructor, "ownedParameter");
		for (Parameter originalParameter : new ArrayList<>(originalConstructor.getOwnedParameters())) {
			Parameter generatedParameter = generatedConstructor.getOwnedParameters().stream()
				.filter(it -> Objects.equals(it.getName(), originalParameter.getName()))
				.findFirst().orElse(null);
			resolveElements(originalParameter, generatedModel.eResource(), "name");
			if (generatedParameter != null) {
				mergeElements(originalParameter, generatedParameter, "name");
			} else {
				generatedConstructor.getOwnedParameters().add(originalParameter);
			}
		}
		// move/copy the remaining operations
		for (Operation originalOperation : new ArrayList<>(originalComponentImpl.getOwnedOperations()).stream()
			.filter(it -> !Objects.equals(it.getName(), originalComponentImpl.getName()))
			.toList()) {
			generatedComponentImpl.getOwnedOperations().add(originalOperation);
		}

		propagate();
		generatedModel = clearResourcesAndReloadRoot(generatedModel);
		return generatedModel;
	}

	private static org.eclipse.uml2.uml.Class findClassByName(org.eclipse.uml2.uml.Package uPackage, String name) {
		return uPackage.getPackagedElements().stream()
			.filter(org.eclipse.uml2.uml.Class.class::isInstance)
			.map(org.eclipse.uml2.uml.Class.class::cast)
			.filter(it -> Objects.equals(it.getName(), name))
			.findFirst().orElse(null);
	}

	protected Model simulateRepositoryInsertion_UML(Model originalRepositoryModel, String umlOutputPath,
		String pcmOutputPath) {
		assertNotNull(originalRepositoryModel);
		org.eclipse.uml2.uml.Package umlRepositoryPackage = Iterables.getFirst(originalRepositoryModel.getNestedPackages(),
			null);
		assertNotNull(umlRepositoryPackage);
		org.eclipse.uml2.uml.Package originalContractsPackage = umlRepositoryPackage.getNestedPackages().stream()
			.filter(it -> Objects.equals(it.getName(), DefaultLiterals.CONTRACTS_PACKAGE_NAME))
			.findFirst().orElse(null);
		org.eclipse.uml2.uml.Package originalDatatypesPackage = umlRepositoryPackage.getNestedPackages().stream()
			.filter(it -> Objects.equals(it.getName(), DefaultLiterals.DATATYPES_PACKAGE_NAME))
			.findFirst().orElse(null);
		List<org.eclipse.uml2.uml.Package> originalComponentPackages = umlRepositoryPackage.getNestedPackages().stream()
			.filter(it -> it != originalContractsPackage && it != originalDatatypesPackage)
			.toList();
		umlRepositoryPackage.getNestedPackages().clear();

		getUserInteraction().addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY); // rootelement is supposed to be a repository
		getUserInteraction().addNextTextInput(pcmOutputPath); // answers where to save the corresponding .pcm model
		startRecordingChanges(resourceAt(Path.of(umlOutputPath))).getContents().add(originalRepositoryModel);
		propagate();
		Model generatedModel = clearResourcesAndReloadRoot(originalRepositoryModel);

		// Create copies of the lists outside the model so that containment is irrelevant
		// and that the loops do not iterate over a changing List.
		for (PackageableElement originalDatatype : new ArrayList<>(originalDatatypesPackage.getPackagedElements())) {
			generatedModel = simulateDataTypeInsertion_UML(generatedModel, originalDatatype);
		}
		for (PackageableElement originalContractsPackageElement : new ArrayList<>(
			originalContractsPackage.getPackagedElements())) {
			generatedModel = simulateContractsPackageElementInsertion_UML(generatedModel,
				originalContractsPackageElement);
		}
		for (org.eclipse.uml2.uml.Package originalComponentPackage : originalComponentPackages) {
			generatedModel = simulateComponentInsertion_UML(generatedModel, originalComponentPackage,
				DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT);
		}
		return generatedModel;
	}

	/**
	 * Compare the root elements of the Resources at the specified project-relative paths,
	 * by first loading them into a temporary ResourceSet to make sure they are consistent with the disk state.
	 *
	 * @param originalWithinProjektPath
	 * 		the path of the resource to compare against
	 * @param generatedWithinProjektPath
	 * 		the path of the generated resource
	 * @return
	 * 		the Comparison produced by the default EMFCompare configuration (EMFCompare.builder.build)
	 */
	public Comparison compare(String originalWithinProjektPath, String generatedWithinProjektPath) {
		URI originalUri = getUri(Path.of(originalWithinProjektPath));
		URI generatedUri = getUri(Path.of(generatedWithinProjektPath));
		return compare(originalUri, generatedUri);
	}

	/**
	 * Compare the root elements at the specified URIs, by first loading them into a temporary ResourceSet
	 * to make sure they are consistent with the disk state.
	 *
	 * @param originalUri
	 * 		the URI of the resource to compare against
	 * @param generatedUri
	 * 		the URI of the generated resource
	 * @return
	 * 		the Comparison produced by the default EMFCompare configuration (EMFCompare.builder.build)
	 */
	public static Comparison compare(URI originalUri, URI generatedUri) {
		ResourceSet resourceSet = new ResourceSetImpl();
		EObject original = Iterables.getFirst(resourceSet.getResource(originalUri, true).getContents(), null);
		EObject generated = Iterables.getFirst(resourceSet.getResource(generatedUri, true).getContents(), null);
		return compare(original, generated);
	}

	/**
	 * This directly applies the default EMFCompare comparator to the passed elements.
	 * It does not ensure that the compared elements are in sync with the disk state.
	 */
	public static Comparison compare(Notifier original, Notifier generated) {
		EMFCompare comparator = EMFCompare.builder().build();
		DefaultComparisonScope scope = new DefaultComparisonScope(original, generated, original);
		return comparator.compare(scope);
	}

	/**
	 * Copy the attributes and move the references from the original element to the target element.
	 * The container feature is always ignored, to avoid moving the target element to the original's container.
	 *
	 * @param original
	 * 		the original element with the values to set on the target
	 * @param target
	 * 		the target element on which the values are to be set
	 * @param skipFeatures
	 * 		the names of the features that should be ignored
	 */
	public static void mergeElements(EObject original, EObject generated, String... skipFeatures) {
		// The relevance check must be done lazily inside the loop, because setting a feature can change
		// which features of the original are still set (e.g. contained elements are moved to the target)
		for (EStructuralFeature feature : original.eClass().getEAllStructuralFeatures()) {
			if (isResolveAndMergeRelevantFeature(feature, original, skipFeatures)) {
				generated.eSet(feature, original.eGet(feature));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void resolveElements(EObject original, Resource generatedResource, String... skipFeatures) {
		// The relevance check must be done lazily inside the loop, because setting a reference can unset
		// other references of the original (e.g. opposite or subsetting references)
		for (EReference reference : original.eClass().getEAllReferences()) {
			if (!isResolveAndMergeRelevantFeature(reference, original, skipFeatures)) {
				continue;
			}
			Object originalValue = original.eGet(reference);
			if (!reference.isMany()) {
				original.eSet(reference, resolve((EObject) originalValue, generatedResource));
			} else {
				original.eSet(reference,
					Lists.transform((List<EObject>) originalValue, it -> resolve(it, generatedResource)));
			}
		}
	}

	private static boolean isResolveAndMergeRelevantFeature(EStructuralFeature feature, EObject object,
		String... skipFeatures) {
		return !feature.isDerived() && feature.isChangeable() && object.eIsSet(feature)
			&& !Arrays.asList(skipFeatures).contains(feature.getName())
			&& (!(feature instanceof EReference reference) || !reference.isContainer());
	}

	private static EObject resolve(EObject original, Resource in) {
		return checkNotNull(in.getResourceSet().getEObject(in.getURI().appendFragment(getHierarchicUriFragment(original)), true),
			"resolved object for %s", original);
	}

}
