package tools.vitruv.applications.pcmjava.ejbtransformations.editortests.java2pcm

import org.emftext.language.java.classifiers.ConcreteClassifier
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationInterface
import tools.vitruv.applications.pcmjava.tests.util.java2pcm.Java2PcmTransformationTest
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EjbJava2PcmChangePropagationSpecification
import tools.vitruv.applications.pcmjava.tests.util.pcm2java.Pcm2JavaTestUtils
import org.palladiosimulator.pcm.repository.Repository
import java.io.IOException
import org.eclipse.core.runtime.CoreException
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import static tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EjbAnnotationHelper.*

/**
 * class that contains special methods for EJB testing
 */
abstract class EjbJava2PcmTransformationTest extends Java2PcmTransformationTest {

	protected static val String TEST_CLASS_NAME = "TestEJBClass"
	protected static val String TEST_INTERFACE_NAME = "TestEJBInterface"
	protected static val String TEST_FIELD_NAME = "testEJBfield"

	override protected getChangePropagationSpecifications() {
		return #[new EjbJava2PcmChangePropagationSpecification()]
	}
	
	def protected createEjbClass(String className) {
		val ConcreteClassifier classifier = super.createClassInPackage(this.mainPackage,
			className) as ConcreteClassifier
		val BasicComponent correspondingBasicComponent = addAnnotationToClassifier(classifier, 
			BEAN_ANNOTATION_NAME, STATELESS_ANNOTATION_NAME, BasicComponent, className)
		correspondingBasicComponent
	}

	def protected createEjbInterface(String interfaceName) {
		val ConcreteClassifier classifier = super.createJaMoPPInterfaceInPackage(mainPackage.name, interfaceName)
		val OperationInterface correspondingOpInterface = this.addAnnotationToClassifier(classifier,
			BEAN_ANNOTATION_NAME, REMOTE_ANNOTATION_NAME, OperationInterface, interfaceName)
		correspondingOpInterface
	}

	def protected createPackageEjbClassAndInterface() {
		createPackageAndEjbInterface()
		return this.createEjbClass(TEST_CLASS_NAME)
	}

	def protected createPackageAndEjbInterface() {
		addRepoContractsAndDatatypesPackage()
		this.createEjbInterface(TEST_INTERFACE_NAME)
	}

	override Repository addRepoContractsAndDatatypesPackage() throws IOException, CoreException {
		this.mainPackage = this.createPackageWithPackageInfo(#[Pcm2JavaTestUtils.REPOSITORY_NAME])
		this.createPackageWithPackageInfo(#[Pcm2JavaTestUtils.REPOSITORY_NAME, "contracts"])
		this.createPackageWithPackageInfo(#[Pcm2JavaTestUtils.REPOSITORY_NAME, "datatypes"])
		val Repository repo = claimOne(
			CorrespondenceModelUtil.getCorrespondingEObjectsByType(correspondenceModel, this.mainPackage, Repository))
		return repo
	}
}
