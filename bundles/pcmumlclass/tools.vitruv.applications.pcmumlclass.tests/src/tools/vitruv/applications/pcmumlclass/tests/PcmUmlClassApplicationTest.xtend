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
	def protected <T extends EObject> getCorrSet(EObject source, Class<T> typeFilter){
		return correspondenceModel.getCorrespondingEObjectsByType(source, typeFilter) as Set<T>
	}
	
	def protected <T extends EObject> getCorr(EObject source, Class<T> typeFilter, String tag){
		return ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(correspondenceModel, source, tag, typeFilter).head
	}
	
	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b){
		return cm.getCorrespondingEObjects(#[a]).exists( corrSet | corrSet.head === b)
	}
	
	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b, String tag){
		return b === ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, a,
				tag, b.class).head
	}
}