package tools.vitruv.applications.umljava.tests.java2uml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Model;
import org.emftext.language.java.JavaClasspath;
import org.emftext.language.java.JavaClasspath.Initializer;
import org.emftext.language.java.JavaPackage;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.MembersFactory;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.ParametersFactory;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.applications.umljava.JavaToUmlChangePropagationSpecification;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.interaction.FreeTextUserInteraction;
import tools.vitruv.framework.change.interaction.UserInteractionBase;
import tools.vitruv.framework.change.interaction.impl.InteractionFactoryImpl;
import tools.vitruv.framework.change.recording.ChangeRecorder;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.repository.VitruvDomainRepository;
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.propagation.ChangePropagationSpecificationRepository;
import tools.vitruv.framework.userinteraction.InternalUserInteractor;
import tools.vitruv.framework.userinteraction.PredefinedInteractionResultProvider;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.internal.VirtualModelImpl;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;

/**
 * A test class to test various corner cases.
 */
@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class JavaToUmlCornerCasesCombinedTest {

	private InternalVirtualModel vsum;

	static String CLASS_NAME_X = "X";
	static String CLASS_NAME_Y = "Y";
	static String CLASS_NAME_Z = "Z";
	static String OPERATION_NAME = "classMethod";
	static String PARAMETER_NAME1 = "parameterName1";
	static String PARAMETER_NAME2 = "parameterName2";
	static String PARAMETER_NAME3 = "parameterName3";

	public Class packagedClass1; // a.p.X extends Y
	public Class packagedClass2; // b.p.Y extends Z
	public Class externalClass; // java.lang.Object
	public ClassMethod methodOneParam; // param1
	public ClassMethod methodTwoParam; // param2, param3
	public OrdinaryParameter jParam1; // type: external
	public OrdinaryParameter jParam2; // type: external
	public OrdinaryParameter jParam3; // type: external

	/**
	 * Initializes JAMOPP, creates a VSUM (with Java and UML domains), records the creation of a Java model, and propagates the changes into the VSUM.
	 * 
	 * @param storageFolder
	 * @throws IOException
	 */
	@BeforeEach
	public void before(@TestProject final Path storageFolder) throws IOException {
		// setup jamopp
		JavaClasspath.getInitializers().clear();
		JavaClasspath.getInitializers().add(new Initializer() {
			@Override
			public void initialize(Resource resource) {
			}

			@Override
			public boolean requiresLocalClasspath() {
				return true;
			}

			@Override
			public boolean requiresStdLib() {
				return false;
			}
		});
		EPackage.Registry.INSTANCE.put("http://www.emftext.org/java", JavaPackage.eINSTANCE);

		// create vsum

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
		JavaToUmlChangePropagationSpecification javaumlcps = new JavaToUmlChangePropagationSpecification();
		changePropagationSpecifications.add(javaumlcps);

		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(javaumlcps.getSourceDomain());
		domains.add(javaumlcps.getTargetDomain());

		VitruvDomainRepository domainRepository = new VitruvDomainRepositoryImpl(domains);

		PredefinedInteractionResultProvider irp = UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null);
		FreeTextUserInteraction ftui = new InteractionFactoryImpl().createFreeTextUserInteraction();
		ftui.setText("umloutput");
		for (int i = 0; i < 100; i++)
			irp.addUserInteractions(new UserInteractionBase[] { ftui, ftui });

		InternalUserInteractor userInteractor = UserInteractionFactory.instance.createUserInteractor(irp);

		final ChangePropagationSpecificationRepository changeSpecificationRepository = new ChangePropagationSpecificationRepository(changePropagationSpecifications);

		for (final ChangePropagationSpecification changePropagationSpecification : changePropagationSpecifications) {
			changePropagationSpecification.setUserInteractor(userInteractor);
		}

		final VsumFileSystemLayout fileSystemLayout = new VsumFileSystemLayout(storageFolder);
		fileSystemLayout.prepare();

		this.vsum = new VirtualModelImpl(fileSystemLayout, userInteractor, domainRepository, changeSpecificationRepository);

		vsum.getResourceSet().getLoadOptions().put("DISABLE_LAYOUT_INFORMATION_RECORDING", Boolean.TRUE);
		vsum.getResourceSet().getLoadOptions().put("DISABLE_LOCATION_MAP", Boolean.TRUE);
		vsum.getResourceSet().getLoadOptions().put(JavaClasspath.OPTION_USE_LOCAL_CLASSPATH, Boolean.TRUE);
		vsum.getResourceSet().getLoadOptions().put(JavaClasspath.OPTION_REGISTER_STD_LIB, Boolean.FALSE);
		JavaClasspath.get(vsum.getResourceSet()).registerClassifierJar(URI.createFileURI(Paths.get("testresources\\rt.jar").toAbsolutePath().toString()));

		// create resource set for recording

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		resourceSet.getLoadOptions().put("DISABLE_LAYOUT_INFORMATION_RECORDING", Boolean.TRUE);
		resourceSet.getLoadOptions().put("DISABLE_LOCATION_MAP", Boolean.TRUE);
		resourceSet.getLoadOptions().put(JavaClasspath.OPTION_USE_LOCAL_CLASSPATH, Boolean.TRUE);
		resourceSet.getLoadOptions().put(JavaClasspath.OPTION_REGISTER_STD_LIB, Boolean.FALSE);
		JavaClasspath.get(resourceSet).registerClassifierJar(URI.createFileURI(Paths.get("testresources\\rt.jar").toAbsolutePath().toString()));

		// create external class
		this.externalClass = (Class) resourceSet.getEObject(URI.createURI("pathmap:/javaclass/java.lang.Object.java#/0/@classifiers.0"), true);

		// begin recording
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();

		// create java class a.p.X
		final Resource r1 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve(CLASS_NAME_X + ".java").toString()));
		CompilationUnit cu1 = ContainersFactory.eINSTANCE.createCompilationUnit();
		cu1.setName(CLASS_NAME_X + ".java");
		cu1.getNamespaces().add("a");
		cu1.getNamespaces().add("p");
		this.packagedClass1 = ClassifiersFactory.eINSTANCE.createClass();
		this.packagedClass1.setName(CLASS_NAME_X);
		TypeReference extendsReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
		this.packagedClass1.setExtends(extendsReference1);
		cu1.getClassifiers().add(this.packagedClass1);
		r1.getContents().add(cu1);

		this.methodOneParam = MembersFactory.eINSTANCE.createClassMethod();
		this.methodOneParam.setName(OPERATION_NAME);
		this.methodTwoParam = MembersFactory.eINSTANCE.createClassMethod();
		this.methodTwoParam.setName(OPERATION_NAME);

		this.jParam1 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
		this.jParam1.setName(PARAMETER_NAME1);
		TypeReference typeReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
		typeReference1.setTarget(this.externalClass);
		this.jParam1.setTypeReference(typeReference1);

		this.methodOneParam.getParameters().add(this.jParam1);

		this.jParam2 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
		this.jParam2.setName(PARAMETER_NAME2);
		TypeReference typeReference2 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
		typeReference2.setTarget(this.externalClass);
		this.jParam2.setTypeReference(typeReference2);

		this.jParam3 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
		this.jParam3.setName(PARAMETER_NAME3);
		TypeReference typeReference3 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
		typeReference3.setTarget(this.externalClass);
		this.jParam3.setTypeReference(typeReference3);

		this.methodTwoParam.getParameters().add(this.jParam2);
		this.methodTwoParam.getParameters().add(this.jParam3);

		this.packagedClass1.getMembers().add(this.methodOneParam);
		this.packagedClass1.getMembers().add(this.methodTwoParam);

		// create java class b.p.Y
		final Resource r2 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve(CLASS_NAME_Y + ".java").toString()));
		CompilationUnit cu2 = ContainersFactory.eINSTANCE.createCompilationUnit();
		cu2.setName(CLASS_NAME_Y + ".java");
		cu2.getNamespaces().add("b");
		cu2.getNamespaces().add("p");
		this.packagedClass2 = ClassifiersFactory.eINSTANCE.createClass();
		this.packagedClass2.setName(CLASS_NAME_Y);
		TypeReference extendsReference2 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
		this.packagedClass2.setExtends(extendsReference2);
		cu2.getClassifiers().add(this.packagedClass2);
		r2.getContents().add(cu2);

		extendsReference1.setTarget(this.packagedClass2);
		extendsReference2.setTarget(this.externalClass);

		// end recording
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		changeRecorder.close();

		// propagate recorded changes into vsum
		this.vsum.propagateChange(recordedChange);
	}

	/**
	 * Checks whether packages were created correctly in the UML model without packages being present in the Java model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testCreatePackagesOnDemand(@TestProject final Path storageFolder) {
		Resource r1 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve(CLASS_NAME_X + ".java").toString())).getResource();

		CompilationUnit cu1 = (CompilationUnit) r1.getContents().get(0);

		org.eclipse.uml2.uml.Class uClass1 = (org.eclipse.uml2.uml.Class) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu1.getClassifiers().get(0)).iterator().next();

		org.eclipse.uml2.uml.Package p = (org.eclipse.uml2.uml.Package) uClass1.eContainer();
		org.eclipse.uml2.uml.Package a = (org.eclipse.uml2.uml.Package) p.eContainer();

		assertEquals(p.getName(), "p");
		assertEquals(a.getName(), "a");

		Resource r2 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve(CLASS_NAME_Y + ".java").toString())).getResource();

		CompilationUnit cu2 = (CompilationUnit) r2.getContents().get(0);

		org.eclipse.uml2.uml.Class uClass2 = (org.eclipse.uml2.uml.Class) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu2.getClassifiers().get(0)).iterator().next();

		org.eclipse.uml2.uml.Package p2 = (org.eclipse.uml2.uml.Package) uClass2.eContainer();
		org.eclipse.uml2.uml.Package b = (org.eclipse.uml2.uml.Package) p2.eContainer();

		assertEquals(p.getName(), "p");
		assertEquals(b.getName(), "b");
	}

	/**
	 * Checks whether two different packages with same name but different qualified name are correctly distinguished in the UML model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testPackagesWithSameNameDifferentQualifiedName(@TestProject final Path storageFolder) {
		Resource r1 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve(CLASS_NAME_X + ".java").toString())).getResource();

		CompilationUnit cu1 = (CompilationUnit) r1.getContents().get(0);

		org.eclipse.uml2.uml.Class uClass1 = (org.eclipse.uml2.uml.Class) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu1.getClassifiers().get(0)).iterator().next();

		org.eclipse.uml2.uml.Package p = (org.eclipse.uml2.uml.Package) uClass1.eContainer();
		org.eclipse.uml2.uml.Package a = (org.eclipse.uml2.uml.Package) p.eContainer();

		assertEquals(p.getName(), "p");
		assertEquals(a.getName(), "a");

		Resource r2 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve(CLASS_NAME_Y + ".java").toString())).getResource();

		CompilationUnit cu2 = (CompilationUnit) r2.getContents().get(0);

		org.eclipse.uml2.uml.Class uClass2 = (org.eclipse.uml2.uml.Class) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu2.getClassifiers().get(0)).iterator().next();

		org.eclipse.uml2.uml.Package p2 = (org.eclipse.uml2.uml.Package) uClass2.eContainer();
		org.eclipse.uml2.uml.Package b = (org.eclipse.uml2.uml.Package) p2.eContainer();

		assertEquals(p.getName(), "p");
		assertEquals(b.getName(), "b");

		// the packages must have the same name
		assertEquals(p.getName(), p2.getName());
		// but they must not be the same package
		assertTrue(p != p2);
	}

	/**
	 * Checks whether stubs have been correctly created in the UML model for every external type (e.g., from a JAR file or elsewhere in the classpath outside of the actually parsed source code) in the Java model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testCreateExternalTypeStubs(@TestProject final Path storageFolder) {
		Resource r = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("umloutput/umloutput.uml").toString())).getResource();

		Model uModel = (Model) r.getContents().get(0);

		assertNotNull(uModel);

		assertEquals(3, uModel.eContents().size());

		org.eclipse.uml2.uml.Package uPackageJava = (org.eclipse.uml2.uml.Package) uModel.eContents().stream().filter(f -> (f instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) f).getName().equals("java")).findAny().get();
		assertNotNull(uPackageJava);

		org.eclipse.uml2.uml.Package uPackageLang = (org.eclipse.uml2.uml.Package) uPackageJava.eContents().stream().filter(f -> (f instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) f).getName().equals("lang")).findAny().get();
		assertNotNull(uPackageLang);

		org.eclipse.uml2.uml.Class uClassObject = (org.eclipse.uml2.uml.Class) uPackageLang.eContents().get(0);

		assertNotNull(uClassObject);

		assertEquals(uClassObject.getQualifiedName(), "umloutput::java::lang::Object");
	}

	// the following two tests are related to method and constructor overloading (i.e., same name but different parameters)

	/**
	 * Checks whether multiple methods with the same name but different parameters (i.e., overloaded methods) are correctly distinguished in the UML model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testOverloadedMethods(@TestProject final Path storageFolder) {
		Resource r = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("umloutput/umloutput.uml").toString())).getResource();

		Model uModel = (Model) r.getContents().get(0);

		assertNotNull(uModel);

		// model -> package a -> package p -> class X
		org.eclipse.uml2.uml.Class uClassX = (org.eclipse.uml2.uml.Class) uModel.eContents().get(0).eContents().get(0).eContents().get(0);

		// there must be two methods
		assertEquals(2, uClassX.getMembers().size());

		org.eclipse.uml2.uml.Operation op1 = (org.eclipse.uml2.uml.Operation) uClassX.getMembers().get(0);
		org.eclipse.uml2.uml.Operation op2 = (org.eclipse.uml2.uml.Operation) uClassX.getMembers().get(1);

		// the methods must have the same name
		assertEquals(op1.getName(), op2.getName());
		// but they must not be the same methods
		assertTrue(op1 != op2);

		// the first method must have one parameter
		assertEquals(1, op1.getOwnedParameters().size());
		// the second method must have two parameters
		assertEquals(2, op2.getOwnedParameters().size());
	}

	/**
	 * Checks whether multiple constructors with the same name but different parameters (i.e., overloaded constructors) are correctly distinguished in the UML model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testOverloadedConstructors(@TestProject final Path storageFolder) {
		// TODO
	}

	// the following three tests are related to the Java keywords "extends" and "implements"

	/**
	 * Checks whether generalization elements of classes are correctly created in the UML model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testClassGeneralization(@TestProject final Path storageFolder) {
		Resource r1 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve(CLASS_NAME_X + ".java").toString())).getResource();

		CompilationUnit cu1 = (CompilationUnit) r1.getContents().get(0);

		org.eclipse.uml2.uml.Class uClass1 = (org.eclipse.uml2.uml.Class) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu1.getClassifiers().get(0)).iterator().next();

		assertEquals(CLASS_NAME_X, uClass1.getName());

		assertEquals(uClass1.getGeneralizations().size(), 1);

		Resource r2 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve(CLASS_NAME_Y + ".java").toString())).getResource();

		CompilationUnit cu2 = (CompilationUnit) r2.getContents().get(0);

		org.eclipse.uml2.uml.Class uClass2 = (org.eclipse.uml2.uml.Class) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu2.getClassifiers().get(0)).iterator().next();

		assertEquals(CLASS_NAME_Y, uClass2.getName());

		assertEquals(uClass2.getGeneralizations().size(), 1);

		// class X extends Y
		assertEquals(uClass1.getGeneralizations().get(0).getGeneral(), uClass2);

		// class Y extends external class Z
		assertEquals(uClass2.getGeneralizations().get(0).getGeneral().getQualifiedName(), "umloutput::java::lang::Object");
	}

	/**
	 * Checks whether generalization elements of interfaces are correctly created in the UML model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testInterfaceGeneralization(@TestProject final Path storageFolder) {
		// TODO
	}

	/**
	 * Checks whether interface realization elements of classes are correctly created in the UML model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testClassInterfaceRealization(@TestProject final Path storageFolder) {
		// TODO
	}

}
