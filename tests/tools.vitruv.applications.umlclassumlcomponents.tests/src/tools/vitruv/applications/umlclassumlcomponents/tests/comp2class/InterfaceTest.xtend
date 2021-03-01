package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.InterfaceRealization
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Usage
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Package

import static extension tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.hamcrest.CoreMatchers.hasItem
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.MatcherAssert.assertThat

class InterfaceTest extends AbstractComp2ClassTest {

	@Test
	def void testInterfaceRealized() {
		rootElement.propagate [
			val interface = UMLFactory.eINSTANCE.createInterface() => [
				name = INTERFACE_NAME
			]
			packagedElements += interface
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
				interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
					name = INTERFACE_REALIZATION_NAME
					suppliers += interface
				]
			]
		]

		val componentPackage = rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX)
		val componentClass = componentPackage.claimPackagedElementWithName(Class, COMP_NAME)
		val classInterface = componentPackage.claimPackagedElementWithName(Interface,
			INTERFACE_NAME + CLASS_INTERFACE_SUFFIX)
		val classInterfaceRealization = componentClass.claimInterfaceRealization(INTERFACE_REALIZATION_NAME +
			CLASS_IFR_AND_USAGE_SUFFIX, classInterface)
		componentClass.checkInterfaceRealization(classInterfaceRealization, classInterface)
	}

	@Test
	def void testDeleteInterfaceRealization() {
		rootElement.propagate [
			val interface = UMLFactory.eINSTANCE.createInterface() => [
				name = INTERFACE_NAME
			]
			packagedElements += interface
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
				interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
					name = INTERFACE_REALIZATION_NAME
					suppliers += interface
				]
			]
		]

		rootElement.claimPackagedElementWithName(Component, COMP_NAME).propagate [
			val componentInterface = rootElement.claimPackagedElementWithName(Interface, INTERFACE_NAME)
			claimInterfaceRealization(INTERFACE_REALIZATION_NAME, componentInterface) => [
				destroy()
			]
		]

		val componentPackage = rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX)
		val componentClass = componentPackage.claimPackagedElementWithName(Class, COMP_NAME)
		val classInterface = componentPackage.claimPackagedElementWithName(Interface,
			INTERFACE_NAME + CLASS_INTERFACE_SUFFIX)
		componentClass.claimNoInterfaceRealization(INTERFACE_REALIZATION_NAME + CLASS_IFR_AND_USAGE_SUFFIX,
			classInterface)
		assertThat(componentClass.interfaceRealizations.size, is(0))
	}

	@Test
	def void testInterfaceUsed() {
		rootElement.propagate [
			val interface = UMLFactory.eINSTANCE.createInterface() => [
				name = INTERFACE_NAME
			]
			packagedElements += interface
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
				interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
					name = INTERFACE_REALIZATION_NAME
					suppliers += interface
				]
			]
		]

		rootElement.propagate [
			val interface = model.claimPackagedElementWithName(Interface, INTERFACE_NAME)
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME2
				val component = it
				packagedElements += UMLFactory.eINSTANCE.createUsage() => [
					name = USAGE_NAME
					clients += component
					suppliers += interface
				]
			]
		]

		val componentPackage = rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX)
		val classInterface = componentPackage.claimPackagedElementWithName(Interface,
			INTERFACE_NAME + CLASS_INTERFACE_SUFFIX)
		val component2Package = rootElement.claimPackagedElementWithName(Package, COMP_NAME2 + PACKAGE_SUFFIX)
		val component2Class = component2Package.claimPackagedElementWithName(Class, COMP_NAME2)
		val class2InterfaceRealization = component2Class.claimInterfaceRealization(USAGE_NAME +
			CLASS_IFR_AND_USAGE_SUFFIX, classInterface)
		component2Class.checkInterfaceRealization(class2InterfaceRealization, classInterface)
	}

	@Test
	def void testDeleteUsage() {
		rootElement.propagate [
			val interface = UMLFactory.eINSTANCE.createInterface() => [
				name = INTERFACE_NAME
			]
			packagedElements += interface
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
				interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
					name = INTERFACE_REALIZATION_NAME
					suppliers += interface
				]
			]
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME2
				val component = it
				packagedElements += UMLFactory.eINSTANCE.createUsage() => [
					name = USAGE_NAME
					clients += component
					suppliers += interface
				]
			]
		]

		rootElement.claimPackagedElementWithName(Component, COMP_NAME2).propagate [
			claimPackagedElementWithName(Usage, USAGE_NAME) => [
				destroy()
			]
		]

		// Assert that everything still exists except for the interface realization
		val componentPackage = rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX)
		val classInterface = componentPackage.claimPackagedElementWithName(Interface,
			INTERFACE_NAME + CLASS_INTERFACE_SUFFIX)
		val component2Package = rootElement.claimPackagedElementWithName(Package, COMP_NAME2 + PACKAGE_SUFFIX)
		val component2Class = component2Package.claimPackagedElementWithName(Class, COMP_NAME2)
		component2Class.claimNoInterfaceRealization(USAGE_NAME + CLASS_IFR_AND_USAGE_SUFFIX, classInterface)
		assertThat(component2Class.interfaceRealizations.size, is(0))
	}

	@Test
	def void testInterfaceUsedTwice() {
		rootElement.propagate [
			val interface = UMLFactory.eINSTANCE.createInterface() => [
				name = INTERFACE_NAME
			]
			packagedElements += interface
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME
				interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
					name = INTERFACE_REALIZATION_NAME
					suppliers += interface
				]
			]
		]

		rootElement.propagate [
			val interface = claimPackagedElementWithName(Interface, INTERFACE_NAME)
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME2
				val component = it
				packagedElements += UMLFactory.eINSTANCE.createUsage() => [
					name = USAGE_NAME
					clients += component
					suppliers += interface
				]
			]
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = COMP_NAME3
				val component = it
				packagedElements += UMLFactory.eINSTANCE.createUsage() => [
					name = USAGE_NAME2
					clients += component
					suppliers += interface
				]
			]
		]

		val componentPackage = rootElement.claimPackagedElementWithName(Package, COMP_NAME + PACKAGE_SUFFIX)
		val classInterface = componentPackage.claimPackagedElementWithName(Interface,
			INTERFACE_NAME + CLASS_INTERFACE_SUFFIX)
		val component2Package = rootElement.claimPackagedElementWithName(Package, COMP_NAME2 + PACKAGE_SUFFIX)
		val component2Class = component2Package.claimPackagedElementWithName(Class, COMP_NAME2)
		val class2InterfaceRealization = component2Class.claimInterfaceRealization(USAGE_NAME +
			CLASS_IFR_AND_USAGE_SUFFIX, classInterface)
		component2Class.checkInterfaceRealization(class2InterfaceRealization, classInterface)
		val component3Package = rootElement.claimPackagedElementWithName(Package, COMP_NAME3 + PACKAGE_SUFFIX)
		val component3Class = component3Package.claimPackagedElementWithName(Class, COMP_NAME3)
		val class3InterfaceRealization = component3Class.claimInterfaceRealization(USAGE_NAME2 +
			CLASS_IFR_AND_USAGE_SUFFIX, classInterface)
		component3Class.checkInterfaceRealization(class3InterfaceRealization, classInterface)
	}

	def static checkInterfaceRealization(Class realizingClass, InterfaceRealization interfaceRealization,
		Interface expectedInterface) {
		assertThat(interfaceRealization.clients.size, is(1))
		assertThat(interfaceRealization.clients, hasItem(realizingClass))
		assertThat(interfaceRealization.suppliers.size, is(1))
		assertThat(interfaceRealization.suppliers, hasItem(expectedInterface))
		assertThat(interfaceRealization.contract, is(expectedInterface))
		assertThat(realizingClass.interfaceRealizations.size, is(1))
		assertThat(realizingClass.interfaceRealizations, hasItem(interfaceRealization))
	}

}
