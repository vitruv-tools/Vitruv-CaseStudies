package tools.vitruv.applications.pcmumlcomponents.pcm2uml.versioning

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Model

import org.junit.Test

import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.Repository

import tools.vitruv.framework.tests.util.TestUtil
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.ConflictDetector
import tools.vitruv.framework.versioning.MultiChangeConflict
import tools.vitruv.framework.versioning.SimpleChangeConflict
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.emfstore.LocalRepository
import tools.vitruv.framework.versioning.emfstore.PushState
import tools.vitruv.framework.versioning.emfstore.RemoteRepository
import tools.vitruv.framework.versioning.emfstore.impl.LocalRepositoryImpl
import tools.vitruv.framework.versioning.emfstore.impl.RemoteRepositoryImpl
import tools.vitruv.framework.vsum.VersioningVirtualModel

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not

import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize

import static org.junit.Assert.assertThat

class UmlToPCMBothDirectionsVersioningTest extends UmlToPCMBothDirectionsTest {
	// Static values.
	static val AUTHOR_NAMES = #["Me", "Not me"]
	static val MY_NAME = "myComponent"
	static val THEIR_NAME = "theirComponent"

	// Static functions.
	static val acceptTheirChangesCallback = [ Conflict c |
		if (c instanceof MultiChangeConflict) {
			return c.targetChanges
		} else if (c instanceof SimpleChangeConflict) {
			return #[c.targetChange]
		} else
			#[]
	]

	static val acceptMyChangesCallback = [ Conflict c |
		if (c instanceof MultiChangeConflict) {
			return c.sourceChanges
		} else if (c instanceof SimpleChangeConflict) {
			return #[c.sourceChange]
		} else
			#[]
	]

	static val triggeredCallback = [ Conflict c |
		if (c instanceof MultiChangeConflict) {
			return c.triggeredTargetChanges
		} else
			#[]
	]
	// Variables.
	protected ConflictDetector conflictDetector = ConflictDetector::createConflictDetector
	LocalRepository<RemoteRepository> mylocalRepository
	LocalRepository<RemoteRepository> theirLocalRepository
	RemoteRepository remoteRepository
	VURI myPCMVURI
	VURI myUMLVURI
	VURI theirPCMVURI
	VURI theirUMLVURI

	private def VURI getCorrespondentVURI(EObject e) {
		val correspondences1 = correspondenceModel.getCorrespondingEObjects(#[myUMLModel])
		assertThat(correspondences1, hasSize(1))
		val pcmRepo = correspondences1.flatten.get(0)
		myPCMVURI = VURI::getInstance(pcmRepo.eResource)
	}

	override setup() {
		super.setup
		myUMLVURI = VURI::getInstance(myUMLModel.eResource)
		myPCMVURI = myUMLModel.correspondentVURI
		theirUMLVURI = myUMLVURI.createVURIByReplacing(MY_MODEL_NAME, THEIR_MODEL_NAME)
		theirPCMVURI = myPCMVURI.createVURIByReplacing(MY_MODEL_NAME, THEIR_MODEL_NAME)

		mylocalRepository = new LocalRepositoryImpl
		theirLocalRepository = new LocalRepositoryImpl
		remoteRepository = new RemoteRepositoryImpl
		val localRepos = #[mylocalRepository, theirLocalRepository]

		localRepos.forEach[addRemoteRepository(remoteRepository)]
		localRepos.forEach[r, i|r.author = Author::createAuthor(AUTHOR_NAMES.get(i))]

		val VersioningVirtualModel newVirtualModel = TestUtil::createVirtualModel(
			"newVMname",
			true,
			vitruvDomains,
			createChangePropagationSpecifications,
			userInteractor
		) as VersioningVirtualModel

		mylocalRepository.virtualModel = virtualModel
		theirLocalRepository.virtualModel = newVirtualModel

		mylocalRepository.addOrigin(mylocalRepository.currentBranch, remoteRepository)
		theirLocalRepository.addOrigin(theirLocalRepository.currentBranch, remoteRepository)
	}

	@Test
	def void testCreateAndRenameConflictAndAcceptTheirName() {
		// PS Same base in both virtual models.
		val myUMLComponent = createUmlComponent(COMPONENT_NAME, false)

		saveAndSynchronizeChanges(rootElement)
		assertThat(mylocalRepository.commit("Base commit", myUMLVURI).changes, hasSize(2))
		assertThat(mylocalRepository.push, is(PushState::SUCCESS))

		theirLocalRepository.pull
		theirLocalRepository.checkout(theirUMLVURI)

		// PS Check if changes are in new virtual model.
		val model = theirLocalRepository.virtualModel.getModelInstance(theirUMLVURI).firstRootEObject as Model
		val myRepo = mylocalRepository.virtualModel.getModelInstance(myPCMVURI).firstRootEObject as Repository
		val theirRep = theirLocalRepository.virtualModel.getModelInstance(theirPCMVURI).firstRootEObject as Repository

		// PS TODO When reapplying the already recorded changes to other virtual model, only the original changes 
		// are applied. So the correspondent PCM elements get new IDs. To handle the objects in PCM still as the same object 
		// although the IDs are different, this mapping is used. 
		theirLocalRepository.addIDPair(theirRep.id -> myRepo.id)
		val myPCMComponent = myRepo.components__Repository.get(0) as BasicComponent
		val theirPCMComponent1 = theirRep.components__Repository.get(0) as BasicComponent
		theirLocalRepository.addIDPair(myPCMComponent.id -> theirPCMComponent1.id)

		val umlComponent = model.packagedElements.get(0) as Component
		assertThat(umlComponent.name, equalTo(COMPONENT_NAME))
		val correspondences1 = theirLocalRepository.virtualModel.correspondenceModel.
			getCorrespondingEObjects(#[umlComponent]).flatten
		assertThat(correspondences1, iterableWithSize(1))
		val basicComponent1 = correspondences1.get(0) as BasicComponent
		assertThat(basicComponent1.entityName, equalTo(COMPONENT_NAME))

		// PS Change 
		myUMLComponent.name = tools.vitruv.applications.pcmumlcomponents.pcm2uml.versioning.
			UmlToPCMBothDirectionsVersioningTest.MY_NAME
		saveAndSynchronizeChanges(rootElement)

		assertThat(mylocalRepository.commit("My commit", myUMLVURI).changes, hasSize(1))
		assertThat(mylocalRepository.push, is(PushState::SUCCESS))

		// PS Change name in pcm, commit and push should abort 
		val correspondences = theirLocalRepository.virtualModel.correspondenceModel.
			getCorrespondingEObjects(#[umlComponent]).flatten.toList
		assertThat(correspondences, hasSize(1))
		val theirPCMComponent = correspondences.get(0) as BasicComponent
		assertThat(theirPCMComponent.entityName, equalTo(COMPONENT_NAME))
		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(theirPCMComponent.eResource.URI, true)
		val repo = sourceModel.contents.get(0) as Repository
		val theirModifiableComponent = repo.components__Repository.get(0) as BasicComponent
		startRecordingChanges(repo)
		theirModifiableComponent.entityName = tools.vitruv.applications.pcmumlcomponents.pcm2uml.versioning.
			UmlToPCMBothDirectionsVersioningTest.THEIR_NAME
		saveAndSynchronizeChanges(theirLocalRepository.virtualModel, repo)
		theirPCMVURI = VURI::getInstance(theirPCMComponent.eResource)
		val rootToRootMap = #{
			myUMLVURI.EMFUri.toFileString -> theirUMLVURI.EMFUri.toFileString,
			myPCMVURI.EMFUri.toFileString -> theirPCMVURI.EMFUri.toFileString
		}
		conflictDetector.addMap(rootToRootMap)
		assertThat(theirLocalRepository.commit("Their commit", theirPCMVURI).changes, hasSize(1))
		assertThat(theirLocalRepository.push, is(PushState::COMMIT_NOT_ACCEPTED))

		// PS Pull new commit and merge 
		val remoteBranch = theirLocalRepository.currentBranch.remoteBranch
		assertThat(theirLocalRepository.getCommits(remoteBranch), hasSize(2))
		theirLocalRepository.pull
		assertThat(theirLocalRepository.getCommits(remoteBranch), hasSize(3))
		val lastRemoteCommit = theirLocalRepository.getCommits(remoteBranch).last
		val lastLocalCommit = theirLocalRepository.getCommits(theirLocalRepository.currentBranch).last
		assertThat(lastRemoteCommit.identifier, not(equalTo(lastLocalCommit.identifier)))

		// PS Merge and accept their changes 
		val mergeCommit = theirLocalRepository.merge(
			remoteBranch -> mylocalRepository.virtualModel,
			theirLocalRepository.currentBranch -> theirLocalRepository.virtualModel,
			acceptTheirChangesCallback,
			triggeredCallback,
			theirLocalRepository.virtualModel
		)
		assertThat(mergeCommit.changes, hasSize(1))
		assertThat(theirLocalRepository.push, is(PushState::SUCCESS))
		val modelAgain = theirLocalRepository.virtualModel.getModelInstance(theirUMLVURI).firstRootEObject as Model
		val umlComponentAgain = modelAgain.packagedElements.get(0) as Component
		assertThat(umlComponentAgain.name, is(THEIR_NAME))
		val correspondences2 = theirLocalRepository.virtualModel.correspondenceModel.
			getCorrespondingEObjects(#[umlComponentAgain]).flatten.toList
		assertThat(correspondences2, hasSize(1))
		val pcmComponentAgain = correspondences2.get(0) as BasicComponent
		assertThat(pcmComponentAgain.entityName, is(THEIR_NAME))
	}

	@Test
	def void testCreateAndRenameConflictAndAcceptMyName() {
		// PS Same base in both virtual models.
		val myUMLComponent = createUmlComponent(COMPONENT_NAME, false)

		saveAndSynchronizeChanges(rootElement)
		assertThat(mylocalRepository.commit("Base commit", myUMLVURI).changes, hasSize(2))
		assertThat(mylocalRepository.push, is(PushState::SUCCESS))

		theirLocalRepository.pull
		theirLocalRepository.checkout(theirUMLVURI)

		// PS Check if changes are in new virtual model.
		val model = theirLocalRepository.virtualModel.getModelInstance(theirUMLVURI).firstRootEObject as Model
		val myRepo = mylocalRepository.virtualModel.getModelInstance(myPCMVURI).firstRootEObject as Repository
		val theirRep = theirLocalRepository.virtualModel.getModelInstance(theirPCMVURI).firstRootEObject as Repository

		// PS TODO When reapplying the already recorded changes to other virtual model, only the original changes 
		// are applied. So the correspondent PCM elements get new IDs. To handle the objects in PCM still as the same object 
		// although the IDs are different, this mapping is used. 
		theirLocalRepository.addIDPair(theirRep.id -> myRepo.id)
		val myPCMComponent = myRepo.components__Repository.get(0) as BasicComponent
		val theirPCMComponent1 = theirRep.components__Repository.get(0) as BasicComponent
		theirLocalRepository.addIDPair(myPCMComponent.id -> theirPCMComponent1.id)

		val umlComponent = model.packagedElements.get(0) as Component
		assertThat(umlComponent.name, equalTo(COMPONENT_NAME))
		val correspondences1 = theirLocalRepository.virtualModel.correspondenceModel.
			getCorrespondingEObjects(#[umlComponent]).flatten
		assertThat(correspondences1, iterableWithSize(1))
		val basicComponent1 = correspondences1.get(0) as BasicComponent
		assertThat(basicComponent1.entityName, equalTo(COMPONENT_NAME))

		// PS Change 
		myUMLComponent.name = tools.vitruv.applications.pcmumlcomponents.pcm2uml.versioning.
			UmlToPCMBothDirectionsVersioningTest.MY_NAME
		saveAndSynchronizeChanges(rootElement)

		assertThat(mylocalRepository.commit("My commit", myUMLVURI).changes, hasSize(1))
		assertThat(mylocalRepository.push, is(PushState::SUCCESS))

		// PS Change name in pcm, commit and push should abort 
		val correspondences = theirLocalRepository.virtualModel.correspondenceModel.
			getCorrespondingEObjects(#[umlComponent]).flatten.toList
		assertThat(correspondences, hasSize(1))
		val theirPCMComponent = correspondences.get(0) as BasicComponent
		assertThat(theirPCMComponent.entityName, equalTo(COMPONENT_NAME))
		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(theirPCMComponent.eResource.URI, true)
		val repo = sourceModel.contents.get(0) as Repository
		val theirModifiableComponent = repo.components__Repository.get(0) as BasicComponent
		startRecordingChanges(repo)
		theirModifiableComponent.entityName = tools.vitruv.applications.pcmumlcomponents.pcm2uml.versioning.
			UmlToPCMBothDirectionsVersioningTest.THEIR_NAME
		saveAndSynchronizeChanges(theirLocalRepository.virtualModel, repo)
		theirPCMVURI = VURI::getInstance(theirPCMComponent.eResource)
		val rootToRootMap = #{
			myUMLVURI.EMFUri.toFileString -> theirUMLVURI.EMFUri.toFileString,
			myPCMVURI.EMFUri.toFileString -> theirPCMVURI.EMFUri.toFileString
		}
		conflictDetector.addMap(rootToRootMap)
		assertThat(theirLocalRepository.commit("Their commit", theirPCMVURI).changes, hasSize(1))
		assertThat(theirLocalRepository.push, is(PushState::COMMIT_NOT_ACCEPTED))

		// PS Pull new commit and merge 
		val remoteBranch = theirLocalRepository.currentBranch.remoteBranch
		assertThat(theirLocalRepository.getCommits(remoteBranch), hasSize(2))
		theirLocalRepository.pull
		assertThat(theirLocalRepository.getCommits(remoteBranch), hasSize(3))
		val lastRemoteCommit = theirLocalRepository.getCommits(remoteBranch).last
		val lastLocalCommit = theirLocalRepository.getCommits(theirLocalRepository.currentBranch).last
		assertThat(lastRemoteCommit.identifier, not(equalTo(lastLocalCommit.identifier)))

		// PS Merge and accept their changes 
		val mergeCommit = theirLocalRepository.merge(
			remoteBranch -> mylocalRepository.virtualModel,
			theirLocalRepository.currentBranch -> theirLocalRepository.virtualModel,
			acceptMyChangesCallback,
			triggeredCallback,
			theirLocalRepository.virtualModel
		)
		assertThat(mergeCommit.changes, hasSize(1))
		assertThat(theirLocalRepository.push, is(PushState::SUCCESS))
		val modelAgain = theirLocalRepository.virtualModel.getModelInstance(theirUMLVURI).firstRootEObject as Model
		val umlComponentAgain = modelAgain.packagedElements.get(0) as Component
		assertThat(umlComponentAgain.name, is(MY_NAME))
		val correspondences2 = theirLocalRepository.virtualModel.correspondenceModel.
			getCorrespondingEObjects(#[umlComponentAgain]).flatten.toList
		assertThat(correspondences2, hasSize(1))
		val pcmComponentAgain = correspondences2.get(0) as BasicComponent
		assertThat(pcmComponentAgain.entityName, is(MY_NAME))
	}
}
