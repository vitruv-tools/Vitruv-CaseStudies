package tools.vitruv.applications.pcmumlcomponents.pcm2uml.versioning

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Model

import org.junit.Test

import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.Repository

import tools.vitruv.applications.pcmumlcomponents.both.tests.AbstractPcmUmlBothDirectionsTest
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.emfstore.LocalRepository
import tools.vitruv.framework.versioning.emfstore.PushState
import tools.vitruv.framework.versioning.emfstore.RemoteRepository
import tools.vitruv.framework.versioning.emfstore.impl.LocalRepositoryImpl
import tools.vitruv.framework.versioning.emfstore.impl.RemoteRepositoryImpl
import tools.vitruv.framework.versioning.extensions.URIRemapper

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.instanceOf
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.assertThat

class VersioningTest extends AbstractPcmUmlBothDirectionsTest {
	static val newModelName = "model2"
	static extension TuidManager = TuidManager::instance
	static extension URIRemapper = URIRemapper::instance

	Author author1
	Author author2
	LocalRepository localRepository1
	LocalRepository localRepository2
	RemoteRepository remoteRepository
	VURI newSourceVURI
	VURI sourceVURI
	static val componentName = "ComponentName"

	override setup() {
		super.setup
		sourceVURI = VURI::getInstance(rootElement.eResource)
		newSourceVURI = createNewVURI(sourceVURI, MODEL_NAME -> newModelName)
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
	def void changeNameInPCMAndAfterwardsInUML() {
		val pcmComponent1 = createBasicComponent
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
		val model = sourceModel.contents.get(0) as Model
		val newUmlComponent = model.packagedElements.get(0)
		startRecordingChanges(model)
		registerObjectUnderModification(newUmlComponent)
		newUmlComponent.name = "newName"
		updateTuidsOfRegisteredObjects
		val propagatedChanges = saveAndSynchronizeChanges(newUmlComponent)
		assertThat(propagatedChanges.length, is(1))
		val propagatedChange = propagatedChanges.get(0)
		assertThat(propagatedChange.originalChange.EChanges.empty, is(false))
		assertThat(propagatedChange.consequentialChanges.EChanges.empty, is(false))
		val viceVersaCorrespondingElements = correspondenceModel.getCorrespondingEObjects(#[newUmlComponent]).flatten
		assertThat(viceVersaCorrespondingElements.size, is(1))
		val pcmComponent2 = viceVersaCorrespondingElements.get(0) as BasicComponent
		assertThat(pcmComponent2.entityName, equalTo("newName"))
	}

	@Test
	def void changeNameInPCMAndAfterwardsInUMLVersioning() {
		val newName1 = "newName1"
		val newName2 = "newName2"
		val pcmComponent1 = createBasicComponent

		pcmComponent1.entityName = componentName
		rootElement.components__Repository += pcmComponent1
		saveAndSynchronizeChanges(pcmComponent1)

		val simpleCommit1 = localRepository1.commit("First commit", virtualModel, sourceVURI)
		assertThat(simpleCommit1.changes.size, is(2))

		val pushState1 = localRepository1.push
		assertThat(pushState1, is(PushState::SUCCESS))
		localRepository2.pull

		pcmComponent1.entityName = newName1
		val simpleCommit2 = localRepository1.commit('''Name changed to «newName1»''', virtualModel, sourceVURI)
		assertThat(simpleCommit2.changes.size, is(1))

		val pushState2 = localRepository1.push
		assertThat(pushState2, is(PushState::SUCCESS))

		localRepository2.checkout(virtualModel, newSourceVURI)

		val pcmRepositoryCopy = virtualModel.getModelInstance(newSourceVURI).firstRootEObject as Repository
		val pcmComponent2 = pcmRepositoryCopy.components__Repository.findFirst[entityName == componentName]
		assertThat(pcmComponent2, not(equalTo(null)))
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmComponent2]).flatten
		assertThat(correspondingElements.size, is(1))
		val umlComponent = correspondingElements.get(0) as Component

		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(umlComponent.eResource.URI, true)
		val modifiableUmlComponent = sourceModel.allContents.findFirst[it instanceof Component] as Component
		registerObjectUnderModification(modifiableUmlComponent)
		modifiableUmlComponent.name = newName2
		updateTuidsOfRegisteredObjects
		flushRegisteredObjectsUnderModification
		val viceVersaCorrespondingElements = correspondenceModel.getCorrespondingEObjects(#[modifiableUmlComponent]).
			flatten
		assertThat(viceVersaCorrespondingElements.size, is(1))
		val pcmComponent3 = viceVersaCorrespondingElements.get(0) as BasicComponent
		assertThat(pcmComponent3.entityName, equalTo(newName2))
	}

	@Test
	def void testCreateComponent() {

		val pcmComponent1 = createBasicComponent
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
		assertThat(commit.changes.length, is(2))
		localRepository1.push
		localRepository2.pull
		assertThat(localRepository1.commits.length, is(localRepository2.commits.length))
	}
}
