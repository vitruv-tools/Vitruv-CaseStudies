package tools.vitruv.domains.java.ui.builder;

import org.apache.log4j.Logger;
import org.eclipse.ui.IStartup;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil.buildAllProjectsIncrementally;

/**
 * {@link JavaBuildOnEclipseStartup} issues an incremental build of all open
 * projects just after Eclipse started. This ensures that the respective builder
 * objects do exist (and install EMF editor monitors) before the user starts
 * editing files.
 */
public class JavaBuildOnEclipseStartup implements IStartup {
	private static final Logger LOGGER = Logger.getLogger(JavaBuildOnEclipseStartup.class);

	@Override
	public void earlyStartup() {
		try {
			buildAllProjectsIncrementally(VitruvJavaBuilder.BUILDER_ID);
		} catch (IllegalStateException e) {
			LOGGER.error("Could not perform initial build for all projects", e);
		}
	}
}
