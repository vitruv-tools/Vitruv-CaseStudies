package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Usage
import org.eclipse.uml2.uml.Component

import static extension tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.hasItem
import static org.hamcrest.CoreMatchers.is

class InterfaceTest extends AbstractClass2CompTest {

	@Test
	def void testInterfaceRequiredAndProvided() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME»' be represented by a Component?''')
		].respondWith("Yes")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME2 + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME2»' be represented by a Component?''')
		].respondWith("Yes")
		rootElement.propagate [
			val model = it
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
				val interface = UMLFactory.eINSTANCE.createInterface() => [
					name = INTERFACE_NAME
				]
				packagedElements += interface
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
					interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
						name = INTERFACE_REALIZATION_NAME
						suppliers += interface
					]
				]

			]
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME2 + PACKAGE_SUFFIX
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME2
					interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
						name = INTERFACE_REALIZATION_NAME2
						suppliers +=
							model.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX).
								claimPackagedElementWithName(Interface, INTERFACE_NAME)
					]
				]
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME2 + PACKAGE_SUFFIX)
		val component1 = rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		val component2 = rootElement.claimPackagedElementWithName(Component, CLASS_NAME2)
		val componentInterface = rootElement.claimPackagedElementWithName(Interface,
			INTERFACE_NAME + COMP_INTERFACE_SUFFIX)
		component1.claimInterfaceRealization(INTERFACE_REALIZATION_NAME + COMP_IFR_AND_USAGE_SUFFIX, componentInterface)
		val usage = component2.claimPackagedElementWithName(Usage,
			INTERFACE_REALIZATION_NAME2 + COMP_IFR_AND_USAGE_SUFFIX)
		assertThat(usage.suppliers, hasItem(componentInterface))
		assertThat(usage.suppliers.size, is(1))
		assertThat(rootElement.packagedElements.size, is(5))
	}

	@Test
	def void testInterfaceHasNoReachOutwards() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME»' be represented by a Component?''')
		].respondWith("Yes")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME2 + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME2»' be represented by a Component?''')
		].respondWith("Yes")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
				val interface = UMLFactory.eINSTANCE.createInterface() => [
					name = INTERFACE_NAME
				]
				packagedElements += interface
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
					interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
						name = INTERFACE_REALIZATION_NAME
						suppliers += interface
					]
					interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
						name = INTERFACE_REALIZATION_NAME2
						suppliers += interface
					]
				]

			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
		val component = rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		rootElement.claimNoPackagedElementWithName(Interface, INTERFACE_NAME + COMP_INTERFACE_SUFFIX)
		assertTrue(component.interfaceRealizations.empty)
		component.claimNoPackagedElementWithName(Usage, INTERFACE_REALIZATION_NAME2 + COMP_IFR_AND_USAGE_SUFFIX)
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Test
	def void testInterfaceRemoved() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME»' be represented by a Component?''')
		].respondWith("Yes")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME2 + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME2»' be represented by a Component?''')
		].respondWith("Yes")
		rootElement.propagate [
			val model = it
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
				val interface = UMLFactory.eINSTANCE.createInterface() => [
					name = INTERFACE_NAME
				]
				packagedElements += interface
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
					interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
						name = INTERFACE_REALIZATION_NAME
						suppliers += interface
					]
				]

			]
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME2 + PACKAGE_SUFFIX
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME2
					interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
						name = INTERFACE_REALIZATION_NAME2
						suppliers +=
							model.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX).
								claimPackagedElementWithName(Interface, INTERFACE_NAME)
					]
				]
			]
		]

		rootElement => [
			it.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
			it.claimPackagedElementWithName(Package, CLASS_NAME2 + PACKAGE_SUFFIX)
			val component1 = it.claimPackagedElementWithName(Component, CLASS_NAME)
			val component2 = it.claimPackagedElementWithName(Component, CLASS_NAME2)
			val componentInterface = it.claimPackagedElementWithName(Interface, INTERFACE_NAME + COMP_INTERFACE_SUFFIX)
			component1.claimInterfaceRealization(INTERFACE_REALIZATION_NAME + COMP_IFR_AND_USAGE_SUFFIX,
				componentInterface)
			val usage = component2.claimPackagedElementWithName(Usage,
				INTERFACE_REALIZATION_NAME2 + COMP_IFR_AND_USAGE_SUFFIX)
			assertThat(usage.suppliers, hasItem(componentInterface))
			assertThat(usage.suppliers.size, is(1))
			assertThat(it.packagedElements.size, is(5))
		]

		rootElement.propagate [
			claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX) => [
				claimPackagedElementWithName(Interface, INTERFACE_NAME) => [
					destroy()
				]
			]
		]

		rootElement => [
			it.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
			it.claimPackagedElementWithName(Package, CLASS_NAME2 + PACKAGE_SUFFIX)
			val component1 = it.claimPackagedElementWithName(Component, CLASS_NAME)
			val component2 = it.claimPackagedElementWithName(Component, CLASS_NAME2)
			it.claimNoPackagedElementWithName(Interface, INTERFACE_NAME + COMP_INTERFACE_SUFFIX)
			assertTrue(component1.interfaceRealizations.empty)
			component2.claimNoPackagedElementWithName(Usage, INTERFACE_REALIZATION_NAME2 + COMP_IFR_AND_USAGE_SUFFIX)
			assertThat(it.packagedElements.size, is(4))
		]
	}

	@Test
	def void testInterfaceRealizaionRemoved() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME»' be represented by a Component?''')
		].respondWith("Yes")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME2 + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME2»' be represented by a Component?''')
		].respondWith("Yes")
		rootElement.propagate [
			val model = it
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
				val interface = UMLFactory.eINSTANCE.createInterface() => [
					name = INTERFACE_NAME
				]
				packagedElements += interface
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
					interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
						name = INTERFACE_REALIZATION_NAME
						suppliers += interface
					]
				]

			]
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME2 + PACKAGE_SUFFIX
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME2
					interfaceRealizations += UMLFactory.eINSTANCE.createInterfaceRealization() => [
						name = INTERFACE_REALIZATION_NAME2
						suppliers +=
							model.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX).
								claimPackagedElementWithName(Interface, INTERFACE_NAME)
					]
				]
			]
		]

		rootElement => [
			it.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
			it.claimPackagedElementWithName(Package, CLASS_NAME2 + PACKAGE_SUFFIX)
			val component1 = it.claimPackagedElementWithName(Component, CLASS_NAME)
			val component2 = it.claimPackagedElementWithName(Component, CLASS_NAME2)
			val componentInterface = it.claimPackagedElementWithName(Interface, INTERFACE_NAME + COMP_INTERFACE_SUFFIX)
			component1.claimInterfaceRealization(INTERFACE_REALIZATION_NAME + COMP_IFR_AND_USAGE_SUFFIX,
				componentInterface)
			val usage = component2.claimPackagedElementWithName(Usage,
				INTERFACE_REALIZATION_NAME2 + COMP_IFR_AND_USAGE_SUFFIX)
			assertThat(usage.suppliers, hasItem(componentInterface))
			assertThat(usage.suppliers.size, is(1))
			assertThat(it.packagedElements.size, is(5))
		]

		// Remove interface usage
		rootElement.propagate [
			claimPackagedElementWithName(Component, CLASS_NAME2) => [
				claimPackagedElementWithName(Usage, INTERFACE_REALIZATION_NAME2 + COMP_IFR_AND_USAGE_SUFFIX) => [
					destroy()
				]
			]
		]

		rootElement => [
			it.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
			it.claimPackagedElementWithName(Package, CLASS_NAME2 + PACKAGE_SUFFIX)
			val component1 = it.claimPackagedElementWithName(Component, CLASS_NAME)
			val component2 = it.claimPackagedElementWithName(Component, CLASS_NAME2)
			val componentInterface = it.claimPackagedElementWithName(Interface, INTERFACE_NAME + COMP_INTERFACE_SUFFIX)
			component1.claimInterfaceRealization(INTERFACE_REALIZATION_NAME + COMP_IFR_AND_USAGE_SUFFIX,
				componentInterface)
			component2.claimNoPackagedElementWithName(Usage, INTERFACE_REALIZATION_NAME2 + COMP_IFR_AND_USAGE_SUFFIX)
			assertThat(it.packagedElements.size, is(5))
		]

		// Remove interface realization
		rootElement.propagate [
			val interface = claimPackagedElementWithName(Interface, INTERFACE_NAME + COMP_INTERFACE_SUFFIX)
			claimPackagedElementWithName(Component, CLASS_NAME) => [
				claimInterfaceRealization(INTERFACE_REALIZATION_NAME + COMP_IFR_AND_USAGE_SUFFIX, interface) => [
					destroy()
				]
			]
		]

		rootElement => [
			it.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
			it.claimPackagedElementWithName(Package, CLASS_NAME2 + PACKAGE_SUFFIX)
			val component1 = it.claimPackagedElementWithName(Component, CLASS_NAME)
			val component2 = it.claimPackagedElementWithName(Component, CLASS_NAME2)
			val componentInterface = it.claimPackagedElementWithName(Interface, INTERFACE_NAME + COMP_INTERFACE_SUFFIX)
			component1.claimNoInterfaceRealization(INTERFACE_REALIZATION_NAME + COMP_IFR_AND_USAGE_SUFFIX,
				componentInterface)
			component2.claimNoPackagedElementWithName(Usage, INTERFACE_REALIZATION_NAME2 + COMP_IFR_AND_USAGE_SUFFIX)
			assertThat(it.packagedElements.size, is(5))
		]
	}

}
