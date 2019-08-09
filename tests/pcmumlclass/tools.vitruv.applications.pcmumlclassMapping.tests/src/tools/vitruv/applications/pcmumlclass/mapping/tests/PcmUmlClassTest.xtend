package tools.vitruv.applications.pcmumlclass.mapping.tests

import mir.reactions.combinedPcmToUml.CombinedPcmToUmlChangePropagationSpecification
import mir.reactions.combinedUmlToPcm.CombinedUmlToPcmChangePropagationSpecification
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.palladiosimulator.pcm.repository.DataType
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.Type
import org.palladiosimulator.pcm.repository.CollectionDataType
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.testutils.VitruviusApplicationTest

abstract class PcmUmlClassTest extends VitruviusApplicationTest {

	override protected createChangePropagationSpecifications() {
		return #[
			new CombinedPcmToUmlChangePropagationSpecification,
			new CombinedUmlToPcmChangePropagationSpecification
		];
	}

	protected var ResourceSet testResourceSet;
	protected var PcmUmlClassTestHelper helper

	protected def getTestResource(URI uri) {
		return testResourceSet.getResource(uri, true)
	}

	override protected getVitruvDomains() {
		return #[new PcmDomainProvider().domain, new UmlDomainProvider().domain];
	}

	override protected cleanup() {
		testResourceSet = null
		helper = null
	}

	override protected setup() {
		testResourceSet = new ResourceSetImpl();
		helper = new PcmUmlClassTestHelper(correspondenceModel, [uri|uri.getModelElement], [uri|uri.modelResource])
	}

	protected def EObject reloadResourceAndReturnRoot(EObject modelElement) {
		stopRecordingChanges(modelElement)
		val resourceURI = modelElement.eResource.URI
		modelElement.eResource.unload
		val rootElement = getModelResource(resourceURI).contents.head
		if (rootElement !== null) {
			startRecordingChanges(rootElement)
		}
		return rootElement
	}

	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b) {
		return cm.getCorrespondingEObjects(#[a]).exists(corrSet|EcoreUtil.equals(corrSet.head, b))
	}

	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b, String tag) {
		return EcoreUtil.equals(b,
			ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, a, tag, b.class).head)
	}

	private static def boolean isCorrectSimpleTypeCorrespondence(
		CorrespondenceModel correspondenceModel,
		DataType pcmDatatype,
		Type umlType,
		int lower,
		int upper
	) {
		val correspondingPrimitiveType = corresponds(correspondenceModel, pcmDatatype, umlType,
			TagLiterals.DATATYPE__TYPE)
		val correspondingCompositeType = corresponds(correspondenceModel, pcmDatatype, umlType,
			TagLiterals.COMPOSITE_DATATYPE__CLASS)
		return (correspondingPrimitiveType || correspondingCompositeType) // inner collection types are not supported
		&& upper == 1 // && lower == 1 // lower could also be 0
	}

	private static def boolean isCorrectCollectionTypeCorrespondence(
		CorrespondenceModel correspondenceModel,
		CollectionDataType pcmCollection,
		Type umlType,
		int lower,
		int upper
	) {
		return lower == 0 && upper == LiteralUnlimitedNatural.UNLIMITED &&
			isCorrectSimpleTypeCorrespondence(correspondenceModel, pcmCollection.innerType_CollectionDataType, umlType,
				1, 1)
	}

	def protected static isCorrect_DataType_Parameter_Correspondence(CorrespondenceModel correspondenceModel,
		DataType pcmDatatype, Parameter umlParam) {
		if (pcmDatatype === null || umlParam.type === null) {
			return pcmDatatype === null && umlParam.type === null
		}
		val simpleTypeCorrespondence = isCorrectSimpleTypeCorrespondence(correspondenceModel, pcmDatatype,
			umlParam.type, umlParam.lower, umlParam.upper)
		val collectionTypeCorrespondenceExists = corresponds(correspondenceModel, pcmDatatype, umlParam,
			TagLiterals.COLLECTION_DATATYPE__PARAMETER)
		val collectionTypeCorrespondenceIsCorrect = if (pcmDatatype instanceof CollectionDataType)
				isCorrectCollectionTypeCorrespondence(correspondenceModel, pcmDatatype as CollectionDataType,
					umlParam.type, umlParam.lower, umlParam.upper)
			else
				null
		return simpleTypeCorrespondence || (collectionTypeCorrespondenceExists && collectionTypeCorrespondenceIsCorrect)
	}

}
