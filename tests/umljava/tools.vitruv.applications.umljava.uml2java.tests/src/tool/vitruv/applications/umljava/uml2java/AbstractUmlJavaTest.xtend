package tool.vitruv.applications.umljava.uml2java

import tools.vitruv.aplications.umljava.uml2java.UmlToJavaChangePropagationSpecification
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Model
import tools.vitruv.framework.tests.VitruviusApplicationTest
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.domains.java.JavaDomainProvider

class AbstractUmlJavaTest extends VitruviusApplicationTest {
	private static val MODEL_FILE_EXTENSION = "uml";
	private static val MODEL_NAME = "model";
	
	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}
	
	protected def Model getRootElement() {
		return MODEL_NAME.projectModelPath.firstRootElement as Model;
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new UmlToJavaChangePropagationSpecification()]; 
	}
	
	override protected getVitruvDomains() {
		return #[new UmlDomainProvider().domain, new JavaDomainProvider().domain];
	}
	
	override protected setup() {
		val umlModel = UMLFactory.eINSTANCE.createModel();
		umlModel.name = MODEL_NAME;
		createAndSynchronizeModel(MODEL_NAME.projectModelPath, umlModel);
	}
	
	override protected cleanup() {
		// Do nothing
	}
	
}
