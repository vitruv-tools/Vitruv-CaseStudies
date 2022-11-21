package tools.vitruv.applications.pcmjava.javaeditor.util;

import static tools.vitruv.applications.pcmjava.javaeditor.util.BlockingProgressMonitor.acceptSynchronousThrowing;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IJavaProject;

import edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil;
import tools.vitruv.applications.pcmjava.javaeditor.java2pcm.legacy.Java2PcmTransformationTest;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;

public class JavaEditorView implements CommittableView {
	private static final Logger logger = Logger.getLogger(Java2PcmTransformationTest.class);
	
	private final Path projectFolder;
	private CommittableView view;
	private IProject eclipseProject;
	private IJavaProject javaProject;
	
	public JavaEditorView(Path projectFolder, View view) {
		this.projectFolder = projectFolder;
		this.view = view.withChangeDerivingTrait();
		createJavaProject();
	}
	
	public IProject getEclipseProject() {
		return eclipseProject;
	}
	
	public IJavaProject getJavaProject() {
		return javaProject;
	}
	
	public JavaEditorManipulationUtil getManipulationUtil() {
		return new JavaEditorManipulationUtil(this, projectFolder);
	}

	@Override
	public Collection<EObject> getRootObjects() {
		return view.getRootObjects();
	}

	@Override
	public boolean isModified() {
		return view.isModified();
	}

	@Override
	public boolean isOutdated() {
		return view.isOutdated();
	}

	@Override
	public void update() {
		try {
			closeJavaProject();
		} catch (CoreException e) {
			logger.error("Failed to close Java project", e);
		}
		view.update();
		createJavaProject();
	}
	
	private void createJavaProject() {
		String projectName = projectFolder.getFileName().toString();
		eclipseProject = IProjectUtil.getWorkspaceProject(projectName);
		if (!eclipseProject.exists()) {
			eclipseProject = IProjectUtil.createProjectAt(projectName, projectFolder);
		}
		javaProject = IProjectUtil.configureAsJavaProject(eclipseProject);
	}

	@Override
	public boolean isClosed() {
		return view.isClosed();
	}

	@Override
	public void registerRoot(EObject object, URI persistAt) {
		view.registerRoot(object, persistAt);
	}

	@Override
	public void moveRoot(EObject object, URI newLocation) {
		view.moveRoot(object, newLocation);
	}
	
	@Override
	public void reloadResource(URI location) {
		view.reloadResource(location);
	}
	
	@Override
	public void moveResource(URI oldLocation, URI newLocation) {
		view.moveResource(oldLocation, newLocation);
	}

	@Override
	public ViewSelection getSelection() {
		return view.getSelection();
	}

	@Override
	public ViewType<? extends ViewSelector> getViewType() {
		return view.getViewType();
	}

	@Override
	public CommittableView withChangeRecordingTrait() {
		return view.withChangeRecordingTrait();
	}

	@Override
	public CommittableView withChangeDerivingTrait(StateBasedChangeResolutionStrategy changeResolutionStrategy) {
		return view;
	}

	@Override
	public void close() throws Exception {
		closeJavaProject();
		view.close();
	}
	
	private void closeJavaProject() throws CoreException {
		if (eclipseProject == null) {
			return;
		}
		acceptSynchronousThrowing(monitor -> eclipseProject.close(monitor));
		eclipseProject = null;
		javaProject = null;
	}

	@Override
	public List<PropagatedChange> commitChanges() {
		return view.commitChanges();
	}
}
