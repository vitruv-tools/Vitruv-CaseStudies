package tools.vitruv.applications.pcmumlcomponents.pcm2uml.versioning

import org.eclipse.uml2.uml.Component
import org.junit.Test
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.CompositeComponent
import tools.vitruv.applications.pcmumlcomponents.both.tests.AbstractUmlToPCMBothDirectionsTest

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.assertThat
import org.eclipse.emf.ecore.resource.ResourceSet
import static org.hamcrest.CoreMatchers.instanceOf
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.palladiosimulator.pcm.repository.Repository
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute

class UmlToPCMBothDirectionsTest extends AbstractUmlToPCMBothDirectionsTest {
	@Test
	def void testCreateCompositeComponentAndChangeName() {
		val umlComponent = createUmlComponent(COMPONENT_NAME, true)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComponent]).flatten
		assertThat(1, equalTo(correspondingElements.length))
		assertThat(correspondingElements.get(0) instanceof CompositeComponent, is(true))
		val pcmComponent = getCorrespondingCompositeComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent.entityName))
		val newName = "newName"
		umlComponent.name = newName
		val propagatedChanges = saveAndSynchronizeChanges(umlComponent)
		assertThat(propagatedChanges.length, is(1))
		val propagatedChange = propagatedChanges.get(0)
		assertThat(propagatedChange.consequentialChanges.EChanges.empty, is(false))
		val pcmComponent2 = getCorrespondingCompositeComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent2.entityName))
	}

	@Test
	def void testCreateBasicComponentAndChangeName() {
		val umlComponent = createUmlComponent(COMPONENT_NAME, false)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComponent]).flatten
		assertThat(1, equalTo(correspondingElements.length))
		assertThat(correspondingElements.get(0) instanceof BasicComponent, is(true))
		val pcmComponent = getCorrespondingBasicComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent.entityName))
		val newName = "newName"
		umlComponent.name = newName
		val propagatedChanges = saveAndSynchronizeChanges(umlComponent)
		assertThat(propagatedChanges.length, is(1))
		val propagatedChange = propagatedChanges.get(0)
		assertThat(propagatedChange.consequentialChanges.EChanges.empty, is(false))
		val pcmComponent2 = getCorrespondingBasicComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent2.entityName))
	}

	@Test
	def void testCreateBasicComponentAndChangeNameAndAfterwardsInPCM() {
		val umlComponent = createUmlComponent(COMPONENT_NAME, false)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlComponent]).flatten
		assertThat(1, equalTo(correspondingElements.length))
		assertThat(correspondingElements.get(0) instanceof BasicComponent, is(true))
		val pcmComponent = getCorrespondingBasicComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent.entityName))
		val newName = "newName"
		umlComponent.name = newName
		val propagatedChanges = saveAndSynchronizeChanges(umlComponent)
		assertThat(propagatedChanges.length, is(1))
		val propagatedChange = propagatedChanges.get(0)
		assertThat(propagatedChange.consequentialChanges.EChanges.empty, is(false))
		val pcmComponent2 = getCorrespondingBasicComponent(umlComponent)
		assertThat(umlComponent.name, equalTo(pcmComponent2.entityName))
		val ResourceSet testResourceSet = new ResourceSetImpl
		testResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("*", new XMIResourceFactoryImpl)
		val sourceModel = testResourceSet.getResource(pcmComponent2.eResource.URI, true)
		val repo = sourceModel.contents.get(0) as Repository
		val pcmComponent3 = repo.components__Repository.get(0) as BasicComponent
		startRecordingChanges(repo)
		val newName2 = "newName2"
		pcmComponent3.entityName = newName2
		val propagatedChanges2 = saveAndSynchronizeChanges(repo)
		assertThat(propagatedChanges2.length, is(1))
		val propagatedChange2 = propagatedChanges2.get(0)
		assertThat(propagatedChange2.originalChange.EChanges.empty, is(false))
		assertThat(propagatedChange2.consequentialChanges.EChanges.empty, is(false))
		val consequentialEChange1 = propagatedChange2.consequentialChanges.EChanges.get(0)
		assertThat(consequentialEChange1, instanceOf(ReplaceSingleValuedEAttribute))
		assertThat(propagatedChange2.consequentialChanges.EChanges.length, is(1))
		val viceVersaCorrespondingElements = correspondenceModel.getCorrespondingEObjects(#[pcmComponent3]).flatten
		assertThat(viceVersaCorrespondingElements.size, is(1))
		val umlComponent2 = viceVersaCorrespondingElements.get(0) as Component
		assertThat(umlComponent2.name, equalTo(newName2))
	}

	protected def Component createUmlComponent(String name, Boolean isComposable) {
		val umlComponent = createComponent
		umlComponent.name = name
		rootElement.packagedElements += umlComponent
		val componentMode = if(isComposable) 0 else 1
		userInteractor.addNextSelections(componentMode)
		saveAndSynchronizeChanges(rootElement)
		return umlComponent
	}

	protected def Component createUmlComponent(String name) {
		createUmlComponent(name, false)
	}
}
