package tools.vitruv.applications.pcmumlcomponents.pcm2uml.versioning

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Model

import org.junit.Test

import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.Repository

import tools.vitruv.applications.pcmumlcomponents.both.tests.AbstractPcmToUmlBothDirectionsTest
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.hamcrest.collection.IsEmptyCollection.empty
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize

import static org.junit.Assert.assertThat

class PCMtoUMLBothDirectionsTest extends AbstractPcmToUmlBothDirectionsTest {
	static extension URIRemapper = URIRemapper::instance
	static val componentName = "ComponentName"
	static val newModelName = "model2"

	Author author1
	Author author2
	LocalRepository<RemoteRepository> localRepository1
	LocalRepository<RemoteRepository> localRepository2
	RemoteRepository remoteRepository
	VURI newSourceVURI
	VURI sourceVURI

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
		val ppC1 = saveAndSynchronizeChanges(pcmComponent1)
		assertThat(ppC1, hasSize(1))
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmComponent1]).flatten

		assertThat(correspondingElements, iterableWithSize(1))
		val umlComponent = correspondingElements.get(0) as Component
		assertThat(umlComponent, instanceOf(Component))
		assertThat(umlComponent.name, equalTo(componentName))
		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(umlComponent.eResource.URI, true)
		val model = sourceModel.contents.get(0) as Model
		val newUmlComponent = model.packagedElements.get(0)
		startRecordingChanges(model)
		newUmlComponent.name = "newName"
		val propagatedChanges = saveAndSynchronizeChanges(newUmlComponent)
		assertThat(propagatedChanges, hasSize(1))
		val propagatedChange = propagatedChanges.get(0)
		assertThat(propagatedChange.originalChange.EChanges, not(empty))
		assertThat(propagatedChange.consequentialChanges.EChanges, hasSize(1))
		val consequentialEChange1 = propagatedChange.consequentialChanges.EChanges.get(0)
		assertThat(consequentialEChange1, instanceOf(ReplaceSingleValuedEAttribute))
		val viceVersaCorrespondingElements = correspondenceModel.getCorrespondingEObjects(#[newUmlComponent]).flatten
		assertThat(viceVersaCorrespondingElements, iterableWithSize(1))
		val pcmComponent2 = viceVersaCorrespondingElements.get(0) as BasicComponent
		assertThat(pcmComponent2.entityName, equalTo("newName"))
	}

	def void changeNameInUMLAndAfterwardsInPCM() {
		val pcmComponent1 = createBasicComponent
		pcmComponent1.entityName = componentName
		rootElement.components__Repository += pcmComponent1
		saveAndSynchronizeChanges(pcmComponent1)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmComponent1]).flatten

		assertThat(correspondingElements, iterableWithSize(1))
		val umlComponent = correspondingElements.get(0) as Component
		assertThat(umlComponent, instanceOf(Component))
		assertThat(umlComponent.name, equalTo(componentName))
		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(umlComponent.eResource.URI, true)
		val model = sourceModel.contents.get(0) as Model
		val newUmlComponent = model.packagedElements.get(0)
		startRecordingChanges(model)
		newUmlComponent.name = "newName"
		val propagatedChanges = saveAndSynchronizeChanges(newUmlComponent)
		assertThat(propagatedChanges, hasSize(1))
		val propagatedChange = propagatedChanges.get(0)
		assertThat(propagatedChange.originalChange.EChanges, not(empty))
		assertThat(propagatedChange.consequentialChanges.EChanges, not(empty))
		val viceVersaCorrespondingElements = correspondenceModel.getCorrespondingEObjects(#[newUmlComponent]).flatten
		assertThat(viceVersaCorrespondingElements, iterableWithSize(1))
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
		assertThat(simpleCommit1.changes, iterableWithSize(2))

		val pushState1 = localRepository1.push
		assertThat(pushState1, is(PushState::SUCCESS))
		localRepository2.pull

		pcmComponent1.entityName = newName1
		saveAndSynchronizeChanges(pcmComponent1)
		val simpleCommit2 = localRepository1.commit('''Name changed to «newName1»''', virtualModel, sourceVURI)
		assertThat(simpleCommit2.changes, iterableWithSize(1))

		val pushState2 = localRepository1.push
		assertThat(pushState2, is(PushState::SUCCESS))

		localRepository2.checkout(virtualModel, newSourceVURI)

		val pcmRepositoryCopy = virtualModel.getModelInstance(newSourceVURI).firstRootEObject as Repository
		val pcmComponent2 = pcmRepositoryCopy.components__Repository.findFirst[entityName == componentName]
		assertThat(pcmComponent2, not(equalTo(null)))
		assertThat(pcmComponent2.entityName, equalTo(componentName))
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmComponent2]).flatten
		assertThat(correspondingElements, iterableWithSize(1))
		val umlComponent = correspondingElements.get(0) as Component
		assertThat(umlComponent.name, equalTo(componentName))

		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(umlComponent.eResource.URI, true)
		val modifiableUmlModel = sourceModel.contents.get(0) as Model

		val modifiableUmlComponent = modifiableUmlModel.packagedElements.get(0) as Component
		modifiableUmlComponent.name = newName2
		saveAndSynchronizeChanges(modifiableUmlComponent)
		val viceVersaCorrespondingElements = correspondenceModel.getCorrespondingEObjects(#[modifiableUmlComponent]).
			flatten
		assertThat(viceVersaCorrespondingElements, iterableWithSize(1))
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
		assertThat(correspondingElements, iterableWithSize(1))
		val umlComponent = correspondingElements.get(0) as Component
		assertThat(umlComponent, instanceOf(Component))
		assertThat(umlComponent.name, equalTo(componentName))
		sourceVURI = VURI::getInstance(pcmComponent1.eResource)

		val commit = localRepository1.commit("New component created", virtualModel, sourceVURI)
		assertThat(commit.changes, hasSize(2))
		localRepository1.push
		localRepository2.pull
		assertThat(localRepository1.commits.length, is(localRepository2.commits.length))
	}
}
