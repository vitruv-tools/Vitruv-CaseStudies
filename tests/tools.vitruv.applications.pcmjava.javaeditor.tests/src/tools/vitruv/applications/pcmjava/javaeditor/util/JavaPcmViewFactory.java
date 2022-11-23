package tools.vitruv.applications.pcmjava.javaeditor.util;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.core.runtime.CoreException;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.testutils.TestViewFactory;

public class JavaPcmViewFactory extends TestViewFactory {
	private final Path javaProjectFolder;

	public JavaPcmViewFactory(Path javaProjectFolder, ViewProvider viewProvider) {
		super(viewProvider);
		this.javaProjectFolder = javaProjectFolder;
	}

	private View createJavaView() {
		return createViewOfElements("Java packages and classes", List.of(Package.class, CompilationUnit.class));
	}

	private JavaEditorView createJavaEditorView() {
		View javaView = createJavaView();
		return new JavaEditorView(javaProjectFolder, javaView);
	}

	private View createPcmView() {
		return createViewOfElements("PCM", List.of(Repository.class, System.class));
	}

	public void changeJavaView(Consumer<CommittableView> modelModification) throws Exception {
		changeViewRecordingChanges(createJavaView(), modelModification);
	}

	/**
	 * Changes the Java view containing all Java packages and classes as root
	 * elements according to the given modification function. Derives the performed
	 * changes, commits the recorded changes, and closes the view afterwards.
	 */
	public void changeJavaEditorView(ThrowingConsumer<JavaEditorView> modelModification) throws Exception {
		JavaEditorView javaEditorView = createJavaEditorView();
		try {
			modelModification.accept(javaEditorView);
		} catch (CoreException e) {
			throw e;
		} catch (Throwable e) {
			throw new IllegalStateException(e);
		}
		javaEditorView.commitChanges();
		javaEditorView.close();
	}

	/**
	 * Validates the Java view containing all packages and classes by applying the
	 * validation function and closes the view afterwards.
	 */
	public void validateJavaView(Consumer<View> viewValidation) throws Exception {
		validateView(createJavaView(), viewValidation);
	}

	public void validatePcmView(Consumer<View> viewValidation) throws Exception {
		validateView(createPcmView(), viewValidation);
	}
}
