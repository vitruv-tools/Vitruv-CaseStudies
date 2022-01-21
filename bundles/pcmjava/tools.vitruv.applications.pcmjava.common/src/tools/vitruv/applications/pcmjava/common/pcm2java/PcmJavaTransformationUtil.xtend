package tools.vitruv.applications.pcmjava.common.pcm2java

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.palladiosimulator.pcm.core.entity.Entity
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.system.System
import org.emftext.language.java.containers.Package

@Utility
class PcmJavaTransformationUtil {
	static def String getCorrespondingPackageName(Entity entity) {
		return entity.entityName.toFirstLower
	}

	static def boolean isPackageFor(Package pkg, Entity entity) {
		// We ignore the casing of packages
		return switch (entity) {
			Repository,
			System:
				pkg.namespaces.empty
			default:
				true
		} && pkg.name.toLowerCase == entity.entityName.toLowerCase
	}

}
