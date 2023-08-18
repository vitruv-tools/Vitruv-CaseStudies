package tools.vitruv.applications.pcmjava.tests.pcm2java;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import tools.mdsd.jamopp.model.java.JavaPackage;
import tools.mdsd.jamopp.model.java.classifiers.ClassifiersFactory;
import tools.mdsd.jamopp.model.java.classifiers.ConcreteClassifier;
import tools.mdsd.jamopp.model.java.classifiers.Interface;
import tools.mdsd.jamopp.model.java.commons.NamedElement;
import tools.mdsd.jamopp.model.java.containers.CompilationUnit;
import tools.mdsd.jamopp.model.java.containers.ContainersFactory;
import tools.mdsd.jamopp.model.java.containers.Package;
import tools.mdsd.jamopp.model.java.members.InterfaceMethod;
import tools.mdsd.jamopp.model.java.members.Member;
import tools.mdsd.jamopp.model.java.members.MembersFactory;
import tools.mdsd.jamopp.model.java.statements.StatementsFactory;
import tools.mdsd.jamopp.model.java.types.TypesFactory;
import tools.mdsd.jamopp.resource.JavaResource2Factory;
import tools.vitruv.applications.util.temporary.java.JavaContainerAndClassifierUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JaMoPPTest {
	private static Logger LOGGER = Logger.getLogger(JaMoPPTest.class);

	@BeforeEach
	public void setUp() throws Exception {
		// register JaMoPP package and factory globally
		EPackage.Registry.INSTANCE.put(JavaPackage.eNS_URI, JavaPackage.eINSTANCE);
		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		final Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("java", new JavaResource2Factory());
		m.put("", new JavaResource2Factory());
	}

	@Test
	public void testJaMoPPRename() throws IOException {
		final Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
		final Interface jaMoPPInterface = ClassifiersFactory.eINSTANCE.createInterface();
		jaMoPPInterface.setName("TestJava");
		final CompilationUnit jaMoPPCompilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit();
		jaMoPPCompilationUnit.setName("TestJava.java");
		jaMoPPCompilationUnit.getClassifiers().add(jaMoPPInterface);
		JavaContainerAndClassifierUtil.updateNamespaces(jaMoPPCompilationUnit, jaMoPPPackage);
		jaMoPPPackage.setName("testJaMoPPPackage");
		final ResourceSet resourceSet = new ResourceSetImpl();
		String uriStr = "src/testpackage/" + jaMoPPCompilationUnit.getName();
		URI uri = URI.createFileURI(uriStr);
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(jaMoPPCompilationUnit);
		resource.save(null);
		jaMoPPCompilationUnit.setName("TestRenameJava.java");
		resource.delete(null);
		uriStr = "src/testpackage/" + jaMoPPCompilationUnit.getName();
		uri = URI.createFileURI(uriStr);
		resource = resourceSet.createResource(uri);
		resource.getContents().add(jaMoPPCompilationUnit);
		resource.save(null);
		resource.delete(null);
	}

	@Test
	public void testJaMoPPPackage() throws Exception {
		final Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
		jaMoPPPackage.setName("testpackageName");
		final ResourceSet resourceSet = new ResourceSetImpl();
		final String srcPath = "src-tmp/";
		String packageName = jaMoPPPackage.getNamespacesAsString() + jaMoPPPackage.getName();
		packageName = packageName.replace(".", "/");
		final URI uri = URI.createFileURI(srcPath + packageName + "/package-info.java");
		final Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(jaMoPPPackage);
		resource.save(null);
		resource.delete(null);
	}

	@Test
	public void testAddInterfaceMethod() throws IOException {
		final CompilationUnit cu = ContainersFactory.eINSTANCE.createCompilationUnit();
		cu.setName("TestAddInterfaceMethod.java");
		final Interface jaIf = ClassifiersFactory.eINSTANCE.createInterface();
		jaIf.setName("TestAddInterfaceMethod");
		cu.getClassifiers().add(jaIf);
		final ResourceSet rs = new ResourceSetImpl();
		final String srcPath = "src-tmp/";
		final String cuName = srcPath + cu.getName();
		final URI uri = URI.createFileURI(cuName);
		final Resource resource = rs.createResource(uri);
		// resource.save(null);
		final InterfaceMethod ifMethod = MembersFactory.eINSTANCE.createInterfaceMethod();
		ifMethod.setName("testMethod");
		ifMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid());
		ifMethod.setStatement(StatementsFactory.eINSTANCE.createEmptyStatement());
		resource.getContents().add(cu);
		resource.save(null);
		jaIf.getMembers().add(ifMethod);
		resource.save(null);
		assertTrue(null != ifMethod.eResource(), "Resource of interface method is null");
		resource.delete(null);
	}

	@Test
	public void testGetLayoutInformation() throws Throwable {
		String uriStr = JaMoPPTest.class.getCanonicalName();
		uriStr = uriStr.replace(".", "/");
		uriStr = "src/" + uriStr + ".java";
		final URI uri = URI.createURI(uriStr);
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet.createResource(uri);
		resource.load(null);
		this.assertLayoutInfosInResource(resource);
	}

	protected void assertLayoutInfosInResource(final Resource resource) {
		final CompilationUnit compilationUnit = (CompilationUnit) resource.getContents().get(0);
		this.printLayoutInformation(compilationUnit);
		for (final ConcreteClassifier classifier : compilationUnit.getClassifiers()) {
			this.printLayoutInformation(classifier);
			for (final Member member : classifier.getMembers()) {
				this.printLayoutInformation(member);
			}
		}
		assertTrue(null != compilationUnit.getLayoutInformations(), "Could not get layout information");
	}

	private void printLayoutInformation(final NamedElement namedElement) {
		LOGGER.trace("commenatable.getLayoutInformations(): " + namedElement.getLayoutInformations());
		for (final tools.mdsd.jamopp.model.commons.layout.LayoutInformation layoutInformation : namedElement.getLayoutInformations()) {
			final int startOffset = layoutInformation.getStartOffset();
			LOGGER.trace("Start offset for " + namedElement.getName() + ": " + startOffset);
		}
	}
}