package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Class

import static extension tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test

import org.eclipse.uml2.uml.UMLFactory
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.is

class ClassTest extends AbstractClass2CompTest {

	@Test
	def void testCreateComponentForClass() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME»' be represented by a Component?''')
		].respondWith("Yes")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
				]
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Test
	def void testCreateComponentForClassInPackageWithNonMatchingName() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME»' be represented by a Component?''')
		].respondWith("Yes")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
				]
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME)
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Test
	def void testCreate2ComponentsFor2ClassesInSamePackage() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME»' be represented by a Component?''')
		].respondWith("Yes")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
				]
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME2
				]
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		rootElement.claimNoPackagedElementWithName(Component, CLASS_NAME2)
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Test
	def void testCreate2ComponentsFor2ClassesInDifferentPackages() {
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
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
				]
			]
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME2 + PACKAGE_SUFFIX
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME2
				]
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME2 + PACKAGE_SUFFIX)
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME2)
		assertThat(rootElement.packagedElements.size, is(4))
	}

	@Test
	def void testCreateNoComponentForClass() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME»' be represented by a Component?''')
		].respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
				]
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimNoPackagedElementWithName(Component, CLASS_NAME)
		assertThat(rootElement.packagedElements.size, is(1))
	}

	@Test
	def void testRenameClass() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME»' be represented by a Component?''')
		].respondWith("Yes")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
				]
			]
		]

		rootElement.propagate [
			claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX) => [
				claimPackagedElementWithName(Class, CLASS_NAME) => [
					name = CLASS_NAME2
				]
			]
		]

		rootElement.claimNoPackagedElementWithName(Component, CLASS_NAME)
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME2)
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Test
	def void testDeleteClass() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''Should '«CLASS_NAME»' be represented by a Component?''')
		].respondWith("Yes")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
				packagedElements += UMLFactory.eINSTANCE.createClass() => [
					name = CLASS_NAME
				]
			]
		]
		assertThat(rootElement.packagedElements.size, is(2))
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)

		rootElement.propagate [
			claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX) => [
				claimPackagedElementWithName(Class, CLASS_NAME) => [
					destroy()
				]
			]
		]

		rootElement.claimNoPackagedElementWithName(Component, CLASS_NAME)
		assertThat(rootElement.packagedElements.size, is(1))
	}

}
