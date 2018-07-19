package tools.vitruv.applications.pcmumlclass.tests

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.VisibilityKind
import org.junit.Test
import org.palladiosimulator.pcm.system.System
import org.palladiosimulator.pcm.system.SystemFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.pcmumlclass.TagLiterals
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static org.junit.Assert.*
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Model
import org.junit.Ignore

// A small 'm' prefix will signal that the eObject is loaded from the resourceSet an therefore modifiable.
// Working only on modifiable instances would theoretically allow for the comparison via identity.
// This would be cleaner for checking composition constraints, as equality [equals(target.container, source)] does not ensure the correct containment relation.
// For now stick with equality.

class SystemConceptTest extends PcmUmlClassApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(SystemConceptTest).simpleName);

	private static val PCM_MODEL_FILE = "model/System.system"
	private static val UML_MODEL_FILE = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME +
			DefaultLiterals.UML_EXTENSION
	private static val PKG_INSERT_CORR_TO_REPOSITORY = 0 // TODO better solution?
	
	private val MODEL_NAME = "testRootModel"
	private val SYSTEM_NAME = "TestSystem"
	
	def protected static checkSystemConcept(
			CorrespondenceModel cm,
			System pcmSystem,
			Package umlSystemPkg,
			Class umlSystemImpl,
			Operation umlSystemConstructor
	){
		assertNotNull(pcmSystem)
		assertNotNull(umlSystemPkg)
		assertNotNull(umlSystemImpl)
		assertNotNull(umlSystemConstructor)
		assertTrue(corresponds(cm, pcmSystem, umlSystemPkg, TagLiterals.SYSTEM__SYSTEM_PACKAGE))
		assertTrue(corresponds(cm, pcmSystem, umlSystemImpl, TagLiterals.IPRE__IMPLEMENTATION))
		assertTrue(pcmSystem.entityName.toFirstLower == umlSystemPkg.name) 
		assertTrue(pcmSystem.entityName == umlSystemPkg.name.toFirstUpper)
		assertTrue(pcmSystem.entityName + DefaultLiterals.IMPLEMENTATION_SUFFIX == umlSystemImpl.name)
		assertTrue(umlSystemImpl.isFinalSpecialization)
		assertTrue(umlSystemImpl.visibility === VisibilityKind.PUBLIC_LITERAL)
		assertTrue(umlSystemImpl.package === umlSystemPkg)
	}
	
	def protected checkSystemConcept(Package umlSystemPkg){
		assertNotNull(umlSystemPkg)
		val pcmSystem = getModifiableCorr(umlSystemPkg, System, TagLiterals.SYSTEM__SYSTEM_PACKAGE)
		assertNotNull(pcmSystem)
		val umlSystemImpl = getModifiableCorr(pcmSystem, Class, TagLiterals.IPRE__IMPLEMENTATION)
		val umlSystemConstructor = getModifiableCorr(pcmSystem, Operation, TagLiterals.IPRE__CONSTRUCTOR)
		checkSystemConcept(correspondenceModel, pcmSystem, umlSystemPkg, umlSystemImpl, umlSystemConstructor)
	}
	
	def protected checkSystemConcept(System pcmSystem){
		assertNotNull(pcmSystem)
		val umlSystemPkg = getModifiableCorr(pcmSystem, Package, TagLiterals.SYSTEM__SYSTEM_PACKAGE)
		val umlSystemImpl = getModifiableCorr(pcmSystem, Class, TagLiterals.IPRE__IMPLEMENTATION)
		val umlSystemConstructor = getModifiableCorr(pcmSystem, Operation, TagLiterals.IPRE__CONSTRUCTOR)
		checkSystemConcept(correspondenceModel, pcmSystem, umlSystemPkg, umlSystemImpl, umlSystemConstructor)
	}

	@Test
//	@Ignore
	def void testCreateSystemConcept_PCM() {
		userInteractor.addNextSelections(UML_MODEL_FILE)
		
		var pcmSystem = SystemFactory.eINSTANCE.createSystem
		createAndSynchronizeModel(PCM_MODEL_FILE, pcmSystem)
		
		pcmSystem = reloadResourceAndReturnRoot(pcmSystem) as System
		
		pcmSystem.entityName = SYSTEM_NAME
		saveAndSynchronizeChanges(pcmSystem)
		
		pcmSystem = reloadResourceAndReturnRoot(pcmSystem) as System
		
		assertTrue(pcmSystem.entityName == SYSTEM_NAME)
		checkSystemConcept(pcmSystem)
	}
	
	@Test
	def void testCreateSystemConcept_UML() {
		userInteractor.addNextSelections(PCM_MODEL_FILE)
		var umlModel = UMLFactory.eINSTANCE.createModel
		umlModel.name = MODEL_NAME
		createAndSynchronizeModel(UML_MODEL_FILE, umlModel)
		
		userInteractor.addNextSelections(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__SYSTEM)
		var umlSystemPkg = umlModel.createNestedPackage(SYSTEM_NAME)
		
		saveAndSynchronizeChanges(umlSystemPkg)
		umlModel = reloadResourceAndReturnRoot(umlModel) as Model
		umlSystemPkg = umlModel.nestedPackages.findFirst[it.name == SYSTEM_NAME.toFirstLower]
		assertNotNull(umlSystemPkg)
		checkSystemConcept(umlSystemPkg)
		
		var umlSystemImpl = umlSystemPkg.packagedElements.filter(Class).findFirst[it.name == SYSTEM_NAME + DefaultLiterals.IMPLEMENTATION_SUFFIX]
		umlSystemImpl.name = SYSTEM_NAME + "_2_" + DefaultLiterals.IMPLEMENTATION_SUFFIX	
		saveAndSynchronizeChanges(umlSystemImpl)
		umlModel = reloadResourceAndReturnRoot(umlModel) as Model
		umlSystemPkg = umlModel.nestedPackages.findFirst[it.name == SYSTEM_NAME.toFirstLower + "_2_"]
		assertNotNull(umlSystemPkg)
		// need to reload pcm model too
		reloadResourceAndReturnRoot(getModifiableCorrSet(umlSystemPkg, System).head)
		checkSystemConcept(umlSystemPkg)
	}
	
}
