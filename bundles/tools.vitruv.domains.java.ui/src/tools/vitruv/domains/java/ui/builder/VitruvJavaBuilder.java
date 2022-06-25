package tools.vitruv.domains.java.ui.builder;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import tools.vitruv.domains.java.ui.monitorededitor.JavaMonitoredEditor;
import tools.vitruv.framework.domains.ui.builder.VitruvProjectBuilder;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil.hasBuilder;
import static tools.vitruv.framework.domains.ui.builder.VitruvProjectBuilderArguments.getPropagateAfterBuild;
import static tools.vitruv.framework.domains.ui.builder.VitruvProjectBuilderArguments.getPropagateAfterChangeMilliseconds;

public class VitruvJavaBuilder extends VitruvProjectBuilder {
	// ID of JavaBuilder
	public static final String BUILDER_ID = "tools.vitruv.domains.java.ui.builder.JavaBuilder.id";

	private static Logger logger = Logger.getLogger(VitruvJavaBuilder.class);

	public VitruvJavaBuilder() {
		super();
	}
	private JavaMonitoredEditor monitoredEditor;
	
	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {
		IProject[] result = super.build(kind, args, monitor);
		if (getPropagateAfterBuild(getCommand())) {
			logger.debug("Propagating changes in project " + this.getProject().getName() + " because of triggered build");
			monitoredEditor.propagateRecordedChanges();
		}
		return result;
	}

	@Override
	protected void startMonitoring() {
		String monitoredProjectName = getProject().getName();
		logger.debug("Start monitoring with Vitruv Java builder for project " + monitoredProjectName);
		monitoredEditor = new JavaMonitoredEditor(this.getVirtualModel(), this::isStillRegistered,
				monitoredProjectName);
		monitoredEditor.setAutomaticPropagationAfterMilliseconds(getPropagateAfterChangeMilliseconds(getCommand()));
		monitoredEditor.startMonitoring();
		logger.info("Started monitoring with Vitruv Java builder for project " + monitoredProjectName);
	}

	private boolean isStillRegistered() {
		return getProject().isOpen() && hasBuilder(getProject(), BUILDER_ID);
	}
}
