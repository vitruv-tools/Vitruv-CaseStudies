package tools.vitruv.applications.pcmjava.tests.ejbtransformations.java2pcm

import org.emftext.language.java.classifiers.ConcreteClassifier
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationInterface
import tools.vitruv.applications.pcmjava.tests.util.Java2PcmTransformationTest
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming.EjbJava2PcmChangePropagationSpecification

/**
 * class that contains special methods for EJB testing
 */
abstract class EjbJava2PcmTransformationTest extends Java2PcmTransformationTest {

	public static val String STATELESS_ANNOTATION_NAME = "Stateless"
	public static val String REMOTE_ANNOTATION_NAME = "Remote"
	public static val String EJB_FIELD_ANNOTATION_NAME = "EJB"

	protected static val String TEST_CLASS_NAME = "TestEJBClass"
	protected static val String TEST_INTERFACE_NAME = "TestEJBInterface"
	protected static val String TEST_FIELD_NAME = "testEJBfield"

	override protected getChangePropagationSpecifications() {
		return #[new EjbJava2PcmChangePropagationSpecification()];
	}

	def protected createEjbClass(String className) {
		val ConcreteClassifier classifier = super.createClassInPackage(this.mainPackage,
			className) as ConcreteClassifier
		val BasicComponent correspondingBasicComponent = this.addAnnotationToClassifier(classifier,
			STATELESS_ANNOTATION_NAME, BasicComponent, className)
		correspondingBasicComponent
	}

	def protected createEjbInterface(String interfaceName) {
		val ConcreteClassifier classifier = super.createJaMoPPInterfaceInPackage(mainPackage.name,
			interfaceName) as ConcreteClassifier
		val OperationInterface correspondingOpInterface = this.addAnnotationToClassifier(classifier,
			REMOTE_ANNOTATION_NAME, OperationInterface, interfaceName)
		correspondingOpInterface
	}

	def protected createPackageEjbClassAndInterface() {
		createPackageAndEjbInterface()
		return this.createEjbClass(TEST_CLASS_NAME)
	}

	def protected createPackageAndEjbInterface() {
		super.addRepoContractsAndDatatypesPackage()
		this.createEjbInterface(TEST_INTERFACE_NAME)
	}

}
