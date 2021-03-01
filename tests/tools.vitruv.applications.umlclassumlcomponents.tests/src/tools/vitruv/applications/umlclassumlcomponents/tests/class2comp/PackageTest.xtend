package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import static extension tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled

import static tools.vitruv.framework.userinteraction.UserInteractionOptions.NotificationType.INFORMATION
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Package
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.is

class PackageTest extends AbstractClass2CompTest {

	@Test
	def void testCreatePackageWithoutLink() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
		assertThat(rootElement.packagedElements.size, is(1))
	}

	@Test
	def void testIllegalRenamePackageToDatatypes() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«PACKAGE_NAME»' to an existing Component''')
		].respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = PACKAGE_NAME
			]
		]

		rootElement.propagate [
			claimPackagedElementWithName(Package, PACKAGE_NAME) => [
				name = CLASS_DATATYPES_PACKAGE_NAME
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Package, PACKAGE_NAME)
		assertThat(rootElement.packagedElements.size, is(1))
	}

	@Test
	def void testIllegalRenameToExistingDatatypes() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_DATATYPES_PACKAGE_NAME»' to an existing Component''')
		].respondWith("No")
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«PACKAGE_NAME»' to an existing Component''')
		].respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_DATATYPES_PACKAGE_NAME
			]
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = PACKAGE_NAME
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
		rootElement.claimPackagedElementWithName(Package, PACKAGE_NAME)
		assertThat(rootElement.packagedElements.size, is(2))

		rootElement.propagate [
			claimPackagedElementWithName(Package, PACKAGE_NAME) => [
				name = CLASS_DATATYPES_PACKAGE_NAME
			]
		]

		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
		rootElement.claimPackagedElementWithName(Package, PACKAGE_NAME)
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Disabled
	def void testIllegalRenameFromDatatypesPackage() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.
				contains('''link this Package '«CLASS_DATATYPES_PACKAGE + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("No")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_DATATYPES_PACKAGE_NAME
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
		assertThat(rootElement.packagedElements.size, is(1))

		rootElement.propagate [
			claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME) => [
				name = PACKAGE_NAME
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Package, CLASS_DATATYPES_PACKAGE_NAME)
		assertThat(rootElement.packagedElements.size, is(1))
	}

	@Test
	def void testLinkPackageToSingleExistingComponentAndRenameIt() {
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = CLASS_NAME
			]
		]

		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		assertThat(rootElement.packagedElements.size, is(1))

		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("Yes")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
		assertThat(rootElement.packagedElements.size, is(2))

		rootElement.propagate [
			claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX) => [
				name = CLASS_NAME2 + PACKAGE_SUFFIX
			]
		]

		rootElement.claimPackagedElementWithName(Component, CLASS_NAME2)
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME2 + PACKAGE_SUFFIX)
		assertThat(rootElement.packagedElements.size, is(2))
	}

	@Test
	def void testLinkPackageToSelectedExistingComponentAndRenameIt() {
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = CLASS_NAME
			]
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = CLASS_NAME2
			]
		]

		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME2)
		assertThat(rootElement.packagedElements.size, is(2))

		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("Yes")
		userInteraction.onMultipleChoiceSingleSelection[message.contains('''Choose an existing Component''')].
			respondWith(CLASS_NAME)
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME2)
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
		assertThat(rootElement.packagedElements.size, is(3))
	}

	@Test
	def void testNotLinkPackageToAlreadyLinkedComponent() {
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createComponent() => [
				name = CLASS_NAME
			]
		]

		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		assertThat(rootElement.packagedElements.size, is(1))

		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("Yes")
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
		assertThat(rootElement.packagedElements.size, is(2))

		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«PACKAGE_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("Yes")
		userInteraction.acknowledgeNotification [
			message.contains("Chosen Component is already linked to existing Package")
		]
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = PACKAGE_NAME + PACKAGE_SUFFIX
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		rootElement.claimPackagedElementWithName(Component, CLASS_NAME)
		rootElement.claimPackagedElementWithName(Package, CLASS_NAME + PACKAGE_SUFFIX)
		rootElement.claimPackagedElementWithName(Package, PACKAGE_NAME + PACKAGE_SUFFIX)
		assertThat(rootElement.packagedElements.size, is(3))
	}

	@Test
	def void testCreatePackageWithLinkButNoComponent() {
		userInteraction.onMultipleChoiceSingleSelection [
			message.contains('''link this Package '«CLASS_NAME + PACKAGE_SUFFIX»' to an existing Component''')
		].respondWith("Yes")
		userInteraction.acknowledgeNotification [
			notificationType == INFORMATION && message == "No available Component found"
		]
		rootElement.propagate [
			packagedElements += UMLFactory.eINSTANCE.createPackage() => [
				name = CLASS_NAME + PACKAGE_SUFFIX
			]
		]

		userInteraction.assertAllInteractionsOccurred()
		assertThat(rootElement.packagedElements.size, is(1))
	}

}
