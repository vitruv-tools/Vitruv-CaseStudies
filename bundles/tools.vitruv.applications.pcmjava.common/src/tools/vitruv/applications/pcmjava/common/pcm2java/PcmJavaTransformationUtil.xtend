package tools.vitruv.applications.pcmjava.common.pcm2java

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.palladiosimulator.pcm.core.entity.Entity
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.system.System
import org.emftext.language.java.containers.Package

@Utility
class PcmJavaTransformationUtil {
	/**
	 * Returns the name of a Java package to which the given {@link Entity} should
	 * be mapped. The returned name conforms to the name check performed by
	 * {@link #isPackageFor}.
	 * 
	 * @param entity - 	the PCM {@link Entity} to return the name of a corresponding 
	 * 					package for 
	 */
	static def String getCorrespondingPackageName(Entity entity) {
		return entity.entityName.toFirstLower
	}

	/**
	 * Returns whether the given Java {@link Package} realizes the given PCM
	 * {@link Entity}. This is given if their names match and, in case of a
	 * PCM {@link Repository} or {@link System}, if the package is at the
	 * root level.
	 * 
	 * @param pkg - 	the Java {@link Package}
	 * @param entity - 	the PCM {@link Entity}
	 */
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
