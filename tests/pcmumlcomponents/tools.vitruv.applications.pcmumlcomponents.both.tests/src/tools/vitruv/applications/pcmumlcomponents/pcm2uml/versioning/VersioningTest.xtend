package tools.vitruv.applications.pcmumlcomponents.pcm2uml.versioning

import org.eclipse.uml2.uml.Component
import org.junit.Test
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.AbstractPcmUmlTest
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.emfstore.LocalRepository
import tools.vitruv.framework.versioning.emfstore.RemoteRepository

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.instanceOf
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import tools.vitruv.framework.versioning.emfstore.impl.LocalRepositoryImpl
import tools.vitruv.framework.versioning.emfstore.impl.RemoteRepositoryImpl
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.uml2.uml.Model
import org.palladiosimulator.pcm.repository.BasicComponent

class VersioningTest extends AbstractPcmUmlTest {
	Author author1
	Author author2
	LocalRepository localRepository1
	LocalRepository localRepository2
	RemoteRepository remoteRepository
//	VURI newSourceVURI
	VURI sourceVURI
	static val componentName = "ComponentName"

	override setup() {
		super.setup
		localRepository1 = new LocalRepositoryImpl
		localRepository2 = new LocalRepositoryImpl
		remoteRepository = new RemoteRepositoryImpl

		localRepository1.addRemoteRepository(remoteRepository)
		localRepository2.addRemoteRepository(remoteRepository)

		author1 = Author::createAuthor("Author1")
		author2 = Author::createAuthor("Author2")
		localRepository1.author = author1
		localRepository2.author = author2

		val currentBranch1 = localRepository1.currentBranch
		val currentBranch2 = localRepository2.currentBranch
		localRepository1.addOrigin(currentBranch1, remoteRepository)
		localRepository2.addOrigin(currentBranch2, remoteRepository)
	}

	@Test
	def void testCreate() {
		val pcmComponent1 = RepositoryFactory::eINSTANCE.createBasicComponent
		pcmComponent1.entityName = componentName
		rootElement.components__Repository += pcmComponent1
		saveAndSynchronizeChanges(pcmComponent1)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmComponent1]).flatten

		assertThat(correspondingElements.size, is(1))
		val umlComponent = correspondingElements.get(0) as Component
		assertThat(umlComponent, is(instanceOf(Component)))
		assertThat(umlComponent.name, equalTo(componentName))
		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(umlComponent.eResource.URI, true)
		val newUmlComponent = sourceModel.allContents.findFirst[it instanceof Component] as Component
		newUmlComponent.name = "newName"
		newUmlComponent.saveAndSynchronizeChanges

		val newSourceModel = testResourceSet.getResource(pcmComponent1.eResource.URI, true)
		val newPCMComponent = newSourceModel.allContents.findFirst[it instanceof BasicComponent] as BasicComponent
		assertThat(newPCMComponent.entityName, equalTo("newName"))
	}

	@Test
	def void testCreateComponent() {

		val pcmComponent1 = RepositoryFactory::eINSTANCE.createBasicComponent
		pcmComponent1.entityName = componentName
		rootElement.components__Repository += pcmComponent1
		saveAndSynchronizeChanges(pcmComponent1)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmComponent1]).flatten
		assertThat(correspondingElements.size, is(1))
		val umlComponent = correspondingElements.get(0) as Component
		assertThat(umlComponent, is(instanceOf(Component)))
		assertThat(umlComponent.name, equalTo(componentName))
		sourceVURI = VURI::getInstance(pcmComponent1.eResource)

		val commit = localRepository1.commit("New component created", virtualModel, sourceVURI)
		assertThat(commit.changes.length, is(1))
		localRepository1.push
		localRepository2.pull
		assertThat(localRepository1.commits.length, is(localRepository2.commits.length))
	}
}
