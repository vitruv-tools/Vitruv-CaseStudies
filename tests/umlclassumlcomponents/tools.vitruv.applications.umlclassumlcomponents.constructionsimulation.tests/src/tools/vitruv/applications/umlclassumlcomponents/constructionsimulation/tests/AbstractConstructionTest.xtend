package tools.vitruv.applications.umlclassumlcomponents.constructionsimulation.tests

import tools.vitruv.applications.umlclassumlcomponents.comp2class.UmlComp2UmlClassChangePropagation
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.tests.VitruviusApplicationTest

abstract class AbstractConstructionTest extends VitruviusApplicationTest  {
	
	override protected createChangePropagationSpecifications() {
		return #[new UmlComp2UmlClassChangePropagation()]
	}	

	//Hack for handling of one singular UML model instead of two
	override protected getVitruvDomains() {
		return #[new UmlDomainProvider().domain]
	}	
	
	//Hack for handling of one singular UML model instead of two
	override protected getCorrespondenceModel() {
		val VitruvDomain umlDomain = this.getVitruvDomains().iterator().next
		return this.getVirtualModel().getCorrespondenceModel(umlDomain.getURI(), umlDomain.getURI()) 
	}
		
	override protected cleanup() {
	}
	
	override protected setup() {
		
	}
}
