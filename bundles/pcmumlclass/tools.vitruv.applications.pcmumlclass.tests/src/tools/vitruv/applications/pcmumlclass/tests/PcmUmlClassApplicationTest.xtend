package tools.vitruv.applications.pcmumlclass.tests

import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.uml2.uml.PrimitiveType
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.testutils.VitruviusApplicationTest

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*

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
	
	protected var PrimitiveDataType PCM_BOOL
	protected var PrimitiveDataType PCM_INT
	protected var PrimitiveDataType PCM_DOUBLE
	protected var PrimitiveDataType PCM_STRING
	protected var PrimitiveDataType PCM_CHAR
	protected var PrimitiveDataType PCM_BYTE
	
	protected var PrimitiveType UML_BOOL
	protected var PrimitiveType UML_INT
	protected var PrimitiveType UML_REAL
	protected var PrimitiveType UML_STRING
	protected var PrimitiveType UML_UNLIMITED_NATURAL
	
	override protected setup() {
		val pcmPrimitiveTypes = PcmUmlClassHelper.getPcmPrimitiveTypes(resourceSet)	
		PCM_BOOL = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.BOOL]
		PCM_INT = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.INT]
		PCM_DOUBLE = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.DOUBLE]
		PCM_STRING = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.STRING]
		PCM_CHAR = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.CHAR]
		PCM_BYTE = pcmPrimitiveTypes.findFirst[it.type === PrimitiveTypeEnum.BYTE]
		
		val umlPrimitiveTypes = PcmUmlClassHelper.getUmlPrimitiveTypes(resourceSet)
		UML_BOOL =  umlPrimitiveTypes.findFirst[it.name == "Boolean"]
		UML_INT =  umlPrimitiveTypes.findFirst[it.name == "Integer"]
		UML_REAL =  umlPrimitiveTypes.findFirst[it.name == "Real"]
		UML_STRING =  umlPrimitiveTypes.findFirst[it.name == "String"]
		UML_UNLIMITED_NATURAL =  umlPrimitiveTypes.findFirst[it.name == "UnlimitedNatural"]
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
	def protected EObject reloadResourceAndReturnRoot(EObject modelElement){
		// TODO this is a hack for testing: 
		//	- load tools.vitruv.testutils into workspace
		// 	- change VitruviusApplicationTest.changeRecorder to protected, in order to make it accessible here
		changeRecorder.removeFromRecording(modelElement.eResource) 
		val resourceURI = modelElement.eResource.URI
		modelElement.eResource.unload
		val rootElement = resourceSet.getResource(resourceURI,true).contents.head
		if(rootElement !== null){
			startRecordingChanges(rootElement) // calls changeRecorder.addToRecording -> calls registerContentsAtUuidResolver
		}
		return rootElement
	}
	
	/**
	 * Fetches the given {@link EObject} from the {@link ResourceSet} of the running test.
	 * <br>
	 * Elements retrieved via correspondence model are read-only (except in the Transactions performed by the framework),
	 * and live in a different resourceSet. If corresponding elements need to be changed or compared, they should be retrieved via this method, 
	 * or the getModifiableCorr(...) methods.
	 * 
	 * @param original
	 * 		the {@link EObject} instance living in some ResourceSet
	 * @return the object instance in the ResourceSet of this test
	 */
	def protected <T extends EObject> getModifiableInstance(T original){
		val originalURI = EcoreUtil.getURI(original)
		return resourceSet.getEObject(originalURI, true) as T
	}
	
	def protected <T extends EObject> Set<T> getCorrSet(EObject source, Class<T> typeFilter){
		return correspondenceModel.getCorrespondingEObjectsByType(source, typeFilter) as Set<T>
	}
	
	def protected <T extends EObject> T getCorr(EObject source, Class<T> typeFilter, String tag){
		return ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(correspondenceModel, source, tag, typeFilter).head
	}
	
	def protected <T extends EObject> Set<T> getModifiableCorrSet(EObject source, Class<T> typeFilter){
		return getCorrSet(source, typeFilter).map[getModifiableInstance(it)].filter[it !== null].toSet
	}
	
	def protected <T extends EObject> T getModifiableCorr(EObject source, Class<T> typeFilter, String tag){
		val correspondence = getCorr(source, typeFilter,tag)
		if(correspondence === null) return null
		return getModifiableInstance(getCorr(source, typeFilter,tag))
	}
	
	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b){
		return cm.getCorrespondingEObjects(#[a]).exists( corrSet | EcoreUtil.equals(corrSet.head, b))
	}
	
	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b, String tag){
		return EcoreUtil.equals(b, ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, a, tag, b.class).head)
	}
}