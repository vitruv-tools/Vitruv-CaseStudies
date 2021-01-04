package tools.vitruv.applications.cbs.testutils

import java.nio.file.Path
import org.palladiosimulator.pcm.PcmFactory
import org.palladiosimulator.pcm.core.composition.CompositionFactory
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.testutils.activeannotations.ModelCreators

import static tools.vitruv.domains.pcm.PcmNamespace.REPOSITORY_FILE_EXTENSION

@ModelCreators(factory=PcmFactory)
class PcmCreators {
	public static val pcm = new PcmCreators
	public val repository = new PcmRepositoryCreators
	public val core = new PcmCoreCreators
	public val domain = new PcmDomainProvider().domain

	def static repository(Path path) {
		path.resolveSibling('''«path.fileName».«REPOSITORY_FILE_EXTENSION»''')
	}

	def static repository(CharSequence path) {
		Path.of(path.toString).repository
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

	def getDomain() { new PcmDomainProvider().domain }
}
