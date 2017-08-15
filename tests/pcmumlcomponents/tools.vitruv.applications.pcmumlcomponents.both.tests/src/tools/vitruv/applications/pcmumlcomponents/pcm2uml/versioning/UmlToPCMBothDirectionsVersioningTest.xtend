package tools.vitruv.applications.pcmumlcomponents.pcm2uml.versioning

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
import tools.vitruv.framework.versioning.extensions.URIRemapper
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.versioning.ConflictDetector

class UmlToPCMBothDirectionsVersioningTest extends UmlToPCMBothDirectionsTest {
	static extension URIRemapper = URIRemapper::instance
	static val myName = "myComponent"
	static val theirName = "theirComponent"
	static val acceptTheirChangesCallback = [ Conflict c |
		if (c instanceof MultiChangeConflict) {
			return c.targetChanges
		} else if (c instanceof SimpleChangeConflict) {
			return #[c.targetChange]
		} else
			#[]
	]
	static val triggeredCallback = [ Conflict c |
		if (c instanceof MultiChangeConflict) {
			return c.triggeredTargetChanges
		} else
			#[]
	]
	protected ConflictDetector conflictDetector = ConflictDetector::createConflictDetector
	Author author1
	Author author2
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

		mylocalRepository.addRemoteRepository(remoteRepository)
		theirLocalRepository.addRemoteRepository(remoteRepository)

		author1 = Author::createAuthor("Me")
		author2 = Author::createAuthor("Not me")
		mylocalRepository.author = author1
		theirLocalRepository.author = author2
		val VersioningVirtualModel newVirtualModel = TestUtil::createVirtualModel("newVMname", true, vitruvDomains,
			createChangePropagationSpecifications, userInteractor) as VersioningVirtualModel

		mylocalRepository.virtualModel = virtualModel
		theirLocalRepository.virtualModel = newVirtualModel
		val currentBranch1 = mylocalRepository.currentBranch
		val currentBranch2 = theirLocalRepository.currentBranch
		mylocalRepository.addOrigin(currentBranch1, remoteRepository)
		theirLocalRepository.addOrigin(currentBranch2, remoteRepository)
	}

	@Test
	def void testCreateAndRenameConflict() {
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
		assertThat(theirRep.id, equalTo(myRepo.id))
		val myPCMComponent = myRepo.components__Repository.get(0) as BasicComponent
		val theirPCMComponent1 = theirRep.components__Repository.get(0) as BasicComponent
		assertThat(myPCMComponent.id, equalTo(theirPCMComponent1.id))
		val umlComponent = model.packagedElements.get(0) as Component
		assertThat(umlComponent.name, equalTo(COMPONENT_NAME))
		val correspondences1 = theirLocalRepository.virtualModel.correspondenceModel.
			getCorrespondingEObjects(#[umlComponent]).flatten
		assertThat(correspondences1, iterableWithSize(1))
		val basicComponent1 = correspondences1.get(0) as BasicComponent
		assertThat(basicComponent1.entityName, equalTo(COMPONENT_NAME))

		// PS Change 
		myUMLComponent.name = myName
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
		theirModifiableComponent.entityName = theirName
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
			remoteBranch,
			theirLocalRepository.currentBranch,
			acceptTheirChangesCallback,
			triggeredCallback,
			theirLocalRepository.virtualModel,
			mylocalRepository.virtualModel
		)
		assertThat(mergeCommit.changes, hasSize(1))
		assertThat(theirLocalRepository.push, is(PushState::SUCCESS))
		val modelAgain = theirLocalRepository.virtualModel.getModelInstance(theirUMLVURI).firstRootEObject as Model
		val umlComponentAgain = modelAgain.packagedElements.get(0) as Component
		assertThat(umlComponentAgain.name, is(myName))
	}
}
