package tools.vitruv.applications.pcmumlclass.tests

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Type
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.palladiosimulator.pcm.repository.DataType
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.testutils.VitruviusApplicationTest

abstract class PcmUmlClassApplicationTest extends VitruviusApplicationTest {
	override protected createChangePropagationSpecifications() {
		return #[
			new CombinedPcmToUmlClassReactionsChangePropagationSpecification, 
			new CombinedUmlClassToPcmReactionsChangePropagationSpecification
		];  
	}
	
	private def patchDomains() {
		new PcmDomainProvider().domain.enableTransitiveChangePropagation
		new UmlDomainProvider().domain.enableTransitiveChangePropagation
	}
	override protected getVitruvDomains() {
		patchDomains();
		return #[new PcmDomainProvider().domain, new UmlDomainProvider().domain];
	}
	
	protected var PcmUmlClassApplicationTestHelper helper
	
	override protected setup() {
		helper = new PcmUmlClassApplicationTestHelper(correspondenceModel, resourceSet)
	}
	
	override protected cleanup() {
		//not used so that test-projects can be checked manually for easier debugging
	}
	
	
	
	/**
	 * Reloads the Resource of the passed element and returns the root element.
	 * <br><br>
	 * Changes to a resource that are resolved by the change propagation framework, may not reflect in local instances.
	 * <br>
	 * TODO better describe why that is: 
	 * As I understand it, the VSUM lives in its own ResourceSet and works on its own copies of the instances. 
	 * When an instance in the VSUM is changed by the change propagation, the instance in the local ResourceSet of the Test can become desynchronized, 
	 * if those changes diverge from the manual changes performed by the developer (e.g. round-trip changes to the transformation's source).
	 * <br><br>
	 * WARNING: This invalidates all {@link EObject} instances in the {@link Resource} of the passed element (they turn into proxy elements).
	 * To do anything with them, best re-retrieve them from the reloaded resource by traversing the references of the returned root element, 
	 * or via the element's URI, however, the proxy's URI will not work, if the element was desynchronized before the reload.
	 * 
	 * @param modelElement
	 * 		any eObject in the Resource you want to reload  
	 * @return the root element in the reloaded Resource, or null if none is present
	 */
	protected def EObject reloadResourceAndReturnRoot(EObject modelElement){
		stopRecordingChanges(modelElement) 
		val resourceURI = modelElement.eResource.URI
		modelElement.eResource.unload
		val rootElement = resourceSet.getResource(resourceURI,true).contents.head
		if(rootElement !== null){
			startRecordingChanges(rootElement) // calls changeRecorder.addToRecording -> calls registerContentsAtUuidResolver
		}
		return rootElement
	}
	
	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b){
		return cm.getCorrespondingEObjects(#[a]).exists( corrSet | EcoreUtil.equals(corrSet.head, b))
	}
	
	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b, String tag){
		return EcoreUtil.equals(b, ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, a, tag, b.class).head)
	}
	
	
	//DataType consistency constraints; defined here because it is used in multiple tests
	
	private static def boolean isCorrectSimpleTypeCorrespondence(CorrespondenceModel correspondenceModel, 
		DataType pcmDatatype, Type umlType, int lower, int upper
	){
		val correspondingPrimitiveType = corresponds(correspondenceModel, pcmDatatype, umlType, TagLiterals.DATATYPE__TYPE)
		val correspondingCompositeType = corresponds(correspondenceModel, pcmDatatype, umlType, TagLiterals.COMPOSITE_DATATYPE__CLASS)
		return (correspondingPrimitiveType || correspondingCompositeType) // inner collection types are not supported
			&& lower == 1 && upper == 1
	}
	
	private static def boolean isCorrectCollectionTypeCorrespondence(CorrespondenceModel correspondenceModel, 
		CollectionDataType pcmCollection, Type umlType, int lower, int upper
	){
		return lower == 0 
			&& upper == LiteralUnlimitedNatural.UNLIMITED
			&& isCorrectSimpleTypeCorrespondence(correspondenceModel, pcmCollection.innerType_CollectionDataType, umlType, 1, 1)
	}
	
	def protected static isCorrect_DataType_Property_Correspondence(CorrespondenceModel correspondenceModel, DataType pcmDatatype, Property umlProperty){
		if (pcmDatatype === null || umlProperty.type === null){
			return pcmDatatype === null && umlProperty.type === null
		}
		return isCorrectSimpleTypeCorrespondence(correspondenceModel, pcmDatatype, umlProperty.type, umlProperty.lower, umlProperty.upper)
			|| (corresponds(correspondenceModel, pcmDatatype, umlProperty, TagLiterals.COLLECTION_DATATYPE__PROPERTY)
				&& isCorrectCollectionTypeCorrespondence(correspondenceModel, pcmDatatype as CollectionDataType, umlProperty.type, umlProperty.lower, umlProperty.upper))
	}
	
	def protected static isCorrect_DataType_Parameter_Correspondence(CorrespondenceModel correspondenceModel, DataType pcmDatatype, Parameter umlParam){
		if (pcmDatatype === null || umlParam.type === null){
			return pcmDatatype === null && umlParam.type === null
		}
		return isCorrectSimpleTypeCorrespondence(correspondenceModel, pcmDatatype, umlParam.type, umlParam.lower, umlParam.upper)
			|| (corresponds(correspondenceModel, pcmDatatype, umlParam, TagLiterals.COLLECTION_DATATYPE__PARAMETER)
				&& isCorrectCollectionTypeCorrespondence(correspondenceModel, pcmDatatype as CollectionDataType, umlParam.type, umlParam.lower, umlParam.upper))
	}
	
}