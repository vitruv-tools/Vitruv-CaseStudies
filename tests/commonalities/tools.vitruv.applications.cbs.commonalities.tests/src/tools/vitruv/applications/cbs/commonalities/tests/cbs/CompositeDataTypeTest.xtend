package tools.vitruv.applications.cbs.commonalities.tests.cbs

import java.util.List
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tools.vitruv.applications.cbs.commonalities.tests.CBSCommonalitiesExecutionTest
import tools.vitruv.applications.cbs.commonalities.tests.DomainModel
import tools.vitruv.applications.cbs.commonalities.tests.DomainModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.cbs.java.JavaCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.pcm.PcmCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.cbs.uml.UmlCompositeDataTypeTestModels
import tools.vitruv.applications.cbs.commonalities.tests.java.JavaTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.pcm.PcmTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsProvider
import tools.vitruv.applications.cbs.commonalities.tests.util.runner.XtextParametersRunnerFactory

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.ParameterizedTestUtil.*

@RunWith(Parameterized)
@Parameterized.UseParametersRunnerFactory(XtextParametersRunnerFactory)
class CompositeDataTypeTest extends CBSCommonalitiesExecutionTest {

	@Parameterized.Parameters(name='{0} to {1}')
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

	val DomainModels sourceModels
	val DomainModels targetModels

	new(DomainModelsProvider<DomainModels> sourceModelsProvider,
		DomainModelsProvider<DomainModels> targetModelsProvider) {
		this.sourceModels = sourceModelsProvider.getModels(vitruvApplicationTestAdapter)
		this.targetModels = targetModelsProvider.getModels(vitruvApplicationTestAdapter)
	}

	// Empty CompositeDataType

	@Test
	def void emptyCompositeDataTypeCreation() {
		sourceModels.emptyCompositeDataTypeCreation.createAndSynchronize()
		targetModels.emptyCompositeDataTypeCreation.check()
	}

	// Primitive inner elements

	@Test
	def void compositeDataTypeWithBooleanElementCreation() {
		sourceModels.compositeDataTypeWithBooleanElementCreation.createAndSynchronize()
		targetModels.compositeDataTypeWithBooleanElementCreation.check()
	}

	@Test
	def void compositeDataTypeWithIntegerElementCreation() {
		sourceModels.compositeDataTypeWithIntegerElementCreation.createAndSynchronize()
		targetModels.compositeDataTypeWithIntegerElementCreation.check()
	}

	@Test
	def void compositeDataTypeWithDoubleElementCreation() {
		sourceModels.compositeDataTypeWithDoubleElementCreation.createAndSynchronize()
		targetModels.compositeDataTypeWithDoubleElementCreation.check()
	}

	@Test
	def void compositeDataTypeWithStringElementCreation() {
		sourceModels.compositeDataTypeWithStringElementCreation.createAndSynchronize()
		targetModels.compositeDataTypeWithStringElementCreation.check()
	}

	@Test
	def void compositeDataTypeWithWithMultiplePrimitiveElementsCreation() {
		sourceModels.compositeDataTypeWithWithMultiplePrimitiveElementsCreation.createAndSynchronize()
		targetModels.compositeDataTypeWithWithMultiplePrimitiveElementsCreation.check()
	}

	// Multiple CompositeDataTypes

	@Test
	def void multipleCompositeDataTypesWithPrimitiveElementsCreation() {
		sourceModels.multipleCompositeDataTypesWithPrimitiveElementsCreation.createAndSynchronize()
		targetModels.multipleCompositeDataTypesWithPrimitiveElementsCreation.check()
	}

	@Test
	def void compositeDataTypeWithCompositeElementsCreation() {
		sourceModels.compositeDataTypeWithCompositeElementsCreation.createAndSynchronize()
		targetModels.compositeDataTypeWithCompositeElementsCreation.check()
	}

	// TODO renaming
}
