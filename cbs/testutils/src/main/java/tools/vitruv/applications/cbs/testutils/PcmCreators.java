package tools.vitruv.applications.cbs.testutils;

import static tools.vitruv.applications.util.temporary.pcm.PcmNamespace.REPOSITORY_FILE_EXTENSION;
import static tools.vitruv.applications.util.temporary.pcm.PcmNamespace.SYSTEM_FILE_EXTENSION;

import java.nio.file.Path;
import java.util.Set;
import org.palladiosimulator.pcm.PcmFactory;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.system.SystemFactory;

public class PcmCreators extends FactoryCreators {
	public static final PcmCreators pcm = new PcmCreators();
	public final PcmRepositoryCreators repository = new PcmRepositoryCreators();
	public final PcmCoreCreators core = new PcmCoreCreators();
	public final PcmSystemCreators system = new PcmSystemCreators();
	private final MetamodelDescriptor metamodel = new MetamodelDescriptor("pcm", Set.of("repository", "system"));

	public PcmCreators() {
		super(PcmFactory.eINSTANCE);
	}

	public MetamodelDescriptor getMetamodel() {
		return metamodel;
	}

	public static Path repository(Path path) {
		return path.resolveSibling(path.getFileName() + "." + REPOSITORY_FILE_EXTENSION);
	}

	public static Path repository(CharSequence path) {
		return repository(Path.of(path.toString()));
	}

	public static Path system(Path path) {
		return path.resolveSibling(path.getFileName() + "." + SYSTEM_FILE_EXTENSION);
	}

	public static Path system(CharSequence path) {
		return system(Path.of(path.toString()));
	}

	public static class PcmRepositoryCreators extends FactoryCreators {
		public PcmRepositoryCreators() {
			super(RepositoryFactory.eINSTANCE);
		}
	}

	public static class PcmCoreCreators {
		public final PcmCompositionCreators composition = new PcmCompositionCreators();
	}

	public static class PcmCompositionCreators extends FactoryCreators {
		public PcmCompositionCreators() {
			super(CompositionFactory.eINSTANCE);
		}
	}

	public static class PcmSystemCreators extends FactoryCreators {
		public PcmSystemCreators() {
			super(SystemFactory.eINSTANCE);
		}
	}
}
