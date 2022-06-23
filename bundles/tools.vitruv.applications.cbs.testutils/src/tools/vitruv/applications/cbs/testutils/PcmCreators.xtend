package tools.vitruv.applications.cbs.testutils

import java.nio.file.Path
import org.palladiosimulator.pcm.PcmFactory
import org.palladiosimulator.pcm.core.composition.CompositionFactory
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.testutils.activeannotations.ModelCreators

import static tools.vitruv.applications.util.temporary.pcm.PcmNamespace.REPOSITORY_FILE_EXTENSION
import static tools.vitruv.applications.util.temporary.pcm.PcmNamespace.SYSTEM_FILE_EXTENSION
import org.palladiosimulator.pcm.system.SystemFactory
import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors

@ModelCreators(factory=PcmFactory)
class PcmCreators {
	public static val pcm = new PcmCreators
	public val repository = new PcmRepositoryCreators
	public val core = new PcmCoreCreators
	public val system = new PcmSystemCreators
	@Accessors
	val MetamodelDescriptor metamodel = new MetamodelDescriptor("pcm", Set.of("repository", "system")) 

	def static repository(Path path) {
		path.resolveSibling('''«path.fileName».«REPOSITORY_FILE_EXTENSION»''')
	}

	def static repository(CharSequence path) {
		Path.of(path.toString).repository
	}

	def static system(Path path) {
		path.resolveSibling('''«path.fileName».«SYSTEM_FILE_EXTENSION»''')
	}

	def static system(CharSequence path) {
		Path.of(path.toString).system
	}

	@ModelCreators(factory=RepositoryFactory)
	static class PcmRepositoryCreators {
	}

	static class PcmCoreCreators {
		public val composition = new PcmCompositionCreators
	}

	@ModelCreators(factory=CompositionFactory)
	static class PcmCompositionCreators {
	}
	
	@ModelCreators(factory=SystemFactory)
	static class PcmSystemCreators {
	}
}
