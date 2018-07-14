package tools.vitruv.applications.pcmumlclass.tests

import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.testutils.VitruviusApplicationTest
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import java.util.Set
import org.eclipse.emf.ecore.util.EcoreUtil

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
	
	override protected cleanup() {
		//not used so that test-projects can be checked manually for easier debugging
	}
	
	//TODO Documentation
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
		return getModifiableInstance(getCorr(source, typeFilter,tag))
	}
	
	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b){
		return cm.getCorrespondingEObjects(#[a]).exists( corrSet | EcoreUtil.equals(corrSet.head, b))
	}
	
	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b, String tag){
		return EcoreUtil.equals(b, ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, a, tag, b.class).head)
	}
}