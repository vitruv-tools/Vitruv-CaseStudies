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
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Constructor;
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
public class JavaToUmlCornerCasesTest {

	private InternalVirtualModel vsum;
	private ResourceSet resourceSet;
	private ChangeRecorder changeRecorder;

	private Class externalClass; // java.util.ArrayList
	private Interface externalInterface; // java.util.List
	private Class externalClassObject; // java.lang.Object

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

		this.vsum.getResourceSet().getLoadOptions().put("DISABLE_LAYOUT_INFORMATION_RECORDING", Boolean.TRUE);
		this.vsum.getResourceSet().getLoadOptions().put("DISABLE_LOCATION_MAP", Boolean.TRUE);
		this.vsum.getResourceSet().getLoadOptions().put(JavaClasspath.OPTION_USE_LOCAL_CLASSPATH, Boolean.TRUE);
		this.vsum.getResourceSet().getLoadOptions().put(JavaClasspath.OPTION_REGISTER_STD_LIB, Boolean.FALSE);
		JavaClasspath.get(this.vsum.getResourceSet()).registerClassifierJar(URI.createFileURI(Paths.get("testresources\\rt.jar").toAbsolutePath().toString()));

		// create resource set for recording
		this.resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		this.resourceSet.getLoadOptions().put("DISABLE_LAYOUT_INFORMATION_RECORDING", Boolean.TRUE);
		this.resourceSet.getLoadOptions().put("DISABLE_LOCATION_MAP", Boolean.TRUE);
		this.resourceSet.getLoadOptions().put(JavaClasspath.OPTION_USE_LOCAL_CLASSPATH, Boolean.TRUE);
		this.resourceSet.getLoadOptions().put(JavaClasspath.OPTION_REGISTER_STD_LIB, Boolean.FALSE);
		JavaClasspath.get(this.resourceSet).registerClassifierJar(URI.createFileURI(Paths.get("testresources\\rt.jar").toAbsolutePath().toString()));

		// create external class
		this.externalClass = (Class) this.resourceSet.getEObject(URI.createURI("pathmap:/javaclass/java.util.ArrayList.java#/0/@classifiers.0"), true);

		// create external interface
		this.externalInterface = (Interface) this.resourceSet.getEObject(URI.createURI("pathmap:/javaclass/java.util.List.java#/0/@classifiers.0"), true);

		// create external class Object
		this.externalClassObject = (Class) this.resourceSet.getEObject(URI.createURI("pathmap:/javaclass/java.lang.Object.java#/0/@classifiers.0"), true);

		// create recorder
		this.changeRecorder = new ChangeRecorder(this.resourceSet);
		this.changeRecorder.addToRecording(this.resourceSet);
	}

	/**
	 * Checks whether packages were created correctly in the UML model without packages being present in the Java model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testCreatePackagesOnDemand(@TestProject final Path storageFolder) {
		{
			// begin recording
			this.changeRecorder.beginRecording();

			// create java class a.p.X
			final Resource r = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("X.java").toString()));
			CompilationUnit cu = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu.setName("X.java");
			cu.getNamespaces().add("a");
			cu.getNamespaces().add("p");
			Class jClass = ClassifiersFactory.eINSTANCE.createClass();
			jClass.setName("X");
			cu.getClassifiers().add(jClass);
			r.getContents().add(cu);

			// end recording
			final TransactionalChange recordedChange = this.changeRecorder.endRecording();
			this.changeRecorder.close();

			// propagate recorded changes into vsum
			this.vsum.propagateChange(recordedChange);
		}

		Resource r1 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("X.java").toString())).getResource();

		CompilationUnit cu1 = (CompilationUnit) r1.getContents().get(0);

		org.eclipse.uml2.uml.Class uClass1 = (org.eclipse.uml2.uml.Class) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu1.getClassifiers().get(0)).iterator().next();

		org.eclipse.uml2.uml.Package p = (org.eclipse.uml2.uml.Package) uClass1.eContainer();
		org.eclipse.uml2.uml.Package a = (org.eclipse.uml2.uml.Package) p.eContainer();

		assertEquals(p.getName(), "p");
		assertEquals(a.getName(), "a");
	}

	/**
	 * Checks whether two different packages with same name but different qualified name are correctly distinguished in the UML model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testPackagesWithSameNameDifferentQualifiedName(@TestProject final Path storageFolder) {
		{
			// begin recording
			this.changeRecorder.beginRecording();

			// create java class a.p.X
			final Resource r1 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("X.java").toString()));
			CompilationUnit cu1 = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu1.setName("X.java");
			cu1.getNamespaces().add("a");
			cu1.getNamespaces().add("p");
			Class jClass1 = ClassifiersFactory.eINSTANCE.createClass();
			jClass1.setName("X");
			cu1.getClassifiers().add(jClass1);
			r1.getContents().add(cu1);

			// create java class b.p.Y
			final Resource r2 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("Y.java").toString()));
			CompilationUnit cu2 = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu2.setName("Y.java");
			cu2.getNamespaces().add("b");
			cu2.getNamespaces().add("p");
			Class jClass2 = ClassifiersFactory.eINSTANCE.createClass();
			jClass2.setName("Y");
			cu2.getClassifiers().add(jClass2);
			r2.getContents().add(cu2);

			// end recording
			final TransactionalChange recordedChange = this.changeRecorder.endRecording();
			this.changeRecorder.close();

			// propagate recorded changes into vsum
			this.vsum.propagateChange(recordedChange);
		}

		Resource r1 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("X.java").toString())).getResource();

		CompilationUnit cu1 = (CompilationUnit) r1.getContents().get(0);

		org.eclipse.uml2.uml.Class uClass1 = (org.eclipse.uml2.uml.Class) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu1.getClassifiers().get(0)).iterator().next();

		org.eclipse.uml2.uml.Package p = (org.eclipse.uml2.uml.Package) uClass1.eContainer();
		org.eclipse.uml2.uml.Package a = (org.eclipse.uml2.uml.Package) p.eContainer();

		assertEquals(p.getName(), "p");
		assertEquals(a.getName(), "a");

		Resource r2 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("Y.java").toString())).getResource();

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
		{
			// begin recording
			this.changeRecorder.beginRecording();

			// create java class X
			final Resource r1 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("X.java").toString()));
			CompilationUnit cu1 = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu1.setName("X.java");
			Class jClassX = ClassifiersFactory.eINSTANCE.createClass();
			jClassX.setName("X");

			TypeReference extendsReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			jClassX.setExtends(extendsReference1);
			TypeReference implementsReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			jClassX.getImplements().add(implementsReference1);

			cu1.getClassifiers().add(jClassX);
			r1.getContents().add(cu1);

			ClassMethod methodOneParam = MembersFactory.eINSTANCE.createClassMethod();
			methodOneParam.setName("classMethod");

			OrdinaryParameter jParam1 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
			jParam1.setName("par1");
			TypeReference typeReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			typeReference1.setTarget(this.externalClassObject);
			jParam1.setTypeReference(typeReference1);

			methodOneParam.getParameters().add(jParam1);

			jClassX.getMembers().add(methodOneParam);

			extendsReference1.setTarget(this.externalClass);
			implementsReference1.setTarget(this.externalInterface);

			// end recording
			final TransactionalChange recordedChange = this.changeRecorder.endRecording();
			this.changeRecorder.close();

			// propagate recorded changes into vsum
			this.vsum.propagateChange(recordedChange);
		}

		Resource r = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("umloutput/umloutput.uml").toString())).getResource();

		Model uModel = (Model) r.getContents().get(0);

		assertNotNull(uModel);

		assertEquals(2, uModel.eContents().size());

		org.eclipse.uml2.uml.Package uPackageJava = (org.eclipse.uml2.uml.Package) uModel.eContents().stream().filter(f -> (f instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) f).getName().equals("java")).findAny().get();
		assertNotNull(uPackageJava);

		org.eclipse.uml2.uml.Package uPackageLang = (org.eclipse.uml2.uml.Package) uPackageJava.eContents().stream().filter(f -> (f instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) f).getName().equals("lang")).findAny().get();
		assertNotNull(uPackageLang);

		org.eclipse.uml2.uml.Package uPackageUtil = (org.eclipse.uml2.uml.Package) uPackageJava.eContents().stream().filter(f -> (f instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) f).getName().equals("util")).findAny().get();
		assertNotNull(uPackageUtil);

		assertEquals(1, uPackageLang.eContents().size());
		assertEquals(2, uPackageUtil.eContents().size());

		org.eclipse.uml2.uml.Class uClassObject = (org.eclipse.uml2.uml.Class) uPackageLang.eContents().get(0);

		assertEquals(uClassObject.getQualifiedName(), "umloutput::java::lang::Object");

		org.eclipse.uml2.uml.Interface uInterfaceList = (org.eclipse.uml2.uml.Interface) uPackageUtil.eContents().get(0);

		assertEquals(uInterfaceList.getQualifiedName(), "umloutput::java::util::List");

		org.eclipse.uml2.uml.Class uClassArrayList = (org.eclipse.uml2.uml.Class) uPackageUtil.eContents().get(1);

		assertEquals(uClassArrayList.getQualifiedName(), "umloutput::java::util::ArrayList");
	}

	// the following two tests are related to method and constructor overloading (i.e., same name but different parameters)

	/**
	 * Checks whether multiple methods with the same name but different parameters (i.e., overloaded methods) are correctly distinguished in the UML model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testOverloadedMethods(@TestProject final Path storageFolder) {
		{
			// begin recording
			this.changeRecorder.beginRecording();

			// create java class X
			final Resource r = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("X.java").toString()));
			CompilationUnit cu = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu.setName("X.java");
			Class jClass = ClassifiersFactory.eINSTANCE.createClass();
			jClass.setName("X");
			cu.getClassifiers().add(jClass);
			r.getContents().add(cu);

			ClassMethod jMethodOneParam = MembersFactory.eINSTANCE.createClassMethod();
			jMethodOneParam.setName("classMethod");
			ClassMethod jMethodTwoParam = MembersFactory.eINSTANCE.createClassMethod();
			jMethodTwoParam.setName("classMethod");

			OrdinaryParameter jParam1 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
			jParam1.setName("par1");
			TypeReference typeReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			typeReference1.setTarget(this.externalClass);
			jParam1.setTypeReference(typeReference1);

			jMethodOneParam.getParameters().add(jParam1);

			OrdinaryParameter jParam2 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
			jParam2.setName("par2");
			TypeReference typeReference2 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			typeReference2.setTarget(this.externalClass);
			jParam2.setTypeReference(typeReference2);

			OrdinaryParameter jParam3 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
			jParam3.setName("par3");
			TypeReference typeReference3 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			typeReference3.setTarget(this.externalClass);
			jParam3.setTypeReference(typeReference3);

			jMethodTwoParam.getParameters().add(jParam2);
			jMethodTwoParam.getParameters().add(jParam3);

			jClass.getMembers().add(jMethodOneParam);
			jClass.getMembers().add(jMethodTwoParam);

			// end recording
			final TransactionalChange recordedChange = this.changeRecorder.endRecording();
			this.changeRecorder.close();

			// propagate recorded changes into vsum
			this.vsum.propagateChange(recordedChange);
		}

		Resource r = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("umloutput/umloutput.uml").toString())).getResource();

		Model uModel = (Model) r.getContents().get(0);

		assertNotNull(uModel);

		// model -> class X
		org.eclipse.uml2.uml.Class uClassX = (org.eclipse.uml2.uml.Class) uModel.eContents().get(0);

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
		{
			// begin recording
			this.changeRecorder.beginRecording();

			// create java class a.p.X
			final Resource r = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("X.java").toString()));
			CompilationUnit cu = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu.setName("X.java");
			Class jClass = ClassifiersFactory.eINSTANCE.createClass();
			jClass.setName("X");
			cu.getClassifiers().add(jClass);
			r.getContents().add(cu);

			Constructor jConstructorOneParam = MembersFactory.eINSTANCE.createConstructor();
			jConstructorOneParam.setName("X");
			Constructor jConstructorTwoParam = MembersFactory.eINSTANCE.createConstructor();
			jConstructorTwoParam.setName("X");

			OrdinaryParameter jParam1 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
			jParam1.setName("par1");
			TypeReference typeReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			typeReference1.setTarget(this.externalClass);
			jParam1.setTypeReference(typeReference1);

			jConstructorOneParam.getParameters().add(jParam1);

			OrdinaryParameter jParam2 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
			jParam2.setName("par2");
			TypeReference typeReference2 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			typeReference2.setTarget(this.externalClass);
			jParam2.setTypeReference(typeReference2);

			OrdinaryParameter jParam3 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
			jParam3.setName("par3");
			TypeReference typeReference3 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			typeReference3.setTarget(this.externalClass);
			jParam3.setTypeReference(typeReference3);

			jConstructorTwoParam.getParameters().add(jParam2);
			jConstructorTwoParam.getParameters().add(jParam3);

			jClass.getMembers().add(jConstructorOneParam);
			jClass.getMembers().add(jConstructorTwoParam);

			// end recording
			final TransactionalChange recordedChange = this.changeRecorder.endRecording();
			this.changeRecorder.close();

			// propagate recorded changes into vsum
			this.vsum.propagateChange(recordedChange);
		}

		Resource r = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("umloutput/umloutput.uml").toString())).getResource();

		Model uModel = (Model) r.getContents().get(0);

		assertNotNull(uModel);

		// model -> class X
		org.eclipse.uml2.uml.Class uClassX = (org.eclipse.uml2.uml.Class) uModel.eContents().get(0);

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

	// the following three tests are related to the Java keywords "extends" and "implements"

	/**
	 * Checks whether generalization elements of classes are correctly created in the UML model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testClassGeneralization(@TestProject final Path storageFolder) {
		{
			// begin recording
			this.changeRecorder.beginRecording();

			// create java class X
			final Resource r1 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("X.java").toString()));
			CompilationUnit cu1 = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu1.setName("X.java");
			Class packagedClass1 = ClassifiersFactory.eINSTANCE.createClass();
			packagedClass1.setName("X");
			TypeReference extendsReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			packagedClass1.setExtends(extendsReference1);
			cu1.getClassifiers().add(packagedClass1);
			r1.getContents().add(cu1);

			// create java class Y
			final Resource r2 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("Y.java").toString()));
			CompilationUnit cu2 = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu2.setName("Y.java");
			Class packagedClass2 = ClassifiersFactory.eINSTANCE.createClass();
			packagedClass2.setName("Y");
			TypeReference extendsReference2 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			packagedClass2.setExtends(extendsReference2);
			cu2.getClassifiers().add(packagedClass2);
			r2.getContents().add(cu2);

			extendsReference1.setTarget(packagedClass2);
			extendsReference2.setTarget(this.externalClass);

			// end recording
			final TransactionalChange recordedChange = this.changeRecorder.endRecording();
			this.changeRecorder.close();

			// propagate recorded changes into vsum
			this.vsum.propagateChange(recordedChange);
		}

		Resource r1 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("X.java").toString())).getResource();

		CompilationUnit cu1 = (CompilationUnit) r1.getContents().get(0);

		org.eclipse.uml2.uml.Class uClass1 = (org.eclipse.uml2.uml.Class) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu1.getClassifiers().get(0)).iterator().next();

		assertEquals("X", uClass1.getName());

		assertEquals(uClass1.getGeneralizations().size(), 1);

		Resource r2 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("Y.java").toString())).getResource();

		CompilationUnit cu2 = (CompilationUnit) r2.getContents().get(0);

		org.eclipse.uml2.uml.Class uClass2 = (org.eclipse.uml2.uml.Class) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu2.getClassifiers().get(0)).iterator().next();

		assertEquals("Y", uClass2.getName());

		assertEquals(uClass2.getGeneralizations().size(), 1);

		// class X extends Y
		assertEquals(uClass1.getGeneralizations().get(0).getGeneral(), uClass2);

		// class Y extends external class
		assertEquals(uClass2.getGeneralizations().get(0).getGeneral().getQualifiedName(), "umloutput::java::util::ArrayList");
	}

	/**
	 * Checks whether generalization elements of interfaces are correctly created in the UML model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testInterfaceGeneralization(@TestProject final Path storageFolder) {
		{
			// begin recording
			this.changeRecorder.beginRecording();

			// create java class X
			final Resource r1 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("X.java").toString()));
			CompilationUnit cu1 = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu1.setName("X.java");
			Interface jInterfaceX = ClassifiersFactory.eINSTANCE.createInterface();
			jInterfaceX.setName("X");
			TypeReference extendsReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			jInterfaceX.getExtends().add(extendsReference1);
			cu1.getClassifiers().add(jInterfaceX);
			r1.getContents().add(cu1);

			// create java class Y
			final Resource r2 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("Y.java").toString()));
			CompilationUnit cu2 = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu2.setName("Y.java");
			Interface jInterfaceY = ClassifiersFactory.eINSTANCE.createInterface();
			jInterfaceY.setName("Y");
			TypeReference extendsReference2 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			jInterfaceY.getExtends().add(extendsReference2);
			cu2.getClassifiers().add(jInterfaceY);
			r2.getContents().add(cu2);

			extendsReference1.setTarget(jInterfaceY);
			extendsReference2.setTarget(this.externalInterface);

			// end recording
			final TransactionalChange recordedChange = this.changeRecorder.endRecording();
			this.changeRecorder.close();

			// propagate recorded changes into vsum
			this.vsum.propagateChange(recordedChange);
		}

		Resource r1 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("X.java").toString())).getResource();

		CompilationUnit cu1 = (CompilationUnit) r1.getContents().get(0);

		org.eclipse.uml2.uml.Interface uInterface1 = (org.eclipse.uml2.uml.Interface) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu1.getClassifiers().get(0)).iterator().next();

		assertEquals("X", uInterface1.getName());

		assertEquals(uInterface1.getGeneralizations().size(), 1);

		Resource r2 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("Y.java").toString())).getResource();

		CompilationUnit cu2 = (CompilationUnit) r2.getContents().get(0);

		org.eclipse.uml2.uml.Interface uInterface2 = (org.eclipse.uml2.uml.Interface) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu2.getClassifiers().get(0)).iterator().next();

		assertEquals("Y", uInterface2.getName());

		assertEquals(uInterface2.getGeneralizations().size(), 1);

		// interface X extends Y
		assertEquals(uInterface1.getGeneralizations().get(0).getGeneral(), uInterface2);

		// interface Y extends external interface
		assertEquals(uInterface2.getGeneralizations().get(0).getGeneral().getQualifiedName(), "umloutput::java::util::List");
	}

	/**
	 * Checks whether interface realization elements of classes are correctly created in the UML model.
	 * 
	 * @param storageFolder
	 */
	@Test
	public void testClassInterfaceRealization(@TestProject final Path storageFolder) {
		{
			// begin recording
			this.changeRecorder.beginRecording();

			// create java class X
			final Resource r1 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("X.java").toString()));
			CompilationUnit cu1 = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu1.setName("X.java");
			Class jClassX = ClassifiersFactory.eINSTANCE.createClass();
			jClassX.setName("X");
			TypeReference implementsReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			jClassX.getImplements().add(implementsReference1);
			TypeReference implementsReference2 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			jClassX.getImplements().add(implementsReference2);
			cu1.getClassifiers().add(jClassX);
			r1.getContents().add(cu1);

			// create java class Y
			final Resource r2 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("Y.java").toString()));
			CompilationUnit cu2 = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu2.setName("Y.java");
			Interface jInterfaceY = ClassifiersFactory.eINSTANCE.createInterface();
			jInterfaceY.setName("Y");
			cu2.getClassifiers().add(jInterfaceY);
			r2.getContents().add(cu2);

			implementsReference1.setTarget(jInterfaceY);
			implementsReference2.setTarget(this.externalInterface);

			// end recording
			final TransactionalChange recordedChange = this.changeRecorder.endRecording();
			this.changeRecorder.close();

			// propagate recorded changes into vsum
			this.vsum.propagateChange(recordedChange);
		}

		Resource r1 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("X.java").toString())).getResource();

		CompilationUnit cu1 = (CompilationUnit) r1.getContents().get(0);

		org.eclipse.uml2.uml.Class uClass1 = (org.eclipse.uml2.uml.Class) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu1.getClassifiers().get(0)).iterator().next();

		assertEquals("X", uClass1.getName());

		assertEquals(uClass1.getInterfaceRealizations().size(), 2);

		Resource r2 = this.vsum.getModelInstance(URI.createFileURI(storageFolder.resolve("Y.java").toString())).getResource();

		CompilationUnit cu2 = (CompilationUnit) r2.getContents().get(0);

		org.eclipse.uml2.uml.Interface uInterface2 = (org.eclipse.uml2.uml.Interface) CorrespondenceModelUtil.getCorrespondingEObjects(this.vsum.getCorrespondenceModel(), cu2.getClassifiers().get(0)).iterator().next();

		assertEquals("Y", uInterface2.getName());

		// class X implements interface Y
		assertTrue(uClass1.getInterfaceRealizations().get(0).getTargets().get(0) == uInterface2);

		// class X also implements external interface
		assertEquals(((org.eclipse.uml2.uml.Interface) uClass1.getInterfaceRealizations().get(1).getTargets().get(0)).getQualifiedName(), "umloutput::java::util::List");
	}

	// the following tests cover combinations of above cases

	public void testEverythingCombined(@TestProject final Path storageFolder) {
		{
			// begin recording
			this.changeRecorder.beginRecording();

			// create java class a.p.X
			final Resource r1 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("X.java").toString()));
			CompilationUnit cu1 = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu1.setName("X.java");
			cu1.getNamespaces().add("a");
			cu1.getNamespaces().add("p");
			Class packagedClass1 = ClassifiersFactory.eINSTANCE.createClass();
			packagedClass1.setName("X");
			TypeReference extendsReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			packagedClass1.setExtends(extendsReference1);
			cu1.getClassifiers().add(packagedClass1);
			r1.getContents().add(cu1);

			ClassMethod methodOneParam = MembersFactory.eINSTANCE.createClassMethod();
			methodOneParam.setName("classMethod");
			ClassMethod methodTwoParam = MembersFactory.eINSTANCE.createClassMethod();
			methodTwoParam.setName("classMethod");

			OrdinaryParameter jParam1 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
			jParam1.setName("par1");
			TypeReference typeReference1 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			typeReference1.setTarget(this.externalClass);
			jParam1.setTypeReference(typeReference1);

			methodOneParam.getParameters().add(jParam1);

			OrdinaryParameter jParam2 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
			jParam2.setName("par2");
			TypeReference typeReference2 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			typeReference2.setTarget(this.externalClass);
			jParam2.setTypeReference(typeReference2);

			OrdinaryParameter jParam3 = ParametersFactory.eINSTANCE.createOrdinaryParameter();
			jParam3.setName("par3");
			TypeReference typeReference3 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			typeReference3.setTarget(this.externalClass);
			jParam3.setTypeReference(typeReference3);

			methodTwoParam.getParameters().add(jParam2);
			methodTwoParam.getParameters().add(jParam3);

			packagedClass1.getMembers().add(methodOneParam);
			packagedClass1.getMembers().add(methodTwoParam);

			// create java class b.p.Y
			final Resource r2 = resourceSet.createResource(URI.createFileURI(storageFolder.resolve("Y.java").toString()));
			CompilationUnit cu2 = ContainersFactory.eINSTANCE.createCompilationUnit();
			cu2.setName("Y.java");
			cu2.getNamespaces().add("b");
			cu2.getNamespaces().add("p");
			Class packagedClass2 = ClassifiersFactory.eINSTANCE.createClass();
			packagedClass2.setName("Y");
			TypeReference extendsReference2 = TypesFactory.eINSTANCE.createNamespaceClassifierReference();
			packagedClass2.setExtends(extendsReference2);
			cu2.getClassifiers().add(packagedClass2);
			r2.getContents().add(cu2);

			extendsReference1.setTarget(packagedClass2);
			extendsReference2.setTarget(this.externalClass);

			// end recording
			final TransactionalChange recordedChange = this.changeRecorder.endRecording();
			this.changeRecorder.close();

			// propagate recorded changes into vsum
			this.vsum.propagateChange(recordedChange);
		}

		// TODO
	}

}
