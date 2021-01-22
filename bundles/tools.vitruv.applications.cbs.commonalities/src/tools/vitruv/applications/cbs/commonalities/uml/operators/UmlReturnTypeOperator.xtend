package tools.vitruv.applications.cbs.commonalities.uml.operators

import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AbstractAttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeType
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

/**
 * Maps between the intermediate representation of return type references and
 * UML Operation parameters.
 * <p>
 * UML represents the return type as a parameter inside an Operation's
 * {@link Operation#getOwnedParameters}. When setting a return type and the
 * Operation has no return parameter yet, we insert a new return parameter. The
 * created return parameter has no name.
 * <p>
 * This reuses the implementation of {@link UmlTypeReferenceOperator} to map
 * between UML and intermediate type references.
 */
@AttributeMappingOperator(
	name='umlReturnType',
	commonalityAttributeType=@AttributeType(multiValued=false, type=String),
	participationAttributeType=@AttributeType(multiValued=true, type=Parameter)
)
class UmlReturnTypeOperator extends AbstractAttributeMappingOperator<String, List<Parameter>> {

	static val Logger logger = Logger.getLogger(UmlReturnTypeOperator)

	// Used to map between UML and intermediate type references:
	val UmlTypeReferenceOperator umlTypeReferenceOperator
	val Operation operation

	/**
	 * @param executionState the reactions execution state
	 * @param targetConceptDomainName the name of the target concept domain
	 */
	new(ReactionExecutionState executionState, String targetConceptDomainName, Operation operation) {
		super(executionState)
		checkNotNull(operation, "operation is null")
		this.umlTypeReferenceOperator = new UmlTypeReferenceOperator(executionState, targetConceptDomainName)
		this.operation = operation
	}

	private static def boolean isReturnParameter(Parameter parameter) {
		assertTrue(parameter !== null)
		return (parameter.direction === ParameterDirectionKind.RETURN_LITERAL)
	}

	/**
	 * @param parameters a list on parameters
	 * @return the return type, or <code>null</code> if there is none
	 */
	private static def getReturnParameter(List<Parameter> parameters) {
		return parameters.filter[isReturnParameter].head
	}

	// UML parameters -> intermediate type reference of the return type
	override applyTowardsCommonality(List<Parameter> umlParameters) {
		val returnParameter = umlParameters.returnParameter
		val umlReturnType = (returnParameter !== null) ? returnParameter.type : null
		val intermediateTypeRef = umlTypeReferenceOperator.applyTowardsCommonality(umlReturnType)
		logger.debug('''Mapping parameters '«umlParameters»' to intermediate return type reference '«
			intermediateTypeRef»'.''')
		return intermediateTypeRef
	}

	// Intermediate type reference of the return type -> UML parameters
	override applyTowardsParticipation(String intermediateTypeReference) {
		val umlReturnType = umlTypeReferenceOperator.applyTowardsParticipation(intermediateTypeReference)
		val List<Parameter> newParameters = new ArrayList(operation.ownedParameters)

		if (umlReturnType !== null) {
			// Setup return parameter if missing:
			var returnParameter = newParameters.returnParameter
			if (returnParameter === null) {
				returnParameter = UMLFactory.eINSTANCE.createParameter
				returnParameter.direction = ParameterDirectionKind.RETURN_LITERAL
				returnParameter.lower = 1
				returnParameter.upper = 1
				newParameters.add(0, returnParameter) // Add as first parameter
			}

			// Set return type:
			returnParameter.type = umlReturnType
		} else {
			// Remove any existing return parameter:
			newParameters.removeIf[isReturnParameter]
		}

		logger.debug('''Mapping return type '«umlReturnType»' and previous parameters '«
			operation.ownedParameters»' to new parameters '«newParameters»'.''')
		return newParameters
	}
}
