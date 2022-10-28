package tools.vitruv.applications.pcmjava.tests.pcm2java

import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.emftext.commons.layout.LayoutInformation
import org.emftext.language.java.JavaPackage
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.commons.NamedElement
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import org.emftext.language.java.members.InterfaceMethod
import org.emftext.language.java.members.Member
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.resource.java.mopp.JavaResourceFactory
import org.emftext.language.java.types.TypesFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmjava.pcm2java.JaMoPPTest

import static org.junit.jupiter.api.Assertions.assertTrue

class NewJaMoPPTest {
	static Logger LOGGER = Logger.getLogger(JaMoPPTest);

	@BeforeEach
	def void setUp() {
		// register JaMoPP package and factory globally
		EPackage.Registry.INSTANCE.put(JavaPackage.eNS_URI, JavaPackage.eINSTANCE);
		val Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		val Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("java", new JavaResourceFactory());
		m.put("", new JavaResourceFactory());
	}

	@Test
	def void testJaMoPPRename() {
		val Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
		val Interface jaMoPPInterface = ClassifiersFactory.eINSTANCE.createInterface();
		jaMoPPInterface.setName("TestJava");
		val CompilationUnit jaMoPPCompilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit();
		jaMoPPCompilationUnit.setName("TestJava.java");
		jaMoPPCompilationUnit.getClassifiers().add(jaMoPPInterface);
		jaMoPPPackage.getCompilationUnits().add(jaMoPPCompilationUnit);
		jaMoPPPackage.setName("testJaMoPPPackage");
		val ResourceSet resourceSet = new ResourceSetImpl();
		var String uriStr = "src/testpackage/" + jaMoPPCompilationUnit.getName();
		var URI uri = URI.createFileURI(uriStr);
		var Resource resource = resourceSet.createResource(uri);
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
	def void testJaMoPPPackage() {
		val Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
		jaMoPPPackage.setName("testpackageName");
		val ResourceSet resourceSet = new ResourceSetImpl();
		val String srcPath = "src-tmp/";
		var String packageName = jaMoPPPackage.getNamespacesAsString() + jaMoPPPackage.getName();
		packageName = packageName.replace(".", "/");
		val URI uri = URI.createFileURI(srcPath + packageName + "/package-info.java");
		val Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(jaMoPPPackage);
		resource.save(null);
		resource.delete(null);
	}

	@Test
	def void testAddInterfaceMethod() {
		val CompilationUnit cu = ContainersFactory.eINSTANCE.createCompilationUnit();
		cu.setName("TestAddInterfaceMethod.java");
		val Interface jaIf = ClassifiersFactory.eINSTANCE.createInterface();
		jaIf.setName("TestAddInterfaceMethod");
		cu.getClassifiers().add(jaIf);
		val ResourceSet rs = new ResourceSetImpl();
		val String srcPath = "src-tmp/";
		val String cuName = srcPath + cu.getName();
		val URI uri = URI.createFileURI(cuName);
		val Resource resource = rs.createResource(uri);
		// resource.save(null);
		val InterfaceMethod ifMethod = MembersFactory.eINSTANCE.createInterfaceMethod();
		ifMethod.setName("testMethod");
		ifMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid());
		resource.getContents().add(cu);
		resource.save(null);
		jaIf.getMembers().add(ifMethod);
		resource.save(null);
		assertTrue(null !== ifMethod.eResource(), "Resource of interface method is null");
		resource.delete(null);
	}

	@Test
	def void testGetLayoutInformation() {
		var String uriStr = JaMoPPTest.getCanonicalName();
		uriStr = uriStr.replace(".", "/");
		uriStr = "src/" + uriStr + ".java";
		val URI uri = URI.createURI(uriStr);
		val ResourceSet resourceSet = new ResourceSetImpl();
		val Resource resource = resourceSet.createResource(uri);
		resource.load(null);
		this.assertLayoutInfosInResource(resource);
	}
	
	protected def void assertLayoutInfosInResource(Resource resource) {
		val CompilationUnit compilationUnit = resource.getContents().get(0) as CompilationUnit;
		this.printLayoutInformation(compilationUnit);
		for (ConcreteClassifier classifier : compilationUnit.getClassifiers()) {
			this.printLayoutInformation(classifier);
			for (Member member : classifier.getMembers()) {
				this.printLayoutInformation(member);
			}
		}
		assertTrue(null !== compilationUnit.getLayoutInformations(), "Could not get layout information");
	}
	
	protected def void printLayoutInformation(NamedElement namedElement) {
		LOGGER.trace("commenatable.getLayoutInformations(): " + namedElement.getLayoutInformations());
		for (LayoutInformation layoutInformation : namedElement.getLayoutInformations()) {
			val int startOffset = layoutInformation.getStartOffset();
			LOGGER.trace("Start offset for " + namedElement.getName() + ": " + startOffset);
		}
	}
}