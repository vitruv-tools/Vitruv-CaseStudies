package tools.vitruv.applications.viewfilter.tests

import java.nio.file.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
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

class FirstTest extends ViewBasedVitruvApplicationTest {
	
	@Accessors(PROTECTED_GETTER)
	static val UML_MODEL_FILE_EXTENSION = "uml"
	@Accessors(PROTECTED_GETTER)
	static val UML_MODEL_NAME = "model"
	@Accessors(PROTECTED_GETTER)
	static val UML_MODEL_FOLDER_NAME = "model"
	
	protected var extension FirstTestFactory viewTestFactory
	
	override protected getChangePropagationSpecifications() {
				return #[
			new CombinedPcmToUmlClassReactionsChangePropagationSpecification,
			new CombinedUmlClassToPcmReactionsChangePropagationSpecification
		]
	}
	
	@BeforeEach
	def void setup() {
		viewTestFactory = new FirstTestFactory(virtualModel)
		createUmlModel[name = UML_MODEL_NAME]
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
		view.selection
		view.viewType
	}
	
	@Test
	def void testFilterForName() {
		
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
		
	protected def void createUmlModel((Model)=>void modelInitialization) {
	changeUmlView [
		val allUmlPrimitiveTypes = it.getRootObjects().flatMap[it.eAllContents.toList].filter [
				it instanceof PrimitiveType
		].map[it as Type].toList
		val umlTestCompositeType2 = new FluentUMLClassBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME_2, false).build
		val umlTestCompositeType1 = new FluentUMLClassBuilder(
			PcmUmlClassApplicationTestHelper.COMPOSITE_DATATYPE_NAME, false).build
		//val expectedDataTypesPackage = new FluentUMLPackageBuilder(DATATYPES_PACKAGE).addPackagedElement(
		//	umlTestCompositeType1).addPackagedElement(umlTestCompositeType2).build

		var allPossibleParameterTypes = new ArrayList(allUmlPrimitiveTypes)
		allPossibleParameterTypes.add(umlTestCompositeType1)
		allPossibleParameterTypes.add(umlTestCompositeType2)
		//val expectedParameterType = allPossibleParameterTypes.head
		
//		val umlInterface = new FluentUMLInterfaceBuilder(PcmUmlClassApplicationTestHelper.INTERFACE_NAME).
//			addOperation(PcmUmlClassApplicationTestHelper.SIGNATURE_NAME,
//				#[
//					new Quadruple(TEST_PARAMETER_NAME, expectedParameterType, expectedParameterLowerValue,
//						expectedParameterUpperValue)]).build
//		val expectedContractsPackage = new FluentUMLPackageBuilder(CONTRACTS_PACKAGE).
//			addPackagedElement(umlInterface).build
		
		//modelInitialization.apply(umlTestCompositeType1)
		createAndRegisterRoot(umlTestCompositeType1, UML_MODEL_NAME.umlProjectModelPath.uri)
	]
}
	
	
	
}