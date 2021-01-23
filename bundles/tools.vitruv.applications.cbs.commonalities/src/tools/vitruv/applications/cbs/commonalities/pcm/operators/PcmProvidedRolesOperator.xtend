package tools.vitruv.applications.cbs.commonalities.pcm.operators

import java.util.List
import org.apache.log4j.Logger
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.ProvidedRole
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

/**
 * Maps between PCM {@link OperationProvidedRole}s and their interfaces and
 * corresponding intermediate type references.
 * <p>
 * This reuses the implementation of {@link PcmTypeReferenceOperator} to map
 * between PCM and intermediate type references.
 */
@AttributeMappingOperator(
	name='pcmProvidedRoles',
	commonalityAttributeType=@AttributeType(multiValued=true, type=String),
	participationAttributeType=@AttributeType(multiValued=true, type=ProvidedRole)
)
class PcmProvidedRolesOperator extends AbstractAttributeMappingOperator<List<String>, List<ProvidedRole>> {

	static val Logger logger = Logger.getLogger(PcmProvidedRolesOperator)

	// Maps between PCM and intermediate type references:
	val PcmTypeReferenceOperator pcmTypeReferenceOperator

	/**
	 * @param executionState the reactions execution state
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String targetConceptDomainName) {
		super(executionState)
		this.pcmTypeReferenceOperator = new PcmTypeReferenceOperator(executionState, targetConceptDomainName)
	}

	// PCM provided roles -> Intermediate type references
	override applyTowardsCommonality(List<ProvidedRole> pcmProvidedRoles) {
		val intermediateTypeReferences = pcmProvidedRoles.filter(OperationProvidedRole).map [ providedRole |
			val pcmInterface = providedRole.providedInterface__OperationProvidedRole
			return pcmTypeReferenceOperator.applyTowardsCommonality(pcmInterface)
		].filterNull.toList
		logger.debug('''Mapping PCM provided roles «pcmProvidedRoles» to intermediate type references «
			intermediateTypeReferences».''')
		return intermediateTypeReferences
	}

	// Intermediate type references -> PCM provided roles
	override applyTowardsParticipation(List<String> intermediateTypeReferences) {
		val pcmProvidedRoles = intermediateTypeReferences.map [
			val pcmInterface = pcmTypeReferenceOperator.applyTowardsParticipation(it) as OperationInterface
			if (pcmInterface === null) return null
			return (RepositoryFactory.eINSTANCE.createOperationProvidedRole => [
				providedInterface__OperationProvidedRole = pcmInterface
			]) as ProvidedRole
		].filterNull.toList
		logger.debug('''Mapping intermediate type references «intermediateTypeReferences» to PCM provided roles «
			pcmProvidedRoles».''')
		return pcmProvidedRoles
	}
}
