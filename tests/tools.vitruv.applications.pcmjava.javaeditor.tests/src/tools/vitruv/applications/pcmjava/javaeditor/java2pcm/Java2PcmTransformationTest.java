package tools.vitruv.applications.pcmjava.javaeditor.java2pcm;

import static tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaTestUtils.REPOSITORY_NAME;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.URI;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;

import tools.vitruv.applications.pcmjava.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaEditorView;
import tools.vitruv.applications.pcmjava.javaeditor.util.JavaPcmViewFactory;
import tools.vitruv.applications.pcmjava.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.applications.util.temporary.java.JavaPersistenceHelper;
import tools.vitruv.applications.util.temporary.java.JavaSetup;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest;

public class Java2PcmTransformationTest extends ViewBasedVitruvApplicationTest {
	protected JavaPcmViewFactory viewFactory;

	@BeforeAll
	public static final void setupJavaFactories() {
		JavaSetup.prepareFactories();
	}

	@BeforeEach
	public final void setupJavaClasspath() {
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
	}

	@BeforeEach
	public final void setupViewFactory(@TestProject Path testProjectFolder) {
		viewFactory = new JavaPcmViewFactory(testProjectFolder, getVirtualModel());
	}

	@Override
	protected boolean enableTransitiveCyclicChangePropagation() {
		return false;
	}

	@Override
	protected Iterable<? extends ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(new Java2PcmChangePropagationSpecification(), new Pcm2JavaChangePropagationSpecification());
	}

	protected void createRepositoryPackage() throws Exception {
		changeJavaView(view -> {
			createPackageWithPackageInfo(view, new String[] { REPOSITORY_NAME });
		});
	}

	protected Package createPackageWithPackageInfo(View view, final String... namespace) {
		final Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
		final List<String> namespaceList = Arrays.asList(namespace);
		jaMoPPPackage.setName(namespaceList.get(namespaceList.size() - 1));
		jaMoPPPackage.getNamespaces().addAll(namespaceList.subList(0, namespaceList.size() - 1));

		URI createPackageURI = getUri(Path.of(JavaPersistenceHelper.buildJavaFilePath(jaMoPPPackage)));
		view.registerRoot(jaMoPPPackage, createPackageURI);
		return jaMoPPPackage;
	}

	protected String getTypeNameOfPcmDataType(DataType dataType) {
		if (dataType == null) {
			return "void";
		} else if (dataType instanceof PrimitiveDataType) {
			return getTypeNameFromPrimitveDataType((PrimitiveDataType) dataType);
		} else if (dataType instanceof NamedElement) {
			return ((NamedElement) dataType).getEntityName();
		}
		throw new RuntimeException("Unknown data type " + dataType);
	}

	private String getTypeNameFromPrimitveDataType(final PrimitiveDataType dataType) {
		switch (dataType.getType()) {
		case BOOL:
			return "boolean";
		case CHAR:
			return "char";
		case BYTE:
			return "byte";
		case DOUBLE:
			return "double";
		case INT:
			return "int";
		case LONG:
			return "long";
		case STRING:
			return "String";
		}
		throw new RuntimeException("Unknown primitive data type " + dataType);
	}

	// === Shortcuts ===

	protected void changeJavaView(Consumer<CommittableView> modelModification) throws Exception {
		viewFactory.changeJavaView(modelModification);
	}

	protected void changeJavaEditorView(ThrowingConsumer<JavaEditorView> modelModification) throws Exception {
		viewFactory.changeJavaEditorView(modelModification);
	}

	protected void changeJavaEditorViewThrowing(ThrowingConsumer<JavaEditorView> modelModification) throws Exception {

	}

	protected void validatePcmView(Consumer<View> viewValidation) throws Exception {
		viewFactory.validatePcmView(viewValidation);
	}
}
