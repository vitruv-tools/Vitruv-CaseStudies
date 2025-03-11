package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.util.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsProvider

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.ParameterizedTest
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider

class CompositeDataTypeTest extends CBSCommonalitiesExecutionTest {

	static def List<Object[]> testParameters() {
		val domainModelsProviders = #[
			new PcmTestModelsProvider [new PcmCompositeDataTypeTestModels(it)],
			new UmlTestModelsProvider [new UmlCompositeDataTypeTestModels(it)],
			new JavaTestModelsProvider [new JavaCompositeDataTypeTestModels(it)]
		]
		return domainModelsProviders.orderedPairs
	}

	interface DomainModels {

		static val COMPOSITE_DATATYPE_1_NAME = 'CompositeDataType1'
		static val COMPOSITE_DATATYPE_2_NAME = 'CompositeDataType2'
		static val BOOLEAN_ELEMENT_NAME = 'booleanElement'
		static val INTEGER_ELEMENT_NAME = 'integerElement'
		static val DOUBLE_ELEMENT_NAME = 'doubleElement'
		static val STRING_ELEMENT_NAME = 'stringElement'
		static val COMPOSITE_ELEMENT_1_NAME = 'compositeElement1'
		static val COMPOSITE_ELEMENT_2_NAME = 'compositeElement2'

		// Empty CompositeDataType

		def DomainModel emptyCompositeDataTypeCreation()

		// Primitive inner elements

		def DomainModel compositeDataTypeWithBooleanElementCreation()
		def DomainModel compositeDataTypeWithIntegerElementCreation()
		def DomainModel compositeDataTypeWithDoubleElementCreation()
		def DomainModel compositeDataTypeWithStringElementCreation()

		/**
		 * A composite data type with elements for each of the following
		 * primitive types:
		 * <ul>
		 * <li>Boolean
		 * <li>Integer
		 * <li>Double
		 * <li>String
		 * </ul>
		 */
		def DomainModel compositeDataTypeWithWithMultiplePrimitiveElementsCreation()

		// Multiple CompositeDataTypes

		/**
		 * Two composite data types, one with a <code>boolean</code> typed
		 * element and one with an <code>integer</code> typed element.
		 */
		def DomainModel multipleCompositeDataTypesWithPrimitiveElementsCreation()

		/**
		 * A composite data type with an <code>integer</code> typed element and
		 * another composite data type with two elements which each reference
		 * the first composite data type as their type.
		 */
		def DomainModel compositeDataTypeWithCompositeElementsCreation()
	}

	// Empty CompositeDataType

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void emptyCompositeDataTypeCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.emptyCompositeDataTypeCreation.createAndSynchronize()
		targetModelsProvider.getModels.emptyCompositeDataTypeCreation.check()
	}

	// Primitive inner elements

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void compositeDataTypeWithBooleanElementCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.compositeDataTypeWithBooleanElementCreation.createAndSynchronize()
		targetModelsProvider.getModels.compositeDataTypeWithBooleanElementCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void compositeDataTypeWithIntegerElementCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.compositeDataTypeWithIntegerElementCreation.createAndSynchronize()
		targetModelsProvider.getModels.compositeDataTypeWithIntegerElementCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void compositeDataTypeWithDoubleElementCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.compositeDataTypeWithDoubleElementCreation.createAndSynchronize()
		targetModelsProvider.getModels.compositeDataTypeWithDoubleElementCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void compositeDataTypeWithStringElementCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.compositeDataTypeWithStringElementCreation.createAndSynchronize()
		targetModelsProvider.getModels.compositeDataTypeWithStringElementCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void compositeDataTypeWithWithMultiplePrimitiveElementsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.compositeDataTypeWithWithMultiplePrimitiveElementsCreation.createAndSynchronize()
		targetModelsProvider.getModels.compositeDataTypeWithWithMultiplePrimitiveElementsCreation.check()
	}

	// Multiple CompositeDataTypes

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void multipleCompositeDataTypesWithPrimitiveElementsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.multipleCompositeDataTypesWithPrimitiveElementsCreation.createAndSynchronize()
		targetModelsProvider.getModels.multipleCompositeDataTypesWithPrimitiveElementsCreation.check()
	}

	@ParameterizedTest(name='{0} to {1}')
	@MethodSource("testParameters")
	def void compositeDataTypeWithCompositeElementsCreation(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		sourceModelsProvider.getModels.compositeDataTypeWithCompositeElementsCreation.createAndSynchronize()
		targetModelsProvider.getModels.compositeDataTypeWithCompositeElementsCreation.check()
	}

	// TODO renaming
}
