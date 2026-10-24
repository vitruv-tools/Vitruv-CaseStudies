package tools.vitruv.applications.testutility.integration;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.applications.util.temporary.pcm.PcmDataTypeUtil;
import tools.vitruv.applications.util.temporary.uml.UmlTypeUtil;

public final class LegacyPcmUmlClassApplicationTestHelper {
	public LegacyPcmUmlClassApplicationTestHelper(CorrespondenceRetriever correspondenceRetriever,
		Function<URI, Resource> resourceRetriever) {
		this.correspondenceRetriever = correspondenceRetriever;
		this.resourceRetriever = resourceRetriever;

		List<PrimitiveDataType> pcmPrimitiveTypes = PcmDataTypeUtil.getPcmPrimitiveTypes(resourceRetriever);
		PCM_BOOL = findPcmPrimitiveType(pcmPrimitiveTypes, PrimitiveTypeEnum.BOOL);
		PCM_INT = findPcmPrimitiveType(pcmPrimitiveTypes, PrimitiveTypeEnum.INT);
		PCM_DOUBLE = findPcmPrimitiveType(pcmPrimitiveTypes, PrimitiveTypeEnum.DOUBLE);
		PCM_STRING = findPcmPrimitiveType(pcmPrimitiveTypes, PrimitiveTypeEnum.STRING);
		PCM_CHAR = findPcmPrimitiveType(pcmPrimitiveTypes, PrimitiveTypeEnum.CHAR);
		PCM_BYTE = findPcmPrimitiveType(pcmPrimitiveTypes, PrimitiveTypeEnum.BYTE);

		List<PrimitiveType> umlPrimitiveTypes = UmlTypeUtil.getUmlPrimitiveTypes(resourceRetriever);
		UML_BOOL = findUmlPrimitiveType(umlPrimitiveTypes, "bool");
		UML_INT = findUmlPrimitiveType(umlPrimitiveTypes, "int");
		UML_DOUBLE = findUmlPrimitiveType(umlPrimitiveTypes, "double");
		UML_STRING = findUmlPrimitiveType(umlPrimitiveTypes, "string");
	}

	private final CorrespondenceRetriever correspondenceRetriever;
	private final Function<URI, Resource> resourceRetriever;

	private static PrimitiveDataType findPcmPrimitiveType(List<PrimitiveDataType> pcmPrimitiveTypes,
		PrimitiveTypeEnum type) {
		return pcmPrimitiveTypes.stream().filter(it -> it.getType() == type).findFirst().orElse(null);
	}

	private static PrimitiveType findUmlPrimitiveType(List<PrimitiveType> umlPrimitiveTypes, String lowerCaseName) {
		return umlPrimitiveTypes.stream().filter(it -> Objects.equals(it.getName().toLowerCase(), lowerCaseName))
			.findFirst().orElse(null);
	}

	/**
	 * Fetches the given {@link EObject} from the {@link ResourceSet} of the running test.
	 * <br>
	 * Elements retrieved via correspondence model are read-only (except in the Transactions performed by the framework),
	 * and live in a different resourceSet. If corresponding elements need to be changed or compared, they should be retrieved via this method,
	 * or the getModifiableCorr(...) methods.
	 *
	 * @param original
	 * 		the {@link EObject} instance living in some ResourceSet
	 * @return the object instance in the ResourceSet of this test
	 */
	@SuppressWarnings("unchecked")
	public <T extends EObject> T getModifiableInstance(T original) {
		URI originalURI = EcoreUtil.getURI(original);
		return (T) resourceRetriever.apply(originalURI.trimFragment()).getEObject(originalURI.fragment());
	}

	public <T extends EObject> T getCorr(EObject source, Class<T> typeFilter, String tag) {
		return claimOne(correspondenceRetriever.getCorrespondingEObjects(source, typeFilter, tag));
	}

	public <T extends EObject> T getModifiableCorr(EObject source, Class<T> typeFilter, String tag) {
		T correspondence = getCorr(source, typeFilter, tag);
		if (correspondence == null) {
			return null;
		}
		return getModifiableInstance(getCorr(source, typeFilter, tag));
	}

	// here start the factory and retrieval methods
	public static final String PCM_MODEL_FILE = "model/Repository.repository";
	public static final String PCM_MODEL_SYSTEM_FILE = "model/System.system";
	public static final String UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/"
		+ DefaultLiterals.UML_MODEL_FILE_NAME + DefaultLiterals.UML_EXTENSION;

	public static final String REPOSITORY_NAME = "TestRepository";

	public static final String COMPONENT_NAME = "TestComponent";
	public static final String COMPONENT_NAME_2 = "TestComponent_2";

	public static final String COMPOSITE_DATATYPE_NAME = "TestCompositeType";
	public static final String COMPOSITE_DATATYPE_NAME_2 = "TestCompositeType_2";
	public static final String COLLECTION_DATATYPE_NAME = "TestCollectionType";

	public static final String INTERFACE_NAME = "TestInterface";
	public static final String SIGNATURE_NAME = "testSignature";

	public final PrimitiveDataType PCM_BOOL;
	public final PrimitiveDataType PCM_INT;
	public final PrimitiveDataType PCM_DOUBLE;
	public final PrimitiveDataType PCM_STRING;
	public final PrimitiveDataType PCM_CHAR;
	public final PrimitiveDataType PCM_BYTE;

	public final PrimitiveType UML_BOOL;
	public final PrimitiveType UML_INT;
	public final PrimitiveType UML_DOUBLE;
	public final PrimitiveType UML_STRING;

	// Repository
	public Repository createRepository() {
		Repository pcmRepository = RepositoryFactory.eINSTANCE.createRepository();
		pcmRepository.setEntityName(REPOSITORY_NAME);
		return pcmRepository;
	}

	public Package getUmlRepositoryPackage(Repository pcmRepository) {
		return getModifiableCorr(pcmRepository, Package.class, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE);
	}

	public Package getUmlContractsPackage(Repository pcmRepository) {
		return getModifiableCorr(pcmRepository, Package.class, TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE);
	}

	public Package getUmlDataTypesPackage(Repository pcmRepository) {
		return getModifiableCorr(pcmRepository, Package.class, TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE);
	}

	// CompositeComponent
	private CompositeComponent createComponent(Repository pcmRepository, String componentName) {
		CompositeComponent pcmComponent = RepositoryFactory.eINSTANCE.createCompositeComponent();
		pcmComponent.setEntityName(componentName);
		pcmRepository.getComponents__Repository().add(pcmComponent);
		return pcmComponent;
	}

	public CompositeComponent createComponent(Repository pcmRepository) {
		return createComponent(pcmRepository, COMPONENT_NAME);
	}

	public CompositeComponent createComponent_2(Repository pcmRepository) {
		return createComponent(pcmRepository, COMPONENT_NAME_2);
	}

	private CompositeComponent getPcmComponent(Repository pcmRepository, String componentName) {
		return pcmRepository.getComponents__Repository().stream()
			.filter(CompositeComponent.class::isInstance)
			.map(CompositeComponent.class::cast)
			.filter(it -> Objects.equals(it.getEntityName(), componentName))
			.findFirst().orElse(null);
	}

	public CompositeComponent getPcmComponent(Repository pcmRepository) {
		return getPcmComponent(pcmRepository, COMPONENT_NAME);
	}

	public CompositeComponent getPcmComponent_2(Repository pcmRepository) {
		return getPcmComponent(pcmRepository, COMPONENT_NAME_2);
	}

	public org.eclipse.uml2.uml.Class getUmlComponentImpl(Repository pcmRepository) {
		return getModifiableCorr(getPcmComponent(pcmRepository), org.eclipse.uml2.uml.Class.class,
			TagLiterals.IPRE__IMPLEMENTATION);
	}

	public org.eclipse.uml2.uml.Class getUmlComponentImpl_2(Repository pcmRepository) {
		return getModifiableCorr(getPcmComponent_2(pcmRepository), org.eclipse.uml2.uml.Class.class,
			TagLiterals.IPRE__IMPLEMENTATION);
	}

	public Operation getUmlComponentConstructor(Repository pcmRepository) {
		return getModifiableCorr(getPcmComponent(pcmRepository), Operation.class, TagLiterals.IPRE__CONSTRUCTOR);
	}

	public Operation getUmlComponentConstructor_2(Repository pcmRepository) {
		return getModifiableCorr(getPcmComponent_2(pcmRepository), Operation.class, TagLiterals.IPRE__CONSTRUCTOR);
	}

	// CompositeDataType
	private CompositeDataType createCompositeDataType(Repository pcmRepository, String name) {
		CompositeDataType pcmCompositeDataType = RepositoryFactory.eINSTANCE.createCompositeDataType();
		pcmCompositeDataType.setEntityName(name);
		pcmRepository.getDataTypes__Repository().add(pcmCompositeDataType);
		return pcmCompositeDataType;
	}

	public CompositeDataType createCompositeDataType(Repository pcmRepository) {
		return createCompositeDataType(pcmRepository, LegacyPcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME);
	}

	public CompositeDataType createCompositeDataType_2(Repository pcmRepository) {
		return createCompositeDataType(pcmRepository, LegacyPcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2);
	}

	private CompositeDataType getPcmCompositeDataType(Repository pcmRepository, String componentName) {
		return pcmRepository.getDataTypes__Repository().stream()
			.filter(CompositeDataType.class::isInstance)
			.map(CompositeDataType.class::cast)
			.filter(it -> Objects.equals(it.getEntityName(), componentName))
			.findFirst().orElse(null);
	}

	public CompositeDataType getPcmCompositeDataType(Repository pcmRepository) {
		return getPcmCompositeDataType(pcmRepository, LegacyPcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME);
	}

	public CompositeDataType getPcmCompositeDataType_2(Repository pcmRepository) {
		return getPcmCompositeDataType(pcmRepository, LegacyPcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2);
	}

	public org.eclipse.uml2.uml.Class getUmlCompositeDataTypeClass(Repository pcmRepository) {
		return getModifiableCorr(getPcmCompositeDataType(pcmRepository), org.eclipse.uml2.uml.Class.class,
			TagLiterals.COMPOSITE_DATATYPE__CLASS);
	}

	public org.eclipse.uml2.uml.Class getUmlCompositeDataTypeClass_2(Repository pcmRepository) {
		return getModifiableCorr(getPcmCompositeDataType_2(pcmRepository), org.eclipse.uml2.uml.Class.class,
			TagLiterals.COMPOSITE_DATATYPE__CLASS);
	}

	// CollectionDataType
	public CollectionDataType createCollectionDataType(Repository pcmRepository, DataType innerType) {
		CollectionDataType pcmCollectionType = RepositoryFactory.eINSTANCE.createCollectionDataType();
		pcmCollectionType.setEntityName(LegacyPcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME);
		pcmCollectionType.setInnerType_CollectionDataType(innerType);
		pcmRepository.getDataTypes__Repository().add(pcmCollectionType);
		return pcmCollectionType;
	}

	public CollectionDataType getPcmCollectionDataType(Repository pcmRepository) {
		return pcmRepository.getDataTypes__Repository().stream()
			.filter(CollectionDataType.class::isInstance)
			.map(CollectionDataType.class::cast)
			.filter(it -> Objects.equals(it.getEntityName(), LegacyPcmUmlClassApplicationTestHelper.COLLECTION_DATATYPE_NAME))
			.findFirst().orElse(null);
	}

	// OperationInterface
	public OperationInterface createOperationInterface(Repository pcmRepository) {
		OperationInterface pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
		pcmInterface.setEntityName(INTERFACE_NAME);
		pcmRepository.getInterfaces__Repository().add(pcmInterface);
		return pcmInterface;
	}

	public OperationInterface getPcmOperationInterface(Repository pcmRepository) {
		return pcmRepository.getInterfaces__Repository().stream()
			.filter(OperationInterface.class::isInstance)
			.map(OperationInterface.class::cast)
			.filter(it -> Objects.equals(it.getEntityName(), INTERFACE_NAME))
			.findFirst().orElse(null);
	}

	public Interface getUmlInterface(Repository pcmRepository) {
		return getModifiableCorr(getPcmOperationInterface(pcmRepository), Interface.class,
			TagLiterals.INTERFACE_TO_INTERFACE);
	}

	// OperationSignature
	public OperationSignature createOperationSignature(OperationInterface pcmInterface) {
		OperationSignature pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature();
		pcmSignature.setEntityName(SIGNATURE_NAME);
		pcmInterface.getSignatures__OperationInterface().add(pcmSignature);
		return pcmSignature;
	}

	public OperationSignature getPcmOperationSignature(OperationInterface pcmInterface) {
		return pcmInterface.getSignatures__OperationInterface().stream()
			.filter(it -> Objects.equals(it.getEntityName(), SIGNATURE_NAME))
			.findFirst().orElse(null);
	}

	public Operation getUmlOperation(OperationInterface pcmInterface) {
		return getModifiableCorr(getPcmOperationSignature(pcmInterface), Operation.class,
			TagLiterals.SIGNATURE__OPERATION);
	}

	public Parameter getUmlReturnParameter(OperationInterface pcmInterface) {
		return getModifiableCorr(getPcmOperationSignature(pcmInterface), Parameter.class,
			TagLiterals.SIGNATURE__RETURN_PARAMETER);
	}

}
