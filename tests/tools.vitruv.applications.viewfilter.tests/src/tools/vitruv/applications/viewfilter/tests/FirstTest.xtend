package tools.vitruv.applications.viewfilter.tests

import java.nio.file.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.containers.CompilationUnit
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.applications.pcmumlclass.CombinedPcmToUmlClassReactionsChangePropagationSpecification
import tools.vitruv.applications.pcmumlclass.CombinedUmlClassToPcmReactionsChangePropagationSpecification
import tools.vitruv.framework.views.View
import tools.vitruv.testutils.ViewBasedVitruvApplicationTest
//import tools.vitruv.applications.pcmumlclass.tests.helper.FluentUMLInterfaceBuilder
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Type
import tools.vitruv.applications.viewfilter.utils.FluentUMLClassBuilder
import tools.vitruv.applications.viewfilter.utils.PcmUmlClassApplicationTestHelper
import java.util.ArrayList
import tools.vitruv.framework.vsum.internal.VirtualModelImpl
import java.io.File
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil

import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.applications.viewfilter.utils.FluentUMLPackageBuilder

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getFirstRootEObject
import static extension tools.vitruv.applications.testutility.uml.UmlQueryUtil.*
import org.eclipse.uml2.uml.internal.impl.PrimitiveTypeImpl
import tools.vitruv.applications.viewfilter.util.framework.impl.FilterSupportingIdentityMappingViewType
import tools.vitruv.framework.views.ViewSelector
import tools.vitruv.applications.viewfilter.util.framework.impl.ModifiableView

@ExtendWith(RegisterMetamodelsInStandalone)
class FirstTest extends ViewBasedVitruvApplicationTest {
	
	@Accessors(PROTECTED_GETTER)
	static val UML_MODEL_FILE_EXTENSION = "uml"
	@Accessors(PROTECTED_GETTER)
	static val UML_MODEL_NAME = "model"
	@Accessors(PROTECTED_GETTER)
	static val UML_MODEL_FOLDER_NAME = "model"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_FOLDER_NAME = "model"
	@Accessors(PROTECTED_GETTER)
	static val MODEL_FILE_EXTENSION = "uml"
	
	static val PROPERTY_NAME = "testAssemblyContextField"
	
	static val UML_MODEL_FILE_PATH = "resources/orhanobut/uml/model.uml"
	
	
	protected var FirstTestFactory viewTestFactory
	protected var extension ViewTestFactory improvedViewTestFactory
	
	@Accessors(PUBLIC_GETTER)
	var org.eclipse.uml2.uml.Class class1
	@Accessors(PUBLIC_GETTER)
	var org.eclipse.uml2.uml.Class class2
	
	override protected getChangePropagationSpecifications() {
				return #[
			new CombinedPcmToUmlClassReactionsChangePropagationSpecification,
			new CombinedUmlClassToPcmReactionsChangePropagationSpecification
		]
	}
	
	@BeforeEach
	def void setup() {
		viewTestFactory = new FirstTestFactory(virtualModel)
		improvedViewTestFactory = new ViewTestFactory(virtualModel)
		createBiggerUmlModel[name = UML_MODEL_NAME]
	}
	
	
	@Test
	def void testCreateSystemConcept_PCM() {
		println("Hello world");
	}
	
	@Test
	def void testView() {
		createUmlView();
	}
	
	@Test
	def void testCreateUmlPcmView() {
		createUmlAndPcmClassesView();
	}
	
	@Test
	def void testCreateFilteredUmlView() {
		var view = createFilteredUmlView();
		(viewType as FilterSupportingIdentityMappingViewType).updateView(view as ModifiableView);
		
		view.selection
		view.viewType
	}
	
	@Test
	def void testFilterForName() {
		createAdvancedUmlModel[name = UML_MODEL_NAME + "big"]
	}
	
	
	 
	
	
//		@Test
//	def void testCreateAssemblyContextConcept_PCM() {
//		createRepositoryWithTwoComponents()
//
//		changePcmView[
//			val pcmComponent1 = PcmUmlClassApplicationTestHelper.getPcmComponent(defaultPcmRepository)
//			val pcmComponent2 = PcmUmlClassApplicationTestHelper.getPcmComponent_2(defaultPcmRepository)
//
//			var pcmAssemblyContext = CompositionFactory.eINSTANCE.createAssemblyContext
//			pcmAssemblyContext.entityName = PROPERTY_NAME
//			// TODO setting the same component as container and encapsulated doesn't seem to trigger change event
//			pcmAssemblyContext.encapsulatedComponent__AssemblyContext = pcmComponent2
//			pcmComponent1.assemblyContexts__ComposedStructure += pcmAssemblyContext
//		]
//
//		validateUmlView[
//			val component2Class = new FluentUMLClassBuilder(PcmUmlClassApplicationTestHelper.COMPONENT_NAME_2_USC +
//				PcmUmlClassApplicationTestHelper.IMPL_SUFFIX, true).addDefaultConstructor.build
//			val component1Class = new FluentUMLClassBuilder(PcmUmlClassApplicationTestHelper.COMPONENT_NAME_USC +
//				PcmUmlClassApplicationTestHelper.IMPL_SUFFIX, true).addDefaultConstructor.addAttribute(PROPERTY_NAME,
//				component2Class).build
//			val component1Package = new FluentUMLPackageBuilder(PcmUmlClassApplicationTestHelper.COMPONENT_NAME_LSC).
//				addPackagedElement(component1Class).build
//			assertEqualityAndContainmentOfUmlPackage(defaultUmlModel,
//				String.join(".", PACKAGE_NAME, PcmUmlClassApplicationTestHelper.COMPONENT_NAME_LSC), component1Package)
//		]
//	}
	
	
	protected def void createAndRegisterRoot(View view, EObject rootObject, URI persistenceUri) {
	view.registerRoot(rootObject, persistenceUri)
	}
	
	protected def Path getUmlProjectModelPath(String modelName) {
		Path.of(UML_MODEL_FOLDER_NAME).resolve(modelName + "." + UML_MODEL_FILE_EXTENSION)
	}
	
	protected def Path getProjectModelPath(String modelName) {
		Path.of(MODEL_FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}
	
//	/**
//	 * Changes the Java view containing all Java packages and classes as root elements 
//	 * according to the given modification function. 
//	 * Records the performed changes, commits the recorded changes, and closes the view afterwards.
//	 */
//	def void changeJavaView((View)=>void modelModification) {
//		changeViewRecordingChanges(createJavaView, modelModification)
//	}
	
//	private def View createJavaView() {
//		createViewOfElements("Java packages and classes", #{Package, CompilationUnit})
//	}
	
//	protected def Path getProjectModelPath(String modelName) {
//	Path.of(MODEL_FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION)
//	}


	protected def void createUmlModel((Model)=>void modelInitialization) {
		changeUmlView [
			val umlModel = UMLFactory.eINSTANCE.createModel
			createAndRegisterRoot(umlModel, UML_MODEL_NAME.projectModelPath.uri)
			modelInitialization.apply(umlModel)
		]
	}
	
	protected def void createBiggerUmlModel((Model)=>void modelInitialization) {
		changeUmlView [
			val umlModel = UMLFactory.eINSTANCE.createModel
			createAndRegisterRoot(umlModel, UML_MODEL_NAME.projectModelPath.uri)
			modelInitialization.apply(umlModel)			
		]
		
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextTextInput("model/System.system")
				
		changeUmlView[
			val package1 = UMLFactory.eINSTANCE.createPackage => [
				it.name = "niklasPackage"
			]
			class1 = package1.createOwnedClass("niklasClass1", false)
			
			userInteraction.addNextSingleSelection(1)
			userInteraction.addNextTextInput("model/System.system")
			
			val package2 = package1.createNestedPackage("niklasNestedPackage")
			class2 = package2.createOwnedClass("niklasClass2", false)
			defaultUmlModel.packagedElements += package1
			
			val stringPrimitiveType = package1.createOwnedPrimitiveType("niklasPrimitiveType1");
			class2.createOwnedAttribute("niklasClass2Attribute", stringPrimitiveType, 0, 1) 
			
//			val package = umlModel.createNestedPackage("test")
//			package.createOwnedClass("test", false)
//			
//			val component2Class = new FluentUMLClassBuilder(PcmUmlClassApplicationTestHelper.COMPONENT_NAME_2_USC +
//			PcmUmlClassApplicationTestHelper.IMPL_SUFFIX, true).addDefaultConstructor.build
//			val component1Class = new FluentUMLClassBuilder(PcmUmlClassApplicationTestHelper.COMPONENT_NAME_USC +
//			PcmUmlClassApplicationTestHelper.IMPL_SUFFIX, true).addDefaultConstructor.addAttribute(PROPERTY_NAME,
//			component2Class).build
//			val component1Package = new FluentUMLPackageBuilder(PcmUmlClassApplicationTestHelper.COMPONENT_NAME_LSC).
//			addPackagedElement(component1Class).build
//			umlModel.packagedElements.add(component1Package)
		]	
		
	}
	
		
	protected def void createAdvancedUmlModel((Model)=>void modelInitialization) {

		changeUmlView [
			val resourceSet = new ResourceSetImpl()
			val model = resourceSet.getResource(URI.createFileURI(UML_MODEL_FILE_PATH), true).firstRootEObject as Model => [
				name = UML_MODEL_NAME
			]
			
			EcoreUtil.resolveAll(model)
	//		changeJavaView [
	//			createAndRegisterRoot(model, UML_MODEL_NAME.projectModelPath.uri)
	//		]
			for (class : model.packagedElements.filter(org.eclipse.uml2.uml.Class).toList) {
				//assertClassWithNameInRootPackage(class.name)
			}
			for (interface : model.packagedElements.filter(org.eclipse.uml2.uml.Interface).toList) {
				//assertInterfaceWithNameInRootPackage(interface.name)
			}
			for (enum : model.packagedElements.filter(org.eclipse.uml2.uml.Enumeration).toList) {
				//assertEnumWithNameInRootPackage(enum.name)
			}
			resourceSet.resources.forEach[unload()]
			resourceSet.resources.clear()

			createAndRegisterRoot(model, UML_MODEL_NAME.umlProjectModelPath.uri)
			modelInitialization.apply(model)
		]
	}
	
	
	//----------- UML Model queries ------
	protected def getDefaultUmlModel(View view) {
		view.claimUmlModel(UML_MODEL_NAME)
	}
	
	
	
}